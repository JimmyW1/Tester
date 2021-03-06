package com.test.logic.testmodules.rk_modules.common;

import com.smartpos.deviceservice.aidl.IDeviceService;
import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.ui.activities.MyApplication;

/**
 * Created by InovaF1 on 2017/5/15.
 */

public class DeviceService {
    private static IDeviceService aidlDeviceService = null;

    public static IDeviceService getDeviceService(){
        ServiceAccesser accesser = MyApplication.getInstance().getCurrentTestEntry().getServiceAccesser();
        if (accesser.getConnectStatus()) {
            return IDeviceService.Stub.asInterface(accesser.getServiceBinder());
        }
        
        return null;
    }

    public static void setDeviceService(IDeviceService aidlDeviceService){
        DeviceService.aidlDeviceService = aidlDeviceService;
    }

}
