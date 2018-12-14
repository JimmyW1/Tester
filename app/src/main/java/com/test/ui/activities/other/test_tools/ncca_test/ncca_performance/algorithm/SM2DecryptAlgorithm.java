package com.test.ui.activities.other.test_tools.ncca_test.ncca_performance.algorithm;

import com.socsi.exception.SDKException;
import com.test.util.StringUtil;

/**
 * Created by CuncheW1 on 2017/9/25.
 */

public class SM2DecryptAlgorithm extends Algorithm {
    private byte[] privateKeyBytes;
    private byte[] decryptDataBytes;

    public SM2DecryptAlgorithm(String privateKeyHexStr, String dataHexStr) {
        try {
            privateKeyBytes = StringUtil.hexStr2Bytes(privateKeyHexStr);
            decryptDataBytes = StringUtil.hexStr2Bytes(dataHexStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result executeAlgorithm() {
        SM2DecryptResult result = null;

        try {
            byte[] decryptedDataBytes = getGmAlgorithm().SM2((byte) 0x01, (byte) 0x01, privateKeyBytes, decryptDataBytes);
            if (decryptedDataBytes != null) {
                result = new SM2DecryptResult(StringUtil.byte2HexStr(decryptedDataBytes));
            }
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return result;
    }
}
