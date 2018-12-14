package com.boc.aidl.scanner;

interface AidlScannerListener{
	/**
	 * 读码成功的回调
	 * @param barcode 读取到的码数据
	 */
    void onScanResult(in String[] barcode);
	/**
	 * 读码结束（或者在超时时间内没有读到码数据）
	 */
    void onFinish();
}