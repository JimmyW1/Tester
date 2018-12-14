package com.boc.aidl.swiper;
import com.boc.aidl.swiper.SwiperResultInfo;

interface AidlSwiper {
    /**
     * 在读卡器刷磁条卡成功后，调用该方法读取磁条卡密文
     */
    SwiperResultInfo readEncryptResult(int readModel, int wkindex);

    /**
     * 在读卡器刷磁条卡成功后，调用该方法读取磁条卡明文
     */
    SwiperResultInfo readPlainResult(int readModel);

}
