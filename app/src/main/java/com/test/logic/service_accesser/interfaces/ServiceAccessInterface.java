package com.test.logic.service_accesser.interfaces;

import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by CuncheW1 on 2017/8/1.
 */

public interface ServiceAccessInterface {
    /**
     * 获取服务绑定需要的Action串
     * @return 服务AndroidManifest.xml中注册的服务action标识
     */
    String getServcieAction();

    /**
     * 获取服务绑定需要的包名字符串
     * @return 服务代码中app/build.gradle中defaultConfig->applicationId对应的字符串，如果没有使用AndroidManifest.xml中的package字符串
     */
    String getServicePackageName();

    /**
     * 获取服务绑定需要的Service子类对象所在的类名，含对应的包名
     * @return 返回Service应用中继承Service类返回binder类对象的类名并包含所在包名
     */
    String getServiceClassName();

    /**
     * 获取服务访问绑定回调，当绑定成功和失败会分别调用ServiceConnection中的对应callback
     * @return  系统定义的ServiceConnection类
     */
    ServiceConnection getServiceConnection();

    /**
     * 获取当前绑定服务的状态,可通过ServiceConnection接口中设置标志位判断
     * @return 当前已绑定服务返回true，否则返回false
     */
    public boolean getConnectStatus();

    /**
     * 获取当前绑定服务返回的Binder
     * @return 如果服务已连接返回正确的Binder，否则返回null
     */
    public IBinder getServiceBinder();
}
