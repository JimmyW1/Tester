package com.icbc.smartpos.deviceservice.aidl;

/**
 * 蜂鸣器对象
 * @author: baoxl
 */
interface IBeeper {
	/**
	 * 开始蜂鸣<br/>
	 * 通过调用该方法可以让POS的蜂鸣器持续鸣叫msec毫秒。如果用户设定msec为0则不进行鸣叫。
	 * 蜂鸣器发声是采用非阻塞方式进行的，调用该函数之后，程序立即退出，不会停留在该函数。　
	 * @param msec - 需要鸣叫的时间，单位ms
	 */
	void startBeep(int msec);
	
	/**
	 * 停止蜂鸣<br/>
	 * 调用该方法后立即停止鸣叫
	 */
	void stopBeep();
}
