package com.sagesurfer.nami.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.sagesurfer.nami.DrawerListAdapter;
import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.library.GetFragments;
import com.sagesurfer.nami.logger.Logger;
import com.sagesurfer.nami.model.DrawerMenu_;
import com.sagesurfer.nami.model.HomeMenu_;
import com.sagesurfer.nami.parser.Login_;
import com.sagesurfer.nami.storage.preferences.Preferences;
import com.sagesurfer.nami.tasks.PerformLogoutTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<HomeMenu_> homeMenuList;
    private List<DrawerMenu_> drawerMenuList;

    private DrawerLayout drawerLayout;
    private DrawerListAdapter drawerListAdapter;
    private ExpandableListView expandableDrawerListView;
    private Toolbar toolbar;

    ImageButton btnEditProfile;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar_layout);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                toolbar.setNavigationIcon(R.drawable.vi_drawer_hamburger_icon);
            }
        };
        toggle.setDrawerIndicatorEnabled(false);

        toolbar.setNavigationIcon(R.drawable.vi_drawer_hamburger_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        expandableDrawerListView = (ExpandableListView) navigationView.findViewById(R.id.drawer_list_view);

        setDrawerMenuList();

        Preferences.save(General.HOME_ICON_NUMBER, "0");
        replaceFragment(1, drawerMenuList.get(1).getMenu(), null);
        homeMenuList = Login_.homeMenuParser();

        btnEditProfile.setOnClickListener(this);
    }

    private void replaceFragment(int id, String name, Bundle bundle) {
        Logger.error(TAG, "menu_id: " + id, getApplicationContext());
        if (id == 0) {
            PerformLogoutTask.logout(this);
            return;
        }

        Fragment fragment = GetFragments.get(id, bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.app_bar_main_container, fragment, TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // set all menus with counters in drawer
    private void setDrawerMenuList() {
        if (drawerMenuList == null) {
            drawerMenuList = new ArrayList<>();
        } else if (drawerMenuList.size() > 0) {
            drawerMenuList.clear();
        }
        drawerMenuList = Login_.drawerMenuParser();

        //Adding Logout menu in Navigation Drawer menu list
        HomeMenu_ homeMenu = new HomeMenu_();
        List<HomeMenu_> subMenu = new ArrayList<>();
        DrawerMenu_ drawerMenu = new DrawerMenu_();
        drawerMenu.setId(0);
        drawerMenu.setMenu(getResources().getString(R.string.logout));
        drawerMenu.setSubMenu(subMenu);
        drawerMenuList.add(drawerMenu);

        HashMap<String, List<HomeMenu_>> childList = new HashMap<>();
        for (int i = 0; i < drawerMenuList.size(); i++) {
            childList.put(drawerMenuList.get(i).getMenu(), drawerMenuList.get(i).getSubMenu());
        }

        drawerListAdapter = new DrawerListAdapter(getApplicationContext(), drawerMenuList, childList);
        expandableDrawerListView.setAdapter(drawerListAdapter);

        expandableDrawerListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                if (drawerMenuList.get(groupPosition).getSubMenu().size() <= 0) {
                    view.setSelected(true);
                    drawerLayout.closeDrawer(Gravity.START);
                    parent.setItemChecked(groupPosition, true);
                    Preferences.save(General.HOME_ICON_NUMBER, "0");
                    setTitle(drawerMenuList.get(groupPosition).getMenu());

                    replaceFragment(drawerMenuList.get(groupPosition).getId(), drawerMenuList.get(groupPosition).getMenu(), null);
                } else {
                    if (expandableDrawerListView.isGroupExpanded(groupPosition)) {
                        expandableDrawerListView.collapseGroup(groupPosition);
                        drawerMenuList.get(groupPosition).setSelected(false);
                        parent.setItemChecked(groupPosition, false);
                    } else {
                        expandableDrawerListView.expandGroup(groupPosition);
                        drawerMenuList.get(groupPosition).setSelected(true);
                        parent.setItemChecked(groupPosition, true);
                    }
                }
                return true;
            }
        });

        expandableDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                view.setSelected(true);
                drawerLayout.closeDrawer(Gravity.START);
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                Preferences.save(General.HOME_ICON_NUMBER, "0");
                replaceFragment(drawerMenuList.get(groupPosition).getSubMenu()
                        .get(childPosition).getId(), drawerMenuList.get(groupPosition).getSubMenu()
                        .get(childPosition).getMenu(), null);
                setTitle(drawerMenuList.get(groupPosition).getSubMenu().get(childPosition).getMenu());

                return true;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile:
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation
                        (getApplicationContext(), R.anim.animation_one, R.anim.animation_two).toBundle();
                Intent forgotIntent = new Intent(getApplicationContext(), EditProfileActvity.class);
                startActivity(forgotIntent, bundleAnimation);

                break;
        }
    }
}
