package com.boc.aidl.printer;

/**
 * Created by CuncheW1 on 2017/3/13.
 */

public class PrinterErrorCode {
    public static final int NORMAL = 0;                 //没有错误，一切正常
    public static final int DATA_PARSING_ERROR = 1;   //打印数据解析错误
    public static final int ERROR_PAPERENDED = 2;     //缺纸
    public static final int ERROR_OVERHEAT = 3;       //超温
    public static final int ERROR_BUSY = 4;            //打印机繁忙
    public static final int ERROR_HARD_ISSUE = 5;     //硬件错误
    public static final int ERROR_PAPER_JAM = 6;      //卡纸
    public static final int GENERAL_ERROR = 0xff;     //其他错误
}
