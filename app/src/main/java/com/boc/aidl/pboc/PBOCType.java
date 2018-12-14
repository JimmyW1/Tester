package com.boc.aidl.pboc;

public class PBOCType {
	/**
	 * 消费
	 */
	public static final int CONSUME = 0x0B;
	/**
	 * 转账
	 */
	public static final int TRANS_ACCOUNT = 0x06;
	
	/**
	 * 预授权
	 */
	public static final int TRANS_PREAUTH = 0x0C;
	/**
	 * 联机账户余额查询
	 */
	public static final int TRANS_BALANCE = 0x0D;
	/**
	 * 电子现金指定账户圈存
	 */
	public static final int EC_APPOINTED_LOAD = 0x21;

	/**
	 * 电子现金非指定账户圈存
	 */
	public static final int EC_NOT_APPOINTED_LOAD = 0x22;

	/**
	 * 电子现金现金圈存
	 */
	public static final int EC_CASH_LOAD = 0x23;

	/**
	 * 电子现金日志
	 */
	public static final int PBOC_LOGGER = 0x0A;

	/**
	 * 电子现金余额查询
	 */
	public static final int EC_AVAILABLE_FUNDS_INQUIRY = 0x25;
	
	/**
	 * 圈存日志
	 */
	public static final int EC_LOGGER = 0x0E;

}
