package com.smartpos.deviceservice.aidl;

import com.smartpos.deviceservice.aidl.PrinterListener;

/**
 * 打印机对象<br/>
 * 实现对文本、条形码、二维码和图片的打印功能。
 * @author: baoxl
 */
interface IPrinter {    
    /**
	 * 获取打印机状态
	 * @return 打印机状态
	 * <ul>
	 * <li>ERROR_NONE(0x00) - 状态正常</li>
	 * <li>ERROR_PAPERENDED(0xF0) - 缺纸，不能打印</li>
     * <li>ERROR_HARDERR(0xF2) - 硬件错误</li>
     * <li>ERROR_OVERHEAT(0xF3) - 打印头过热</li>
     * <li>ERROR_BUFOVERFLOW(0xF5) - 缓冲模式下所操作的位置超出范围 </li>
     * <li>ERROR_LOWVOL(0xE1) - 低压保护 </li>
     * <li>ERROR_PAPERENDING(0xF4) - 纸张将要用尽，还允许打印(单步进针打特有返回值)</li>    
     * <li>ERROR_MOTORERR(0xFB) - 打印机芯故障(过快或者过慢)</li>  
     * <li>ERROR_PENOFOUND(0xFC) - 自动定位没有找到对齐位置,纸张回到原来位置   </li>
     * <li>ERROR_PAPERJAM(0xEE) - 卡纸</li>
     * <li>ERROR_NOBM(0xF6) - 没有找到黑标</li>
     * <li>ERROR_BUSY(0xF7) - 打印机处于忙状态</li>
     * <li>ERROR_BMBLACK(0xF8) - 黑标探测器检测到黑色信号</li>
     * <li>ERROR_WORKON(0xE6) - 打印机电源处于打开状态</li>
     * <li>ERROR_LIFTHEAD(0xE0) - 打印头抬起(自助热敏打印机特有返回值)</li>
     * <li>ERROR_CUTPOSITIONERR(0xE2) - 切纸刀不在原位(自助热敏打印机特有返回值)</li>
     * <li>ERROR_LOWTEMP(0xE3) - 低温保护或AD出错(自助热敏打印机特有返回值)</li>
     * </ul>
	 */
	int getStatus();
	
    /**
	 * 设置打印灰度
	 * @param gray - 打印灰度0~7
	 */
	void setGray(int gray);
	
	/**
	 * 添加一行打印文本
	 * @param format - 打印字体格式
	 * <ul>
	 * <li>font(int) - 0:small, 1:normal, 2:large</li>
	 * <li>align(int) - 0:left, 1:center, 2:right</li>
	 * <li>newline(boolean) - true:换行, false:不换行</li>
	 * </ul>
	 * @param text - 打印文本
	 */
	void addText(in Bundle format, String text);
	
    /**
	 * 添加条码打印
	 * @param format - 打印格式，可设置打印的位置、宽度、高度
	 * <ul>
	 * <li>align(int) - 0:left, 1:center, 2:right</li>
	 * <li>width(int) - 宽度</li>
	 * <li>height(int) - 高度</li>
	 * </ul>
	 * @param barcode - 条码内容
	 */
	void addBarCode(in Bundle format, in String barcode);
	
    /**
	 * 添加二维码打印
	 * @param format - 打印格式，可设置打印的位置、期望高度
	 * <ul>
	 * <li>offset(int) - 打印起始位置 </li>
	 * <li>expectedHeight(int) - 期望高度</li>
	 * </ul>
	 * @param qrCode - 二维码内容
	 */
	void addQrCode(in Bundle format, String qrCode);
	
    /**
	 * 添加图片打印
	 * @param format - 打印格式，可设置打印的位置、宽度、高度
	 * <ul>
	 * <li>offset(int) - 打印起始位置</li>
	 * <li>width(int) - 宽度</li>
     * <li>height(int) - 高度</li>
     * </ul>
	 * @param imageData - 图片数据
	 */
	void addImage(in Bundle format, in byte[] imageData);
	
    /**
	 * 走纸
	 * @param lines - 行数(lines > 1 && lines <= 50)
	 */
	void feedLine(int lines);
	
	/**
	 * 启动打印
	 * @param listener - 打印结果监听器
	 */
	void startPrint(PrinterListener listener);

	/**
     * 启动打印（打印不清缓存）
     * @param listener - 打印结果监听器
     */
    void  startSaveCachePrint(PrinterListener listener);

    /**
	 * 设置行间距
	 * @param space - 行间距0~50
	 */
    void setLineSpace(int space);
}
