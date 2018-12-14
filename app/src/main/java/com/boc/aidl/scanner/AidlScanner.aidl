package com.boc.aidl.scanner;
import com.boc.aidl.scanner.AidlScannerListener;

interface AidlScanner{
	/**
	 * 开启扫码
	 * @param scanType 扫码类型：前置摄像头扫码、后置摄像头扫码，详见Const.ScanType
	 * @param timeout  超时时间，单位ms
	 * @param listener  扫码回调
	 */
	void startScan(int scanType,int timeout, AidlScannerListener listener);
	
	
	/**
	 * 停止扫码
	 */
    void stopScan();
}