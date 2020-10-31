package com.sagesurfer.nami.model;

import com.google.gson.annotations.SerializedName;
import com.sagesurfer.nami.constant.General;

public class HomeMenu_ {
    @SerializedName(General.ID)
    private int id;

    @SerializedName("menu")
    private String menu;

    private int counter;

    public void setId(int id) {
        this.id = id;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getId() {
        return this.id;
    }

    public String getMenu() {
        return this.menu;
    }


}
