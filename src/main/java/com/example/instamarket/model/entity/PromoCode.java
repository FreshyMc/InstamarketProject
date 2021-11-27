package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
public class PromoCode extends BaseEntity{
    @ManyToOne
    private User seller;
    @Column(nullable = false, length = 50)
    private String code;
    @Column(nullable = false)
    private BigDecimal promotionRate;
    @Column(nullable = false)
    private LocalDateTime expiration;

    public PromoCode() {
    }

    public User getSeller() {
        return seller;
    }

    public PromoCode setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PromoCode setCode(String code) {
        this.code = code;
        return this;
    }

    public BigDecimal getPromotionRate() {
        return promotionRate;
    }

    public PromoCode setPromotionRate(BigDecimal promotionRate) {
        this.promotionRate = promotionRate;
        return this;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public PromoCode setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
        return this;
    }
}
