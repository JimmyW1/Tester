package com.test.logic.testmodules.boc_modules;

import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.boc.aidl.constant.Const;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.pinpad.AidlPinpadListener;
import com.boc.aidl.pinpad.AidlPinpad;
import com.socsi.exception.PINPADException;
import com.socsi.exception.SDKException;
import com.socsi.smartposapi.ped.Ped;
import com.socsi.utils.StringUtil;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.ui.activities.MyApplication;
import com.test.ui.views.boc_pinpad_keyboard.CallbackSetLayout;
import com.test.ui.views.boc_pinpad_keyboard.CallbackStartPininput;
import com.test.ui.views.boc_pinpad_keyboard.InputPinActivity;
import com.test.util.ToastUtil;

import static com.socsi.smartposapi.ped.Ped.KEK_TYPE_MASTER_ENCTYPT_MASTER;
import static com.socsi.smartposapi.ped.Ped.KEK_TYPE_UNENCTYPT_MASTER;
import static com.socsi.smartposapi.ped.Ped.KEY_TYPE_ENCTYPTED_KEY;
import static com.socsi.smartposapi.ped.Ped.KEY_TYPE_UNENCTYPTED_KEY;

/**
 * Created by InovaF1 on 2017/6/14.
 */

public class PinpadTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";
    private CallbackStartPininput currentStartPininputCallback;
    private CallbackSetLayout currentSetLayoutCallback;
    private static PinpadTestCase instance;

    public PinpadTestCase() {
        instance = this;
    }

    public CallbackStartPininput getCurrentStartPininputCallback() {
        return currentStartPininputCallback;
    }

    public CallbackSetLayout getCurrentSetLayoutCallback() {
        return currentSetLayoutCallback;
    }

    public static synchronized PinpadTestCase getInstance() {
        return instance;
    }

    public boolean API01loadMainKey(int mkIndex, String key, String kcv) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                boolean flag = AidlPinpad.Stub.asInterface(handle.getPinpad()).loadMainKey(mkIndex, key, kcv);
                return flag;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 下载设备主密钥
     *
     * @param keyIdx 201索引以索引2存储
     * @param key
     * @param kcv
     * @return
     */
    private boolean loadMainIdx02Key(int keyIdx, String key, String kcv) {
        boolean bRet = false;

        Log.i(TAG, "开始加载传输key");
        try {
            bRet = Ped.getInstance().loadMKey((byte) KEK_TYPE_UNENCTYPT_MASTER, (byte) keyIdx, key, 0x01, Ped.KEY_TYPE_UNENCTYPTED_KEY, kcv, false);
            if (bRet) {
                Log.i(TAG, "Load 0x02 MainKey success");
            } else {
                Log.i(TAG, "Load 0x02 MainKey failed");
            }
        } catch (SDKException e) {
            e.printStackTrace();
        } catch (PINPADException e) {
            Log.i(TAG, "load main key failed.");
            e.printStackTrace();
        }

        if (bRet) {
            Log.i(TAG, "加载主密钥02 key Success.");
        } else {
            Log.i(TAG, "加载主密钥02 key Failed.");
        }

        return bRet;
    }

    /**
     * 装载工作密钥
     */
    public boolean API02loadWorkKey(String keyType, int mkIndex, int wkIndex, String keyValue, String kcv) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                return AidlPinpad.Stub.asInterface(handle.getPinpad()).loadWorkKey(keyType, mkIndex, wkIndex, keyValue, kcv);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 计算mac
     */
    public byte[] API03calcMAC(int macAlgorithm, int macIndex, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                return AidlPinpad.Stub.asInterface(handle.getPinpad()).calcMAC(macAlgorithm, macIndex, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密数据
     */
    public byte[] API04encryptData(int keyIndex, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                return AidlPinpad.Stub.asInterface(handle.getPinpad()).encryptData(keyIndex, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密数据
     */
    public byte[] API05dencryptData(int keyIndex, byte[] data) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                return AidlPinpad.Stub.asInterface(handle.getPinpad()).dencryptData(keyIndex, data);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 开启密码输入
     */
    public void API06startPininput(int pinKeyIndex, String pan, byte[] lenLimit, AidlPinpadListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                AidlPinpad.Stub.asInterface(handle.getPinpad()).startPininput(pinKeyIndex, pan, lenLimit, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取8字节随机数
     */
    public String API07getRandom() throws RemoteException {
        AidlDeviceService handle = DeviceService.getDeviceService();
        if (handle != null) {
            String Random = AidlPinpad.Stub.asInterface(handle.getPinpad()).getRandom();
            Log.i(TAG, "API07getRandom: Random=" + Random);
            return Random;
        }
        return null;
    }

    /**
     * 撤销密码输入
     */
    public void API08cancelPininput() {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                AidlPinpad.Stub.asInterface(handle.getPinpad()).cancelPininput();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置随机密码键盘布局
     */
    public byte[] API09setPinpadLayout(byte[] layout) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if (handle != null) {
                return AidlPinpad.Stub.asInterface(handle.getPinpad()).setPinpadLayout(layout);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 下载通讯密钥
     */
    public void loadDataKey_3des(byte masterkeyIndex)
    {

        String keyData = "99A5089CBC0F6D0C6A51B3CC14B9C04F";
        byte decMKeyIdx = 0x02;
        byte keyType = KEY_TYPE_ENCTYPTED_KEY;
        String kcv = "535174";
        boolean isTmsKey = true;
        try {
            boolean isSuccess = Ped.getInstance().loadMKey(KEK_TYPE_MASTER_ENCTYPT_MASTER, masterkeyIndex, keyData, decMKeyIdx, keyType, kcv, true);
            if(isSuccess){
                Log.i(TAG, "load zek 3des success");
                ToastUtil.showLong("load zek 3des success" + "\n" + "索引：" + masterkeyIndex);
            }else{
                Log.i(TAG, "load zek 3des failed");
                ToastUtil.showLong("load zek 3des failed");
            }
        } catch (SDKException e) {
            Log.e(TAG, "download zek sdkException");
            e.printStackTrace();
        } catch (PINPADException e) {
            Log.e(TAG, "download zek PINPADException");
            e.printStackTrace();
        }
    }



    /**
     * 测试案例
     */

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "",
            resDialogTitle = "", APIName = "下载设备主密钥")
    public void load_3Des_MKey() {
        Log.i(TAG, "开始下载设备主密钥。。。");
        byte kekType = KEK_TYPE_UNENCTYPT_MASTER;
        byte keyType = KEY_TYPE_UNENCTYPTED_KEY;
        byte masterkeyIndex = 0x02;
        // 设备主密钥明文（3DES）
        String keyData = "D010B0E3BFC12CCE79E9B3F202FD9283";
        String kcv = "20ED72D31BF67D67";
        byte decMKeyIdx = 0x01;
        boolean isTmsKey = false;
        String show = "loadkek_des Success [" + keyData +"] index=[" + masterkeyIndex + "]";

        try {
            boolean isSuccess = Ped.getInstance().loadMKey(kekType, masterkeyIndex, keyData, decMKeyIdx, keyType, kcv, isTmsKey);
            if(isSuccess)
            {
                ToastUtil.showLong("设备主密钥下载成功（3DES）！"+ "\n" + "索引：" + masterkeyIndex + "\n" +"设备主密钥："+ keyData);
            }else
            {
                ToastUtil.showLong("设备主密钥下载失败（3DES）！");
            }
        } catch (SDKException e) {
            e.printStackTrace();
        } catch (PINPADException e) {
            e.printStackTrace();
        }

    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "",
            resDialogTitle = "", APIName = "下载设备主密钥")
    public void load_Des_MKey() {
        Log.i(TAG, "开始下载设备主密钥。。。");
        byte kekType = KEK_TYPE_UNENCTYPT_MASTER;
        byte keyType = KEY_TYPE_UNENCTYPTED_KEY;
        byte masterkeyIndex = 0x02;
        // 设备主密钥明文（DES）
        String keyData = "CBBFFDE379985723";
        // 校验值
        String kcv = "FF4AEDEE9D17CE71";
        byte decMKeyIdx = 0x01;
        boolean isTmsKey = false;
        String show = "loadkek_des Success [" + keyData +"] index=[" + masterkeyIndex + "]";

        try {
            boolean isSuccess = Ped.getInstance().loadMKey(kekType, masterkeyIndex, keyData, decMKeyIdx, keyType, kcv, isTmsKey);
            if(isSuccess)
            {
                ToastUtil.showLong("设备主密钥下载成功（DES）！" + "\n" + "索引：" + masterkeyIndex + "\n" +"设备主密钥："+ keyData );
            }else
            {
                ToastUtil.showLong("设备主密钥下载失败（DES）！");
            }
        } catch (SDKException e) {
            e.printStackTrace();
        } catch (PINPADException e) {
            e.printStackTrace();
        }

    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN001",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN001() {
        Log.i(TAG, "CASE_loadMainKey_CN001");
        boolean ret =  API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
        if(ret)
        {
            ToastUtil.showLong("load MainKey success");
        }else
        {
            ToastUtil.showLong("load MainKey failed");
        }
        return ret;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN002",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN002() {
        Log.i(TAG, "CASE_loadMainKey_CN002");
        boolean ret =  API01loadMainKey(1,"E077BE8EC431923A","D0AFD8");
        if(ret)
        {
            ToastUtil.showLong("load MainKey success");
        }else
        {
            ToastUtil.showLong("load MainKey failed");
        }
        return ret;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN003",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN003() {
        Log.i(TAG, "CASE_loadMainKey_CN003");
        return !API01loadMainKey(0, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN004",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN004() {
        Log.i(TAG, "CASE_loadMainKey_CN004");
        return !API01loadMainKey(-1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN005",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN005() {
        Log.i(TAG, "CASE_loadMainKey_CN005");
        return API01loadMainKey(100, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN006",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN006() {
        Log.i(TAG, "CASE_loadMainKey_CN006");
        return !API01loadMainKey(101, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN007",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN007() {
        Log.i(TAG, "CASE_loadMainKey_CN007");
        return !API01loadMainKey(Integer.MAX_VALUE, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN008",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN008() {
        Log.i(TAG, "CASE_loadMainKey_CN008");
        return !API01loadMainKey(Integer.MIN_VALUE, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN009",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN009() {
        Log.i(TAG, "CASE_loadMainKey_CN009");
        return !API01loadMainKey(Integer.MAX_VALUE + 1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN010",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN010() {
        Log.i(TAG, "CASE_loadMainKey_CN010");
        return !API01loadMainKey(Integer.MIN_VALUE - 1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN011",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN011() {
        Log.i(TAG, "CASE_loadMainKey_CN011");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBE", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN012",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN012() {
        Log.i(TAG, "CASE_loadMainKey_CN012");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA800", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN013",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN013() {
        Log.i(TAG, "CASE_loadMainKey_CN013");
        return !API01loadMainKey(1, "", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN014",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN014() {
        Log.i(TAG, "CASE_loadMainKey_CN014");
        return !API01loadMainKey(1, null, "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN015",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN015() {
        Log.i(TAG, "CASE_loadMainKey_CN015");
        return !API01loadMainKey(1, "E077BE8EC43192", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN016",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN016() {
        Log.i(TAG, "CASE_loadMainKey_CN016");
        return !API01loadMainKey(1, "E077BE8EC431923A00", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN017",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN017() {
        Log.i(TAG, "CASE_loadMainKey_CN017");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA0", "9F0689");
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN018",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN018() {
        Log.i(TAG, "CASE_loadMainKey_CN018");
        return API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F06");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN019",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN019() {
        Log.i(TAG, "CASE_loadMainKey_CN019");
        return API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F068941");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN020",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN020() {
        Log.i(TAG, "CASE_loadMainKey_CN020");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0600");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN021",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN021() {
        Log.i(TAG, "CASE_loadMainKey_CN021");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN022",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN022() {
        Log.i(TAG, "CASE_loadMainKey_CN022");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN023",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN023() {
        Log.i(TAG, "CASE_loadMainKey_CN023");
        loadMainIdx02Key(2, "D010B0E3BFC12CCE79E9B3F202FD9283", "20ED72");
        API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
        return API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN024",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN024() {
        Log.i(TAG, "CASE_loadMainKey_CN024");
        loadMainIdx02Key(2, "CBBFFDE379985723", "FF4AED");
        API01loadMainKey(1, "E077BE8EC431923A", "D0AFD8");
        return API01loadMainKey(1, "E077BE8EC431923A", "D0AFD8");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN025",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN025() {
        Log.i(TAG, "CASE_loadMainKey_CN025");
        return !API01loadMainKey(3, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadMainKey_CN026",
            resDialogTitle = "", APIName = "loadMainKey")
    public boolean CASE_loadMainKey_CN026() {
        Log.i(TAG, "CASE_loadMainKey_CN026");
        return !API01loadMainKey(1, "4AE131B871F7B5108A4CE0FCF2CBBEA8", "9F0689");
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN001",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN001() {
        Log.i(TAG, "CASE_loadWorkKey_CN001");
        load_3Des_MKey();
        CASE_loadMainKey_CN001();
        String iPinKey = "469CAF0C72614A6BB09DBCF56B04DD4E";
        boolean ret =  API02loadWorkKey(Const.KeyType.PIN_KEY, 1, 1,iPinKey , "5C24BE");
        if(ret)
        {
            ToastUtil.showLong("pin_key load success" + "\n" + "pin_key:" + iPinKey);
        }else
        {
            ToastUtil.showLong("pin_key load failed" + "\n" + "pin_key:" + iPinKey);
        }
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN002",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN002() {
        Log.i(TAG, "CASE_loadWorkKey_CN002");
        load_3Des_MKey();
        CASE_loadMainKey_CN001();
        String iTrackKey = "5D49B9C6FF3F96BBA04179A696EFB9B6";
        boolean ret = API02loadWorkKey(Const.KeyType.TRACK_KEY, 1, 1, iTrackKey, "5B22FD");
        if(ret)
        {
            ToastUtil.showLong("track_key load success" + "\n" + "track_key:" + iTrackKey);
        }else
        {
            ToastUtil.showLong("track_key load failed" + "\n" + "track_key:" + iTrackKey);
        }
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN003",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN003() {
        Log.i(TAG, "CASE_loadWorkKey_CN003");
        load_3Des_MKey();
        CASE_loadMainKey_CN001();
        String iMacKey = "CB62B8244C9F388BFC00229DC8823546";
        boolean ret =  API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, iMacKey, "F38989");
        if(ret)
        {
            ToastUtil.showLong("mac_key load success" + "\n" + "mac_key:" + iMacKey);
        }else
        {
            ToastUtil.showLong("mac_key load failed" + "\n" + "mac_key:" + iMacKey);
        }
        return ret;
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN004",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN004() {
        Log.i(TAG, "CASE_loadWorkKey_CN004");
        load_Des_MKey();
        CASE_loadMainKey_CN002();
        String iPinkey = "019B29453C35CF02";
        boolean ret =  API02loadWorkKey(Const.KeyType.PIN_KEY,1,1, iPinkey,"2022FA");
        if(ret)
        {
            ToastUtil.showLong("pin_key load success" + "\n" + "pin_key:" + iPinkey);
        }else{
            ToastUtil.showLong("pin_key load failed" + "\n" + "pin_key:" + iPinkey);
        }
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN005",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN005() {
        Log.i(TAG, "CASE_loadWorkKey_CN005");
        load_Des_MKey();
        CASE_loadMainKey_CN002();
        String iTrackKey = "E4523D901E81E961";
        boolean ret =  API02loadWorkKey(Const.KeyType.TRACK_KEY,1,1, iTrackKey,"DBAFD6");
        if(ret)
        {
            ToastUtil.showLong("track_key load success" + "\n" + "track_key:" + iTrackKey);
        }else{
            ToastUtil.showLong("track_key load failed" + "\n" + "track_key:" + iTrackKey);
        }
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN006",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN006() {
        Log.i(TAG, "CASE_loadWorkKey_CN006");
        load_Des_MKey();
        CASE_loadMainKey_CN002();
        String iMacKey = "CA027E536995FBE4";
        boolean ret =  API02loadWorkKey(Const.KeyType.MAC_KEY,1,1, iMacKey,"D4D62D");
        if(ret)
        {
            ToastUtil.showLong("mac_key load success" + "\n" + "mac_key:" + iMacKey);
        }else{
            ToastUtil.showLong("mac_key load failed" + "\n" + "mac_key:" + iMacKey);
        }
        return ret;
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN007",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN007() {
        Log.i(TAG, "CASE_loadWorkKey_CN007");
        return !API02loadWorkKey("KEY", 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN008",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN008() {
        Log.i(TAG, "CASE_loadWorkKey_CN008");
        return !API02loadWorkKey("", 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN009",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN009() {
        Log.i(TAG, "CASE_loadWorkKey_CN009");
        return !API02loadWorkKey(null, 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN010",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN010() {
        Log.i(TAG, "CASE_loadWorkKey_CN010");
        return !API02loadWorkKey("~!@#$%^&*()_+{}|:\"<>?[]\\;\',./", 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN011",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN011() {
        Log.i(TAG, "CASE_loadWorkKey_CN011");
        return !API02loadWorkKey("中国文字", 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN012",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN012() {
        Log.i(TAG, "CASE_loadWorkKey_CN012");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, 0, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN013",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN013() {
        Log.i(TAG, "CASE_loadWorkKey_CN013");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, 101, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN014",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN014() {
        Log.i(TAG, "CASE_loadWorkKey_CN014");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, -1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN015",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN015() {
        Log.i(TAG, "CASE_loadWorkKey_CN015");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, Integer.MIN_VALUE, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN016",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN016() {
        Log.i(TAG, "CASE_loadWorkKey_CN016");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, Integer.MIN_VALUE + 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN017",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN017() {
        Log.i(TAG, "CASE_loadWorkKey_CN017");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, Integer.MIN_VALUE, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN018",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN018() {
        Log.i(TAG, "CASE_loadWorkKey_CN018");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, Integer.MIN_VALUE - 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN019",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN019() {
        Log.i(TAG, "CASE_loadWorkKey_CN019");
        return !API02loadWorkKey(Const.KeyType.PIN_KEY, Integer.MIN_VALUE - 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN020",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN020() {
        Log.i(TAG, "CASE_loadWorkKey_CN020");
        return !API02loadWorkKey("TRACK_KEY", 1, -1, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN021",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN021() {
        Log.i(TAG, "CASE_loadWorkKey_CN021");
        return !API02loadWorkKey("TRACK_KEY", 1, -0, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN022",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN022() {
        Log.i(TAG, "CASE_loadWorkKey_CN022");
        return API02loadWorkKey("TRACK_KEY", 1, 100, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN023",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN023() {
        Log.i(TAG, "CASE_loadWorkKey_CN023");
        return !API02loadWorkKey("TRACK_KEY", 1, 101, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN024",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN024() {
        Log.i(TAG, "CASE_loadWorkKey_CN024");
        return !API02loadWorkKey("TRACK_KEY", 1, Integer.MAX_VALUE, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN025",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN025() {
        Log.i(TAG, "CASE_loadWorkKey_CN025");
        return !API02loadWorkKey("TRACK_KEY", 1, Integer.MAX_VALUE + 1, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN026",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN026() {
        Log.i(TAG, "CASE_loadWorkKey_CN026");
        return !API02loadWorkKey("TRACK_KEY", 1, Integer.MIN_VALUE, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN027",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN027() {
        Log.i(TAG, "CASE_loadWorkKey_CN027");
        return !API02loadWorkKey("TRACK_KEY", 1, Integer.MIN_VALUE - 1, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN028",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN028() {
        Log.i(TAG, "CASE_loadWorkKey_CN028");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC882354600", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN029",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN029() {
        Log.i(TAG, "CASE_loadWorkKey_CN029");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC88235", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN030",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN030() {
        Log.i(TAG, "CASE_loadWorkKey_CN030");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN031",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN031() {
        Log.i(TAG, "CASE_loadWorkKey_CN031");
        return !API02loadWorkKey("MAC_KEY", 1, 1, null, "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN032",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN032() {
        Log.i(TAG, "CASE_loadWorkKey_CN032");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN033",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN033() {
        Log.i(TAG, "CASE_loadWorkKey_CN033");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "！·#￥%……—*（）——+{}｜：“《》？[]、；‘，。、", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN034",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN034() {
        Log.i(TAG, "CASE_loadWorkKey_CN034");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "中国文字", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN035",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN035() {
        Log.i(TAG, "CASE_loadWorkKey_CN035");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CA027E536995FBE400", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN036",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN036() {
        Log.i(TAG, "CASE_loadWorkKey_CN036");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CA027E536995FB", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN037",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN037() {
        Log.i(TAG, "CASE_loadWorkKey_CN037");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CA027E536995FBE400", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN038",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN038() {
        Log.i(TAG, "CASE_loadWorkKey_CN038");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN039",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN039() {
        Log.i(TAG, "CASE_loadWorkKey_CN039");
        return !API02loadWorkKey("MAC_KEY", 1, 1, null, "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN040",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN040() {
        Log.i(TAG, "CASE_loadWorkKey_CN040");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CA027E536995FB00", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN041",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN041() {
        Log.i(TAG, "CASE_loadWorkKey_CN041");
        return API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F389");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN042",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN042() {
        Log.i(TAG, "CASE_loadWorkKey_CN042");
        return API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989A4");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN043",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN043() {
        Log.i(TAG, "CASE_loadWorkKey_CN043");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989A473");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN044",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN044() {
        Log.i(TAG, "CASE_loadWorkKey_CN044");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN045",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN045() {
        Log.i(TAG, "CASE_loadWorkKey_CN045");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN046",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN046() {
        Log.i(TAG, "CASE_loadWorkKey_CN046");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38980");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN047",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN047() {
        Log.i(TAG, "CASE_loadWorkKey_CN047");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "~!@#$%^&*()_+{}|:\"<>?`-=[]\\;',./", "F38989");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN048",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN048() {
        Log.i(TAG, "CASE_loadWorkKey_CN048");
        return !API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "中国文字");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN049",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN049() {
        Log.i(TAG, "CASE_loadWorkKey_CN049");
        return API02loadWorkKey("PIN_KEY", 1, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN050",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN050() {
        Log.i(TAG, "CASE_loadWorkKey_CN050");
        return API02loadWorkKey("TRACK_KEY", 1, 1, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN051",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN051() {
        Log.i(TAG, "CASE_loadWorkKey_CN051");
        return API02loadWorkKey("MAC_KEY", 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN052",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN052() {
        Log.i(TAG, "CASE_loadWorkKey_CN052");
        return !API02loadWorkKey("PIN_KEY", 99, 1, "469CAF0C72614A6BB09DBCF56B04DD4E", "5C24BE");
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_loadWorkKey_CN053",
            resDialogTitle = "", APIName = "loadWorkKey")
    public boolean CASE_loadWorkKey_CN053() {
        Log.i(TAG, "CASE_loadWorkKey_CN053");
        return !API02loadWorkKey("TRACK_KEY", 1, 1, "5D49B9C6FF3F96BBA04179A696EFB9B6", "5B22FD");
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN001",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN001() {
        Log.i(TAG, "CASE_calcMAC_CN001");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "97A28EB3E9FD0A69";
        byte[] macStr = new byte[0];
        if(flag)
        {
            macStr = API03calcMAC(Const.MacType.MAC_X99, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
            if(exp.equals(StringUtil.byte2HexStr(macStr)))
            {
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_X99 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算正确");
            }else{
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_X99 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算错误");
            }

        }else{
            ToastUtil.showLong("mac_key load failed");
        }
        return exp.equals(StringUtil.byte2HexStr(macStr));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN002",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN002() {
        Log.i(TAG, "CASE_calcMAC_CN002");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "041DFFC35533E5AB";
        byte[] macStr = new byte[0];
        if(flag)
        {
            macStr = API03calcMAC(Const.MacType.MAC_X919, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
            if(exp.equals(StringUtil.byte2HexStr(macStr)))
            {
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_X919 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算正确");
            }else{
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_X919 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算错误");
            }
        }
        return exp.equals(StringUtil.byte2HexStr(macStr));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN003",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN003() {
        Log.i(TAG, "CASE_calcMAC_CN003");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "4242334238344646";
        byte[] macStr = new byte[0];
        if(flag)
        {
            macStr = API03calcMAC(Const.MacType.MAC_ECB, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
            if(exp.equals(StringUtil.byte2HexStr(macStr)))
            {
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_ECB +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算正确");
            }else{
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_ECB +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算错误");
            }
        }
        return exp.equals(StringUtil.byte2HexStr(macStr));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN004",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN004() {
        Log.i(TAG, "CASE_calcMAC_CN004");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "9452572B0DDCC6DA";
        byte[] macStr = new byte[0];
        if(flag)
        {
            macStr = API03calcMAC(Const.MacType.MAC_9606, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
            if(exp.equals(StringUtil.byte2HexStr(macStr)))
            {
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_9606 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算正确");
            }else{
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_9606 +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算错误");
            }
        }
        return exp.equals(StringUtil.byte2HexStr(macStr));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN005",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN005() {
        Log.i(TAG, "CASE_calcMAC_CN005");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "97A28EB3E9FD0A69";
        byte[] macStr = new byte[0];
        if(flag)
        {
            macStr = API03calcMAC(Const.MacType.MAC_CBC, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
            if(exp.equals(StringUtil.byte2HexStr(macStr)))
            {
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_CBC +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算正确");
            }else{
                ToastUtil.showLong("加密方式：" + Const.MacType.MAC_CBC +
                        "\n" + "macStr:" + StringUtil.byte2HexStr(macStr) +
                        "\n" + "MAC计算错误");
            }
        }
        return exp.equals(StringUtil.byte2HexStr(macStr));
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN006",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN006() {
        Log.i(TAG, "CASE_calcMAC_CN006");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN007",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN007() {
        Log.i(TAG, "CASE_calcMAC_CN007");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(6, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN008",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN008() {
        Log.i(TAG, "CASE_calcMAC_CN008");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(-1, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN009",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN009() {
        Log.i(TAG, "CASE_calcMAC_CN009");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(Integer.MAX_VALUE, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN010",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN010() {
        Log.i(TAG, "CASE_calcMAC_CN010");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(Integer.MIN_VALUE, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN011",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN011() {
        Log.i(TAG, "CASE_calcMAC_CN011");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(Integer.MAX_VALUE + 1, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN012",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN012() {
        Log.i(TAG, "CASE_calcMAC_CN012");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(Integer.MIN_VALUE - 1, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN013",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN013() {
        Log.i(TAG, "CASE_calcMAC_CN013");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, 0, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN014",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN014() {
        Log.i(TAG, "CASE_calcMAC_CN014");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, -1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN015",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN015() {
        Log.i(TAG, "CASE_calcMAC_CN015");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, 101, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN016",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN016() {
        Log.i(TAG, "CASE_calcMAC_CN016");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, Integer.MAX_VALUE, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN017",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN017() {
        Log.i(TAG, "CASE_calcMAC_CN017");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, Integer.MAX_VALUE + 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN018",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN018() {
        Log.i(TAG, "CASE_calcMAC_CN018");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, Integer.MIN_VALUE, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN019",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN019() {
        Log.i(TAG, "CASE_calcMAC_CN019");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, Integer.MIN_VALUE - 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN020",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN020() {
        Log.i(TAG, "CASE_calcMAC_CN020");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, 1, new byte[]{});
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN021",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN021() {
        Log.i(TAG, "CASE_calcMAC_CN021");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, 1, null);
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN022",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN022() {
        Log.i(TAG, "CASE_calcMAC_CN022");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "97A28EB3E9FD0A69";
        boolean flag1 = exp.equals(StringUtil.byte2HexStr(API03calcMAC(0x01, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C1000899000"))));
        return exp.equals(StringUtil.byte2HexStr(API03calcMAC(0x01, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C1000899000"))));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN023",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN023() {
        Log.i(TAG, "CASE_calcMAC_CN023");
        boolean flag = API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        String exp = "97A28EB3E9FD0A69";
        boolean flag1 = exp.equals(StringUtil.byte2HexStr(API03calcMAC(0x01, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C100089"))));
        return exp.equals(StringUtil.byte2HexStr(API03calcMAC(0x01, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C100089"))));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_calcMAC_CN024",
            resDialogTitle = "", APIName = "calcMAC")
    public boolean CASE_calcMAC_CN024() {
        Log.i(TAG, "CASE_calcMAC_CN024");
        API02loadWorkKey(Const.KeyType.MAC_KEY, 1, 1, "CB62B8244C9F388BFC00229DC8823546", "F38989");
        byte[] ret = API03calcMAC(0x01, 1, StringUtil.hexStr2Bytes("010C6000000012138010006C10008990"));
        return (ret == null);
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN001",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN001() {
        Log.i(TAG, "CASE_encryptData_CN001");
        byte masterkeyIndex = 0x0A;
        loadDataKey_3des(masterkeyIndex);
        String exp = "BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939";

        byte[] encryptResult = API04encryptData(10, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        if(encryptResult != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "加密后的数据：" + "\n" + StringUtil.byte2HexStr(encryptResult));
        }
        return exp.equals(StringUtil.byte2HexStr(encryptResult));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN002",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN002() {
        Log.i(TAG, "CASE_encryptData_CN002");
        byte masterkeyIndex = 0x01;
        loadDataKey_3des(masterkeyIndex);
        String exp = "BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939";
        byte[] encryptResult = API04encryptData(1, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        if(encryptResult != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "加密后的数据：" + "\n" + StringUtil.byte2HexStr(encryptResult));
        }
        return exp.equals(StringUtil.byte2HexStr(encryptResult));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN003",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN003() {
        Log.i(TAG, "CASE_encryptData_CN003");
        byte masterkeyIndex = 0x64;
        loadDataKey_3des(masterkeyIndex);
        String exp = "BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939";
        byte[] encryptResult = API04encryptData(100, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        if(encryptResult != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "加密后的数据：" + "\n" + StringUtil.byte2HexStr(encryptResult));
        }
        return exp.equals(StringUtil.byte2HexStr(encryptResult));
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN004",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN004() {
        Log.i(TAG, "CASE_encryptData_CN004");
        byte[] ret = API04encryptData(0, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        ToastUtil.show(new String(ret), 60);
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN005",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN005() {
        Log.i(TAG, "CASE_encryptData_CN005");
        byte[] ret = API04encryptData(-1, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN006",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN006() {
        Log.i(TAG, "CASE_encryptData_CN006");
        byte[] ret = API04encryptData(101, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN007",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN007() {
        Log.i(TAG, "CASE_encryptData_CN007");
        byte[] ret = API04encryptData(Integer.MAX_VALUE, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN008",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN008() {
        Log.i(TAG, "CASE_encryptData_CN008");
        byte[] ret = API04encryptData(Integer.MAX_VALUE + 1, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN009",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN009() {
        Log.i(TAG, "CASE_encryptData_CN009");
        byte[] ret = API04encryptData(Integer.MIN_VALUE, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN010",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN010() {
        Log.i(TAG, "CASE_encryptData_CN010");
        byte[] ret = API04encryptData(Integer.MIN_VALUE - 1, StringUtil.hexStr2Bytes("6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN011",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN011() {
        Log.i(TAG, "CASE_encryptData_CN011");
        byte[] ret = API04encryptData(0, null);
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_encryptData_CN012",
            resDialogTitle = "", APIName = "encryptData")
    public boolean CASE_encryptData_CN012() {
        Log.i(TAG, "CASE_encryptData_CN012");
        byte[] ret = API04encryptData(0, new byte[]{});
        return (ret == null);
    }*/


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN001",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN001() {
        Log.i(TAG, "CASE_dencryptData_CN001");
        byte masterkeyIndex = 0x0A;
        loadDataKey_3des(masterkeyIndex);
        String exp = "6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000";
        byte[] dencryptData = API05dencryptData(10, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        if(dencryptData != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "解密后的数据：" + "\n" + StringUtil.byte2HexStr(dencryptData));
        }
        return exp.equals(StringUtil.byte2HexStr(dencryptData));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN002",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN002() {
        Log.i(TAG, "CASE_dencryptData_CN002");
        byte masterkeyIndex = 0x01;
        loadDataKey_3des(masterkeyIndex);
        String exp = "6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000";
        byte[] dencryptData = API05dencryptData(1, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        if(dencryptData != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "解密后的数据：" + "\n" + StringUtil.byte2HexStr(dencryptData));
        }
        return exp.equals(StringUtil.byte2HexStr(dencryptData));
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN003",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN003() {
        Log.i(TAG, "CASE_dencryptData_CN003");
        byte masterkeyIndex = 0x64;
        loadDataKey_3des(masterkeyIndex);
        String exp = "6000120000130602007020078020C18309166253381182560271000000000000000001000633007200000012143706253381182560271D330620100000031000";
        byte[] dencryptData = API05dencryptData(100, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        if(dencryptData != null)
        {
            ToastUtil.showLong( "密钥索引：" + masterkeyIndex + "\n" + "解密后的数据：" + "\n" + StringUtil.byte2HexStr(dencryptData));
        }
        return exp.equals(StringUtil.byte2HexStr(dencryptData));
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN004",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN004() {
        Log.i(TAG, "CASE_dencryptData_CN004");
        byte[] ret = API05dencryptData(0, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN005",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN005() {
        Log.i(TAG, "CASE_dencryptData_CN005");
        byte[] ret = API05dencryptData(-1, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN006",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN006() {
        Log.i(TAG, "CASE_dencryptData_CN006");
        byte[] ret = API05dencryptData(101, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN007",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN007() {
        Log.i(TAG, "CASE_dencryptData_CN007");
        byte[] ret = API05dencryptData(Integer.MAX_VALUE, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN008",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN008() {
        Log.i(TAG, "CASE_dencryptData_CN008");
        byte[] ret = API05dencryptData(Integer.MAX_VALUE + 1, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN009",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN009() {
        Log.i(TAG, "CASE_dencryptData_CN009");
        byte[] ret = API05dencryptData(Integer.MIN_VALUE, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN010",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN010() {
        Log.i(TAG, "CASE_dencryptData_CN010");
        byte[] ret = API05dencryptData(Integer.MIN_VALUE - 1, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN011",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN011() {
        Log.i(TAG, "CASE_dencryptData_CN011");
        byte[] ret = API05dencryptData(10, null);
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN012",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN012() {
        Log.i(TAG, "CASE_dencryptData_CN012");
        byte[] ret = API05dencryptData(10, new byte[]{});
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN013",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN013() {
        Log.i(TAG, "CASE_dencryptData_CN013");
        byte[] ret = API05dencryptData(10, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_dencryptData_CN014",
            resDialogTitle = "", APIName = "dencryptData")
    public boolean CASE_dencryptData_CN014() {
        Log.i(TAG, "CASE_dencryptData_CN014");
        byte[] ret = API05dencryptData(100, StringUtil.hexStr2Bytes("BDC1B51778F4791B8B2DE3642F6A397E8D3FE39DB17356390B64930A929811FFB6D49F1E13653E5A0BB8146C050C5FBF6E27C3558F3931D0547291FFD89E4939"));
        return (ret == null);
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN001",
            resDialogTitle = "CASE_startPininput_CN001", APIName = "startPininput")
    public void CASE_startPininput_CN001() {
        Log.i(TAG, "CASE_startPininput_CN001");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data = " + StringUtil.byte2HexStr(data));
                ToastUtil.showLong("onConfirm data = " + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN002",
            resDialogTitle = "CASE_startPininput_CN002", APIName = "startPininput")
    public void CASE_startPininput_CN002() {
        Log.i(TAG, "CASE_startPininput_CN002");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data = " + StringUtil.byte2HexStr(data));
                ToastUtil.showLong("onConfirm data = " + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456789", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN003",
            resDialogTitle = "CASE_startPininput_CN003", APIName = "startPininput")
    public void CASE_startPininput_CN003() {
        Log.i(TAG, "CASE_startPininput_CN003");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data = " + StringUtil.byte2HexStr(data));
                ToastUtil.showLong("onConfirm data = " + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN004",
            resDialogTitle = "CASE_startPininput_CN004", APIName = "startPininput")
    public void CASE_startPininput_CN004() {
        Log.i(TAG, "CASE_startPininput_CN004");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data = " + StringUtil.byte2HexStr(data));
                ToastUtil.showLong("onConfirm data = " + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456789", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN005",
            resDialogTitle = "CASE_startPininput_CN005", APIName = "startPininput")
    public void CASE_startPininput_CN005() {
        Log.i(TAG, "CASE_startPininput_CN005");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(0, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN006",
            resDialogTitle = "CASE_startPininput_CN006", APIName = "startPininput")
    public void CASE_startPininput_CN006() {
        Log.i(TAG, "CASE_startPininput_CN006");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(101, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN007",
            resDialogTitle = "CASE_startPininput_CN007", APIName = "startPininput")
    public void CASE_startPininput_CN007() {
        Log.i(TAG, "CASE_startPininput_CN007");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(-1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN008",
            resDialogTitle = "CASE_startPininput_CN008", APIName = "startPininput")
    public void CASE_startPininput_CN008() {
        Log.i(TAG, "CASE_startPininput_CN008");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(Integer.MAX_VALUE, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN009",
            resDialogTitle = "CASE_startPininput_CN009", APIName = "startPininput")
    public void CASE_startPininput_CN009() {
        Log.i(TAG, "CASE_startPininput_CN009");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(Integer.MAX_VALUE + 1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN010",
            resDialogTitle = "CASE_startPininput_CN010", APIName = "startPininput")
    public void CASE_startPininput_CN010() {
        Log.i(TAG, "CASE_startPininput_CN010");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(Integer.MIN_VALUE, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN011",
            resDialogTitle = "CASE_startPininput_CN011", APIName = "startPininput")
    public void CASE_startPininput_CN011() {
        Log.i(TAG, "CASE_startPininput_CN011");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(Integer.MIN_VALUE - 1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN012",
            resDialogTitle = "CASE_startPininput_CN012", APIName = "startPininput")
    public void CASE_startPininput_CN012() {
        Log.i(TAG, "CASE_startPininput_CN012");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN013",
            resDialogTitle = "CASE_startPininput_CN013", APIName = "startPininput")
    public void CASE_startPininput_CN013() {
        Log.i(TAG, "CASE_startPininput_CN013");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "12345678901234567890", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN014",
            resDialogTitle = "CASE_startPininput_CN014", APIName = "startPininput")
    public void CASE_startPininput_CN014() {
        Log.i(TAG, "CASE_startPininput_CN014");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN015",
            resDialogTitle = "CASE_startPininput_CN015", APIName = "startPininput")
    public void CASE_startPininput_CN015() {
        Log.i(TAG, "CASE_startPininput_CN015");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, null, new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN016",
            resDialogTitle = "CASE_startPininput_CN016", APIName = "startPininput")
    public void CASE_startPininput_CN016() {
        Log.i(TAG, "CASE_startPininput_CN016");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "ABCDabcd", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN017",
            resDialogTitle = "CASE_startPininput_CN017", APIName = "startPininput")
    public void CASE_startPininput_CN017() {
        Log.i(TAG, "CASE_startPininput_CN017");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "~!@#$%^&*()_+{}|:\"<>?-=[]\\;\',./", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN018",
            resDialogTitle = "CASE_startPininput_CN018", APIName = "startPininput")
    public void CASE_startPininput_CN018() {
        Log.i(TAG, "CASE_startPininput_CN018");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "中国文字", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN019",
            resDialogTitle = "CASE_startPininput_CN019", APIName = "startPininput")
    public void CASE_startPininput_CN019() {
        Log.i(TAG, "CASE_startPininput_CN019");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN020",
            resDialogTitle = "CASE_startPininput_CN020", APIName = "startPininput")
    public void CASE_startPininput_CN020() {
        Log.i(TAG, "CASE_startPininput_CN020");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", null, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN021",
            resDialogTitle = "CASE_startPininput_CN021", APIName = "startPininput")
    public void CASE_startPininput_CN021() {
        Log.i(TAG, "CASE_startPininput_CN021");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{1, 2, 3}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN022",
            resDialogTitle = "CASE_startPininput_CN022", APIName = "startPininput")
    public void CASE_startPininput_CN022() {
        Log.i(TAG, "CASE_startPininput_CN022");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{13}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN023",
            resDialogTitle = "CASE_startPininput_CN023", APIName = "startPininput")
    public void CASE_startPininput_CN023() {
        Log.i(TAG, "CASE_startPininput_CN023");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, null);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN024",
            resDialogTitle = "CASE_startPininput_CN024", APIName = "startPininput")
    public void CASE_startPininput_CN024() {
        Log.i(TAG, "CASE_startPininput_CN024");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN025",
            resDialogTitle = "CASE_startPininput_CN025", APIName = "startPininput")
    public void CASE_startPininput_CN025() {
        Log.i(TAG, "CASE_startPininput_CN025");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN026",
            resDialogTitle = "CASE_startPininput_CN026", APIName = "startPininput")
    public void CASE_startPininput_CN026() {
        Log.i(TAG, "CASE_startPininput_CN026");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN027",
            resDialogTitle = "CASE_startPininput_CN027", APIName = "startPininput")
    public void CASE_startPininput_CN027() {
        Log.i(TAG, "CASE_startPininput_CN027");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN028",
            resDialogTitle = "CASE_startPininput_CN028", APIName = "startPininput")
    public void CASE_startPininput_CN028() {
        Log.i(TAG, "CASE_startPininput_CN028");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_startPininput_CN029",
            resDialogTitle = "CASE_startPininput_CN029", APIName = "startPininput")
    public void CASE_startPininput_CN029() {
        Log.i(TAG, "CASE_startPininput_CN029");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        CASE_startPininput_CN001();
        CASE_startPininput_CN002();
        CASE_startPininput_CN003();
        CASE_startPininput_CN004();
        CASE_startPininput_CN005();
        CASE_startPininput_CN006();
        CASE_startPininput_CN007();
        CASE_startPininput_CN008();
        CASE_startPininput_CN009();
        CASE_startPininput_CN010();
        CASE_startPininput_CN011();
        CASE_startPininput_CN012();
        CASE_startPininput_CN013();
        CASE_startPininput_CN014();
        CASE_startPininput_CN015();
        CASE_startPininput_CN016();
        CASE_startPininput_CN017();
        CASE_startPininput_CN018();
        CASE_startPininput_CN019();
        CASE_startPininput_CN020();
        CASE_startPininput_CN021();
        CASE_startPininput_CN022();
        CASE_startPininput_CN023();
        CASE_startPininput_CN024();
        CASE_startPininput_CN025();
        CASE_startPininput_CN026();
        CASE_startPininput_CN027();
        CASE_startPininput_CN028();
    }*/

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getRandom_CN001",
            resDialogTitle = "", APIName = "getRandom")
    public boolean CASE_getRandom_CN001() throws RemoteException {
        Log.i(TAG, "CASE_getRandom_CN001");
        String randomData = API07getRandom();
        if(randomData != null)
        {
            ToastUtil.showLong("randomData = " + randomData);
            Log.i(TAG,"randomData = " + randomData);
            return true;
        }
        return false;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_getRandom_CN002",
            resDialogTitle = "", APIName = "getRandom")
    public boolean CASE_getRandom_CN002() throws RemoteException {
        Log.i(TAG, "CASE_getRandom_CN002");
        for (int i = 0; i < 10; i++) {
            String randomData = API07getRandom();
            ToastUtil.showLong("randomData = " + randomData);
            Log.i(TAG,"randomData = " + randomData);
            if (randomData == null) {
                return false;
            }
        }
        return true;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_cancelPininput_CN001",
            resDialogTitle = "", APIName = "cancelPininput")
    public void CASE_cancelPininput_CN001() throws RemoteException {
        Log.i(TAG, "CASE_cancelPininput_CN001");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API08cancelPininput();
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_cancelPininput_CN002",
            resDialogTitle = "", APIName = "cancelPininput")
    public void CASE_cancelPininput_CN002() throws RemoteException {
        Log.i(TAG, "CASE_cancelPininput_CN002");
        AidlPinpadListener listener = new AidlPinpadListener.Stub() {
            @Override
            public void onKeyDown(int len, int key) throws RemoteException {
                Log.i(TAG, "len=" + len + " key=" + key);
            }

            @Override
            public void onPinRslt(byte[] data) throws RemoteException {
                Log.i(TAG, "data=" + StringUtil.byte2HexStr(data));
            }

            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "errorCode=" + errorCode + " Desc=" + errorDescription);
            }

            @Override
            public void onCancel() throws RemoteException {
                Log.i(TAG, "Cancel input");
            }
        };
        API06startPininput(1, "1234567890123456", new byte[]{0, 4, 5, 6, 7, 8, 9, 10, 11, 12}, listener);
        //十秒内输入密码
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API08cancelPininput();
    }


    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_setPinpadLayout_CN001",
            resDialogTitle = "", APIName = "setPinpadLayout")
    public void CASE_setPinpadLayout_CN001() {
        Log.i(TAG,"CASE_setPinpadLayout_CN001");
        CASE_loadWorkKey_CN001();   // 下载3DES_PinKey
        currentSetLayoutCallback = new CallbackSetLayout() {
            @Override
            public byte[] setPinpadLayout(byte[] layout) {
                byte[] iLayout =  API09setPinpadLayout(layout);
                Log.i(TAG,"PinpadLayout:" + StringUtil.byte2HexStr(iLayout));
                return iLayout;
            }
        };

        currentStartPininputCallback = new CallbackStartPininput() {
            @Override
            public void startPininput(int pinKeyIndex, String pan, byte[] lenLimit, AidlPinpadListener listener) {
                Log.i(TAG,"startPininput");
                API06startPininput(1,"1234567812345678",new byte[]{0,6,7,8,9,10,11,12}, listener);
            }
        };

        Intent intent = new Intent(MyApplication.getInstance().getMainActivity(), InputPinActivity.class);
        MyApplication.getInstance().getMainActivity().startActivity(intent);
    }

}
