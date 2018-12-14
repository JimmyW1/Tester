package com.icbc.smartpos.deviceservice.aidl;

import com.icbc.smartpos.deviceservice.aidl.CheckCardListener;
import com.icbc.smartpos.deviceservice.aidl.UPCardListener;
import com.icbc.smartpos.deviceservice.aidl.PBOCHandler;
import com.icbc.smartpos.deviceservice.aidl.OnlineResultHandler;
import com.icbc.smartpos.deviceservice.aidl.CandidateAppInfo;

/**
 * PBOC金融交易对象<br/>
 * 用来处理金融卡相关的EMV交易流程。
 * @author: baoxl
 */
interface IPBOC {
    /**
     * PBOC检卡
     * @param cardOption - 支持的卡类型，磁条卡、IC卡和非接卡
     * <ul>
     * <li>supportMagCard(boolean) - 是否支持磁条卡</li>
     * <li>supportICCard(boolean) - 是否支持IC卡</li>
     * <li>supportRFCard(boolean) - 是否支持非接卡</li>
     * </ul>
     * @param timeout - 超时时间
     * @param listener - 检卡结果监听器
     */
	void checkCard(in Bundle cardOption, int timeout, CheckCardListener listener);
	
    /**
     * 停止PBOC检卡
     */   
	void stopCheckCard();
	
    /**
     * 读取手机芯片卡
     * @param listener - 读卡结果监听器
     */
    void readUPCard(UPCardListener listener);
	
    /**
     * 启动PBOC处理流程
     * @param transType - 交易类型
     * <ul>
     * <li>EC_BALANCE(1) - 查询电子现金余额</li>
     * <li>TRANSFER(2) - 转账</li>
     * <li>EC_LOAD(3) - 指定账户圈存</li>
     * <li>EC_LOAD_U(4) - 非制定账户圈存</li>
     * <li>EC_LOAD_CASH(5) - 现金圈存/现金充值</li>
     * <li>EC_LOAD_CASH_VOID(6) - 圈存撤销</li>
     * <li>PURCHASE(7) - 消费</li>
     * <li>Q_PURCHASE(8) - 快速支付</li>
     * <li>CHECK_BALANCE(9) - 余额查询</li>
     * <li>PRE_AUTH(10) - 预授权交易</li>
     * <li>SALE_VOID(11) - 消费撤销</li>
     * <li>SIMPLE_PROCESS(12) - 简易流程交易</li>
     * <li>Q_QUERY(13) - qPBOC查询</li>     
     * </ul>
     * @param intent - 请求数据
     * <ul>
     * <li>cardType(int)：卡类型，CARD_INSERT(0)-接触式IC卡，CARD_RF(1)-非接触式卡</li>
     * <li>authAmount(long)：授权金额（交易金额）</li>
     * <li>isSupportQ(boolean)：是否支持QPBOC</li>
     * <li>isSupportSM(boolean)：是否支持国密</li>
     * <li>isQPBOCForceOnline(boolean)：QPBOC是否支持强制联机</li>
     * <li>merchantName(String)：商户名称</li>
     * <li>merchantId(String)：商户号</li>
     * <li>terminalId(String)：终端号</li>
     * </ul>
     * @param handler - PBOC流程回调处理器
     */    
	void startPBOC(int transType, in Bundle intent, PBOCHandler handler);
	
    /**
     * 终止PBOC流程
     */   
	void abortPBOC();

    /**
     * 更新AID参数
     * @param operation - 操作标识，增、删、清空
     * <ul>
     * <li>1：新增 </li>
     * <li>2：删除 </li>
     * <li>3：清空 </li>
     * </ul>
     * @param aidType - AID参数类型
     * <ul>
     * <li>1：接触式 </li>
     * <li>2：非接</li>
     * </ul>
     * @param aid - AID参数，构成AID参数的TLV字符串
     * @return 成功true，失败false
     */
    boolean updateAID(int operation,int aidType, String aid);

     /**
     * 获取当前aid参数TLV数据列表，在选择完应用后可以调用
     * @param taglist - 需要获得的tag列表
     * @return tlv数据，null表示没有数据
     */
    String getAidParams(in String[] taglist);

    /**
     * 更新CA公钥参数
     * @param operation - 操作标识，增、删、清空
     * <ul>
     * <li>1：新增 </li>
     * <li>2：删除 </li>
     * <li>3：清空 </li>
     * </ul>
     * @param rid - CA公钥参数
     * @return 成功true，失败false
     */
    boolean updateRID(int operation, String rid);
    
    /**
     * 导入金额
     * @param amount - 交易金额
     */    
    void importAmount(long amount);
	
    /**
     * 导入应用选择结果（多应用卡片）
     * @param index - 应用选择的序号从1开始，0为取消
     */    
    void importAppSelect(int index);
    
    /**
     * 导入PIN
     * @param option(int) - 操作选项
     * <ul>
     * <li> CANCEL(0) - 取消 </li>
     * <li> CONFIRM(1) - 确认 </li>
     * </ul>
     * @param pin - PIN数据
     */    
    void importPin(int option, in byte[] pin);
    
    /**
     * 导入身份认证结果
     * @param option - 确认结果
     * <ul>
     * <li> CANCEL(0) - 取消，跳过（BYPASS) </li>
     * <li> CONFIRM(1) - 确认持卡人身份 </li>
     * <li> NOTMATCH(2) - 身份信息不符 </li>
     * </ul>
     */    
    void importCertConfirmResult(int option);
    
    /**
     * 导入卡信息确认结果
     * @param pass - 是否通过
     */    
    void importCardConfirmResult(boolean pass);
    
    
    /**
     * 导入联机应答数据
     * @param onlineResult - 联机结果
     * <ul>
     * <li> isOnline(boolean) - 是否联机</li>
     * <li> respCode(String) - 应答码</li>
     * <li> authCode(String) - 授权码 </li>
     * <li> field55(String) - 55域应答数据</li>
     * </ul>
     * @param handler - 联机结果处理
     */
    void inputOnlineResult(in Bundle onlineResult, OnlineResultHandler handler);

    /**
     * 设置TLV内核参数
     * @param tlvList 内置参数TLV列表
     */
    void setEMVData(in List<String> tlvList);
    
    /**
	 * 获取PBOC内核TLV数据列表
	 * @param taglist - 需要获得的tag列表
	 * @return tlv数据，null表示没有数据
	 */
	String getAppTLVList(in String[] taglist);
	
    /**
	 * 获取卡片上EMV数据元
	 * @param tagName - 标签名称
	 * @return 卡片EMV数据
	 */
	byte[] getCardData(String tagName);
	
	/**
	 * 获取PBOC流程数据，如卡号、有效期、卡片序列号等。<br/>
	 * <em>EMV必须执行到相应流程才能获取到对应的卡片数据，否则返回空。</em>
	 * @param tagName - 标签名称
	 * <ul>
     * <li> PAN - 卡号</li>
     * <li> TRACK2 - 磁道二数据</li>
     * <li> CARD_SN - 卡片序列号 </li>
     * <li> EXPIRED_DATE - 有效期</li>
     * <li> DATE - 日期</li>
     * <li> TIME - 时间</li>
     * <li> BALANCE - 电子现金余额</li>
     * <li> CURRENCY - 币种</li>
     * </ul>
	 * @return PBOC流程中内核返回的数据
	 */
	String getPBOCData(String tagName);
	
    /**
	 * 获取卡片候选应用信息<br/>
	 * 用于电子签名上送和小额免签免密。
	 * @return 卡片候选应用信息，具体见{@link CandidateAppInfo}定义
	 */
	CandidateAppInfo getCandidateAppInfo();
}
