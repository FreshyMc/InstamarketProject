package com.example.instamarket.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordBindingModel {
    @NotNull
    @Size(min = 8)
    private String oldPassword;
    @NotNull
    @Size(min = 8)
    private String newPassword;
    @NotNull
    @Size(min = 8)
    private String newPasswordConfirm;

    public ChangePasswordBindingModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordBindingModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public ChangePasswordBindingModel setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
        return this;
    }

    public boolean passwordMatch(){
        return this.newPassword.equals(this.newPasswordConfirm);
    }
}
