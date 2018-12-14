package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.printer.AidlPrinter;
import com.boc.aidl.printer.AidlPrinterListener;
import com.boc.aidl.scanner.AidlScanner;
import com.boc.aidl.scanner.AidlScannerListener;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/15.
 */

public class ScannerTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";
    boolean status_ret = false;

    AidlScannerListener getNewScannerListener() {
        return new AidlScannerListener.Stub() {
            @Override
            public void onScanResult(String[] barcode) throws RemoteException {
                Log.i(TAG,"barcode=" + barcode[0]);
                ToastUtil.show("扫描结果：" + "\n" + barcode[0],6000);
                status_ret = true;
            }


            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG,"Finish input");
                status_ret = true;
            }
        };
    }

    public boolean API01startScan_1(int scanType,int timeout, AidlScannerListener listener) {
        status_ret = false;
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlScanner.Stub.asInterface(handle.getScan()).startScan(scanType,timeout, listener);
            }

            for(int i=0; i<timeout/1000; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(status_ret)
                {
                    return status_ret;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return status_ret;
    }

    public boolean API01startScan(int scanType,int timeout, AidlScannerListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlScanner.Stub.asInterface(handle.getScan()).startScan(scanType,timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return status_ret;
    }

    public void API02stopScan() {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlScanner.Stub.asInterface(handle.getScan()).stopScan();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //CASE_otherTest_CN027
    public void API04printQrCode(String qrCode, int height, int errorCorrectingLevel, int position, int timeout, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).printQrCode(qrCode, height, errorCorrectingLevel, position, timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN001",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN001() {
        Log.i(TAG, "CASE_startScan_CN001");
        return API01startScan_1(0, 60000, getNewScannerListener());
    }

     @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN002",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN002() {
        Log.i(TAG, "CASE_startScan_CN002");
        return API01startScan_1(1, 60000, getNewScannerListener());
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN003",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN003() {
        Log.i(TAG, "CASE_startScan_CN003");
        return !API01startScan_1(-1, 60000, getNewScannerListener());
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN004",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN004() {
        Log.i(TAG, "CASE_startScan_CN004");
        return !API01startScan_1(2, 60000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN005",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN005() {
        Log.i(TAG, "CASE_startScan_CN005");
        return !API01startScan_1(Integer.MAX_VALUE, 60000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN006",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN006() {
        Log.i(TAG, "CASE_startScan_CN006");
        return !API01startScan_1(Integer.MAX_VALUE+1, 60000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN007",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN007() {
        Log.i(TAG, "CASE_startScan_CN007");
        return !API01startScan_1(Integer.MIN_VALUE, 60000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN008",
            resDialogTitle = "", APIName = "startScan")
    public boolean CASE_startScan_CN008() {
        Log.i(TAG, "CASE_startScan_CN008");
        return !API01startScan_1(Integer.MIN_VALUE-1, 60000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN009",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN009() {
        Log.i(TAG, "CASE_startScan_CN009");
        API01startScan_1(0, 0, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0010",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0010() {
        Log.i(TAG, "CASE_startScan_CN0010");
        API01startScan_1(1, -1, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0011",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0011() {
        Log.i(TAG, "CASE_startScan_CN0011");
        API01startScan_1(0, Integer.MAX_VALUE, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0012",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0012() {
        Log.i(TAG, "CASE_startScan_CN0012");
        API01startScan_1(0, Integer.MAX_VALUE+1, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0013",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0013() {
        Log.i(TAG, "CASE_startScan_CN0013");
        API01startScan_1(0, Integer.MIN_VALUE, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0014",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0014() {
        Log.i(TAG, "CASE_startScan_CN0014");
        API01startScan_1(0, Integer.MIN_VALUE-1, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0015",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0015() {
        Log.i(TAG, "CASE_startScan_CN0015");
        API01startScan_1(0, 60000, null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0016",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0016() {
        Log.i(TAG, "CASE_startScan_CN0016");
        API01startScan_1(0, 30000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0017",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0017() {
        Log.i(TAG, "CASE_startScan_CN0017");
        API01startScan_1(1, 30000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0018",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0018() {
        Log.i(TAG, "CASE_startScan_CN0018");
        API01startScan_1(0, 30000, getNewScannerListener());
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startScan_CN0019",
            resDialogTitle = "", APIName = "startScan")
    public void CASE_startScan_CN0019() {
        Log.i(TAG, "CASE_startScan_CN0019");
        API01startScan_1(1, 30000, getNewScannerListener());
    }*/

  @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN001",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN001() {
        Log.i(TAG, "CASE_stopScan_CN001");
        API01startScan(0, 60000, getNewScannerListener());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN002",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN002() {
        Log.i(TAG, "CASE_stopScan_CN002");
        API01startScan(1, 60000, getNewScannerListener());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN003",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN003() {
        Log.i(TAG, "CASE_stopScan_CN003");
        API01startScan(0, 60000, getNewScannerListener());
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN004",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN004() {
        Log.i(TAG, "CASE_stopScan_CN004");
        API01startScan(1, 60000, getNewScannerListener());
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN005",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN005() {
        Log.i(TAG, "CASE_stopScan_CN005");
        API01startScan_1(0, 60000, getNewScannerListener());
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN006",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN006() {
        Log.i(TAG, "CASE_stopScan_CN006");
        API01startScan_1(1, 60000, getNewScannerListener());
        API02stopScan();
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_stopScan_CN007",
            resDialogTitle = "", APIName = "stopScan")
    public void CASE_stopScan_CN007() {
        Log.i(TAG, "CASE_stopScan_CN007");
        API02stopScan();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_otherTest_CN027",
            resDialogTitle = "", APIName = "otherTest")
    public void CASE_otherTest_CN027() {
        Log.i(TAG, "CASE_otherTest_CN027");
        API01startScan_1(0, 30000, getNewScannerListener());
        API04printQrCode("1", 100, 0, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_otherTest_CN028",
            resDialogTitle = "", APIName = "otherTest")
    public void CASE_otherTest_CN028() {
        Log.i(TAG, "CASE_otherTest_CN028");
        API01startScan_1(0, 30000, getNewScannerListener());
        API01startScan_1(1, 30000, getNewScannerListener());
        API01startScan_1(0, 30000, getNewScannerListener());
        API01startScan_1(1, 30000, getNewScannerListener());
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_otherTest_CN029",
            resDialogTitle = "", APIName = "otherTest")
    public void CASE_otherTest_CN029() {
        Log.i(TAG, "CASE_otherTest_CN029");
        for (int i=0; i<100; i++)
        {
            API01startScan(0, 60000, getNewScannerListener());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            API02stopScan();
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_otherTest_CN030",
            resDialogTitle = "", APIName = "otherTest")
    public void CASE_otherTest_CN030() {
        Log.i(TAG, "CASE_otherTest_CN030");
        for (int i=0; i<100; i++)
        {
            API01startScan(1, 60000, getNewScannerListener());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            API02stopScan();
        }
    }*/
}
