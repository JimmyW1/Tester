package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.constant.Const;
import com.boc.aidl.pinpad.AidlPinpad;
import com.boc.aidl.pinpad.AidlPinpadListener;
import com.socsi.exception.PINPADException;
import com.socsi.exception.SDKException;
import com.socsi.smartposapi.ped.Ped;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.other.pinpad.InputPinActivity;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.StringUtil;
import com.test.util.ToastUtil;

/**
 * Created by CuncheW1 on 2017/2/28.
 */

public class ModulePinpad extends BaseModule {
    private final String TAG = "ModulePinpad";
    private AidlPinpad iPinpad;
    private byte[] encryptData;

    public ModulePinpad() {
        this.iPinpad = DeviceService.getPinpad();
    }

    /**
     * 装载主密钥
     */
    public boolean API01loadMainKey(int mkIndex,String key,String kcv) {
        try {
            return iPinpad.loadMainKey(mkIndex, key, kcv);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 装载工作密钥
     */
    public boolean API02loadWorkKey(String keyType,int mkIndex,int wkIndex, String keyValue,String kcv) {
        try {
            return iPinpad.loadWorkKey(keyType, mkIndex, wkIndex, keyValue, kcv);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 计算mac
     */
    public byte[] API03calcMAC(int macAlgorithm,int macIndex, byte[] data) {
        try {
            return iPinpad.calcMAC(macAlgorithm, macIndex, data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 加密数据
     */
    public byte[] API04encryptData(int keyIndex, byte[] data) {
        try {
            return iPinpad.encryptData(keyIndex, data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 解密数据
     */
    public byte[] API05dencryptData(int keyIndex, byte[] data)  {
        try {
            return iPinpad.dencryptData(keyIndex, data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 开启密码输入
     */
    public void API06startPininput(int pinKeyIndex,String pan, byte [] lenLimit, AidlPinpadListener listener) {
        try {
            iPinpad.startPininput(pinKeyIndex, pan, lenLimit, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取8字节随机数
     */
    public String API07getRandom() throws RemoteException {
        return iPinpad.getRandom();
    }

    /**
     * 撤销密码输入
     */
    public void API08cancelPininput() {
        try {
            iPinpad.cancelPininput();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置随机密码键盘布局
     */
    public byte[] API09setPinpadLayout( byte[] layout) throws RemoteException {
        return iPinpad.setPinpadLayout(layout);
    }

    public boolean CASE_startPininput_CN001() {
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                //ToastUtil.showShort("len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                ToastUtil.showLong("data=" + StringUtil.byte2HexStr(data));
                LogUtil.d("Module", "Pin data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                ToastUtil.showLong("errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                ToastUtil.showShort("Cancel input");
            }
        };

        API06startPininput(1, "123456789012345678", new byte[]{1, 5, 6}, listener);

        return true;
    }

    public boolean CASE_startPininput_CN000() {
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                //ToastUtil.showShort("len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                ToastUtil.showLong("data=" + StringUtil.byte2HexStr(data));
                LogUtil.d("Module", "Pin data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                ToastUtil.showLong("errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                ToastUtil.showShort("Cancel input");
            }
        };

        API06startPininput(1, "123456789012345678", new byte[]{1, 2, 7, 10}, listener);

        return true;
    }
    public boolean CASE_startPininput_CN0001() {
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                //ToastUtil.showShort("len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                ToastUtil.showLong("data=" + StringUtil.byte2HexStr(data));
                LogUtil.d("Module", "Pin data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                ToastUtil.showLong("errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                ToastUtil.showShort("Cancel input");
            }
        };

        API06startPininput(1, "123456789012345678", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);

        return true;
    }

    public boolean CASE_startPininput_CN002() {
        //原始PIN_KEY:"34637438468239825298654387628574"
        boolean bRet = API02loadWorkKey(Const.KeyType.PIN_KEY, 1, 1, "850142041C0D07D0066491AB2FDA01CE", "6BF29482");
        if (!bRet) {
            LogUtil.e("Module", "loadWorkKey failed. CN002");
        }

        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                //ToastUtil.showShort("len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                DialogUtil.showConfirmDialog("PinBlock", "加密Pin为:" + StringUtil.byte2HexStr(data), null);
                LogUtil.d("Module", "Pin data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                ToastUtil.showLong("errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                ToastUtil.showShort("Cancel input");
            }
        };

        API06startPininput(1, "6224402600000066", new byte[]{1, 5, 6}, listener);

        return true;
    }

    public  boolean CASE_cancelPininput_CN001() {
        API08cancelPininput();
        return true;
    }

    public boolean CASE_setPinpadLayout_CN001() {
        MyApplication.getInstance().startActivity(InputPinActivity.class);
        return true;
    }

    public boolean CASE_loadWorkKey_CN001() {
        boolean isFailed = false;
        boolean bRet;
        //原始PIN_KEY:"4508013E4AC8E394800E45BF4A627F2A"
        bRet = API02loadWorkKey(Const.KeyType.PIN_KEY, 1, 1, "DAC7785CF2E9E627EF68F877756850CD", "5C24BEF8");
        if (!bRet)
            isFailed = true;

        bRet = API02loadWorkKey(Const.KeyType.PIN_KEY, 1, 2, "DAC7785CF2E9E627EF68F877756850CD", "5C24BEF8");
        if (!bRet)
            isFailed = true;

        //原始MAC_KEY:"4986C104D9DAC1EC803BB5643EE607C8"
        bRet = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "C824FF4BB9FF09105A637841145B38ED", "F38989A4");
        if (!bRet)
            isFailed = true;

        //原始TRACK_KEY:"A27976620DE6CD6DF829D5370731B515"
        bRet = API02loadWorkKey(Const.KeyType.TRACK_KEY, 1, 1, "B96B13EE916166C17A1F5598D4CBE2E5", "5B22FD9A");
        if (!bRet)
            isFailed = true;

        if (isFailed)
            return false;
        else
            return true;
    }

    public boolean CASE_loadMainKey_CNcorrectKcv() {
        getDescriptionTv().showInfoInNewLine("主密钥加载测试");

        String tekKeyString = "D3D529A44F517F860DA291FE4C917C31";
        String checkValueString = "0016F2663B577DD6";

        loadMainIdx02Key(0x02, tekKeyString, checkValueString);

        getResultTv().showInfoInNewLine("开始加载主密钥");
        // Been encrypt key is "D010B0E3BFC12CCE79E9B3F202FD9283"
        return API01loadMainKey(1, "4FE6C54B025CB9B44FE4DCDACFA143F3", "20ED72D3");
    }

    public boolean CASE_loadMainKey_CNuncorrectKcv() {
        getDescriptionTv().showInfoInNewLine("主密钥加载测试,错误kcv");

        String tekKeyString = "D3D529A44F517F860DA291FE4C917C31";
        String checkValueString = "0016F2663B577DD6";
        loadTransKey(0x02, tekKeyString, checkValueString); // 0x02是和service约定，不能改动

        getResultTv().showInfoInNewLine("开始加载主密钥");
        // Been encrypt key is "D010B0E3BFC12CCE79E9B3F202FD9283"
        return API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F068941"); // 下载非tek加密的主密钥
    }

    public boolean CASE_cacuMac_CN001() {
        getResultTv().showInfoInNewLine("计算MAC_9606,data=11111");
        byte[] mac = API03calcMAC(Const.MacType.MAC_9606, 1, new byte[] {1,1,1,1,1,1});
        getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(mac));

        getResultTv().showInfoInNewLine("计算MAC_CBC,data=11111");
        mac = API03calcMAC(Const.MacType.MAC_CBC, 1, new byte[] {1,1,1,1,1,1});
        getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(mac));

        getResultTv().showInfoInNewLine("计算MAC_ECB,data=11111");
        mac = API03calcMAC(Const.MacType.MAC_ECB, 1, new byte[] {1,1,1,1,1,1});
        getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(mac));

        getResultTv().showInfoInNewLine("计算MAC_X99,data=11111");
        mac = API03calcMAC(Const.MacType.MAC_X99, 1, new byte[] {1,1,1,1,1,1});
        getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(mac));

        getResultTv().showInfoInNewLine("计算MAC_X919,data=11111");
        mac = API03calcMAC(Const.MacType.MAC_X919, 1, new byte[] {1,1,1,1,1,1});
        getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(mac));

        return true;
    }

    public boolean CASE_encryptData_CN001() {
        encryptData = API04encryptData(1, StringUtil.hexStr2Bytes("1111111111111111"));
        if (encryptData == null) {
            return false;
        } else {
            getResultTv().showInfoInNewLine(StringUtil.byte2HexStr(encryptData));
            return true;
        }
    }

    public boolean CASE_decryptData_CN001() {
        if (encryptData == null)  {
            getResultTv().showInfoInNewLine("加密数据为空，请先做encryptData->CN001案例");
            return false;
        }

        byte[] decryptData = API05dencryptData(1, encryptData);
        if (decryptData != null) {
            getResultTv().showInfoInNewLine("Decrypt data=" + StringUtil.byte2HexStr(decryptData));
            return true;
        } else {
            getResultTv().showInfoInNewLine("Decrypt data failed.");
            return false;
        }
    }

    private boolean loadTransKey(int keyIdx, String key, String kcv) {
        boolean bRet = false;

        getResultTv().showInfoInNewLine("开始加载传输key");
        try {
            bRet = Ped.getInstance().loadPlainKEK((byte)keyIdx, StringUtil.hexStr2Bytes(key), StringUtil.hexStr2Bytes(kcv));
            if (bRet) {
                LogUtil.d(TAG, "LoadPainKEK success");
            } else {
                LogUtil.d(TAG, "LoadPainKEK failed");
            }
        } catch (SDKException e) {
            e.printStackTrace();
        } catch (PINPADException e) {
            LogUtil.d(TAG, "load kek key failed.");
            e.printStackTrace();
        }

        if (bRet) {
            getResultTv().showInfoInNewLine("加载传输key Success.");
        } else  {
            getResultTv().showInfoInNewLine("加载传输key Failed.");
        }

        return bRet;
    }

    private boolean loadMainIdx02Key(int keyIdx, String key, String kcv) {
        boolean bRet = false;

        getResultTv().showInfoInNewLine("开始加载传输key");
        try {
            bRet = Ped.getInstance().loadMKey((byte) Ped.KEK_TYPE_UNENCTYPT_MASTER, (byte)keyIdx, key, 0x01, Ped.KEY_TYPE_UNENCTYPTED_KEY, kcv, false);
            if (bRet) {
                LogUtil.d(TAG, "Load 0x02 MainKey success");
            } else {
                LogUtil.d(TAG, "Load 0x02 MainKey failed");
            }
        } catch (SDKException e) {
            e.printStackTrace();
        } catch (PINPADException e) {
            LogUtil.d(TAG, "load main key failed.");
            e.printStackTrace();
        }

        if (bRet) {
            getResultTv().showInfoInNewLine("加载主密钥02 key Success.");
        } else  {
            getResultTv().showInfoInNewLine("加载主密钥02 key Failed.");
        }

        return bRet;
    }
}
