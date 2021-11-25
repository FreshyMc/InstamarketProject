package com.example.instamarket.repository.specification;

import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.WishList;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import com.example.instamarket.model.service.SearchServiceModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class OfferSearchSpecification implements Specification<Offer> {
    private static final String CATEGORY = "offerCategory";
    private static final String SHIPPING = "shippingType";
    private static final String PRICE = "price";

    private final SearchServiceModel searchServiceModel;

    public OfferSearchSpecification(SearchServiceModel searchServiceModel) {
        this.searchServiceModel = searchServiceModel;
    }

    @Override
    public Predicate toPredicate(Root<Offer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        if(searchServiceModel.getCategory() != null){
            p.getExpressions()
                    .add(criteriaBuilder.equal(root.join(CATEGORY).get("category"),
                            searchServiceModel.getCategory()));
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

        Predicate conditions = cb.and(cb.equal(root.get("id"), subqueryRoot.get("offer")));

        return subquery.where(conditions);
    }
}
