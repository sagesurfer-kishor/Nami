package com.sagesurfer.nami.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.sagesurfer.nami.modules.WellnessPlan.MonthlyFragment;
import com.sagesurfer.nami.modules.WellnessPlan.TodaysFragment;
import com.sagesurfer.nami.modules.WellnessPlan.YearlyFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = TabPagerAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 3;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new MonthlyFragment();
            case 1:
                return new TodaysFragment();
            case 2:
                return new YearlyFragment();

        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Monthly";
            case 1:
                return "Todays";
            case 2:
                return "Yearly";

        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
