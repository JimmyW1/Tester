package com.test.ui.activities.other.test_tools.ncca_test.ncca_performance.algorithm;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.test.util.StringUtil;

/**
 * Created by CuncheW1 on 2017/9/25.
 */

public class SM2EncryptAlgorithm extends Algorithm {
    byte[] publicKeyBytes;
    byte[] dataBytes;

    public SM2EncryptAlgorithm(String publicKeyHexStr, String dataHexStr) {
        try {
            publicKeyBytes = StringUtil.hexStr2Bytes(publicKeyHexStr);
            dataBytes = StringUtil.hexStr2Bytes(dataHexStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result executeAlgorithm() {
        SM2EncryptResult result = null;
        try {
            byte[] encryptBytes = getGmAlgorithm().SM2((byte) 0x00, (byte) 0x00, publicKeyBytes, dataBytes);
            if (encryptBytes != null) {
                result = new SM2EncryptResult(StringUtil.byte2HexStr(encryptBytes));
            }
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return result;
    }
}
