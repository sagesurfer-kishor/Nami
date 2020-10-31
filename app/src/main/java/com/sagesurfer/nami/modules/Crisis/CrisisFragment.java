package com.sagesurfer.nami.modules.Crisis;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sagesurfer.nami.Activity.EditFriendlistActvity;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.network.NetworkCall_;
import com.sagesurfer.nami.network.Urls_;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;


public class CrisisFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CrisisFragment.class.getSimpleName();
    private Activity activity;

    ImageView btn_edit_friend_list;

    RecyclerView callFriendList;
    public CallFriendListAdapter callFriendListAdapter;
    public ArrayList<CallFriend> callFriendArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crisis, null);

        btn_edit_friend_list = view.findViewById(R.id.btn_edit_friend_list);
        btn_edit_friend_list.setOnClickListener(this);

        callFriendList = view.findViewById(R.id.call_friend_list);

        getFriendList("friend_list");

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_friend_list:
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getActivity(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getActivity(), EditFriendlistActvity.class);
                startActivity(forgotIntent, bundleAnimation);

                break;
        }
    }

    private void getFriendList(String action) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, action);
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));
        String url = Preferences.get(General.DOMAIN) + Urls_.Mobile_CRISIS;

        RequestBody requestBody = NetworkCall_.make(requestMap, url, TAG, activity, activity);
        if (requestBody != null) {
            try {
                String response = NetworkCall_.post(url, requestBody, TAG, activity, activity);
                Log.e("CaseloadResponse", response);
                if (response != null) {
                    callFriendArrayList = FriendlistParser_.parseCaseload(response, action, activity.getApplicationContext(), TAG);
                    Log.e("uuuu", callFriendArrayList.toString());
                    if (callFriendArrayList.size() > 0) {
                        if (callFriendArrayList.get(0).getStatus() == 1) {
                            showError(false, 1);


                            CallFriendListNewAdapter caseloadListAdapter = new CallFriendListNewAdapter(activity, callFriendArrayList);
                            callFriendList.setAdapter(caseloadListAdapter);


                        } else {
                            showError(true, callFriendArrayList.get(0).getStatus());
                        }
                    } else {
                        showError(true, callFriendArrayList.get(0).getStatus());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void showError(boolean isError, int status) {
        if (isError) {

        } else {

        }
    }
}
