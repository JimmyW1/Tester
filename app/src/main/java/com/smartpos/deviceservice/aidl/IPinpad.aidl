package com.smartpos.deviceservice.aidl;

import com.smartpos.deviceservice.aidl.PinInputListener;

/**
 * 密码键盘对象<br/>
 * 实现金融交易过程中密钥管理、数据加密和PIN输入处理过程。
 * @author: baoxl
 */
interface IPinpad {
	/**
	 * 判断密钥是否存在（密钥已下载）<br/>
	 * @param keyType - 密钥类型
	 * <ul>
	 * <li>0-主密钥</li>
	 * <li>1-MAC密钥</li>
	 * <li>2-PIN密钥</li>
	 * <li>3-TD密钥</li>
	 * <li>4-(SN4)主密钥</li>
	 * <li>5-(SN4)MAC密钥</li>
	 * <li>6-(SN4)PIN密钥</li>
	 * <li>7-(SN4)TD密钥</li>
	 * </ul>
	 * @param keyId - 密钥ID
	 * @return 存在返回true，不存在返回false
	 **/
	boolean isKeyExist(int keyType, int keyId);

    /**
	 * 下装TEK密钥<br/>
	 * TEK是加密主密钥的密钥KEK
	 * @param keyId - 密钥存储的ID
	 * @param key - 密钥
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadTEK(int keyId, in byte[] key, in byte[] checkValue);
	
	/**
	 * 下装密文主密钥<br/>
	 * @param keyId - 下装密钥存储ID
	 * @param key - 密钥
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadEncryptMainKey(int keyId, in byte[] key, in byte[] checkValue);

	/**
	 * 下装密文主密钥(含国密)<br/>
	 * @param keyId - 下装密钥存储ID
	 * @param key - 密钥
	 * @param keyType - 密钥类型 1-des密文 3-国密密文 5-AES密文
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadEncryptMainKeyWithGM(int keyId, in byte[] key, int keyType, in byte[] checkValue);

	/**
	 * 下装明文主密钥<br/>
	 * @param keyId - 下装密钥存储ID
	 * @param key - 密钥
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadMainKey(int keyId, in byte[] key, in byte[] checkValue);

	/**
	 * 下装明文主密钥(含国密)<br/>
	 * @param keyId - 下装密钥存储ID
	 * @param key - 密钥
	 * @param keyType - 密钥类型 2-des明文 4-国密明文 6-AES明文
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadMainKeyWithGM(int keyId, in byte[] key, int keyType, in byte[] checkValue);

	/**
	 * 下装工作密钥<br/>
	 * @param keyType - 下装工作密钥类型，1-MAC密钥，2-PIN密钥，3-TD密钥
	 * @param mkId - 解密工作密钥的主密钥ID
	 * @param wkId - 下装密钥存储ID
	 * @param key - 密钥
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadWorkKey(int keyType, int mkId, int wkId, in byte[] key, in byte[] checkValue);

	/**
	 * 下装工作密钥(含国密)<br/>
	 * @param keyType - 下装工作密钥类型，1-MAC密钥，2-PIN密钥，3-TD密钥 5-(SN4)MAC密钥 6-(SN4)PIN密钥 7-(SN4)TD密钥
	 * @param mkId - 解密工作密钥的主密钥ID
	 * @param wkId - 下装密钥存储ID
	 * @param decKeyType - 解密密钥类型 0-DES master key 1-transport key 2-SM4 master key 3-AES master key
	 * @param key - 密钥
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
	 */
	boolean loadWorkKeyWithGM(int keyType, int mkId, int wkId, int decKeyType, in byte[] key, in byte[] checkValue);

	/**
	 * 计算MAC值<br/>
	 * @param keyId - MAC密钥索引
	 * @param data - 计算MAC的数据
	 * @return 成功返回mac值，失败返回null
	 */
	byte[] calcMAC(int keyId, in byte[] data);

	/**
	 * 计算MAC值<br/>
	 * @param keyId - MAC密钥索引
	 * @param type - 计算规范
	 * |--0x00-MAC X99
	 * |--0x01-MAC X919
	 * |--0x02-ECB CUP standard ECB algorithm
	 * |--0x03-MAC 9696
	 * |--0x04-CBC MAC calculation
	 * @param data - 计算MAC的数据
	 * @param desType- 计算模式
	 * |--0x00-des
	 * |--0x01-3des
	 * |--0x02-sm4
	 * @param keyType-MAC密钥类型
	 * |--0x01-MAC_KEY_DES
	 * |--0x02-MAC_KEY_SM4
	 * @return 成功返回mac值，失败返回null
	 */
	byte[] calcMACWithGM(int keyId, int type, in byte[] data, int desType, int keyType);

	/**
	 * 加密磁道数据<br/>
	 * @param mode - 加密模式 1-CBC加密 2-ECB加密
	 * @param keyId - TDK密钥索引
	 * @param trkData - 待加密磁道数据
	 * @return 成功返回加密后的磁道数据，失败返回null
	 */
	byte[] encryptTrackData(int mode, int keyId, in byte[] trkData);

	/**
	 * 加密磁道数据<br/>
	 * @param mode - 加密模式 1-CBC加密 2-ECB加密
	 * @param keyId - TDK密钥索引
	 * @param keyType - TDK密钥类型 0x01-TD密钥 0x11-(SN4)TD密钥 todo 注意这个和以前不一样,上层注意修改
	 * @param desType - 计算模式 0x01-3des 0x02-SM4
	 * @param trkData - 待加密磁道数据
	 * @return 成功返回加密后的磁道数据，失败返回null
	 */
	byte[] encryptTrackDataWithGM(int mode, int keyId, int keyType, int desType, in byte[] trkData);

    /**
     * 启动PIN输入<br/>
     * @param keyId - PIN密钥索引
     * @param param - PIN输入配置参数
     * <ul>
     * <li>keyType(int) - 密钥类型（0x00-3des 0x03-sm4）</li>
     * <li>desType(int) - 计算模式（0x01-3des 0x02-sm4）</li>
     * <li>pinLimit(byte[]) - 允许输入密码的长度</li>
     * <li>timeout(int) - 输入超时时间，单位（秒）</li>
     * <li>isOnline(boolean) - 是否联机PIN</li>
     * <li>promptString(String) - 提示信息</li>
     * <li>pan(String) - 用于加密联机PIN的主帐号（卡号）</li>
     * </ul>
     * @param listener - PIN输入过程监听器
     * @return
     */
	void startPinInput(int keyId, in Bundle param, PinInputListener listener);
	
	/**
     * 提交PIN输入
     */
    void submitPinInput();
    
    /**
	 * 取消PIN输入过程
	 */
    void stopPinInput();
    
    
    /**
     * 获取最近一次操作的错误<br/>
     * @return 错误描述
     */
	String getLastError();
}
