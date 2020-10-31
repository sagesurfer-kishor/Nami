package com.sagesurfer.nami.modules.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.storage.preferences.Preferences;

public class CommunityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);



        return view;

    }
}
