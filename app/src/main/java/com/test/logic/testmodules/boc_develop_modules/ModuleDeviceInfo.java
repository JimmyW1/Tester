package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.deviceInfo.AidlDeviceInfo;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleDeviceInfo extends BaseModule {
    private AidlDeviceInfo iDevInfo;
    private static final String TAG = "ModuleDeviceInfo";
    private boolean bRet = false;

    public ModuleDeviceInfo() {
        this.iDevInfo = DeviceService.getDeviceInfo();
    }

    public String API01getTUSN() throws RemoteException {
       return iDevInfo.getTUSN();
    }

    public String API02getCSN() throws RemoteException {
        return iDevInfo.getCSN();
    }

    public String API03getVID() throws RemoteException {
        return iDevInfo.getVID();
    }

    public String API04getVName() throws RemoteException {
        return iDevInfo.getVName();
    }

    public String API05getKSN() throws RemoteException {
        return iDevInfo.getKSN();
    }

    public boolean API06isSupportIcCard() throws RemoteException {
        return iDevInfo.isSupportIcCard();
    }

    public boolean API07isSupportMagCard() throws RemoteException {
        return iDevInfo.isSupportMagCard();
    }

    public boolean API08isSupportRFCard() throws RemoteException {
        return iDevInfo.isSupportRFCard();
    }
    public boolean API09isSupportPrint() throws RemoteException {
        return iDevInfo.isSupportPrint();
    }

    public boolean API10isSupportOffline() throws RemoteException {
        return iDevInfo.isSupportOffLine();
    }

    public boolean API11isSupportBeep() throws RemoteException {
        return iDevInfo.isSupportBeep();
    }

    public boolean API12isSupportLed() throws RemoteException {
        return iDevInfo.isSupportLed();
    }

    public String API13getVersion() throws RemoteException {
        return iDevInfo.getVersion();
    }

    public String API14getIMSI() throws RemoteException {
        return iDevInfo.getIMSI();
    }

    public String API15getIMEI() throws RemoteException {
        return iDevInfo.getIMEI();
    }

    public String API16getICCID() throws RemoteException {
        return iDevInfo.getICCID();
    }

    public String API17getType() throws RemoteException {
        return iDevInfo.getType();
    }

    public String API18getOSVersion() throws RemoteException {
        return iDevInfo.getOSVersion();
    }

    public String API19getKernelVersion() throws RemoteException {
        return iDevInfo.getKernelVersion();
    }

    public String API20getServiceVersion() throws RemoteException {
        return iDevInfo.getServiceVersion();
    }

    public boolean CASE_DeviceInfo_CN_showAllInfo() {
        String sn = null;

        try {
            getResultTv().showInfoInNewLine("SN=[" + API01getTUSN() + "]");
            getResultTv().showInfoInNewLine("CSN=[" + API02getCSN() + "]");
            getResultTv().showInfoInNewLine("VID=[" + API03getVID() + "]");
            getResultTv().showInfoInNewLine("VName=[" + API04getVName() + "]");
            getResultTv().showInfoInNewLine("KSN=[" + API05getKSN() + "]");
            getResultTv().showInfoInNewLine("SupportICCard:" + API06isSupportIcCard());
            getResultTv().showInfoInNewLine("SupportMagCard:" + API07isSupportMagCard());
            getResultTv().showInfoInNewLine("SupportRFCard:" + API08isSupportRFCard());
            getResultTv().showInfoInNewLine("SupportPrint:" + API09isSupportPrint());
            getResultTv().showInfoInNewLine("SupportOffLine:" + API10isSupportOffline());
            getResultTv().showInfoInNewLine("SupportBeep:" + API11isSupportBeep());
            getResultTv().showInfoInNewLine("SupportLed:" + API12isSupportLed());
            getResultTv().showInfoInNewLine("Version=[" + API13getVersion() + "]");
            getResultTv().showInfoInNewLine("IMSI=[" + API14getIMSI() + "]");
            getResultTv().showInfoInNewLine("IMEI=[" + API15getIMEI() + "]");
            getResultTv().showInfoInNewLine("ICCID=[" + API16getICCID() + "]");
            getResultTv().showInfoInNewLine("Type=[" + API17getType() + "]");
            getResultTv().showInfoInNewLine("OSVersion=[" + API18getOSVersion() + "]");
            getResultTv().showInfoInNewLine("KernelVersion=[" + API19getKernelVersion() + "]");
            getResultTv().showInfoInNewLine("ServiceVersion=[" + API20getServiceVersion() + "]");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return true;
    }
}
