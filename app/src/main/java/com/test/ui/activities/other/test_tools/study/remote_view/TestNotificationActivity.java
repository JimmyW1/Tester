package com.test.ui.activities.other.test_tools.study.remote_view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.study.TestMyCircleViewActivity;

public class TestNotificationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);
        Button button = (Button) findViewById(R.id.notification_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.expandable_normal);
        builder.setTicker("nihao");
        builder.setContentText("nihao Content");
        builder.setContentTitle("nihao Titile");
        Notification notification = builder.build();
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent intent = new Intent(this, TestMyCircleViewActivity.class);
        Intent[] intents = new Intent[1];
        intents[0] = intent;
        notification.contentIntent = PendingIntent.getActivities(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
