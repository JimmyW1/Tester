package com.test.ui.activities.auto_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AutoTestActivity extends BaseActivity implements View.OnClickListener {
    private Map<Integer, Button> btnMap;
    private final int MAX_MODULE_SIZE = 20;
    private String testAllUIName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_test);
        btnMap = new HashMap<>();
        findviews();
        setModuleVisable();
    }

    private void setModuleVisable() {
        int idx = 0;
        TestEntry testEntry = MyApplication.getInstance().getCurrentTestEntry();
        if (testEntry != null) {
            ArrayList<ModuleEntry> moduleEntries = testEntry.getModuleEntries();
            if (moduleEntries != null) {
                Button button;
                Iterator<ModuleEntry> iterator = moduleEntries.iterator();
                while (iterator.hasNext() && idx < MAX_MODULE_SIZE - 1) {
                    ModuleEntry moduleEntry = iterator.next();
                    button = btnMap.get(idx++);
                    button.setText(moduleEntry.getAutoTestUIName());
                    button.setVisibility(View.VISIBLE);
                }

                /**
                 * 以下是非自动解析添加的按钮，需要单独处理
                 */
                button = btnMap.get(idx);
                testAllUIName = SystemUtil.getResString(R.string.auto_all);
                button.setText(testAllUIName);
                button.setVisibility(View.VISIBLE);
            }
        }
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

    @Override
    public void onClick(View v) {
        String moduleUIName = (String) ((Button) v).getText();
        String moduleName;
        if (moduleUIName.equals(testAllUIName)) {
            moduleName = "ModuleAll";
        } else {
            moduleName = MyApplication.getInstance().getCurrentTestEntry().getModuleEntryByAutoTestUIName(moduleUIName).getModuleName();
        }

        Intent intent = new Intent(MyApplication.getContext(), ShowTestProcessActivity.class);
        intent.putExtra("ModuleName", moduleName);
        startActivity(intent);
    }
}
