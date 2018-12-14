package com.icbc.smartpos.deviceservice.aidl;

/**
 * 扫码器扫码结果监听器
 * @author: baoxl
 */
interface ScannerListener {
	/**
	 * 扫码成功回调
	 * @param barcode - 条码
	 */
    void onSuccess(String barcode);
    
    /**
     * 扫码出错
     * @param error - 错误码
     * @param message - 错误描述
     */
    void onError(int error, String message);
    
	/**
	 * 扫码超时回调
	 */
    void onTimeout();
    
    /**
     * 扫码取消回调
     */
    void onCancel();
}
