package com.boc.aidl.iccard;


interface AidlICCard {
	/**
	 * IC卡上电
	 * 
	 * @param icCardSlot
	 *            IC卡卡槽
	 * @param icCardType
	 *            IC卡类型
	 * @return ATR数据
	 */
	byte[] powerOn(int icCardSlot, int icCardType);

	/**
	 * IC卡通讯
	 * 
	 * @param icCardSlot
	 *            IC卡卡槽
	 * @param icCardType
	 *            IC卡类型
	 * @param data
	 *            APDU指令数据
	 * @param timeout
	 *            超时时间（时间单位为秒）
	 * @return
	 */
	byte[] call(int icCardSlot, int icCardType,in byte[] data, int timeout);

	/**
	 * IC卡下电
	 * 
	 * @param icCardSlot
	 *            IC卡卡槽
	 * @param icCardType
	 *            IC卡类型
	 */
	void powerOff(int icCardSlot, int icCardType);

		
	/**
	 * 获取当前IC卡卡槽状态<p>
	 * 
	 * @return
	 * 		当前IC卡槽状态
	 */
	 boolean checkSlotsState(int ICCardSlot);
}
