package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.cardreader.AidlCardReader;
import com.boc.aidl.cardreader.AidlCardReaderListener;
import com.boc.aidl.constant.Const;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;
import com.test.util.LogUtil;
import com.test.util.ToastUtil;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleCardReader extends BaseModule {
    private AidlCardReader iCardReader;
    private  final String TAG = "ModuleCardReader";

    public ModuleCardReader() {
        this.iCardReader = DeviceService.getCardReader();
    }

    public void API01openCardReader(int openCardType, boolean isAllowfallback,boolean isMSDChecking, int timeout, AidlCardReaderListener cardReaderListener) {
        try {
            iCardReader.openCardReader(openCardType, isAllowfallback, isMSDChecking, timeout, cardReaderListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API02cancelCardRead() {
        try {
            iCardReader.cancelCardRead();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API03closeCardReader() {
        try {
            iCardReader.closeCardReader();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean CASE_openCardReader_CN001() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.MagicCard;
        getResultTv().showInfoInNewLine("开始磁条卡寻卡");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN002() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.MagicCard;
        getResultTv().showInfoInNewLine("开始磁条卡寻卡 true mscheck");
        API01openCardReader(supportCard, false, true, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN003() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.ICCard;
        getResultTv().showInfoInNewLine("开始IC卡寻卡 false false");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN004() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.ICCard;
        getResultTv().showInfoInNewLine("开始IC卡寻卡 true false");
        API01openCardReader(supportCard, true, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN005() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.ICCard;
        getResultTv().showInfoInNewLine("开始IC卡寻卡 true true");
        API01openCardReader(supportCard, true, true, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN006() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.RFCard;
        getResultTv().showInfoInNewLine("开始RF卡寻卡 false false");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN007() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.RFCard;
        supportCard |= Const.OpenCardType.ICCard;
        getResultTv().showInfoInNewLine("开始IC RF卡寻卡 false false");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN008() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.ICCard;
        supportCard |= Const.OpenCardType.MagicCard;
        getResultTv().showInfoInNewLine("开始IC Magic卡寻卡 false false");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }
    public boolean CASE_openCardReader_CN009() {
        int supportCard = 0;
        supportCard |= Const.OpenCardType.ICCard;
        supportCard |= Const.OpenCardType.MagicCard;
        supportCard |= Const.OpenCardType.RFCard;
        getResultTv().showInfoInNewLine("开始IC Magic RF卡寻卡 false false");
        API01openCardReader(supportCard, false, false, 60000, getNewCardReaderListener());
        return true;
    }

    public boolean CASE_cancelCardReader_CN001() {
        API02cancelCardRead();
        return true;
    }

    public boolean CASE_closeCardReader_CN001() {
        API03closeCardReader();
        return true;
    }

    AidlCardReaderListener getNewCardReaderListener() {
        return new AidlCardReaderListener.Stub() {
            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                LogUtil.d(TAG, "Code=" + errorCode);
                LogUtil.d(TAG, "Description=" + errorDescription);

                ToastUtil.showLong("onError[Code=" + errorCode + " Desc=" + errorDescription + "]");
            }

            @Override
            public void onFindingCard(int cardType) throws RemoteException {
                LogUtil.d(TAG, "Card type=" + cardType);
                ToastUtil.showLong("onFindingCard[CardType=" + cardType + "]");
            }
        };
    }
}
