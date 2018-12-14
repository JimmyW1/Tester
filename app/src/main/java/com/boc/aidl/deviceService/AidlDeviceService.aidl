package com.boc.aidl.deviceService;

interface AidlDeviceService{
    /**
     * 获取beeper句柄
     * @return 返回PBOC流程句柄，参见AidlBeeper
     */
    IBinder getBeeper();
    /**
     * 获取pboc流程句柄
     * @return 返回PBOC流程句柄，参见AidlPboc
     */
    IBinder getPBOC();
    
    /**
     * 获取pinpad设备句柄
     * @return 返回pinpad句柄，参见AidlPinpad
     */
    IBinder getPinpad();
    
    /**
     * 获取打印机操作句柄
     * @return 打印机句柄，参见AildPrinter.aidl类
     */
    IBinder getPrinter();
    
    /**
     * 获取设备信息句柄
     * @return DeviceInfo句柄，参见AidlDeviceInfo
     */
    IBinder getDeviceInfo();
    
    /**
     * 获取扫描枪句柄
     */
    IBinder getScan();
    
    /**
     * 获取指示灯句柄
     */
    IBinder getLed();
    
    /**
     * 获取IC卡句柄
     */
    IBinder getICCard();
    
    /**
     * 获取非接卡句柄
     */
    IBinder getRFCard();
    
    /**
     * 获取终端管理句柄
     */
   	IBinder getTerminalManage();
   	/**
     * 获取刷卡模块
     */
   	IBinder getSwiper();
    
    /**
     * 获取读卡器模块
     */
   	IBinder getCardReader();
}