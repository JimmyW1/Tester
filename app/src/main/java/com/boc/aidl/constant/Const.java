package com.boc.aidl.constant;

/**
 * Created by CuncheW1 on 2017/3/13.
 */
public class Const {

	public static class KeyType {
		/**
		 * pin密钥
		 */
		public static final String PIN_KEY = "PIN_KEY";
		/**
		 * 磁道密钥
		 */
		public static final String TRACK_KEY = "TRACK_KEY";

		/**
		 * mac密钥
		 */
		public static final String MAC_KEY = "MAC_KEY";
	}

	public static class ScanType {
		/**
		 * 前置扫码
		 */
		public static final int FRONT = 0;
		/**
		 * 后置扫码
		 */
		public static final int BACK = 1;
	}

	public static class OpenCardType {
		/**
		 * 磁条卡
		 */
		public static final int MagicCard = 0x01;
		/**
		 * ic卡
		 */
		public static final int ICCard = 0x02;
		/**
		 * 非接卡
		 */
		public static final int RFCard = 0x04;
		
	}
	
	/**
	 *打印内容的位置
	 *
	 */
	public static class PrintContentPosition{
		/**
		 * 靠左
		 */
		public static final int LEFT = 0x01;
		/**
		 * 居中
		 */
		public static final int CENTER = 0x02;
		/**
		 * 靠右
		 */
		public static final int RIGHT = 0x03;
		
	}
	
	public static class ReadModel{
		/**
		 * 第一磁道
		 */
		public static final int READ_FIRST_TRACK = 0x01;
		/**
		 * 第二磁道
		 */
		public static final int READ_SECOND_TRACK = 0x02;
		/**
		 * 第三磁道
		 */
		public static final int READ_THIRD_TRACK = 0x04;
	}
	
	public static class MacType{
		public static final int MAC_X99 = 0x01;
		public static final int MAC_X919 = 0x02;
		public static final int MAC_ECB = 0x03;
		public static final int MAC_9606 = 0x04;
		public static final int MAC_CBC =0x05;
	}
	
	public static class KeyMode{
		public static final int ECB = 0x01;
		public static final int CBC =0x02;
	}
	
	public static class SwipResultType{
		/**
		 * 处理成功
		 */
		public static final int SUCCESS = 0x01;
		/**
		 * 参数错误
		 */
		public static final int PARAM_ERROR = 0x02;
		/**
		 * 数据域长度错误
		 */
		public static final int DATALENGTH_ERROR = 0x03;
		/**
		 * 长度错误
		 */
		public static final int LENGTH_ERROR = 0x04;
		/**
		 * TYPE错误
		 */
		public static final int TYPE_ERROR = 0x05;
		/**
		 * 读取磁条卡数据格式错误
		 */
		public static final int DATAFORMAT_ERROR = 0x06;
		/**
		 * 读取磁条卡数据超时
		 */
		public static final int READTRACK_TIMEOUT = 0x07;
		/**
		 * 读取磁条卡刷卡失败
		 */
		public static final int SWIP_FAILED = 0x08;
	}
}
