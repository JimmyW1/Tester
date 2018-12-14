package com.boc.aidl.constant;

/**
 * Created by CuncheW1 on 2017/3/13.
 */

public class DeviceErrorCode {
	
	/**
	 * 打开读卡器失败
	 */
	public static final int OPEN_CARDREADER_ERROR=20001;
	/**
	 * 该业务只支持刷卡
	 */
	public static final int SWIPCARD_ONLY=20002;
	/**
	 * aidl远程回调异常
	 */
	public static final int REMOTE_COMM_ERROR=20003;
	/**
	 * pboc过程异常
	 */
	public static final int PBOC_ERROR=20004;
	/**
	 * pboc事务忙
	 */
	public static final int PBOC_BUSY=20005;
	/**
	 * 磁卡数据读取失败
	 */
	public static final int SWIPER_DATA_ERROR=20006;
	/**
	 * 操作标识不正确
	 */
	public static final int OPERATE_FLAG_ERROR=20007;
	/**
	 * 刷卡失败
	 */
	public static final int MSDDATA_ERROR=20008;
	
	/**
	 * 打开读卡器超时
	 */
	public static final int OPEN_CARDREADER_OUTTIME=20009;
	
	/**
	 * 取消读卡
	 */
	public static final int OPEN_CARDREADER_CANCEL=20010;
	
	/**
	 * 二次授权异常
	 */
	public static final int PBOC_SECONDISSUANCE_ERROR=20011;
	/**
	 * 卡片降级
	 */
	public static final int PBOC_FALLBACK=20012;
	
	/**
	 * 圈存日志读取失败
	 */
	public static final int PBOC_EC_LOGGER_ERROR=20013;
	
	/**
	 * 电子现金日志读取失败
	 */
	public static final int PBOC_LOGGER_ERROR=20014;
	
	/**
	 * 电子现金余额查询失败
	 */
	public static final int PBOC_EC_INQUIRY_ERROR=20015;
	

}
