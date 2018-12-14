package com.test.ui.activities.other.test_tools.ncca_test.ncca_performance.algorithm;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;

/**
 * Created by CuncheW1 on 2017/9/25.
 */

public abstract class Algorithm {
    private GMAlgorithm gmAlgorithm;

    public Algorithm() {
        if (gmAlgorithm == null) {
            gmAlgorithm = new GMAlgorithm();
            try {
                gmAlgorithm.init();
            } catch (SDKException e) {
                e.printStackTrace();
            }
        }
    }

    public GMAlgorithm getGmAlgorithm() {
        return gmAlgorithm;
    }

    public abstract Result executeAlgorithm();
}
