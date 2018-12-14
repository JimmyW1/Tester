package com.boc.aidl.pboc;

public class CardType {
	/**
	 * 识别到卡类型返回
	 */
	private static final int CARD_TYPE_OFFSET = 0x100;
	/**
	 * 磁卡
	 */
	public static final int MAG_CARD = CARD_TYPE_OFFSET + 1;
	
	/**
	 * IC卡
	 */
	public static final int IC_CARD = CARD_TYPE_OFFSET + 2;
	/**
	 * 非接卡
	 */
	public static final int RF_CARD = CARD_TYPE_OFFSET + 3;
	/**
	 * 未知的卡类型
	 */
	public static final int CARD_TYPE_UNKNOWN = CARD_TYPE_OFFSET + 4;
}
