package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/9/27.
 */

public class TestOnMeasureOnLayViewGroup extends ViewGroup {
    private final String TAG = "TestOnMeasureOnLayViewGroup";

    public TestOnMeasureOnLayViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


//        int viewWidthMeasureSpec = widthMeasureSpec;
//        int viewHeightMeasureSpec = heightMeasureSpec;

        LogUtil.d(TAG, "widthMode=" + widthMode);
        LogUtil.d(TAG, "widthSize=" + widthSize);
        LogUtil.d(TAG, "heightMode=" + heightMode);
        LogUtil.d(TAG, "heightSize=" + heightSize);
        for(int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            LogUtil.d(TAG, "OnMeasure: childWidth=" + childWidth);
            LogUtil.d(TAG, "OnMeasure: childHeight=" + childHeight);
            int tmpHeight = childView.getLayoutParams().height;
            int tmpWidth =  childView.getLayoutParams().width;
            LogUtil.d(TAG, "OnMeasure LayoutParams: childWidth=" + tmpWidth);
            LogUtil.d(TAG, "OnMeasure LayoutParams: childHeight=" + tmpHeight);

            int viewHeightMeasureSpec;
            if (tmpHeight == WindowManager.LayoutParams.MATCH_PARENT) {
                tmpHeight = heightSize;
                viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tmpHeight, MeasureSpec.EXACTLY);
            } else if (tmpHeight > 0) {
                tmpHeight = tmpHeight > heightSize ? heightSize : tmpHeight;
                viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tmpHeight, MeasureSpec.EXACTLY);
            } else {
                measureChildren(widthMeasureSpec, heightMeasureSpec);
                tmpHeight = childView.getMeasuredHeight() > heightSize ? heightSize : childView.getMeasuredHeight();
                LogUtil.d(TAG, "=====height=" + tmpHeight);
                viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tmpHeight, MeasureSpec.EXACTLY);
                /*
                if (childView instanceof ViewGroup) {
                    ViewGroup tmpViewGroup = (ViewGroup) childView;
                    int tmpMeasureWidth = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
                    int tmpMeasureHeight = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
                    childView.measure(tmpMeasureWidth, tmpMeasureHeight);
                    tmpHeight = 0;
                    for (int c = 0; c < tmpViewGroup.getChildCount(); c++) {
                        LogUtil.d(TAG, "child height=" + tmpViewGroup.getChildAt(c).getMeasuredHeight());
                        tmpHeight += tmpViewGroup.getChildAt(c).getMeasuredHeight();
                    }
                    LogUtil.d(TAG, "Wrap_content, tmpHeight=" + tmpHeight);
                    tmpHeight = tmpHeight > heightSize ? heightSize : tmpHeight;
                    LogUtil.d(TAG, "Wrap_content, tmpHeight=" + tmpHeight);
                    viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tmpHeight, MeasureSpec.EXACTLY);
                } else {
                    viewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
                }
                */
            }

            int viewWidthMeasureSpec;
            if (tmpWidth == WindowManager.LayoutParams.MATCH_PARENT) {
                tmpWidth = widthSize;
                viewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(tmpWidth, MeasureSpec.AT_MOST);
            } else if (tmpWidth > 0) {
                tmpWidth = tmpWidth > widthSize ? widthSize : tmpWidth;
                viewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(tmpWidth, MeasureSpec.EXACTLY);
            } else {
                measureChildren(widthMeasureSpec, heightMeasureSpec);
                tmpWidth = childView.getMeasuredWidth() > widthSize ? widthSize : childView.getMeasuredWidth();
                LogUtil.d(TAG, "=====width=" + tmpWidth);
                viewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(tmpWidth, MeasureSpec.AT_MOST);
            }

            childView.measure(viewWidthMeasureSpec, viewHeightMeasureSpec);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            LogUtil.d(TAG, "OnMeasure: childWidth=" + childWidth);
            LogUtil.d(TAG, "OnMeasure: childHeight=" + childHeight);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        View viewChild;

        LogUtil.d(TAG, "l=" + l);
        LogUtil.d(TAG, "t=" + t);
        LogUtil.d(TAG, "r=" + r);
        LogUtil.d(TAG, "b=" + b);
        LogUtil.d(TAG, "childCount" + childCount);

        int left = l;
        int top = t;
        for(int i = 0; i < childCount; i++) {
            viewChild = this.getChildAt(i);
            int childWidth = viewChild.getMeasuredWidth();
            int childHeight = viewChild.getMeasuredHeight();
            LogUtil.d(TAG, "childWidth=" + childWidth);
            LogUtil.d(TAG, "childHeight=" + childHeight);
            viewChild.layout(left, top, left + childWidth, top + childHeight);
            top += childHeight;
        }
    }
}
