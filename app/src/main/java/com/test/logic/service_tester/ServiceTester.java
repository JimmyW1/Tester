package com.test.logic.service_tester;

import com.test.configuration.TestConfigManager;
import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.util.LogUtil;
import com.test.util.PackageUtil;
import com.test.util.ReflectUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TAGW1 on 2017/8/2.
 */

public class ServiceTester {
    private final String TAG = "ServiceTester";
    private static ServiceTester instance;
    private ArrayList<TestEntry> testEntries;
    TestConfigManager testConfigManager;

    public ServiceTester() {
        testConfigManager = TestConfigManager.getInstance();
        testEntries = new ArrayList<>();
        initTester();
    }

    public static synchronized ServiceTester getInstance() {
        if (instance == null) {
            instance = new ServiceTester();
        }

        return instance;
    }

    /**
     * 手动添加新建的Manager
     */
    private void initTester() {
        String packagePath = testConfigManager.getServiceAccesserPackagePath();
        List<String> classNameList = PackageUtil.getClassName(packagePath);
        Iterator<String> iterator = classNameList.iterator();

        while (iterator.hasNext()) {
            String classFullName = iterator.next();
            LogUtil.d(TAG, "ClassFullName[" + classFullName + "]");
            String className = classFullName.substring(classFullName.lastIndexOf(".") + 1);
            LogUtil.d(TAG, "ClassName[" + className + "]");

            TestEntry entry = new TestEntry(className);
            entry.setEntryUIName(testConfigManager.getServiceAccesserUIName(className));

            String modulePackagePath = testConfigManager.getServiceTestPackageName(className);
            LogUtil.d(TAG, "Module package path=[" + modulePackagePath + "].");
            entry.setModuleEntries(createModuleEntrys(modulePackagePath));

            ServiceAccesser serviceAccesser = (ServiceAccesser) ReflectUtil.newInstance(classFullName);
            if (serviceAccesser == null) {
                LogUtil.e(TAG, "Reflect class[" + classFullName + "] failed.");
                continue;
            }
            entry.setServiceAccesser(serviceAccesser);

            testEntries.add(entry);
        }
    }

    private ArrayList<ModuleEntry> createModuleEntrys(String packageName) {
        ArrayList<ModuleEntry> moduleEntries = new ArrayList<>();

        //编译debug需要关闭settings->Instant Run, Release 不用，原因如下连接
        //https://stackoverflow.com/questions/36572515/dexfile-in-2-0-versions-of-android-studio-and-gradle/36594966#36594966
        List<String> classNameList = PackageUtil.getClassName(packageName);
        if (classNameList != null && classNameList.size() > 0) {
            Iterator<String> iter = classNameList.iterator();
            while (iter.hasNext()) {
                ModuleEntry entry = new ModuleEntry();
                String className = iter.next();
                LogUtil.d(TAG, "ClassName[" + className + "]");
                String moduleName = className.substring(className.lastIndexOf(".") + 1);
                LogUtil.d(TAG, "ModuleName[" + moduleName + "]");
                entry.setModuleClassName(className);
                entry.setModuleName(moduleName);
                entry.setManaulTestUIName(testConfigManager.getModuleManaulTestUIName(moduleName));
                entry.setAutoTestUIName(testConfigManager.getModuleAutoTestUIName(moduleName));
                moduleEntries.add(entry);
           }
        }

        return moduleEntries;
    }

    private TestEntry createTestEntry(String entryName, ServiceAccesser accesser, ArrayList<ModuleEntry> moduleEntries) {
        TestEntry entry = new TestEntry(entryName);
        entry.setServiceAccesser(accesser);
        entry.setModuleEntries(moduleEntries);
        return entry;
    }

    public TestEntry getTestEntryByEntryName(String entryName) {
        Iterator<TestEntry> iterator = testEntries.iterator();

        while (iterator.hasNext()) {
            TestEntry entity = iterator.next();
            if (entity.getEntryName().equalsIgnoreCase(entryName)) {
                return entity;
            }
        }

        LogUtil.i(TAG, "TestEntry[" + entryName + "] not found.");
        return null;
    }

    public TestEntry getTestEntryByEntryUIName(String entryUIName) {
        Iterator<TestEntry> iterator = testEntries.iterator();

        while (iterator.hasNext()) {
            TestEntry entity = iterator.next();
            if (entity.getEntryUIName().equalsIgnoreCase(entryUIName)) {
                return entity;
            }
        }

        LogUtil.i(TAG, "TestEntry[" + entryUIName + "] not found.");
        return null;
    }

    public ArrayList<String> getEntryUINameList() {
        ArrayList<String> entryNameList = new ArrayList<>();

        Iterator<TestEntry> iterator = testEntries.iterator();
        while (iterator.hasNext()) {
            TestEntry entry = iterator.next();
            entryNameList.add(entry.getEntryUIName());
        }

        return entryNameList;
    }


}
