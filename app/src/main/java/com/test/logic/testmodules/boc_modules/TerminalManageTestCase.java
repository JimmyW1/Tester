package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.terminal.AidlTerminalManage;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/20.
 */

public class TerminalManageTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";

    public void API01setDeviceDate(String date)  {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                AidlTerminalManage.Stub.asInterface(handle.getTerminalManage()).setDeviceDate(date);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public String API02getDeviceDate()  {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if(handle != null) {
            try {
                return AidlTerminalManage.Stub.asInterface(handle.getTerminalManage()).getDeviceDate();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN001",
            resDialogTitle = "CASE_setDeviceDate_CN001", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN001() {
        Log.i(TAG, "CASE_setDeviceDate_CN001");
        API01setDeviceDate("19710101010101");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN002",
            resDialogTitle = "CASE_setDeviceDate_CN002", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN002() {
        Log.i(TAG, "CASE_setDeviceDate_CN002");
        API01setDeviceDate("20170101010101");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN003",
            resDialogTitle = "CASE_setDeviceDate_CN003", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN003() {
        Log.i(TAG, "CASE_setDeviceDate_CN003");
        API01setDeviceDate("20371231235959");
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN004",
            resDialogTitle = "CASE_setDeviceDate_CN004", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN004() {
        Log.i(TAG, "CASE_setDeviceDate_CN004");
        API01setDeviceDate(null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN005",
            resDialogTitle = "CASE_setDeviceDate_CN005", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN005() {
        Log.i(TAG, "CASE_setDeviceDate_CN005");
        API01setDeviceDate("");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN006",
            resDialogTitle = "CASE_setDeviceDate_CN006", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN006() {
        Log.i(TAG, "CASE_setDeviceDate_CN006");
        API01setDeviceDate("-1");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN007",
            resDialogTitle = "CASE_setDeviceDate_CN007", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN007() {
        Log.i(TAG, "CASE_setDeviceDate_CN007");
        API01setDeviceDate("201701010101010");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN008",
            resDialogTitle = "CASE_setDeviceDate_CN008", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN008() {
        Log.i(TAG, "CASE_setDeviceDate_CN008");
        API01setDeviceDate("2017010101010");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN009",
            resDialogTitle = "CASE_setDeviceDate_CN009", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN009() {
        Log.i(TAG, "CASE_setDeviceDate_CN009");
        API01setDeviceDate("21000101010101");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN010",
            resDialogTitle = "CASE_setDeviceDate_CN010", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN010() {
        Log.i(TAG, "CASE_setDeviceDate_CN010");
        API01setDeviceDate("20171301010101");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN011",
            resDialogTitle = "CASE_setDeviceDate_CN011", APIName = "20170132010101")
    public void CASE_setDeviceDate_CN011() {
        Log.i(TAG, "CASE_setDeviceDate_CN011");
        API01setDeviceDate("20991231235959");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN012",
            resDialogTitle = "CASE_setDeviceDate_CN012", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN012() {
        Log.i(TAG, "CASE_setDeviceDate_CN012");
        API01setDeviceDate("20170101250101");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN013",
            resDialogTitle = "CASE_setDeviceDate_CN013", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN013() {
        Log.i(TAG, "CASE_setDeviceDate_CN013");
        API01setDeviceDate("20170101016001");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN014",
            resDialogTitle = "CASE_setDeviceDate_CN014", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN014() {
        Log.i(TAG, "CASE_setDeviceDate_CN014");
        API01setDeviceDate("20170101010160");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setDeviceDate_CN015",
            resDialogTitle = "CASE_setDeviceDate_CN015", APIName = "setDeviceDate")
    public void CASE_setDeviceDate_CN015() {
        Log.i(TAG, "CASE_setDeviceDate_CN015");
        API01setDeviceDate("18990000999999");
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceDate_CN001",
            resDialogTitle = "查看log", APIName = "getDeviceDate")
    public boolean CASE_getDeviceDate_CN001() {
        Log.i(TAG, "CASE_getDeviceDate_CN001");
        String ret = API02getDeviceDate();
        ToastUtil.showLong("getDeviceDate = " + ret);
        return (ret != null && ret.length() != 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceDate_CN002",
            resDialogTitle = "查看log", APIName = "getDeviceDate")
    public boolean CASE_getDeviceDate_CN002() {
        Log.i(TAG, "CASE_getDeviceDate_CN002");
        API01setDeviceDate("19710101010101");
        String ret = API02getDeviceDate();
        ToastUtil.showLong("getDeviceDate = " + ret);
        return (ret.equals("19710101010101"));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceDate_CN003",
            resDialogTitle = "查看log", APIName = "getDeviceDate")
    public boolean CASE_getDeviceDate_CN003() {
        Log.i(TAG, "CASE_getDeviceDate_CN003");
        API01setDeviceDate("20170101010101");
        String ret = API02getDeviceDate();
        ToastUtil.showLong("getDeviceDate = " + ret);
        return (ret.equals("20170101010101"));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getDeviceDate_CN004",
            resDialogTitle = "查看log", APIName = "getDeviceDate")
    public boolean CASE_getDeviceDate_CN004() {
        Log.i(TAG, "CASE_getDeviceDate_CN004");
        API01setDeviceDate("20371231235959");
        String ret = API02getDeviceDate();
        ToastUtil.showLong("getDeviceDate = " + ret);
        return (ret.equals("20371231235959"));
    }

}
