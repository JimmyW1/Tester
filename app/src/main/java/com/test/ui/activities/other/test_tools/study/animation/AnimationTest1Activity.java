package com.test.ui.activities.other.test_tools.study.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;

public class AnimationTest1Activity extends BaseActivity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test1);
        button = (Button) findViewById(R.id.button10);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_test);
//        Animation animation =  new Rotate3dAnimation(0f, 90f, v.getX(), v.getY(), 100f, false);
//        button.startAnimation(animation);
        /*
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(button, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        */
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(button, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(button, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(button, "rotation", 0, -90),
                ObjectAnimator.ofFloat(button, "translationX", 0, 90),
                ObjectAnimator.ofFloat(button, "translationY", 0, 90),
                ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.5f),
                ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.5f),
                ObjectAnimator.ofFloat(button, "alpha", 1, 0.25f, 1)
        );
        set.setDuration(5000).start();
    }
}
