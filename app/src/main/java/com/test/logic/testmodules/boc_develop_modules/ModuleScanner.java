package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.scanner.AidlScanner;
import com.boc.aidl.scanner.AidlScannerListener;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleScanner extends BaseModule {
    private final String TAG = "ModuleScanner";
    private AidlScanner iScanner;
    public ModuleScanner() {
        this.iScanner = DeviceService.getScanner();
    }

    public void API_01startScan(int scanType,int timeout, AidlScannerListener listener) {
        try {
            iScanner.startScan(scanType, timeout, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API_02stopScan() {
        try {
            iScanner.stopScan();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean CASE_Scanner_CNstartScan() {
        String items[] = new String[2];
        items[0] = "前置摄像头";
        items[1] = "后置摄像头";
        DialogUtil.showSelectItemDialog("扫描摄像头选择", items, new DialogUtil.SelectDialogListener() {
            @Override
            public void setSelectIndex(int index) {
                int scanType = 1;

                if (index == 0) {
                    scanType = 0;
                } else {
                    scanType = 1;
                }
                API_01startScan(scanType, 60000, new AidlScannerListener.Stub() {
                    @Override
                    public void onScanResult(String[] barcode) throws RemoteException {
                        if (barcode != null) {
                            LogUtil.d(TAG, "barcode=" + barcode[0]);
                            DialogUtil.showConfirmDialog("扫描结果", "barcode=" + barcode[0], null);
                        } else {
                            DialogUtil.showConfirmDialog("扫描结果", "barcode=null", null);
                        }
                    }

                    @Override
                    public void onFinish() throws RemoteException {
                        LogUtil.d(TAG, "---------onFinish-----------");
                    }
                });
            }
        });

        return true;
    }

    public boolean CASE_Scanner_CNstopScan() {
        API_02stopScan();
        return true;
    }
}
