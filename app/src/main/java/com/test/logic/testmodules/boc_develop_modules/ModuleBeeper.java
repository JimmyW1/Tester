package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.beeper.AidlBeeper;
import com.test.logic.annotations.CaseAttributes;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/2/28.
 */

public class ModuleBeeper extends BaseModule {
    private AidlBeeper iBeeper;

    public ModuleBeeper() {
        this.iBeeper = DeviceService.getBeeper();
    }

    public void API_beep(int times, float frequency, float voice) {
        try {
            iBeeper.beep(times, frequency, voice);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @CaseAttributes(visableLevel = 5)
    public boolean CASE_beep_CN001() {
        getDescriptionTv().showInfoInNewLine("Test 1,......");
        getResultTv().showInfo("----------------");
        Log.i("Cunche", "Beer -------------CN001-");
        API_beep(20, 0.7f, 0.4f);
        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_beep_CN003() {
        API_beep(0, 0f, 0f);
        return true;
    }

    public boolean CASE_beep_CN002() {
        Log.i("Cunche", "Beer -------------CN002-");
        API_beep(2, 2.0f, 3.0f);
        return true;
    }

    @CaseAttributes(isSupportAutoTest = true)
    public boolean CASE_bbbeep_CN002() {
        Log.i("Cunche", "Beer ---bbbeep----------CN002-");
        API_beep(5, 5.0f, 5.0f);
        return false;
    }

    public boolean CASE_bbbeep_CN003() {
        Log.i("Cunche", "Beer ---bbbeep----------CN002-");
        API_beep(5, 5.0f, 5.0f);
        return true;
    }
}
