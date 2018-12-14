package com.test.ui.activities.manual_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ManualTestActivity extends BaseActivity implements View.OnClickListener {
    private Map<Integer, Button> btnMap;
    private final int MAX_MODULE_SIZE = 20;
    private int caseTestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnMap = new HashMap<>();
        caseTestType = getIntent().getIntExtra("TestCaseType", ModuleEntry.CASE_ALL);
        setContentView(R.layout.activity_manual_test);
        findviews();
        setModuleVisable();
    }

    private void setModuleVisable() {
        int idx = 0;
        TestEntry testEntry = MyApplication.getInstance().getCurrentTestEntry();
        if (testEntry != null) {
            ArrayList<ModuleEntry> moduleEntries = testEntry.getModuleEntries();
            if (moduleEntries != null) {
                Iterator<ModuleEntry> iterator = moduleEntries.iterator();
                while (iterator.hasNext() && idx < MAX_MODULE_SIZE) {
                    ModuleEntry moduleEntry = iterator.next();
                    Button button = btnMap.get(idx++);
                    button.setText(moduleEntry.getManaulTestUIName());
                    button.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        String moduleUIName = (String) ((Button)v).getText();
        Intent intent = new Intent(this, CasePageActivity.class);
        String moduleName = MyApplication.getInstance().getCurrentTestEntry().getModuleEntryByManaulTestUIName(moduleUIName).getModuleName();
        intent.putExtra("ModuleName", moduleName);
        intent.putExtra("TestCaseType", caseTestType);
        startActivity(intent);
    }

    private void findviews() {
        Button button;
        int idx = 0;

        button = (Button) findViewById(R.id.btn_module1);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module2);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module3);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module4);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module5);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module6);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module7);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module8);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module9);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module10);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module11);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module12);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module13);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module14);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module15);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module16);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module17);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module18);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module19);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
        button = (Button) findViewById(R.id.btn_module20);
        button.setOnClickListener(this);
        button.setVisibility(View.GONE);
        btnMap.put(idx++, button);
    }
}
