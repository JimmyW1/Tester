package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.cardreader.AidlCardReader;
import com.boc.aidl.cardreader.AidlCardReaderListener;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.swiper.AidlSwiper;
import com.boc.aidl.swiper.SwiperResultInfo;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/16.
 */

public class SwiperTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";


    public SwiperResultInfo API01readPlainResult(int readModel) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlSwiper.Stub.asInterface(handle.getSwiper()).readPlainResult(readModel);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public SwiperResultInfo API02readEncryptResult(int readModel, int wkindex) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlSwiper.Stub.asInterface(handle.getSwiper()).readEncryptResult(readModel, wkindex);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private void showResult(SwiperResultInfo resultInfo) {
        if (resultInfo != null) {
            Log.i(TAG, "swipRslt=" + resultInfo.getSwipRslt());
            Log.i(TAG, "acctNo=" + resultInfo.getAcctNo());
            Log.i(TAG, "firstTrackData=" + resultInfo.getFirstTrackData());
            Log.i(TAG, "secondTrackData=" + resultInfo.getSecondTrackData());
            Log.i(TAG, "thirdTrackData=" + resultInfo.getThirdTrackData());
            Log.i(TAG, "validData=" + resultInfo.getValidDate());
            Log.i(TAG, "serviceCode=" + resultInfo.getServiceCode());
            Log.i(TAG, "ksn=" + resultInfo.getKsn());
            Log.i(TAG, "extInfo=" + resultInfo.getExtInfo());
        }
    }

    boolean status_ret = false;
    AidlCardReaderListener getNewCardReaderListener() {
        return new AidlCardReaderListener.Stub() {
            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "Code=" + errorCode);
                Log.i(TAG, "Description=" + errorDescription);
                status_ret = false;
            }

            @Override
            public void onFindingCard(int cardType) throws RemoteException {
                Log.i(TAG, "Card type=" + cardType);
                status_ret = true;
            }
        };
    }
    public boolean API01openCardReader(int openCardType, boolean isAllowfallback,boolean isMSDChecking, int timeout, AidlCardReaderListener cardReaderListener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlCardReader.Stub.asInterface(handle.getCardReader()).openCardReader(openCardType, isAllowfallback, isMSDChecking, timeout, cardReaderListener);
            }
            for(int i=0; i<=timeout/5000; i++) {
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return status_ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN001",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN001() {
        Log.i(TAG, "CASE_readEncryptResult_CN001");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(1,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN002",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN002() {
        Log.i(TAG, "CASE_readEncryptResult_CN002");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(2,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN003",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN003() {
        Log.i(TAG, "CASE_readEncryptResult_CN003");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(4,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN004",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN004() {
        Log.i(TAG, "CASE_readEncryptResult_CN004");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(3,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN005",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN005() {
        Log.i(TAG, "CASE_readEncryptResult_CN005");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(6,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN006",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN006() {
        Log.i(TAG, "CASE_readEncryptResult_CN006");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(5,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN007",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN007() {
        Log.i(TAG, "CASE_readEncryptResult_CN007");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN008",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN008() {
        Log.i(TAG, "CASE_readEncryptResult_CN008");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(0,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN009",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN009() {
        Log.i(TAG, "CASE_readEncryptResult_CN009");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(8,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN010",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN010() {
        Log.i(TAG, "CASE_readEncryptResult_CN010");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(-1,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN011",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN011() {
        Log.i(TAG, "CASE_readEncryptResult_CN011");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(Integer.MAX_VALUE,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN012",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN012() {
        Log.i(TAG, "CASE_readEncryptResult_CN012");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(Integer.MIN_VALUE,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN013",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN013() {
        Log.i(TAG, "CASE_readEncryptResult_CN013");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,0);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN014",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN014() {
        Log.i(TAG, "CASE_readEncryptResult_CN014");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,101);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN015",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN015() {
        Log.i(TAG, "CASE_readEncryptResult_CN015");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,Integer.MAX_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN016",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN016() {
        Log.i(TAG, "CASE_readEncryptResult_CN016");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,Integer.MIN_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readEncryptResult_CN017",
            resDialogTitle = "log查看磁道数据", APIName = "readEncryptResult")
    public boolean CASE_readEncryptResult_CN017() {
        Log.i(TAG, "CASE_readEncryptResult_CN017");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API02readEncryptResult(7,1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN001",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN001() {
        Log.i(TAG, "CASE_readPlainResult_CN001");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(1);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN002",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN002() {
        Log.i(TAG, "CASE_readPlainResult_CN002");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(2);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN003",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN003() {
        Log.i(TAG, "CASE_readPlainResult_CN003");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(4);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN004",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN004() {
        Log.i(TAG, "CASE_readPlainResult_CN004");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(3);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN005",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN005() {
        Log.i(TAG, "CASE_readPlainResult_CN005");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(6);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN006",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN006() {
        Log.i(TAG, "CASE_readPlainResult_CN006");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(5);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN007",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN007() {
        Log.i(TAG, "CASE_readPlainResult_CN007");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(7);
        Log.i(TAG,"resultInfo:" + resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            ToastUtil.showLong("firstTrackData=" + resultInfo.getFirstTrackData());
            ToastUtil.showLong("secondTrackData=" + resultInfo.getSecondTrackData());
            ToastUtil.showLong("thirdTrackData=" + resultInfo.getThirdTrackData());
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN008",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN008() {
        Log.i(TAG, "CASE_readPlainResult_CN008");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(0);
        Log.i(TAG, "resultInfo："+ resultInfo);
        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN009",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN009() {
        Log.i(TAG, "CASE_readPlainResult_CN009");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(8);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN010",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN010() {
        Log.i(TAG, "CASE_readPlainResult_CN010");

        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(-1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN011",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN011() {
        Log.i(TAG, "CASE_readPlainResult_CN010");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(Integer.MAX_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN012",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN012() {
        Log.i(TAG, "CASE_readPlainResult_CN010");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(Integer.MIN_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN013",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN013() {
        Log.i(TAG, "CASE_readPlainResult_CN010");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(Integer.MAX_VALUE + 1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readPlainResult_CN014",
            resDialogTitle = "log查看磁道数据", APIName = "readPlainResult")
    public boolean CASE_readPlainResult_CN014() {
        Log.i(TAG, "CASE_readPlainResult_CN010");
        API01openCardReader(0x01, false, true, 60000, getNewCardReaderListener());
        SwiperResultInfo resultInfo = API01readPlainResult(Integer.MIN_VALUE - 1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }*/
}
