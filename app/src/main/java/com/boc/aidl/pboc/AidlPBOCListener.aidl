package com.boc.aidl.pboc;
import com.boc.aidl.pboc.MagCardInfo;
import com.boc.aidl.pboc.CardInfo;
import com.boc.aidl.pboc.AidEntry;
import com.boc.aidl.pboc.TransferLog;

interface AidlPBOCListener{
    /**
     * PBOC流程发生异常时触发参见PBOCResultCode
     */
    void onError(int errorCode,String errorDescription);

    /**
     * 寻卡成功
     * @param cardType 卡类型请参考Const.CardType类
     * @param data 当为刷卡时，使用MagCardInfo类进行读取，当为IC、RF时为null
     */
    void onFindingCard(int cardType, in MagCardInfo magCardInfo);
    
    /**
     * 要求选择应用
     * @param aidData 应用列表
     */
    void onSelectApplication(in AidEntry[] aidEntry);

   /**
    *是否使用电子现金（当卡片支持电子现金时触发）
    */
    void onEcSwitch();
    /**
     * 要求确认认证信息
     * @param certType 认证类型
     * @param certInfo 认证信息
     */
    void onConfirmCertInfo(String certType, String certInfo);
    
    /**
     * 要求确认卡信息
     * @param cardNo 卡号
     */
    void onConfirmCardInfo(in String cardNo);
    
    /**
     * 要求输入PIN
     * @param isOnlinePin 是否是联机pin
     * @param retryTimes 脱机pin的输入次数
     */
    void onRequestPinEntry(boolean isOnlinePin, int retryTimes);
    
    /**
     * PBOC行为分析后要求联机,根据实际需要主动调用AidlPBOC.readKernelData获取卡片数据
     */
    void onRequestOnline();
    
    /**
     * pboc流程结果,(包含交易成功与失败)
     *如需获取卡片数据，则调用AidlPBOC.readKernelData获取
     * @param resultCode 详见PBOCResultCode
     */
    void onPbocFinished(int resultCode);
    
    /**
     * 请求输入金额
     */
    void onRequestAmount();
    
    /**
     * 读取电子现金的余额
     * @param cardInfo 电子现金余额对象
     */
    void onReadECBalance(in CardInfo cardInfo);
    
    /**
     * 读取电子现金的交易记录集合
     * @param transLog pboc交易日志对象
     */
    void onReadPBOCLog(in List<TransferLog> transLog);
}