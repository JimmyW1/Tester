package com.test.ui.activities.other.test_result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.logic.service_tester.TestStatistics;
import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;

public class ShowTestResultActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG = "ShowTestResultActivity";
    private ModuleEntry moduleEntry;
    private TestStatistics testStatistics;
    private String moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test_result);
        moduleName = getIntent().getStringExtra("moduleName");
        boolean isAutoTest = getIntent().getBooleanExtra("isAutoTest", false);
        LogUtil.d(TAG, "Show Test result isAutoTest=" + isAutoTest);

        Button btnTotal = (Button) findViewById(R.id.btn_check_total);
        Button btnSuccess = (Button) findViewById(R.id.btn_check_success);
        Button btnFail = (Button) findViewById(R.id.btn_check_fail);
        Button btnNoSelect = (Button) findViewById(R.id.btn_check_no_select);
        Button btnNoTest = (Button) findViewById(R.id.btn_check_no_test);
        btnTotal.setOnClickListener(this);
        btnSuccess.setOnClickListener(this);
        btnFail.setOnClickListener(this);
        btnNoSelect.setOnClickListener(this);
        btnNoTest.setOnClickListener(this);

        TextView totalView = (TextView) findViewById(R.id.num_total);
        TextView successView = (TextView) findViewById(R.id.num_success);
        TextView failedView = (TextView) findViewById(R.id.num_fail);
        TextView noSelectView = (TextView) findViewById(R.id.num_no_select);
        TextView noTestView = (TextView) findViewById(R.id.num_no_test);

        if (moduleName.equals("ModuleAll")) {
            TestEntry testEntry = MyApplication.getInstance().getCurrentTestEntry();
            if (testEntry != null) {
                testStatistics = testEntry.getAllTestResult(isAutoTest);
            }
        } else {
            moduleEntry = MyApplication.getInstance().getCurrentTestEntry().getModuleEntryByModuleName(moduleName);
            if (moduleEntry != null) {
                testStatistics = moduleEntry.getTestStatistics(isAutoTest);
            }
        }

        totalView.setText("" + testStatistics.getTotalStatisNumByModule(moduleName));
        successView.setText("" + testStatistics.getStatisNumByModuleName(TestStatistics.TYPE_SUCCESS, moduleName));
        failedView.setText("" + testStatistics.getStatisNumByModuleName(TestStatistics.TYPE_FAILED, moduleName));
        noSelectView.setText("" + testStatistics.getStatisNumByModuleName(TestStatistics.TYPE_NO_SELECT, moduleName));
        noTestView.setText("" + testStatistics.getStatisNumByModuleName(TestStatistics.TYPE_NO_TEST, moduleName));
    }

    @Override
    public void onClick(View v) {
        if (testStatistics == null) {
            DialogUtil.showConfirmDialog("", SystemUtil.getResString(R.string.content_null), null);
            return;
        }

        Intent intent = new Intent(MyApplication.getContext(), ResultDetailActivity.class);
        switch (v.getId()) {
            case R.id.btn_check_total:
                intent.putExtra("resultType", "total");
                intent.putExtra("caseApis", testStatistics.getAllCaseApi(moduleName));
                break;
            case R.id.btn_check_success:
                intent.putExtra("resultType", "success");
                intent.putExtra("caseApis", testStatistics.getAllCaseApiByModule(TestStatistics.TYPE_SUCCESS, moduleName));
                break;
            case R.id.btn_check_fail:
                intent.putExtra("resultType", "failed");
                intent.putExtra("caseApis", testStatistics.getAllCaseApiByModule(TestStatistics.TYPE_FAILED, moduleName));
                break;
            case R.id.btn_check_no_select:
                intent.putExtra("resultType", "no_select");
                intent.putExtra("caseApis", testStatistics.getAllCaseApiByModule(TestStatistics.TYPE_NO_SELECT, moduleName));
                break;
            case R.id.btn_check_no_test:
                intent.putExtra("resultType", "no_test");
                intent.putExtra("caseApis", testStatistics.getAllCaseApiByModule(TestStatistics.TYPE_NO_TEST, moduleName));
                break;
        }
        startActivity(intent);
    }
}
