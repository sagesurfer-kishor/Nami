package com.sagesurfer.nami.modules.Crisis;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.parser.GetJson_;
import com.sagesurfer.nami.snack.ShowToast;

public class Error_ {

    public static int oauth(String response, Context _context) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
        if (jsonObject.has(General.STATUS)) {
            int result = jsonObject.get(General.STATUS).getAsInt();
            if (result == 13) {
                ShowToast.authenticationFailed(_context);
            }
            return result;
        }
        return 0;
    }

    public static int noData(String response, String jsonName, Context _context) {
        JsonArray jsonArray = GetJson_.getArray(response, jsonName);
        if (jsonArray != null) {
            JsonObject object = jsonArray.get(0).getAsJsonObject();
            if (object.has(General.STATUS)) {
                int result = object.get(General.STATUS).getAsInt();
                if (result == 2) {
                    return result;
                }
            }
        }
        return 0;
    }
}
