package com.sagesurfer.nami.icons;




/*
 * This class returns home menu icon based on menu id
 */

import com.sagesurfer.nami.R;

public class GetHomeMenuIcon {
    public static int get(int id) {
        switch (id) {
            case 3:
                return R.mipmap.ic_launcher;

            default:
                return R.drawable.primary_circle;
        }
    }
}
