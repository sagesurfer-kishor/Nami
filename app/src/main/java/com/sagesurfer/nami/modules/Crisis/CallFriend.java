package com.sagesurfer.nami.modules.Crisis;

import com.google.gson.annotations.SerializedName;
import com.sagesurfer.nami.constant.General;

import java.io.Serializable;

public class CallFriend implements Serializable {

    @SerializedName(General.ID)
    private String id;
    @SerializedName(General.NAME)
    private String name;
    @SerializedName(General.PHONE)
    private String phone;
    @SerializedName(General.STATUS)
    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
