package com.test.util;

import com.test.ui.activities.MyApplication;

/**
 * Created by CuncheW1 on 2017/8/2.
 */

public class SystemUtil {
    public static String getResString(int resId) {
        try {
            return MyApplication.getInstance().getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
