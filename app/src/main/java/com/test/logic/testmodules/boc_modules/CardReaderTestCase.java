package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.cardreader.AidlCardReader;
import com.boc.aidl.cardreader.AidlCardReaderListener;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/16.
 */

public class CardReaderTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";
    boolean status_ret = false;

    public static void API01openCardReader(int openCardType, boolean isAllowfallback,boolean isMSDChecking, int timeout, AidlCardReaderListener cardReaderListener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlCardReader.Stub.asInterface(handle.getCardReader()).openCardReader(openCardType, isAllowfallback, isMSDChecking, timeout, cardReaderListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void API02cancelCardRead() {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlCardReader.Stub.asInterface(handle.getCardReader()).cancelCardRead();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API03closeCardReader() {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlCardReader.Stub.asInterface(handle.getCardReader()).closeCardReader();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    AidlCardReaderListener getNewCardReaderListener() {
        return new AidlCardReaderListener.Stub() {
            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "Code=" + errorCode);
                Log.i(TAG, "Description=" + errorDescription);
                ToastUtil.showLong(errorCode + ":" + errorDescription);
                status_ret = false;
            }

            @Override
            public void onFindingCard(int cardType) throws RemoteException {
                Log.i(TAG, "Card type=" + cardType);
                ToastUtil.showLong("Card type=" + cardType);
                status_ret = true;
            }
        };
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN001",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN001() {
        Log.i(TAG, "CASE_openCardReader_CN001");
        status_ret = false;
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN002",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN002() {
        Log.i(TAG, "CASE_openCardReader_CN002");
        status_ret = false;
        API01openCardReader(0x02, true, false, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN003",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN003() {
        Log.i(TAG, "CASE_openCardReader_CN003");
        status_ret = false;
        API01openCardReader(0x04, true, false, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN004",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN004() {
        Log.i(TAG, "CASE_openCardReader_CN004");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN005",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN005() {
        Log.i(TAG, "CASE_openCardReader_CN005");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN006",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN006() {
        Log.i(TAG, "CASE_openCardReader_CN006");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN007",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN007() {
        Log.i(TAG, "CASE_openCardReader_CN007");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN008",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN008() {
        Log.i(TAG, "CASE_openCardReader_CN008");
        status_ret = true;
        API01openCardReader(0x01|0x02|0x04, false, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!status_ret)
            {
                return !status_ret;
            }
        }
        return !status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN009",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN009() {
        Log.i(TAG, "CASE_openCardReader_CN009");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN010",
            resDialogTitle = "", APIName = "openCardReader")
    public boolean CASE_openCardReader_CN010() {
        Log.i(TAG, "CASE_openCardReader_CN010");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, false, 60000, getNewCardReaderListener());
        for(int i=0; i<=12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                return status_ret;
            }
        }
        return status_ret;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN011",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN011() {
        Log.i(TAG, "CASE_openCardReader_CN011");
        API01openCardReader(0, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN012",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN012() {
        Log.i(TAG, "CASE_openCardReader_CN012");
        API01openCardReader(-1, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN013",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN013() {
        Log.i(TAG, "CASE_openCardReader_CN013");
        API01openCardReader(3, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN014",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN014() {
        Log.i(TAG, "CASE_openCardReader_CN014");
        API01openCardReader(7, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN015",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN015() {
        Log.i(TAG, "CASE_openCardReader_CN015");
        API01openCardReader(Integer.MAX_VALUE, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN016",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN016() {
        Log.i(TAG, "CASE_openCardReader_CN016");
        API01openCardReader(Integer.MAX_VALUE+1, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN017",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN017() {
        Log.i(TAG, "CASE_openCardReader_CN017");
        API01openCardReader(Integer.MIN_VALUE, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN018",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN018() {
        Log.i(TAG, "CASE_openCardReader_CN018");
        API01openCardReader(Integer.MIN_VALUE-1, true, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN019",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN019() {
        Log.i(TAG, "CASE_openCardReader_CN019");
        API01openCardReader(0x01|0x02|0x04, null, true, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN020",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN020() {
        Log.i(TAG, "CASE_openCardReader_CN020");
        API01openCardReader(0x01|0x02|0x04, true, null, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN021",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN021() {
        Log.i(TAG, "CASE_openCardReader_CN021");
        API01openCardReader(0x01|0x02|0x04, true, true, 0, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN022",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN022() {
        Log.i(TAG, "CASE_openCardReader_CN022");
        API01openCardReader(0x01|0x02|0x04, true, true, -1, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN023",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN023() {
        Log.i(TAG, "CASE_openCardReader_CN023");
        API01openCardReader(0x01|0x02|0x04, true, true, Integer.MAX_VALUE, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN024",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN024() {
        Log.i(TAG, "CASE_openCardReader_CN024");
        API01openCardReader(0x01|0x02|0x04, true, true, Integer.MAX_VALUE+1, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN025",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN025() {
        Log.i(TAG, "CASE_openCardReader_CN025");
        API01openCardReader(0x01|0x02|0x04, true, true, Integer.MIN_VALUE, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN026",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN026() {
        Log.i(TAG, "CASE_openCardReader_CN026");
        API01openCardReader(0x01|0x02|0x04, true, true, Integer.MIN_VALUE-1, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_openCardReader_CN027",
            resDialogTitle = "查看log", APIName = "openCardReader")
    public void CASE_openCardReader_CN027() {
        Log.i(TAG, "CASE_openCardReader_CN027");
        API01openCardReader(0x01|0x02|0x04, true, true, 60000, null);
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_cancelCardRead_CN001",
            resDialogTitle = "log无读卡信息", APIName = "cancelCardRead")
    public void CASE_cancelCardRead_CN001() {
        Log.i(TAG, "CASE_cancelCardRead_CN001");
        API01openCardReader(0x01|0x02|0x04, true, false, 60000, getNewCardReaderListener());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API02cancelCardRead();
        return;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_closeCardReader_CN001",
            resDialogTitle = "log无读卡信息", APIName = "closeCardReader")
    public void CASE_closeCardReader_CN001() {
        Log.i(TAG, "CASE_closeCardReader_CN001");
        API01openCardReader(0x01|0x02|0x04, true, false, 60000, getNewCardReaderListener());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API03closeCardReader();
        return;
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_closeCardReader_CN002",
            resDialogTitle = "log只有第一次刷卡的信息", APIName = "closeCardReader")
    public void CASE_closeCardReader_CN002() {
        Log.i(TAG, "CASE_closeCardReader_CN002");
        status_ret = false;
        API01openCardReader(0x01|0x02|0x04, true, false, 60000, getNewCardReaderListener());
        for(int i=0; i<=60; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(status_ret)
            {
                API03closeCardReader();
                return;
            }
        }
        return;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_closeCardReader_CN003",
            resDialogTitle = "log无读卡信息", APIName = "closeCardReader")
    public void CASE_closeCardReader_CN003() {
        Log.i(TAG, "CASE_closeCardReader_CN003");
        API03closeCardReader();
        return;
    }
}
