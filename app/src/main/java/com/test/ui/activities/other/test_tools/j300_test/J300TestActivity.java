package com.test.ui.activities.other.test_tools.j300_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;

public class J300TestActivity extends BaseActivity implements View.OnClickListener {
    J300NativeApis j300Apis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j300_test);

        Button btn;

        btn = (Button) findViewById(R.id.btn_j300_pinpad);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_j300_scan);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_j300_ctls);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_j300_signboard);
        btn.setOnClickListener(this);


        j300Apis = new J300NativeApis();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.btn_j300_pinpad:
               j300Apis.startPinInput();
               break;
           case R.id.btn_j300_scan:
               j300Apis.startScanner();
               break;
           case R.id.btn_j300_ctls:
               j300Apis.startFeiKa();
               break;
           case R.id.btn_j300_signboard:
               j300Apis.startEsign();
               break;
       }
    }
}
