package com.test.ui.activities.other.test_tools.ncca_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;

public class NCCALoadTEKActivity extends BaseActivity implements View.OnClickListener {
    private int loginTimes;
    LinearLayout layoutLogin;
    LinearLayout layoutDownload;
    EditText editTextUserName;
    EditText editTextPassWord;
    Button btnLogin;
    EditText editTextKey;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccaload_tek);
        loginTimes = 0;

        layoutLogin = (LinearLayout) findViewById(R.id.layout_login);
        layoutDownload = (LinearLayout) findViewById(R.id.layout_download);
        editTextUserName = (EditText) findViewById(R.id.edit_username);
        editTextPassWord = (EditText) findViewById(R.id.edit_passwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        editTextKey = (EditText) findViewById(R.id.edit_key);
        btnDownload = (Button) findViewById(R.id.btn_download);

        layoutLogin.setVisibility(View.VISIBLE);
        layoutDownload.setVisibility(View.GONE);
        btnLogin.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_download:
                doDownload();
                break;
        }
    }

    private void doLogin() {
        String userName = editTextUserName.getText().toString().trim();
        String passWord = editTextPassWord.getText().toString().trim();
        if (userName == null || userName.length() == 0) {
            DialogUtil.showConfirmDialog("错误提示：", "用户名输入错误！！", null);
            return;
        }

        if (passWord == null || passWord.length() == 0) {
            DialogUtil.showConfirmDialog("错误提示：", "密码输入错误！！", null);
            return;
        }

        if (loginTimes == 0) {
            if (!userName.equals("admin")) {
                DialogUtil.showConfirmDialog("错误提示：", "非法用户名！！", null);
                return;
            }

            if (!passWord.equals("123456789")) {
                DialogUtil.showConfirmDialog("错误提示：", "密码错误！！", null);
                return;
            }
            loginTimes++;
        } else {
            if (!userName.equals("operator1")) {
                DialogUtil.showConfirmDialog("错误提示：", "非法用户名！！", null);
                return;
            }

            if (!passWord.equals("123456")) {
                DialogUtil.showConfirmDialog("错误提示：", "密码错误！！", null);
                return;
            }
            loginTimes = 0;
        }

        layoutLogin.setVisibility(View.GONE);
        layoutDownload.setVisibility(View.VISIBLE);
    }

    private void doDownload() {
        String key = editTextKey.getText().toString().trim();
        if (key == null || key.length() == 0) {
            DialogUtil.showConfirmDialog("错误提示：", "密钥长度不符合规范！！", null);
            return;
        }

        DialogUtil.showConfirmDialog("", "恭喜你，密钥下载成功！！", new DialogUtil.ConfirmDialogListener() {
            @Override
            public void setConfirmFlag(boolean isConfirmSuccess) {
                layoutDownload.setVisibility(View.GONE);
                layoutLogin.setVisibility(View.VISIBLE);
                editTextUserName.setText("");
                editTextPassWord.setText("");
                editTextKey.setText("");
            }
        });
    }
}
