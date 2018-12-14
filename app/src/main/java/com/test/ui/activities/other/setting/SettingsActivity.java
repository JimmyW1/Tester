package com.test.ui.activities.other.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.test.logic.settings.SettingManager;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    Spinner spinnerVisableLevel;
    SettingManager settingManager;
    Button btnCancel;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinnerVisableLevel = (Spinner) findViewById(R.id.spinner_visablelevel);
        settingManager = SettingManager.getInstance();
        int visableLevel = settingManager.getVisableLevel();
        if (visableLevel < 1 || visableLevel > 10) {
            visableLevel = 1;
        }
        spinnerVisableLevel.setSelection(visableLevel - 1);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                int visableLevel = Integer.parseInt((String) spinnerVisableLevel.getSelectedItem());
                settingManager.setVisableLevel(visableLevel);
                break;
            case R.id.btn_cancel:
                break;
        }

        finish();
    }
}
