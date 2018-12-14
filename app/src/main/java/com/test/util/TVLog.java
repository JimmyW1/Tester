package com.test.util;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;

import com.test.ui.activities.MyApplication;

/**
 * Created by CuncheW1 on 2017/3/7.
 */

public class TVLog {
    Handler handler;
    private TextView tvView;
    StringBuffer tvInfo;

    public TVLog() {
        tvView = null;
        handler = null;
        tvInfo = new StringBuffer();
    }

    public TVLog(TextView view) {
        tvView = view;
        tvInfo = new StringBuffer();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setTvView(TextView view) {
        tvView = view;
    }

    public TextView getTvView () {
        return tvView;
    }

    public void clearTVInfo() {
        if (tvView != null)
            tvView.setText("");

        tvInfo.delete(0, tvInfo.length());
    }

    public void showInfo(String info) {
        if (info != null && info.length() > 0) {
            tvInfo.append(info);
            if (tvView != null) {
                Activity currentActivity = MyApplication.getInstance().getTopActivity();
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvView.setText(tvInfo.toString());
                    }
                });
            }
        }
    }

    public void showInfoInNewLine(String info) {
        if (info != null && info.length() > 0) {
            tvInfo.append("\n");
            showInfo(info.toString());
        }
    }

    public void clearBeforeShowInfo(String info) {
        clearTVInfo();
        showInfo(info);
    }
}
