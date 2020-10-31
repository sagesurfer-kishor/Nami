package com.sagesurfer.nami.library;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.sagesurfer.nami.modules.Community.CommunityFragment;
import com.sagesurfer.nami.modules.Crisis.CrisisFragment;
import com.sagesurfer.nami.modules.Home.HomeFragment;
import com.sagesurfer.nami.modules.ImFeeling.ImFellingFragment;
import com.sagesurfer.nami.modules.Information.InformationFragment;
import com.sagesurfer.nami.modules.PositiveQuotes.PositiveQuotesFragment;
import com.sagesurfer.nami.modules.Resources.ResourcesFragment;
import com.sagesurfer.nami.modules.Uplift.UpliftFragment;
import com.sagesurfer.nami.modules.WellnessPlan.WellnessplanFragment;

/*
 * This class return fragment respective to menu id
 */

public class GetFragments {

    public static Fragment get(int id, Bundle bundle) {
        boolean isDailyDosingReport = false;
        switch (id) {
            case 1:
                return new HomeFragment();
            case 2:
                return new WellnessplanFragment();
            case 3:
                return new ResourcesFragment();
            case 4:
                return new PositiveQuotesFragment();
            case 5:
                return new UpliftFragment();
            case 6:
                return new ImFellingFragment();
            case 7:
                return new CommunityFragment();
            case 8:
                return new InformationFragment();
            case 9:
                return new ResourcesFragment();
            case 10:
                return new ResourcesFragment();
            case 11:
                return new ResourcesFragment();
            case 12:
                return new ResourcesFragment();
            case 13:
                return new CrisisFragment();
            case 14:
                return new ImFellingFragment();
            case 15:
                return new CommunityFragment();
            case 16:
                return new UpliftFragment();
            case 17:
                return new PositiveQuotesFragment();
            case 18:
                return new ToDoListFragment();

            default:
                return null;
        }
    }
}
