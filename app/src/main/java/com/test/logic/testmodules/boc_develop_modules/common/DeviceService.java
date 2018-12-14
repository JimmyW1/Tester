package com.test.logic.testmodules.boc_develop_modules.common;

import android.os.RemoteException;

import com.boc.aidl.beeper.AidlBeeper;
import com.boc.aidl.cardreader.AidlCardReader;
import com.boc.aidl.deviceInfo.AidlDeviceInfo;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.iccard.AidlICCard;
import com.boc.aidl.led.AidlLED;
import com.boc.aidl.pboc.AidlPBOC;
import com.boc.aidl.pinpad.AidlPinpad;
import com.boc.aidl.printer.AidlPrinter;
import com.boc.aidl.rfcard.AidlRFCard;
import com.boc.aidl.scanner.AidlScanner;
import com.boc.aidl.swiper.AidlSwiper;
import com.boc.aidl.terminal.AidlTerminalManage;
import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.ui.activities.MyApplication;

/**
 * Created by CuncheW1 on 2017/8/7.
 */

public class DeviceService {
    private static AidlDeviceService handle;

    public static AidlDeviceService getDeviceService() {
        ServiceAccesser accesser = MyApplication.getInstance().getCurrentTestEntry().getServiceAccesser();
        if (accesser.getConnectStatus() == false) {
            return null;
        }

        if (handle == null) {
            handle = AidlDeviceService.Stub.asInterface(accesser.getServiceBinder());
        }

        return handle;
    }

    public static AidlDeviceInfo getDeviceInfo() {
        try {
            return AidlDeviceInfo.Stub.asInterface(getDeviceService().getDeviceInfo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlPinpad getPinpad() {
        try {
            return AidlPinpad.Stub.asInterface(getDeviceService().getPinpad());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlPBOC getPBOC() {
        try {
            return AidlPBOC.Stub.asInterface(getDeviceService().getPBOC());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AidlPrinter getPrinter() {
        try {
            return AidlPrinter.Stub.asInterface(getDeviceService().getPrinter());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AidlICCard getICCard() {
        try {
            return AidlICCard.Stub.asInterface(getDeviceService().getICCard());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlRFCard getRFCard() {
        try {
            return AidlRFCard.Stub.asInterface(getDeviceService().getRFCard());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlScanner getScanner() {
        try {
            return AidlScanner.Stub.asInterface(getDeviceService().getScan());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlBeeper getBeeper() {
        try {
            return AidlBeeper.Stub.asInterface(getDeviceService().getBeeper());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlLED getLed() {
        try {
            return AidlLED.Stub.asInterface(getDeviceService().getLed());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlTerminalManage getTerminalManager() {
        try {
            return AidlTerminalManage.Stub.asInterface(getDeviceService().getTerminalManage());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlSwiper getSwiper() {
        try {
            return AidlSwiper.Stub.asInterface(getDeviceService().getSwiper());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static AidlCardReader getCardReader() {
        try {
            return AidlCardReader.Stub.asInterface(getDeviceService().getCardReader());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
