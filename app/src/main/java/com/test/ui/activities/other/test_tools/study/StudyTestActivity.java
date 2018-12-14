package com.test.ui.activities.other.test_tools.study;

import android.animation.ObjectAnimator;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.test.logic.service_tester.base_datas.ModuleEntry;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.LogUtil;

public class StudyTestActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "StudyTestActivity";
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private EditText editText;
    private EditText editText2;
    private EditText editText3;

    private float transX;
    private float transY;
    private float lastTransX;
    private float lastTransY;
    private int [] locationXY;

    private Scroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_test);
        transX = 0;
        transY = 0;
        lastTransX = lastTransY = 0;
        locationXY = new int[2];
        linearLayout = (LinearLayout) findViewById(R.id.layout_study_linear1);
        linearLayout2 = (LinearLayout) findViewById(R.id.layout_study_linear2);
        linearLayout3 = (LinearLayout) findViewById(R.id.layout_study_linear3);
        linearLayout4 = (LinearLayout) findViewById(R.id.layout_study_linear4);
        linearLayout5 = (LinearLayout) findViewById(R.id.layout_study_linear5);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_study_relative);
        button = (Button) findViewById(R.id.button);
        button.setText("button");
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setText("button2");
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setText("button3");
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setText("button4");
        button4.setOnClickListener(this);
        scroller = new Scroller(MyApplication.getContext());


        editText = (EditText) findViewById(R.id.editText);
        editText.setText("editText");
        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setText("editText2");
        editText3 = (EditText) findViewById(R.id.editText3);
        editText3.setText("editText3");
        showViewInfo("linearLayout1", linearLayout);
        showViewInfo("relativeLayout", relativeLayout);
        showViewInfo("button", button);
        showViewInfo("button2", button2);
        showViewInfo("button3", button3);
    }

    private void showViewInfo(String title, View view) {
        LogUtil.d(TAG, "-----------------" + title + "--------------");
        LogUtil.d(TAG, "View left=" + view.getLeft());
        LogUtil.d(TAG, "View top=" + view.getTop());
        LogUtil.d(TAG, "View right=" + view.getRight());
        LogUtil.d(TAG, "View bottom=" + view.getBottom());
        LogUtil.d(TAG, "View width=" + (view.getRight() - view.getLeft()));
        LogUtil.d(TAG, "View height=" + (view.getBottom() - view.getTop()));
        LogUtil.d(TAG, "View x=" + view.getX());
        LogUtil.d(TAG, "View y=" + view.getY());
        LogUtil.d(TAG, "View z=" + view.getZ());
        LogUtil.d(TAG, "View mScrollX=" + view.getScrollX());
        LogUtil.d(TAG, "View mScrollY=" + view.getScrollY());
        LogUtil.d(TAG, "View translationX=" + view.getTranslationX());
        LogUtil.d(TAG, "View translationY=" + view.getTranslationY());
    }

    @Override
    public void onClick(View v) {
        showViewInfo("relativeLayout", relativeLayout);
        showViewInfo("linearLayout1", linearLayout);
        showViewInfo("linearLayout2", linearLayout2);
        showViewInfo("linearLayout3", linearLayout3);
        showViewInfo("linearLayout4", linearLayout4);
        showViewInfo("linearLayout5", linearLayout5);
        showViewInfo("button", button);
        showViewInfo("button2", button2);
        showViewInfo("button3", button3);
        showViewInfo("editText", editText);
        showViewInfo("editText2", editText2);
        showViewInfo("editText3", editText3);

        if (v.getId() == R.id.button3) {
            linearLayout4.scrollTo(20, 20);
        }

        if (v.getId() == R.id.button4) {
//            linearLayout4.scrollBy(20, 20);
            ObjectAnimator.ofFloat(button3, "translationX", transX, transX+10).setDuration(2000).start();
            ObjectAnimator.ofFloat(button3, "translationY", transY, transY+10).setDuration(2000).start();
            transX+=10;
            transY+=10;
        }

        showViewInfo("linearLayout5", linearLayout5);
        showViewInfo("button3", button3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        VelocityTracker velocityTracker = VelocityTracker.obtain();
//        velocityTracker.addMovement(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            button3.getLocationOnScreen(locationXY);
            lastTransX = button3.getTranslationX();
            lastTransY = button3.getTranslationY();
            LogUtil.d(TAG, "touchX=" + locationXY[0]);
            LogUtil.d(TAG, "touchY=" + locationXY[1]);
            LogUtil.d(TAG, "button3X=" + button3.getX());
            LogUtil.d(TAG, "button3Y=" + button3.getY());
            LogUtil.d(TAG, "button3TX=" + button3.getTranslationX());
            LogUtil.d(TAG, "button3TY=" + button3.getTranslationY());
            locationXY[0] = (int) (locationXY[0] - button3.getTranslationX());
            locationXY[1] = (int) (locationXY[1] - button3.getTranslationY());
        }
        transX = event.getRawX() - locationXY[0];
        transY = event.getRawY() - locationXY[1];
//        transX = locationXY[0] - event.getRawX();
//        transY = locationXY[1] - event.getRawY();
//        LogUtil.d(TAG, "locationXY=[" + locationXY[0] + "," + locationXY[1] +"]");
//        LogUtil.d(TAG, "touchXY=[" + event.getRawX() + "," + event.getRawY() +"]");
//        LogUtil.d(TAG, "transXY=[" + transX + "," + transY +"]");


        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            velocityTracker.computeCurrentVelocity(1000);
//            LogUtil.d(TAG, "xVelocity=" + velocityTracker.getXVelocity());
//            LogUtil.d(TAG, "yVelocity=" + velocityTracker.getYVelocity());

        }


        ObjectAnimator.ofFloat(button3, "translationX", lastTransX, transX).setDuration(100).start();
        ObjectAnimator.ofFloat(button3, "translationY", lastTransY, transY).setDuration(100).start();
        lastTransX = transX;
        lastTransY = transY;

        if (event.getAction() == MotionEvent.ACTION_UP) {
//            velocityTracker.clear();
//            velocityTracker.recycle();
            LogUtil.d(TAG, "button3TX=" + button3.getTranslationX());
            LogUtil.d(TAG, "button3TY=" + button3.getTranslationY());
            LogUtil.d(TAG, "lastTransX=" + lastTransX);
            LogUtil.d(TAG, "lastTransY=" + lastTransY);
        }

//        return super.onTouchEvent(event);
        return true;
    }
}
