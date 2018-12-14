package com.test.logic.service_accesser;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.test.logic.service_accesser.service_accesser.ServiceAccesser;

/**
 * Created by CuncheW1 on 2017/8/1.
 */

public class BOCServiceAccesser extends ServiceAccesser {
    private final String serviceAction = "com.boc.DeviceService";
    private final String servicePackageName = "com.boc.spos.service";
    private final String serviceClassName = "com.boc.service.DeviceService";
    private ServiceConnection connection;
    private static BOCServiceAccesser instance;

    private boolean isConnected; /* 是否服务已经连接上 */
    private IBinder iBinder; /* 连接service返回的binder */

    public BOCServiceAccesser() {
        connection = createServiceConnection();
        isConnected = false;
        iBinder = null;
    }

    public static synchronized BOCServiceAccesser getInstance() {
        if (instance == null) {
            instance = new BOCServiceAccesser();
        }

        return instance;
    }

    @Override
    public String getServcieAction() {
        return serviceAction;
    }

    @Override
    public String getServicePackageName() {
        return servicePackageName;
    }

    @Override
    public String getServiceClassName() {
        return serviceClassName;
    }

    @Override
    public ServiceConnection getServiceConnection() {
        return connection;
    }

    @Override
    public boolean getConnectStatus() {
        return isConnected;
    }

    @Override
    public IBinder getServiceBinder() {
        return iBinder;
    }

    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isConnected = true;
                iBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                iBinder = null;
            }
        };
    }
}
