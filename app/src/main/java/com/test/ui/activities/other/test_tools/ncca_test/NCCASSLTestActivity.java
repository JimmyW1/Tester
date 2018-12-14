package com.test.ui.activities.other.test_tools.ncca_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;

public class NCCASSLTestActivity extends BaseActivity {
    private final String TAG = "NCCASSLTestActivity";
    private EditText ipAddrText;
    private EditText portText;
    private Button connectButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccassltest);

        ipAddrText = (EditText) findViewById(R.id.et_ncca_ssl_ip);
        portText = (EditText) findViewById(R.id.et_ncca_ssl_port);

        preferences = MyApplication.getContext().getSharedPreferences("SSL_config", Context.MODE_PRIVATE);
        String ip = preferences.getString("IP_ADDR", null);
        String port = preferences.getString("PORT", null);

        if (ip != null && ip.length() != 0) {
            ipAddrText.setText(ip);
        }

        if (port != null && port.length() != 0) {
            portText.setText(port);
        }

        connectButton = (Button) findViewById(R.id.btn_ssl_connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ip = ipAddrText.getText().toString().trim();
                final String port = portText.getText().toString().trim();
                Log.d(TAG, "ip=" + ip);
                Log.d(TAG, "port=" + port);
                if (ip == null || ip.length() == 0) {
                    DialogUtil.showConfirmDialog("提示:", "请输入IP地址!!", null);
                    return;
                }

                if (port == null || port.length() == 0) {
                    DialogUtil.showConfirmDialog("提示:", "请输入端口号!!", null);
                    return;
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean bRet;
                        try {
                            preferences.edit().putString("IP_ADDR", ip).commit();
                            preferences.edit().putString("PORT", port).commit();
                            bRet = startSSLConnect(ip, port);
                        } catch (Exception e) {
                            e.printStackTrace();
                            bRet = false;
                        }

                        if (bRet) {
                            DialogUtil.showConfirmDialog("提示:", "恭喜你，连接成功!!", null);
                        } else {
                            DialogUtil.showConfirmDialog("提示:", "对不起，连接失败!!", null);
                        }
                    }
                }).start();
           }
        });
    }

    private native boolean startSSLConnect(String ip, String port);

    static {
        System.loadLibrary("native-lib");
    }
}
