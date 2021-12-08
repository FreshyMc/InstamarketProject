package com.example.instamarket.model.view;

public class FeedbackViewModel {
    private String feedback;
    private Integer rating;
    private String profilePicture;
    private String firstName;
    private String lastName;

    public FeedbackViewModel() {
    }

    public String getFeedback() {
        return feedback;
    }

    public FeedbackViewModel setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public FeedbackViewModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public FeedbackViewModel setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public FeedbackViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public FeedbackViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
