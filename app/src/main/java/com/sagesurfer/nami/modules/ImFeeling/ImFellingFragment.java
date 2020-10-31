package com.sagesurfer.nami.modules.ImFeeling;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.Actions_;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.interfaces.MainActivityInterface;
import com.sagesurfer.nami.storage.preferences.Preferences;


public class ImFellingFragment extends Fragment implements  BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationViewLeft, bottomNavigationViewRight;
    private Fragment fragment;
    private Activity activity;
    private MainActivityInterface mainActivityInterface;

    private ColorStateList colorStateListText, colorStateListIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        View view = inflater.inflate(R.layout.fragment_imfelling, null);


        setBottomNavigationTextColor();
        setBottomNavigationIconColor();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        fragment = new JournalFragment();
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(Actions_.SOS);
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        oldFragment = fragmentManager.findFragmentByTag(Actions_.SOS);
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.replace(R.id.mood_container, fragment, Actions_.SOS);
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_journal:
                Preferences.save(General.SELECTED_MOOD_FRAGMENT, activity.getResources().getString(R.string.journal));
                mainActivityInterface.setMoodToolbar(51);
                fragment = new JournalFragment();
                break;
            case R.id.menu_statistics:
                Preferences.save(General.SELECTED_MOOD_FRAGMENT, activity.getResources().getString(R.string.statistics));
                mainActivityInterface.setMoodToolbar(51);
                fragment = new StatisticsFragment();
                break;
            case R.id.menu_mood:
                Preferences.save(General.SELECTED_MOOD_FRAGMENT, activity.getResources().getString(R.string.mood));
                mainActivityInterface.setMoodToolbar(51);
                fragment = new MoodFragment();
                break;
            case R.id.menu_calender:
                Preferences.save(General.SELECTED_MOOD_FRAGMENT, activity.getResources().getString(R.string.calender));
                mainActivityInterface.setMoodToolbar(51);
                fragment = new CalenderFrgament();
                break;
            default:
                return false;
        }
        updateNavigationBarState(item.getItemId());
        replaceFragment(fragment);
        return true;
    }

    private void updateNavigationBarState(int actionId) {
        for (int i = 0; i < bottomNavigationViewLeft.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationViewLeft.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == actionId;
            menuItem.setChecked(isChecked);
        }
        for (int i = 0; i < bottomNavigationViewRight.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationViewRight.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == actionId;
            menuItem.setChecked(isChecked);
        }
    }



    public void setBottomNavigationTextColor() {
        int[][] state = new int[][]{
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{-android.R.attr.state_checked}
        };
        int[] color = new int[]{
                getResources().getColor(R.color.colorPrimary), //colorPrimary
                getResources().getColor(R.color.colorPrimary) //sos_grey
        };
        colorStateListText = new ColorStateList(state, color);
    }

    public void setBottomNavigationIconColor() {
        int[][] state2 = new int[][]{
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{-android.R.attr.state_checked}
        };
        int[] color2 = new int[]{
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary)
        };
        colorStateListIcon = new ColorStateList(state2, color2);
    }

}
