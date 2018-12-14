package com.test.logic.service_tester.base_datas;

/**
 * Created by CuncheW1 on 2017/8/7.
 */

import com.test.util.LogUtil;

/**
 * 例如CASE_startPinpad_CN001, CaseEntry各个属性变量值如下：
 * caseId = "CN001"
 * caseApiName = "CASE_startPinpad_CN001"
 * caseModuleName = "ModulePinpad"
 */
public class CaseEntry {
    private final String TAG = "CaseEntry";
    private String caseId;
    private String caseGroupName;
    private String caseApiName;
    private String caseModuleName; // case所属模块名
    private int visableLevel; // 可在设置中选择的当前可见等级测试等级高于设置等级的案例，类型如：单向接口测试等级1，回归测试等级2等
    private boolean isSupportAutoTest; // 是否支持自动化测试，支持为true
    private String caseDescription; // 对测试case的描述
    private int caseDescriptionRid; // 测试case的描述，写在 res/values/strings.xml

    private int testResult; // 对测试结果进行标记，1-测试结果为成功; 0-测试结果为失败; -1 测试结果为待定(即需要测试人员判断，但测试人员未选择结果); -2 未测试
    /*测试结果枚举*/
    public static final int RESULT_PASS = 1;
    public static final int RESULT_FAIL = 0;
    public static final int RESULT_NOT_SELECT = -1;
    public static final int RESULT_NO_TEST = -2;

    public CaseEntry() {
        testResult = RESULT_NO_TEST; // 默认测试结果为未测试状态
    }

    public String getCaseId() {
        return caseId;
    }

    public String getCaseGroupName() {
        return caseGroupName;
    }

    public String getCaseApiName() {
        return caseApiName;
    }

    public String getCaseModuleName() {
        return caseModuleName;
    }

    public int getVisableLevel() {
        return visableLevel;
    }

    public boolean isSupportAutoTest() {
        return isSupportAutoTest;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public int getTestResult() {
        return testResult;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public void setCaseGroupName(String caseGroupName) {
        this.caseGroupName = caseGroupName;
    }

    public void setCaseApiName(String caseApiName) {
        this.caseApiName = caseApiName;
    }

    public void setCaseModuleName(String caseModuleName) {
        this.caseModuleName = caseModuleName;
    }

    public void setVisableLevel(int visableLevel) {
        this.visableLevel = visableLevel;
    }

    public void setSupportAutoTest(boolean supportAutoTest) {
        isSupportAutoTest = supportAutoTest;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }
    public void setTestResult(int testResult) {
        if (testResult < RESULT_NO_TEST || testResult > RESULT_PASS) {
            this.testResult = RESULT_NOT_SELECT;
            return;
        }
        LogUtil.d(TAG, "Test api[" + caseApiName + "], result=" + testResult);
        this.testResult = testResult;
    }
    public int getCaseDescriptionRid() {
        return caseDescriptionRid;
    }

    public void setCaseDescriptionRid(int caseDescriptionRid) {
        this.caseDescriptionRid = caseDescriptionRid;
    }
}
