package com.test.logic.notifier.interfaces;

import com.test.logic.notifier.Notifier;

/**
 * Created by wangcunche on 2017/8/6.
 */

public interface NotifierListener {
    /**
     * 事件通知，当某个模块创建一个Notifier之后，会将产生的相关事件通过此回调接口通知给注册此Notifier的线程
     * @param entry
     * @param eventType 通知事件类型
     * @param data 通知传达的数据
     */
    void callback(Notifier entry, int eventType, Object data);
}
