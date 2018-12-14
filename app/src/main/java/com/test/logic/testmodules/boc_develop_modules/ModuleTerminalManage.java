package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.terminal.AidlTerminalManage;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleTerminalManage extends BaseModule {
    private static final String TAG = "ModuleTerminal";
    private AidlTerminalManage iTermManager;

    public ModuleTerminalManage() {
        this.iTermManager = DeviceService.getTerminalManager();
    }

    public boolean CASE_TermManage_CN001() {
        Log.i("TAG", "setDeviceDate");
        try {
            iTermManager.setDeviceDate("20201111111111");
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean CASE_TermManage_CN002() {
        Log.i("TAG", "setDeviceDate");
        try {
            iTermManager.setDeviceDate("20201311115511");
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean CASE_TermManage_CN003() {
        Log.i("TAG", "setDeviceDate");
        try {
            iTermManager.setDeviceDate("20121111116611");
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean CASE_TermManage_CN004() {
        String date = null;
        try {
            date = iTermManager.getDeviceDate();
            Log.i("TAG", "getDeviceDate: " + date);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        getResultTv().showInfoInNewLine("date=[" + date + "]");
        if (date == null)
            return false;
        else
            return true;
    }
}
