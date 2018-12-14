package com.test.ui.activities.other.test_tools.ncca_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.StringUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NCCAAlgorithmTestctivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "NCCAAlgorithmTestctivity";
    EditText randomBitsText;
    NCCAUtil nccaUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccatestalgorithm);
        Button btn;
        nccaUtil = new NCCAUtil();

        btn = (Button) findViewById(R.id.btn_sm2_encrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_decrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_genkeypairs);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_pre_sign);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_pre_verify);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_nopre_sign);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm2_nopre_verify);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_sm3_mac_verify);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_sm4_mac_verify);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm4_cbc_encrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm4_cbc_decrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm4_ecb_encrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_sm4_ecb_decrypt);
        btn.setOnClickListener(this);


        btn = (Button) findViewById(R.id.btn_rsa_encrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_rsa_decrypt);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_rsa_sign);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_rsa_verify);
        btn.setOnClickListener(this);

        randomBitsText = (EditText) findViewById(R.id.edittext_randombits);
        btn = (Button) findViewById(R.id.btn_random);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_other_performance);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_other_128m_random);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String showInfo = "";

        switch (v.getId()) {
            case R.id.btn_sm2_encrypt:
                showInfo = nccaUtil.sm2Encrypt();
                break;
            case R.id.btn_sm2_decrypt:
                showInfo = nccaUtil.sm2Decrypt();
                break;
            case R.id.btn_sm2_genkeypairs:
                showInfo = nccaUtil.sm2GenerateKeyPair();
                break;
            case R.id.btn_sm2_nopre_sign:
                showInfo = nccaUtil.sm2SignNoPreSign();
                break;
            case R.id.btn_sm2_nopre_verify:
                showInfo = nccaUtil.sm2SignNoPreCheck();
                break;
            case R.id.btn_sm2_pre_sign:
                showInfo = nccaUtil.sm2SignPreSign();
                break;
            case R.id.btn_sm2_pre_verify:
                showInfo = nccaUtil.sm2SignPreCheck();
                break;
            case R.id.btn_sm3_mac_verify:
                showInfo = nccaUtil.sm3CheckMac();
                break;
            case R.id.btn_sm4_mac_verify:
                showInfo = nccaUtil.sm4CheckMac();
                break;
            case R.id.btn_sm4_cbc_encrypt:
                showInfo = nccaUtil.sm4CheckCbcEncrypt();
                break;
            case R.id.btn_sm4_cbc_decrypt:
                showInfo = nccaUtil.sm4CheckCbcDecrypt();
                break;
            case R.id.btn_sm4_ecb_encrypt:
                showInfo = nccaUtil.sm4CheckEcbEncrypt();
                break;
            case R.id.btn_sm4_ecb_decrypt:
                showInfo = nccaUtil.sm4CheckEcbDecrypt();
                break;
            case R.id.btn_rsa_encrypt:
                showInfo = nccaUtil.rsaEncrypt();
                break;
            case R.id.btn_rsa_decrypt:
                showInfo = nccaUtil.rsaDecrypt();
                break;
            case R.id.btn_rsa_sign:
                showInfo = nccaUtil.rsaSign();
                break;
            case R.id.btn_rsa_verify:
                showInfo = nccaUtil.rsaCheckSign();
                break;
            case R.id.btn_random:
                String numStr = randomBitsText.getText().toString();
                if (numStr != null && numStr.length() > 0) {
                    showInfo = nccaUtil.getRandomNumber(Integer.parseInt(numStr));
                } else {
                    showInfo = "未输入想要获取随机数位数";
                }
                break;
            case R.id.btn_other_performance:
                Intent intent = new Intent(MyApplication.getContext(), NCCAPerformanceActivity.class);
                startActivity(intent);
                return;
            case R.id.btn_other_128m_random:
                nccaUtil.gen128MRam();
                return;
        }

        DialogUtil.showConfirmDialog("执行结果：", showInfo, null);
    }
}
