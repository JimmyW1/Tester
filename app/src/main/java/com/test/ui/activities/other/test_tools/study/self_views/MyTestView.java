package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by CuncheW1 on 2017/10/16.
 */

public class MyTestView extends android.support.v7.widget.AppCompatTextView {
    int mViewWidth;
    int mTranslate;
    Paint mPaint;
    LinearGradient mLinearGradient;
    Matrix mMatrix;

    public MyTestView(Context context) {
        super(context);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.BLUE, 0xffffffff, Color.BLUE}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null) {
            mTranslate += mViewWidth/5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate -= mViewWidth;
            }
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);

            postInvalidateDelayed(100);
        }
    }
}
