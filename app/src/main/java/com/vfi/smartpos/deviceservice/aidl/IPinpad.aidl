package com.vfi.smartpos.deviceservice.aidl;

import com.vfi.smartpos.deviceservice.aidl.PinInputListener;
import com.vfi.smartpos.deviceservice.aidl.PinKeyCoorInfo;

/**
 * \cn_
 * @brief
 *
 * \_en_
 * @brief
 *
 * \en_e
 * \code{.java}
 * \endcode
 * @version
 * @see
 *
 * \cn_
 * @brief 密码键盘对象
 *
 * 实现金融交易过程中密钥管理、数据加密和PIN输入处理过程。
 * \_en_
 * @brief the object of PIN pad
 *
 * download keys, data encrypt, pin input
 * \en_e
 * \code{.java}
 * \endcode
 * @version
 * @see
 *
 * @author Kai.L@verifone.cn, Chao.L@verifone.cn
 */
interface IPinpad {
	/**
     * \cn_
     * @brief 判断密钥是否存在（密钥已下载）
     *
	 * @param keyType - 密钥类型
	 * <ul>
	 * <li>0-主密钥 | MASTER key</li>
	 * <li>1-MAC密钥 | MAC key</li>
	 * <li>2-PIN密钥 | PIN key</li>
	 * <li>3-TD密钥 | TD key</li>
	 * <li>4-(SM)主密钥 | (SM) MASTER key</li>
	 * <li>5-(SM)MAC密钥 | (SM)MAC key</li>
	 * <li>6-(SM)PIN密钥 | (SM)PIN key</li>
	 * <li>7-(SM)TD密钥 | (SM)TD key</li>
	 * <li>8-(AES)主密钥 | (AES)MASTER key</li>
	 * <li>9-(AES)MAC密钥 | (AES)MAC key</li>
	 * <li>10-(AES)PIN密钥 | (AES)PIN key</li>
	 * <li>11-(AES)TD密钥 | (AES)TD key</li>
	 * <li>12-dukpt密钥 | dukpt key</li>
	 * </ul>
	 * @param keyId - 密钥ID | the id (dukpt index 0~4, other 0~99) of the key
	 * @return 存在返回true，不存在返回false | true for exist, false for not exists
     * \_en_
     * @brief Check if Key is exists
     *
	 * @param keyType - 密钥类型
	 * <ul>
	 * <li>0 MASTER(main) key</li>
	 * <li>1 MAC key</li>
	 * <li>2 PIN(work) key</li>
	 * <li>3 TD key</li>
	 * <li>4 (SM) MASTER key</li>
	 * <li>5 (SM)MAC key</li>
	 * <li>6 (SM)PIN key</li>
	 * <li>7 (SM)TD key</li>
	 * <li>8 (AES)MASTER key</li>
	 * <li>9 (AES)MAC key</li>
	 * <li>10 (AES)PIN key</li>
	 * <li>11 (AES)TD key</li>
	 * <li>12 dukpt key</li>
	 * </ul>
	 * @param keyId the id (dukpt index 0~4, other 0~99) of the key
	 * @return true for exist, false for not exists
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 **/
	boolean isKeyExist(int keyType, int keyId);
	
    /**
     * \cn_
     * @brief 下装TEK密钥(默认3des算法)
     *
	 * TEK是加密主密钥的密钥KEK
	 * @param keyId - 密钥存储的ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param checkValue - 校验值 | the check value
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load TEK key(algorithm 3des default)
     *
	 * TEK is the transfer key to encrypt master key
	 * @param keyId the id (index) , from 0 to 99
	 * @param the key
	 * @param checkValue the check value
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadTEKWithAlgorithmType loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType
     *
	 */
	boolean loadTEK(int keyId, in byte[] key, in byte[] checkValue);

    /**
     * \cn_
     * @brief 下装TEK密钥
     *
	 * TEK是加密主密钥的密钥KEK
	 * @param keyId - 密钥存储的ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param algorithmType - 密钥类型 1-3des密文 2-3des明文 3-国密密文 4-国密明文 5-AES密文 6-AES明文 |
	 *                                1-3des encrypt key 2-3des plain key 3-SM4 encrypt key 4-SM4 plain key 5-AES encrypt key 6-AES plain key
	 * @param checkValue - 校验值 | the check value
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load TEK key with Algorithm Type given
     *
     * TEK is the transfer key to encrypt master key
	 * @param keyId the id (index, 0 to 99)
	 * @param key the key
	 * @param algorithmType 1-3des encrypted key <BR>2-3des plain key <BR>3-SM4 encrypte key <BR>4-SM4 plain key <BR>5-AES encrypted key <BR>6-AES plain key
	 * @param checkValue the check value
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadTEK loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType
     *
	 */
	boolean loadTEKWithAlgorithmType(int keyId, in byte[] key, in byte algorithmType, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装密文主密钥(默认3des算法)
     *
	 * @param keyId - 下装密钥存储ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param checkValue - 校验值 | check value (default NULL)
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load the encrypted master key (3des algorithm default)
     *
	 * @param keyId the id (index, 0 to 99)
	 * @param key the encrypted key
	 * @param checkValue check value (default NULL)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadTEK loadTEKWithAlgorithmType loadEncryptMainKeyWithAlgorithmType loadMainKey loadWorkKey loadWorkKeyWithDecryptType
     *
	 */
	boolean loadEncryptMainKey(int keyId, in byte[] key, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装密文主密钥
     *
	 * @param keyId - 下装密钥存储ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param algorithmType - 算法类型 1-3des密文 3-SM4密文 5-AES密文 | 1-3des algorithm 3-SM4 algorithm 5-AES algorithm
	 * @param checkValue - 校验值 | check value (default NULL)
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load the encrypted master key given Algorithm Type
     *
	 * @param keyId the id (index, 0 to 99)
	 * @param key the encrypted key
	 * @param algorithmType 1-3des algorithm <BR>3-SM4 algorithm <BR>5-AES algorithm
	 * @param checkValue - 校验值 | check value (default NULL)
	 * @return 成功true，失败false | true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadTEK loadTEKWithAlgorithmType loadEncryptMainKey loadMainKey loadWorkKey loadWorkKeyWithDecryptType
	 */
	boolean loadEncryptMainKeyWithAlgorithmType(int keyId, in byte[] key, int algorithmType, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装明文主密钥(默认3des算法)
     *
	 * @param keyId - 下装密钥存储ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param checkValue - 校验值 | the check value (default NULL)
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load the plain master key(3des algorithm default)
     *
	 * @param keyId the id (index)
	 * @param key the key
	 * @param checkValue the check value (default NULL)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType loadMainKeyWithAlgorithmType loadWorkKey loadWorkKeyWithDecryptType
     *
	 */
	boolean loadMainKey(int keyId, in byte[] key, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装明文主密钥
     *
	 * @param keyId - 下装密钥存储ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param algorithmType - 密钥类型 2-3des明文 4-国密明文 6-AES明文 | 2-3des algorithm 4-SM4 algorithm 6-AES algorithm
	 * @param checkValue - 校验值 | the check value (default NULL)
	 * @return 成功true，失败false
     * \_en_
     * @brief load the plain master key given the algorithm Type
     *
	 * @param keyId the id (index)
	 * @param key the key
	 * @param algorithmType 2 for 3des algorithm <BR>4 for SM4 algorithm <BR>6 for AES algorithm
	 * @param checkValue the check value (default NULL)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType loadMainKey loadWorkKey loadWorkKeyWithDecryptType
     *
	 */
	boolean loadMainKeyWithAlgorithmType(int keyId, in byte[] key, int algorithmType, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装DUKPT密钥
     *
	 * @param keyId - 下装密钥存储ID | the id (index 0~4)
	 * @param ksn - 密钥序列号 | the key serial number
	 * @param key - 密钥 | the key
	 * @param checkValue - 校验值 | the check value (default NULL)
	 * @return 成功true，失败false
     * \_en_
     * @brief load the DUKPT key
     *
	 * @param keyId the id (index 0~4)
	 * @param ksn the key serial number
	 * @param key the key
	 * @param checkValue the check value (default NULL)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 */
	boolean loadDukptKey(int keyId, in byte[] ksn, in byte[] key, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装工作密钥 (默认解密主密钥为3des)
     *
	 * @param keyType - 下装工作密钥类型 | select the workkey type: 1-MAC key, 2-PIN key, 3-TD key
	 * @param mkId - 解密工作密钥的主密钥ID | the id of master key for decrypt work key
	 * @param keyId - 下装密钥存储ID | set the workkey id (index 0~99)
	 * @param key - 密钥 | the key (16byte)
	 * @param checkValue - 校验值 | check value
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load the work key (3DES decrypt master key default)
     *
	 * @param keyType select the workkey type: 1-MAC key, 2-PIN key, 3-TD key
	 * @param mkId the id of master key for decrypt work key
	 * @param keyId set the workkey id (index 0~99)
	 * @param key the key (16bytes)
	 * @param checkValue check value (null for none)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadWorkKeyWithDecryptType loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType loadMainKey loadMainKeyWithAlgorithmType
     *
	 * <br/>
	 */
	boolean loadWorkKey(int keyType, int mkId, int wkId, in byte[] key, in byte[] checkValue);

	/**
     * \cn_
     * @brief 下装工作密钥
     *
	 * @param keyType - 下装工作密钥类型 | select the workkey type<BR>
	 *     |---1-MAC key, 2-PIN key, 3-TD key<BR>
	 *     |---5-(SM4)MAC key, 6-(SM4)PIN key, 7-(SM4)TD key<BR>
	 *     |---9-(AES)MAC key, 10-(AES)PIN key, 11-(AES)TD key<BR>
	 * @param mkId - 解密工作密钥的主密钥ID | the id of master key for decrypt work key
	 * @param wkId - 下装密钥存储ID | set the workkey id (index 0~99)
	 * @param decKeyType - 解密密钥类型 | select decrypt key type<BR>
	 *     |---0-3DES master key<BR>
	 *     |---1-transport key<BR>
	 *     |---2-SM4 master key<BR>
	 *     |---3-AES master key<BR>
	 * @param key - 密钥 | encrypt key
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
     * \_en_
     * @brief load the work key given decrypt type
     *
	 * @param keyType select the workkey type<BR>
	 *     |---1-MAC key, 2-PIN key, 3-TD key<BR>
	 *     |---5-(SM4)MAC key, 6-(SM4)PIN key, 7-(SM4)TD key<BR>
	 *     |---9-(AES)MAC key, 10-(AES)PIN key, 11-(AES)TD key<BR>
	 * @param mkId the id of master key for decrypt work key
	 * @param wkId set the workkey id (index 0~99)
	 * @param decKeyType select decrypt key type<BR>
	 *     |---0-3DES master key<BR>
	 *     |---1-transport key<BR>
	 *     |---2-SM4 master key<BR>
	 *     |---3-AES master key<BR>
	 * @param encrypt key
	 * @param checkValue check value (null for none)
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadWorkKey loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType loadMainKey loadMainKeyWithAlgorithmType
     *
	 *  | <br/>
	 */
	boolean loadWorkKeyWithDecryptType(int keyType, int mkId, int wkId, int decKeyType, in byte[] key, in byte[] checkValue);

	/**
     * \cn_
     * @brief 计算MAC值 | calcuate the MAC (3des default & X919)
     *
	 * @param keyId - MAC密钥索引 | the index of MAC KEY
	 * @param data - 计算MAC的数据 | the source date
	 * @return 成功返回mac值，失败返回null | @return, the mac value, null means failure
     * \_en_
     * @brief calcuate the MAC (3des default & X919)
     *
	 * @param keyId the index of MAC KEY
	 * @param data the source date
	 * @return the mac value, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadWorkKey loadWorkKeyWithDecryptType
     *
	 * <br/>
	 */
	byte[] calcMAC(int keyId, in byte[] data);

	/**
     * \cn_
     * @brief 计算MAC值
     *
	 * @param keyId - MAC密钥索引或者dukpt密钥索引 | the index of MAC KEY(0~99) or dukpt key(0~4)
	 * @param type - 加密模式 | Calculation mode 0x00-MAC X99; 0x01-MAC X919; 0x02-ECB (CUP standard ECB algorithm); 0x03-MAC 9606; 0x04-CBC MAC calculation
	 * @param CBCInitVec - CBC initial vector. fixed length 8, can be null, default 8 bytes 0x00
	 * @param data - 计算MAC的数据 | the source date
	 * @param desType - 加密类型 | encrypt type<BR>
     * |--0x00-des<BR>
     * |--0x01-3des<BR>
     * |--0x02-sm4<BR>
     * |--0x03-aes<BR>
     * @param dukptRequest - 是否使用dukpt | if true, the keyId is dukpt key id
	 * @return 成功返回mac值，失败返回null | @return, the mac value, null means failure
     * \_en_
     * @brief calcute the MAC with given type
     *
	 * @param keyId the index of MAC KEY(0~99) or dukpt key(0~4)
	 * @param type Calculation mode 0x00-MAC X99; 0x01-MAC X919; 0x02-ECB (CUP standard ECB algorithm); 0x03-MAC 9606; 0x04-CBC MAC calculation
	 * @param CBCInitVec - CBC initial vector. fixed length 8, can be null, default 8 bytes 0x00
	 * @param data the source date
	 * @param desType encrypt type<BR>
     * |--0x00-des<BR>
     * |--0x01-3des<BR>
     * |--0x02-sm4<BR>
     * |--0x03-aes<BR>
     * @param dukptRequest true means the keyId is dukpt key id
	 * @return the mac value, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadWorkKey loadWorkKeyWithDecryptType calcMAC
     *
	 *  | <br/>
	 */
	byte[] calcMACWithCalType(int keyId, int type, in byte[] CBCInitVec, in byte[] data, int desType, boolean dukptRequest);

	/**
     * \cn_
     * @brief 加密磁道数据 (默认3des算法)
     *
	 * @param mode - 加密模式 0：ECB模式，1：CBC模式 | mode , 0 for ECB, 1 for CBC
	 * @param keyId - TDK密钥索引 | the id of TDK
	 * @param trkData - 待加密磁道数据 | the track date
	 * @return 成功返回加密后的磁道数据，失败返回null | @return the encrypted trace data, null means failure
     * \_en_
     * @brief encrypt the trace date(3des algorithm default)
     *
	 * @param mode mode , 0 for ECB, 1 for CBC
	 * @param keyId the id of TDK
	 * @param trkData the track date
	 * @return @return the encrypted track data, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 */
	byte[] encryptTrackData(int mode, int keyId, in byte[] trkData);

	/**
     * \cn_
     * @brief 加密磁道数据
     *
	 * @param mode - 加密模式 0：ECB模式，1：CBC模式 | mode , 0 for ECB, 1 for CBC
	 * @param keyId - TDK密钥索引或者dukpt密钥| the id of TDK(0~99) or dukpt key(0~4)
	 * @param AlgorithmType - 计算模式 | algorithmType type 0x01-3des 0x02-SM4 0x03-AES
	 * @param trkData - 待加密磁道数据 | the track date
	 * @param dukptRequest - 是否使用dukpt key进行加密
	 * @return 成功返回加密后的磁道数据，失败返回null | @return the encrypted trace data, null means failure
     * \_en_
     * @brief encrypt the trace date given algorithm type
     *
	 * @param mode mode , 0 for ECB, 1 for CBC
	 * @param keyId the id of TDK(0~99) or dukpt key(0~4)
	 * @param AlgorithmType algorithmType type 0x01-3des 0x02-SM4 0x03-AES
	 * @param trkData the track date
	 * @param dukptRequest if true, the keyId is dukpt key id
	 * @return the encrypted track data, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 */
	byte[] encryptTrackDataWithAlgorithmType(int mode, int keyId, int algorithmType, in byte[] trkData, boolean dukptRequest);

    /**
     * \cn_
     * @brief 启动PIN输入
     *
     * @param keyId - PIN密钥索引或者dukpt密钥索引| the index of PIN KEY(0~99) or dukpt key (0~4)
     * @param param - PIN输入配置参数 | the parameter
     * <ul>
     *     <li>pinLimit(byte[]) - 允许输入密码的长度 | the valid length(s) array of the PIN (such as 0,4,5,6 means the valid length is 0, 4 ~6)</li>
     *     <li>timeout(int) - 输入超时时间，单位（秒）| the timeout, second</li>
     *     <li>isOnline(boolean) - 是否联机PIN | is a online PIN</li>
     *     <li>promptString(String) - 提示信息 | the prompt string</li>
     *     <li>pan(String) - 用于加密联机PIN的主帐号（卡号）| the pan for encrypt online PIN</li>
     *     <li>desType(int) - calculate type 算法计算方式<BR>
     *           |----0x01 MK/SK + 3DES (default)<BR>
     *           |----0x02 MK/SK + AES<BR>
     *           |----0x03 MK/SK + SM4<BR>
     *           |----0x04 DUKPT + 3DES<BR>
     *     </li>
     *     <li>numbersFont(String) - url of numbers ttf font (value "" is android system fonts)</li>
     *     <li>promptsFont(String) - url of prompt ttf font(value "" is android system fonts)</li>
     *     <li>otherFont(String) - url of other ttf font(confirm button & backspace button)(value "" is android system fonts)</li>
     *     <li>displayKeyValue(byte[]) - custom the sequence key number of pinpad</li>
     * </ul>
     * @param listener - PIN输入过程监听器 | the call back listener
     * @param globalParam - set global display (if set null, 0~9 are Arabic numerals and confirm/backspace button are english display)
     * <ul>
     *     <li>Display_One(String)</li>
     *     <li>Display_Two(String)</li>
     *     <li>Display_Three(String)</li>
     *     <li>Display_Four(String)</li>
     *     <li>Display_Five(String)</li>
     *     <li>Display_Six(String)</li>
     *     <li>Display_Seven(String)</li>
     *     <li>Display_Eight(String)</li>
     *     <li>Display_Nine(String)</li>
     *     <li>Display_Zero(String)</li>
     *     <li>Display_Confirm(String)</li>
     *     <li>Display_BackSpace(String)</li>
     * </ul>
     * \_en_
     * @brief start PIN input
     *
     * @param keyId the index of PIN KEY(0~99) or dukpt key (0~4)
     * @param param the parameter
     * <ul>
     * <li>pinLimit(byte[]) the valid length(s) array of the PIN (such as {0,4,5,6} means the valid length is 0, 4 ~6)</li>
     * <li>timeout(int) the timeout, second</li>
     * <li>isOnline(boolean) is a online PIN</li>
     * <li>promptString(String) the prompt string</li>
     * <li>pan(String) the pan for encrypt online PIN</li>
     * <li>desType(int) calculate type<BR>
     *   |----0x01 MK/SK + 3DES (default)<BR>
     *   |----0x02 MK/SK + AES<BR>
     *   |----0x03 MK/SK + SM4<BR>
     *   |----0x04 DUKPT + 3DES<BR>
     * </li>
     * <li>numbersFont(String) - url of numbers ttf font (value "" is android system fonts)</li>
     * <li>promptsFont(String) - url of prompt ttf font(value "" is android system fonts)</li>
     * <li>otherFont(String) - url of other ttf font(confirm button & backspace button)(value "" is android system fonts)</li>
     * <li>displayKeyValue(byte[]) - custom the sequence key number of pinpad</li>
     * </ul>
     * @param listener the call back listener
     * @param globalParam - set global display (if set null, 0~9 are Arabic numerals and confirm/backspace button are english display)
     * <ul>
     *     <li>Display_One(String)</li>
     *     <li>Display_Two(String)</li>
     *     <li>Display_Three(String)</li>
     *     <li>Display_Four(String)</li>
     *     <li>Display_Five(String)</li>
     *     <li>Display_Six(String)</li>
     *     <li>Display_Seven(String)</li>
     *     <li>Display_Eight(String)</li>
     *     <li>Display_Nine(String)</li>
     *     <li>Display_Zero(String)</li>
     *     <li>Display_Confirm(String)</li>
     *     <li>Display_BackSpace(String)</li>
     * </ul>
     * @return
     * \en_e
     * \code{.java}
     * \endcode
     * @version 1.0.5 (1.1.0)
     * there is no globleParam if version before 1.0.5.
     * void startPinInput(int keyId, in Bundle param, PinInputListener listener);
     * @see
     *
     */
	void startPinInput(int keyId, in Bundle param, in Bundle globleParam, PinInputListener listener);

	/**
     * \cn_
     * @brief 提交PIN输入
     *
     * \_en_
     * @brief submit the pin input
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     */
    void submitPinInput();
    
    /**
     * \cn_
     * @brief 取消PIN输入过程
     *
     * \_en_
     * @brief stop the pin input
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 */
    void stopPinInput();
    
    /**
     * \cn_
     * @brief 获取最近一次操作的错误
     *
     * @return 错误描述 | @return the description of the last error
     * \_en_
     * @brief get the last error
     *
     * @return the description of the last error
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     */
	String getLastError();

    /**
     * \cn_
     * @brief
     *
     * \_en_
     * @brief same the "calculateData"
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see calculateData
     *
     *
     */
	byte[] colculateData(int mode, int desType, in byte[] key, in byte[] data);


    /**
     * \cn_
     * @brief DUKPT数据加密
     *
     * @param desType - 加密类型 | the type of encrypt
     * <ul>
     *     <li>TYPE_DES - 0x00 DES Type | </li>
     *     <li>TYPE_3DES - 0x01 3DES Type | </li>
     *     <li>TYPE_SM4 - 0x02 SM4 Type | </li>
     *     <li>TYPE_AES - 0x03 AES Type| </li>
     * </ul>
     * @param algorithm - 算法类型 | the type of algorithm 0x01-CBC 0x02-ECB
	 * @param keyid - key索引 | index of key (0~4)
	 * @param data - 计算数据 | the source date
	 * @param CBCInitVec - CBC initial vector. fixed length 8, can be null, default 8 bytes 0x00
     * \_en_
     * @brief DUKPT encrypt
     *
     * @param desType the type of encrypt
     * <ul>
     * <li>TYPE_DES - 0x00 DES Type </li>
     * <li>TYPE_3DES - 0x01 3DES Type </li>
     * <li>TYPE_SM4 - 0x02 SM4 Type</li>
     * <li>TYPE_AES - 0x03 AES Type</li>
     * </ul>
     * @param algorithm the type of algorithm 0x01-CBC 0x02-ECB
	 * @param keyid - index of key (0~4)
	 * @param data - the source date
	 * @param CBCInitVec - CBC initial vector. fixed length 8, can be null, default 8 bytes 0x00
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     *
     */
	byte[] dukptEncryptData(int destype, int algorithm, int keyid, in byte[] data, in byte[] CBCInitVec);

    /**
     * \cn_
     * @brief 保存明文工作密钥（只支持3des）
     *
	 * @param keyType - | the key type 下装工作密钥类型，1-MAC 密钥，2-PIN 密钥，3-TD 密钥
	 * @param keyId - 工作密钥索引 | the index of KEY
	 * @param key - 计算key | the source key
	 *
     * \_en_
     * @brief save the plain key(just support 3des)
     *
	 * @param keyType the key type 1 for MAC ，2 for PIN ，3 for TD
	 * @param keyId the index of KEY
	 * @param key the source key
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     *
	 */
    boolean savePlainKey(int keyType, int keyId, in byte[] key);

    /**
     * \cn_
     * @brief 获取当前ksn
     *
     * @return 返回当前ksn | @return get current dukpt ksn
     * \_en_
     * @brief get the current KSN
     *
     * @return the current dukpt ksn
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     *
	 */
	 byte[] getDukptKsn();


    /**
     * \cn_
     * @brief 获取国密SM2公私钥对
     *
      * @return bundle
      * <ul>
      *     <li>publicKey(string)</li> - 公钥
      *     <li>privateKey(string)</li> - 私钥
      * </ul>
     * \_en_
     * @brief get the SM2 public key & private key
     *
      * @return bundle
      * <ul>
      *     <li>publicKey(string)</li>
      *     <li>privateKey(string)</li>
      * </ul>
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     */
     Bundle generateSM2KeyPair();

    /**
     * \cn_
     * @brief
     *
     * \_en_
     * @brief Get SM3 data summary
     *
      * @data - data;
      * @return byte[] summary
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     */
     byte[] getSM3Summary(in byte[] data);

    /**
     * \cn_
     * @brief
     *
     * \_en_
     * @brief get the SM2 sign
     *
    * @param bundle
    * <ul>
    *     <li>prikey(byte[])</li> - the private key
    *     <li>data(byte[])</li> - the data want to sign
      * </ul>
      * @return calculate resule;
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     */
     byte[] getSM2Sign(in Bundle bundle);

    /**
     * \cn_
     * @brief
     *
    * @keyIndex - the index of key
    * @keyType - type of key
    *     0x01 data encryption key;
    *     0x02 PIN working key
    *     0x03 MAC key;
    *     0x04 transfer key
    *     0x05 Main key
    *     0x11 data encryption key(SM4)
    *     0x12 PIN working key(SM4)
    *     0x13 MAC key(SM4)
    *     0x14 sm4传输密钥transport key(SM4)
    *     0x15 sm4主密钥master key(SM4)
    *     0x21 DATA key(AES)
    *     0x22 PIN key(AES)
    *     0x23 MAC key(AES)
    *     0x24 AES传输密钥 AES transmission key
    *     0x25 AES主密钥 AES master key
     * \_en_
     * @brief Get the checkValue of key
     *
    * @keyIndex - the index of key
    * @keyType - type of key<BR>
    *     0x01 data encryption key;<BR>
    *     0x02 PIN working key<BR>
    *     0x03 MAC key;<BR>
    *     0x04 transfer key<BR>
    *     0x05 Main key<BR>
    *     0x11 data encryption key(SM4)<BR>
    *     0x12 PIN working key(SM4)<BR>
    *     0x13 MAC key(SM4)<BR>
    *     0x14 sm4transport key(SM4)<BR>
    *     0x15 master key(SM4)<BR>
    *     0x21 DATA key(AES)<BR>
    *     0x22 PIN key(AES)<BR>
    *     0x23 MAC key(AES)<BR>
    *     0x24 AES transmission key<BR>
    *     0x25 AES master key<BR>
    *  @return the check value of the key
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
    *
    * */
     byte[] getKeyKCV(int keyIndex, int keyType);

    /**
     * \cn_
     * @brief
     *
     * @keyId - pinkey id (the id is the loadWorkKey(pin) id);
     * @param - parameter
     * <ul>
     *     <li>pinLimit(byte[]) - 允许输入密码的长度 | the valid length(s) array of the PIN (such as 0,4,5,6 means the valid length is 0, 4 ~6)</li>
     *     <li>timeout(int) - 输入超时时间，单位（秒）| the timeout, second</li>
     *     <li>isOnline(boolean) - 是否联机PIN | is a online PIN</li>
     *     <li>pan(String) - 用于加密联机PIN的主帐号（卡号）| the pan for encrypt online PIN</li>
     *     <li>desType(int) - calculate type 算法计算方式
     *       |----0x01 MK/SK + 3DES (default)
     *       |----0x02 MK/SK + AES
     *       |----0x03 MK/SK + SM4
     *       |----0x04 DUKPT + 3DES
     *     </li>
     *     <li>displayKeyValue(byte[]) - custom the sequence key number of pinpad</li>
     * </ul>
     * @pinKeyInfos - the list of PinKeyCoorInfo
     * @listener - listener of PinInputListener
     * @return  map<String String> - the value of 0~9 key to display
     * \_en_
     * @brief App custom pinpad ui interface
     *
     * @keyId - pinkey id (the id is the loadWorkKey(pin) id);
     * @param - parameter
     * <ul>
     *     <li>pinLimit(byte[]) the valid length(s) array of the PIN (such as 0,4,5,6 means the valid length is 0, 4 ~6)</li>
     *     <li>timeout(int) the timeout, second</li>
     *     <li>isOnline(boolean) - is a online PIN</li>
     *     <li>pan(String) - the pan for encrypt online PIN</li>
     *     <li>desType(int) - calculate type <BR>
     *       |----0x01 MK/SK + 3DES (default)<BR>
     *       |----0x02 MK/SK + AES<BR>
     *       |----0x03 MK/SK + SM4<BR>
     *       |----0x04 DUKPT + 3DES<BR>
     *     </li>
     *     <li>displayKeyValue(byte[]) - custom the sequence key number of pinpad</li>
     * </ul>
     * @pinKeyInfos - the list of PinKeyCoorInfo
     * @listener - listener of PinInputListener
     * @return  map<String String> - the value of 0~9 key to display
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     *
     *
     * */
	Map initPinInputCustomView(int keyId, in Bundle param, in List<PinKeyCoorInfo> pinKeyInfos, PinInputListener listener);

	/**
     * \cn_
     * @brief
     *
     * \_en_
     * @brief Execute this interface to activate pinpad.
     *
	 * If you get Map<string string>, you should traversal the map to get the value of key to display.
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 *
	 * */
	void startPinInputCustomView();

	/**
     * \cn_
     * @brief
     *
     * \_en_
     * @brief stop custom pinpad
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 *
	 */
	void endPinInputCustomView();

    /**
     * \cn_
     * @brief 加解密
     *
     * @param mode - 加解密模式
     * <ul>
     * <li>0x00 MK/SK 加密 </li>
     * <li>0x01 MK/SK 解密 </li>
     * </ul>
     * @param desType - 加解密类型
     * <ul>
     * <li>TYPE_DES - 0x00 DES Type  </li>
     * <li>TYPE_3DES - 0x01 3DES Type(EBC) | </li>
     * <li class="strike">TYPE_SM4 - 0x02 SM4 Type  </li>
     * <li class="strike">TYPE_AES - 0x03 AES Type </li>
     * <li>TYPE_SM2_PUBKEY - 0x04 SM2 Type(use public key) </li>
     * <li>TYPE_SM2_PRIVKEY - 0x05 SM2 Type(use private key)  </li>
     * <li>TYPE_3DES - 0x06 3DES Type(CBC, initVec = 00000000)  </li>
     * </ul>
	 * @param key - 计算key | the source key
	 * @param data - 计算数据 | the source date
     *
     * @return 成功返回加解密的数据 | @return the encrypted or decrypted data, null means failure
     * \_en_
     * @brief encrypt or decrypt data
     *
     * @param mode the mode of encrypt or decrypt
     * <ul>
     * <li>0x00 MK/SK encrypt </li>
     * <li>0x01 MK/SK decrypt </li>
     * </ul>
     * @param desType the type of encrypt or decrypt
     * <ul>
     * <li>TYPE_DES - 0x00 DES Type  </li>
     * <li>TYPE_3DES - 0x01 3DES Type(EBC)  </li>
     * <li class="strike">TYPE_SM4 - 0x02 SM4 Type </li>
     * <li class="strike">TYPE_AES - 0x03 AES Type </li>
     * <li>TYPE_SM2_PUBKEY - 0x04 SM2 Type(use public key) </li>
     * <li>TYPE_SM2_PRIVKEY - 0x05 SM2 Type(use private key) </li>
     * <li>TYPE_3DES - 0x06 3DES Type(CBC, initVec = 00000000) </li>
     * </ul>
	 * @param key the source key
	 * @param data the source date
     *
     * @return the encrypted or decrypted data, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     */
	byte[] calculateData(int mode, int desType, in byte[] key, in byte[] data);

    /**
     * \cn_
     * @brief  加解密
     *
     * @param mode - 加解密模式
     * <ul>
     * <li>0x00 MK/SK 加密 </li>
     * <li>0x01 MK/SK 解密 </li>
     * </ul>
     * @param desType - 加解密类型
     * <ul>
     * <li>TYPE_DES - 0x00 DES Type </li>
     * <li>TYPE_3DES - 0x01 3DES Type(EBC) </li>
     * <li class="strike">TYPE_SM4 - 0x02 SM4 Type </li>
     * <li class="strike">TYPE_AES - 0x03 AES Type </li>
     * <li>TYPE_SM2_PUBKEY - 0x04 SM2 Type(use public key) </li>
     * <li>TYPE_SM2_PRIVKEY - 0x05 SM2 Type(use private key) </li>
     * <li>TYPE_3DES - 0x06 3DES Type(CBC) NOTICE: WorkKey(TD) id = 60 will be occupied, so user app should not use 60 index of TD </li>
     * </ul>
	 * @param key - 计算key | the source key
	 * @param data - 计算数据 | the source date
	 * @param initVec - 3des cbc初始向量 | 3des cbc init vector
     *
     * @return 成功返回加解密的数据 | @return the encrypted or decrypted data, null means failure
     * \_en_
     * @brief encrypt or decrypt data
     *
     * @param mode the mode of encrypt or decrypt
     * <ul>
     * <li>0x00 MK/SK encrypt </li>
     * <li>0x01 MK/SK decrypt </li>
     * </ul>
     * @param desType the type of encrypt or decrypt
     * <ul>
     * <li>TYPE_DES - 0x00 DES Type </li>
     * <li>TYPE_3DES - 0x01 3DES Type(EBC) </li>
     * <li class="strike">TYPE_SM4 - 0x02 SM4 Type </li>
     * <li class="strike">TYPE_AES - 0x03 AES Type</li>
     * <li>TYPE_SM2_PUBKEY - 0x04 SM2 Type(use public key) </li>
     * <li>TYPE_SM2_PRIVKEY - 0x05 SM2 Type(use private key) </li>
     * <li>TYPE_3DES - 0x06 3DES Type(CBC) NOTICE: WorkKey(TD) id = 60 will be occupied, so user app should not use 60 index of TD </li>
     * </ul>
	 * @param key the source key
	 * @param data the source date
	 * @param initVec 3des cbc init vector
     *
     * @return the encrypted or decrypted data, null means failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     * <br/>
     */
	byte[] calculateDataEx(int mode, int desType, in byte[] key, in byte[] data, in byte[] initVec);

    /**
     * \cn_
     * @brief
     *
     * @param pinKeyId - PIN密钥索引 | the index of PIN KEY(0~99)
     * @param desType - calculate type 算法计算方式
     *   |----0x01 MK/SK + 3DES (default)
     *   |----0x02 MK/SK + AES
     *   |----0x03 MK/SK + SM4
     *   |----0x04 DUKPT + 3DES
     * @param cardNumber - card number (ascii type, such as "1234", you should input byte[4] = "31 32 33 34")
     * @param passws- plain password (String type, such as "1234", you should input String = "1234")
     * \_en_
     * @brief encrypt pinblock from cardnumber & passwd
     *
     * @param pinKeyId the index of PIN KEY(0~99)
     * @param desType calculate type <BR>
     *   |----0x01 MK/SK + 3DES (default)<BR>
     *   |----0x02 MK/SK + AES<BR>
     *   |----0x03 MK/SK + SM4<BR>
     *   |----0x04 DUKPT + 3DES<BR>
     * @param cardNumber - card number (ascii type, such as "1234", you should input byte[4] = "31 32 33 34")
     * @param passws- plain password (String type, such as "1234", you should input String = "1234")
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
     *
     */
	byte[] encryptPinFormat0(int pinKeyId, int desType, in byte[] cardNumber, String passwd);


	/**
     * \cn_
     * @brief 数据密钥加解密
     *
	 * @param keyId - 磁道密钥索引(0~99)
	 * @param encAlg - 加解密算法<BR>
	 *     |---0x01 3DES<BR>
	 *     |---0x02 SM4<BR>
	 *     |---0x03 AES<BR>
	 * @param encMode - 加解密模式<BR>
	 *     |--0x01 ECB <BR>
	 *     |--0x02 CBC <BR>
	 * @param encFlag - 加密或解密<BR>
	 *     |--0x00 encrypt <BR>
	 *     |--0x01 decrypt <BR>
	 * @param data - 数据
	 * @param CBCInitVec - 初始向量，default set null;
	 * @return the result of encrypt data or decrypt data;
     * \_en_
     * @brief encrypt data or decrypt data by data key
     *
	 * @param keyId - data key index(0~99)
	 * @param encAlg- encryption algorithm<BR>
	 *     |---0x01 3DES<BR>
	 *     |---0x02 SM4<BR>
	 *     |---0x03 AES<BR>
	 * @param encMode - encryption mode of operation<BR>
	 *     |--0x01 ECB<BR>
	 *     |--0x02 CBC<BR>
	 * @param encFlag - encryption flag<BR>
	 *     |--0x00 encrypt<BR>
	 *     |--0x01 decrypt<BR>
	 * @param data - data
	 * @param initVec - init vec，default set null;
	 * @return the result of encrypt data or decrypt data;
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     *
	 */
    byte[] calculateByDataKey(int keyId, int encAlg, int encMode, int encFlag, in byte[] data, in byte[] initVec);

	/**
     * \cn_
     * @brief 下装密文主密钥
     *
	 * @param keyId - 下装密钥存储ID | the id (index)
	 * @param key - 密钥 | the key
	 * @param algorithmType - 算法类型 1-3des密文 3-SM4密文 5-AES密文 | 1-3des algorithm 3-SM4 algorithm 5-AES algorithm
	 * @param checkValue - 校验值 | check value (default NULL)
	 * @param extend - 额外附加参数
     * <ul>
     *     <li>isCBCType(boolean) 主密钥加密模式是否是CBC(default false)</li>
     *     <li>initVec(byte[]) cbc初始向量(default 16byte 0)</li>
     * </ul>
	 * @return 成功true，失败false | true on success, false on failure
     * \_en_
     * @brief load the encrypted master key given Algorithm Type
     *
	 * @param keyId the id (index, 0 to 99)
	 * @param key the encrypted key
	 * @param algorithmType 1-3des algorithm <BR>3-SM4 algorithm <BR>5-AES algorithm
	 * @param checkValue - 校验值 | check value (default NULL)
	 * @param extend - extend param
     * <ul>
     *     <li>isCBCType(boolean) judge the mk encrypt mode whether is CBC mode(default false)</li>
     *     <li>initVec(byte[]) cbc initVec(default 16byte 0)</li>
     * </ul>
	 * @return 成功true，失败false | true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadTEK loadTEKWithAlgorithmType loadEncryptMainKey loadMainKey loadWorkKey loadWorkKeyWithDecryptType
	 */

	boolean loadEncryptMainKeyEX(int keyId, in byte[] key, int algorithmType, in byte[] checkValue, in Bundle extend);

	/**
     * \cn_
     * @brief 下装工作密钥
     *
	 * @param keyType - 下装工作密钥类型 | select the workkey type<BR>
	 *     |---1-MAC key, 2-PIN key, 3-TD key<BR>
	 *     |---5-(SM4)MAC key, 6-(SM4)PIN key, 7-(SM4)TD key<BR>
	 *     |---9-(AES)MAC key, 10-(AES)PIN key, 11-(AES)TD key<BR>
	 * @param mkId - 解密工作密钥的主密钥ID | the id of master key for decrypt work key
	 * @param wkId - 下装密钥存储ID | set the workkey id (index 0~99)
	 * @param decKeyType - 解密密钥类型 | select decrypt key type<BR>
	 *     |---0-3DES master key<BR>
	 *     |---1-transport key<BR>
	 *     |---2-SM4 master key<BR>
	 *     |---3-AES master key<BR>
	 * @param key - 密钥 | encrypt key
	 * @param checkValue - 校验值
	 * @return 成功true，失败false
     * \_en_
     * @brief load the work key given decrypt type
     *
	 * @param keyType select the workkey type<BR>
	 *     |---1-MAC key, 2-PIN key, 3-TD key<BR>
	 *     |---5-(SM4)MAC key, 6-(SM4)PIN key, 7-(SM4)TD key<BR>
	 *     |---9-(AES)MAC key, 10-(AES)PIN key, 11-(AES)TD key<BR>
	 * @param mkId the id of master key for decrypt work key
	 * @param wkId set the workkey id (index 0~99)
	 * @param decKeyType select decrypt key type<BR>
	 *     |---0-3DES master key<BR>
	 *     |---1-transport key<BR>
	 *     |---2-SM4 master key<BR>
	 *     |---3-AES master key<BR>
	 * @param encrypt key
	 * @param checkValue check value (null for none)
	 * @param extend - extend param
     * <ul>
     *     <li>isCBCType(boolean) judge the mk encrypt mode whether is CBC mode(default false)</li>
     *     <li>initVec(byte[]) cbc initVec(default 16byte 0)</li>
     * </ul>
	 * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see loadWorkKey loadEncryptMainKey loadEncryptMainKeyWithAlgorithmType loadMainKey loadMainKeyWithAlgorithmType
     *
	 *  | <br/>
	 */
	boolean loadWorkKeyEX(int keyType, int mkId, int wkId, int decKeyType, in byte[] key, in byte[] checkValue, in Bundle extend);
}
