package com.test.logic.service_tester.base_datas;

import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.logic.service_tester.TestStatistics;
import com.test.util.LogUtil;
import com.test.util.TVLog;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class TestEntry {
    private final String TAG = "TestEntry";
    private String entryName;
    private String entryUIName;
    private ServiceAccesser serviceAccesser;
    private ArrayList<ModuleEntry> moduleEntries;
    private TVLog resultTv; // 手动测试的时候显示执行case结果信息窗口
    private TVLog descriptionTv; // 手动测试的时候显示执行case描述信息窗口

    public TestEntry(String entryName) {
        setEntryName(entryName);
    }

    public String getEntryUIName() {
        return entryUIName;
    }

    public void setEntryUIName(String entryUIName) {
        this.entryUIName = entryUIName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryName() {
        return entryName;
    }

    public ModuleEntry getModuleEntryByManaulTestUIName(String UIName) {
        if (UIName == null || UIName.length() == 0) {
            return null;
        }

        Iterator<ModuleEntry> iterator = moduleEntries.iterator();

        while(iterator.hasNext()) {
            ModuleEntry moduleEntry = iterator.next();
            if (moduleEntry.getManaulTestUIName().equals(UIName)) {
                return moduleEntry;
            }
        }

        return null;
    }

    public ModuleEntry getModuleEntryByAutoTestUIName(String UIName) {
        if (UIName == null || UIName.length() == 0) {
            return null;
        }

        Iterator<ModuleEntry> iterator = moduleEntries.iterator();

        while(iterator.hasNext()) {
            ModuleEntry moduleEntry = iterator.next();
            if (moduleEntry.getAutoTestUIName().equals(UIName)) {
                return moduleEntry;
            }
        }

        return null;
    }

    public ModuleEntry getModuleEntryByModuleName(String moduleName) {
        if (moduleName == null || moduleName.length() == 0) {
            return null;
        }

        Iterator<ModuleEntry> iterator = moduleEntries.iterator();

        while(iterator.hasNext()) {
            ModuleEntry moduleEntry = iterator.next();
            if (moduleEntry.getModuleName().equals(moduleName)) {
                return moduleEntry;
            }
        }

        return null;
    }

    public void clearTestResult() {
        if (moduleEntries != null) {
            Iterator<ModuleEntry> iterator = moduleEntries.iterator();

            while (iterator.hasNext()) {
                ModuleEntry moduleEntry = iterator.next();
                moduleEntry.clearTestResult();
            }
        }
    }

    public void excuteAllAutoTestCase(ModuleEntry.AutoTestInterface autoTestInterface) {
        if (moduleEntries != null) {
            Iterator<ModuleEntry> iterator = moduleEntries.iterator();

            while (iterator.hasNext()) {
                ModuleEntry moduleEntry = iterator.next();
                moduleEntry.executeAutoTest(autoTestInterface);
            }
        }
    }

    public TestStatistics getAllTestResult(boolean isAutoTest) {
        TestStatistics testStatistics = new TestStatistics();
        if (moduleEntries != null) {
            Iterator<ModuleEntry> iterator = moduleEntries.iterator();

            while (iterator.hasNext()) {
                TestStatistics tmp = iterator.next().getTestStatistics(isAutoTest);
                LogUtil.d(TAG, "Failed Size=" + tmp.getFailedRecordList().size());
                testStatistics.addTestStatistics(tmp);
            }
        }

        return testStatistics;
    }

    public void setServiceAccesser(ServiceAccesser serviceAccesser) {
        this.serviceAccesser = serviceAccesser;
    }

    public ServiceAccesser getServiceAccesser() {
        return serviceAccesser;
    }

    public ArrayList<ModuleEntry> getModuleEntries() {
        return moduleEntries;
    }

    public void setModuleEntries(ArrayList<ModuleEntry> moduleEntries) {
        this.moduleEntries = moduleEntries;
    }

    public TVLog getResultTv() {
        if (resultTv == null) {
            resultTv = new TVLog();
        }
        return resultTv;
    }

    public TVLog getDescriptionTv() {
        if (descriptionTv == null) {
            descriptionTv = new TVLog();
        }
        return descriptionTv;
    }
}
