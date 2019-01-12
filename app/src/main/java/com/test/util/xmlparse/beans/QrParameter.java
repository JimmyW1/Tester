package com.test.util.xmlparse.beans;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class QrParameter {
    /**
     * qr,支付宝，微信二维码默认查询次数
     * 默认暂时设为3
     */
    private int qrAutoInquiryTimes = 3;
    /**
     * qr,支付宝，微信二维码静默时间
     * 默认40,测试暂时设为4
     */
    private int qrIntervalTime = 40;
    /**
     * qr,支付宝，微信查询时间
     * 默认20,测试暂时设为4
     */
    private int qrInquiryTime = 20;
    /**
     * qr,支付宝，微信请求人工查询静默时间
     * 默认60,测试暂时设为4
     */
    private int qrManualInquiryTime = 60;

    /**
     * Alipay/Wechat b scan c status is unknow,perform auto inquiry times.
     */
    private int aliWeAutoInquiryTimes=4;

    /**
     * Alipay/Wechat b scan c status is unknow,perform auto inquiry times every sec.
     */
    private int aliWeInquiryTime=10;


    /**
     * qr,支付宝，微信apiversion
     */
    private String qrApiVersion = "2.0";

    /**
     * qr,支付宝，微信请求人工查询静默时间
     * 默认60,测试暂时设为4
     */
    private String qrForcePassword = "123456";

    private String qrBillerIdOnlyRef1 = "311040039475100";

    private String qrBillerIdWithRef2 = "311040039475101";

    private String qrMerchantAID = "A000000677010112";

    private boolean qrSupportRef2 = true;

    private boolean qrSwitchBscanC = true;

    private boolean qrSwitchCscanB = true;

    private boolean qrManualInputRef2 = true;

    private String qrDefaultRef2 = "00000000000000000000";

    private boolean qrAliSwitchBscanC = true;

    private boolean qrAliSwitchCscanB = true;

    private boolean qrWeChSwitchBscanC = true;

    private boolean qrWeChSwitchCscanB = true;

    public void toTmsXml(XmlSerializer xmlSerializer, QrParameter qrParameter, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "SYSTEM_PARAMETER";

        writeParameter(xml, index, tn, "QR_AUTO_INQUIRY_TIMES", "" + qrParameter.qrAutoInquiryTimes);
        writeParameter(xml, index, tn, "QR_INTERVAL_TIME", "" + qrParameter.qrIntervalTime);
        writeParameter(xml, index, tn, "QR_INQUIRY_TIME", "" + qrParameter.qrInquiryTime);
        writeParameter(xml, index, tn, "QR_MANUAL_INQUIRY_TIME", "" + qrParameter.qrManualInquiryTime);
        writeParameter(xml, index, tn, "ALIWECH_BSCANC_AUTO_INQUIRY_TIMES", "" + qrParameter.aliWeAutoInquiryTimes);
        writeParameter(xml, index, tn, "ALIWECH_BSCANC_INQUIRY_TIME", "" + qrParameter.aliWeInquiryTime);
        writeParameter(xml, index, tn, "QR_API_VERSION", "" + qrParameter.qrApiVersion);
        writeParameter(xml, index, tn, "QR_FORCE_PWD", "" + qrParameter.qrForcePassword);
        //ALLOW_QR_REFUND
        writeParameter(xml, index, tn, "QR_BILLERID_ONLY_REF1", "" + qrParameter.qrBillerIdOnlyRef1);
        writeParameter(xml, index, tn, "QR_BILLERID_WITH_REF2", "" + qrParameter.qrBillerIdWithRef2);
        writeParameter(xml, index, tn, "QR_MERCHANT_AID", "" + qrParameter.qrMerchantAID);
        writeParameter(xml, index, tn, "QR_SUPPORT_REF2", "" + qrParameter.qrSupportRef2);
        writeParameter(xml, index, tn, "QR_SWITCH_B_SCAN_C", "" + qrParameter.qrSwitchBscanC);
        writeParameter(xml, index, tn, "QR_SWITCH_C_SCAN_B", "" + qrParameter.qrSwitchCscanB);
        writeParameter(xml, index, tn, "QR_MANUAL_INPUT_REF2", "" + qrParameter.qrManualInputRef2);
        writeParameter(xml, index, tn, "QR_DEFAULT_REF2", "" + qrParameter.qrDefaultRef2);
        //QR_CURRENCY
        //QR_TERMINAL_TYPE
        //QR_PARTNER_CODE
        writeParameter(xml, index, tn, "ALIPAY_SWITCH_B_SCAN_C", "" + qrParameter.qrAliSwitchBscanC);
        writeParameter(xml, index, tn, "ALIPAY_SWITCH_C_SCAN_B", "" + qrParameter.qrAliSwitchCscanB);
        writeParameter(xml, index, tn, "WECHAT_SWITCH_C_SCAN_B", "" + qrParameter.qrWeChSwitchBscanC);
        writeParameter(xml, index, tn, "WECHAT_SWITCH_B_SCAN_C", "" + qrParameter.qrWeChSwitchCscanB);
    }

    private void writeParameter(XmlSerializer xmlSerializer, String index, String tableName, String valueTAG, String value) {
        final String TAG_NAME = "parameter";
        final String ATTRIBUTE_databaseFieldName = "databaseFieldName";
        final String ATTRIBUTE_name = "name";
        final String ATTRIBUTE_value = "value";

        final String dbFieldNamePre = "AP";

        String dbFieldNameSuffix = "";
        if (!index.equals("")) {
            dbFieldNameSuffix = "-" + index;
        }

        if (value == null) {
            value = "";
        }

        value = value.trim();

        if (value.equals("true")) {
            value = "1";
        } else if (value.equals("false")) {
            value = "0";
        }

        try {
            xmlSerializer.startTag(null, TAG_NAME);

            xmlSerializer.startTag(null, ATTRIBUTE_databaseFieldName);
            xmlSerializer.text(dbFieldNamePre+ "-" + tableName + "-" + valueTAG + dbFieldNameSuffix);
            xmlSerializer.endTag(null, ATTRIBUTE_databaseFieldName);

            xmlSerializer.startTag(null, ATTRIBUTE_name);
            xmlSerializer.text(tableName);
            xmlSerializer.endTag(null, ATTRIBUTE_name);

            xmlSerializer.startTag(null, ATTRIBUTE_value);
            xmlSerializer.text(value);
            xmlSerializer.endTag(null, ATTRIBUTE_value);

            xmlSerializer.endTag(null, TAG_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getQrAutoInquiryTimes() {
        return qrAutoInquiryTimes;
    }

    public void setQrAutoInquiryTimes(int qrAutoInquiryTimes) {
        this.qrAutoInquiryTimes = qrAutoInquiryTimes;
    }

    public int getQrIntervalTime() {
        return qrIntervalTime;
    }

    public void setQrIntervalTime(int qrIntervalTime) {
        this.qrIntervalTime = qrIntervalTime;
    }

    public int getQrInquiryTime() {
        return qrInquiryTime;
    }

    public void setQrInquiryTime(int qrInquiryTime) {
        this.qrInquiryTime = qrInquiryTime;
    }

    public int getQrManualInquiryTime() {
        return qrManualInquiryTime;
    }

    public void setQrManualInquiryTime(int qrManualInquiryTime) {
        this.qrManualInquiryTime = qrManualInquiryTime;
    }

    public String getQrApiVersion() {
        return qrApiVersion;
    }

    public void setQrApiVersion(String qrApiVersion) {
        this.qrApiVersion = qrApiVersion;
    }

    public String getQrForcePassword() {
        return qrForcePassword;
    }

    public void setQrForcePassword(String qrForcePassword) {
        this.qrForcePassword = qrForcePassword;
    }

    public String getQrBillerIdOnlyRef1() {
        return qrBillerIdOnlyRef1;
    }

    public void setQrBillerIdOnlyRef1(String qrBillerIdOnlyRef1) {
        this.qrBillerIdOnlyRef1 = qrBillerIdOnlyRef1;
    }

    public String getQrBillerIdWithRef2() {
        return qrBillerIdWithRef2;
    }

    public void setQrBillerIdWithRef2(String qrBillerIdWithRef2) {
        this.qrBillerIdWithRef2 = qrBillerIdWithRef2;
    }

    public String getQrMerchantAID() {
        return qrMerchantAID;
    }

    public void setQrMerchantAID(String qrMerchantAID) {
        this.qrMerchantAID = qrMerchantAID;
    }

    public boolean isQrSupportRef2() {
        return qrSupportRef2;
    }

    public void setQrSupportRef2(boolean qrSupportRef2) {
        this.qrSupportRef2 = qrSupportRef2;
    }

    public boolean isQrSwitchBscanC() {
        return qrSwitchBscanC;
    }

    public void setQrSwitchBscanC(boolean qrSwitchBscanC) {
        this.qrSwitchBscanC = qrSwitchBscanC;
    }

    public boolean isQrSwitchCscanB() {
        return qrSwitchCscanB;
    }

    public void setQrSwitchCscanB(boolean qrSwitchCscanB) {
        this.qrSwitchCscanB = qrSwitchCscanB;
    }

    public boolean isQrManualInputRef2() {
        return qrManualInputRef2;
    }

    public void setQrManualInputRef2(boolean qrManualInputRef2) {
        this.qrManualInputRef2 = qrManualInputRef2;
    }

    public String getQrDefaultRef2() {
        return qrDefaultRef2;
    }

    public void setQrDefaultRef2(String qrDefaultRef2) {
        this.qrDefaultRef2 = qrDefaultRef2;
    }

    public boolean isQrAliSwitchBscanC() {
        return qrAliSwitchBscanC;
    }

    public void setQrAliSwitchBscanC(boolean qrAliSwitchBscanC) {
        this.qrAliSwitchBscanC = qrAliSwitchBscanC;
    }

    public boolean isQrAliSwitchCscanB() {
        return qrAliSwitchCscanB;
    }

    public void setQrAliSwitchCscanB(boolean qrAliSwitchCscanB) {
        this.qrAliSwitchCscanB = qrAliSwitchCscanB;
    }

    public boolean isQrWeChSwitchBscanC() {
        return qrWeChSwitchBscanC;
    }

    public void setQrWeChSwitchBscanC(boolean qrWeChSwitchBscanC) {
        this.qrWeChSwitchBscanC = qrWeChSwitchBscanC;
    }

    public boolean isQrWeChSwitchCscanB() {
        return qrWeChSwitchCscanB;
    }

    public void setQrWeChSwitchCscanB(boolean qrWeChSwitchCscanB) {
        this.qrWeChSwitchCscanB = qrWeChSwitchCscanB;
    }

    public int getAliWeAutoInquiryTimes() {
        return aliWeAutoInquiryTimes;
    }

    public void setAliWeAutoInquiryTimes(int aliWeAutoInquiryTimes) {
        this.aliWeAutoInquiryTimes = aliWeAutoInquiryTimes;
    }

    public int getAliWeInquiryTime() {
        return aliWeInquiryTime;
    }

    public void setAliWeInquiryTime(int aliWeInquiryTime) {
        this.aliWeInquiryTime = aliWeInquiryTime;
    }
}
