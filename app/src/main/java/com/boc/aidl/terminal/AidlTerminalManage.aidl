package com.boc.aidl.terminal;

import android.content.Intent;

interface AidlTerminalManage {
	/**
	 * 设置设备内部时间<p>
	 * yyyyMMddHHmmss 
	 * 
	 * @param date
	 * @throws UnsupportedOperationException 当不支持该方法时抛出该异常
	 */
	void setDeviceDate(String date);
	
	/**
	 * 获得当前设备内时间<p>
	 * yyyyMMddHHmmss
	 * 
	 * @return
	 * @throws UnsupportedOperationException 当不支持该方法时抛出该异常
	 */
	String getDeviceDate();
}
