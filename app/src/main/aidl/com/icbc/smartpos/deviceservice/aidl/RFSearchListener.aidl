package com.icbc.smartpos.deviceservice.aidl;

/**
 * 非接寻卡过程监听接口定义
 * @author: baoxl
 */
interface RFSearchListener {
	/**
	 * 检测到磁条卡
	 * @param cardType - 卡类型
	 * <ul>
     * <li>S50_CARD(0x00) - S50卡</li>
     * <li>S70_CARD(0x01) - S70卡</li>
     * <li>PRO_CARD(0x02) - PRO卡</li>
     * <li>S50_PRO_CARD(0x03) - 支持S50驱动与PRO驱动的PRO卡</li>
     * <li>S70_PRO_CARD(0x04) - 支持S70驱动与PRO驱动的PRO卡 </li>
     * <li>CPU_CARD(0x05) - CPU卡</li>
	 * </ul>
	 */
	void onCardPass(int cardType);
	
	/**
	 * 寻卡失败回调
	 * @param error - 错误码
	 * <ul>
	 * <li>ERROR_TRANSERR(0xA2) - 通讯错误 </li>
	 * <li>ERROR_PROTERR(0xA3) - 卡片返回数据不符合规范要求</li>
	 * <li>ERROR_MULTIERR(0xA4) - 感应区内多卡存在</li>
	 * <li>ERROR_CARDTIMEOUT(0xA7) - 超时无响应</li>
	 * <li>ERROR_CARDNOACT(0xB3) - Pro卡或者TypeB卡未激活</li>
	 * <li>ERROR_MCSERVICE_CRASH(0xff01) - 主控服务异常</li>
	 * <li>ERROR_REQUEST_EXCEPTION(0xff02) - 请求异常</li>
	 * </ul>
	 * @param message - 错误描述
	 */
	void onFail(int error, String message);
}
