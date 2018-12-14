package com.test.ui.activities.other.test_tools.study.demon.toolbar;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.test.ui.activities.R;
import com.test.util.LogUtil;

public class ToolBarDemonActivity extends AppCompatActivity {
    private final String TAG = "ToolBarDemonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar_demon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*
        toolbar.setNavigationIcon(R.mipmap.ab_android);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Tester");
        toolbar.setSubtitle("version:0.0.2");
        */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.toolbar_edit);
        LogUtil.d(TAG, "000000000000000000000000000000");
        MyActionBarMenu provider = (MyActionBarMenu) MenuItemCompat.getActionProvider(menuItem);
        try {
            provider.show();
        }catch (Exception e) {
            LogUtil.d(TAG, "Exception");
            e.printStackTrace();
        }
        LogUtil.d(TAG, "22222222222222222222222222222");
        return true;
//        return super.onCreateOptionsMenu(menu);
    }
}
