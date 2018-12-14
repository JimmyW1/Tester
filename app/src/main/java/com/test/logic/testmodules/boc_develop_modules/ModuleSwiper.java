package com.test.logic.testmodules.boc_develop_modules;

import android.os.RemoteException;

import com.boc.aidl.swiper.AidlSwiper;
import com.boc.aidl.swiper.SwiperResultInfo;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleSwiper extends BaseModule {
    private AidlSwiper iSwiper;
    public ModuleSwiper() {
        this.iSwiper = DeviceService.getSwiper();
    }

    public SwiperResultInfo API01readPlainResult(int readModel) {
        try {
            return iSwiper.readPlainResult(readModel);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public SwiperResultInfo API02readEncryptResult(int readModel, int wkindex) {
        try {
//            return iSwiper.readPlainResult(readModel);
            return  iSwiper.readEncryptResult(readModel, wkindex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public boolean CASE_readPlainResult_CN001() {
        SwiperResultInfo resultInfo = API01readPlainResult(0x01 | 0x02 | 0x04);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }

        return false;
    }

    public boolean CASE_readEncryptResult_CN001() {
        SwiperResultInfo resultInfo = API02readEncryptResult((0x01 | 0x02 | 0x04), 1);

        if (resultInfo != null) {
            showResult(resultInfo);
            return true;
        }

        return false;
    }

    private void showResult(SwiperResultInfo resultInfo) {
         if (resultInfo != null) {
             getResultTv().showInfoInNewLine("swipRslt=" + resultInfo.getSwipRslt());
             getResultTv().showInfoInNewLine("acctNo=" + resultInfo.getAcctNo());
             getResultTv().showInfoInNewLine("firstTrackData=" + resultInfo.getFirstTrackData());
             getResultTv().showInfoInNewLine("secondTrackData=" + resultInfo.getSecondTrackData());
             getResultTv().showInfoInNewLine("thirdTrackData=" + resultInfo.getThirdTrackData());
             getResultTv().showInfoInNewLine("validData=" + resultInfo.getValidDate());
             getResultTv().showInfoInNewLine("serviceCode=" + resultInfo.getServiceCode());
             getResultTv().showInfoInNewLine("ksn=" + resultInfo.getKsn());
             getResultTv().showInfoInNewLine("extInfo=" + resultInfo.getExtInfo());
         }
    }
}
