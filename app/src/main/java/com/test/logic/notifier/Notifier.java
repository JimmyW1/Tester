package com.test.logic.notifier;

import com.test.logic.notifier.interfaces.NotifierListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by wangcunche on 2017/8/6.
 */

public class Notifier {
    private String notifierName;
    private ArrayList<NotifierListener> listeners;

    public Notifier(String notifierName) {
        this.notifierName = notifierName;
        listeners = new ArrayList<>();
    }
    /*
     * 想要发送通知的程序继承此类，并设置相关属性
     * (1) 定义事件类型等
     */

    public void notifyAll(int eventType, Object data) {
        Iterator<NotifierListener> iterator = listeners.iterator();

        while (iterator.hasNext()) {
            NotifierListener listener = iterator.next();
            try {
                listener.callback(this, eventType, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setNotifierListener(NotifierListener listener) {
        listeners.add(listener);
    }

    public void removeNotifierListener(NotifierListener listener) {
        Iterator<NotifierListener> iterator = listeners.iterator();

        while (iterator.hasNext()) {
            NotifierListener notifierListener = iterator.next();
            if (notifierListener == listener) {
                listeners.remove(notifierListener);
            }
        }
    }
}
