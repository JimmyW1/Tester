package com.boc.aidl.printer;

interface AidlPrinterListener {
/**
     * 打印错误
     * @param errorCode 错误码,详见PrinterErrorCode.java
     * @param detail 错误详情
     */
    void onError(int errorCode, String detail);
    
/**
     * 打印完成
     */
    void onFinish();

}