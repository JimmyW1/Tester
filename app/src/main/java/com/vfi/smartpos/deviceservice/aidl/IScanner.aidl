package com.vfi.smartpos.deviceservice.aidl;

import com.vfi.smartpos.deviceservice.aidl.ScannerListener;

/**
 * \cn_
 * @brief 扫码器对象
 *
 * 实现设备对二维码扫码功能。
 * \_en_
 * @brief the object of scanner
 *
 * for scanning the bar-code or QR-code
 * \en_e
 * \code{.java}
 * \endcode
 * @version
 * @see
 *
 * @author: baoxl
 */
interface IScanner {
	/**
     * \cn_
     * @brief 启动扫码
     *
	 * @param配置参数 | the parameter
     *  topTitleString(String)：扫描框最上方提示信息，中间对齐，| the title string on the top, align center.
     *  upPromptString(String)：扫描框上方提示信息，中间对齐， | the prompt string upside of the scan box, align center.
     *  downPromptString(String)：扫描框下方提示信息，中间对齐 | the prompt string downside of the scan box , align center.
	 *  timeout - 超时时间，单位ms | the timeout, millsecond.
	 *  listener - 扫码结果监听 | the call back listerner
	 *  showScannerBorder(boolean, true is default): 是否显示扫码框 | set false, scanner border & upPromptString &downPromptString will be hided,
     * \_en_
     * @brief start scan
     *
	 * @param param
     *  <BR>topTitleString(String) the title string on the top, align center.
     *  <BR>upPromptString(String) the prompt string upside of the scan box, align center.
     *  <BR>downPromptString(String) the prompt string downside of the scan box , align center.
	 *  <BR>showScannerBorder(boolean, true is default) false for: scanner border & upPromptString &downPromptString will be hided,
	 * @param timeout  the timeout, millsecond.
	 * @param  listener  the call back listerner
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 *
	 */
	void startScan(in Bundle param, long timeout, ScannerListener listener);
	
	/**
     * \cn_
     * @brief 停止扫码
     *
     * \_en_
     * @brief stop scan
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 *
	 */
	void stopScan();
}