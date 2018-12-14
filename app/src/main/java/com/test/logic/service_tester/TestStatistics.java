package com.test.logic.service_tester;

import com.test.util.LogUtil;
import com.test.util.StrUtil;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by CuncheW1 on 2017/4/19.
 */

public class TestStatistics {
    private final String TAG = "TestStatistics";
    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_FAILED = 0;
    public static final int TYPE_NO_SELECT = -1;
    public static final int TYPE_NO_TEST = -2;


    private ArrayList<ResultRecord> successRecordList;
    private ArrayList<ResultRecord> failedRecordList;
    private ArrayList<ResultRecord> noSelectRecordList;
    private ArrayList<ResultRecord> noTestRecordList;

    public TestStatistics() {
        successRecordList = new ArrayList<>();
        failedRecordList = new ArrayList<>();
        noSelectRecordList = new ArrayList<>();
        noTestRecordList = new ArrayList<>();
    }

    private ArrayList<ResultRecord> getResultRecordList(int type) {
        if (type == TYPE_SUCCESS) {
            LogUtil.d(TAG, "Get success list.");
            return successRecordList;
        } else if (type == TYPE_FAILED) {
            LogUtil.d(TAG, "Get failed list.");
            return failedRecordList;
        } else if (type == TYPE_NO_SELECT) {
            LogUtil.d(TAG, "Get no select list.");
            return noSelectRecordList;
        } else {
            LogUtil.d(TAG, "Get no test list.");
            return noTestRecordList;
        }
    }

    public void addTestRecord(int type, String moduleName, String caseName) {
        LogUtil.d(TAG, "type=" + type + " moduleName=" + moduleName + " caseName=" + caseName);
        ResultRecord record = new ResultRecord(moduleName, caseName);
        getResultRecordList(type).add(record);
    }

    /**
     *
     * @param type  TYPE_SUCCESS(1) - 获取成功结果的统计数量  TYPE_FAILED(0) - 获取失败
     * @param moduleName 如果moduleName为ALL则统计所有成功记录的数量,否则统计出相应模块名的数量
     * @return
     */
    public int getStatisNumByModuleName(int type, String moduleName) {
        int totalNum = 0;
        LogUtil.d(TAG, "type=" + type + " moduleName=" + moduleName);
        ArrayList<ResultRecord> list = getResultRecordList(type);

        if (moduleName.equalsIgnoreCase("ModuleAll")) {
            return list.size();
        } else {
            Iterator<ResultRecord> iterator = list.iterator();
            while (iterator.hasNext()) {
                ResultRecord resultRecord = iterator.next();
                if (resultRecord.getModuleName().equals(moduleName)) {
                    totalNum++;
                }
            }

            LogUtil.d(TAG, "totalNum=" + totalNum);
            return totalNum;
        }
    }

    /**
     * 获取当前统计case总数
     * @return 当前统计总数
     */
    public int getTotalStatisNum() {
        return successRecordList.size() + failedRecordList.size() + noTestRecordList.size() + noSelectRecordList.size();
    }

    /**
     * 获取各种类型的统计数量
     * @param type
     * @return
     */
    public int getTotalStatisNumByType(int type) {
        return getResultRecordList(type).size();
    }

    public int getTotalStatisNumByModule(String moduleName) {
        int totalNum = 0;

        totalNum += getStatisNumByModuleName(TYPE_SUCCESS, moduleName);
        totalNum += getStatisNumByModuleName(TYPE_FAILED, moduleName);
        totalNum += getStatisNumByModuleName(TYPE_NO_SELECT, moduleName);
        totalNum += getStatisNumByModuleName(TYPE_NO_TEST, moduleName);

        return totalNum;
    }

    /**
     *
     * @param type  TYPE_SUCCESS(1) - 获取成功结果的统计数量  TYPE_FAILED(0) - 获取失败
     * @param moduleName - ALL 返回相应类型所有记录，否则返回对应类型记录
     * @return
     */
    public ArrayList<ResultRecord> getResultRecord(int type, String moduleName) {
        ArrayList<ResultRecord> list;
        ArrayList<ResultRecord> tmpList = new ArrayList<>();

        if (type == TYPE_FAILED) {
            list = failedRecordList;
        } else {
            list = successRecordList;
        }

        if (moduleName.equalsIgnoreCase("ALL")) {
            return list;
        } else {
            for (ResultRecord record: list ) {
                if (record.getModuleName().equalsIgnoreCase(moduleName)) {
                    tmpList.add(new ResultRecord(record.getModuleName(), record.getCaseName()));
                }
            }

            return tmpList;
        }
    }

    /**
     * 获取模块中所有类型为指定类型的case名
     * @param type
     * @param moduleName 如果值为"ALL",表示获取所有模块
     * @return 返回case名String数组
     */
    public String[] getAllCaseApiByModule(int type, String moduleName) {
        ArrayList<String> caseApis = getCaseApisArrayList(type, moduleName);
        return StrUtil.trans(caseApis);
    }

    private ArrayList<String> getCaseApisArrayList(int type, String moduleName) {
        ArrayList<ResultRecord> list = getResultRecordList(type);
        ArrayList<String> caseApis = new ArrayList<>();

        Iterator<ResultRecord> iterator = list.iterator();
        while (iterator.hasNext()) {
            ResultRecord resultRecord = iterator.next();
            if (moduleName.equalsIgnoreCase("ModuleAll") || moduleName.equals(moduleName)) {
                caseApis.add(resultRecord.getCaseName());
            }
        }

        return caseApis;
    }

    /**
     * 返回模块名为moduleName的所有case名, 当moduleName为ALL时返回所有case名
     * @param moduleName
     * @return case名String数组
     */
    public String[] getAllCaseApi(String moduleName) {
        ArrayList<String> allCaseApis = new ArrayList<>();
        ArrayList<String> caseApi;

        caseApi = getCaseApisArrayList(TYPE_SUCCESS, moduleName);
        allCaseApis.addAll(caseApi);
        caseApi = getCaseApisArrayList(TYPE_FAILED, moduleName);
        allCaseApis.addAll(caseApi);
        caseApi = getCaseApisArrayList(TYPE_NO_SELECT, moduleName);
        allCaseApis.addAll(caseApi);
        caseApi = getCaseApisArrayList(TYPE_NO_TEST, moduleName);
        allCaseApis.addAll(caseApi);

        return StrUtil.trans(allCaseApis);
    }

    public class ResultRecord {
        private String moduleName;
        private String caseName;

        public ResultRecord(String moduleName, String caseName) {
            this.moduleName = moduleName;
            this.caseName = caseName;
        }

        public String getCaseName() {
            return caseName;
        }

        public String getModuleName() {
            return moduleName;
        }
    }

    public void addTestStatistics(TestStatistics testStatistics) {
        if (testStatistics != null) {
            Iterator<ResultRecord> iterator = testStatistics.getSuccessRecordList().iterator();
            while (iterator.hasNext()) {
                ResultRecord record = iterator.next();
                this.successRecordList.add(record);
            }

            iterator = testStatistics.getFailedRecordList().iterator();
            while (iterator.hasNext()) {
                ResultRecord record = iterator.next();
                this.failedRecordList.add(record);
            }

            iterator = testStatistics.getNoSelectRecordList().iterator();
            while (iterator.hasNext()) {
                ResultRecord record = iterator.next();
                this.noSelectRecordList.add(record);
            }

            iterator = testStatistics.getNoTestRecordList().iterator();
            while (iterator.hasNext()) {
                ResultRecord record = iterator.next();
                this.noTestRecordList.add(record);
            }
        }
    }

    public ArrayList<ResultRecord> getSuccessRecordList() {
        return successRecordList;
    }

    public ArrayList<ResultRecord> getFailedRecordList() {
        return failedRecordList;
    }

    public ArrayList<ResultRecord> getNoSelectRecordList() {
        return noSelectRecordList;
    }

    public ArrayList<ResultRecord> getNoTestRecordList() {
        return noTestRecordList;
    }
}
