package com.example.instamarket.model.service;

public class ChangePasswordServiceModel {
    private String oldPassword;
    private String newPassword;

    public ChangePasswordServiceModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordServiceModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordServiceModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
