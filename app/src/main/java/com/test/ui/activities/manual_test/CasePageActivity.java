package com.test.ui.activities.manual_test;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.logic.notifier.Notifier;
import com.test.logic.notifier.interfaces.NotifierListener;
import com.test.logic.service_tester.base_datas.CaseEntry;
import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.settings.SettingManager;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.ui.activities.manual_test.adapters.CaseListExpandableAdapter;
import com.test.ui.activities.other.test_result.ShowTestResultActivity;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;
import com.test.util.TVLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CasePageActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "CasePageActivity";
    ExpandableListView caseTreeView;
    CaseListExpandableAdapter caseListExpandableAdapter;
    ModuleEntry moduleEntry;
    String moduleName;
    TVLog resultTv;
    TVLog descriptionTV;
    LinearLayout selectLayout;
    Button successBtn;
    Button failedBtn;
    Map<String, ArrayList<CaseEntry>> currentModuleEntryMap;
    CaseEntry currentCaseEntry;
    View currentOnclickView;
    int testCaseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_page);

        moduleName = getIntent().getStringExtra("ModuleName");
        LogUtil.d(TAG, "CasePage Activity module name is " + moduleName);
        testCaseType = getIntent().getIntExtra("TestCaseType", ModuleEntry.CASE_ALL);
        LogUtil.d(TAG, "testCaseType=" + testCaseType);

        TextView resultView = (TextView) findViewById(R.id.test_process);
        final TextView descriptionView = (TextView) findViewById(R.id.case_description);
        resultTv = MyApplication.getInstance().getCurrentTestEntry().getResultTv();
        resultTv.setTvView(resultView);
        descriptionTV = MyApplication.getInstance().getCurrentTestEntry().getDescriptionTv();
        descriptionTV.setTvView(descriptionView);

        successBtn = (Button) findViewById(R.id.button_success);
        failedBtn = (Button) findViewById(R.id.button_failed);
        successBtn.setOnClickListener(this);
        failedBtn.setOnClickListener(this);
        selectLayout = (LinearLayout) findViewById(R.id.layout_select);


        moduleEntry = MyApplication.getInstance().getCurrentTestEntry().getModuleEntryByModuleName(moduleName);
        if (moduleEntry != null) {
            final int currentVisableLevel = SettingManager.getInstance().getVisableLevel();
            LogUtil.d(TAG, "Settings visableLevel=" + currentVisableLevel);
            /* 根据当前应用设置中的显示case等级获取对应的map */
            currentModuleEntryMap = moduleEntry.getCaseEntryMap(testCaseType);
            TextView testProcessView = (TextView) findViewById(R.id.test_process);
            testProcessView.setMovementMethod(ScrollingMovementMethod.getInstance());
            TextView testDescriptView = (TextView) findViewById(R.id.case_description);
            testDescriptView.setMovementMethod(ScrollingMovementMethod.getInstance());

            watchVisableLevleChangeEvent();

            caseListExpandableAdapter = new CaseListExpandableAdapter(currentModuleEntryMap);
            caseTreeView = (ExpandableListView) findViewById(R.id.casetree);
            caseTreeView.setAdapter(caseListExpandableAdapter);
            caseTreeView.setGroupIndicator(null);
            caseTreeView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    currentOnclickView = v;

                    String caseGroupName = (String) caseListExpandableAdapter.getGroup(groupPosition);
                    String caseId = (String) caseListExpandableAdapter.getChild(groupPosition, childPosition);

                    // 异步执行Case案例,因为案例由测试人员编写，不确定其执行时间会小于5秒钟
                    new ExcuteMethodAsyncTask(caseGroupName, caseId).execute();

                    return false;
                }
            });
        }
    }

    private Handler getHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    private class ExcuteMethodAsyncTask extends AsyncTask {
        private String caseGroupName;
        private String caseId;
        private String methodName;

        public ExcuteMethodAsyncTask(String caseGroupName, String caseId) {
            this.caseGroupName = caseGroupName;
            this.caseId = caseId;
            methodName = "CASE_" + caseGroupName + "_" + caseId;
            LogUtil.i(TAG, "Method name is " + methodName);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            resultTv.clearTVInfo();
            descriptionTV.clearTVInfo();
            currentCaseEntry = getCurrentCaseEntry(caseGroupName, caseId);
            selectLayout.setVisibility(View.GONE);
            if (!currentCaseEntry.isSupportAutoTest()) {
                selectLayout.setVisibility(View.VISIBLE);
            }
            descriptionTV.showInfoInNewLine(currentCaseEntry.getCaseDescription());
            int descriptionRid = currentCaseEntry.getCaseDescriptionRid();
            if (descriptionRid != -1) {
                descriptionTV.showInfoInNewLine(SystemUtil.getResString(descriptionRid));
            }

            setTestResult(CaseEntry.RESULT_NOT_SELECT); // 执行案例之前先设置结果为默认未选择
            resultTv.showInfoInNewLine(SystemUtil.getResString(R.string.case_begin) + ":" + methodName);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            /*反射调用测试人员点击的案例*/
            boolean bRet = moduleEntry.executeMethod(methodName);
            LogUtil.d(TAG, "Excute method result=" + bRet);

            return bRet;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            boolean bRet = (boolean) result;
            resultTv.showInfoInNewLine(SystemUtil.getResString(R.string.case_end));
            if (currentCaseEntry.isSupportAutoTest()) { // 只有案例支持自动测试可以保存测试结果,否则等待人工选择结果
                if (bRet) {
                    setTestResult(CaseEntry.RESULT_PASS);
                } else {
                    setTestResult(CaseEntry.RESULT_FAIL);
                }
            } else {
                resultTv.showInfoInNewLine(SystemUtil.getResString(R.string.case_select));
            }
        }
    }

    private CaseEntry getCurrentCaseEntry(String caseGroupName, String caseId) {
        CaseEntry caseEntry;
        ArrayList<CaseEntry> caseEntries = currentModuleEntryMap.get(caseGroupName);
        Iterator<CaseEntry> iterator = caseEntries.iterator();
        while (iterator.hasNext()) {
            caseEntry = iterator.next();
            if (caseEntry.getCaseId().equals(caseId)) {
                return caseEntry;
            }
        }

        return null;
    }

    private void watchVisableLevleChangeEvent() {
        NotifierListener listener = new NotifierListener() {
            @Override
            public void callback(Notifier entry, int eventType, Object data) {
                if (eventType == SettingManager.EVENT_VISABLELEVEL_CHANGED) {
                    int visableLevel = (int) data;
                    currentModuleEntryMap = moduleEntry.getCaseEntryMap(testCaseType);
                    caseListExpandableAdapter.setDataMap(currentModuleEntryMap);
                    caseListExpandableAdapter.notifyDataSetChanged();
                }
            }
        };

        SettingManager.getInstance().setSettingsNotifierListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_success:
                setTestResult(CaseEntry.RESULT_PASS);
                break;
            case R.id.button_failed:
                setTestResult(CaseEntry.RESULT_FAIL);
                break;
            default:
                setTestResult(CaseEntry.RESULT_NOT_SELECT);
                break;
        }
    }

    private void setTestResult(int testResult) {
        if (testResult == CaseEntry.RESULT_PASS) {
            currentOnclickView.setBackgroundColor(Color.GREEN);
            resultTv.showInfoInNewLine(SystemUtil.getResString(R.string.case_result_success));
        } else if (testResult == CaseEntry.RESULT_FAIL) {
            currentOnclickView.setBackgroundColor(Color.RED);
            resultTv.showInfoInNewLine(SystemUtil.getResString(R.string.case_result_failed));
        } else {
            testResult = CaseEntry.RESULT_NOT_SELECT;
            currentCaseEntry.setTestResult(CaseEntry.RESULT_NOT_SELECT);
            currentOnclickView.setBackgroundColor(Color.GREEN);
        }

        if (currentCaseEntry != null) {
            currentCaseEntry.setTestResult(testResult);
        }
    }

    @Override
    public void onBackPressed() {
        DialogUtil.showConfirmDialog("", SystemUtil.getResString(R.string.dialog_show_result_msg), new DialogUtil.ConfirmDialogListener() {
            @Override
            public void setConfirmFlag(boolean isConfirmSuccess) {
                if (isConfirmSuccess) {
                    Intent intent = new Intent(MyApplication.getContext(), ShowTestResultActivity.class);
                    intent.putExtra("moduleName", moduleName);
                    intent.putExtra("isAutoTest", false);
                    startActivity(intent);
                }
            }
        });
        super.onBackPressed();
    }
}
