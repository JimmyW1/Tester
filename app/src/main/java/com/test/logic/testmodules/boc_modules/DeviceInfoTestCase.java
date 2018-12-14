package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.deviceInfo.AidlDeviceInfo;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/19.
 */

public class DeviceInfoTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";

    public String API01getTUSN()  {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getTUSN();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API02getCSN() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getCSN();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API03getVID(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getVID();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API04getVName() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getVName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API05getKSN() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getKSN();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean API06isSupportIcCard() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportIcCard();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API07isSupportMagCard() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportMagCard();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API08isSupportRFCard(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportRFCard();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API09isSupportPrint(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportPrint();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API10isSupportOffline(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportOffLine();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API11isSupportBeep(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportBeep();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean API12isSupportLed() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).isSupportLed();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String API13getVersion() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API14getIMSI() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getIMSI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API15getIMEI() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getIMEI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API16getICCID() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getICCID();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API17getType(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getType();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API18getOSVersion(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getOSVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API19getKernelVersion(){
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getKernelVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API20getServiceVersion() {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getServiceVersion();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API21getHardwareNumber()
    {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getHardwareNumber();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API22getLicenseNumber()
    {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getLicenseNumber();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API23getLongitude()
    {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getLongitude();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API24getLatitude ()
    {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getLatitude();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String API25getSM4EncryptedData (String data)
    {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlDeviceInfo.Stub.asInterface(handle.getDeviceInfo()).getSM4EncryptedData(data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *  测试案例
     */
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN001",
            resDialogTitle = "查看log", APIName = "getTUSN")
    public boolean CASE_getDeviceInfo_CN001() {
        Log.i(TAG, "CASE_getDeviceInfo_CN001");
        String ret = API01getTUSN();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getTUSN=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN002",
            resDialogTitle = "查看log", APIName = "getCSN")
    public boolean CASE_getDeviceInfo_CN002() {
        Log.i(TAG, "CASE_getDeviceInfo_CN002");
        String ret = API02getCSN();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getCSN=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN003",
            resDialogTitle = "查看log", APIName = "getVID")
    public boolean CASE_getDeviceInfo_CN003() {
        Log.i(TAG, "CASE_getDeviceInfo_CN003");
        String ret = API03getVID();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getVID=" + ret);
            return true;
        }
        return false;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN004",
            resDialogTitle = "查看log", APIName = "getVName")
    public boolean CASE_getDeviceInfo_CN004() {
        Log.i(TAG, "CASE_getDeviceInfo_CN004");
        String ret = API04getVName();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getVName=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN005",
            resDialogTitle = "查看log", APIName = "getKSN")
    public boolean CASE_getDeviceInfo_CN005() {
        Log.i(TAG, "CASE_getDeviceInfo_CN005");
        String ret = API05getKSN();
       if(ret != null && ret.length() != 0)
       {
           ToastUtil.showLong("getKSN=" + ret);
           return true;
       }
       return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN006",
            resDialogTitle = "查看log", APIName = "isSupportIcCard")
    public boolean CASE_getDeviceInfo_CN006() {
        Log.i(TAG, "CASE_getDeviceInfo_CN006");
        boolean ret = API06isSupportIcCard();
        Log.i(TAG, "isSupportIcCard=" + ret);
        if(ret)
        {
            ToastUtil.showLong("支持IC卡");
            return ret;
        }else
        {
            ToastUtil.showLong("不支持IC卡");
            return false;
        }

    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN007",
//            resDialogTitle = "查看log", APIName = "isSupportIcCard")
//    public boolean CASE_getDeviceInfo_CN007() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN007");
//        boolean ret = API06isSupportIcCard();
//        Log.i(TAG, "isSupportIcCard=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN008",
            resDialogTitle = "查看log", APIName = "isSupportMagCard")
    public boolean CASE_getDeviceInfo_CN008() {
        Log.i(TAG, "CASE_getDeviceInfo_CN008");
        boolean ret = API07isSupportMagCard();
        if(ret)
        {
            ToastUtil.showLong("支持磁条卡");
            return true;
        }else
        {
            ToastUtil.showLong("不支持磁条卡");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN009",
//            resDialogTitle = "查看log", APIName = "isSupportMagCard")
//    public boolean CASE_getDeviceInfo_CN009() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN009");
//        boolean ret = API07isSupportMagCard();
//        Log.i(TAG, "isSupportMagCard=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN010",
            resDialogTitle = "查看log", APIName = "isSupportRFCard")
    public boolean CASE_getDeviceInfo_CN010() {
        Log.i(TAG, "CASE_getDeviceInfo_CN010");
        boolean ret = API08isSupportRFCard();
        if(ret)
        {
            ToastUtil.showLong("支持非接卡");
            return true;
        }else
        {
            ToastUtil.showLong("不支持非接卡");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN011",
//            resDialogTitle = "查看log", APIName = "isSupportRFCard")
//    public boolean CASE_getDeviceInfo_CN011() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN011");
//        boolean ret = API08isSupportRFCard();
//        Log.i(TAG, "isSupportRFCard=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN012",
            resDialogTitle = "查看log", APIName = "isSupportPrint")
    public boolean CASE_getDeviceInfo_CN012() {
        Log.i(TAG, "CASE_getDeviceInfo_CN012");
        boolean ret = API09isSupportPrint();
        if(ret)
        {
            ToastUtil.showLong("支持打印");
            return true;
        }else
        {
            ToastUtil.showLong("不支持打印");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN013",
//            resDialogTitle = "查看log", APIName = "isSupportPrint")
//    public boolean CASE_getDeviceInfo_CN013() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN013");
//        boolean ret = API09isSupportPrint();
//        Log.i(TAG, "isSupportPrint=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN014",
            resDialogTitle = "查看log", APIName = "isSupportOffLine")
    public boolean CASE_getDeviceInfo_CN014() {
        Log.i(TAG, "CASE_getDeviceInfo_CN014");
        boolean ret = API10isSupportOffline();
        if(ret)
        {
            ToastUtil.showLong("支持脱机");
            return true;
        }else
        {
            ToastUtil.showLong("不支持脱机");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN015",
//            resDialogTitle = "查看log", APIName = "isSupportOffLine")
//    public boolean CASE_getDeviceInfo_CN015() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN015");
//        boolean ret = API10isSupportOffline();
//        Log.i(TAG, "isSupportOffLine=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN016",
            resDialogTitle = "查看log", APIName = "isSupportBeep")
    public boolean CASE_getDeviceInfo_CN016() {
        Log.i(TAG, "CASE_getDeviceInfo_CN016");
        boolean ret = API11isSupportBeep();
        if(ret)
        {
            ToastUtil.showLong("支持蜂鸣器");
            return true;
        }else
        {
            ToastUtil.showLong("不支持蜂鸣器");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN017",
//            resDialogTitle = "查看log", APIName = "isSupportBeep")
//    public boolean CASE_getDeviceInfo_CN017() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN017");
//        boolean ret = API11isSupportBeep();
//        Log.i(TAG, "isSupportBeep=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN018",
            resDialogTitle = "查看log", APIName = "isSupportLed")
    public boolean CASE_getDeviceInfo_CN018() {
        Log.i(TAG, "CASE_getDeviceInfo_CN018");
        boolean ret = API12isSupportLed();
        if(ret)
        {
            ToastUtil.showLong("支持LED");
            return ret;
        }else
        {
            ToastUtil.showLong("不支持LED");
            return false;
        }
    }
//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN019",
//            resDialogTitle = "查看log", APIName = "isSupportLed")
//    public boolean CASE_getDeviceInfo_CN019() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN019");
//        boolean ret = API12isSupportLed();
//        Log.i(TAG, "isSupportLed=" + ret);
//        return !ret;
//    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN020",
            resDialogTitle = "查看log", APIName = "getVersion")
    public boolean CASE_getDeviceInfo_CN020() {
        Log.i(TAG, "CASE_getDeviceInfo_CN020");
        String ret = API13getVersion();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getVersions=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN021",
            resDialogTitle = "查看log", APIName = "getIMSI")
    public boolean CASE_getDeviceInfo_CN021() {
        Log.i(TAG, "CASE_getDeviceInfo_CN021");
        String ret = API14getIMSI();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getIMSI=" + ret);
            return true;
        }
        return false;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN022",
            resDialogTitle = "查看log", APIName = "getIMEI")
    public boolean CASE_getDeviceInfo_CN022() {
        Log.i(TAG, "CASE_getDeviceInfo_CN022");
        String ret = API15getIMEI();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getIMEI=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN023",
            resDialogTitle = "查看log", APIName = "getICCID")
    public boolean CASE_getDeviceInfo_CN023() {
        Log.i(TAG, "CASE_getDeviceInfo_CN023");
        String ret = API16getICCID();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getICCID=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN024",
            resDialogTitle = "查看log", APIName = "getType")
    public boolean CASE_getDeviceInfo_CN024() {
        Log.i(TAG, "CASE_getDeviceInfo_CN024");
        String ret = API17getType();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getType=" + ret);
            return true;
        }
        return false;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN025",
            resDialogTitle = "查看log", APIName = "getOSVersion")
    public boolean CASE_getDeviceInfo_CN025() {
        Log.i(TAG, "CASE_getDeviceInfo_CN025");
        String ret = API18getOSVersion();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getOSVersion=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN026",
            resDialogTitle = "查看log", APIName = "getKernelVersion")
    public boolean CASE_getDeviceInfo_CN026() {
        Log.i(TAG, "CASE_getDeviceInfo_CN026");
        String ret = API19getKernelVersion();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getKernelVersion=" + ret);
            return true;
        }
        return false;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN027",
            resDialogTitle = "查看log", APIName = "getServiceVersion")
    public boolean CASE_getDeviceInfo_CN027() {
        Log.i(TAG, "CASE_getDeviceInfo_CN027");
        String ret = API20getServiceVersion();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getServiceVersion=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN028",
            resDialogTitle = "查看log", APIName = "getHardwareNumber()")
    public boolean CASE_getDeviceInfo_CN028() {
        Log.i(TAG, "CASE_getDeviceInfo_CN028");
        String ret = API21getHardwareNumber();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("硬件序列号=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN029",
            resDialogTitle = "查看log", APIName = "getLicenseNumber()")
    public boolean CASE_getDeviceInfo_CN029() {
        Log.i(TAG, "CASE_getDeviceInfo_CN029");
        String ret = API22getLicenseNumber();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getLicenseNumber=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN030",
            resDialogTitle = "查看log", APIName = "getLongitude()")
    public boolean CASE_getDeviceInfo_CN030() {
        Log.i(TAG, "CASE_getDeviceInfo_CN030");
        String ret = API23getLongitude();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getLongitude=" + ret);
            return true;
        }
        return false;

    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN031",
            resDialogTitle = "查看log", APIName = "getLatitude()")
    public boolean CASE_getDeviceInfo_CN031() {
        Log.i(TAG, "CASE_getDeviceInfo_CN031");
        String ret = API24getLatitude();
        if(ret != null && ret.length() != 0)
        {
            ToastUtil.showLong("getLatitude=" + ret);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN032",
            resDialogTitle = "查看log", APIName = "getSM4EncryptedData()")
    public String CASE_getDeviceInfo_CN032() {
        Log.i(TAG, "CASE_getDeviceInfo_CN032");
        String data = API25getSM4EncryptedData("112233");
        Log.i(TAG,"getSM4EncryptedData=" + data);
        if(data != null && data.length() != 0)
        {
            ToastUtil.showLong(data);
        }
        return data;
    }

//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN033",
//            resDialogTitle = "查看log", APIName = "getSM4EncryptedData()")
//    public String CASE_getDeviceInfo_CN033() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN033");
//        String data = API25getSM4EncryptedData("");
//        Log.i(TAG,"getSM4EncryptedData=" + data);
//        if(data != null && data.length() != 0)
//        {
//            ToastUtil.showLong(data);
//        }
//        return data;
//    }

//    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceInfo_CN034",
//            resDialogTitle = "查看log", APIName = "getSM4EncryptedData()")
//    public String CASE_getDeviceInfo_CN034() {
//        Log.i(TAG, "CASE_getDeviceInfo_CN034");
//        String data = API25getSM4EncryptedData(null);
//        Log.i(TAG,"getSM4EncryptedData=" + data);
//        if(data != null && data.length() != 0)
//        {
//            ToastUtil.showLong(data);
//        }
//        return data;
//    }


}