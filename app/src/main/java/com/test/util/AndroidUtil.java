package com.test.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.test.logic.service_accesser.service_accesser.ServiceAccesser;
import com.test.ui.activities.MainActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.ui.activities.SelectActivity;

public class AndroidUtil {
    private static final String TAG = "AndroidUtil";

    public static void bindEntryService(ServiceAccesser accesser, ServiceConnection connection) {
        LogUtil.d(TAG, "Bind Service.....");
        if (accesser == null || connection == null) {
            return;
        }

        String action = accesser.getServcieAction();
        String packageName = accesser.getServicePackageName();
        String serviceClassName = accesser.getServiceClassName();

        if (action == null || action.length() == 0
                || packageName == null || packageName.length() == 0
                || serviceClassName == null || serviceClassName.length() == 0) {
            /*
             * 错误原因程序员没有在对应的ServiceAccesser中返回正确的绑定属性，请参考ServiceAccessInterface描述进行修改代码
             */
            DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.error_tip), SystemUtil.getResString(R.string.accesser_error), null);
        } else {
            Intent intent = new Intent();
            intent.setAction(action);
            intent.setClassName(packageName, serviceClassName);
            try {
                if (accesser.getConnectStatus()) {
                    LogUtil.d(TAG, "unbind service.");
                    MyApplication.getContext().unbindService(connection);
                }

                boolean bRet = MyApplication.getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
