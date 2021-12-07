package com.example.instamarket.service.impl;

import com.example.instamarket.exception.ObjectNotFoundException;
import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.entity.Address;
import com.example.instamarket.model.entity.Order;
import com.example.instamarket.model.entity.OrderStatus;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.OrderStatusEnum;
import com.example.instamarket.model.view.OrderViewModel;
import com.example.instamarket.repository.OrderRepository;
import com.example.instamarket.repository.OrderStatusRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderStatusRepository orderStatusRepository, OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeOfferStatus() {
        if(orderStatusRepository.count() == 0){
            Arrays.stream(OrderStatusEnum.values()).map(statusEnum -> {
                OrderStatus orderStatus = new OrderStatus();

                orderStatus.setName(statusEnum);

                return orderStatus;
            }).forEach(orderStatusRepository::save);
        }
    }

    @Override
    public List<OrderViewModel> showRecentOrders(String username) {
        User buyer = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        OrderStatus completedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.COMPLETED).get();

        List<OrderViewModel> orders = orderRepository.findAllByBuyerAndStatusNot(buyer, completedStatus).stream().map(this::mapAsOrderView).collect(Collectors.toList());

        return orders;
    }

    @Override
    public List<OrderViewModel> getSellerRecentOrders(String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.WAITING).get();

        List<OrderViewModel> orders = orderRepository.findAllSellerOrdersByStatus(seller, orderStatus).stream().map(this::mapAsOrderView).collect(Collectors.toList());

        return orders;
    }

    @Override
    public void acceptOrder(Long orderId, String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Order order = orderRepository.findSellerOrder(seller, orderId).orElseThrow(() -> new ObjectNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.ACCEPTED).get();

        order.setStatus(orderStatus);

        orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> getSellerAcceptedOrders(String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.ACCEPTED).get();

        List<OrderViewModel> orders = orderRepository.findAllSellerOrdersByStatus(seller, orderStatus).stream().map(this::mapAsOrderView).collect(Collectors.toList());

        return orders;
    }

    @Override
    public void cancelOrder(Long orderId, String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Order order = orderRepository.findSellerOrder(seller, orderId).orElseThrow(() -> new ObjectNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.CANCELED).get();

        order.setStatus(orderStatus);

        orderRepository.save(order);
    }

    @Override
    public void shipOrder(Long orderId, String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Order order = orderRepository.findSellerOrder(seller, orderId).orElseThrow(() -> new ObjectNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.SHIPPED).get();

        order.setStatus(orderStatus);

        orderRepository.save(order);
    }

    @Override
    public void completeOrder(Long orderId, String username) {
        User buyer = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Order order = orderRepository.findBuyerOrder(buyer, orderId).orElseThrow(() -> new ObjectNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.COMPLETED).get();

        order.setStatus(orderStatus);

        order.setDeliveryDate(LocalDate.now());

        orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> getSellerCompletedOrders(String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.COMPLETED).get();

        List<OrderViewModel> orders = orderRepository.findAllSellerOrdersByStatus(seller, orderStatus).stream().map(this::mapAsOrderView).collect(Collectors.toList());

        return orders;
    }

    @Override
    public List<OrderViewModel> showCompletedOrders(String username) {
        User buyer = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        OrderStatus completedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.COMPLETED).get();

        List<OrderViewModel> orders = orderRepository.findAllByBuyerAndStatus(buyer, completedStatus).stream().map(this::mapAsOrderView).collect(Collectors.toList());

        return orders;
    }

    private OrderViewModel mapAsOrderView(Order order){
        OrderViewModel mappedOrder = modelMapper.map(order, OrderViewModel.class);

        mappedOrder.setOfferTitle(order.getOffer().getTitle());

        mappedOrder.setOfferId(order.getOffer().getId());

        mappedOrder.setOrderStatus(order.getStatus().getName().getDisplayName());

        Set<String> orderImagesUrl = order.getOffer().getImages().stream().map(img -> {
            return img.getImageUrl();
        }).collect(Collectors.toSet());

        mappedOrder.setOfferImages(orderImagesUrl);

        Address address = order.getDeliveryAddress();

        String deliveryAddress = String.format("%s, %s, %s, %s", address.getCountry(), address.getPostalCode(), address.getCity(), address.getStreet());

        mappedOrder.setDeliveryAddress(deliveryAddress);

        if(order.getOfferOption() != null){
            mappedOrder.setOptionKey(order.getOfferOption().getOptionName());
            mappedOrder.setOptionValue(order.getOfferOption().getOptionValue());
        }

        mappedOrder.setShipped(order.getStatus().getName().equals(OrderStatusEnum.SHIPPED));

        return mappedOrder;
    }
}
