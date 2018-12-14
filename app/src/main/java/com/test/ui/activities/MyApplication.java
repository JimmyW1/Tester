package com.test.ui.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by CuncheW1 on 2017/2/27.
 */

public class MyApplication extends Application {
    private final String TAG = "MyApplication";
    private static MyApplication myApplication;
    private List<Activity> activityList;
    private TestEntry currentTestEntry;
    private Handler currentHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "time=" + System.currentTimeMillis());
        myApplication = this;
        currentTestEntry = null;
        currentHandler = null;
        activityList = new ArrayList<Activity>();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public static Context getContext() {
        return myApplication.getApplicationContext();
    }

    public void addActivity(Activity activity) {
        LogUtil.d(TAG, "Add activity=" + activity.getLocalClassName());
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        LogUtil.d(TAG, "Remove activity=" + activity.getLocalClassName());
        activityList.remove(activity);
    }

    public void backToMainActivity() {
        boolean flag = false;

        for (Activity activity : activityList) {
            if (flag) {
                LogUtil.d(TAG, "Activity=" + activity.getLocalClassName() + " Finish flag=" + activity.isFinishing());
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            flag = true;
        }
    }

    public Activity getMainActivity() {
        return activityList.get(0);
    }

    public Activity getTopActivity() {
        return activityList.get(activityList.size() - 1);
    }

    public Handler getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(Handler handler) {
        currentHandler = handler;
    }

    public TestEntry getCurrentTestEntry() {
        return currentTestEntry;
    }

    public void setCurrentTestEntry(TestEntry currentTestEntry) {
        this.currentTestEntry = currentTestEntry;
    }

    public void startActivity(Class className) {
        Intent intent = new Intent(getMainActivity(), className);
        getMainActivity().startActivity(intent);
    }
}
