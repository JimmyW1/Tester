package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ListView;

import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/9/29.
 */

public class MyListView extends ListView {
    private final String TAG = "MyListView";
    private float downX;
    private float downY;
    private MySelfViewPager mySelfViewPager;
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMySelfViewPager(MySelfViewPager mySelfViewPager) {
        this.mySelfViewPager = mySelfViewPager;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
         boolean isDisallow = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
                LogUtil.d(TAG, "========================");
                isDisallow = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = Math.abs(ev.getRawX() - downX);
                float diffY = Math.abs(ev.getRawY() - downY);
                LogUtil.d(TAG, "diffX=" + diffX);
                LogUtil.d(TAG, "diffY=" + diffY);
                if (diffX > diffY) {
                    isDisallow = false;
                } else {
                    isDisallow = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isDisallow = false;
                break;
        }
        if (mySelfViewPager != null) {
            mySelfViewPager.requestDisallowInterceptTouchEvent(isDisallow);
        }
        return super.onTouchEvent(ev);
    }
}
