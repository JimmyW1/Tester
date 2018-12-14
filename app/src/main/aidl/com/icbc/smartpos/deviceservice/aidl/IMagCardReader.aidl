package com.icbc.smartpos.deviceservice.aidl;

import com.icbc.smartpos.deviceservice.aidl.MagCardListener;

/**
 * 磁条卡刷卡器对象
 * @author: baoxl
 */
interface IMagCardReader {
	/**
	 * 等待刷卡，获取磁卡卡片的磁道数据
	 * @param timeout - 检卡超时时间(单位秒)
	 * @param listener - 磁卡刷卡监听器
	 */
	void searchCard(int timeout, MagCardListener listener);
	
	/**
	 * 取消等待刷卡
	 */
	void stopSearch();
}
