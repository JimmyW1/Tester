package com.test.logic.testmodules.boc_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.beeper.AidlBeeper;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;

public class BeepTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";

    public void API_beep(int times, float frequency, float voice) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlBeeper.Stub.asInterface(handle.getBeeper()).beep(times, frequency, voice);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_beep_CN001 蜂鸣器响应1次，声音大小为0",
            resDialogTitle ="CASE_beep_CN001", APIName = "beep")
    public void CASE_beep_CN001() {
        Log.i(TAG, "CASE_beep_CN001");
        API_beep(1, 0.5f, 0.0f);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_beep_CN002 蜂鸣器响应1次",
            resDialogTitle ="CASE_beep_CN002", APIName = "beep")
    public void CASE_beep_CN002() {
        Log.i(TAG, "CASE_beep_CN002");
        API_beep(1, 0.5f, 0.5f);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_beep_CN003 蜂鸣器无限循环响应",
            resDialogTitle ="CASE_beep_CN003", APIName = "beep")
    public void CASE_beep_CN003() {
        Log.i(TAG, "CASE_beep_CN003");
        API_beep(-1, 2.0f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_beep_CN004 停止蜂鸣",
            resDialogTitle ="CASE_beep_CN004", APIName = "beep")
    public void CASE_beep_CN004() {
        Log.i(TAG, "CASE_beep_CN004");
        CASE_beep_CN003();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_beep(0, 0f, 0f);
    }

    /*@TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_beep_CN005 蜂鸣器正常响应，响应次数为int最大值",
            resDialogTitle ="CASE_beep_CN005", APIName = "入参times异常取值")
    public void CASE_beep_CN005() {
        Log.i(TAG, "CASE_beep_CN005");
        API_beep(Integer.MAX_VALUE, 2.0f, 1.0f);
    }


    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_beep_CN006 蜂鸣器无响应，调用后输出\"BeeperManager:times not available, times=int最小值\"",
            resDialogTitle ="CASE_beep_CN006", APIName = "入参times异常取值")
    public void CASE_beep_CN006() {
        Log.i(TAG, "CASE_beep_CN006");
        API_beep(Integer.MIN_VALUE, 2.0f, 1.0f);
    }

/*    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_beep_CN007 编译不通过",
            resDialogTitle ="CASE_beep_CN007", APIName = "入参times异常取值")
    public void CASE_beep_CN007() {
        Log.i(TAG, "CASE_beep_CN007");
        API_beep(Integer.MAX_VALUE+1, 2.0f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_beep_CN008 编译不通过",
            resDialogTitle ="CASE_beep_CN008", APIName = "入参times异常取值")
    public void CASE_beep_CN008() {
        Log.i(TAG, "CASE_beep_CN008");
        API_beep(Integer.MIN_VALUE-1, 2.0f, 1.0f);
    }*/

    /*@TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_beep_CN009 蜂鸣器无响应，调用后输出\"BeeperManager:times not available, times=0\"",
            resDialogTitle ="CASE_beep_CN009", APIName = "入参times异常取值")
    public void CASE_beep_CN009() {
        Log.i(TAG, "CASE_beep_CN009");
        API_beep(0, 2.0f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_beep_CN010 蜂鸣器无响应，日志输出\"BeeperManager:frequency not available, frquency=0.4\"",
            resDialogTitle ="CASE_beep_CN010", APIName = "入参frequency异常边界值")
    public void CASE_beep_CN010() {
        Log.i(TAG, "CASE_beep_CN010");
        API_beep(1, 0.4f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_beep_CN011 蜂鸣器无响应，日志输出\"BeeperManager:frequency not available, frquency=2.1\"",
            resDialogTitle ="CASE_beep_CN011", APIName = "入参frequency异常边界值")
    public void CASE_beep_CN011() {
        Log.i(TAG, "CASE_beep_CN011");
        API_beep(1, 2.1f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_beep_CN012 蜂鸣器无响应，日志输出\"BeeperManager:frequency not available, frquency=0\"",
            resDialogTitle ="CASE_beep_CN012", APIName = "入参frequency异常取值")
    public void CASE_beep_CN012() {
        Log.i(TAG, "CASE_beep_CN012");
        API_beep(1, 0f, 0f);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_beep_CN013 蜂鸣器无响应，日志输出\"BeeperManager:frequency not available, frquency=-1.0\"",
            resDialogTitle ="CASE_beep_CN013", APIName = "入参frequency异常取值")
    public void CASE_beep_CN013() {
        Log.i(TAG, "CASE_beep_CN013");
        API_beep(1, -1.0f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_beep_CN014 蜂鸣器无响应，日志输出\"BeeperManager:frequency not available, frquency=2.22循环\"",
            resDialogTitle ="CASE_beep_CN014", APIName = "入参frequency异常取值")
    public void CASE_beep_CN014() {
        Log.i(TAG, "CASE_beep_CN014");
        API_beep(1, 2.22f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_beep_CN015 蜂鸣器无响应，调用后输出\"BeeperManager:voice not available, voice=-1.0\"",
            resDialogTitle ="CASE_beep_CN015", APIName = "入参voice异常边界值")
    public void CASE_beep_CN015() {
        Log.i(TAG, "CASE_beep_CN015");
        API_beep(1, 2.0f, -1.0f);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_beep_CN016 蜂鸣器无响应，调用后输出\"BeeperManager:voice not available, voice=1.1\"",
            resDialogTitle ="CASE_beep_CN016", APIName = "入参voice异常边界值")
    public void CASE_beep_CN016() {
        Log.i(TAG, "CASE_beep_CN016");
        API_beep(1, 2.0f, 1.1f);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_beep_CN017 蜂鸣器无响应，调用后输出\"BeeperManager:voice not available, voice=1.11循环\"",
            resDialogTitle ="CASE_beep_CN017", APIName = "入参voice异常取值")
    public void CASE_beep_CN017() {
        Log.i(TAG, "CASE_beep_CN017");
        API_beep(1, 2.0f, 1.11f);
    }

/*
    @TestDetailCaseAnnotation(id = 6, itemDetailName = "CASE_beep_CN018 编译不通过",
            resDialogTitle ="CASE_beep_CN018", APIName = "入参为非法类型")
    public void CASE_beep_CN018() {
        Log.i(TAG, "CASE_beep_CN018");
        API_beep(1f, 2.0, 1.11);
    }*/

   /* @TestDetailCaseAnnotation(id = 7, itemDetailName = "CASE_beep_CN019 蜂鸣器每间隔5秒蜂鸣一次，总计蜂鸣100次",
            resDialogTitle ="CASE_beep_CN019", APIName = "间隔性调用beep()")
    public void CASE_beep_CN019() {
        Log.i(TAG, "CASE_beep_CN019");
        for (int i=0; i<100; i++) {
            API_beep(1, 2.0f, 1.0f);
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TestDetailCaseAnnotation(id = 8, itemDetailName = "CASE_beep_CN020 蜂鸣器正常蜂鸣100次，中间无间隔",
            resDialogTitle ="CASE_beep_CN020", APIName = "连续性调用beep()")
    public void CASE_beep_CN020() {
        Log.i(TAG, "CASE_beep_CN020");
        for (int i=0; i<100; i++) {
            API_beep(1, 2.0f, 1.0f);
            API_beep(0, 0, 0);
        }
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_beep_CN025 蜂鸣器正常响应1次",
            resDialogTitle ="CASE_beep_CN025", APIName = "低电量测试")
    public void CASE_beep_CN025() {
        Log.i(TAG, "CASE_beep_CN025");
            API_beep(1, 1.0f, 1.0f);
    }

    @TestDetailCaseAnnotation(id = 9, itemDetailName = "CASE_beep_CN026 蜂鸣器正常蜂鸣100次，停止蜂鸣",
            resDialogTitle ="CASE_beep_CN026", APIName = "低电量测试")
    public void CASE_beep_CN026() {
        Log.i(TAG, "CASE_beep_CN026");
        API_beep(-1, 2.0f, 1.0f);
        API_beep(0, 0f, 0f);
    }*/
}
