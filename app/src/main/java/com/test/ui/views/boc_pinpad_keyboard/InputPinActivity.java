package com.test.ui.views.boc_pinpad_keyboard;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

import com.boc.aidl.pinpad.AidlPinpadListener;

import com.socsi.utils.StringUtil;
import com.test.logic.testmodules.boc_modules.PinpadTestCase;
import com.test.ui.activities.R;
import com.test.util.ToastUtil;

import java.io.FileOutputStream;

public class InputPinActivity extends AppCompatActivity {
    private final String TAG = "ModulePinpad";
    private N900PinKeyBoard pkb;
    private TextView txtPassword;
    private TextView tvAmount;
    private TextView tvPan;
    private TextView input_tip_msg;
    private boolean first;
    private byte[] pinpadCoordinate;
    private static InputPinActivity instance;
    private CallbackStartPininput callbackStartPininput;
    private CallbackSetLayout callbackSetLayout;
    AidlPinpadListener.Stub pinpadListener;
    View rootView;

    public static final int TYPE_TEXT_PASSWD = 1000;
    public static final int TYPE_TEXT_AMOUNT = 1001;
    public static final int TYPE_TEXT_PAN = 1002;
    public static final int TYPE_TEXT_TIPMSG = 1003;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        first = false;
        instance = this;
        setContentView(R.layout.input_pin_fragment);

        callbackSetLayout = PinpadTestCase.getInstance().getCurrentSetLayoutCallback();
        callbackStartPininput = PinpadTestCase.getInstance().getCurrentStartPininputCallback();

        pinpadListener = new AidlPinpadListener.Stub() {
            public void onCancel()
                    throws RemoteException {
                Log.d(TAG, "onCancel been called.");
                finish();
            }

            public void onError(int errorCode, String description)
                    throws RemoteException {
                Log.d(TAG, "onError been called. errorCode=" + errorCode);
                Log.d(TAG, "description=" + description);
                finish();
            }

            public void onKeyDown(final int keyLength, int keyValue)
                    throws RemoteException {
                Log.d(TAG, "=================+"  + keyLength +"=====================================");
                String passwdStr = "************".substring(0, keyLength);
                setTextValue(InputPinActivity.TYPE_TEXT_PASSWD, passwdStr);
            }

            public void onPinRslt(byte[] paramAnonymousArrayOfByte)
                    throws RemoteException {
                Log.d(TAG, "PinResult:" + StringUtil.byte2HexStr(paramAnonymousArrayOfByte));
                takeScreenShot();
                // 仅用于测试，判断个人密码是否为"123456789012"
                if (StringUtil.byte2HexStr(paramAnonymousArrayOfByte).equals("557288C570C1B6C7")) {
                    ToastUtil.showLong("success!");
                }else
                {
                    ToastUtil.showLong("fail!");
                }
                finish();
            }
        };
        findView();
        rootView =  ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
    }
    // 截取当前屏幕
    public void takeScreenShot() {
        String fname = "/sdcard/screenshot.png";
        View view = rootView;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if(bitmap != null) {
            System.out.println("bitmap got!");
            try{
                FileOutputStream out = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
                System.out.println("file " + fname + "output done.");
            }catch(Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("bitmap is NULL!");
        }
    }

    private void findView() {
        Log.d(TAG, "findView been called.");
        this.tvAmount = ((TextView)findViewById(R.id.input_pin_amount));
        this.tvPan = ((TextView)findViewById(R.id.tv_input_pin_pan));
        this.input_tip_msg = ((TextView)findViewById(R.id.input_tip_msg));
        this.txtPassword = ((TextView)findViewById(R.id.txt_password));
        this.pkb = ((N900PinKeyBoard)findViewById(R.id.n990_pin_keyborad));
        this.pkb.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

            public boolean onPreDraw()
            {
                Log.i(TAG, "onPreDraw been called.");
                if (!first)
                {
                    first = true;
                    try {
                        Log.i(TAG, "getRandomKeyBoradNumber2.");
                        InputPinActivity.this.pinpadCoordinate = InputPinActivity.this.pkb.getCoordinate();
                        if (callbackSetLayout != null) {
                            byte[] keyboardNum = callbackSetLayout.setPinpadLayout(InputPinActivity.this.pinpadCoordinate);
                            setRandomNumber(keyboardNum);
                        }
                        if (callbackStartPininput != null) {
                            callbackStartPininput.startPininput(1, "1232", new byte[]{}, InputPinActivity.this.pinpadListener);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "FindView() exception happened.");
                        e.printStackTrace();
                    }
                }
                return first;
            }
        });
    }

    public static synchronized  InputPinActivity getInstance() {
        return instance;
    }

    public byte[] getPinpadCoordinate() {
        if (first) {
            Log.i(TAG, "Got coordinate success.");
            return pinpadCoordinate;
        } else {
            Log.i(TAG, "Got coordinate failed.");
            return null;
        }
    }

    public void setRandomNumber(byte[] randomNumber)
    {
        try
        {
            byte[] arrayOfByte = new byte[10];
            arrayOfByte[0] = randomNumber[0];
            arrayOfByte[1] = randomNumber[1];
            arrayOfByte[2] = randomNumber[2];
            arrayOfByte[3] = randomNumber[4];
            arrayOfByte[4] = randomNumber[5];
            arrayOfByte[5] = randomNumber[6];
            arrayOfByte[6] = randomNumber[8];
            arrayOfByte[7] = randomNumber[9];
            arrayOfByte[8] = randomNumber[10];
            arrayOfByte[9] = randomNumber[12];
            int[] radomNumberArray = new int[arrayOfByte.length];
            int i = 0;
            while (i < arrayOfByte.length)
            {
                arrayOfByte[i] -= 48;
                radomNumberArray[i] = arrayOfByte[i];
                Log.i(TAG, "Rodom[" + i + "]=" + radomNumberArray[i]);
                i += 1;
            }
            this.pkb.setRandomNumber(radomNumberArray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setTextValue(final int type, final String text) {
        InputPinActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case TYPE_TEXT_AMOUNT:
                        //tvAmount.setText(text);
                        break;
                    case TYPE_TEXT_PAN:
                        tvPan.setText(text);
                        break;
                    case TYPE_TEXT_PASSWD:
                        txtPassword.setText(text);
                        break;
                    case TYPE_TEXT_TIPMSG:
                        input_tip_msg.setText(text);
                        break;
                    default:
                        Log.d(TAG, "Unkown type=" + type);
                }
            }
        });
    }

}


