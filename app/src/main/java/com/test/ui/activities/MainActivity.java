package com.test.ui.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.logic.service_tester.ServiceTester;
import com.test.logic.service_tester.base_datas.TestEntry;
import com.test.ui.activities.developer_test.boc_developer.BocDeveloperTestActivity;
import com.test.ui.activities.other.test_tools.TestToolsActivity;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private ServiceTester tester;
    private Map<Integer, Button> btnMap;
    private final int MAX_ENTRY_SIZE = 10; /*由于mainlayout.xml里面静态定义的Btn数最大为10个*/
    private ServiceConnection myConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tester = ServiceTester.getInstance();
        btnMap = new HashMap<>();
        setContentView(R.layout.activity_main);
        findViews();
        setEntryVisable();
    }

    private void setEntryVisable() {
        Button entryBtn;
        ArrayList<String> entryUINameList = tester.getEntryUINameList();
        if (entryUINameList == null || entryUINameList.size() <= 0) {
            DialogUtil.showConfirmDialog("错误提示", "未找到ServiceAccesser，大概率原因如下：\n\t(1)ServiceTester使用反射机制，根据包名查找路径下的类名并进行自动解析，使用Instant Run设置会导致apk打包的dex中没有class文件，解决办法关闭debug编译的Instant Run功能(百度)，或者以release方式编译！", null);
        }

        Iterator<String> iterator = entryUINameList.iterator();
        int idx = 0;
        while (iterator.hasNext() && idx < MAX_ENTRY_SIZE - 1) {
            String entryName = iterator.next();
            entryBtn = btnMap.get(idx++);
            if (entryBtn != null) {
                entryBtn.setText(entryName);
                entryBtn.setVisibility(View.VISIBLE);
            }
        }

        entryBtn = btnMap.get(idx);
        if (entryBtn != null) {
            entryBtn.setText(SystemUtil.getResString(R.string.test_tools));
            entryBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button)v;
        String entryUIName = (String) btn.getText();
        LogUtil.d(TAG, "Entry[" + entryUIName + "] been clicked");
        if (entryUIName.equals(SystemUtil.getResString(R.string.test_tools))) { // 工具测试独立于别的，不需要绑定
            Intent intent = new Intent(MyApplication.getContext(), TestToolsActivity.class);
            startActivity(intent);
        } else {
            bindEntryService(entryUIName);
        }
    }

    private void bindEntryService(String entryUIName) {
        TestEntry testEntry = tester.getTestEntryByEntryUIName(entryUIName);
        ServiceAccesser accesser = testEntry.getServiceAccesser();
        String action = accesser.getServcieAction();
        String packageName = accesser.getServicePackageName();
        String serviceClassName = accesser.getServiceClassName();

        LogUtil.d(TAG, "Bind Service.....");

        if (action == null || action.length() == 0
                || packageName == null || packageName.length() == 0
                || serviceClassName == null || serviceClassName.length() == 0) {
            /*
             * 错误原因程序员没有在对应的ServiceAccesser中返回正确的绑定属性，请参考ServiceAccessInterface描述进行修改代码
             */
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.accesser_error), null);
        } else {
            Intent intent = new Intent();
            intent.setAction(action);
            intent.setClassName(packageName, serviceClassName);
            if (myConnection == null) {
                myConnection = new MyConnection(accesser);
            }
            try {
                if (accesser.getConnectStatus()) {
                    LogUtil.d(TAG, "unbind service.");
                    this.getApplicationContext().unbindService(myConnection);
                }

                boolean bRet = this.getApplicationContext().bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
                if (bRet) {
                    MyApplication.getInstance().setCurrentTestEntry(testEntry);
                    Intent intentStartActivity = new Intent(this, SelectActivity.class);
                    startActivity(intentStartActivity);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.bind_service_failed), null);
            MyApplication.getInstance().setCurrentTestEntry(null);
        }
    }

    public class MyConnection implements ServiceConnection {
        private ServiceAccesser serviceAccesser;
        public MyConnection(ServiceAccesser accesser){
            this.serviceAccesser = accesser;
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected-----1-----" + iBinder.toString());
            if (serviceAccesser != null && serviceAccesser.getServiceConnection() != null) {
                serviceAccesser.getServiceConnection().onServiceConnected(componentName, iBinder);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "DestoryServiceTest----------");
           // myApplication.backToMainActivity();
            if (serviceAccesser != null && serviceAccesser.getServiceConnection() != null) {
                serviceAccesser.getServiceConnection().onServiceDisconnected(componentName);
            }
            MyApplication.getInstance().setCurrentTestEntry(null);
            MyApplication.getInstance().backToMainActivity();
        }
    }

    private void findViews() {
        Button btn;
        int idx = 0;
        btn = (Button) findViewById(R.id.btn_entry1);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry2);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry3);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry4);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry5);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry6);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry7);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry8);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry9);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
        btn = (Button) findViewById(R.id.btn_entry10);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);
        btnMap.put(idx++, btn);
    }
}
