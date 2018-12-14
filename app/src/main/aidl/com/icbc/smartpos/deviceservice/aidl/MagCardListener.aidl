package com.icbc.smartpos.deviceservice.aidl;

/**
 * 磁条卡刷卡结果回调
 * @author: baoxl
 */
interface MagCardListener {
	/**
	 * 刷卡成功回调
	 * @param track - 磁卡数据对象
     * <ul>
     * <li>PAN(String) - 主账号（卡号）</li>
     * <li>TRACK1(String) - 磁道1数据</li>
     * <li>TRACK2(String) - 磁道2数据 </li>
     * <li>TRACK3(String) - 磁道3数据</li>
     * <li>SERVICE_CODE(String) - 服务码 </li>
     * <li>EXPIRED_DATE(String) - 卡片有效期 </li>
     * </ul>
	 */
	void onSuccess(in Bundle track);

	/**
      * 刷卡失败回调
      * @param error - 错误码
      * <ul>
      * <li>SERVICE_CRASH(99) - 服务崩溃 </li>
      * <li>REQUEST_EXCEPTION(100) - 请求异常</li>
      * <li>MAG_SWIPE_ERROR(1) - 刷卡失败</li>
      * </ul>
      * @param message - 错误描述
      */
     void onError(int error,String message );
	
	/**
	 * 刷卡超时回调
	 */
	void onTimeout();
}
