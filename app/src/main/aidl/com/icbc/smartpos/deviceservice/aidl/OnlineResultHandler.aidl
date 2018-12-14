package com.icbc.smartpos.deviceservice.aidl;

/**
 * PBOC流程联机应答数据处理
 * @author: baoxl
 */
interface OnlineResultHandler {
	/**
	 * 联机应答数据处理回调<br/>
	 * @param result - 处理结果
	 * <ul>
	 * <li>ONLINE_RESULT_TC(0) - 联机成功 </li>
	 * <li>ONLINE_RESULT_AAC(1) - 联机拒绝 </li>
	 * <li>ONLINE_RESULT_OFFLINE_TC(101) - 联机失败，脱机成功 </li>
	 * <li>ONLINE_RESULT_SCRIPT_NOT_EXECUTE(102) - 脚本未执行 </li>
     * <li>ONLINE_RESULT_SCRIPT_EXECUTE_FAIL(103) - 脚本执行失败 </li>
	 * <li>ONLINE_RESULT_NO_SCRIPT(104) - 联机失败，未下送脚本 </li>
	 * <li>ONLINE_RESULT_TOO_MANY_SCRIPT(105) - 联机失败，脚本超过1个 </li>
	 * <li>ONLINE_RESULT_TERMINATE(106) - 联机失败，交易终止(GAC返回非9000，要提示交易终止,0x8F) </li>
	 * <li>ONLINE_RESULT_ERROR(107) - 联机失败，EMV内核错误</li>
	 * </ul>
	 * @param data - 结果数据
	 * <ul>
     * <li>TC_DATA(String) - 交易结果TLV数据 </li>
     * <li>SCRIPT_DATA(String) - 脚本结果TLV数据 </li>
     * <li>REVERSAL_DATA(String) - 冲正TLV数据 </li>
     * </ul>
	 */
	void onProccessResult(int result, in Bundle data);
}
