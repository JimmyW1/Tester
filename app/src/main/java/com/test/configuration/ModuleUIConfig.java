package com.test.configuration;

import com.test.util.LogUtil;
import com.test.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class ModuleUIConfig {
    private final String TAG = "ModuleUIConfig";
    private static ModuleUIConfig instance;
    private Map<String, String> manualTestUIMap;
    private Map<String, String> autoTestUIMap;

    public ModuleUIConfig() {
        manualTestUIMap = new HashMap<>();
        autoTestUIMap = new HashMap<>();
    }

    public static synchronized ModuleUIConfig getInstance() {
        if (instance == null) {
            instance = new ModuleUIConfig();
        }

        return instance;
    }

    public String getManualTestUIName(String moduleName) {
        if (!manualTestUIMap.containsKey(moduleName)) {
            return null;
        }

        return manualTestUIMap.get(moduleName);
    }

    public String getAutoTestUIName(String moduleName) {
        if (!autoTestUIMap.containsKey(moduleName)) {
            return null;
        }

        return autoTestUIMap.get(moduleName);
    }

    public void addConfig(String moduleName, int manualTestResId, int autoTestResId) {
        String manualTestUIName = SystemUtil.getResString(manualTestResId);
        String autoTestUIName = SystemUtil.getResString(autoTestResId);

        if (manualTestUIMap.containsKey(moduleName)) {
            LogUtil.d(TAG, "moduleName[" + moduleName + "] already exist in manualTestMap!");
        } else {
            manualTestUIMap.put(moduleName, manualTestUIName);
        }

        if (autoTestUIMap.containsKey(moduleName)) {
            LogUtil.d(TAG, "moduleName[" + moduleName + "] already exist in autoTestMap!");
        } else {
            autoTestUIMap.put(moduleName, autoTestUIName);
        }
    }
}
