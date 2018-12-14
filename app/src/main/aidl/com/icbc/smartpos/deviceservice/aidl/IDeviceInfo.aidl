package com.icbc.smartpos.deviceservice.aidl;
import com.icbc.smartpos.deviceservice.aidl.TusnData;


interface IDeviceInfo {

    /**
    * 获取终端设备序列号
    */
    String getSerialNo();

    /**
    * 获取终端IMSI号
    */
    String getIMSI();

    /**
    * 获取终端IMEI号
    */
    String getIMEI();

    /**
    * 获取终端SIM卡ICCID号
    */
    String getICCID();

    /**
    * 获取厂商名称
    */
    String getManufacture();

    /**
    * 获取终端型号
    */
    String getModel();

    /**
    * 获取Android操作系统版本
    */
    String getAndroidOSVersion();

    /**
    * 获取Android内核版本
    */
    String getAndroidKernelVersion();

    /**
    * 获取终端ROM版本
    */
    String getROMVersion();

    /**
    * 获取终端固件版本
    */
    String getFirmwareVersion();

    /**
    * 获取终端硬件版本
    */
    String getHardwareVersion();

    /**
    * 更新终端系统时间
    */
    boolean updateSystemTime(String date, String time);

    /**
    * 系统功能设置
    * bundle
    * <li>HOMEKEY(boolean)</li>    //是否屏蔽home键
    * <li>STATUSBARKEY(boolean)</li>  //是否屏蔽下拉栏
    */
    boolean setSystemFunction(in Bundle bundle);

    /**
     * 获取银联终端唯一终端号TUSN
     * @param mode 模式, 预留参数， 需为0
     * @param input 指 对TUSN计算 MAC 时，参与计算的随机数(随机因子)，允许范围:4~10字节
     * @return 成功返回TUSN数据，失败返回null。
    **/
    TusnData getTUSN(int mode, in byte[] input);
}
