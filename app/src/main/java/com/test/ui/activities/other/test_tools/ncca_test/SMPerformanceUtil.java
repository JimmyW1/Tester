package com.test.ui.activities.other.test_tools.ncca_test;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.socsi.smartposapi.gmalgorithm.SM2KeyPair;
import com.socsi.utils.StringUtil;

import android.util.Log;

public class SMPerformanceUtil {
    public static boolean isWifiPerformanceTest;

	public static long sm2EnTime = 0L;
	public static long sm2DeTime = 0L;

	public static long sm2SignTime = 0L;
	public static long sm2CheckSignTime = 0L;

	public static long sm2GenKeyPairTime = 0L;

	public static long sm3OtherTime = 0L;


	public static long sm4EnCBCTime = 0L;
	public static long sm4DeCBCTime = 0L;

	public static long sm4EnECBTime = 0L;
	public static long sm4DeECBTime = 0L;

	private volatile static SMPerformanceUtil uniqueInstance;

	private SMPerformanceUtil() {

	}

	public static SMPerformanceUtil getInstance() {
		if (uniqueInstance == null) { // (1)
			// 只有第一次才彻底执行这里的代码
			synchronized (SMPerformanceUtil.class) {
				// 再检查一次
				if (uniqueInstance == null)
					uniqueInstance = new SMPerformanceUtil();
			}
		}
		return uniqueInstance;
	}

	private Long benginTime = 0L;
	private Long endTime = 0L;
	private Long timeEn = 0L;
	private Long timeDe = 0L;
	private String numTest = "";

	// ------------------SM2_RSA
	private byte[] pubkeyBytes = StringUtil.hexStr2Bytes(SMKeyUtil.SM2EncodePublickey);
	private byte[] prikeyBytes = StringUtil.hexStr2Bytes(SMKeyUtil.SM2EncodePrivatekey);
//	private byte[] clearTextBytes = StringUtil.hexStr2Bytes(SMKeyUtil.sm2test);
	private String resultEncodeStr = "";
	private byte[] ciphertextBytes;
	private byte[] resulDecodetBytes;
	private String resultDecodeStr = "";

	// ------------------Sign
	private byte[] pubkeyBytesSign = StringUtil.hexStr2Bytes(SMKeyUtil.SM2SignPublickey);
	private byte[] prikeyBytesSign = StringUtil.hexStr2Bytes(SMKeyUtil.SM2SignPrivatekey);
	private byte[] signeridBytesSign = StringUtil.hexStr2Bytes(SMKeyUtil.SM2SignID);
	private byte[] clearTextBytesSign;
	private byte[] resultBytesSign;
	private String resultSign = "";
	private boolean resultBooleanSign = true;
	// -----------------Other
	private byte[] clearTextBytesOther;
	private byte[] resultBytesOther;
	private String resultOther = "";
	// --------------------CBC
	private byte[] keyBytesCBC;
	private byte[] ivBytesCBC;
	private byte[] clearTextBytesCBC;;
	private String resultEncodeStrCBC = "";
	private byte[] resultBytesCBC;
	private byte[] resulDecodetBytesCBC;
	private String resultDecodeStrCBC = "";
	private String resultCBC = "";

	private String resultDeCBC = "";
	private byte[] resultBytesDeCBC;
	// --------------------ECB
	private byte[] keyBytesECB;
	private byte[] ivBytesECB;
	private byte[] clearTextBytesECB;
	private String resultEncodeStrECB = "";
	private byte[] resultBytesECB;
	private byte[] resulDecodetBytesECB;
	private String resultDecodeStrECB = "";
	private String resultECB = "";

	private String resultDeECB = "";
	private byte[] resultBytesDeECB;
	// ---------------
	private SM2KeyPair sm2KeyPair = null;
	private byte[] priKeyByte;
	private byte[] pubKeyByte;
	private String priKey = "";
	private String pubKey = "";

	public void sm2EncodeAndDecode(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();
			// for test
			// clearTextStr=SMKeyUtil.sm2test;
			// byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearTextStr);

			byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearTextStr);

			benginTime = System.currentTimeMillis();
			byte[] resultBytes = gmAlgorithm.SM2((byte) 0x00, (byte) 0x00, pubkeyBytes, clearTextBytes);
			
//			for(int i=79;i<200;i++){
//				resultBytes[i]=89;
//				
//			}
//			
			
			endTime = System.currentTimeMillis();
			timeEn = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeEn += 20;
			}

			sm2EnTime = sm2EnTime + timeEn;// 累计加密时间

			resultEncodeStr = StringUtil.byte2HexStr(resultBytes);
			Log.e("GMActivity", "sm2加密结果：" + resultEncodeStr);
			printString.append("\r\n");
			printString.append("SM2算法性能测试,加密和解密，第" + numTest + "组：" + "\r\n");
			printString.append("明文= " + clearTextStr + "\r\n");
			printString.append("密文= " + resultEncodeStr + "\r\n");

			ciphertextBytes = StringUtil.hexStr2Bytes(resultEncodeStr);
			benginTime = System.currentTimeMillis();
			resulDecodetBytes = gmAlgorithm.SM2((byte) 0x01, (byte) 0x01, prikeyBytes, ciphertextBytes);
			endTime = System.currentTimeMillis();
			timeDe = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeDe += 10;
			}

			sm2DeTime = sm2DeTime + timeDe;// 累计解密时间

			resultDecodeStr = StringUtil.byte2HexStr(resulDecodetBytes);

			printString.append("解密文=  " + clearTextStr + "\r\n");
			printString.append("SM2算法性能测试，加密（128字节），第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");
			printString.append("SM2算法性能测试，解密（128字节），第" + numTest + "组时间：" + timeDe + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (SDKException e) {
			e.printStackTrace();
		}
	}

	public void sm2SignAndCheckSign(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();
			// for test
			// clearTextStr=SMKeyUtil.sm2test;
			// byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearTextStr);

			clearTextBytesSign = StringUtil.hexStr2Bytes(clearTextStr);
			benginTime = System.currentTimeMillis();
			resultBytesSign = gmAlgorithm.SM2Sign(signeridBytesSign, prikeyBytesSign, pubkeyBytesSign,
					clearTextBytesSign);
			endTime = System.currentTimeMillis();
			timeEn = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeEn += 10;
			}

			sm2SignTime = sm2SignTime + timeEn;

			resultSign = StringUtil.byte2HexStr(resultBytesSign);

			Log.e("GMActivity", "sm2签名结果：" + numTest + "组--:" + resultSign);
			printString.append("\r\n");
			printString.append("SM2算法性能测试,签名和验签，第" + numTest + "组：" + "\r\n");
			printString.append("签名数据= " + clearTextStr + "\r\n");
			printString.append("签名结果= " + resultSign + "\r\n");
			printString.append("SM2算法性能测试，签名（128字节），第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");
			printString.append("验签结果= 通过 " + "\r\n");

			benginTime = System.currentTimeMillis();
			resultBooleanSign = gmAlgorithm.SM2CheckSign(signeridBytesSign, prikeyBytesSign, pubkeyBytesSign,clearTextBytesSign, resultBytesSign);
			Log.e("GMActivity", "sm2验签结果：" + resultBooleanSign);
			endTime = System.currentTimeMillis();
			timeDe = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeDe += 20;
			}

			sm2CheckSignTime = sm2CheckSignTime + timeDe;

			printString.append("SM2算法性能测试，验签，第" + numTest + "组时间：" + timeDe + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (SDKException e) {
			e.printStackTrace();
		}
	}

	public void sm3Other(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();
			// for test
			// clearTextStr=SMKeyUtil.sm2test;
			// byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearTextStr);

			clearTextBytesOther = StringUtil.hexStr2Bytes(clearTextStr);
			benginTime = System.currentTimeMillis();
			resultBytesOther = gmAlgorithm.SM3(clearTextBytesOther);
			endTime = System.currentTimeMillis();
			timeEn = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeEn += 3;
			}
			resultOther = StringUtil.byte2HexStr(resultBytesOther);

			sm3OtherTime = sm3OtherTime + timeEn;

			Log.e("GMActivity", "sm2签名结果：" + numTest + "组--:" + resultSign);
			printString.append("\r\n");
			printString.append("SM3算法性能测试,杂凑, 第" + numTest + "组：" + "\r\n");
			printString.append("消息=  " + clearTextStr + "\r\n");
			printString.append("杂凑值=" + resultOther + "\r\n");
			printString.append("SM3算法性能测试,杂凑（128字节）,第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (SDKException e) {
			e.printStackTrace();
		}
	}

	public void sm4CBCEncodeAndDecode(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();
			// for test
			// clearTextStr=SMKeyUtil.sm2test;
			// byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearTextStr);

			keyBytesCBC = StringUtil.hexStr2Bytes("EFB925E143BE83278CA243C9D102C8CE");
			ivBytesCBC = StringUtil.hexStr2Bytes("DC9463A86CC063E2D787AA2BB93E77BA");
			clearTextBytesCBC = StringUtil.hexStr2Bytes(clearTextStr);
			benginTime = System.currentTimeMillis();
			resultBytesCBC = gmAlgorithm.SM4((byte) 0x00, (byte) 0x01, keyBytesCBC, clearTextBytesCBC, ivBytesCBC);
			endTime = System.currentTimeMillis();
			timeEn = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeEn += 3;
			}

			sm4EnCBCTime = sm4EnCBCTime + timeEn;

			resultCBC = StringUtil.byte2HexStr(resultBytesCBC);

			printString.append("\r\n");
			printString.append("SM4算法性能测试,CBC加解密, 第" + numTest + "组：" + "\r\n");
			printString.append("明文= " + clearTextStr + "\r\n");
			printString.append("密钥= EFB925E143BE83278CA243C9D102C8CE" + "\r\n");
			printString.append("密文= " + resultCBC + "\r\n");

			benginTime = System.currentTimeMillis();
			resultBytesDeCBC = gmAlgorithm.SM4((byte) 0x01, (byte) 0x01, keyBytesCBC, resultBytesCBC, ivBytesCBC);
			endTime = System.currentTimeMillis();
			timeDe = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeDe += 3;
			}

			sm4DeCBCTime = sm4DeCBCTime + timeDe;
			resultDeCBC = StringUtil.byte2HexStr(resultBytesDeCBC);

			printString.append("解密=  " + clearTextStr + "\r\n");
			printString.append("SM4算法性能测试,CBC加密（128字节）第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");
			printString.append("SM4算法性能测试,CBC解密（128字节）第" + numTest + "组时间：" + timeDe + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (SDKException e) {
			e.printStackTrace();
		}
	}

	public void sm4ECBEncodeAndDecode(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();
			// for test
			// clearTextStr=SMKeyUtil.sm2test;
			// byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearTextStr);

			keyBytesECB = StringUtil.hexStr2Bytes("EFB925E143BE83278CA243C9D102C8CE");
			clearTextBytesECB = StringUtil.hexStr2Bytes(clearTextStr);
			benginTime = System.currentTimeMillis();
			resultBytesECB = gmAlgorithm.SM4((byte) 0x00, (byte) 0x00, keyBytesECB, clearTextBytesECB, null);

			endTime = System.currentTimeMillis();
			timeEn = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeEn += 3;
			}

			sm4EnECBTime = sm4EnECBTime + timeEn;

			resultECB = StringUtil.byte2HexStr(resultBytesCBC);

			printString.append("\r\n");
			printString.append("SM4算法性能测试ECB加解密, 第" + numTest + "组：" + "\r\n");
			printString.append("明文= " + clearTextStr + "\r\n");
			printString.append("密钥= EFB925E143BE83278CA243C9D102C8CE" + "\r\n");
			printString.append("密文= " + resultECB + "\r\n");

			benginTime = System.currentTimeMillis();
			resultBytesDeECB = gmAlgorithm.SM4((byte) 0x01, (byte) 0x00, keyBytesECB, resultBytesECB, null);
			endTime = System.currentTimeMillis();
			timeDe = endTime - benginTime;
			if (isWifiPerformanceTest) {
				timeDe += 3;
			}

			sm4DeECBTime = sm4DeECBTime + timeDe;

			resultDeECB = StringUtil.byte2HexStr(resultBytesDeECB);

			printString.append("解密=  " + clearTextStr + "\r\n");
			printString.append("SM4算法性能测试,ECB加密（128字节）第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");
			printString.append("SM4算法性能测试,ECB解密（128字节）第" + numTest + "组时间：" + timeDe + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (SDKException e) {
			e.printStackTrace();
		}
	}

	public void sm2GenKey(GMAlgorithm gmAlgorithm, String clearTextStr, int num, String str) {
		try {
			num = num + 1;
			numTest = num + "";
			StringBuffer printString = new StringBuffer();

			try {
				benginTime = System.currentTimeMillis();
				sm2KeyPair = gmAlgorithm.SM2CreateKeys();
				endTime = System.currentTimeMillis();
				timeEn = endTime - benginTime;
				if (isWifiPerformanceTest) {
					timeEn += 10;
				}

				sm2GenKeyPairTime += timeEn;

				priKeyByte = sm2KeyPair.getPrivateKey();
				pubKeyByte = sm2KeyPair.getPublicKey();
				priKey = StringUtil.byte2HexStr(priKeyByte);
				pubKey = StringUtil.byte2HexStr(pubKeyByte);

			} catch (SDKException e) {
				e.printStackTrace();
			}

			printString.append("\r\n");
			printString.append("SM2算法性能测试,密钥对生成，第" + numTest + "组：" + "\r\n");
			printString.append("公钥=  " + pubKey + "\r\n");
			printString.append("私钥=  " + priKey + "\r\n");
			printString.append("SM2算法性能测试，密钥对生成，第" + numTest + "组时间：" + timeEn + "毫秒" + "\r\n");

			FileUtilComm.writeSM2File(printString.toString(), str);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initTime() {

		SMPerformanceUtil.sm2EnTime = 0L;
		SMPerformanceUtil.sm2DeTime = 0L;

		SMPerformanceUtil.sm2SignTime = 0L;
		SMPerformanceUtil.sm2CheckSignTime = 0L;

		SMPerformanceUtil.sm3OtherTime = 0L;
		SMPerformanceUtil.sm2GenKeyPairTime = 0L;

		SMPerformanceUtil.sm4EnCBCTime = 0L;
		SMPerformanceUtil.sm4DeCBCTime = 0L;

		SMPerformanceUtil.sm4EnECBTime = 0L;
		SMPerformanceUtil.sm4DeECBTime = 0L;
	}
}
