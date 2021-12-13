package com.example.instamarket.service.impl;

import com.example.instamarket.exception.ObjectNotFoundException;
import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.entity.SellerRequest;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.model.view.SellerApprovalRequest;
import com.example.instamarket.repository.RoleRepository;
import com.example.instamarket.repository.SellerRequestRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final SellerRequestRepository sellerRequestRepository;
    private final RoleRepository roleRepository;
    private final InstamarketUserServiceImpl instamarketUserService;
    private final SessionRegistry sessionRegistry;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, SellerRequestRepository sellerRequestRepository, RoleRepository roleRepository, InstamarketUserServiceImpl instamarketUserService, SessionRegistry sessionRegistry, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.sellerRequestRepository = sellerRequestRepository;
        this.roleRepository = roleRepository;
        this.instamarketUserService = instamarketUserService;
        this.sessionRegistry = sessionRegistry;
        this.modelMapper = modelMapper;
    }

    @Override
    public void approveSeller(Long sellerRequestId) {
        //TODO Add custom object not found exception
        SellerRequest sellerRequest = sellerRequestRepository.findById(sellerRequestId).orElseThrow();

        if(sellerRequest.isApproved()){
            return;
        }

        Role userRole = roleRepository.findByName(RolesEnum.USER);
        Role sellerRole = roleRepository.findByName(RolesEnum.SELLER);

        User approvedSeller = sellerRequest.getSeller().setRoles(Set.of(userRole, sellerRole));

        userRepository.save(approvedSeller);

        sellerRequest.setApproved(true);

        sellerRequestRepository.save(sellerRequest);

        logoutUser(approvedSeller.getUsername());
    }

    @Override
    public void disapproveSeller(Long sellerRequestId) {
        //TODO Add custom object not found exception
        SellerRequest sellerRequest = sellerRequestRepository.findById(sellerRequestId).orElseThrow(()-> new ObjectNotFoundException());

        if(!sellerRequest.isApproved()){
            return;
        }

        Role userRole = roleRepository.findByName(RolesEnum.USER);

        User approvedSeller = sellerRequest.getSeller().setRoles(Set.of(userRole));

        userRepository.save(approvedSeller);

        sellerRequest.setApproved(false);

        sellerRequestRepository.save(sellerRequest);

        logoutUser(approvedSeller.getUsername());
    }

    @Override
    public List<SellerApprovalRequest> sellerApprovalRequests() {
        List<SellerRequest> approveRequests = sellerRequestRepository.findAllByApprovedIsFalse();

        List<SellerApprovalRequest> approvals = approveRequests.stream().map(this::mapToApprovalRequest).collect(Collectors.toList());

        return approvals;
    }

    @Override
    public List<SellerApprovalRequest> approvedSellers() {
        List<SellerRequest> approveRequests = sellerRequestRepository.findAllByApprovedIsTrue();

        List<SellerApprovalRequest> approvals = approveRequests.stream().map(this::mapToApprovalRequest).collect(Collectors.toList());

        return approvals;
    }

    private SellerApprovalRequest mapToApprovalRequest(SellerRequest approval){
        SellerApprovalRequest sellerApprovalRequest = modelMapper.map(approval, SellerApprovalRequest.class);

        sellerApprovalRequest.setUserId(approval.getSeller().getId());
        sellerApprovalRequest.setUsername(approval.getSeller().getUsername());

        return sellerApprovalRequest;
    }

    private void logoutUser(String username){
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for(Object p : allPrincipals){
            if(p instanceof InstamarketUser){
                if(((InstamarketUser) p).getUsername().equals(username)){
                    sessionRegistry.getAllSessions(p, false).stream().forEach(s -> {
                        s.expireNow();
                    });
                }
            }
        }
    }
}
