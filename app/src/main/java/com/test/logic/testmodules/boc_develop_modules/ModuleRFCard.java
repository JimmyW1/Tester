package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.rfcard.AidlRFCard;
import com.boc.aidl.rfcard.PowerOnRFResult;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleRFCard extends BaseModule {
    private AidlRFCard iRfcard;
    private final String TAG = "ModuleRFCard";

    public ModuleRFCard() {
        this.iRfcard = DeviceService.getRFCard();
    }

    private PowerOnRFResult API01_powerOn(final int rfCardType, int timeout) {
        try {
            return iRfcard.powerOn(rfCardType, timeout);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        LogUtil.e(TAG, "poweron failed.");
        return null;
    }


}
