package com.boc.aidl.cardreader;

import com.boc.aidl.cardreader.AidlCardReaderListener;
interface AidlCardReader {
	/**
	 * 撤消当前读卡操作。
	 * 
	 */
	void cancelCardRead();
	/**
	 * 关闭读卡器<p>
	 * 
	 */
	void closeCardReader();
	/**
	 * @param openCardType 期望的读卡器监听类型
	 * @param isAllowFallback 是否允许设备判定降级交易。若开启，设备将允许降级交易发生。
	 * @param isMSDChecking 是否开启磁道校验。校验方式根据实现需求，如：开启银联卡磁道校验。
	 * @param timeout 超时时间 ,单位为s
	 */
	void openCardReader(int openCardType, boolean isAllowfallback,
			boolean isMSDChecking, int timeout,AidlCardReaderListener cardReaderListener);
}
