package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.led.AidlLED;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleLed extends BaseModule {
    private static final String TAG = "ModuleLed";
    private AidlLED iLed;

    public ModuleLed() {
        this.iLed = DeviceService.getLed();
    }

    public boolean CASE_LedOn_CN001() {
        Log.i("TAG", "CASE_LedOn_CN001");
        try {
            return iLed.ledOperation(1, 1, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOn_CN002() {
        Log.i("TAG", "CASE_LedOn_CN002");
        try {
            return iLed.ledOperation(2, 1, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOn_CN003() {
        Log.i("TAG", "CASE_LedOn_CN003");
        try {
            return iLed.ledOperation(4, 1, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOn_CN004() {
        Log.i("TAG", "CASE_LedOn_CN004");
        try {
            return iLed.ledOperation(8, 1, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOff_CN001() {
        Log.i("TAG", "CASE_LedOff_CN001");
        try {
            return iLed.ledOperation(1, 0, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOff_CN002() {
        Log.i("TAG", "CASE_LedOff_CN002");
        try {
            return iLed.ledOperation(2, 0, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOff_CN003() {
        Log.i("TAG", "CASE_LedOff_CN003");
        try {
            return iLed.ledOperation(4, 0, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedOff_CN004() {
        Log.i("TAG", "CASE_LedOff_CN004");
        try {
            return iLed.ledOperation(8, 0, 10000);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN001() {
        Log.i("TAG", "CASE_LedBlink_CN001");
        try {
            return iLed.ledOperation(1, 2, 10);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN002() {
        Log.i("TAG", "CASE_LedBlink_CN002");
        try {
            return iLed.ledOperation(1, 2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN003() {
        Log.i("TAG", "CASE_LedBlink_CN003");
        try {
            return iLed.ledOperation(2, 2, 10);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN004() {
        Log.i("TAG", "CASE_LedBlink_CN004");
        try {
            return iLed.ledOperation(2, 2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN005() {
        Log.i("TAG", "CASE_LedBlink_CN005");
        try {
            return iLed.ledOperation(4, 2, 10);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN006() {
        Log.i("TAG", "CASE_LedBlink_CN006");
        try {
            return iLed.ledOperation(4, 2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN007() {
        Log.i("TAG", "CASE_LedBlink_CN007");
        try {
            return iLed.ledOperation(8, 2, 10);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CASE_LedBlink_CN008() {
        Log.i("TAG", "CASE_LedBlink_CN008");
        try {
            return iLed.ledOperation(8, 2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
