package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.led.AidlLED;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;

public class LedTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";

    public boolean API_led(int color, int operation, int times) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlLED.Stub.asInterface(handle.getLed()).ledOperation(color, operation, times);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN001 蓝灯常亮",
            resDialogTitle = "CASE_ledOperation_CN001", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN001() {
        Log.i(TAG, "CASE_ledOperation_CN001");
        return API_led(1, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN002 绿灯常亮",
            resDialogTitle = "CASE_ledOperation_CN002", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN002() {
        Log.i(TAG, "CASE_ledOperation_CN002");
        return API_led(2, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN003 黄灯常亮",
            resDialogTitle = "CASE_ledOperation_CN003", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN003() {
        Log.i(TAG, "CASE_ledOperation_CN003");
        return API_led(4, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN004 红灯常亮",
            resDialogTitle = "CASE_ledOperation_CN004", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN004() {
        Log.i(TAG, "CASE_ledOperation_CN004");
        return API_led(8, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN005 蓝灯灭",
            resDialogTitle = "CASE_ledOperation_CN005", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN005() {
        Log.i(TAG, "CASE_ledOperation_CN005");
        return API_led(1, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN006 绿灯灭",
            resDialogTitle = "CASE_ledOperation_CN006", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN006() {
        Log.i(TAG, "CASE_ledOperation_CN006");
        return API_led(2, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN007 黄灯灭",
            resDialogTitle = "CASE_ledOperation_CN007", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN007() {
        Log.i(TAG, "CASE_ledOperation_CN007");
        return API_led(4, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN008 红灯灭",
            resDialogTitle = "CASE_ledOperation_CN008", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN008() {
        Log.i(TAG, "CASE_ledOperation_CN008");
        return API_led(8, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN009 蓝灯无限闪烁",
            resDialogTitle = "CASE_ledOperation_CN009", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN009() {
        Log.i(TAG, "CASE_ledOperation_CN009");
        return API_led(1, 2, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN010 绿灯无限闪烁",
            resDialogTitle = "CASE_ledOperation_CN010", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN010() {
        Log.i(TAG, "CASE_ledOperation_CN010");
        API_led(1, 0, 1);
        return API_led(2, 2, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN011 黄灯无限闪烁",
            resDialogTitle = "CASE_ledOperation_CN011", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN011() {
        Log.i(TAG, "CASE_ledOperation_CN011");
        API_led(2, 0, 1);
        return API_led(4, 2, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN012 红灯无限闪烁",
            resDialogTitle = "CASE_ledOperation_CN012", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN012() {
        Log.i(TAG, "CASE_ledOperation_CN012");
        API_led(4, 0, 1);
        return API_led(8, 2, 0);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN013 蓝灯闪烁1次",
            resDialogTitle = "CASE_ledOperation_CN013", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN013() {
        API_led(8, 0, 1);
        Log.i(TAG, "CASE_ledOperation_CN013");
        return API_led(1, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN014 绿灯闪烁1次",
            resDialogTitle = "CASE_ledOperation_CN014", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN014() {
        Log.i(TAG, "CASE_ledOperation_CN014");
        return API_led(2, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN015 黄灯闪烁1次",
            resDialogTitle = "CASE_ledOperation_CN015", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN015() {
        Log.i(TAG, "CASE_ledOperation_CN015");
        return API_led(4, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN016 红灯闪烁1次",
            resDialogTitle = "CASE_ledOperation_CN016", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN016() {
        Log.i(TAG, "CASE_ledOperation_CN016");
        return API_led(8, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN017 蓝灯闪烁100次",
            resDialogTitle = "CASE_ledOperation_CN017", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN017() {
        Log.i(TAG, "CASE_ledOperation_CN017");
        boolean ret =  API_led(1, 2, 100);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_led(1, 0, 1);
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN018 绿灯闪烁100次",
            resDialogTitle = "CASE_ledOperation_CN018", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN018() {
        Log.i(TAG, "CASE_ledOperation_CN018");
        boolean ret =  API_led(2, 2, 100);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_led(2, 0, 1);
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN019 黄灯闪烁100次",
            resDialogTitle = "CASE_ledOperation_CN019", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN019() {
        Log.i(TAG, "CASE_ledOperation_CN019");
        boolean ret = API_led(4, 2, 100);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_led(4, 0, 1);
        return ret;
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_ledOperation_CN020 红灯闪烁100次",
            resDialogTitle = "CASE_ledOperation_CN020", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN020() {
        Log.i(TAG, "CASE_ledOperation_CN020");
        boolean ret = API_led(8, 2, 100);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_led(8, 0, 1);
        return ret;
    }

    /*@TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN021 所有灯均不灭",
            resDialogTitle = "CASE_ledOperation_CN021", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN021() {
        Log.i(TAG, "CASE_ledOperation_CN021");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return !API_led(0, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN022 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN022", APIName = "ledOperation")
    public boolean CASE_ledOperation_CN022() {
        Log.i(TAG, "CASE_ledOperation_CN022");
        API_led(1, 0, 1);
        API_led(2, 0, 1);
        API_led(4, 0, 1);
        API_led(8, 0, 1);
        return !API_led(0, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN023 所有灯均不闪烁",
            resDialogTitle = "CASE_ledOperation_CN023", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN023() {
        Log.i(TAG, "CASE_ledOperation_CN023");
        return !API_led(0, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN024 所有灯均不灭",
            resDialogTitle = "CASE_ledOperation_CN024", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN024() {
        Log.i(TAG, "CASE_ledOperation_CN024");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return !API_led(-1, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN025 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN025", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN025() {
        Log.i(TAG, "CASE_ledOperation_CN025");
        API_led(1, 0, 1);
        API_led(2, 0, 1);
        API_led(4, 0, 1);
        API_led(8, 0, 1);
        return !API_led(-1, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN026 所有灯均不闪烁",
            resDialogTitle = "CASE_ledOperation_CN026", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN026() {
        Log.i(TAG, "CASE_ledOperation_CN026");
        return !API_led(-1, 2, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN027 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN027", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN027() {
        Log.i(TAG, "CASE_ledOperation_CN027");
        return !API_led(java.lang.Integer.MAX_VALUE, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN028 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN028", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN028() {
        Log.i(TAG, "CASE_ledOperation_CN028");
        return !API_led(java.lang.Integer.MIN_VALUE, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN029 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN029", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN029() {
        Log.i(TAG, "CASE_ledOperation_CN029");
        return !API_led(3, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_ledOperation_CN030 所有灯均不亮",
            resDialogTitle = "CASE_ledOperation_CN030", APIName = "入参color异常取值")
    public boolean CASE_ledOperation_CN030() {
        Log.i(TAG, "CASE_ledOperation_CN030");
        return !API_led(7, 1, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN031 蓝灯既不亮也不闪烁",
            resDialogTitle = "CASE_ledOperation_CN031", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN031() {
        Log.i(TAG, "CASE_ledOperation_CN031");
        return !API_led(1, 3, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN032 蓝灯仍为常亮",
            resDialogTitle = "CASE_ledOperation_CN032", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN032() {
        Log.i(TAG, "CASE_ledOperation_CN032");
        API_led(1, 1, 1);
        return !API_led(1, 3, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN033 蓝灯仍为闪烁",
            resDialogTitle = "CASE_ledOperation_CN033", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN033() {
        Log.i(TAG, "CASE_ledOperation_CN033");
        API_led(1, 2, 0);
        return !API_led(1, 3, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN034 绿灯既不亮也不闪烁",
            resDialogTitle = "CASE_ledOperation_CN034", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN034() {
        Log.i(TAG, "CASE_ledOperation_CN034");
        return !API_led(2, -1, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN035 绿灯仍为常亮",
            resDialogTitle = "CASE_ledOperation_CN035", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN035() {
        Log.i(TAG, "CASE_ledOperation_CN035");
        API_led(2, 1, 1);
        return !API_led(2, -1, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN036 绿灯仍为闪烁",
            resDialogTitle = "CASE_ledOperation_CN036", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN036() {
        Log.i(TAG, "CASE_ledOperation_CN036");
        API_led(2, 2, 0);
        return !API_led(2, -1, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN037 黄灯既不亮也不闪烁",
            resDialogTitle = "CASE_ledOperation_CN037", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN037() {
        Log.i(TAG, "CASE_ledOperation_CN037");
        return !API_led(4, Integer.MAX_VALUE, 1);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_ledOperation_CN038 红灯既不亮也不闪烁",
            resDialogTitle = "CASE_ledOperation_CN038", APIName = "入参operation异常取值")
    public boolean CASE_ledOperation_CN038() {
        Log.i(TAG, "CASE_ledOperation_CN038");
        return !API_led(8, Integer.MIN_VALUE, 1);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_ledOperation_CN039 蓝灯不闪烁",
            resDialogTitle = "CASE_ledOperation_CN039", APIName = "入参times异常取值")
    public boolean CASE_ledOperation_CN039() {
        Log.i(TAG, "CASE_ledOperation_CN039");
        API_led(1, 0, 1);
        return !API_led(1, 2, -1);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_ledOperation_CN040 绿灯闪烁int最大值次",
            resDialogTitle = "CASE_ledOperation_CN040", APIName = "入参times异常取值")
    public boolean CASE_ledOperation_CN040() {
        Log.i(TAG, "CASE_ledOperation_CN040");
        return API_led(2, 2, Integer.MAX_VALUE);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_ledOperation_CN041 黄灯不闪烁",
            resDialogTitle = "CASE_ledOperation_CN041", APIName = "入参times异常取值")
    public boolean CASE_ledOperation_CN041() {
        Log.i(TAG, "CASE_ledOperation_CN041");
        API_led(2, 0, 1);
        return !API_led(4, 2, Integer.MIN_VALUE);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_ledOperation_CN042 所有灯均不亮也不闪烁",
            resDialogTitle = "CASE_ledOperation_CN042", APIName = "入参color、operation、times异常取值")
    public boolean CASE_ledOperation_CN042() {
        Log.i(TAG, "CASE_ledOperation_CN042");
        API_led(1, 0, 1);
        API_led(2, 0, 1);
        API_led(4, 0, 1);
        API_led(8, 0, 1);
        return !API_led(-1, -1, -1);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_ledOperation_CN043 所有灯均不熄灭",
            resDialogTitle = "CASE_ledOperation_CN043", APIName = "入参color、operation、times异常取值")
    public boolean CASE_ledOperation_CN043() {
        Log.i(TAG, "CASE_ledOperation_CN043");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return !API_led(-1, -1, -1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN047 蓝灯熄灭，其他灯仍为亮",
            resDialogTitle = "CASE_ledOperation_CN047", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN047() {
        Log.i(TAG, "CASE_ledOperation_CN047");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return API_led(1, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN048 绿灯熄灭，其他灯仍为亮",
            resDialogTitle = "CASE_ledOperation_CN048", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN048() {
        Log.i(TAG, "CASE_ledOperation_CN048");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return API_led(2, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN049 黄灯熄灭，其他灯仍为亮",
            resDialogTitle = "CASE_ledOperation_CN049", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN049() {
        Log.i(TAG, "CASE_ledOperation_CN049");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return API_led(4, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN050 红灯熄灭，其他灯仍为亮",
            resDialogTitle = "CASE_ledOperation_CN050", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN050() {
        Log.i(TAG, "CASE_ledOperation_CN050");
        API_led(1, 1, 1);
        API_led(2, 1, 1);
        API_led(4, 1, 1);
        API_led(8, 1, 1);
        return API_led(8, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN051 蓝灯熄灭，其他灯仍闪烁",
            resDialogTitle = "CASE_ledOperation_CN051", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN051() {
        Log.i(TAG, "CASE_ledOperation_CN051");
        API_led(1, 2, 0);
        API_led(2, 2, 0);
        API_led(4, 2, 0);
        API_led(8, 2, 0);
        return API_led(1, 0, 0);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN052 绿灯熄灭，其他灯仍闪烁",
            resDialogTitle = "CASE_ledOperation_CN052", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN052() {
        Log.i(TAG, "CASE_ledOperation_CN052");
        API_led(1, 2, 0);
        API_led(2, 2, 0);
        API_led(4, 2, 0);
        API_led(8, 2, 0);
        return API_led(2, 0, 0);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN053 黄灯熄灭，其他灯仍闪烁",
            resDialogTitle = "CASE_ledOperation_CN053", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN053() {
        Log.i(TAG, "CASE_ledOperation_CN053");
        API_led(1, 2, 0);
        API_led(2, 2, 0);
        API_led(4, 2, 0);
        API_led(8, 2, 0);
        return API_led(4, 0, 0);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN054 红灯熄灭，其他灯仍闪烁",
            resDialogTitle = "CASE_ledOperation_CN054", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN054() {
        Log.i(TAG, "CASE_ledOperation_CN054");
        API_led(1, 2, 0);
        API_led(2, 2, 0);
        API_led(4, 2, 0);
        API_led(8, 2, 0);
        return API_led(8, 0, 0);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN055 绿灯常亮，不响应第2次调用",
            resDialogTitle = "CASE_ledOperation_CN055", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN055() {
        Log.i(TAG, "CASE_ledOperation_CN055");
        API_led(2, 1, 1);
        return API_led(2, 1, 1);//第二次调用返回值待定
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN056 蓝灯闪烁5次后停止，再闪烁10次",
            resDialogTitle = "CASE_ledOperation_CN056", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN056() {
        Log.i(TAG, "CASE_ledOperation_CN056");
        return API_led(1, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_ledOperation_CN057 蓝灯闪烁10次，不响应第2次调用",
            resDialogTitle = "CASE_ledOperation_CN057", APIName = "逻辑测试")
    public boolean CASE_ledOperation_CN057() {
        Log.i(TAG, "CASE_ledOperation_CN057");
        return API_led(1, 0, 1);
    }

    @TestDetailCaseAnnotation(id = 8, itemDetailName = "CASE_ledOperation_CN058 所有灯响应正常",
            resDialogTitle = "CASE_ledOperation_CN058", APIName = "压力测试")
    public boolean CASE_ledOperation_CN058() {
        Log.i(TAG, "CASE_ledOperation_CN058");
        for(int i=0; i<100; i++)
        {
            if (!API_led(1, 1, 1)
            || !API_led(2, 1, 1)
            || !API_led(4, 1, 1)
            || !API_led(8, 1, 1)
            || !API_led(1, 0, 1)
            || !API_led(2, 0, 1)
            || !API_led(4, 0, 1)
            || !API_led(8, 0, 1))
            {
                return false;
            }
        }
        return true;
    }

    @TestDetailCaseAnnotation(id = 8, itemDetailName = "CASE_ledOperation_CN059 所有灯响应正常",
            resDialogTitle = "CASE_ledOperation_CN059", APIName = "压力测试")
    public boolean CASE_ledOperation_CN059() {
        Log.i(TAG, "CASE_ledOperation_CN059");
        for(int i=0; i<100; i++)
        {
            if (!API_led(1, 2, 0)
                    || !API_led(2, 2, 0)
                    || !API_led(4, 2, 0)
                    || !API_led(8, 2, 0)
                    || !API_led(1, 0, 1)
                    || !API_led(2, 0, 1)
                    || !API_led(4, 0, 1)
                    || !API_led(8, 0, 1))
            {
                return false;
            }
        }
        return true;
    }

    @TestDetailCaseAnnotation(id = 8, itemDetailName = "CASE_ledOperation_CN060 所有灯常亮24小时后关闭",
            resDialogTitle = "CASE_ledOperation_CN060", APIName = "压力测试")
    public boolean CASE_ledOperation_CN060() {
        Log.i(TAG, "CASE_ledOperation_CN060");
        if (!API_led(1, 1, 1)
                || !API_led(2, 1, 1)
                || !API_led(4, 1, 1)
                || !API_led(8, 1, 1))
        {
            return false;
        }
        try {
            Thread.sleep(24*60*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (API_led(1, 0, 1)&&API_led(2, 0, 1)&&API_led(4, 0, 1)&&API_led(8, 0, 1));
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_ledOperation_CN061 4个灯常亮",
            resDialogTitle = "CASE_ledOperation_CN061", APIName = "低电量测试")
    public boolean CASE_ledOperation_CN061() {
        Log.i(TAG, "CASE_ledOperation_CN061");
        return (API_led(1, 1, 1)&&API_led(2, 1, 1)&&API_led(4, 1, 1)&&API_led(8, 1, 1));
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_ledOperation_CN062 4个灯均被熄灭",
            resDialogTitle = "CASE_ledOperation_CN062", APIName = "低电量测试")
    public boolean CASE_ledOperation_CN062() {
        Log.i(TAG, "CASE_ledOperation_CN062");
        return (API_led(1, 1, 1)&&API_led(2, 1, 1)&&API_led(4, 1, 1)&&API_led(8, 1, 1)&&API_led(1, 0, 1)&&API_led(2, 0, 1)&&API_led(4, 0, 1)&&API_led(8, 0, 1));
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_ledOperation_CN063 4个灯闪烁",
            resDialogTitle = "CASE_ledOperation_CN063", APIName = "低电量测试")
    public boolean CASE_ledOperation_CN063() {
        Log.i(TAG, "CASE_ledOperation_CN063");
        return (API_led(1, 2, 0)&&API_led(2, 2, 0)&&API_led(4, 2, 0)&&API_led(8, 2, 0));
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_ledOperation_CN064 4个灯均被熄灭",
            resDialogTitle = "CASE_ledOperation_CN064", APIName = "低电量测试")
    public boolean CASE_ledOperation_CN064() {
        Log.i(TAG, "CASE_ledOperation_CN064");
        return (API_led(1, 2, 0)&&API_led(2, 2, 0)&&API_led(4, 2, 0)&&API_led(8, 2, 0)&&API_led(1, 0, 1)&&API_led(2, 0, 1)&&API_led(4, 0, 1)&&API_led(8, 0, 1));
    }
/*    @TestDetailCaseAnnotation(id = 2, itemDetailName = "add 测试相加case1",
            resDialogTitle = "", APIName = "add")
    public boolean _2_add_case_1_() {
        Log.d(TAG, "硬件无关测试");
        int a = 1;
        int b = 2;
        if (a + b == 3)
            return true;
        else
            return false;
    }*/
}
