package com.test.ui.activities.other.test_tools.j300_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.test.ui.activities.R;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.PrinterUtil;
import com.test.util.SystemUtil;

/**
 * Created by CuncheW1 on 2017/8/15.
 */

public class J300NativeApis {

    public void startPinInput() {
        final String pinBlock = startJ300PinInput();
        DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.test_result), pinBlock, new DialogUtil.ConfirmDialogListener() {
            @Override
            public void setConfirmFlag(boolean isConfirmSuccess) {
                PrinterUtil.printString("PinBlock:", pinBlock);
            }
        });
    }

    public void startEsign() {
        startJ300Esign();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap img = BitmapFactory.decodeFile("/sdcard/esign.bmp", options);
        PrinterUtil.printBitmap(img);
    }

    public void startScanner() {
        final String result = startJ300Scanner();
        DialogUtil.showConfirmDialog(SystemUtil.getResString(R.string.test_result), result, new DialogUtil.ConfirmDialogListener() {
            @Override
            public void setConfirmFlag(boolean isConfirmSuccess) {
                PrinterUtil.printString("Scanner result:", result);
            }
        });
    }

    public void  jShow() {
        LogUtil.d("Nihao", "==========================");
        LogUtil.d("Nihao", "==========================");
        LogUtil.d("Nihao", "==========================");
        LogUtil.d("Nihao", "==========================");
    }

    public void startFeiKa() {
        startCtls();
    }

    private native String startJ300PinInput();
    private native void startJ300Esign();
    private native String startJ300Scanner();
    private native void startCtls();

    static {
        System.loadLibrary("native-lib");
    }
}
