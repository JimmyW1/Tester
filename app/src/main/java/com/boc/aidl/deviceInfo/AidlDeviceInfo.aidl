package com.boc.aidl.deviceInfo;

interface AidlDeviceInfo{
   /**
    * 获得设备序列号
    * 对于原有设备获取SN 新设备获取TUSN
    * @return 设备序列号
    */
	String getTUSN();
	/**
	 * 获得客户定义的设备序列号CSN
	 * @return 户定义的设备序列号
	 */
	String getCSN();
	/**
	 * 获得厂商ID
	 * @return 厂商ID
	 */
	String getVID();
	/**
	 * 获得厂商Name
	 * @return 厂商Name
	 */
	String getVName();
	/**
	 * 获得客户定义的密钥序列号KSN
	 * @return 客户定义的密钥序列号
	 */
	String getKSN();
	/**
	 * 是否支持IC卡
	 * @return 是否支持IC卡
	 */
	boolean isSupportIcCard();
   /**
    * 是否支持磁条卡
    * @return 是否支持磁条卡
    */
	boolean isSupportMagCard();
    /**
     * 是否支持非接卡
     * @return 是否支持非接卡
     */
	boolean isSupportRFCard();
	/**
	 * 是否支持打印
	 * @return 是否支持打印
	 */
	boolean isSupportPrint();
    /**
     * 是否支持脱机交易
     * @return 是否支持脱机交易
     */
	boolean isSupportOffLine();
    /**
     * 是否支持蜂鸣器
     * @return 是否支持蜂鸣器
     */
	boolean isSupportBeep();
    /**
     * 是否支持LED
     * @return 是否支持LED
     */
	boolean isSupportLed();
	
   /**
	*
	*获取系统版本号
	*/
    String getVersion();
  /**
	*
	*获取IMSI号
	*/  
    String getIMSI();
   /**
	*
	*获取IMEI号
	*/  
    String getIMEI();
    /**
	*
	*获取SIM卡的ICCID号
	*/  
    String getICCID();
   /**
	*
	*获取设备型号
	*/  
    String getType();
      /**
	*
	*获取android系统版本号
	*/  
    String getOSVersion();
   /**
	*
	*获取android内核版本
	*/  
    String getKernelVersion();
   
   /**
	*
	*获取Service版本
	*/
    String getServiceVersion();
   /**
	*
	*获取硬件序列号
	*/
    String getHardwareNumber();
    /**
	*
	*获取入网编号
	*/
    String getLicenseNumber();
    /**
	*
	*获取经度
	*/
    String getLongitude();
    /**
	*
	*获取维度
	*/
    String getLatitude();
    /**
	*
	*获取通过人行21号文规则使用SM4加密的数据
	*/
    String getSM4EncryptedData (String data);
}