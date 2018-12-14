package com.test.ui.activities.auto_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.logic.service_tester.base_datas.CaseEntry;
import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.ui.activities.manual_test.CasePageActivity;
import com.test.ui.activities.manual_test.ManualTestActivity;
import com.test.ui.activities.other.test_result.ShowTestResultActivity;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;
import com.test.util.TVLog;

import java.util.ArrayList;
import java.util.Map;

public class ShowTestProcessActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "ShowTestProcessActivity";
    private LinearLayout selectLinearLayout;
    private ProgressBar progressBar;
    private TVLog resultTv;
    private String moduleName;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test_process);
        moduleName = getIntent().getStringExtra("ModuleName");
        if (moduleName == null || moduleName.length() <= 0) {
            finish();
            return;
        }

        handler = getHandler(getMainLooper());

        selectLinearLayout = (LinearLayout) findViewById(R.id.linearlayout_select);
        selectLinearLayout.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.autotest_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.textview_autotest_result);
        resultTv = new TVLog(textView);

        Button button;
        button = (Button) findViewById(R.id.btn_check_result);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_continue_manual_test);
        button.setOnClickListener(this);
        doAutoTest();
    }

    private void doManualTest() {
        Intent intent;

        if (moduleName.equals("ModuleAll")) {
            intent = new Intent(MyApplication.getContext(), ManualTestActivity.class);
            intent.putExtra("TestCaseType", ModuleEntry.CASE_MANUALTEST);
        } else {
            intent = new Intent(MyApplication.getContext(), CasePageActivity.class);
            intent.putExtra("ModuleName", moduleName);
            intent.putExtra("TestCaseType", ModuleEntry.CASE_MANUALTEST);
        }

        startActivity(intent);
        finish();
    }

    private Handler getHandler(Looper looper) {
        return new Handler(looper, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        LogUtil.d(TAG, "msg1=" + msg.arg1);
                        progressBar.setProgress(msg.arg1);
                        break;
                    case 2:
                        LogUtil.d(TAG, "msg3=" + msg.arg1);
                        progressBar.setMax(msg.arg1);
                        break;
                    case 3:
                        LogUtil.d(TAG, "show info:" + msg.obj);
                        resultTv.showInfoInNewLine((String) msg.obj);
                        break;
                    case 4:
                        progressBar.setVisibility(View.GONE);
                        selectLinearLayout.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 执行自动测试
     */
    private void doAutoTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ModuleEntry.AutoTestInterface autoTestInterface = new ModuleEntry.AutoTestInterface() {
                    @Override
                    public void resultCallback(boolean isSuccess, CaseEntry caseEntry, int numTested) {
                        if (handler != null) {
                            Message tmpMsg = handler.obtainMessage();
                            tmpMsg.what = 3;
                            tmpMsg.obj = "Test" + numTested + " " + caseEntry.getCaseApiName() + " result[" + isSuccess + "]";
                            handler.sendMessage(tmpMsg);
                            tmpMsg = handler.obtainMessage();
                            tmpMsg.what = 1;
                            tmpMsg.arg1 = numTested;
                            handler.sendMessage(tmpMsg);
                        }
                    }
                };

                TestEntry testEntry = MyApplication.getInstance().getCurrentTestEntry();
                if (testEntry != null) {
                    if (moduleName.equals("ModuleAll")) {
                        testEntry.excuteAllAutoTestCase(autoTestInterface);
                    } else {
                        ModuleEntry moduleEntry = testEntry.getModuleEntryByModuleName(moduleName);
                        Message tmpMsg = handler.obtainMessage();
                        tmpMsg.what = 2;
                        tmpMsg.arg1 = moduleEntry.getSupportAutoTestCaseTotalNum();
                        handler.sendMessage(tmpMsg);
                        moduleEntry.executeAutoTest(autoTestInterface);
                    }

                    if (handler != null) {
                        Message tmpMsg = handler.obtainMessage();
                        tmpMsg.what = 4;
                        handler.sendMessage(tmpMsg);
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_result:
                Intent intent = new Intent(MyApplication.getContext(), ShowTestResultActivity.class);
                intent.putExtra("moduleName", moduleName);
                intent.putExtra("isAutoTest", true);
                startActivity(intent);
                break;
            case R.id.btn_continue_manual_test:
                doManualTest();
                break;
        }
    }
}
