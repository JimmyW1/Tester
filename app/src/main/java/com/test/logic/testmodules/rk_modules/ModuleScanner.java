package com.test.logic.testmodules.rk_modules;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.smartpos.deviceservice.aidl.IScanner;
import com.smartpos.deviceservice.aidl.ScannerListener;
import com.test.logic.annotations.CaseAttributes;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.rk_modules.common.DeviceService;
import com.test.util.LogUtil;

public class ModuleScanner extends BaseModule {
    private final String TAG = "ModuleScanner";
    private IScanner iScanner;
    private static ModuleScanner instance;

    public ModuleScanner() {
        try {
            iScanner = DeviceService.getDeviceService().getScanner(0);
            LogUtil.d(TAG, "aidlScanner=" + iScanner == null ? "null" : "not null");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ModuleScanner getInstance() {
        if (instance == null) {
            instance = new ModuleScanner();
        }

        return instance;
    }

    public boolean API01_startScan(Bundle param, long timeout, ScannerListener scannerListener) {
        try {
            if (iScanner == null) {
                LogUtil.d(TAG, "iscanner is null");
                return false;
            }
            iScanner.startScan(param, timeout, new ScannerListener.Stub() {
                @Override
                public void onSuccess(String barcode) throws RemoteException {

                }

                @Override
                public void onError(int error, String message) throws RemoteException {

                }

                @Override
                public void onTimeout() throws RemoteException {

                }

                @Override
                public void onCancel() throws RemoteException {

                }
            });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @CaseAttributes(isSupportAutoTest = false)
    public boolean CASE_startScan_CN001() {
        Bundle msgPrompt = new Bundle();
        msgPrompt.putString("upPromptString", "上标题");
        msgPrompt.putString("downPromptString", "下标题");
        msgPrompt.putString("topTitleString", "主标题");
        msgPrompt.putInt("whichCamera", 0);

        API01_startScan(msgPrompt, 60000, null);
        return true;
    }

    @CaseAttributes(isSupportAutoTest = false)
    public boolean CASE_startScan_CN002() {
        Bundle msgPrompt = new Bundle();
        msgPrompt.putString("upPromptString", "上标题");
        msgPrompt.putString("downPromptString", "下标题");
        msgPrompt.putString("topTitleString", "主标题");
        msgPrompt.putInt("whichCamera", 1);

        API01_startScan(msgPrompt, 60000, null);
        return true;
    }
}
