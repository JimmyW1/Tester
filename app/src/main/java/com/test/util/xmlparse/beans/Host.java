package com.test.util.xmlparse.beans;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class Host {
    String index = "";
    private String merchantId; //
    private String terminalId;
    private String batchNum;
    private String traceNum;
    private String NII;
    private String tleEdcNII;
    private String tleKmsNII;
    private String primaryIP; //6
    private String primaryPort;
    private String secondaryIP;
    private String secondaryPort;
    private String simApn; //10
    private String hostName;
    private String LTMKAcquirerID;
    private String acquirerID;
    private String TE_ID;
    private String TE_PIN; //16
    private boolean isTLEAllowed;
    private String qrHostServer;
    private String qrHostGetTmk;
    private String qrHostGetTwk;
    private String qrHostNormal;

    private String qrAcquirer;
    private String qrVendorId;
    private String qrSubVendorId;
    private String qrMerchantName;
    private String qrDefaultSecretkey;


    public int getHostType() {
        switch (hostName) {
            case "SCB":
                return HostType.SCB;
            case "UPI":
                return HostType.UPI;
            case "DCC":
                return HostType.DCC;
            case "IPP":
                return HostType.IPP;
            case "ALIPAY":
                return HostType.ALIPAY;
            case "WECHAT":
                return HostType.WECHAT;
            case "ThaiQR":
                return HostType.QR_PROMPTPAY;
            case "QRCS":
                return HostType.QR_CARD;

        }

        return HostType.UNKOWN;
    }

    public void toTmsXml(XmlSerializer xmlSerializer, Host host, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "HOST_INFO";

        int hostType = getHostType();
        writeParameter(xml, index, tn, "HOST_TYPE", "" + hostType);
        writeParameter(xml, index, tn, "MERCHANT_ID", "" + host.merchantId);
        writeParameter(xml, index, tn, "TERMINAL_ID", "" + host.terminalId);
        writeParameter(xml, index, tn, "BATCH_NO", "" + host.batchNum);
        writeParameter(xml, index, tn, "STAN_NO", "" + host.traceNum);
        writeParameter(xml, index, tn, "NII", "" + host.NII);
        writeParameter(xml, index, tn, "TLE_EDC_NII", "" + host.tleEdcNII);
        writeParameter(xml, index, tn, "TLE_KMS_NII", "" + host.tleKmsNII);
        writeParameter(xml, index, tn, "PRIMARY_IP", "" + host.primaryIP);
        writeParameter(xml, index, tn, "PRIMARY_PORT", "" + host.primaryPort);
        writeParameter(xml, index, tn, "SECONDARY_IP", "" + host.secondaryIP);
        writeParameter(xml, index, tn, "SECONDARY_PORT", "" + host.secondaryPort);
//        writeParameter(xml, index, tn, "", "" + host.simApn);
        writeParameter(xml, index, tn, "HOST_NAME", "" + host.hostName);
        writeParameter(xml, index, tn, "LTMK_ACQUIRER_ID", "" + host.LTMKAcquirerID);
        writeParameter(xml, index, tn, "ACQUIRER_ID", "" + host.acquirerID);
        writeParameter(xml, index, tn, "TE_ID", "" + host.TE_ID);
        writeParameter(xml, index, tn, "TE_PIN", "" + host.TE_PIN);
        writeParameter(xml, index, tn, "ALLOW_TLE", "" + host.isTLEAllowed);
        writeParameter(xml, index, tn, "QR_HOST_SERVER", "" + host.qrHostServer);
        writeParameter(xml, index, tn, "QR_HOST_GET_TMK", "" + host.qrHostGetTmk);
        writeParameter(xml, index, tn, "QR_HOST_GET_TWK", "" + host.qrHostGetTwk);
        writeParameter(xml, index, tn, "QR_HOST_NORMAL", "" + host.qrHostNormal);
        writeParameter(xml, index, tn, "QR_ACQUIRER", "" + host.qrAcquirer);
        writeParameter(xml, index, tn, "QR_VENDOR_ID", "" + host.qrVendorId);
        writeParameter(xml, index, tn, "QR_SUB_VENDOR_ID", "" + host.qrSubVendorId);
        //QR_BILLERID
        writeParameter(xml, index, tn, "QR_MERCHANT_NAME", "" + host.qrMerchantName);
        writeParameter(xml, index, tn, "QR_DEFAULT_SECRET_KEY", "" + host.qrDefaultSecretkey);
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum;
    }

    public String getNII() {
        return NII;
    }

    public void setNII(String NII) {
        this.NII = NII;
    }

    public String getPrimaryIP() {
        return primaryIP;
    }

    public void setPrimaryIP(String primaryIP) {
        this.primaryIP = primaryIP;
    }

    public String getPrimaryPort() {
        return primaryPort;
    }

    public void setPrimaryPort(String primaryPort) {
        this.primaryPort = primaryPort;
    }

    public String getSecondaryIP() {
        return secondaryIP;
    }

    public void setSecondaryIP(String secondaryIP) {
        this.secondaryIP = secondaryIP;
    }

    public String getSecondaryPort() {
        return secondaryPort;
    }

    public void setSecondaryPort(String secondaryPort) {
        this.secondaryPort = secondaryPort;
    }

    public String getSimApn() {
        return simApn;
    }

    public void setSimApn(String simApn) {
        this.simApn = simApn;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLTMKAcquirerID() {
        return LTMKAcquirerID;
    }

    public void setLTMKAcquirerID(String LTMKAcquirerID) {
        this.LTMKAcquirerID = LTMKAcquirerID;
    }

    public String getAcquirerID() {
        return acquirerID;
    }

    public void setAcquirerID(String acquirerID) {
        this.acquirerID = acquirerID;
    }

    public String getTE_ID() {
        return TE_ID;
    }

    public void setTE_ID(String TE_ID) {
        this.TE_ID = TE_ID;
    }

    public String getTE_PIN() {
        return TE_PIN;
    }

    public void setTE_PIN(String TE_PIN) {
        this.TE_PIN = TE_PIN;
    }

    public String getTleEdcNII() {
        return tleEdcNII;
    }

    public String getTleKmsNII() {
        return tleKmsNII;
    }

    public void setTleEdcNII(String tleEdcNII) {
        this.tleEdcNII = tleEdcNII;
    }

    public void setTleKmsNII(String tleKmsNII) {
        this.tleKmsNII = tleKmsNII;
    }

    public void setIsTLEAllowed(boolean isTLEAllowed) {
        this.isTLEAllowed = isTLEAllowed;
    }

    public boolean getIsTLEAllowed() {
        return isTLEAllowed;
    }

    public String getQrHostServer() {
        return qrHostServer;
    }

    public void setQrHostServer(String qrHostServer) {
        this.qrHostServer = qrHostServer;
    }

    public String getQrHostGetTmk() {
        return qrHostGetTmk;
    }

    public void setQrHostGetTmk(String qrHostGetTmk) {
        this.qrHostGetTmk = qrHostGetTmk;
    }

    public String getQrHostGetTwk() {
        return qrHostGetTwk;
    }

    public void setQrHostGetTwk(String qrHostGetTwk) {
        this.qrHostGetTwk = qrHostGetTwk;
    }

    public String getQrHostNormal() {
        return qrHostNormal;
    }

    public void setQrHostNormal(String qrHostNormal) {
        this.qrHostNormal = qrHostNormal;
    }

    public String getQrAcquirer() {
        return qrAcquirer;
    }

    public void setQrAcquirer(String qrAcquirer) {
        this.qrAcquirer = qrAcquirer;
    }

    public String getQrVendorId() {
        return qrVendorId;
    }

    public void setQrVendorId(String qrVendorId) {
        this.qrVendorId = qrVendorId;
    }

    public String getQrSubVendorId() {
        return qrSubVendorId;
    }

    public void setQrSubVendorId(String qrSubVendorId) {
        this.qrSubVendorId = qrSubVendorId;
    }

    public String getQrMerchantName() {
        return qrMerchantName;
    }

    public void setQrMerchantName(String qrMerchantName) {
        this.qrMerchantName = qrMerchantName;
    }

    public String getQrDefaultSecretkey() {
        return qrDefaultSecretkey;
    }

    public void setQrDefaultSecretkey(String qrDefaultSecretkey) {
        this.qrDefaultSecretkey = qrDefaultSecretkey;
    }
}
