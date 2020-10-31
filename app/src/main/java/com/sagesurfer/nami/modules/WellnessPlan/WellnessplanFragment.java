package com.sagesurfer.nami.modules.WellnessPlan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sagesurfer.nami.Adapter.TabPagerAdapter;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.Calendar;
import java.util.Locale;

public class WellnessplanFragment extends Fragment {
    private static final String TAG = WellnessplanFragment.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wellnessplan, null);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.pager);

        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
