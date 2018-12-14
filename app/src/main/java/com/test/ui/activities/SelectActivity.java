package com.test.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.auto_test.AutoTestActivity;
import com.test.ui.activities.developer_test.boc_developer.BocDeveloperTestActivity;
import com.test.ui.activities.manual_test.ManualTestActivity;

public class SelectActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "SelectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Button btn_manual = (Button) findViewById(R.id.btn_manual_test);
        btn_manual.setOnClickListener(this);
        Button btn_auto = (Button) findViewById(R.id.btn_auto_test);
        btn_auto.setOnClickListener(this);
        Button btn_devekoper = (Button) findViewById(R.id.btn_develop_test);
        btn_devekoper.setOnClickListener(this);
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_manual_test:
                clearTestResult();
                intent = new Intent(this, ManualTestActivity.class);
                intent.putExtra("TestCaseType", ModuleEntry.CASE_ALL);
                break;
            case R.id.btn_auto_test:
                clearTestResult();
                intent = new Intent(this, AutoTestActivity.class);
                break;
            case R.id.btn_develop_test:
                intent = new Intent(this, BocDeveloperTestActivity.class);
                break;
            case R.id.btn_back:
                finish();
                return;
            default:
                return;
        }
        startActivity(intent);
    }

    private void clearTestResult() {
        TestEntry testEntry = MyApplication.getInstance().getCurrentTestEntry();
        if (testEntry != null) {
            testEntry.clearTestResult();
        }
    }
}
