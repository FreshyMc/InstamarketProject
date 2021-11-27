package com.example.instamarket.repository.specification;

import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.entity.WishList;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import com.example.instamarket.model.service.SearchServiceModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class OfferSearchSpecification implements Specification<Offer> {
    private static final String TITLE = "title";
    private static final String CATEGORY = "offerCategory";
    private static final String SHIPPING = "shippingType";
    private static final String PRICE = "price";

    private final SearchServiceModel searchServiceModel;
    private final User user;

    public OfferSearchSpecification(SearchServiceModel searchServiceModel, User user) {
        this.searchServiceModel = searchServiceModel;
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Offer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        p.getExpressions().add(criteriaBuilder.like(root.get(TITLE), "%" + searchServiceModel.getSearch() + "%"));

        if(searchServiceModel.getCategory() != null){
            p.getExpressions()
                    .add(criteriaBuilder.and(criteriaBuilder.equal(root.join(CATEGORY).get("category"),
                            searchServiceModel.getCategory())));
        }

        if(searchServiceModel.getMinPrice().intValue() > 0){
            p.getExpressions().
                    add(criteriaBuilder.
                            and(criteriaBuilder.
                                    greaterThanOrEqualTo(root.get(PRICE), searchServiceModel.getMinPrice())));
        }

        if(searchServiceModel.getMaxPrice().intValue() > 0){
            p.getExpressions().
                    add(criteriaBuilder.
                            and(criteriaBuilder.
                                    lessThanOrEqualTo(root.get(PRICE), searchServiceModel.getMaxPrice())));
        }

        if(Boolean.TRUE.equals(searchServiceModel.getFreeShipping())){
            p.getExpressions()
                    .add(criteriaBuilder.and(criteriaBuilder.equal(root.join(SHIPPING).get("shipping"),
                            ShippingTypesEnum.FREE)));
        }

        if(Boolean.TRUE.equals(searchServiceModel.getFavouriteOffer())){
            Subquery<WishList> subQuery = createSubquery(root, query, criteriaBuilder);

            p.getExpressions().add(criteriaBuilder.exists(subQuery));
        }

        return p;
    }

    private Subquery<WishList> createSubquery(Root<Offer> root, CriteriaQuery<?> query, CriteriaBuilder cb){
        Subquery<WishList> subquery = query.subquery(WishList.class);
        Root<WishList> subqueryRoot = subquery.from(WishList.class);

        subquery.select(subqueryRoot);

        Predicate offer = cb.and(cb.equal(root.get("id"), subqueryRoot.get("offer")));

        Predicate belongsToUser = cb.and(cb.equal(subqueryRoot.get("user"), user.getId()));

        Predicate wishlisted = cb.and(cb.equal(subqueryRoot.get("removed"), false));

        return subquery.where(offer, wishlisted, belongsToUser);
    }
}
