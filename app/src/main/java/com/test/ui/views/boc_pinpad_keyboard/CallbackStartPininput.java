package com.test.ui.views.boc_pinpad_keyboard;

import com.boc.aidl.pinpad.AidlPinpadListener;

/**
 * Created by YahuG1 on 2017/7/21.
 */

public interface CallbackStartPininput {
    void startPininput(int pinKeyIndex, String pan, byte[] lenLimit, AidlPinpadListener listener);
}
