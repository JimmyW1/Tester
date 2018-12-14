package com.test.ui.activities.other.test_tools.study;

import android.graphics.Color;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.study.self_views.TestOnMeasureOnLayViewGroup;


public class TestOnMeasureOnlayActivity extends AppCompatActivity {
    private TestOnMeasureOnLayViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewGroup = new TestOnMeasureOnLayViewGroup(TestOnMeasureOnlayActivity.this);
        /*
        TextView textView = new TextView(this);
        textView.setText("111111111");
        textView.setBackgroundColor(Color.BLUE);
        viewGroup.addView(textView);
        textView = new TextView(this);
        textView.setText("222222222");
        textView.setBackgroundColor(Color.YELLOW);
        viewGroup.addView(textView);
        */
 //       viewGroup.addView(viewMeasureGroup);
 //       viewGroup.setBackgroundColor(Color.RED);
 //       setContentView(viewGroup);
        View view = getLayoutInflater().inflate(R.layout.activity_test_on_measure_onlay, null);
        TestOnMeasureOnLayViewGroup measureGroup = (TestOnMeasureOnLayViewGroup) view.findViewById(R.id.measure_self);
        Button button = new Button(this);
        button.setText("1111");
        measureGroup.addView(button);
        setContentView(view);
    }
}
