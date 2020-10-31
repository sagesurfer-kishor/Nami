package com.sagesurfer.nami.model;

import com.google.gson.annotations.SerializedName;
import com.sagesurfer.nami.constant.General;

public class UserInfo_ {

    @SerializedName(General.USERNAME)
    private String username;

    @SerializedName("firstname")
    private String first_name;

    @SerializedName("lastname")
    private String last_name;

    @SerializedName("userid")
    private String user_id;


    @SerializedName(General.EMAIL)
    private String email;

    @SerializedName(General.IMAGE)
    private String image;

    @SerializedName(General.PHONE)
    private String phone;

    @SerializedName("login_log_id")
    private String login_log_id;


    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    /*Setter*/

    public String getUserId() {
        return user_id;
    }

    public String getName() {
        return this.first_name + " " + this.last_name;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public String getLastName() {
        return this.last_name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getImage() {
        return this.image;
    }

    public String getPhone() {
        return this.phone;
    }


}
