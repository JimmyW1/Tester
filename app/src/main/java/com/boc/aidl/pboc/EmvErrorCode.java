package com.boc.aidl.pboc;

public class EmvErrorCode {
	/**
	 * 读重点配置失败
	 */
	public static final int EMV_ERR_READCONFIG  = 1;
	/**
	 * 读终端列表失败
	 */
	public static final int EMV_ERR_READAIDLIST = 2;
	/**
	 * IC卡无法上电
	 */
	public static final int EMV_ERR_POWERUP     = 3;
	/**
	 * IC卡不支持的指令
	 */
	public static final int EMV_ERR_NOTSUPPORT  = 4;
	/**
	 * 应用锁定
	 */
	public static final int EMV_ERR_APPBLOCK    = 5;
	/**
	 * 找不到支持的应用
	 */
	public static final int EMV_ERR_FINDAPP     = 6;
	/**
	 * 放弃交易
	 */
	public static final int EMV_ERR_Cancel      = 7;
	/**
	 * 应用选择失败
	 */
	public static final int EMV_ERR_SELECTAPP   = 8;
    /**
     * 应用初始化失败
     */
	public static final int EMV_ERR_APPINIT     = 9;
	/**
	 * 读应用数据失败
	 */
	public static final int EMV_ERR_READAPPDATA =  10;
	/**
	 * 脱机数据认证失败
	 */
	public static final int EMV_ERR_OFFAUTH      =11;
	/**
	 * 处理限制失败
	 */
	public static final int EMV_ERR_PROCESSLIMIT  = 12;
	/**
	 * 持卡人认证失败
	 */
	public static final int EMV_ERR_CARDVERIFY      = 13;
	/**
	 * 终端风险管理失败
	 */
	public static final int EMV_ERR_TERMRISKMANAGE   =  14;
	/**
	 * 终端行为分析失败
	 */
	public static final int EMV_ERR_TERMACTANALYZE    = 15;
	/**
	 * 不支持的服务
	 */
	public static final int EMV_ERR_NOTSUPPORTSERVICE  = 16;
	/**
	 * 无随机数
	 */
	public static final int EMV_ERR_NORANDNUM         =  17;
	/**
	 * 卡片锁定
	 */
	public static final int EMV_ERR_CARDBLOCK       =    18;
	

	/**
	 * PPSE命令返回失败
	 */
	public static final int PPSE_FAIL_ERR	 =   19;
	
	/**
	 * GPO数据打包失败
	 */
	public static final int INITERR_GPO_PACK_FIAL	 =   20;
	/**
	 * GPO命令返回失败
	 */
	public static final int INITERR_GPO_RETURN_FIAL	 =   21;
	/**
	 *电子现金拒绝 
	 */
	public static final int INITERR_ECONLY_DENIAL	 =   22;
	/**
	 * GPO返回错误
	 */
	public static final int INITERR_GPO_RETURN_6984  =   23;
	/**
	 * GPO卡片拒绝交易
	 */
	public static final int INITERR_GPO_DENIAL	  =  24;
	/**
	 * 非接检测到IC卡
	 */
	public static final int INITERR_RF_IC	  =  25;
	/**
	 * 非接应用不支持
	 */
	public static final int INITERR_RF_APPNOSUPPORT	  =  26;
	/**
	 * 纯电子现金卡不支持联机
	 */
	public static final int INITERR_RF_ECONLY_ONLINE  =  27;
	/**
	 * 纯电子现金卡不支持CVM
	 */
	public static final int INITERR_RF_ECONLY_CVM	 =  28;
	/**
	 * 非接检测到磁条卡
	 */
	public static final int INITERR_RF_STRIPE	    =    29;

	
	/**
	 * 读最后一条记录失败
	 */
	public static final int READREC_FAIL     	=	30;
	/**
	 * 纯电子现金卡无授权响应码
	 */
	public static final int READREC_NO9F74  		=	31;  /*EC*/
	/**
	 * 纯电子现金卡余额读取失败
	 */
	public static final int READREC_NO9F79  		=	32;
	/**
	 * 卡片已过期
	 */
	public static final int READREC_ERR_DATEEXPIRE	  =  33;	/*QPBOC*/
	/**
	 * 卡片未生效
	 */
	public static final int READREC_ERR_DATENOEFFECT  =  34;



	/**
	 * 电子现金余额不足
	 */
	public static final int GACERR_EC_BALANCELACK  = 35;        /*EC余额不足*/
	/**
	 * 纯电子现金卡要求EC联机则拒绝
	 */
	public static final int GACERR_ECONLY_GOONLINE = 36;        /*纯电子现金卡要求EC联机则拒绝*/
	/**
	 *EC余额<交易金额+阈值,转联机
	 */
	public static final int GACERR_EC_THRESHOLD    = 37;        /*EC 余额 <  交易金额 + 阈值,导致联机*/

	/**
	 * 脚本超限
	 */
	public static final int COMERR_BASE_SCRIPT			=   38;			/*脚本超限*/
	/**
	 * EC脚本空
	 */
	public static final int COMERR_BASE_SCRIPTEMPTY		=	39;			/*EC脚本空*/
	/**
	 * EC圈存金额超出限额
	 */
	public static final int COMERR_BASE_ECLOADAMOUNT	=	40;			/*EC圈存金额超出限额*/
	/**
	 * 脚本执行错误
	 */
	public static final int COMERR_BASE_SCRIPTRET		=	41;			/*脚本执行错误*/
	/**
	 * 脚本错误
	 */
	public static final int COMERR_BASE_SCRIPTERROR     =  42;			/*脚本错误*/ 

	
	/**
	 * 闪卡卡号不一致
	 */
	public static final int RFERR_CARDNO_ERROR    =  43;
	/**
	 * 闪卡ATC不一致
	 */
	public static final int RFERR_ATC_ERROR     =  44;
	/**
	 * 闪卡交易货币代码不一致
	 */
	public static final int RFERR_CURRENCY_ERROR     =  45;
	/**
	 * 闪卡余额不一致
	 */
	public static final int RFERR_BALANCELACK_ERROR     =  46;
	/**
	 * 闪卡未发生消费
	 */
	public static final int RFERR_NO_CONSUME     =  47;
	/**
	 * 闪卡读最后条记录无响应
	 */
	public static final int RFERR_READREC_ERROR     =  48;
	/**
	 * 闪卡未获取卡号
	 */
	public static final int RFERR_NOGET_CARDNO     =  49;
	/**
	 * 闪卡获取卡号不一致
	 */
	public static final int RFERR_GET_CARDNO_ERROR     =  50;
	/**
	 * 闪卡获取AID不一致
	 */
	public static final int RFERR_GET_AID_ERROR     =  51;
	/**
	 * 闪卡获取卡号无响应
	 */
	public static final int RFERR_GET_CARDNO_FAIL     =  52;
	
	
	/**
	 * 预处理参数文件错误
	 */
	public static final int RFERR_PREPROCESS_PARAFILE     =  53;
	/**
	 * 预处理输入金额用户退出
	 */
	public static final int RFERR_PREPROCESS_AMTQUIT      =  54;
	/**
	 *预处理输入金额超时
	 */
	public static final int RFERR_PREPROCESS_AMTTIMEOUT   =  55;
	/**
	 * 预处理输入金额失败
	 */
	public static final int RFERR_PREPROCESS_AMTFAIL      = 56 ;
	/**
	 * 预处理输入金额超出限额
	 */
	public static final int RFERR_PREPROCESS_AMTLIMITOVER = 57 ;
	/**
	 * 预处理要求联机,终端不能联机
	 */
	public static final int RFERR_PREPROCESS_REQONLINE    = 58 ;
	/**
	 * 射频卡去卡失败
	 */
	public static final int RFERR_ICCDEACTIVE             = 59;
	/**
	 * 卡片返回错误
	 */
	public static final int RFERR_ICCRETURNERROR          = 60;
	/**
	 * 读应用数据失败
	 */
	public static final int RFERR_READAPPDATA             =61;
	/**
	 * 卡片黑名单
	 */
	public static final int RFERR_BLKCARD                 = 62;

	/**
	 * 非接触QPBOC交易拒绝
	 */
	public static final int EMV_TRANS_QPBOC_DENIAL                 = 63;
	/**
	 * 卡片已失效
	 */
	public static final int RFERR_ICCEXPIRE               = 64;
	/**
	 * 卡片数据认证失败
	 */
	public static final int RFERR_DATAAUTH                = 65;
	/**
	 * 卡片二磁等价数据失败
	 */
	public static final int RFERR_TRACK2EDATA             = 66;
	/**
	 * 频度检查超限
	 */
	public static final int RFERR_ICCFCHECK               = 67;
	/**
	 * 纯电子现金卡不能联机
	 */
	public static final int RFERR_ECPURE_CANNOT_ONLINE    = 68;
	/**
	 * 卡片拒绝
	 */
	public static final int RFERR_CARD_DENIAL             = 69;
	/**
	 *卡片AIP没有数据认证
	 */
	public static final int RFERR_NOODA                   = 70;
	/**
	 * 卡片9F10中返回交易结果错误
	 */
	public static final int RFERR_9F10CID                 = 71;
	/**
	 * 交易拒绝
	 */
	public static final int EMV_TRANS_DENIAL                 = 72;
	/**
	 * 未定义错误
	 */
	public static final int UNKNOW_ERROR = 99;
}
