package com.test.logic.testmodules.boc_modules.common;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.ui.activities.MyApplication;

/**
 * Created by InovaF1 on 2017/5/15.
 */

public class DeviceService {
    private static AidlDeviceService aidlDeviceService = null;
    public static AidlDeviceService getDeviceService(){
        if (aidlDeviceService == null) {
            ServiceAccesser accesser = MyApplication.getInstance().getCurrentTestEntry().getServiceAccesser();
            if (accesser.getConnectStatus() == false) {
                aidlDeviceService = null;
                return aidlDeviceService;
            }

            aidlDeviceService = AidlDeviceService.Stub.asInterface(accesser.getServiceBinder());
        }

        return aidlDeviceService;
    }

    public static void setDeviceService(AidlDeviceService aidlDeviceService){
        DeviceService.aidlDeviceService = aidlDeviceService;
    }
}
