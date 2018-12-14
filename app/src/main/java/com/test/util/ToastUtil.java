package com.test.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.test.ui.activities.MyApplication;

/**
 * Created by CuncheW1 on 2017/3/16.
 */

public class ToastUtil {
    public static void showLong(final String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void showShort(final String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(final String msg, final int duration) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), msg, duration).show();
            }
        });
    }
}
