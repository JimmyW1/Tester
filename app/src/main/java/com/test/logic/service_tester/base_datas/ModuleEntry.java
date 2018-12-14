package com.test.logic.service_tester.base_datas;

import com.test.logic.annotations.AnnotationUtil;
import com.test.logic.service_tester.TestStatistics;
import com.test.logic.settings.SettingManager;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class ModuleEntry {
    private final String TAG = "ModuleEntry";
    private String moduleName;
    private String manaulTestUIName;
    private String autoTestUIName;
    private String moduleClassName;
    private BaseModule module;
    private ArrayList<CaseEntry> caseEntries;
    private int supportAutoTestCaseTotalNum;

    // 临时保存Map 如果lastVisableLevel和lastCaseType有变化会影响其变化
    private Map<String, ArrayList<CaseEntry>> caseEntryMap;
    private int lastVisableLevel;
    private int lastCaseType;

    public static final int CASE_ALL = 1000;
    public static final int CASE_AUTOTEST = 1001;
    public static final int CASE_MANUALTEST = 1002;

    public void setManaulTestUIName(String manaulTestUIName) {
        this.manaulTestUIName = manaulTestUIName;
    }

    public void setAutoTestUIName(String autoTestUIName) {
        this.autoTestUIName = autoTestUIName;
    }

    public String getManaulTestUIName() {
        return manaulTestUIName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getAutoTestUIName() {
        return autoTestUIName;
    }

    public String getModuleClassName() {
        return moduleClassName;
    }

    public BaseModule getModule() {
        LogUtil.d(TAG, "get Module[" + this.getModuleName() + "]");

//        if (module == null) {
            try {
                LogUtil.d(TAG, "get new Module[" + this.getModuleName() + "]");
                Class<?> aClass = Class.forName(this.getModuleClassName());
                module = (BaseModule) aClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                module = null;
            }
//        }

        return module;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setModuleClassName(String moduleClassName) {
        this.moduleClassName = moduleClassName;
    }

    public void setModule(BaseModule module) {
        this.module = module;
    }

    public Map<String, ArrayList<CaseEntry>> getCaseEntryMap(int caseType) {
        int visableLevel = SettingManager.getInstance().getVisableLevel();

        LogUtil.d(TAG, "getCaseEntryMap visableLevel=" + visableLevel + " caseType=" + caseType);
        if (visableLevel <=0 || visableLevel > 10) {
            visableLevel = 10;
        }

        if (caseType != CASE_ALL && caseType != CASE_AUTOTEST && caseType != CASE_MANUALTEST) {
            caseType = CASE_ALL;
        }

        if (caseEntryMap == null || lastVisableLevel != visableLevel || lastCaseType != caseType) {
            caseEntryMap = null;
            createCaseEntryMap(visableLevel, caseType);
            lastVisableLevel = visableLevel;
            lastCaseType = caseType;
        }

        return caseEntryMap;
    }

    private void createCaseEntrys() {
        Class<?> moduleClass;

        if (caseEntries == null) {
            caseEntries = new ArrayList<>();
            LogUtil.i(TAG, "Executing createCaseEntrys(" + moduleName + ")");

            try {
                moduleClass = Class.forName(this.getModuleClassName());
                LogUtil.i(TAG, "ModuleClass=" + moduleClass.getName());

                Method[] methods = moduleClass.getMethods();
                for (Method method:methods
                        ) {
                    String methodName = method.getName();
                    LogUtil.i(TAG, "Method=[" + methodName + "]");
                    if (methodName.startsWith("CASE_")) {
                        int idx = methodName.indexOf("_CN", "CASE_".length());
                        String caseGroupName = methodName.substring("CASE_".length() , idx);
                        LogUtil.i(TAG, "GroupName=[" + caseGroupName + "]");

                        String caseId = methodName.substring(idx+1);
                        LogUtil.i(TAG, "CaseId=[" + caseId + "]");

                        CaseEntry caseEntry = new CaseEntry();
                        caseEntry.setCaseApiName(methodName);
                        caseEntry.setCaseId(caseId);
                        caseEntry.setCaseModuleName(getModuleName());
                        caseEntry.setCaseGroupName(caseGroupName);

                        /**
                         * 查看是否case有CaseAttributes注解，如果有设置注解值，否则设置默认值
                         * 返回小于等于visableLevel的所有case
                         */
                        caseEntry.setSupportAutoTest(AnnotationUtil.isSupportAutoTest(method));
                        caseEntry.setVisableLevel(AnnotationUtil.getVisableLevel(method));
                        caseEntry.setCaseDescription(AnnotationUtil.getCaseDescription(method));

                        /**
                         * 兼容李凯设计的中行自动化测试程序
                         */
                        String description = AnnotationUtil.getTestDetailCaseAnnotationDescription(method);
                        if (description != null || description.length() > 0) {
                            caseEntry.setCaseDescription(description);
                        }

                        /**
                         * 银商测试用例描述写在strings.xml中的，我觉得挺好，不用在代码那写一大串中文描述
                         */
                        int descriptionRid = AnnotationUtil.getDescriptionRid(method);
                        caseEntry.setCaseDescriptionRid(descriptionRid);

                        if (caseEntry.isSupportAutoTest()) {
                            supportAutoTestCaseTotalNum++;
                        }

                        caseEntries.add(caseEntry);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                caseEntries = null;
            }
        }
    }

    private Map<String, ArrayList<CaseEntry>> createCaseEntryMap(int visableLevel, int caseType) {
        createCaseEntrys(); // 检查是否已建立所有CaseEntry的数组，没有建立

        LogUtil.i(TAG, "Executing createCaseMap(" + moduleName + ")");
        if (caseEntryMap == null) {
            caseEntryMap = new HashMap<>();
        }

        if (caseEntries != null) {
            Iterator<CaseEntry> iterator = caseEntries.iterator();
            while (iterator.hasNext()) {
                CaseEntry caseEntry = iterator.next();
                String caseGroupName = caseEntry.getCaseGroupName();

                ArrayList<CaseEntry> caseGroup = caseEntryMap.get(caseGroupName);
                if (caseEntry.getVisableLevel() <= visableLevel) {
                    if (caseType == CASE_ALL
                            || (caseEntry.isSupportAutoTest() && caseType == CASE_AUTOTEST)
                            || (!caseEntry.isSupportAutoTest() && caseType == CASE_MANUALTEST)) {
                        if (caseGroup == null) { // 放到这里是为了避免空group
                            caseGroup = new ArrayList<>();
                            caseEntryMap.put(caseGroupName, caseGroup);
                        }

                        caseGroup.add(caseEntry);
                    }
                }
            }
        }

        return caseEntryMap;
    }

    public boolean executeMethod(String methodName) {
        try {
            Class<?> aClass = Class.forName(this.getModuleClassName());
            Method method = aClass.getMethod(methodName);
            LogUtil.i(TAG, "executeMethod caseName=" + methodName + " ModuleName=" + moduleName);
            if (method != null) {
                LogUtil.i(TAG, "----true-----------");
                try {
                    BaseModule module = getModule();

                    /* 判断执行case返回值类型是否是boolean */
                    Class <?> returnType = method.getReturnType();
                    if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
                        return (boolean) method.invoke(module);
                    } else {
                        LogUtil.d(TAG, "Method[" + methodName + "] return type not boolean.");
                        method.invoke(module);
                        return false; // 如果是自动类型CASE，但CASE函数返回值不为空，就会被统计到错误CASE中。
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "ExecuteMethod exception happened.");
                    if (!MyApplication.getInstance().getTopActivity().isFinishing()) {
                        MyApplication.getInstance().getTopActivity().finish();
                    }
                    DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.error_module_not_extends), null);
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                LogUtil.i(TAG, "----false-----------");
                return false;
            }
        } catch (ClassNotFoundException e) {
            LogUtil.e(TAG, "No class found, className=" + moduleClassName);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            LogUtil.e(TAG, "No such method found, methodName=" + methodName);
            e.printStackTrace();
        }

        return false;
    }

    public TestStatistics getTestStatistics(boolean isAutoTest) {
        TestStatistics testStatistics = new TestStatistics();

        int visableLevel = SettingManager.getInstance().getVisableLevel();
        Iterator<CaseEntry> iterator = caseEntries.iterator();
        while (iterator.hasNext()) {
            CaseEntry caseEntry = iterator.next();
            if (isAutoTest && !caseEntry.isSupportAutoTest()) { // 统计自动测试结果，如果非自动测试案例不加入统计
                continue;
            }

            if (caseEntry.getVisableLevel() > visableLevel) { // 小于当前设置的优先级的案例都不加入统计
                continue;
            }

            String moduleName = caseEntry.getCaseModuleName();
            String caseName = caseEntry.getCaseApiName();
            switch (caseEntry.getTestResult()) {
                case CaseEntry.RESULT_PASS:
                    testStatistics.addTestRecord(TestStatistics.TYPE_SUCCESS, moduleName, caseName);
                    break;
                case CaseEntry.RESULT_FAIL:
                    testStatistics.addTestRecord(TestStatistics.TYPE_FAILED, moduleName, caseName);
                    break;
                case CaseEntry.RESULT_NOT_SELECT:
                    testStatistics.addTestRecord(TestStatistics.TYPE_NO_SELECT, moduleName, caseName);
                    break;
                case CaseEntry.RESULT_NO_TEST:
                    testStatistics.addTestRecord(TestStatistics.TYPE_NO_TEST, moduleName, caseName);
                    break;
            }
        }

        return testStatistics;
    }

    public void executeAutoTest(AutoTestInterface autoTestInterface) {
        createCaseEntrys();
        int visableLevel = SettingManager.getInstance().getVisableLevel();
        if (caseEntries != null) {
            int numTested = 0;
            Iterator<CaseEntry> iterator = caseEntries.iterator();
            while (iterator.hasNext()) {
                CaseEntry caseEntry = iterator.next();
                if (caseEntry.isSupportAutoTest() && caseEntry.getVisableLevel() <= visableLevel) {
                    boolean bRet = executeMethod(caseEntry.getCaseApiName());
                    if (bRet) {
                        caseEntry.setTestResult(CaseEntry.RESULT_PASS);
                    } else {
                        caseEntry.setTestResult(CaseEntry.RESULT_FAIL);
                    }

                    numTested++;
                    if (autoTestInterface != null) {
                        autoTestInterface.resultCallback(bRet, caseEntry, numTested);
                    }
                }
            }
        }
    }

    public interface AutoTestInterface {
        void resultCallback(boolean isSuccess, CaseEntry caseEntry, int numTested);
    }

    public int getSupportAutoTestCaseTotalNum() {
        createCaseEntrys();
        return supportAutoTestCaseTotalNum;
    }

    public void clearTestResult() {
        if (caseEntries != null) {
            Iterator<CaseEntry> iterator = caseEntries.iterator();
            while (iterator.hasNext()) {
                CaseEntry caseEntry = iterator.next();
                caseEntry.setTestResult(CaseEntry.RESULT_NO_TEST);
            }
        }
    }
}
