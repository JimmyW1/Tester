package com.boc.aidl.cardreader;
interface AidlCardReaderListener{
    /**
     * 读卡流程发生异常时触发
     */
    void onError(int errorCode,String errorDescription);

    /**
     * 寻卡成功
     * @param cardType 卡类型请参考Const.CardType类
     */
    void onFindingCard(int cardType);
}