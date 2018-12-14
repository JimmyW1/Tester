package com.test.configuration;

import com.test.util.LogUtil;
import com.test.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class ServiceAccessUIConfig {
    private final String TAG = "ServiceAccessUIConfig";
    private static ServiceAccessUIConfig instance;
    private Map<String, String> accesserUIMap;
    private Map<String, String> accesserPackagePathMap;

    public ServiceAccessUIConfig() {
        accesserUIMap = new HashMap<>();
        accesserPackagePathMap = new HashMap<>();
    }

    public static synchronized ServiceAccessUIConfig getInstance() {
        if (instance == null) {
            instance = new ServiceAccessUIConfig();
        }

        return instance;
    }

    public String getUIName(String accesserName) {
        if (!accesserUIMap.containsKey(accesserName)) {
            return null;
        }

        return accesserUIMap.get(accesserName);
    }

    public void addUIConfig(String serviceAccessName, int resId) {
        String uiName = SystemUtil.getResString(resId);

        if (accesserUIMap.containsKey(serviceAccessName)) {
            LogUtil.d(TAG, "ServiceAccesser[" + serviceAccessName + "] already exist in ui name!");
            return;
        }

        accesserUIMap.put(serviceAccessName, uiName);
    }

    public void addPackagePath(String serviceAccessName, String testModulesPackagePath) {
        if (accesserPackagePathMap.containsKey(serviceAccessName)) {
            LogUtil.d(TAG, "ServiceAccesser[" + serviceAccessName + "] already exist in add package path!");
            return;
        }

        accesserPackagePathMap.put(serviceAccessName, testModulesPackagePath);
    }

    public String getTestModulesPackagePath(String serviceAccessName) {
        if (!accesserPackagePathMap.containsKey(serviceAccessName)) {
            return null;
        }

        return accesserPackagePathMap.get(serviceAccessName);
    }
}
