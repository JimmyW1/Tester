package com.test.ui.activities.other.test_tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.j300_test.J300TestActivity;
import com.test.ui.activities.other.test_tools.ncca_test.NCCATestActivity;
import com.test.ui.activities.other.test_tools.scb_qr_scan.QRScanActivity;
import com.test.ui.activities.other.test_tools.study.demon.fragments.first_example.FragmentExample1Activity;
import com.test.ui.activities.other.test_tools.study.demon.rxjava.RxJavaTestActivity;
import com.test.ui.activities.other.test_tools.study.demon.toolbar.ToolBarDemonActivity;

public class TestToolsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tools);
        Button btn = (Button) findViewById(R.id.btn_ncca_test);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_j300_test);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_study);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_qr_scan);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_ncca_test:
                intent = new Intent(MyApplication.getContext(), NCCATestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_j300_test:
                intent = new Intent(MyApplication.getContext(), J300TestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_study:
//                intent = new Intent(MyApplication.getContext(), StudyTestActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestOnMeasureOnlayActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestSelfViewPager.class);
//                intent = new Intent(MyApplication.getContext(), TestScrollCollision1Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestScrollCollision2Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestMyCircleViewActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestNotificationActivity.class);
//                intent = new Intent(MyApplication.getContext(), AnimationTest1Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestMyTestViewActivity.class);
//                intent = new Intent(MyApplication.getContext(), ToolBarDemonActivity.class);
//                intent = new Intent(MyApplication.getContext(), FragmentExample1Activity.class);
                intent = new Intent(MyApplication.getContext(), RxJavaTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qr_scan:
                intent = new Intent(MyApplication.getContext(), QRScanActivity.class);
                startActivity(intent);
                break;
        }
    }
}
