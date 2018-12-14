package com.smartpos.deviceservice.aidl;

import com.smartpos.deviceservice.aidl.ScannerListener;

/**
 * 扫码器对象<br/>
 * 实现设备对二维码扫码功能。
 * @author: baoxl
 */
interface IScanner {
	/**
	 * 启动扫码
	 * @param配置参数
     *  topTitleString(String)：扫描框最上方提示信息，最大12汉字,默认中间对齐，
     *  upPromptString(String)：扫描框上方提示信息，最大20汉字，默认中间对齐，
     *  downPromptString(String)：扫描框下方提示信息，最大20汉字，默认中间对齐
     *  whichCamera(int)：0 - 后置摄像头   1 - 前置摄像头，不传此参数则根据IDeviceService中getScanner的参数做选择，
     *                   如果使用此参数建议使用ScannerConst 中定义的常量作为参数传递
     *
	 * @timeout - 超时时间，单位ms
	 * @listener - 扫码结果监听
	 */
	void startScan(in Bundle param, long timeout, ScannerListener listener);
	
	/**
	 * 停止扫码
	 */
	void stopScan();
}