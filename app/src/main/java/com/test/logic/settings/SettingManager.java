package com.test.logic.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.logic.notifier.Notifier;
import com.test.logic.notifier.interfaces.NotifierListener;
import com.test.ui.activities.MyApplication;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/8/8.
 */

public class SettingManager {
    private final String TAG = "SettingManager";
    private SharedPreferences sharedPreferences;
    private static SettingManager instance;
    private int visableLevel;
    private Notifier settingsNotifier; // 用于通知watchers某个设置改变了

    private final String KEY_VISABLELEVEL = "visable_level";

    public static int EVENT_VISABLELEVEL_CHANGED = 1001;  // visableLevel的设置发生改变

    public SettingManager() {
        sharedPreferences = MyApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        visableLevel = sharedPreferences.getInt(KEY_VISABLELEVEL, 10);
        LogUtil.d(TAG, "visableLevel=" + visableLevel);
        settingsNotifier = new Notifier("SettingNotifier");
    }

    public static synchronized SettingManager getInstance() {
        if (instance == null) {
            instance = new SettingManager();
        }

        return instance;
    }

    public int getVisableLevel() {
        return visableLevel;
    }

    public void setVisableLevel(int visableLevel) {
        if (this.visableLevel != visableLevel) {
            this.visableLevel = visableLevel;
            sharedPreferences.edit().putInt(KEY_VISABLELEVEL, visableLevel).commit();
            settingsNotifier.notifyAll(EVENT_VISABLELEVEL_CHANGED, visableLevel);
        }
    }

    public void setSettingsNotifierListener(NotifierListener listener) {
        if (listener != null) {
            settingsNotifier.setNotifierListener(listener);
        }
    }
}
