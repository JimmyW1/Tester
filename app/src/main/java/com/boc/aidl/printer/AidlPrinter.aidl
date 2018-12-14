package com.boc.aidl.printer;

import com.boc.aidl.printer.AidlPrinterListener;
import java.util.Map;
import android.graphics.Bitmap;
interface AidlPrinter {
	/**
	 * 打印图片
	 * @param offset 图片偏移量
	 * @param bitmap bitmap格式的图片
	 * @param timeout 超时时间（单位为秒）
	 * @param listener 打印的监听回调
	 */
	void printBitMap(int offset, in Bitmap bitmap, in int timeout, in AidlPrinterListener listener);
	
	/**
	 * 打印脚本（纯文本）
	 * @param data 脚本内容
	 * @param map Map<String,Bitmap>
	 * @param timeout 超时时间（单位为秒）
	 * @param listener 打印的监听回调
	 */
	void printScript(String data,in Map map,in int timeout, in AidlPrinterListener listener);
	
	/**
	 * 打印条形码
	 * @param barCode 条形码的内容
	 * @param width 单条条形码的宽度取值[1,8],默认为2，建议>=2
	 * @param height 期望的高度[1,320],该值必须是8的倍数，默认为64
	 * @param position 打印的位置，可以 左、中、右，参见printContentPosition
	 * @param timeout 超时时间（单位为秒）
	 * @param listener 打印的监听回调
	 */
	void printBarCode(String barCode,int width, int height, int position, int timeout, in AidlPrinterListener listener);
	
	/**
	 * 打印二维码
	 * @param qrCode 二维码的内容
	 * @param height 期望的高度，不超过打印边界，默认为100
	 * @param errorCorrectingLevel 纠错级别[0,3],默认为2
	 * @param position 打印的位置，可以 左、中、右，参见printContentPosition
	 * @param timeout 超时时间（单位为秒）
	 * @param listener 打印的监听回调
	 */
	void printQrCode(String qrCode, int height, int errorCorrectingLevel, int position, int timeout, in AidlPrinterListener listener);
	
	/**
	 * 打印小票(可包含文本、图片、一维码、二维码)
	 * @param json 打印的字符串（以json形式传入）
	 * @param bitmap bitmap格式的图片数组
	 * @param listener 打印的监听回调
	 */
    void print(String json, in Bitmap[] bitmap, AidlPrinterListener listener);
    
 
    /**
     * 小票底部部空余行数 默认0
     * 热敏打印机芯退纸最多50行，行间距为0，超出则参数错误。
     * @param line 行数，正数进纸
     */
    void paperSkip(int line);  
    
    /**
     * 获取当前打印机状态
     *
     */
    int getStatus();
}
