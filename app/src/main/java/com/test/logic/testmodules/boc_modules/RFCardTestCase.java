package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.rfcard.AidlRFCard;
import com.boc.aidl.rfcard.PowerOnRFResult;
import com.boc.aidl.rfcard.RFCardConst;
import com.socsi.utils.StringUtil;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.util.ToastUtil;

/**
 * Created by InovaF1 on 2017/6/30.
 */

public class RFCardTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";
    public PowerOnRFResult API01powerOn(int rfCardType, int timeout) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlRFCard.Stub.asInterface(handle.getRFCard()).powerOn(rfCardType, timeout);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public byte[] API02call(byte[] req, long timeout) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlRFCard.Stub.asInterface(handle.getRFCard()).call(req, timeout);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }
    public void API03powerOff(int timeout) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlRFCard.Stub.asInterface(handle.getRFCard()).powerOff(timeout);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void API04authenticateByExtendKey(int rfKeyMode, byte[] SNR, int blockNo, byte[] key) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlRFCard.Stub.asInterface(handle.getRFCard()).authenticateByExtendKey(rfKeyMode, SNR, blockNo, key);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void API05writeDataBlock(int blockNo, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlRFCard.Stub.asInterface(handle.getRFCard()).writeDataBlock(blockNo, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public byte[] API06readDataBlock(int blockNo) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlRFCard.Stub.asInterface(handle.getRFCard()).readDataBlock(blockNo);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void API07incrementOperation(int blockNo, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlRFCard.Stub.asInterface(handle.getRFCard()).incrementOperation(blockNo, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void API08decrementOperation(int blockNo, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlRFCard.Stub.asInterface(handle.getRFCard()).decrementOperation(blockNo, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private void showResult(PowerOnRFResult resultInfo) {
        if (resultInfo != null) {
            Log.i(TAG, "rfcardType=" + resultInfo.getRfcardType());
            Log.i(TAG, "cardSerialNo=" + StringUtil.byte2HexStr(resultInfo.getCardSerialNo()));
            Log.i(TAG, "atqa=" + StringUtil.byte2HexStr(resultInfo.getAtqa()));
            ToastUtil.showLong(
                    "CardType:" + resultInfo.getRfcardType() +"\n"+
                    "CardSerialNo:" + StringUtil.byte2HexStr(resultInfo.getCardSerialNo()) +"\n"+
                    "Atqa:" + StringUtil.byte2HexStr(resultInfo.getAtqa()));
        }
    }

    /**
     * 非接上电
     * @return
     */
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN001",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN001() {
        Log.i(TAG, "CASE_powerOn_CN001");
        PowerOnRFResult resultInfo = null;
        resultInfo = API01powerOn(1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN002",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN002() {
        Log.i(TAG, "CASE_powerOn_CN002");

        PowerOnRFResult resultInfo = API01powerOn(2,60);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN003",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN003() {
        Log.i(TAG, "CASE_powerOn_CN003");

        PowerOnRFResult resultInfo = API01powerOn(4,60);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN004",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN004() {
        Log.i(TAG, "CASE_powerOn_CN004");

        PowerOnRFResult resultInfo = API01powerOn(3,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN005",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN005() {
        Log.i(TAG, "CASE_powerOn_CN005");

        PowerOnRFResult resultInfo = API01powerOn(7,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN006",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN006() {
        Log.i(TAG, "CASE_powerOn_CN006");

        PowerOnRFResult resultInfo = API01powerOn(0,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN007",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN007() {
        Log.i(TAG, "CASE_powerOn_CN007");

        PowerOnRFResult resultInfo = API01powerOn(-1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN008",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN008() {
        Log.i(TAG, "CASE_powerOn_CN008");

        PowerOnRFResult resultInfo = API01powerOn(Integer.MAX_VALUE,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN009",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN009() {
        Log.i(TAG, "CASE_powerOn_CN009");

        PowerOnRFResult resultInfo = API01powerOn(Integer.MAX_VALUE+1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN010",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN010() {
        Log.i(TAG, "CASE_powerOn_CN010");

        PowerOnRFResult resultInfo = API01powerOn(Integer.MIN_VALUE,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN011",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN011() {
        Log.i(TAG, "CASE_powerOn_CN011");

        PowerOnRFResult resultInfo = API01powerOn(Integer.MIN_VALUE-1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
        return (resultInfo==null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN012",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN012() {
        Log.i(TAG, "CASE_powerOn_CN012");

        PowerOnRFResult resultInfo = API01powerOn(1,0);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN013",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN013() {
        Log.i(TAG, "CASE_powerOn_CN013");

        PowerOnRFResult resultInfo = API01powerOn(1,-1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN014",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN014() {
        Log.i(TAG, "CASE_powerOn_CN014");

        PowerOnRFResult resultInfo = API01powerOn(1,Integer.MAX_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN015",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN015() {
        Log.i(TAG, "CASE_powerOn_CN015");

        PowerOnRFResult resultInfo = API01powerOn(1,Integer.MAX_VALUE+1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN016",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN016() {
        Log.i(TAG, "CASE_powerOn_CN016");

        PowerOnRFResult resultInfo = API01powerOn(1,Integer.MIN_VALUE);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN017",
            resDialogTitle = "查看log", APIName = "powerOn")
    public boolean CASE_powerOn_CN017() {
        Log.i(TAG, "CASE_powerOn_CN017");

        PowerOnRFResult resultInfo = API01powerOn(1,Integer.MIN_VALUE-1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }
        return false;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN018",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN018() {
        Log.i(TAG, "CASE_powerOn_CN018");

        PowerOnRFResult resultInfo = API01powerOn(1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN019",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN019() {
        Log.i(TAG, "CASE_powerOn_CN019");

        PowerOnRFResult resultInfo = API01powerOn(2,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN020",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN020() {
        Log.i(TAG, "CASE_powerOn_CN020");

        PowerOnRFResult resultInfo = API01powerOn(4,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN021",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN021() {
        Log.i(TAG, "CASE_powerOn_CN021");

        PowerOnRFResult resultInfo = API01powerOn(2,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN022",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN022() {
        Log.i(TAG, "CASE_powerOn_CN022");

        PowerOnRFResult resultInfo = API01powerOn(4,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOn_CN023",
            resDialogTitle = "查看log", APIName = "powerOn")
    public void CASE_powerOn_CN023() {
        Log.i(TAG, "CASE_powerOn_CN023");

        PowerOnRFResult resultInfo = API01powerOn(1,60);

        if (resultInfo != null) {
            showResult(resultInfo);
        }
    }*/

    /**
     * 非接通讯
     */
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN001",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN001() {
        Log.i(TAG, "CASE_call_CN001");
        final PowerOnRFResult powerOnRFResult = API01powerOn(1, 60);
        String callData = "00A404000E325041592E5359532E4444463031";
        if(powerOnRFResult != null){
            byte[] data =API02call(StringUtil.hexStr2Bytes(callData), 60);
            if (data != null) {
                ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
            }
        }
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN002",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN002() {
        Log.i(TAG, "CASE_call_CN002");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00}, 60);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN003",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN003() {
        Log.i(TAG, "CASE_call_CN003");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00, 0x04}, 60);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN004",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN004() {
        Log.i(TAG, "CASE_call_CN004");

        byte[] data =API02call(new byte[]{}, 60);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN005",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN005() {
        Log.i(TAG, "CASE_call_CN005");

        byte[] data =API02call(null, 60);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN006",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN006() {
        Log.i(TAG, "CASE_call_CN006");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, 0);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN007",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN007() {
        Log.i(TAG, "CASE_call_CN007");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, -1);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN008",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN008() {
        Log.i(TAG, "CASE_call_CN008");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, Integer.MAX_VALUE);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN009",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN009() {
        Log.i(TAG, "CASE_call_CN009");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, Integer.MAX_VALUE+1);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN010",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN010() {
        Log.i(TAG, "CASE_call_CN010");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, Integer.MIN_VALUE);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_call_CN011",
            resDialogTitle = "查看log", APIName = "call")
    public void CASE_call_CN011() {
        Log.i(TAG, "CASE_call_CN011");

        byte[] data =API02call(new byte[]{0x00, (byte) 0x84, 0x00, 0x00}, Integer.MIN_VALUE-1);

        if (data != null) {
            ToastUtil.showLong("call return: " + StringUtil.byte2HexStr(data));
        }
    }*/

    /**
     * 非接下电
     */
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN001",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN001() {
        Log.i(TAG, "CASE_powerOff_CN001");

        API03powerOff(60000);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN002",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN002() {
        Log.i(TAG, "CASE_powerOff_CN002");

        API03powerOff(60000);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN003",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN003() {
        Log.i(TAG, "CASE_powerOff_CN003");

        API03powerOff(60000);
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN004",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN004() {
        Log.i(TAG, "CASE_powerOff_CN004");
        CASE_powerOn_CN001();
        API03powerOff(0);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN005",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN005() {
        Log.i(TAG, "CASE_powerOff_CN005");
        CASE_powerOn_CN001();
        API03powerOff(-1);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN006",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN006() {
        Log.i(TAG, "CASE_powerOff_CN006");
        CASE_powerOn_CN001();
        API03powerOff(Integer.MAX_VALUE);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN007",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN007() {
        Log.i(TAG, "CASE_powerOff_CN007");
        CASE_powerOn_CN001();
        API03powerOff(Integer.MAX_VALUE+1);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN008",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN008() {
        Log.i(TAG, "CASE_powerOff_CN008");
        CASE_powerOn_CN001();
        API03powerOff(Integer.MIN_VALUE);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN009",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN009() {
        Log.i(TAG, "CASE_powerOff_CN009");
        CASE_powerOn_CN001();
        API03powerOff(Integer.MIN_VALUE-1);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN010",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN010() {
        Log.i(TAG, "CASE_powerOff_CN010");
        CASE_powerOn_CN001();
        API03powerOff(60000);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_powerOff_CN011",
            resDialogTitle = "", APIName = "powerOff")
    public void CASE_powerOff_CN011() {
        Log.i(TAG, "CASE_powerOff_CN011");
        CASE_powerOn_CN001();
        API03powerOff(60000);
    }*/

    /**
     * 外部密钥认证
     */

//    String key = "ffffffffffff";
    String key = "123456789010";
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN001",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN001() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN001");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null)
        {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(),0, StringUtil.hexStr2Bytes(key));
        }

    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN002",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN002() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN002");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null) {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN003",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN003() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN003");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null) {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(), 2, StringUtil.hexStr2Bytes(key));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN004",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN004() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN004");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null) {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(), 63, StringUtil.hexStr2Bytes("000008F708F7"));
        }
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN005",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN005() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN005");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(-1, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN006",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN006() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN006");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(4, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN007",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN007() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN007");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, new byte[]{}, 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN008",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN008() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN008");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, null, 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN009",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN009() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN009");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, new byte[]{0,0,0,0,0,0,0,0}, 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN010",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN010() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN010");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 64, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN011",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN011() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN011");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), -1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN012",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN012() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN012");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), Integer.MAX_VALUE, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN013",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN013() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN013");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), Integer.MAX_VALUE+1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN014",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN014() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN014");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), Integer.MIN_VALUE, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN015",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN015() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN015");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), Integer.MIN_VALUE-1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN016",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN016() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN016");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, new byte[]{0,0,0,0,0,0});
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN017",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN017() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN017");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, new byte[]{});
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN018",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN018() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN018");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN019",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN019() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN019");
        PowerOnRFResult resultInfo = API01powerOn(1,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN020",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN020() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN020");
        PowerOnRFResult resultInfo = API01powerOn(2,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_authenticateByExtendKey_CN021",
            resDialogTitle = "", APIName = "authenticateByExtendKey")
    public void CASE_authenticateByExtendKey_CN021() {
        Log.i(TAG, "CASE_authenticateByExtendKey_CN021");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
    }*/

    /**
     * 写块操作
     */
    String data = "000000000000FF078069123456789010";
//    String data = "01000000FEFFFFFF0100000008F708F7";
    // 15个字节
    String data1 = "01000000FEFFFFFF0100000008F708";
    // 17个字节
    String data2 = "01000000FEFFFFFF0100000008F708F7FF";
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN001",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN001() {
        Log.i(TAG, "CASE_writeDataBlock_CN001");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(0, StringUtil.hexStr2Bytes(data));
        byte[] blockData = API06readDataBlock(0);
        if(blockData != null)
        {
            ToastUtil.showLong("blockData:" +"\n" + StringUtil.byte2HexStr(blockData));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN002",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN002() {
        Log.i(TAG, "CASE_writeDataBlock_CN002");
        CASE_authenticateByExtendKey_CN002();
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data));
        byte[] blockData = API06readDataBlock(1);
        if(blockData != null)
        {
            ToastUtil.showLong("blockData:" +"\n" + StringUtil.byte2HexStr(blockData));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN003",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN003() {
        Log.i(TAG, "CASE_writeDataBlock_CN003");
        CASE_authenticateByExtendKey_CN003();
        API05writeDataBlock(2, StringUtil.hexStr2Bytes(data));
        byte[] blockData = API06readDataBlock(2);
        if(blockData != null)
        {
            ToastUtil.showLong("blockData:" +"\n" + StringUtil.byte2HexStr(blockData));
        }
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN004",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN004() {
        Log.i(TAG, "CASE_writeDataBlock_CN004");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null) {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(), 63, StringUtil.hexStr2Bytes("FFFFFFFFFFFF"));
        }
        API05writeDataBlock(63, StringUtil.hexStr2Bytes("000000000000FF078069FFFFFFFFFFFF"));
        byte[] blockData = API06readDataBlock(63);
        if(blockData != null)
        {
            ToastUtil.showLong("blockData:" +"\n" + StringUtil.byte2HexStr(blockData));
        }
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN005",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN005() {
        Log.i(TAG, "CASE_writeDataBlock_CN005");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(-1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN006",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN006() {
        Log.i(TAG, "CASE_writeDataBlock_CN006");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(64, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN007",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN007() {
        Log.i(TAG, "CASE_writeDataBlock_CN007");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(Integer.MAX_VALUE, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN008",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN008() {
        Log.i(TAG, "CASE_writeDataBlock_CN008");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(Integer.MAX_VALUE+1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN009",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN009() {
        Log.i(TAG, "CASE_writeDataBlock_CN009");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(Integer.MIN_VALUE, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN010",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN010() {
        Log.i(TAG, "CASE_writeDataBlock_CN010");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(Integer.MIN_VALUE-1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN011",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN011() {
        Log.i(TAG, "CASE_writeDataBlock_CN011");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(1,StringUtil.hexStr2Bytes(data1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN012",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN012() {
        Log.i(TAG, "CASE_writeDataBlock_CN012");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data2));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN013",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN013() {
        Log.i(TAG, "CASE_writeDataBlock_CN013");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(1, new byte[]{});
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN014",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN014() {
        Log.i(TAG, "CASE_writeDataBlock_CN014");
        CASE_authenticateByExtendKey_CN001();
        API05writeDataBlock(1, null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN015",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN015() {
        Log.i(TAG, "CASE_writeDataBlock_CN015");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes(key));
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN016",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN016() {
        Log.i(TAG, "CASE_writeDataBlock_CN016");
        PowerOnRFResult resultInfo = API01powerOn(1,60);
        byte[] key_A=new byte[]{1,2,3,4,5,6};
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 0, key_A);
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN017",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN017() {
        Log.i(TAG, "CASE_writeDataBlock_CN017");
        PowerOnRFResult resultInfo = API01powerOn(2,60);
        byte[] key_B=new byte[]{1,2,3,4,5,6};
        API04authenticateByExtendKey(0, resultInfo.getCardSerialNo(), 0, key_B);
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_writeDataBlock_CN018",
            resDialogTitle = "", APIName = "writeDataBlock")
    public void CASE_writeDataBlock_CN018() {
        Log.i(TAG, "CASE_writeDataBlock_CN018");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes(key));
        API05writeDataBlock(1, StringUtil.hexStr2Bytes(data));
    }*/

    /**
     * 读块操作
     */
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN001",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN001() {
        Log.i(TAG, "CASE_readDataBlock_CN001");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(),0, StringUtil.hexStr2Bytes(key));
        ToastUtil.showLong("readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(0)));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN002",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN002() {
        Log.i(TAG, "CASE_readDataBlock_CN002");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
//        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
//        API05writeDataBlock(1,StringUtil.hexStr2Bytes(data));
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        ToastUtil.showLong("readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(1)));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN003",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN003() {
        Log.i(TAG, "CASE_readDataBlock_CN003");

        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null) {
            API04authenticateByExtendKey(RFCardConst.RFKeyMode.KEYB_0X01, resultInfo.getCardSerialNo(), 63, StringUtil.hexStr2Bytes("FFFFFFFFFFFF"));
        }
        ToastUtil.showLong("readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(63)));
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN004",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN004() {
        Log.i(TAG, "CASE_readDataBlock_CN004");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(-1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN005",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN005() {
        Log.i(TAG, "CASE_readDataBlock_CN005");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(64));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN006",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN006() {
        Log.i(TAG, "CASE_readDataBlock_CN006");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API05writeDataBlock(1,StringUtil.hexStr2Bytes(data));
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN007",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN007() {
        Log.i(TAG, "CASE_readDataBlock_CN007");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN008",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN008() {
        Log.i(TAG, "CASE_readDataBlock_CN008");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN009",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN009() {
        Log.i(TAG, "CASE_readDataBlock_CN009");
        PowerOnRFResult resultInfo = API01powerOn(1,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(1));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_readDataBlock_CN010",
            resDialogTitle = "查看log", APIName = "readDataBlock")
    public void CASE_readDataBlock_CN010() {
        Log.i(TAG, "CASE_readDataBlock_CN010");
        PowerOnRFResult resultInfo = API01powerOn(2,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));      ;
        ToastUtil.showLong("readDataBlock: " + API06readDataBlock(1));
    }*/

    /**
     * 增量操作
     */
    String incrementData = "01000000";
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN001",
            resDialogTitle = "查看log", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN001() {
        Log.i(TAG, "CASE_incrementOperation_CN001");

        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null)
        {

            API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
            ToastUtil.showLong("readDataBlock1: " + StringUtil.byte2HexStr(API06readDataBlock(1)));

            API05writeDataBlock(4,StringUtil.hexStr2Bytes("01000000FEFFFFFF0100000008F708F7"));
            ToastUtil.showLong("readDataBlock2: " + StringUtil.byte2HexStr(API06readDataBlock(1)));

            API07incrementOperation(1, StringUtil.hexStr2Bytes(incrementData));
            ToastUtil.showLong("After increment readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(1)));
        }


    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN002",
            resDialogTitle = "查看log", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN002() {
        Log.i(TAG, "CASE_incrementOperation_CN002");
        PowerOnRFResult resultInfo = API01powerOn(2,60);
        if(resultInfo != null)
        {

            API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 4, StringUtil.hexStr2Bytes(key));
            ToastUtil.showLong("readDataBlock1: " + StringUtil.byte2HexStr(API06readDataBlock(4)));
            API05writeDataBlock(4,StringUtil.hexStr2Bytes("01000000FEFFFFFF0100000008F708F7"));
            ToastUtil.showLong("readDataBlock2: " + StringUtil.byte2HexStr(API06readDataBlock(4)));
            API07incrementOperation(4, StringUtil.hexStr2Bytes(incrementData));
            ToastUtil.showLong("After increment, readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(4)));
        }

    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN003",
            resDialogTitle = "查看log", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN003() {
        Log.i(TAG, "CASE_incrementOperation_CN003");
        CASE_readDataBlock_CN001();
        API07incrementOperation(0,StringUtil.hexStr2Bytes(incrementData));
        PowerOnRFResult resultInfo = API01powerOn(2,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes(key));
        ToastUtil.showLong("After increment, readDataBlock: " + API06readDataBlock(0));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN004",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN004() {
        Log.i(TAG, "CASE_incrementOperation_CN004");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), -1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(-1, StringUtil.hexStr2Bytes(incrementData));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN005",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN005() {
        Log.i(TAG, "CASE_incrementOperation_CN005");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 64, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(64, StringUtil.hexStr2Bytes(incrementData));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN006",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN006() {
        Log.i(TAG, "CASE_incrementOperation_CN006");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, StringUtil.hexStr2Bytes("010000"));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN007",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN007() {
        Log.i(TAG, "CASE_incrementOperation_CN007");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, StringUtil.hexStr2Bytes("0100000000"));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN008",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN008() {
        Log.i(TAG, "CASE_incrementOperation_CN008");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, new byte[]{});
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_incrementOperation_CN009",
            resDialogTitle = "", APIName = "incrementOperation")
    public void CASE_incrementOperation_CN009() {
        Log.i(TAG, "CASE_incrementOperation_CN009");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, null);
    }*/

    /**
     * 减量操作
     */
    String srcData = "02000000FDFFFFFF0200000008F708F7";
    String decrementData = "01000000";
    /* result = "01000000FEFFFFFF0100000008F708F7";*/
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN001",
            resDialogTitle = "查看log", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN001() {
        Log.i(TAG, "CASE_decrementOperation_CN001");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null)
        {
            API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes("123456789010"));
            API05writeDataBlock(1,StringUtil.hexStr2Bytes(srcData));
            ToastUtil.showLong("readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(1)));
            API08decrementOperation(1, StringUtil.hexStr2Bytes(decrementData));
            ToastUtil.showLong("After decrement, readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(1)));
        }

    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN002",
            resDialogTitle = "查看log", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN002() {
        Log.i(TAG, "CASE_decrementOperation_CN002");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        if(resultInfo != null)
        {
            API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 4, StringUtil.hexStr2Bytes("123456789010"));
            API05writeDataBlock(4,StringUtil.hexStr2Bytes(srcData));
            ToastUtil.showLong("readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(4)));
            API08decrementOperation(4, StringUtil.hexStr2Bytes(decrementData));
            ToastUtil.showLong("After decrement, readDataBlock: " + StringUtil.byte2HexStr(API06readDataBlock(4)));
        }
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN003",
            resDialogTitle = "查看log", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN003() {
        Log.i(TAG, "CASE_decrementOperation_CN003");
        CASE_readDataBlock_CN001();
        API07incrementOperation(0, StringUtil.hexStr2Bytes(decrementData));
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 0, StringUtil.hexStr2Bytes("123456789010"));
        ToastUtil.showLong("After decrement, readDataBlock: " + API06readDataBlock(0));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN004",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN004() {
        Log.i(TAG, "CASE_decrementOperation_CN004");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), -1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(-1, StringUtil.hexStr2Bytes(decrementData));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN005",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN005() {
        Log.i(TAG, "CASE_decrementOperation_CN005");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 64, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(64, StringUtil.hexStr2Bytes(decrementData));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN006",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN006() {
        Log.i(TAG, "CASE_decrementOperation_CN006");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, StringUtil.hexStr2Bytes("010000"));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN007",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN007() {
        Log.i(TAG, "CASE_decrementOperation_CN007");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, StringUtil.hexStr2Bytes("0100000000"));
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN008",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN008() {
        Log.i(TAG, "CASE_decrementOperation_CN008");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, new byte[]{});
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_decrementOperation_CN009",
            resDialogTitle = "", APIName = "decrementOperation")
    public void CASE_decrementOperation_CN009() {
        Log.i(TAG, "CASE_decrementOperation_CN009");
        PowerOnRFResult resultInfo = API01powerOn(4,60);
        API04authenticateByExtendKey(3, resultInfo.getCardSerialNo(), 1, StringUtil.hexStr2Bytes(key));
        API07incrementOperation(1, null);
    }*/
}
