package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by CuncheW1 on 2017/9/29.
 */

public class MySelfViewPager extends SelfViewPager {
    public MySelfViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setLastTransX(ev.getRawX());
                return false;
            case MotionEvent.ACTION_MOVE:
                return true;
        }

        return false;
    }
}
