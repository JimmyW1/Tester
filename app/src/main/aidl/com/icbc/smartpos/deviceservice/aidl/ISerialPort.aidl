package com.icbc.smartpos.deviceservice.aidl;

/**
 * 串口设备对象<br/>
 * 实现串口数据收发功能。
 * @author: baoxl
 */
interface ISerialPort {
	/**
	 * 打开串口
	 * @return 成功true，失败false
	 */
	boolean open();
	
	/**
	 * 关闭串口
	 * @return 成功true，失败false
	 */
	boolean close();
	
	/**
	 * 初始化串口
	 * @param bps	波特率
	 * <ul>
     * <li>1200 - 对应1200波特率</li>
     * <li>2400 - 对应2400波特率</li>
     * <li>4800 - 对应4800波特率</li>
     * <li>9600 - 对应9600波特率</li>
     * <li>14400 - 对应14400波特率</li>
     * <li>19200 - 对应19200波特率</li>
     * <li>28800 - 对应28800波特率</li>
     * <li>38400 - 对应38400波特率</li>
     * <li>57600 - 对应57600波特率</li>
     * <li>115200 - 对应115200波特率</li>
     * </ul>
	 * @param par	效验
	 * <ul>
     * <li>0 - 不校验</li>
     * <li>1 - 奇校验</li>
     * <li>2 - 偶校验</li>
     * </ul>
	 * @param dbs	数据位
	 * @return 成功true，失败false
	 */
	boolean init(int bps, int par, int dbs); 
	
	/**
	 * 读数据(接收)
	 * @param buffer	缓冲区
	 * @param timeout	超时时间，毫秒
	 * @return 返回实际读取的数据长度，失败返回-1
	 */
	int read(inout byte[] buffer, int timeout);
	
    /**
	 * 写数据(发送)
	 * @param data		要发送的数据
	 * @param timeout	超时时间，毫秒
	 * @return 返回实际写入的数据长度，失败返回-1
	 */
	int write(in byte[] data, int timeout);
	
	/**
	 * 清除接收缓冲区
	 * @return 成功true，失败false
	 */
	boolean clearInputBuffer();
	
	/**
	 * 查看缓冲区是否为空
	 * @param input	true为输入缓冲区，false为输出缓冲区
	 * @return 成功true，失败false
	 */
	 boolean isBufferEmpty(boolean input);
}
