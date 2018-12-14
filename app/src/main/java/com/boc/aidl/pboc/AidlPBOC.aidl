package com.boc.aidl.pboc;

import com.boc.aidl.pboc.AidEntry;
import com.boc.aidl.pboc.AidlPBOCListener;
import com.boc.aidl.pboc.OnlineResultData;

interface AidlPBOC {

   /**
    * 开启交易
    * @param openCardType 打开读卡器类型使用或运算 参见Const.OpenCardType
    * @param pbocType 交易类型
    * @param amount 消费金额，以分为单位
    * @param isForceOnline 是否强制联机
    * @param listener pboc监听回调
    */
	void startTransfer(int openCardType, int pbocType,long amount,boolean isForceOnline, AidlPBOCListener listener);
	 
    /**
     * 取消检卡
     */ 
	 void cancelCheckCard();
    /**
     * pboc应用选择
     * 当pboc流程回调请求应用选择时(onSelectApplication)，app选择应用后调用
     * @param aid 选中的aid
     */    
    void selectApplication(in byte[] aid);

   /**
     *电子现金选择
     *当服务回调onEcSwitch监听时,app调用该方法进行反馈
	 * <li>1：继续电子现金交易</li>
	 * <li>0：不进行电子现金交易</li>
	 * <li>－1:用户中止</li>
	 * <li>－3:超时</li>
    */
    void confirmEcSwitch(int code);
    
    /**
     * 当交易未输入金额时回调触发
     * @param amount 金额，以分为单位
     */
    void importAmount(long amount);
    /**
     * 密码输入
     *当服务回调onRequestInputPIN监听时,app输入密码后,调用该方法进行反馈
     * @param pin 密码
     */
    void comfirmPinInput(in byte[] pin);
   /**
     * 撤销密码输入
     *当服务回调onRequestInputPIN监听时,app取消密码输入后,调用该方法进行反馈
     * @param pin 密码
     */
    void cancelPinInput();
    /**
     * 确认卡号信息
     *当服务回调onConfirmCardInfo监听时，app调用该方法进行反馈
     * @param isConfirm 是否核实正确
     */
    void confirmCardInfo(boolean isConfirm);
    
    /**
     * 确认身份信息
     *当服务回调onConfirmCerdInfo监听时，app调用该方法进行反馈
     * @param isConfirm 是否核实正确
     */
    void confirmCerInfo(boolean isConfirm);
    
    /**
     * 获取当次emv交易的卡片相关数据
     *当需要获取卡片数据时调用（如联机请求时）
     * @param taglist[] 卡片相关数据的tag
     */
    byte[] readKernelData(in String[] emvTag);
    
    /**
     * 联机交易成功后二次授权
     *当服务回调onRequestOnline监听时，app发起联机交易成功后，调用该方法进行反馈
     * @param respData 联机交易数据
     */
    void secondIssuance(in OnlineResultData resultData);
    
   /**
	 * 终止emv交易(如非接联机交易后根据交易结果调用该方法完成emv流程)
	 */
	void endPboc(boolean isSuccess);
    /**
     * 更新AID参数
     * @param operation 使用PBOCParamOper中的变量
     * @param dataSource  aid数据
     * @return 成功结束返回true
     */
    boolean updateAID(int operation, String dataSource);

    /**
     * 更新CAPK参数
     * @param operation 使用PBOCParamOper中的变量
     * @param dataSource 公钥数据
     * @return 成功结束返回true
     */
    boolean updateCAPK(int operation, String dataSource);
    
    /**
     * 获取失败的具体错误码
     */
    int fetchErrorCode();
}