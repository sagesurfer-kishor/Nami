package com.sagesurfer.nami.icons;



/*
 * This class returns drawer menu icon based on menu id
 */

import com.sagesurfer.nami.R;

public class GetDrawerIcon {
    public static int get(int id) {
        switch (id) {
            case 0:
                return R.drawable.logout_sidebr_blk;
            case 1:
                return R.drawable.home_sidebr_blk;
            case 2:
                return R.drawable.daily_well_sidebr_blk;
            case 3:
                return R.drawable.resources_sidebr_blk;
            case 4:
                return R.drawable.positive_quotes_sidebr_blk;
            case 5:
                return R.drawable.uplift_sidebr_blk;
            case 6:
                return R.drawable.im_feeling_sidebr_blk;
            case 7:
                return R.drawable.community_sidebr_blk;
            case 8:
                return R.drawable.info_sidebr_blk;

            default:
                return R.drawable.primary_circle;
        }
    }
}
