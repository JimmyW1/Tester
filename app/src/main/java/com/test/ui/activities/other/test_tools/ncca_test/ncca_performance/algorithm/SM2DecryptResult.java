package com.test.ui.activities.other.test_tools.ncca_test.ncca_performance.algorithm;

/**
 * Created by CuncheW1 on 2017/9/25.
 */

public class SM2DecryptResult extends Result {
    private String decryptedData;

    public SM2DecryptResult(String decryptedData) {
        this.decryptedData = decryptedData;
    }

    public String getDecryptedData() {
        return decryptedData;
    }
}
