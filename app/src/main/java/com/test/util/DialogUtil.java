package com.test.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.EditText;

import com.test.ui.activities.MyApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/4/20.
 */

public class DialogUtil {
    private static final String TAG = "DialogUtil";

    public static synchronized void showConfirmDialog(String title, String message, final ConfirmDialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setConfirmFlag(false);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setConfirmFlag(true);
            }
        });
        showDialog(builder);
   }

    public static synchronized void showConfirmItemsDialog(String title, String[] items, final ConfirmDialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
        builder.setTitle(title);
        StringBuffer buffer = new StringBuffer();
        for (String item: items ) {
            buffer.append(item + "\n");
        }
        builder.setMessage(buffer.toString());
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setConfirmFlag(false);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setConfirmFlag(true);
            }
        });
        showDialog(builder);
    }

    public static synchronized void showSelectItemDialog(String title, String[] items, final SelectDialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setSelectIndex(which);
            }
        });
        showDialog(builder);
    }

    public static synchronized void showInputDialog(String title, final InputDialogListener listener) {

        final EditText mEditText = new EditText(MyApplication.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
        builder.setTitle(title);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(mEditText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                //数据获取
                if (listener != null)
                    listener.setInputString(mEditText.getText().toString(), true);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null)
                    listener.setInputString("", false);
            }
        });
        showDialog(builder);
    }

    public static synchronized void showAutoDismissDialog(final String title, final String message, final int timeSecond) {
        final Activity mainActivity = MyApplication.getInstance().getMainActivity();
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getContext());
                builder.setTitle(title);
                builder.setMessage(message);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();
                try {
                    Thread.sleep(timeSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
   }

    public static void showTlvDialog(String title, byte[] tagTlvValue) {
        if (tagTlvValue != null) {
            Map<String, String> dataMap = TlvUtil.tlvToMap(tagTlvValue);
            Iterator iterator = dataMap.keySet().iterator();
            ArrayList<String> arrayList = new ArrayList<>();
            String[] items = new String[dataMap.size()];

            int i = 0;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                items[i++] = "Tag[" + key + "]=" + dataMap.get(key);
            }

            DialogUtil.showConfirmItemsDialog(title, items, null);
        }
    }

    private static void showDialog(final AlertDialog.Builder builder) {
        if (builder == null)
            return;

        final Activity currentActivity = MyApplication.getInstance().getTopActivity();
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();
            }
        });
    }

    public interface InputDialogListener {
        public void setInputString(String inputStr, boolean isConfirm);
    }

    public interface ConfirmDialogListener {
        public void setConfirmFlag(boolean isConfirmSuccess);
    }

    public interface SelectDialogListener {
        public void setSelectIndex(int index);
    }
}

