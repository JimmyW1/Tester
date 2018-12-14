package com.icbc.smartpos.deviceservice.aidl;

/**
 * 设备LED灯对象
 * @author: baoxl
 */
interface ILed {
	/**
	 * 点亮LED灯
	 * @param led - 灯标识
	 * <ul>
	 * <li> 1 - 蓝灯</li>
	 * <li> 2 - 黄灯</li>
	 * <li> 3 - 绿灯</li>
	 * <li> 4 - 红灯</li>
	 * </ul>
	 */
	void turnOn(int led);
	
	/**
	 * 关闭LED灯
	 * @param led - 灯标识
     * <ul>
     * <li> 1 - 蓝灯</li>
     * <li> 2 - 黄灯</li>
     * <li> 3 - 绿灯</li>
     * <li> 4 - 红灯</li>
     * </ul>
	 */
	void turnOff(int led);
}
