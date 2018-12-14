package com.icbc.smartpos.deviceservice.aidl;

/**
 * PBOC交易流程回调接口
 * @author: baoxl
 */
interface PBOCHandler {
	/**
	 * 请求输入金额
	 */
	void onRequestAmount();
	
	/**
	 * 请求应用选择
	 * @param appList - 应用列表
	 */
	void onSelectApplication(in List<String> appList);
    
    /**
     * 要求确认卡信息
     * @param info - 卡片信息
     * <ul>
     * <li>PAN(String) - 主账号（卡号）</li>
     * <li>TRACK2(String) - 磁道2数据 </li>
     * <li>CARD_SN(String) - 卡片序列号 </li>
     * <li>SERVICE_CODE(String) - 服务码 </li>
     * <li>EXPIRED_DATE(String) - 卡片有效期 </li>
     * </ul>
     */
    void onConfirmCardInfo(in Bundle info);
    
    /**
     * 要求输入PIN
     * @param isOnlinePin 是否是联机pin
     * @param retryTimes 脱机pin的输入次数
     */
    void onRequestInputPIN(boolean isOnlinePin, int retryTimes);
    
    /**
     * 要求确认持卡人证件
     * @param certType - 认证类型
     * @param certInfo - 认证信息
     */
    void onConfirmCertInfo(String certType, String certInfo);
    
    /**
     * 联机处理请求
     * @param aaResult 卡片分析结果，联机数据
     * <ul>
     * <li>RESULT(int) - 结果类型：QPBOC_ARQC(201) - qPBOC联机请求; AARESULT_ARQC(2) - 行为分析结果ARQC</li>
     * <li>ARQC_DATA(String) - 联机请求卡片55域数据 </li>
     * <li>REVERSAL_DATA(String) - IC卡冲正TLV数据 </li>
     * </ul>
     */
    void onRequestOnlineProcess(in Bundle aaResult);
    
    /**
     * PBOC交易结果，见于简易流程，qPBOC，以及交易过程失败等
     * @param result - 交易结果
     * <ul>
     * <li>EMV_COMPLETE(9) - EMV简易流程结束 </li>
     * <li>EMV_ERROR(11) - EMV内核错误</li>
     * <li>EMV_FALLBACK(12) - FALLBACK </li>
     * <li>EMV_DATA_AUTH_FAIL(13) - 脱机数据认证失败 </li>
     * <li>EMV_APP_BLOCKED(14) - 应用被锁定 </li>
     * <li>EMV_NOT_ECCARD(15) - 非电子现金卡 </li>
     * <li>EMV_UNSUPPORT_ECCARD(16) - 该交易不支持电子现金卡 </li>
     * <li>EMV_AMOUNT_EXCEED_ON_PURELYEC(17) - 纯电子现金卡消费金额超限 </li>
     * <li>EMV_SET_PARAM_ERROR(18) - 参数设置错误(9F7A) </li>
     * <li>EMV_PAN_NOT_MATCH_TRACK2(19) - 主账号与二磁道不符 </li>
     * <li>EMV_CARD_HOLDER_VALIDATE_ERROR(20) - 持卡人认证失败 </li>
     * <li>EMV_PURELYEC_REJECT(21) - 纯电子现金卡被拒绝交易 </li>
     * <li>EMV_BALANCE_INSUFFICIENT(22) - 余额不足 </li>
     * <li>EMV_AMOUNT_EXCEED_ON_RFLIMIT_CHECK(23) - 交易金额超过非接限额检查 </li>
     * <li>EMV_CARD_BIN_CHECK_FAIL(24) - 卡BIN检查失败 </li>
     * <li>EMV_CARD_BLOCKED(25) - 卡被锁 </li>
     * <li>EMV_MULTI_CARD_ERROR(26) - 多卡冲突 </li>
     * <li>EMV_BALANCE_EXCEED(27) - 余额超出 </li>
     * <li>EMV_RFCARD_PASS_FAIL(60) - 挥卡失败 </li>
     * <li>EMV_IN_QPBOC_PROCESS(99) - QPBOC流程处理中 </li>
     *
     * <li>AARESULT_TC(0) - 行为分析结果，交易批准(脱机)</li>
     * <li>AARESULT_AAC(1) - 行为分析结果，交易拒绝 </li>
     * <li>QPBOC_AAC(202) - qPBOC交易结果，交易拒绝</li>
     * <li>QPBOC_ERROR(203) - qPBOC交易结果，交易失败 </li>
     * <li>QPBOC_TC(204) - qPBOC交易结果，交易批准 </li>
     * <li>QPBOC_CONT(205) - qPBOC结果，转接触式卡 </li>
     * <li>QPBOC_NO_APP(206) - qPBOC交易结果，无应用(可转UP Card)</li>
     * <li>QPBOC_NOT_CPU_CARD(207) - qPBOC交易结果，该卡非TYPE B/PRO卡</li>
     * </ul>
     *
     * @param data 交易结果数据
     * <ul>
     * <li>TC_DATA(String) - IC卡交易批准卡片返回数据 </li>
     * <li>REVERSAL_DATA(String) - IC卡冲正数据 </li>
     * <li>ERROR(String) - 错误描述(PBOC流程错误返回) </li>
     * </ul>
     */
    void onTransactionResult(int result, in Bundle data);
}
