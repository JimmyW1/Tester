package com.test.ui.activities.developer_test.boc_developer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;

public class BocDeveloperTestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_test);

        Button btnPBOCTest = (Button) findViewById(R.id.btn_pboc_test);
        btnPBOCTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_pboc_test:
                break;
        }
    }
}
