package com.boc.aidl.pinpad;

import com.boc.aidl.pinpad.AidlPinpadListener;
interface AidlPinpad{
 /**
     * 下装主密钥
     *@param mkIndex 主秘钥索引
     * @param key   加密密钥数据
     * @param kcv 校验值
     * @return true表示成功， false表示失败
     */
    boolean loadMainKey(int mkIndex,String key,String kcv);
    
    /**
     * 下装工作密钥
     * @param keyType 秘钥类型 ：参见Const.KeyType
     * @param mkIndex 主秘钥索引
     * @param wkIndex 工作秘钥索引
     * @param keyValue  秘钥值
     * @param kcv    检验值
     * @return boolean
     */
    boolean loadWorkKey(String keyType,int mkIndex,int wkIndex, String keyValue,String kcv);
    
    /**
     * 计算MAC
     *@param macIndex mac秘钥索引
     * @param data      待加密数据
     * @return  mac数据，null表示失败
     */
    byte[] calcMAC(int macAlgorithm, int macIndex,in byte[] data);
    
    /**
     * 加密数据
     * @param KeyIndex   秘钥索引
     * @param data      磁道数据
     * @return          加密后的数据
     */
    byte[] encryptData(int keyIndex,in byte[] data);
    
    /**
     * 解密数据
     * @param 解密算法类型  
     * @param keyIndex   密钥索引
     * @param data      待解密数据
     * @return          解密后的数据
     */
    byte[] dencryptData(int keyIndex,in byte[] data);
    
    /**
     * 开启密码输入
     * @param pinKeyIndex pin秘钥索引
     * @param pan 主帐号
     * @param lenLimit 允许输入的密码长度范围
     * @param listener 监听对象
     */
    void startPininput(int pinKeyIndex,String pan,in byte [] lenLimit,AidlPinpadListener listener);

    /**
     * 获取8字节随机数
     */
    String getRandom();
    
    /**
     * 撤销密码输入
     */
     void cancelPininput();
     
     /**
     * 设置密码键盘布局
     * @layout 按键位置     
     */    
    byte[] setPinpadLayout(in byte[] layout);
}