package com.sagesurfer.nami.modules.Home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sagesurfer.nami.R;
import com.sagesurfer.nami.constant.General;
import com.sagesurfer.nami.library.ChangeCase;
import com.sagesurfer.nami.library.GetFragments;
import com.sagesurfer.nami.model.DrawerMenu_;
import com.sagesurfer.nami.model.HomeMenu_;
import com.sagesurfer.nami.parser.Login_;
import com.sagesurfer.nami.storage.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getSimpleName();

    TextView txtHotlineNumber;

    private List<HomeMenu_> homeMenuList;
    private List<DrawerMenu_> drawerMenuList;


    private int color;

    private RecyclerView positiveQuoteList;


    LinearLayout crisis_home_linear_layout, imfeeling_home_linear_layout, community_home_linear_layout, uplift_home_linear_layout, positive_home_linear_layout, todolist_home_linear_layout;
    Button btncrisis_home, btnimfeeling_home, btncommunnity_home, btnuplift_home, btnpositive_home, btntodolist_home;

    AppCompatImageView imgcrisis_home, imgimfeeling_home, imguplift_home, imgpositive_home, imgtodolist_home, imgcommunnity_home;
    TextView txtcrisis_home, txtimfeeling_home, txtuplift_home, txtpositive_home, txttodolist_home, txtcommunnity_home;

    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);

        txtHotlineNumber = view.findViewById(R.id.txtHotlinenumber_home);
        String s = Preferences.get(General.HOTLINE);
        txtHotlineNumber.setText(s);

        positiveQuoteList = (RecyclerView) view.findViewById(R.id.quote_list);

        // linear layout for menus
        crisis_home_linear_layout = view.findViewById(R.id.crisis_home_linear_layout);
        imfeeling_home_linear_layout = view.findViewById(R.id.imfeeling_home_linear_layout);
        community_home_linear_layout = view.findViewById(R.id.community_home_linear_layout);
        uplift_home_linear_layout = view.findViewById(R.id.uplift_home_linear_layout);
        positive_home_linear_layout = view.findViewById(R.id.positive_home_linear_layout);
        todolist_home_linear_layout = view.findViewById(R.id.todolist_home_linear_layout);

        // Buttons for home menus
        btncrisis_home = view.findViewById(R.id.btncrisis_home);
        btnimfeeling_home = view.findViewById(R.id.btnimfeeling_home);
        btncommunnity_home = view.findViewById(R.id.btncommunnity_home);
        btnuplift_home = view.findViewById(R.id.btnuplift_home);
        btnpositive_home = view.findViewById(R.id.btnpositive_home);
        btntodolist_home = view.findViewById(R.id.btntodolist_home);

        crisis_home_linear_layout.setOnClickListener(this);
        imfeeling_home_linear_layout.setOnClickListener(this);
        community_home_linear_layout.setOnClickListener(this);
        uplift_home_linear_layout.setOnClickListener(this);
        positive_home_linear_layout.setOnClickListener(this);
        todolist_home_linear_layout.setOnClickListener(this);

        //AppcompImageView for home menus
        imgcrisis_home = view.findViewById(R.id.imgcrisis_home);
        imgimfeeling_home = view.findViewById(R.id.imgimfeeling_home);
        imguplift_home = view.findViewById(R.id.imguplift_home);
        imgpositive_home = view.findViewById(R.id.imgpositive_home);
        imgtodolist_home = view.findViewById(R.id.imgtodolist_home);
        imgcommunnity_home = view.findViewById(R.id.imgcommunity_home);

        // textview for home menus
        txtcrisis_home = view.findViewById(R.id.txtcrisis_home);
        txtimfeeling_home = view.findViewById(R.id.txtimfeeling_home);
        txtuplift_home = view.findViewById(R.id.txtuplift_home);
        txtpositive_home = view.findViewById(R.id.txtpositive_home);
        txttodolist_home = view.findViewById(R.id.txttodolist_home);
        txtcommunnity_home = view.findViewById(R.id.txtcommunity_home);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        homeMenuList = Login_.homeMenuParser();
        drawerMenuList = Login_.drawerMenuParser();

        AppCompatImageView[] imageViewArray = {imgcrisis_home, imgimfeeling_home, imgpositive_home, imgtodolist_home, imguplift_home};
        TextView[] textViews = {txtcrisis_home, txtimfeeling_home, txtpositive_home, txttodolist_home, txtuplift_home};
        Button[] buttonViews = {btncrisis_home, btnimfeeling_home, btnuplift_home, btnpositive_home, btntodolist_home, btncommunnity_home};

        for (int i = 0; i < homeMenuList.size(); i++) {
            if (homeMenuList.get(i).getId() != 0) {
                color = Color.parseColor("#ffffff");
                buttonViews[i].setVisibility(View.VISIBLE);
                // imageViewArray[i].setImageResource(GetHomeMenuIcon.get(homeMenuList.get(i).getId()));

            }
            String sentence = ChangeCase.toTitleCase(homeMenuList.get(i).getMenu());
            if (homeMenuList.get(i).getId() == 32) { //Assignment instead of Admin Approval
                if (Preferences.get(General.DOMAIN_CODE).equalsIgnoreCase("sage013") || Preferences.get(General.DOMAIN_CODE).equalsIgnoreCase("sage015")) {
                    //sentence = activity.getResources().getString(R.string.assignment);
                }
            }

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.crisis_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(0).getId(), homeMenuList.get(0).getMenu(), null);
                break;
            case R.id.imfeeling_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(1).getId(), homeMenuList.get(0).getMenu(), null);
                break;
            case R.id.community_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(2).getId(), homeMenuList.get(0).getMenu(), null);
                break;
            case R.id.uplift_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(3).getId(), homeMenuList.get(0).getMenu(), null);
                break;
            case R.id.positive_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(4).getId(), homeMenuList.get(0).getMenu(), null);
                break;
            case R.id.todolist_home_linear_layout:
                //For Toolbar color changing depending on background color of home icons
                Preferences.save(General.HOME_ICON_NUMBER, "1");
                replaceFragment(homeMenuList.get(5).getId(), homeMenuList.get(0).getMenu(), null);
                break;
        }
    }


    private void replaceFragment(int id, String name, Bundle bundle) {

        Fragment fragment = GetFragments.get(id, bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.app_bar_main_container, fragment, TAG);
        ft.commit();

    }

   /* private void qouteDetailsLayout() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        positiveQuoteList.setLayoutManager(mLinearLayoutManager);

        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;

            @Override
            public void run() {
                if (count < positiveQuoteAdapter.getItemCount()) {
                    if (count == positiveQuoteAdapter.getItemCount() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    positiveQuoteList.smoothScrollToPosition(count);
                    handler.postDelayed(this, speed);
                }
            }
        };
        handler.postDelayed(runnable, speed);
        positiveQuoteAdapter = new PositiveQuoteAdapter(activity, this);
        positiveQuoteList.setAdapter(positiveQuoteAdapter);
    }*/

}
