package com.test.logic.testmodules.icbc_modules;

import android.os.RemoteException;

import com.icbc.smartpos.deviceservice.aidl.ExternalSerialConst;
import com.icbc.smartpos.deviceservice.aidl.IExternalSerialPort;
import com.icbc.smartpos.deviceservice.aidl.SerialDataControl;
import com.test.logic.annotations.CaseAttributes;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.icbc_modules.common.DeviceService;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/8/24.
 */

public class ModuleExternalSerial extends BaseModule {
    private final String TAG = "ModuleExternalSerial";
    private IExternalSerialPort aidlExternalSerial;
    private static ModuleExternalSerial instance;

    public ModuleExternalSerial() {
        try {
            aidlExternalSerial = DeviceService.getDeviceService().getExternalSerialPort();
            LogUtil.d(TAG, "aidlExternalSerial=" + aidlExternalSerial == null ? "null" : "not null");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ModuleExternalSerial getInstance() {
        if (instance == null) {
            instance = new ModuleExternalSerial();
        }

        return instance;
    }

    public boolean API01_openSerialPort(int portNum, SerialDataControl dataControl) {
        try {
            if (aidlExternalSerial == null) {
                LogUtil.d(TAG, "aidlExternalSerial is null");
                return false;
            }
            return aidlExternalSerial.openSerialPort(portNum, dataControl);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void API02_closeSerialPort(int portNum) {
        try {
            aidlExternalSerial.closeSerialPort(portNum);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int API03_writeSeaialPort(int portNum, byte[] writeData, int dataLength) {
        try {
            return aidlExternalSerial.writeSerialPort(portNum, writeData, dataLength);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int API04_readSeaialPort(int portNum, byte[] readData, int dataLength) {
        try {
            return aidlExternalSerial.readSerialPort(portNum, readData, dataLength);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int API05_safeWriteSeaialPort(int portNum, byte[] writeData, int Length, long timeoutMs) {
        try {
            return aidlExternalSerial.safeWriteSerialPort(portNum, writeData, Length, timeoutMs);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int API06_safeReadSeaialPort(int portNum, byte[] readData, int Length, long timeoutMs) {
        try {
            return aidlExternalSerial.safeReadSerialPort(portNum, readData, Length, timeoutMs);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int API07_setExtPinpadPortMode(int portMode) {
        try {
            return aidlExternalSerial.setExtPinpadPortMode(portMode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean API08_isExternalConnected() {
        try {
            return aidlExternalSerial.isExternalConnected();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_openSerialPort_CN001() {
        //getDescriptionTv().showInfoInNewLine("打开pinpad口");
        SerialDataControl dataControl = new SerialDataControl(ExternalSerialConst.BD115200, ExternalSerialConst.DATA_8, ExternalSerialConst.DSTOP_1, ExternalSerialConst.DPARITY_N);
        if (API01_openSerialPort(ExternalSerialConst.PORT_PINPAD, dataControl)) {
            return true;
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_openSerialPort_CN002() {
        //getDescriptionTv().showInfoInNewLine("打开RS232口");
        SerialDataControl dataControl = new SerialDataControl(ExternalSerialConst.BD115200, ExternalSerialConst.DATA_8, ExternalSerialConst.DSTOP_1, ExternalSerialConst.DPARITY_N);
        if (API01_openSerialPort(ExternalSerialConst.PORT_RS232, dataControl)) {
            return true;
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_closeSerialPort_CN001() {
        //getDescriptionTv().showInfoInNewLine("关闭pinpad口");
        API02_closeSerialPort(ExternalSerialConst.PORT_PINPAD);
        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_closeSerialPort_CN002() {
        //getDescriptionTv().showInfoInNewLine("关闭RS232口");
        API02_closeSerialPort(ExternalSerialConst.PORT_RS232);
        return true;
    }

    public boolean CASE_writeSerialPort_CN001() {
        String data = "dasfafdasfdsafjljl";
        int ret = API03_writeSeaialPort(ExternalSerialConst.PORT_PINPAD, data.getBytes(), data.length());
        LogUtil.d(TAG, "CASE_writeSerialPort_CN001 ret=" + ret);
        return true;
    }

    public boolean CASE_writeSerialPort_CN002() {
        String data = "dasfafdasfdsafjljl";
        int ret = API03_writeSeaialPort(ExternalSerialConst.PORT_RS232, data.getBytes(), data.length());
        LogUtil.d(TAG, "CASE_writeSerialPort_CN002 ret=" + ret);
        return true;
    }


    public boolean CASE_safeWriteSerialPort_CN001() {
        String data = "dfa3---------------------------------------------fdsa------------------------------------------=============================================================================================================================================================================================================================================================0";
        int ret = API05_safeWriteSeaialPort(ExternalSerialConst.PORT_PINPAD, data.getBytes(), data.length(), 1000);
        LogUtil.d(TAG, "CASE_safeWriteSerialPort_CN001 ret=" + ret);
        return true;
    }

    public boolean CASE_safeWriteSerialPort_CN002() {
        String data = "dfa3---------------------------------------------fdsa------------------------------------------=============================================================================================================================================================================================================================================================0";
        int ret = API05_safeWriteSeaialPort(ExternalSerialConst.PORT_RS232, data.getBytes(), data.length(), 1000);
        LogUtil.d(TAG, "CASE_safeWriteSerialPort_CN002 ret=" + ret);
        return true;
    }

    public boolean CASE_safeWriteSerialPort_CN003() {
        String data = "dfa3---------------------------------------------fdsa------------------------------------------=============================================================================================================================================================================================================================================================0";
        int ret = API05_safeWriteSeaialPort(ExternalSerialConst.PORT_RS232, data.getBytes(), data.length(), 3000);
        LogUtil.d(TAG, "CASE_safeWriteSerialPort_CN003 ret=" + ret);
        return true;
    }

    public boolean CASE_readSerialPort_CN001() {
        byte[] recvData = new byte[1024];
        int ret = API04_readSeaialPort(ExternalSerialConst.PORT_PINPAD, recvData, recvData.length);
        LogUtil.d(TAG, "CASE_readSerialPort_CN001 ret=" + ret);
        LogUtil.d(TAG, "data=" + new String(recvData));

        return true;
    }

    public boolean CASE_safeReadSerialPort_CN001() {
        byte[] recvData = new byte[1024];
        int ret = API06_safeReadSeaialPort(ExternalSerialConst.PORT_PINPAD, recvData, recvData.length, 1000);
        LogUtil.d(TAG, "CASE_safeReadSerialPort_CN001 ret=" + ret);
        LogUtil.d(TAG, "data=" + new String(recvData));
        return true;
    }

    public boolean CASE_safeReadSerialPort_CN002() {
        byte[] recvData = new byte[1024];
        int ret = API06_safeReadSeaialPort(ExternalSerialConst.PORT_PINPAD, recvData, recvData.length, 5000);
        LogUtil.d(TAG, "CASE_safeReadSerialPort_CN002 ret=" + ret);
        LogUtil.d(TAG, "data=" + new String(recvData));
        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_isExternalConnected_CN001() {
        return API08_isExternalConnected();
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_setExtPinpadPortMode_CNTransparent() {
        int ret = API07_setExtPinpadPortMode(ExternalSerialConst.MODE_TRANSPARENT);
        if (ret == ExternalSerialConst.MODE_TRANSPARENT) {
            return true;
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_setExtPinpadPortMode_CNPinpadMode() {
        int ret = API07_setExtPinpadPortMode(ExternalSerialConst.MODE_PP1000V3_PINPAD);
        if (ret == ExternalSerialConst.MODE_PP1000V3_PINPAD) {
            return true;
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_setExtPinpadPortMode_CNCTLSMode() {
        int ret = API07_setExtPinpadPortMode(ExternalSerialConst.MODE_PP1000V3_CTLS);
        if (ret == ExternalSerialConst.MODE_PP1000V3_CTLS) {
            return true;
        }

        return false;
    }
}
