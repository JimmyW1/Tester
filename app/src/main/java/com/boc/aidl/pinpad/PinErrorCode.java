package com.boc.aidl.pinpad;

public class PinErrorCode {
	/**
	 * 密码输入中断
	 */
	public static final int INTERRUPT_ERROR = 0x01; 
	/**
	 *输入密码超时
	 */
	public static final int TIMEOUT_ERROR = 0x02;
	/**
	 *输入密码撤销
	 */
	public static final int CANCEL_ERROR = 0x03;
	/**
	 *其他错误
	 */
	public static final int OTHER_ERROR = 0x04;
}
