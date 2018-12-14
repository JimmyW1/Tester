package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/9/27.
 */

public class SelfViewPager extends ViewGroup {
    private final String TAG = "SelfViewPage";
    private Scroller mScroller;
    private int mTouchSlop; // 认为是在滑动的最小距离,可以从View配置中读取
    private int maxChildIdx; // 子控件总数
    private int currentChildIdx; // 当前控件所在索引
    private boolean isFirst;

    public void setLastTransX(float lastTransX) {
        this.lastTransX = lastTransX;
    }

    private float lastTransX;
    private float lastTransY;

    public SelfViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        currentChildIdx = 0;
        LogUtil.d(TAG, "mTouchSlop=" + mTouchSlop);
        isFirst = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.measureChildren(widthMeasureSpec, heightMeasureSpec);
        /*
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childHeight = childView.getMeasuredHeight();

        }
        */
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewPagerWidth = r - l;
        LogUtil.d(TAG, "viewPager height=" + viewPagerWidth);
        int leftPosition = l;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(leftPosition, t, leftPosition + childView.getMeasuredWidth(), t + childView.getMeasuredHeight());
            leftPosition += viewPagerWidth;
        }
        maxChildIdx = getChildCount() - 1;
        maxChildIdx = maxChildIdx >= 0 ? maxChildIdx : 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            lastTransX = ev.getRawX();
            LogUtil.d(TAG, "firstX=" + lastTransX);
        } else if(ev.getAction() == MotionEvent.ACTION_MOVE) {
            int moveWidth = (int) Math.abs(ev.getRawX() - lastTransX);
            if (moveWidth > mTouchSlop) {
                isFirst = true;
                scrollBy((int) (lastTransX - ev.getRawX()), 0);
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "==========================" + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastTransX = event.getRawX();
            lastTransY = event.getRawY();
            LogUtil.d(TAG, "firstX=" + lastTransX);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int x = (int) (event.getRawX() - lastTransX);
            LogUtil.d(TAG, "x=" + x);
            LogUtil.d(TAG, "mScrollX=" + getScrollX());
            lastTransX = event.getRawX();
            scrollBy(-x, 0);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            LogUtil.d(TAG, "currentMeasureedWidth=" + getMeasuredWidth());
            LogUtil.d(TAG, "currentChildIdx=" + currentChildIdx);
            LogUtil.d(TAG, "if=" + (getScrollX() - currentChildIdx*getMeasuredWidth()));
            LogUtil.d(TAG, "childTotal=" + maxChildIdx);
            LogUtil.d(TAG, "scrollX=" + getScrollX());

            currentChildIdx = (getScrollX() + getWidth() / 2) / getWidth();
            if (currentChildIdx > getChildCount() - 1) {
                currentChildIdx = getChildCount() - 1;
            } else if (currentChildIdx < 0) {
                currentChildIdx = 0;
            }


            LogUtil.d(TAG, "currentChildIdx=" + currentChildIdx);
            LogUtil.d(TAG, "childLeft=" + getChildAt(currentChildIdx).getLeft());
            mScroller.startScroll(getScrollX(), 0, getChildAt(currentChildIdx).getLeft() - getScrollX(), 0);
            invalidate();
        }

        return false;
        //return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
