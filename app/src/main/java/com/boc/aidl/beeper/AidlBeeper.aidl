package com.boc.aidl.beeper;

interface AidlBeeper{
	/**
	 * 蜂鸣器发声，time为发声的时间长度,单位为ms，frequency 为发声频率，voice为发声大小
	 * @param times 循环次数,-1为无限循环
	 * @param frequency 发声频率(0.5~2.0)
	 * @param voice 发声大小(0.0~1.0)
	 */
	void beep(int times, float frequency, float voice); 
}