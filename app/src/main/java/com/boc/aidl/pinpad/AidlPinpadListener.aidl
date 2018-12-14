package com.boc.aidl.pinpad;

interface AidlPinpadListener{
    /**
     * 按键时回调
     * @param len 当前密码的长度
     * @param key *号掩码键值
     */
    void onKeyDown(int len, int key);
    /**
     * 当用户点击确认按键时被调用
     * @param data pin码
     */
    void onPinRslt(in byte[] data);
    /**
     * pinpad设备发生错误时候被调用
     * @param errorCode 错误码，参见PinpadErrorCode类中的描述
     * @param errorDescription 错误描述
     */
    void onError(int errorCode,String errorDescription);
    /**
     * 取消密码输入
     */
    void onCancel();
}