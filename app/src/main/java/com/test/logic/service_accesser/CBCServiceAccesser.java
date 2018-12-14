package com.test.logic.service_accesser;

import android.content.ServiceConnection;
import android.os.IBinder;

import com.test.logic.service_accesser.service_accesser.ServiceAccesser;

/**
 * Created by CuncheW1 on 2017/8/2.
 */

public class CBCServiceAccesser extends ServiceAccesser {
    @Override
    public String getServcieAction() {
        return null;
    }

    @Override
    public String getServicePackageName() {
        return null;
    }

    @Override
    public String getServiceClassName() {
        return null;
    }

    @Override
    public ServiceConnection getServiceConnection() {
        return null;
    }

    @Override
    public boolean getConnectStatus() {
        return false;
    }

    @Override
    public IBinder getServiceBinder() {
        return null;
    }
}
