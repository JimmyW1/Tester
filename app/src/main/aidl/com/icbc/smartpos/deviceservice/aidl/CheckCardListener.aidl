package com.icbc.smartpos.deviceservice.aidl;

/**
 * 检卡过程监听接口定义
 * @author: baoxl
 */
interface CheckCardListener {
	/**
	 * 检测到磁条卡
	 * @param track - 磁卡数据
	 * <ul>
     * <li>PAN(String) - 主账号（卡号）</li>
     * <li>TRACK1(String) - 磁道1数据</li>
     * <li>TRACK2(String) - 磁道2数据 </li>
     * <li>TRACK3(String) - 磁道3数据</li>
     * <li>SERVICE_CODE(String) - 服务码 </li>
     * <li>EXPIRED_DATE(String) - 卡片有效期 </li>
	 * </ul>
	 */
	void onCardSwiped(in Bundle track);
	
	/**
	 * IC卡上电成功
	 */
	void onCardPowerUp();
	
	/**
	 * 非接卡激活成功
	 */
	void onCardActivate();
	
	/**
	 * 检卡超时
	 */	
	void onTimeout();
	
	/**
	 * 检卡出错
	 * @param error - 错误码
	 * <ul>
	 * <li>SERVICE_CRASH(99) - 服务崩溃 </li>
	 * <li>REQUEST_EXCEPTION(100) - 请求异常</li>
	 * <li>MAG_SWIPE_ERROR(1) - 刷卡失败</li>
	 * <li>IC_INSERT_ERROR(2) - 插卡失败</li>
	 * <li>IC_POWERUP_ERROR(3) - 卡上电失败</li>
	 * <li>RF_PASS_ERROR(4) - 非接挥卡失败</li>
	 * <li>RF_ACTIVATE_ERROR(5) - 非接卡激活失败</li>
	 * </ul>
	 * @param message - 错误描述
	 */	
	void onError(int error, String message);
}
