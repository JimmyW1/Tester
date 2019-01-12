package com.test.util.xmlparse.beans;


import com.test.util.xmlparse.XmlParser;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class Terminal {
    private String receiptHeadLine1 = "MERCHANT NAME LN#1";
    private String receiptHeadLine2 = "MERCHANT NAME LN#2";
    private String receiptHeadLine3 = "MERCHANT NAME LN#3";
    private String vendorID = "00000001";
    private String subVendorID = "00000001";
    private int commTimeout = 30;
    private int commRetryTimes = 1;
    private String SysTrace = "000001";
    private int operatorTimeout = 60;
    private boolean isSupportFallback = true;
    private boolean isSupportTip = true;
    private boolean isSupRefundVoid = true;
    private boolean isSupUpiRefundVoid = true;
    private boolean isSupPreAuthCompleteRefundVoid = true;
    private boolean isScbRefundSupFullEmv = true;
    private boolean isUnlockForceSettlement = false;
    private boolean isTrainingMode = false;
    private boolean isIsoLogOn = false;
    private int autoInquiryTimes = 3;
    private boolean isAutoSettlemnt = false;
    private String autoSettlementTime = "00:00";
    private String autoSettlementFailedRetryTimeMins = "2";
    private String forceSettlementTime = "00:00";
    private boolean isForceSettlement = false;
    private int intervalTime = 40;
    private int inquiryTime = 20;
    private int manualInquiryTime = 60;
    private String adminDefaultPwd = "123456";
    private String transManagePwd = "123456";
    private boolean isSkipUserLogin = false;
    private boolean isSupportSaleInputCardNum = true;
    private boolean isRefundNeedInputRefNum = true;
    private boolean isRefundNeedInputApprovalCode = true;
    private boolean isSupportTCUpload = true;
    private boolean isDCCAllowed = false;

    public void toTmsXml(XmlSerializer xmlSerializer, Terminal terminal, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "SYSTEM_PARAMETER";
        writeParameter(xml, index, tn, "RECEIPT_HEADER_LINE1", "" + terminal.receiptHeadLine1);
        writeParameter(xml, index, tn, "RECEIPT_HEADER_LINE2",  "" + terminal.receiptHeadLine2);
        writeParameter(xml, index, tn, "RECEIPT_HEADER_LINE3",  "" + terminal.receiptHeadLine3);
        writeParameter(xml, index, tn, "TLE_VENDOR_ID",  "" + terminal.vendorID);
//        writeParameter(xml, index, tn, "TLE_VENDOR_ID",  "" + terminal.subVendorID);
        writeParameter(xml, index, tn, "COMM_TIMEOUT",  "" + terminal.commTimeout);
        writeParameter(xml, index, tn, "COMM_RETRY_TIMES",  "" + terminal.commRetryTimes);
        writeParameter(xml, index, tn, "SYS_TRACE",  "" + terminal.SysTrace);
        writeParameter(xml, index, tn, "OPERATOR_TIME_OUT",  "" + terminal.operatorTimeout);
        writeParameter(xml, index, tn, "ALLOW_FALLBACK",  "" + terminal.isSupportFallback);
        writeParameter(xml, index, tn, "ALLOW_TIP",  "" + terminal.isSupportTip);
        writeParameter(xml, index, tn, "ALLOW_REFUND_VOID",  "" + terminal.isSupRefundVoid);
        writeParameter(xml, index, tn, "ALLOW_UPI_REFUND_VOID",  "" + terminal.isSupUpiRefundVoid);
        writeParameter(xml, index, tn, "ALLOW_PRE_COMPLETE_REFUND_VOID",  "" + terminal.isSupPreAuthCompleteRefundVoid);
        writeParameter(xml, index, tn, "ALLOW_REFUND_FULL_EMV",  "" + terminal.isScbRefundSupFullEmv);
        writeParameter(xml, index, tn, "ALLOW_UNLOCK_FORCE_SETTLEMENT",  "" + terminal.isUnlockForceSettlement);
        writeParameter(xml, index, tn, "IS_TRAINING_MODE",  "" + terminal.isTrainingMode);
        writeParameter(xml, index, tn, "IS_ISO_LOG_ON",  "" + terminal.isIsoLogOn);
        writeParameter(xml, index, tn, "QR_AUTO_INQUIRY_TIMES",  "" + terminal.autoInquiryTimes);
        writeParameter(xml, index, tn, "IS_AUTO_SETTLEMENT",  "" + terminal.isAutoSettlemnt);
        writeParameter(xml, index, tn, "AUTO_SETTLEMENT_TIME",  "" + terminal.autoSettlementTime);
        writeParameter(xml, index, tn, "RETRY_AUTO_SETTLE_INTERVAL_IN_MIN",  "" + terminal.autoSettlementFailedRetryTimeMins);
        writeParameter(xml, index, tn, "FORCE_SETTLEMENT_TIME",  "" + terminal.forceSettlementTime);
        writeParameter(xml, index, tn, "ALLOW_FORCE_SETTLEMENT",  "" + terminal.isForceSettlement);
        writeParameter(xml, index, tn, "QR_INTERVAL_TIME",  "" + terminal.intervalTime);
        writeParameter(xml, index, tn, "QR_INQUIRY_TIME",  "" + terminal.inquiryTime);
        writeParameter(xml, index, tn, "QR_MANUAL_INQUIRY_TIME",  "" + terminal.manualInquiryTime);
        //ALIWECH_BSCANC_AUTO_INQUIRY_TIMES
        //ALIWECH_BSCANC_INQUIRY_TIME
        //QR_API_VERSION
        //QR_FORCE_PWD
        //ALLOW_QR_REFUND
        //QR_BILLERID_ONLY_REF1
        //QR_BILLERID_WITH_REF2
        //QR_SUPPORT_REF2
        //QR_MANUAL_INPUT_REF2
        //QR_DEFAULT_REF2
        //QR_MERCHANT_AID
        //QR_CURRENCY
        //QR_TERMINAL_TYPE
        //QR_PARTNER_CODE
        //QR_SWITCH_B_SCAN_C
        //QR_SWITCH_C_SCAN_B
        //ALIPAY_SWITCH_B_SCAN_C
        //ALIPAY_SWITCH_C_SCAN_B
        //WECHAT_SWITCH_C_SCAN_B
        //WECHAT_SWITCH_B_SCAN_C


        //IS_PRINT_RATE_SLIP
        //DCC_DISCLAIMER_FOR_MASTER
        //DCC_DISCLAIMER_FOR_VISA
        writeParameter(xml, index, tn, "IS_SKIP_USER_LOGIN",  "" + terminal.isSkipUserLogin);
        // isSupportSaleManualInputCardNum 不支持TMS
        //writeParameter(xml, index, tn, "",  "" + terminal.isSupportSaleInputCardNum);
        writeParameter(xml, index, tn, "IS_REFUND_NEED_INPUT_REF_NUM",  "" + terminal.isRefundNeedInputRefNum);
        writeParameter(xml, index, tn, "IS_REFUND_NEED_INPUT_APPR_CODE",  "" + terminal.isRefundNeedInputApprovalCode);
        writeParameter(xml, index, tn, "ALLOW_TC_UPLOAD",  "" + terminal.isSupportTCUpload);
        writeParameter(xml, index, tn, "ALLOW_DCC",  "" + terminal.isDCCAllowed);


        //注意问张强
//        writeParameter(xml, index, tn, "",  "" + terminal.adminDefaultPwd);
//        writeParameter(xml, index, tn, "",  "" + terminal.transManagePwd);
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

    public String getVendorID() {
        return vendorID;
    }

    public String getSubVendorID() {
        return subVendorID;
    }

    public String getReceiptHeadLine1() {
        return receiptHeadLine1;
    }

    public String getReceiptHeadLine2() {
        return receiptHeadLine2;
    }

    public String getReceiptHeadLine3() {
        return receiptHeadLine3;
    }

    public int getCommRetryTimes() {
        return commRetryTimes;
    }

    public int getCommTimeout() {
        return commTimeout;
    }

    public String getSysTrace() {
        return SysTrace;
    }

    public int getOperatorTimeout() {
        return operatorTimeout;
    }

    public boolean isSupportFallback() {
        return isSupportFallback;
    }

    public boolean isSupportTip() {
        return isSupportTip;
    }


    public boolean isSupRefundVoid() {
        return isSupRefundVoid;
    }

    public boolean isSupUpiRefundVoid() {
        return isSupUpiRefundVoid;
    }

    public boolean isSupPreAuthCompleteRefundVoid() {
        return isSupPreAuthCompleteRefundVoid;
    }

    public boolean isScbRefundSupFullEmv() {
        return isScbRefundSupFullEmv;
    }

    public boolean isUnlockForceSettlement() {
        return isUnlockForceSettlement;
    }

    public boolean isTrainingMode() {
        return isTrainingMode;
    }

    public boolean isIsoLogOn() {
        return isIsoLogOn;
    }

    public boolean isAutoSettlemnt() {
        return isAutoSettlemnt;
    }

    public String getAutoSettlementTime() {
        return autoSettlementTime;
    }

    public String getAutoSettlementFailedRetryTimeMins() {
        return autoSettlementFailedRetryTimeMins;
    }

    public void setAutoSettlementFailedRetryTimeMins(String autoSettlementFailedRetryTimeMins) {
        this.autoSettlementFailedRetryTimeMins = autoSettlementFailedRetryTimeMins;
    }

    public String getForceSettlementTime() {
        return forceSettlementTime;
    }

    public boolean isForceSettlement() {
        return isForceSettlement;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public int getInquiryTime() {
        return inquiryTime;
    }

    public int getManualInquiryTime() {
        return manualInquiryTime;
    }

    public String getAdminDefaultPwd() {
        return adminDefaultPwd;
    }

    public String getTransManagePwd() {
        return transManagePwd;
    }

    public boolean isSkipUserLogin() {
        return isSkipUserLogin;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public void setSubVendorID(String subVendorID) {
        this.subVendorID = subVendorID;
    }

    public void setReceiptHeadLine1(String receiptHeadLine1) {
        this.receiptHeadLine1 = receiptHeadLine1;
    }

    public void setReceiptHeadLine2(String receiptHeadLine2) {
        this.receiptHeadLine2 = receiptHeadLine2;
    }

    public void setReceiptHeadLine3(String receiptHeadLine3) {
        this.receiptHeadLine3 = receiptHeadLine3;
    }

    public void setCommRetryTimes(int commRetryTimes) {
        this.commRetryTimes = commRetryTimes;
    }

    public void setCommTimeout(int commTimeout) {
        this.commTimeout = commTimeout;
    }

    public void setSysTrace(String sysTrace) {
        SysTrace = sysTrace;
    }

    public void setOperatorTimeout(int operatorTimeout) {
        this.operatorTimeout = operatorTimeout;
    }

    public void setSupportFallback(boolean supportFallback) {
        isSupportFallback = supportFallback;
    }

    public void setSupportTip(boolean supportTip) {
        isSupportTip = supportTip;
    }

    public void setSupRefundVoid(boolean supRefundVoid) {
        isSupRefundVoid = supRefundVoid;
    }

    public void setSupUpiRefundVoid(boolean supUpiRefundVoid) {
        isSupUpiRefundVoid = supUpiRefundVoid;
    }

    public void setSupPreAuthCompleteRefundVoid(boolean supPreAuthCompleteRefundVoid) {
        isSupPreAuthCompleteRefundVoid = supPreAuthCompleteRefundVoid;
    }

    public void setScbRefundSupFullEmv(boolean scbRefundSupFullEmv) {
        isScbRefundSupFullEmv = scbRefundSupFullEmv;
    }

    public void setUnlockForceSettlement(boolean unlockForceSettlement) {
        isUnlockForceSettlement = unlockForceSettlement;
    }

    public void setTrainingMode(boolean trainingMode) {
        isTrainingMode = trainingMode;
    }

    public void setIsoLogOn(boolean isoLogOn) {
        isIsoLogOn = isoLogOn;
    }

    public void setAutoSettlemnt(boolean autoSettlemnt) {
        isAutoSettlemnt = autoSettlemnt;
    }

    public void setAutoSettlementTime(String autoSettlementTime) {
        this.autoSettlementTime = autoSettlementTime;
    }

    public void setForceSettlementTime(String forceSettlementTime) {
        this.forceSettlementTime = forceSettlementTime;
    }

    public void setForceSettlement(boolean forceSettlement) {
        isForceSettlement = forceSettlement;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void setInquiryTime(int inquiryTime) {
        this.inquiryTime = inquiryTime;
    }

    public void setManualInquiryTime(int manualInquiryTime) {
        this.manualInquiryTime = manualInquiryTime;
    }

    public void setAdminDefaultPwd(String adminDefaultPwd) {
        this.adminDefaultPwd = adminDefaultPwd;
    }

    public void setTransManagePwd(String transManagePwd) {
        this.transManagePwd = transManagePwd;
    }

    public void setSkipUserLogin(boolean skipUserLogin) {
        isSkipUserLogin = skipUserLogin;
    }

    public int getAutoInquiryTimes() {
        return autoInquiryTimes;
    }

    public void setAutoInquiryTimes(int autoInquiryTimes) {
        this.autoInquiryTimes = autoInquiryTimes;
    }

    public boolean isSupportSaleInputCardNum() {
        return isSupportSaleInputCardNum;
    }

    public void setSupportSaleInputCardNum(boolean supportSaleInputCardNum) {
        isSupportSaleInputCardNum = supportSaleInputCardNum;
    }

    public boolean isRefundNeedInputRefNum() {
        return isRefundNeedInputRefNum;
    }

    public boolean isRefundNeedInputApprovalCode() {
        return isRefundNeedInputApprovalCode;
    }

    public void setRefundNeedInputRefNum(boolean refundNeedInputRefNum) {
        isRefundNeedInputRefNum = refundNeedInputRefNum;
    }

    public void setRefundNeedInputApprovalCode(boolean refundNeedInputApprovalCode) {
        isRefundNeedInputApprovalCode = refundNeedInputApprovalCode;
    }

    public boolean isSupportTCUpload() {
        return isSupportTCUpload;
    }

    public void setSupportTCUpload(boolean supportTCUpload) {
        isSupportTCUpload = supportTCUpload;
    }

    public boolean isDCCAllowed() {
        return isDCCAllowed;
    }

    public void setDCCAllowed(boolean DCCAllowed) {
        isDCCAllowed = DCCAllowed;
    }
}
