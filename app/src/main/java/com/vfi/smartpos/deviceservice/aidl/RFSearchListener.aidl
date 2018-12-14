package com.vfi.smartpos.deviceservice.aidl;

/**
 * \cn_
 * @brief 非接寻卡过程监听接口定义
 *
 * \_en_
 * @brief listener of CTLS search
 *
 * \en_e
 * \code{.java}
 * \endcode
 * @version
 * @see
 */
interface RFSearchListener {
	/**
     * \cn_
     * @brief 检测到非接触卡
     *
	 * @param cardType - 卡类型 | the card type
	 * <ul>
     * <li>S50_CARD(0x00) - S50卡</li>
     * <li>S70_CARD(0x01) - S70卡</li>
     * <li>PRO_CARD(0x02) - PRO卡</li>
     * <li>S50_PRO_CARD(0x03) - 支持S50驱动与PRO驱动的PRO卡</li>
     * <li>S70_PRO_CARD(0x04) - 支持S70驱动与PRO驱动的PRO卡 </li>
     * <li>CPU_CARD(0x05) - CPU卡</li>
	 * </ul>
     * \_en_
     * @brief on card pass
     *
	 * @param cardType the card type<BR>
	 * <ul><BR>
     * <li>S50_CARD(0x00) S50, mifare card</li><BR>
     * <li>S70_CARD(0x01) - S70, mifare card</li><BR>
     * <li>PRO_CARD(0x02) - PRO card</li><BR>
     * <li>S50_PRO_CARD(0x03) - S50 PRO card</li><BR>
     * <li>S70_PRO_CARD(0x04) - S70 PRO card </li><BR>
     * <li>CPU_CARD(0x05) - CPU card(contactless card)</li><BR>
	 * </ul>
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
	 */
	void onCardPass(int cardType);
	
	/**
     * \cn_
     * @brief 寻卡失败回调
     *
	 * @param error - 错误码 | the error code
	 * <ul>
	 * <li>ERROR_TRANSERR(0xA2) - 通讯错误 | error on transation, communication</li>
	 * <li>ERROR_PROTERR(0xA3) - 卡片返回数据不符合规范要求| the response is illegal</li>
	 * <li>ERROR_MULTIERR(0xA4) - 感应区内多卡存在 | multi-cards found</li>
	 * <li>ERROR_CARDTIMEOUT(0xA7) - 超时无响应 | timeout</li>
	 * <li>ERROR_CARDNOACT(0xB3) - Pro卡或者TypeB卡未激活 | card (pro, typeB) not actived</li>
	 * <li>ERROR_MCSERVICE_CRASH(0xff01) - 主控服务异常 | mater service crash</li>
	 * <li>ERROR_REQUEST_EXCEPTION(0xff02) - 请求异常 | request exception </li>
	 * </ul>
	 * @param message - 错误描述
     * \_en_
     * @brief on failure
     *
	 * @param error the error code<BR>
	 * <ul><BR>
	 * <li>ERROR_TRANSERR(0xA2) error on transation, communication</li><BR>
	 * <li>ERROR_PROTERR(0xA3) the response is illegal</li><BR>
	 * <li>ERROR_MULTIERR(0xA4)multi-cards found</li><BR>
	 * <li>ERROR_CARDTIMEOUT(0xA7) timeout</li><BR>
	 * <li>ERROR_CARDNOACT(0xB3) card (pro, typeB) not actived</li><BR>
	 * <li>ERROR_MCSERVICE_CRASH(0xff01) mater service crash</li><BR>
	 * <li>ERROR_REQUEST_EXCEPTION(0xff02) request exception </li><BR>
	 * </ul><BR>
	 * @param message - the message
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
	 */
	void onFail(int error, String message);
}
