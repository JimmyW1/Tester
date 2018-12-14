package com.boc.aidl.pboc;

public class PBOCResultCode {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 0x00 ;
	/**
	 * 失败
	 */
	public static final int FAILED = 0x02 ;
	/**
	 * 要求联机
	 */
	public static final int REQUEST_ONLINE = 0x03;
	/**
	 * 交易拒绝
	 */
	public static final int EMV_TRANS_DENIAL = 0x04;
	
	/**
	 * 其他错误
	 */
	public static final int OHTER_ERROR = 255;
	
	
	
	
	public static final int EMV_ERR_SELECTAPP   =  -8;        /*应用选择失败*/
	public static final int EMV_ERR_APPINIT     =  -9;        /*应用初始化失败*/
	public static final int EMV_ERR_READAPPDATA =  -10;       /*读应用数据失败*/
	
	public static final int GACERR_EC_BALANCELACK  = -1822;        /*EC余额不足*/
	
	public static final int COMERR_BASE_ECLOADAMOUNT	=	-1903;			/*EC圈存金额超出限额*/
	
	public static final int RFERR_PREPROCESS_AMTLIMITOVER =   -2105 ;        /**< 预处理输入金额 超出限额 */
	public static final int RFERR_ECPURE_CANNOT_ONLINE    =   -2120;        /**< 纯电子现金卡不能联机 */
	
	public static class OpenCardResult {
		public static final int USER_CANCEL = 0x11;
		public static final int TIME_OUT = 0x12;
		public static final int FAILED = 0x13;
		public static final int OTHER_ERROR = 0x14;
	}
}
