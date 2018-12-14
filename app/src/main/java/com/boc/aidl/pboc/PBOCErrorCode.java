package com.boc.aidl.pboc;

public class PBOCErrorCode {
	/**
	 * 其他错误
	 */
	public static final int OTHER_ERROR = 0x8F;
	
	private static final int OFFSET = 0x300;
	/**
	 * pboc流程正在被占用
	 */
	public static final int PBOC_SERVICE_IS_OCCUPIED = OFFSET + 1;
	/**
	 * pboc流程没有要求pin输入
	 */
	public static final int NOT_REQUEST_INPUT_PIN = OFFSET + 2;
	/**
	 * pboc流程没有要求选择app
	 */
	public static final int NOT_REQUEST_SELECT_APP = OFFSET + 3;
	/**
	 * pboc流程没有要求确认卡片信息
	 */
	public static final int NOT_REQUEST_CONFIRM_CARD_INFO = OFFSET + 4;

	/**
	 * pboc流程没有被终止
	 */
	public static final int PBOC_SERVICE_IS_NOT_ABORTING = OFFSET + 5;
	
	/**
	 * 在QPBOC流程中
	 */
	public static final int PBOC_SERVICE_IS_IN_QPBOC = OFFSET + 6;

	/**
	 * PBOC流程还没有开始
	 */
	public static final int PBOC_HAS_NOT_START = OFFSET + 7;
	
	
	// EMV返回值定义
	public static final int EMV_RESULT_OFFSET = 0xFF00;
	/**
	 * FALLBACK
	 */
	public static final int EMV_FALLBACK = EMV_RESULT_OFFSET + 1;
	/**
	 * 交易失败，其它错误
	 */
	public static final int EMV_ERROR = EMV_RESULT_OFFSET + 2;
	/**
	 * 交易失败，脱机数据认证异常
	 */
	public static final int EMV_DATA_AUTH_FAIL = EMV_RESULT_OFFSET + 3;
	/**
	 * 交易失败，应用被锁
	 */
	public static final int EMV_APP_BLOCKED = EMV_RESULT_OFFSET + 4;
	/**
	 * 交易失败，非电子现金卡(该交易被设置为仅支持电子现金)
	 */
	public static final int EMV_NOT_ECCARD = EMV_RESULT_OFFSET + 5;
	/**
	 * 交易失败，该交易不支持电子现金卡
	 */
	public static final int EMV_UNSUPPORT_ECCARD = EMV_RESULT_OFFSET + 6;
	/**
	 * 交易失败，如果是纯电子现金卡交易，消费金额必须在限额以内
	 */
	public static final int EMV_AMOUNT_EXCEED_ON_PURELYEC = EMV_RESULT_OFFSET + 7;
	/**
	 * 交易失败，参数设置错误(9F7A参数)
	 */
	public static final int EMV_SET_PARAM_ERROR = EMV_RESULT_OFFSET + 8;
	/**
	 * 交易失败，主帐号与二磁道不符
	 */
	public static final int EMV_PAN_NOT_MATCH_TRACK2 = EMV_RESULT_OFFSET + 9;
	/**
	 * 交易失败，持卡人认证失败
	 */
	public static final int EMV_CARD_HOLDER_VALIDATE_ERROR = EMV_RESULT_OFFSET + 10;
	/**
	 * 交易失败，纯电子现金卡被拒绝交易
	 */
	public static final int EMV_PURELYEC_REJECT = EMV_RESULT_OFFSET + 11;
	/**
	 * 交易失败，余额不足
	 */
	public static final int EMV_BALANCE_INSUFFICIENT = EMV_RESULT_OFFSET + 12;
	/**
	 * 交易失败，交易金额超过非接限额检查
	 */
	public static final int EMV_AMOUNT_EXCEED_ON_RFLIMIT_CHECK = EMV_RESULT_OFFSET + 13;
	/**
	 * 交易失败，卡BIN检查失败
	 */
	public static final int EMV_CARD_BIN_CHECK_FAIL = EMV_RESULT_OFFSET + 14;
	/**
	 * 交易失败，卡被锁
	 */
	public static final int EMV_CARD_BLOCKED = EMV_RESULT_OFFSET + 15;
	/**
	 * 交易失败，多卡冲突
	 */
	public static final int EMV_MULTI_CARD_ERROR = EMV_RESULT_OFFSET + 16;
	/**
	 * 确认卡号、应用列表选择时用户取消
	 */
	public static final int EMV_CANCEL = EMV_RESULT_OFFSET + 17;
	/**
	 * 交易失败，交易金额超过电子现金限额检查
	 */
	public static final int EMV_AMOUNT_EXCEED_ON_ECLIMIT_CHECK = EMV_RESULT_OFFSET + 18;
	
}
