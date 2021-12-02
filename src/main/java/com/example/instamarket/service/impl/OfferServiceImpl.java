package com.example.instamarket.service.impl;

import com.example.instamarket.exception.OfferNotFoundException;
import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.service.OfferQuestionServiceModel;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.model.view.OfferDetailsViewModel;
import com.example.instamarket.model.view.OfferSellerViewModel;
import com.example.instamarket.repository.*;
import com.example.instamarket.repository.specification.OfferSearchSpecification;
import com.example.instamarket.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ShippingRepository shippingRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SubscriberRepository subscriberRepository;
    private final OfferQuestionRepository offerQuestionRepository;
    private final ModelMapper modelMapper;
    private static final String uploadDir = "D://projImages//";
    private static final String offerImagesDir = "offerImages//";
    private static final String optionImagesDir = "optionImages//";
    private static final List<String> validFileFormats = Arrays.asList("jpeg", "jpg", "png", "gif");
    private static final char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$_".toCharArray();

    public OfferServiceImpl(OfferRepository offerRepository, ShippingRepository shippingRepository, CategoryRepository categoryRepository, UserRepository userRepository, SubscriberRepository subscriberRepository, OfferQuestionRepository offerQuestionRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.shippingRepository = shippingRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.subscriberRepository = subscriberRepository;
        this.offerQuestionRepository = offerQuestionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeShippingTypes() {
        if(shippingRepository.count() == 0) {
            Arrays.stream(ShippingTypesEnum.values()).map(shippingEnum -> {
                Shipping shipping = new Shipping();

                shipping.setShipping(shippingEnum).setDisplayName(shippingEnum.getDisplayName());

                return shipping;
            }).forEach(shippingRepository::save);
        }
    }

    @Override
    public void initializeOfferCategories() {
        if(categoryRepository.count() == 0){
            Arrays.stream(CategoriesEnum.values()).map(categoryEnum -> {
                Category category = new Category();

                category.setCategory(categoryEnum).setDisplayName(categoryEnum.getDisplayName());

                return category;
            }).forEach(categoryRepository::save);
        }
    }

    @Override
    @Transactional
    public Long addOffer(AddOfferServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Category category = categoryRepository.findCategoryByCategory(model.getOfferCategory()).get();

        Shipping shipping = shippingRepository.findShippingByShipping(model.getShippingType()).get();

        Offer offer = new Offer();

        offer.setTitle(model.getTitle());
        offer.setDescription(model.getDescription());
        offer.setPrice(model.getPrice());
        offer.setOfferCategory(category);
        offer.setShippingType(shipping);
        offer.setSeller(user);

        Set<OfferImage> offerImages = new HashSet<>();

        Set<OfferProperty> offerProperties = new HashSet<>();

        List<OfferOption> offerOptions = new LinkedList<>();

        model.getOfferImages().stream().filter(img -> {
           return isValidFileFormat(img.getOriginalFilename());
        }).map(img -> {
            String fileName = "";

            try {
                String fileExt = FilenameUtils.getExtension(img.getOriginalFilename());

                byte[] bytes = img.getBytes();

                while(true){
                    fileName = generateFileName(30, fileExt);

                    Path path = Paths.get(uploadDir + offerImagesDir + fileName);

                    if(!Files.exists(path)){
                        break;
                    }
                }

                Path path = Paths.get(uploadDir + offerImagesDir + fileName);

                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fileName;
        }).forEach(imgFileName -> {
            OfferImage offerImage = new OfferImage();

            offerImage.setOffer(offer);
            offerImage.setImageUrl(imgFileName);

            offerImages.add(offerImage);
        });

        model.getProperties().stream().filter(property -> {
            return (property.getName() != null && property.getValue() != null);
        }).map(property -> {
           OfferProperty offerProperty = new OfferProperty();

           offerProperty.setPropertyName(property.getName());
           offerProperty.setPropertyValue(property.getValue());
           offerProperty.setOffer(offer);

           return offerProperty;
        }).forEach(offerProperty -> {
            offerProperties.add(offerProperty);
        });

        model.getOptions().stream().filter(option -> {
            return (option.getValue() != null && !option.getImage().isEmpty() && isValidFileFormat(option.getImage().getOriginalFilename()));
        }).map(option -> {
           OfferOption offerOption = new OfferOption();

            MultipartFile img = option.getImage();

            String fileName = "";

            //Option image upload code
            try {
                String fileExt = FilenameUtils.getExtension(img.getOriginalFilename());

                byte[] bytes = img.getBytes();

                while(true){
                    fileName = generateFileName(30, fileExt);

                    Path path = Paths.get(uploadDir + optionImagesDir + fileName);

                    if(!Files.exists(path)){
                        break;
                    }
                }

                Path path = Paths.get(uploadDir + optionImagesDir + fileName);

                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            offerOption.setOptionName(fileName);
            offerOption.setOptionValue(option.getValue());
            offerOption.setOffer(offer);

           return offerOption;
        }).forEach(offerOption -> {
            offerOptions.add(offerOption);
        });

        offer.setImages(offerImages);
        offer.setOfferOptions(offerOptions);
        offer.setOfferProperties(offerProperties);

        Offer newOffer = offerRepository.save(offer);

        return newOffer.getId();
    }

    @Override
    @Transactional
    public OfferDetailsViewModel getOffer(Long offerId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Offer offer = offerRepository.findById(offerId).orElseThrow(()-> new OfferNotFoundException());

        OfferDetailsViewModel offerModel = modelMapper.map(offer, OfferDetailsViewModel.class);

        Set<String> offerImagesUrl = new HashSet<>();

        Map<String, String> offerOptions = new LinkedHashMap<>();

        Map<String, String> offerProperties = new HashMap<>();

        offer.getImages().stream().forEach(img -> {
            offerImagesUrl.add(img.getImageUrl());
        });

        offer.getOfferOptions().stream().forEach(option -> {
            offerOptions.put(option.getOptionName(), option.getOptionValue());
        });

        offer.getOfferProperties().stream().forEach(property -> {
            offerProperties.put(property.getPropertyName(), property.getPropertyValue());
        });

        offerModel.setOfferImages(offerImagesUrl);
        offerModel.setOptions(offerOptions);
        offerModel.setProperties(offerProperties);
        offerModel.setOfferCategory(offer.getOfferCategory().getDisplayName());
        offerModel.setShippingType(offer.getShippingType().getDisplayName());
        offerModel.setOwner(user.getId() == offer.getSeller().getId());

        return offerModel;
    }

    @Override
    public Page<OfferDTO> getOffers(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        return offerRepository.findAll(pageable).map(this::asOffer);
    }

    @Override
    public Page<OfferDTO> searchOffers(int pageNo, int pageSize, String sortBy, SearchServiceModel model, String username) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        return offerRepository.findAll(new OfferSearchSpecification(model, user), pageable).map(this::asOffer);
    }

    @Override
    public OfferSellerViewModel getOfferSeller(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(()-> new OfferNotFoundException());

        User seller = offer.getSeller();

        OfferSellerViewModel model = modelMapper.map(seller, OfferSellerViewModel.class);

        model.setProfilePictureUrl(seller.getProfilePicture().getUrl());

        model.setOfferCount(offerRepository.countOffersBySellerAndDeleted(seller, false));

        model.setSubscriberCount(subscriberRepository.countSubscribers(seller));

        return model;
    }

    @Override
    public void saveOfferQuestion(OfferQuestionServiceModel model, String username) {
        User inquiring = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Offer offer = offerRepository.findById(model.getOfferId()).orElseThrow(()-> new OfferNotFoundException());

        OfferQuestion offerQuestion = new OfferQuestion();

        offerQuestion.setQuestion(model.getQuestion()).setInquiring(inquiring).setOffer(offer);

        offerQuestionRepository.save(offerQuestion);
    }

    private boolean isValidFileFormat(String filename){
        String fileExt = FilenameUtils.getExtension(filename);

        if(validFileFormats.contains(fileExt)){
            return true;
        }

        return false;
    }

    private String generateFileName(int length, String ext){
        String uploadFilename = "";

        int upperBound = characters.length;

        Random random = new Random();

        for(int i = 0; i < length; i++){
            char c = characters[random.nextInt(upperBound)];

            uploadFilename = uploadFilename.concat(c + "");
        }

        return String.format("%s.%s", uploadFilename, ext);
    }

    private OfferDTO asOffer(Offer offer){
        OfferDTO offerDTO = modelMapper.map(offer, OfferDTO.class);

        Set<String> imageUrls = offer.getImages().stream().map(img -> {
            return img.getImageUrl();
        }).collect(Collectors.toSet());

        offerDTO.setImages(imageUrls);

        offerDTO.setOfferUrl(String.format("/offers/%d/details", offer.getId()));

        return offerDTO;
    }
}
