package com.test.logic.testmodules.vfi_modules;

import android.os.Bundle;
import android.os.RemoteException;

import com.test.logic.annotations.CaseAttributes;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.vfi_modules.common.DeviceService;
import com.test.util.LogUtil;
import com.test.util.StringUtil;
import com.vfi.smartpos.deviceservice.aidl.IPinpad;
import com.vfi.smartpos.deviceservice.aidl.PinInputListener;
import com.vfi.smartpos.deviceservice.aidl.PinpadKeyType;

/**
 * Created by CuncheW1 on 2017/9/4.
 */

public class ModulePinpad extends BaseModule {
    private final String TAG = "ModulePinpad";
    private IPinpad aidlPinpad;
    private static ModulePinpad instance;

    public ModulePinpad() {
        try {
            aidlPinpad = DeviceService.getDeviceService().getPinpad(0);
            LogUtil.d(TAG, "aidlPinpad=" + aidlPinpad == null ? "null" : "not null");
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }
    }

    public static synchronized ModulePinpad getInstance() {
        if (instance == null) {
            instance = new ModulePinpad();
        }

        return instance;
    }

    public boolean API01_isKeyExist(int keyType, int keyId) {
        try {
            return aidlPinpad.isKeyExist(keyType, keyId);
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }

        return false;
    }

    public boolean API02_loadTEK(int keyId, byte[] key, byte[] checkValue) {
        boolean bRet = false;
        try {
            bRet = aidlPinpad.loadTEK(keyId, key, checkValue);
            if (!bRet) {
                LogUtil.d(TAG, "Last error:" + aidlPinpad.getLastError());
            }

            return bRet;
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }

        return false;
    }

    public boolean API03_loadEncryptMainKey(int keyId, byte[] key, byte[] checkValue) {
        boolean bRet = false;
        try {
            bRet = aidlPinpad.loadEncryptMainKey(keyId, key, checkValue);
            if (!bRet) {
                LogUtil.d(TAG, "Last error:" + aidlPinpad.getLastError());
            }
            return bRet;
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }

        return false;
    }

    public boolean API04_loadMainKey(int keyId, byte[] key, byte[] checkValue) {
        try {
            boolean bRet = aidlPinpad.loadMainKey(keyId, key, checkValue);
            if (!bRet) {
                LogUtil.d(TAG, "Last error:" + aidlPinpad.getLastError());
            }
            return bRet;
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }

        return false;
    }

    public boolean API05_loadWorkKey(int keyType, int mkId, int wkId, byte[] key, byte[] checkValue) {
        try {
            boolean bRet = aidlPinpad.loadWorkKey(keyType, mkId, wkId, key, checkValue);
            if (!bRet) {
                LogUtil.d(TAG, "Last error:" + aidlPinpad.getLastError());
            }
            return bRet;
        } catch (RemoteException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "RemoteException " + e.getMessage());
        }

        return false;
    }


    public void API06_startPinInput(int keyId, Bundle param, Bundle globalParam, PinInputListener listener) {
        try {
            aidlPinpad.startPinInput(keyId, param, globalParam, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String API07_getLastError() {
        try {
            return aidlPinpad.getLastError();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean API08_savePlainKey(int keyType, int keyId, byte[] key) {
        try {
            boolean bRet = aidlPinpad.savePlainKey(keyType, keyId, key);
            if (!bRet) {
                LogUtil.d(TAG, "Last error:" + aidlPinpad.getLastError());
            }

            return bRet;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return false;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_isKeyExist_CNPinkey() {
        return API01_isKeyExist(PinpadKeyType.PINKEY, 1);
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_isKeyExist_CNTek() {
        return API01_isKeyExist(PinpadKeyType.TDKEY, 1);
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_isKeyExist_CNMasterKey() {
        return API01_isKeyExist(PinpadKeyType.MACKEY, 1);
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadTEK_CNTEK_01() {
        getDescriptionTv().showInfoInNewLine("nihao-");
        String key = "2494E889B78C6F895593728E4990EE21";
        String checkValue = null;
        int keyId = 1;
        return API02_loadTEK(keyId, StringUtil.hexStr2Bytes(key), null);
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadMainKey_CNTMK_01() {
        String key = "360434DF2EC23808377CC6201294EDBA";
        // Plain key = 5B8C86200E4F016B4908197FB6FD4AFE
        String keyValue = "0846C8C28161D62D";
        int keyId = 1;
        return API03_loadEncryptMainKey(keyId, StringUtil.hexStr2Bytes(key),StringUtil.hexStr2Bytes(keyValue));
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadWorkKey_CNSCB() {
        String key = "BF97DAF191E9BA70686D3DCE404F6154";
        String key2 = "B097DAF191E9BA70686D3DCE404F6154";
        int keyId = 1;
        int keyId2 = 2;

        boolean bRet = API08_savePlainKey(PinpadKeyType.PINKEY, keyId, StringUtil.hexStr2Bytes(key));
        LogUtil.d(TAG, "bRet = " + bRet);
        bRet = API08_savePlainKey(PinpadKeyType.PINKEY, keyId2, StringUtil.hexStr2Bytes(key));
        LogUtil.d(TAG, "bRet = " + bRet);
        bRet = API08_savePlainKey(PinpadKeyType.PINKEY, keyId, StringUtil.hexStr2Bytes(key2));
        LogUtil.d(TAG, "bRet = " + bRet);
        bRet = API08_savePlainKey(PinpadKeyType.PINKEY, keyId2, StringUtil.hexStr2Bytes(key));
        LogUtil.d(TAG, "bRet = " + bRet);

        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadWorkKey_CNSCB_resetIndex00() {
        String key = "00000000000000000000000000000004";
        String keyValue = "EF840B00DA448234";
        int masterkeyId = 1;
        int keyId = 0;

        return API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(keyValue));
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadWorkKey_CNSCB_01() {
        String key = "A588F244DBEBCD1E46A4DA99735EB0C2";
        // Plain key = BF97DAF191E9BA70686D3DCE404F6154
        String keyValue = "0F9E5F3A36307B27";
        int masterkeyId = 1;
        int keyId = 1;
//        for (int i = 0; i <= 99; i++) {
//            boolean bRet = API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, i, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(keyValue));
//            LogUtil.d(TAG, "KeyIdx=" + i + " bRet=" + bRet);
//        }

        boolean bRet = API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(keyValue));
        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_loadWorkKey_CNSCB_02() {
        String key = "DD56426ADAE125C0C479BE681DA4F328";
        // Plain key = 02E6EC43706EA810F857E6D601FEAB20
        String keyValue = "8BCCA9818ED1D7F5";
        int masterkeyId = 1;
        int keyId = 1;
        return API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(keyValue));
    }

//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadWorkKey_CN00k1Id1M1_16() {
//        String key = "A588F244DBEBCD1E46A4DA99735EB0C2";
//        String checkValue = "81063F02E211BAED";
//        //String key = "950973182317F80B950973182317F80B02695576B94C242320";
//        int masterkeyId = 1;
//        int keyId = 1;
//        return API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(checkValue));
//    }

//    public boolean CASE_startPinInput_CN00Id1() {
//        int keyId = 1;
//        Bundle param = new Bundle();
//        param.putBoolean(KeyBoardConfig.BUNDLE_PINPARAM_ISONLINE, true);
//        param.putString(KeyBoardConfig.BUNDLE_PINPARAM_PAN, "6224402600000066");
//        param.putInt(KeyBoardConfig.BUNDLE_KEY_TIMEOUT, 60);
//        byte[] lenLimit = new byte[8];
//        lenLimit[0] =(byte)0;
//        lenLimit[1] =(byte)6;
//        lenLimit[2] =(byte)7;
//        lenLimit[3] =(byte)8;
//        lenLimit[4] =(byte)9;
//        lenLimit[5] =(byte)10;
//        lenLimit[6] =(byte)11;
//        lenLimit[7] =(byte)12;
//        param.putByteArray(KeyBoardConfig.BUNDLE_PINPARAM_PINLIMIT, lenLimit);
//
//        API06_startPinInput(keyId, param, new PinInputListener.Stub() {
//            @Override
//            public void onInput(int len, int key) throws RemoteException {
//                LogUtil.d(TAG, "onInput len=" + len + " key=" + key);
//            }
//
//            @Override
//            public void onConfirm(byte[] data, boolean isNonePin) throws RemoteException {
//                LogUtil.d(TAG, "onConfirm data=" + StringUtil.byte2HexStr(data) + " isNonePin=" + isNonePin);
//            }
//
//            @Override
//            public void onCancel() throws RemoteException {
//                LogUtil.d(TAG, "onCancel");
//            }
//
//            @Override
//            public void onError(int errorCode) throws RemoteException {
//                LogUtil.d(TAG, "onError errorCode=" + errorCode);
//                LogUtil.d(TAG, "onError description=" + API07_getLastError());
//            }
//        });
//
//        return true;
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadTEK_CN00k1Id0() {
//        String key = "2494E889B78C6F895593728E4990EE21";
//        //String key = "6F2A62CF498C0282AB903ED371564959";
//        String checkValue = null;
//        int keyId = 0;
//        return API02_loadTEK(keyId, StringUtil.hexStr2Bytes(key), null);
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadMainKey_CN00k1Id0() {
//        String key = "FCAD332CE832FC41C22B74F0FC49271B000D544C574919CBF6";
//        //String key = "158F16098CA2D773158F16098CA2D77300CE7E2DE598A1A115";
//        int keyId = 0;
//        return API03_loadEncryptMainKey(keyId, StringUtil.hexStr2Bytes(key), null);
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadWorkKey_CN00k1Id0() {
//        String key = "89B07B35A1B3F47E89B07B35A1B3F4880281063F02E211BAED";
//        //String key = "950973182317F80B950973182317F80B02695576B94C242320";
//        int masterkeyId = 0;
//        int keyId = 0;
//        return API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), null);
//    }
//
//    public boolean CASE_startPinInput_CN00Id0() {
//        int keyId = 0;
//        Bundle param = new Bundle();
//        param.putBoolean(KeyBoardConfig.BUNDLE_PINPARAM_ISONLINE, true);
//        param.putString(KeyBoardConfig.BUNDLE_PINPARAM_PAN, "6224402600000066");
//        param.putInt(KeyBoardConfig.BUNDLE_KEY_TIMEOUT, 60);
//        param.putString("amount", "20.00");
//        byte[] lenLimit = new byte[8];
//        lenLimit[0] =(byte)0;
//        lenLimit[1] =(byte)6;
//        lenLimit[2] =(byte)7;
//        lenLimit[3] =(byte)8;
//        lenLimit[4] =(byte)9;
//        lenLimit[5] =(byte)10;
//        lenLimit[6] =(byte)11;
//        lenLimit[7] =(byte)12;
//        param.putByteArray(KeyBoardConfig.BUNDLE_PINPARAM_PINLIMIT, lenLimit);
//
//        API06_startPinInput(keyId, param, new PinInputListener.Stub() {
//            @Override
//            public void onInput(int len, int key) throws RemoteException {
//                LogUtil.d(TAG, "onInput len=" + len + " key=" + key);
//            }
//
//            @Override
//            public void onConfirm(byte[] data, boolean isNonePin) throws RemoteException {
//                LogUtil.d(TAG, "onConfirm data=" + StringUtil.byte2HexStr(data) + " isNonePin=" + isNonePin);
//            }
//
//            @Override
//            public void onCancel() throws RemoteException {
//                LogUtil.d(TAG, "onCancel");
//            }
//
//            @Override
//            public void onError(int errorCode) throws RemoteException {
//                LogUtil.d(TAG, "onError errorCode=" + errorCode);
//                LogUtil.d(TAG, "onError description=" + API07_getLastError());
//            }
//        });
//
//        return true;
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadTEK_CN00k2Id0() {
//        //String key = "2494E889B78C6F895593728E4990EE21";
//        String key = "6F2A62CF498C0282AB903ED371564959";
//        String checkValue = null;
//        int keyId = 0;
//        return API02_loadTEK(keyId, StringUtil.hexStr2Bytes(key), null);
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadMainKey_CN00k2Id0() {
//        //String key = "FCAD332CE832FC41C22B74F0FC49271B000D544C574919CBF6";
//        String key = "158F16098CA2D773158F16098CA2D77300CE7E2DE598A1A115";
//        int keyId = 0;
//        return API03_loadEncryptMainKey(keyId, StringUtil.hexStr2Bytes(key), null);
//    }
//
//    @CaseAttributes(isSupportAutoTest = true)
//    public boolean CASE_loadWorkKey_CN00K2Id0() {
//        //String key = "89B07B35A1B3F47E89B07B35A1B3F4880281063F02E211BAED";
//        String key = "950973182317F80B950973182317F80B02695576B94C242320";
//        int masterkeyId = 0;
//        int keyId = 0;
//        return API05_loadWorkKey(PinpadKeyType.PINKEY, masterkeyId, keyId, StringUtil.hexStr2Bytes(key), null);
//    }
}
