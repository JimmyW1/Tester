package com.boc.aidl.rfcard;

import android.content.Intent;
import com.boc.aidl.rfcard.PowerOnRFResult;

interface AidlRFCard {
	/**
	 * @param rfCardType
	 *            非接卡类型
	 * @param timeout
	 *            超时时间
	 * @return 上电结果
	 *            
	 */
	PowerOnRFResult powerOn(int rfCardType, int timeout);

	/**
	 * 非接通讯
	 * 
	 * @param req 通讯数据
	 * @param timeout 超时时间（单位为秒）
	 * @return 通讯响应的数据
	 */
	byte[] call(in byte[] req, long timeout);
	
	/**
	 * 非接下电
	 * @param timeout 超时时间(单位为毫秒)
	 */
	void powerOff(int timeout);
	
	/**
	 * 使用外部密钥进行认证（直接传入密钥，使用该密钥对 M1 卡进行认证）
	 * @param RFKeyMode KEY模式
	 * @param SNR 激活时获取的序列号
	 * @param blockNo 要认证的块号
	 * @param key 外部密钥
	 */
	void authenticateByExtendKey(int rfKeyMode, in byte[] SNR, int blockNo,in byte[] key);
	
	/**
	 * 写块数据
	 * @param blockNo 块号
	 * @param data 要写入的 16 个字节的数据
	 */
	void writeDataBlock(int blockNo, in byte[] data);
	
	/**
	 * 读块数据
	 * @param blockNo 块号
	 * @return
	 */
	byte[] readDataBlock(int blockNo);
	
	/**
	 * 增量操作
	 * @param blockNo 块号
	 * @param data 增量值
	 */
	void incrementOperation(int blockNo, in byte[] data);
	
	/**
	 * 减量操作
	 * @param blockNo 减量
	 * @param data 减量值
	 */
	void decrementOperation(int blockNo, in byte[] data);
}
