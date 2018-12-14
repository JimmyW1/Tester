package com.test.ui.activities.other.test_tools.study.self_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.test.ui.activities.R;
import com.test.util.LogUtil;

/**
 * Created by CuncheW1 on 2017/10/9.
 */

public class MyCircleView extends View {
    private final String TAG = "MyCircleView";
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyCircleView(Context context) {
        super(context);
        init();
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleView);
        mColor = typedArray.getColor(R.styleable.MyCircleView_circle_color, Color.RED);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        LogUtil.d(TAG, "Padding=[" + paddingLeft + "," + paddingRight + "," + paddingTop + "," + paddingBottom + "]");

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width, height) / 2;
        LogUtil.d(TAG, "width=" + width + " height=" + height + " radius=" + radius);

        canvas.drawCircle(paddingLeft + width/2, paddingTop + height/2, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (getLayoutParams().width == WindowManager.LayoutParams.WRAP_CONTENT) {
            widthSize = 100;
            widthMode = MeasureSpec.EXACTLY;
        }

        if (getLayoutParams().height == WindowManager.LayoutParams.WRAP_CONTENT) {
            heightSize = 100;
            heightMode = MeasureSpec.EXACTLY;
        }


        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }
}
