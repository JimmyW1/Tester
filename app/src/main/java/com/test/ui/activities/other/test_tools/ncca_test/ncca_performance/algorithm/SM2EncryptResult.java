package com.test.ui.activities.other.test_tools.ncca_test.ncca_performance.algorithm;

/**
 * Created by CuncheW1 on 2017/9/25.
 */

public class SM2EncryptResult extends Result {
    private String encryptedResult;

    public SM2EncryptResult(String encryptedData) {
        encryptedResult = encryptedData;
    }

    public String getEncryptedResult() {
        return encryptedResult;
    }
}
