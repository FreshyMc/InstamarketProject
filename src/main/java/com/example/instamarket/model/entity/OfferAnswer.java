package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_question_answers")
public class OfferAnswer extends BaseEntity {
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String answerText;
    private LocalDateTime addedAt;

    public OfferAnswer() {
    }

    public String getAnswerText() {
        return answerText;
    }

    public OfferAnswer setAnswerText(String answerText) {
        this.answerText = answerText;
        return this;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public OfferAnswer setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    @PrePersist
    public void populateAddedAt(){
        this.setAddedAt(LocalDateTime.now());
    }
}
