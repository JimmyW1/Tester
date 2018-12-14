package com.test.ui.views.boc_pinpad_keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class N900PinKeyBoard
        extends View
{
    private final String TAG = "N900PinKeyBoard";
    private int contentsize;
    private int[] coordinateInt;
    private DisplayMetrics dm;
    private float height;
    private int[] nums;
    private final Point p = new Point(540, 880);
    private Paint paint;
    private Path path;
    private float width;

    public N900PinKeyBoard(Context paramContext)
    {
        super(paramContext);
        getScreenResolution(paramContext);
        init();
    }

    public N900PinKeyBoard(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        getScreenResolution(paramContext);
        init();
    }

    private void drawStringCenter(Canvas canvas, float paramFloat1, float paramFloat2, String paramString)
    {
        this.paint.setStyle(Style.FILL);
        this.paint.setStrokeWidth(4.0F);
        FontMetrics localFontMetrics = this.paint.getFontMetrics();
        int i = (int)this.paint.measureText(paramString);
        int j = (int)Math.ceil(localFontMetrics.descent - localFontMetrics.ascent);
        int k = (int)localFontMetrics.descent;
        canvas.drawText(paramString, paramFloat1 - i / 2, paramFloat2 - k + j / 2, this.paint);
    }

    private void drawdelete(Canvas canvas, float paramFloat1, float paramFloat2, float paramFloat3)
    {
        paramFloat1 -= paramFloat3;
        paramFloat2 -= paramFloat3 / 2.0F;
        this.path.reset();
        this.path.moveTo(paramFloat1, paramFloat3 / 2.0F + paramFloat2);
        this.path.lineTo(paramFloat3 / 2.0F + paramFloat1, paramFloat2);
        this.path.lineTo(2.0F * paramFloat3 + paramFloat1, paramFloat2);
        this.path.lineTo(2.0F * paramFloat3 + paramFloat1, paramFloat2 + paramFloat3);
        this.path.lineTo(paramFloat3 / 2.0F + paramFloat1, paramFloat2 + paramFloat3);
        this.path.close();
        this.path.moveTo(2.0F * paramFloat3 / 8.0F * 3.0F + paramFloat1 + 5.0F, 8 + paramFloat2);
        this.path.lineTo(2.0F * paramFloat3 / 8.0F * 7.0F + paramFloat1 - 5.0F, paramFloat2 + paramFloat3 - 8);
        this.path.moveTo(2.0F * paramFloat3 / 8.0F * 7.0F + paramFloat1 - 5.0F, 8 + paramFloat2);
        this.path.lineTo(2.0F * paramFloat3 / 8.0F * 3.0F + paramFloat1 + 5.0F, paramFloat2 + paramFloat3 - 8);
        this.paint.setStyle(Style.STROKE);
        canvas.drawPath(this.path, this.paint);
    }

    private void getScreenResolution(Context paramContext)
    {
        WindowManager windowManager = (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
        this.dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(this.dm);
        Log.i("N900PinKeyBoard", "hright=" + this.dm.heightPixels + ";width" + this.dm.widthPixels);
    }

    private void init()
    {
        this.path = new Path();
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.nums = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
    }

    public byte[] getCoordinate()
    {
        int[] localObject = new int[2];
        getLocationOnScreen((int[])localObject);
        int i = localObject[0];
        int j = (int)(localObject[0] + this.width / 4.0F);
        int k = (int)(localObject[0] + this.width / 2.0F);
        int m = (int)(localObject[0] + this.width / 4.0F * 3.0F);
        int n = (int)(localObject[0] + this.width);
        int i1 = localObject[1];
        int i2 = (int)(localObject[1] + this.height / 4.0F);
        int i3 = (int)(localObject[1] + this.height / 2.0F);
        int i4 = (int)(localObject[1] + this.height / 4.0F * 3.0F);
        int i5 = (int)(localObject[1] + this.height);
        if (localObject[1] != 0) {
            this.coordinateInt = new int[] { i, i1, j, i2, j, i1, k, i2, k, i1, m, i2, i, i4, j, i5, i, i2, j, i3, j, i2, k, i3, k, i2, m, i3, m, i1, n, i3, i, i3, j, i4, j, i3, k, i4, k, i3, m, i4, i, i1, i, i1, j, i4, m, i5, n, i5, n, i5, m, i3, n, i5 };
        }

        byte[] coordinate = new byte[this.coordinateInt.length * 2];
        i = 0;
        j = 0;
        while (i < this.coordinateInt.length)
        {
            coordinate[j] = ((byte)(this.coordinateInt[i] >> 8 & 0xFF));
            j += 1;
            coordinate[j] = ((byte)(this.coordinateInt[i] & 0xFF));
            i += 1;
            j += 1;
        }
        return coordinate;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffffffff - 657926);
        this.paint.setAlpha(255);
        this.paint.setColor(0xffffffff - 897970);
        canvas.drawRect(0.0F, 1.0F + this.height / 4.0F * 3.0F, this.width / 4.0F - 1.0F, this.height, this.paint);
        this.paint.setColor(0xffffffff - 794031);
        canvas.drawRect(this.width / 4.0F * 3.0F + 1.0F, 0.0F, this.width, this.height / 2.0F, this.paint);
        this.paint.setColor(0xffffffff - 9383610);
        canvas.drawRect(this.width / 4.0F * 3.0F + 1.0F, this.height / 2.0F, this.width, this.height, this.paint);
        this.paint.setColor(0xffffffff - 1973790);
        this.paint.setStrokeWidth(1.0F);
        canvas.drawLine(0.0F, this.height / 4.0F, this.width / 4.0F * 3.0F, this.height / 4.0F, this.paint);
        canvas.drawLine(0.0F, this.height / 2.0F, this.width / 4.0F * 3.0F, this.height / 2.0F, this.paint);
        canvas.drawLine(0.0F, this.height / 4.0F * 3.0F, this.width / 4.0F * 3.0F, this.height / 4.0F * 3.0F, this.paint);
        canvas.drawLine(this.width / 4.0F, 0.0F, this.width / 4.0F, this.height, this.paint);
        canvas.drawLine(this.width / 2.0F, 0.0F, this.width / 2.0F, this.height / 4.0F * 3.0F, this.paint);
        canvas.drawLine(this.width / 4.0F * 3.0F, 0.0F, this.width / 4.0F * 3.0F, this.height, this.paint);
        this.paint.setColor(0xffffffff);
        this.paint.setTextSize(this.contentsize);
        drawStringCenter(canvas, this.width / 8.0F, this.height / 8.0F * 7.0F, "取 消");
        drawStringCenter(canvas, this.width / 8.0F * 7.0F, this.height / 4.0F * 3.0F, "确 认");
        drawdelete(canvas, this.width / 8.0F * 7.0F, this.height / 4.0F, this.height / 12.0F);
        this.paint.setColor(Color.BLACK);
        this.paint.setTextSize(this.contentsize + 10);
        drawStringCenter(canvas, this.width / 8.0F, this.height / 8.0F, this.nums[0] + "");
        drawStringCenter(canvas, this.width / 8.0F * 3.0F, this.height / 8.0F, this.nums[1] + "");
        drawStringCenter(canvas, this.width / 8.0F * 5.0F, this.height / 8.0F, this.nums[2] + "");
        drawStringCenter(canvas, this.width / 8.0F, this.height / 8.0F * 3.0F, this.nums[3] + "");
        drawStringCenter(canvas, this.width / 8.0F * 3.0F, this.height / 8.0F * 3.0F, this.nums[4] + "");
        drawStringCenter(canvas, this.width / 8.0F * 5.0F, this.height / 8.0F * 3.0F, this.nums[5] + "");
        drawStringCenter(canvas, this.width / 8.0F, this.height / 8.0F * 5.0F, this.nums[6] + "");
        drawStringCenter(canvas, this.width / 8.0F * 3.0F, this.height / 8.0F * 5.0F, this.nums[7] + "");
        drawStringCenter(canvas, this.width / 8.0F * 5.0F, this.height / 8.0F * 5.0F, this.nums[8] + "");
        drawStringCenter(canvas, this.width / 2.0F, this.height / 8.0F * 7.0F, this.nums[9] + "");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure widthMeasureSpec=" + widthMeasureSpec + " heightMeasureSpec=" + heightMeasureSpec);
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = (this.width / 5.0F * 4.0F);
        Log.d(TAG, "onMeasure width=" + width + " height=" + height);

        widthMeasureSpec = 15;
        for (;;)
        {
            if (widthMeasureSpec < 100)
            {
                this.paint.setTextSize(widthMeasureSpec);
                FontMetrics localFontMetrics = this.paint.getFontMetrics();
                float f1 = localFontMetrics.descent;
                float f2 = localFontMetrics.ascent;
                float f3 = this.paint.measureText("确认");
                if ((f1 - f2 > this.height / 8.0F) || (f3 > this.width / 8.0F)) {
                    this.contentsize = widthMeasureSpec;
                    setMeasuredDimension((int)this.width, (int)this.height);
                    Log.d(TAG, "contentsize=" + contentsize);
                    break;
                }
            }
            else
            {
                Log.d(TAG, "widthMeasureSpec > 100 ,set contentsize to default 46");
                this.contentsize = 46;
                setMeasuredDimension((int)this.width, (int)this.height);
                break;
            }
            widthMeasureSpec += 1;
        }
    }

    public void setRandomNumber(int[] paramArrayOfInt)
    {
        this.nums = paramArrayOfInt;
        invalidate();
    }
}

