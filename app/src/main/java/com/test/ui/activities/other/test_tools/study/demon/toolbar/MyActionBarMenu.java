package com.test.ui.activities.other.test_tools.study.demon.toolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.test.ui.activities.R;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/10/26.
 */

public class MyActionBarMenu extends android.support.v4.view.ActionProvider {
    private final String TAG = "MyActionBarMenu";
    private Context mContext;
    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public MyActionBarMenu(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View onCreateActionView() {
        LogUtil.d(TAG, "===========================================");
        LogUtil.d(TAG, "===========================================");
        LogUtil.d(TAG, "===========================================");
        View view  = LayoutInflater.from(mContext).inflate(R.layout.menu_item2, null);
        return view;
    }

    public void show() {
        LogUtil.d(TAG, "99999999999999999999999999999999999");
    }
}
