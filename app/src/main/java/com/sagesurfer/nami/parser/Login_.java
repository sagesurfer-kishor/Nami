package com.sagesurfer.nami.parser;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.gson.reflect.TypeToken;
import com.sagesurfer.nami.BuildConfig;
import com.sagesurfer.nami.model.DrawerMenu_;
import com.sagesurfer.nami.model.HomeMenu_;
import com.sagesurfer.nami.model.UserInfo_;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.lang.reflect.Type;
import java.util.List;


public class Login_ {
    private static final String TAG = Login_.class.getSimpleName();

    public static UserInfo_ userInfoParser(String response, String jsonName, Context _context) {
        UserInfo_ userInfo = new UserInfo_();
        if (response != null) {
            Gson gson = new Gson();
            userInfo = gson.fromJson(GetJson_.getObject(response, jsonName).toString(), UserInfo_.class);

            Preferences.save("drawer", GetJson_.getArray(response, "drawer").toString());
            Preferences.save("home", GetJson_.getArray(response, "home").toString());

        }
        return userInfo;
    }


    public static List<HomeMenu_> homeMenuParser() {
        Gson gson = new Gson();
        Type homeType = new TypeToken<List<HomeMenu_>>() {
        }.getType();
        List<HomeMenu_> homeMenuList = gson.fromJson(Preferences.get("home"), homeType);
        if (homeMenuList.size() < 6) {
            for (int i = homeMenuList.size(); i < 6; i++) {
                HomeMenu_ homeMenu_ = new HomeMenu_();
                homeMenu_.setId(0);
                homeMenuList.add(homeMenu_);
            }
        }
        return homeMenuList;
    }

    public static List<DrawerMenu_> drawerMenuParser() {
        Gson gson = new Gson();
        Type homeType = new TypeToken<List<DrawerMenu_>>() {
        }.getType();
        return gson.fromJson(Preferences.get("drawer"), homeType);
    }


}
