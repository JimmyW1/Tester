package com.icbc.smartpos.deviceservice.aidl;

import com.icbc.smartpos.deviceservice.aidl.IBeeper;
import com.icbc.smartpos.deviceservice.aidl.IDeviceInfo;
import com.icbc.smartpos.deviceservice.aidl.ILed;
import com.icbc.smartpos.deviceservice.aidl.IMagCardReader;
import com.icbc.smartpos.deviceservice.aidl.IDeviceService;
import com.icbc.smartpos.deviceservice.aidl.IInsertCardReader;
import com.icbc.smartpos.deviceservice.aidl.IPBOC;
import com.icbc.smartpos.deviceservice.aidl.IPinpad;
import com.icbc.smartpos.deviceservice.aidl.IPrinter;
import com.icbc.smartpos.deviceservice.aidl.IRFCardReader;
import com.icbc.smartpos.deviceservice.aidl.IScanner;
import com.icbc.smartpos.deviceservice.aidl.ISerialPort;
import com.icbc.smartpos.deviceservice.aidl.IExternalSerialPort;

/**
 * 设备服务对象，提供范围终端各外设对象的服务接口
 * @author: baoxl
 */
interface IDeviceService {
    /**
     * 获取蜂鸣器操作对象
     * @return IBeeper对象，参见IBeeper.aidl类
     */
    IBeeper getBeeper();
    
    /**
     * 获取LED灯操作对象
     * @return ILed对象，参见ILed.aidl类
     */
    ILed getLed();
    
    /**
     * 获取串口操作对象
     * @return ISerialPort对象，参见ISerialPort.aidl类
     */
    ISerialPort getSerialPort();
    
    /**
     * 获取扫码器操作对象
     * @param cameraId - 0前置扫码器，1后置扫码器
     * @return IScanner对象，参见IScanner.aidl类
     */
    IScanner getScanner(int cameraId);

    /**
     * 获取磁卡操作句柄
     * @return IMagCardReader对象，参见IMagCardReader.aidl类
     */
    IMagCardReader getMagCardReader();
    
    /**
     * 获取IC卡操作对象
     * @return IC卡操作对象，参见IInsertCardReader.aidl类
     */
    IInsertCardReader getInsertCardReader();
    
    /**
     * 获取RF卡操作对象
     * @return RF卡操作对象，参见IRFCardReader.aidl类
     */
    IRFCardReader getRFCardReader();
    
    /**
     * 获取密码键盘操作对象
     * @param kapId  密码键盘kapId索引，每个kapId对一个逻辑密码键盘
     * @return IPinpad对象，参见IPinpad.aidl类
     */
    IPinpad getPinpad(int kapId);
    
    /**
     * 获取打印机操作对象
     * @return IPrinter对象，参见IPrinter.aidl类
     */
    IPrinter getPrinter();
    
    /**
     * 获取PBOC(EMV)流程操作对象
     * @return IPBOC对象，参见IPBOC.aidl类
     */
    IPBOC getPBOC();
    
    /**
     * 设备操作对象
     * @return IDeviceInfo对象，参见IDeviceInfo.aidl类
     */
    IDeviceInfo getDeviceInfo();

     /**
     * 底座串口操作对象
     * @return IExternalSerialPort对象，参见IExternalSerialPort.aidl类
     */
    IExternalSerialPort getExternalSerialPort();
}
