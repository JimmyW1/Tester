package com.test.logic.notifier;

import com.test.logic.notifier.interfaces.NotifierListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangcunche on 2017/8/6.
 */

public class NotifierManager {
    public final String TAG = "NotifierManager";
    private static NotifierManager instance;
    private Map<String, Notifier> notifierMap;

    public NotifierManager() {
        notifierMap = new HashMap<>();
    }

    public static synchronized NotifierManager getInstance() {
        if (instance == null) {
            instance = new NotifierManager();
        }

        return instance;
    }

    public void addNotifier(String notifierName, Notifier notifier) {
        notifierMap.put(notifierName, notifier);
    }

    public void registerNotifierEventListener(String notifierName, NotifierListener listener) {
        if (notifierMap.containsKey(notifierName)) {
            notifierMap.get(notifierName).setNotifierListener(listener);
        }
    }
}
