package com.sagesurfer.nami.modules.Crisis;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sagesurfer.nami.parser.GetJson_;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FriendlistParser_ {

    public static ArrayList<CallFriend> parseCaseload(String response, String jsonName, Context _context, String TAG) {
        ArrayList<CallFriend> caseloadArrayList = new ArrayList<>();
        try {
            if (response == null) {
                CallFriend caseloadItem_ = new CallFriend();
                caseloadItem_.setStatus(12);
                caseloadArrayList.add(caseloadItem_);
                return caseloadArrayList;
            }
            if (Error_.oauth(response, _context) == 13) {
                CallFriend caseloadItem_ = new CallFriend();
                caseloadItem_.setStatus(13);
                caseloadArrayList.add(caseloadItem_);
                return caseloadArrayList;
            }
            if (Error_.noData(response, jsonName, _context) == 2) {
                CallFriend caseloadItem_ = new CallFriend();
                caseloadItem_.setStatus(2);
                caseloadArrayList.add(caseloadItem_);
                return caseloadArrayList;
            }
            JsonArray jsonArray = GetJson_.getArray(response, jsonName);

            if (jsonArray != null) {
//                Gson gson = new Gson();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(UNRELIABLE_INTEGER_FACTORY)
                        .create();
                Type listType = new TypeToken<List<CallFriend>>() {
                }.getType();
                caseloadArrayList = gson.fromJson(GetJson_.getArray(response, jsonName), listType);
            } else {
                CallFriend caseloadItem_ = new CallFriend();
                caseloadItem_.setStatus(11);
                caseloadArrayList.add(caseloadItem_);
                return caseloadArrayList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseloadArrayList;
    }

    public static final TypeAdapter<Number> UNRELIABLE_INTEGER = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            JsonToken jsonToken = in.peek();
            switch (jsonToken) {
                case NUMBER:
                case STRING:
                    String s = in.nextString();
                    try {
                        return Integer.parseInt(s);
                    } catch (NumberFormatException ignored) {
                    }
                    try {
                        return (int) Double.parseDouble(s);
                    } catch (NumberFormatException ignored) {
                    }
                    return null;
                case NULL:
                    in.nextNull();
                    return null;
                case BOOLEAN:
                    in.nextBoolean();
                    return null;
                default:
                    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
            }
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    };
    public static final TypeAdapterFactory UNRELIABLE_INTEGER_FACTORY = TypeAdapters.newFactory(int.class, Integer.class, UNRELIABLE_INTEGER);

}
