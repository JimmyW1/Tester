package com.test.ui.activities.other.pinpad;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

import com.boc.aidl.pinpad.AidlPinpadListener;
import com.test.ui.activities.R;
import com.test.ui.views.keyboard_view.N900PinKeyBoard;
import com.test.ui.activities.BaseActivity;
import com.test.logic.testmodules.boc_develop_modules.ModulePinpad;
import com.test.util.LogUtil;
import com.test.util.StringUtil;

public class InputPinActivity extends BaseActivity {
    private final String TAG = "ModulePinpad";
    private ModulePinpad modulePinpad;
    private N900PinKeyBoard pkb;
    private TextView txtPassword;
    private TextView tvAmount;
    private TextView tvPan;
    private TextView input_tip_msg;
    private boolean first;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        first = false;
        setContentView(R.layout.input_pin_fragment);
//        this.modulePinpad = (ModulePinpad) TestModuleManager.getInstance().getModule(ModulePinpad.ModuleID);
        findView();
    }

    private void findView() {
        LogUtil.d(TAG, "findView been called.");
        this.tvAmount = ((TextView)findViewById(R.id.input_pin_amount));
        this.tvPan = ((TextView)findViewById(R.id.tv_input_pin_pan));
        this.input_tip_msg = ((TextView)findViewById(R.id.input_tip_msg));
        this.txtPassword = ((TextView)findViewById(R.id.txt_password));
        this.pkb = ((N900PinKeyBoard)findViewById(R.id.n990_pin_keyborad));
        this.pkb.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

            public boolean onPreDraw()
            {
                LogUtil.i(TAG, "onPreDraw been called.");
                if (!first)
                {
                    first = true;
                    try {
                        LogUtil.i(TAG, "getRandomKeyBoradNumber2.");
                        InputPinActivity.this.getRandomKeyBoardNumber2();
                        InputPinActivity.this.startPinInput();
                    } catch (Exception e) {
                        LogUtil.e(TAG, "FindView() exception happened.");
                        e.printStackTrace();
                    }
                }
                return first;
            }
        });
    }

    private void getRandomKeyBoardNumber2()
    {
        try
        {
            byte[] arrayOfByte = this.pkb.getCoordinate();
            byte[] localObject = modulePinpad.API09setPinpadLayout(arrayOfByte);
            arrayOfByte = new byte[10];
            arrayOfByte[0] = localObject[0];
            arrayOfByte[1] = localObject[1];
            arrayOfByte[2] = localObject[2];
            arrayOfByte[3] = localObject[4];
            arrayOfByte[4] = localObject[5];
            arrayOfByte[5] = localObject[6];
            arrayOfByte[6] = localObject[8];
            arrayOfByte[7] = localObject[9];
            arrayOfByte[8] = localObject[10];
            arrayOfByte[9] = localObject[12];
            int[] radomNumberArray = new int[arrayOfByte.length];
            int i = 0;
            while (i < arrayOfByte.length)
            {
                arrayOfByte[i] -= 48;
                radomNumberArray[i] = arrayOfByte[i];
                LogUtil.i(TAG, "Rodom[" + i + "]=" + radomNumberArray[i]);
                i += 1;
            }
            this.pkb.setRandomNumber(radomNumberArray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startPinInput()
    {
        try
        {
            String str = "123456789012345678";
            AidlPinpadListener.Stub local2 = new AidlPinpadListener.Stub()
            {
                public void onCancel()
                        throws RemoteException
                {
                    LogUtil.d(TAG, "onCancel been called.");
                    finish();
                }

                public void onError(int errorCode, String description)
                        throws RemoteException
                {
                    LogUtil.d(TAG, "onError been called. errorCode=" + errorCode);
                    LogUtil.d(TAG, "description=" + description);
                    finish();
                }

                public void onKeyDown(final int paramAnonymousInt1, int paramAnonymousInt2)
                        throws RemoteException
                {
                    InputPinActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            StringBuffer localStringBuffer = new StringBuffer();
                            int i = 0;
                            while (i < paramAnonymousInt1)
                            {
                                localStringBuffer.append(" * ");
                                i += 1;
                            }
                            InputPinActivity.this.txtPassword.setText(localStringBuffer.toString());
                        }
                    });
                }

                public void onPinRslt(byte[] paramAnonymousArrayOfByte)
                        throws RemoteException
                {
                    LogUtil.d(TAG, "PinResult:" + StringUtil.byte2HexStr(paramAnonymousArrayOfByte));
                    finish();
                }
            };
            modulePinpad.API06startPininput(2, str, new byte[] { 4, 6 }, local2);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


