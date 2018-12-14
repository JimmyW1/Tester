package com.boc.aidl.led;

interface AidlLED{
	/**
	 * Led控制
	 * @param color 灯颜色，详见LedConst.LedColor
	 * @param operation 操作，详见 .LedOperation
	 * @param times 闪烁次数(当操作类型为闪烁时生效)
	 * @return 控制结果
	 */
	boolean  ledOperation(int color, int operation,int times);
}