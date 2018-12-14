package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.iccard.AidlICCard;
import com.socsi.utils.StringUtil;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/16.
 */

public class ICCardTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";

    public byte[] API01powerOn(int icCardSlot, int icCardType) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlICCard.Stub.asInterface(handle.getICCard()).powerOn(icCardSlot, icCardType);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public byte[] API02call(int icCardSlot, int icCardType,byte[] data, int timeout) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlICCard.Stub.asInterface(handle.getICCard()).call(icCardSlot, icCardType, data, timeout);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public void API03powerOff(int icCardSlot, int icCardType) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlICCard.Stub.asInterface(handle.getICCard()).powerOff(icCardSlot, icCardType);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean API04checkSlotsState(int icCardSlot) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlICCard.Stub.asInterface(handle.getICCard()).checkSlotsState(icCardSlot);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showResult(String tag, String resultInfo) {
        if (resultInfo != null) {
            Log.i(TAG, tag + resultInfo);
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN001",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN001() {
        Log.i(TAG, "CASE_powerOn_CN001");

        byte[] data = API01powerOn(0,0);

        if (data != null) {
            ToastUtil.showLong("powerOn return:" + "\n" +  StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN002",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN002() {
        Log.i(TAG, "CASE_powerOn_CN002");

        byte[] data = API01powerOn(0,1);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN003",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN003() {
        Log.i(TAG, "CASE_powerOn_CN003");

        byte[] data = API01powerOn(0,2);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN004",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN004() {
        Log.i(TAG, "CASE_powerOn_CN004");

        byte[] data = API01powerOn(0,3);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN005",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN005() {
        Log.i(TAG, "CASE_powerOn_CN005");

        byte[] data = API01powerOn(0,4);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN006",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN006() {
        Log.i(TAG, "CASE_powerOn_CN006");

        byte[] data = API01powerOn(0,5);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN007",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN007() {
        Log.i(TAG, "CASE_powerOn_CN007");

        byte[] data = API01powerOn(0,6);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN008",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN008() {
        Log.i(TAG, "CASE_powerOn_CN008");

        byte[] data = API01powerOn(0,7);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN009",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN009() {
        Log.i(TAG, "CASE_powerOn_CN009");

        byte[] data = API01powerOn(0,8);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN010",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN010() {
        Log.i(TAG, "CASE_powerOn_CN010");

        byte[] data = API01powerOn(1,0);

        if (data != null) {
            ToastUtil.showLong("powerOn return: " + "\n" + StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN011",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN011() {
        Log.i(TAG, "CASE_powerOn_CN011");

        byte[] data = API01powerOn(-1,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN012",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN012() {
        Log.i(TAG, "CASE_powerOn_CN012");

        byte[] data = API01powerOn(2,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN013",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN013() {
        Log.i(TAG, "CASE_powerOn_CN013");

        byte[] data = API01powerOn(0,-1);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN014",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN014() {
        Log.i(TAG, "CASE_powerOn_CN014");

        byte[] data = API01powerOn(1,9);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN015",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN015() {
        Log.i(TAG, "CASE_powerOn_CN015");

        byte[] data = API01powerOn(0,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN016",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN016() {
        Log.i(TAG, "CASE_powerOn_CN016");

        byte[] data = API01powerOn(0,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN017",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN017() {
        Log.i(TAG, "CASE_powerOn_CN017");

        byte[] data = API01powerOn(1,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN018",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN018() {
        Log.i(TAG, "CASE_powerOn_CN018");

        byte[] data = API01powerOn(0,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN019",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN019() {
        Log.i(TAG, "CASE_powerOn_CN019");

        byte[] data = API01powerOn(1,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN020",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN020() {
        Log.i(TAG, "CASE_powerOn_CN020");

        byte[] data = API01powerOn(0,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN021",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN021() {
        Log.i(TAG, "CASE_powerOn_CN021");

        byte[] data = API01powerOn(1,0);

        if (data != null) {
            showResult("powerOn return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }*/

    /**
     * @data    select file 指令
     * @return  6F 32 84 0E 31 50 41 59 2E 53 59 53 2E 44 44 46 30 31 A5 20 88 01 01 5F 2D 04 7A 68 65 6E 9F 11 01 01 BF 0C 0F D1 02 31 32 C2 04 49 43 42 43 9F 4D 02 0B 0A 90 00
     * 常见返回码：
     * 6D00 ：出错
     * 6A82 ：出错 该文件未找到
     */
    static String data = "00A404000E315041592E5359532E4444463031FF";
    static final byte APDU[] =StringUtil.hexStringToByte(data);

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN001",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN001() {
        Log.i(TAG, "CASE_call_CN001");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 0, APDU, 60);

        if (data != null && data.length != 0) {
            ToastUtil.showLong("call return: "+ "\n" + StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN002",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN002() {
        Log.i(TAG, "CASE_call_CN002");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 1, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN003",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN003() {
        Log.i(TAG, "CASE_call_CN003");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 2, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN004",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN004() {
        Log.i(TAG, "CASE_call_CN004");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 3, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN005",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN005() {
        Log.i(TAG, "CASE_call_CN005");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 4, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN006",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN006() {
        Log.i(TAG, "CASE_call_CN006");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 5, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN007",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN007() {
        Log.i(TAG, "CASE_call_CN007");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 6, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN008",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN008() {
        Log.i(TAG, "CASE_call_CN008");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 7, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN009",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN009() {
        Log.i(TAG, "CASE_call_CN009");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 8, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN010",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN010() {
        Log.i(TAG, "CASE_call_CN010");
        CASE_powerOn_CN010();
        byte[] data =API02call(1, 0, APDU, 60);

        if (data != null && data.length != 0) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN011",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN011() {
        Log.i(TAG, "CASE_call_CN011");
        CASE_powerOn_CN001();
        byte[] data =API02call(-1, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN012",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN012() {
        Log.i(TAG, "CASE_call_CN012");
        CASE_powerOn_CN001();
        byte[] data =API02call(2, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN013",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN013() {
        Log.i(TAG, "CASE_call_CN013");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, -1, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN014",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN014() {
        Log.i(TAG, "CASE_call_CN014");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 9, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN015",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN015() {
        Log.i(TAG, "CASE_call_CN015");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 9, new byte[]{0x00,(byte)0xA4,0x04,0x00,0x0E,0x11,0x22,0x33}, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN016",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN016() {
        Log.i(TAG, "CASE_call_CN016");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 9, new byte[]{0x00,(byte)0xA4,0x04,0x00}, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN017",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN017() {
        Log.i(TAG, "CASE_call_CN017");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 9, new byte[] {}, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN018",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN018() {
        Log.i(TAG, "CASE_call_CN018");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 9, null, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN019",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN019() {
        Log.i(TAG, "CASE_call_CN019");
        CASE_powerOn_CN001();
        byte[] data =API02call(0,0, APDU, 0);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN020",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN020() {
        Log.i(TAG, "CASE_call_CN020");
        CASE_powerOn_CN001();
        byte[] data =API02call(0,0, APDU, -1);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN021",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN021() {
        Log.i(TAG, "CASE_call_CN021");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN022",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN022() {
        Log.i(TAG, "CASE_call_CN022");
        CASE_powerOn_CN001();
        byte[] data =API02call(1, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN023",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN023() {
        Log.i(TAG, "CASE_call_CN023");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN024",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN024() {
        Log.i(TAG, "CASE_call_CN024");
        CASE_powerOn_CN001();
        byte[] data =API02call(1, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN025",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN025() {
        Log.i(TAG, "CASE_call_CN025");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN026",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN026() {
        Log.i(TAG, "CASE_call_CN026");
        CASE_powerOn_CN001();
        byte[] data =API02call(1, 0, APDU, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN027",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN027() {
        Log.i(TAG, "CASE_call_CN027");
        CASE_powerOn_CN001();
        byte[] data =API02call(0, 0, new byte[]{1,2,3}, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN028",
            resDialogTitle = "查看log", APIName = "call")
    public boolean CASE_call_CN028() {
        Log.i(TAG, "CASE_call_CN028");
        CASE_powerOn_CN001();
        byte[] data =API02call(1, 0, new byte[]{1,2,3}, 60);

        if (data != null) {
            showResult("call return: ", StringUtil.byte2HexStr(data));
            return true;
        }
        return false;
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN001",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN001() {
        Log.i(TAG, "CASE_powerOff_CN001");
//        CASE_powerOn_CN001();
        API03powerOff(0, 0);
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN002",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN002() {
        Log.i(TAG, "CASE_powerOff_CN002");

        API03powerOff(0, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN003",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN003() {
        Log.i(TAG, "CASE_powerOff_CN003");

        API03powerOff(0, 2);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN004",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN004() {
        Log.i(TAG, "CASE_powerOff_CN004");

        API03powerOff(0, 3);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN005",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN005() {
        Log.i(TAG, "CASE_powerOff_CN005");

        API03powerOff(0, 4);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN006",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN006() {
        Log.i(TAG, "CASE_powerOff_CN006");

        API03powerOff(0, 5);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN007",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN007() {
        Log.i(TAG, "CASE_powerOff_CN007");

        API03powerOff(0, 6);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN008",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN008() {
        Log.i(TAG, "CASE_powerOff_CN008");

        API03powerOff(0, 7);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN009",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN009() {
        Log.i(TAG, "CASE_powerOff_CN009");

        API03powerOff(0, 8);
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN010",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN010() {
        Log.i(TAG, "CASE_powerOff_CN010");

        API03powerOff(1, 0);
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN011",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN011() {
        Log.i(TAG, "CASE_powerOff_CN011");

        API03powerOff(-1, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN012",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN012() {
        Log.i(TAG, "CASE_powerOff_CN012");

        API03powerOff(2, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN013",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN013() {
        Log.i(TAG, "CASE_powerOff_CN013");

        API03powerOff(0, -1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN014",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN014() {
        Log.i(TAG, "CASE_powerOff_CN014");

        API03powerOff(1, 9);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN015",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN015() {
        Log.i(TAG, "CASE_powerOff_CN015");

        API03powerOff(0, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN016",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN016() {
        Log.i(TAG, "CASE_powerOff_CN016");

        API03powerOff(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN017",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN017() {
        Log.i(TAG, "CASE_powerOff_CN017");

        API03powerOff(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN018",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN018() {
        Log.i(TAG, "CASE_powerOff_CN018");

        API03powerOff(Integer.MAX_VALUE+1, Integer.MAX_VALUE+1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN019",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN019() {
        Log.i(TAG, "CASE_powerOff_CN019");

        API03powerOff(Integer.MIN_VALUE-1, Integer.MIN_VALUE-1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN020",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN020() {
        Log.i(TAG, "CASE_powerOff_CN020");

        API03powerOff(0, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN021",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN021() {
        Log.i(TAG, "CASE_powerOff_CN021");

        API03powerOff(1, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN022",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN022() {
        Log.i(TAG, "CASE_powerOff_CN022");

        API03powerOff(0, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN023",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN023() {
        Log.i(TAG, "CASE_powerOff_CN023");

        API03powerOff(1, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN024",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN024() {
        Log.i(TAG, "CASE_powerOff_CN024");

        API03powerOff(0, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN025",
            resDialogTitle = "是否通过？", APIName = "powerOff")
    public void CASE_powerOff_CN025() {
        Log.i(TAG, "CASE_powerOff_CN025");

        API03powerOff(1, 0);
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN001",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN001() {
        Log.i(TAG, "CASE_checkSlotsState_CN001");
        boolean ret = API04checkSlotsState(0);
        if(ret)
        {
            ToastUtil.showLong("卡已经插入");
        }else{
            ToastUtil.showLong("未插卡");
        }

        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN002",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN002() {
        Log.i(TAG, "CASE_checkSlotsState_CN002");
        boolean ret = API04checkSlotsState(0);
        if(ret)
        {
            ToastUtil.showLong("卡已经插入");
        }else{
            ToastUtil.showLong("未插卡");
        }

        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN003",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN003() {
        Log.i(TAG, "CASE_checkSlotsState_CN003");
        boolean ret = API04checkSlotsState(1);
        if(ret)
        {
            ToastUtil.showLong("卡已经插入");
        }else{
            ToastUtil.showLong("未插卡");
        }

        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN004",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN004() {
        Log.i(TAG, "CASE_checkSlotsState_CN004");
        boolean ret = API04checkSlotsState(1);
        if(ret)
        {
            ToastUtil.showLong("卡已经插入");
        }else{
            ToastUtil.showLong("未插卡");
        }

        return ret;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN005",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN005() {
        Log.i(TAG, "CASE_checkSlotsState_CN005");

        return (API04checkSlotsState(-1)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN006",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN006() {
        Log.i(TAG, "CASE_checkSlotsState_CN006");

        return (API04checkSlotsState(2)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN007",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN007() {
        Log.i(TAG, "CASE_checkSlotsState_CN007");

        return (API04checkSlotsState(Integer.MAX_VALUE)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN008",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN008() {
        Log.i(TAG, "CASE_checkSlotsState_CN008");

        return (API04checkSlotsState(Integer.MIN_VALUE)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN009",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN009() {
        Log.i(TAG, "CASE_checkSlotsState_CN009");

        return (API04checkSlotsState(Integer.MAX_VALUE+1)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN010",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN010() {
        Log.i(TAG, "CASE_checkSlotsState_CN010");

        return (API04checkSlotsState(Integer.MIN_VALUE-1)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN011",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN011() {
        Log.i(TAG, "CASE_checkSlotsState_CN011");

        return (API04checkSlotsState(0)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN012",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN012() {
        Log.i(TAG, "CASE_checkSlotsState_CN012");

        return (API04checkSlotsState(1)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN013",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN013() {
        Log.i(TAG, "CASE_checkSlotsState_CN013");

        return (API04checkSlotsState(0)==false);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_checkSlotsState_CN014",
            resDialogTitle = "是否通过", APIName = "checkSlotsState")
    public boolean CASE_checkSlotsState_CN014() {
        Log.i(TAG, "CASE_checkSlotsState_CN014");

        return (API04checkSlotsState(1)==false);
    }*/
}
