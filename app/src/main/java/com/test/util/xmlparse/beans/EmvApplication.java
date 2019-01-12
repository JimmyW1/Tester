package com.test.util.xmlparse.beans;

import com.test.util.LogUtil;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class EmvApplication {
    String aid = "";
    String verNum = "";
    String appName = "";
    String asi = "";
    String floorLimit = "";
    String threshold = "";
    String targetPercentage = "";
    String maxTargetPercentage = "";
    String floorLimit_Intl = "";
    String threshold_Intl = "";
    String targetPercentage_Intl = "";
    String maxTargetPercentage_Intl = "";
    String tac_Denial = "";
    String tac_Online = "";
    String tac_Default = "";
    String emv_Application = "";
    String defaultTDOL = "";
    String defaultDDOL = "";
    String merchIdent = "";
    String countryCodeTerm = "";
    String currencyCode = "";
    String appTerminalType = "";
    String appTermCap = "";
    String appTermAddCap = "";
    String ecTransLimit = "";
    String ctls_TAC_Denial = "";
    String ctls_TAC_Online = "";
    String ctls_TAC_Default = "";
    String ctlsTransLimit = "";
    String ctlsFloorLimit = "";
    String ctlsCVMLimit = "";
    String masterAID = "";
    String terminalTransactionQualifiers = "";

    public void toTmsXml(XmlSerializer xmlSerializer, EmvApplication emvApp, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "EMV_APPLICATION";

        writeParameter(xml, index, tn, "AID", "" + emvApp.aid);
        writeParameter(xml, index, tn, "VER_NO", "" + emvApp.verNum);
        writeParameter(xml, index, tn, "APP_NAME", "" + emvApp.appName);
        writeParameter(xml, index, tn, "ASI", "" + emvApp.asi);
        writeParameter(xml, index, tn, "FLOOR_LIMIT", "" + emvApp.floorLimit);
        writeParameter(xml, index, tn, "THRESHOLD", "" + emvApp.threshold);
        writeParameter(xml, index, tn, "TARGET_PERCENTAGE", "" + emvApp.targetPercentage);
        writeParameter(xml, index, tn, "MAX_TARGET_PERCENTAGE", "" + emvApp.maxTargetPercentage);
//        writeParameter(xml, index, tn, "", "" + emvApp.floorLimit_Intl);
//        writeParameter(xml, index, tn, "", "" + emvApp.threshold_Intl);
//        writeParameter(xml, index, tn, "", "" + emvApp.targetPercentage_Intl);
//        writeParameter(xml, index, tn, "", "" + emvApp.maxTargetPercentage_Intl);
        writeParameter(xml, index, tn, "TAC_DEINAL", "" + emvApp.tac_Denial);
        writeParameter(xml, index, tn, "TAC_ONLINE", "" + emvApp.tac_Online);
        writeParameter(xml, index, tn, "TAC_DEFAULT", "" + emvApp.tac_Default);
        writeParameter(xml, index, tn, "EMV_APPLICATION", "" + emvApp.emv_Application);
        writeParameter(xml, index, tn, "DEFAULT_TDOL", "" + emvApp.defaultTDOL);
        writeParameter(xml, index, tn, "DEFAULT_DDOL", "" + emvApp.defaultDDOL);
        writeParameter(xml, index, tn, "MERCH_IDENT", "" + emvApp.merchIdent);
        writeParameter(xml, index, tn, "COUNTRY_CODE_TERM", "" + emvApp.countryCodeTerm);
        writeParameter(xml, index, tn, "CURRENCY_CODE", "" + emvApp.currencyCode);
        writeParameter(xml, index, tn, "APP_TERMINAL_TYPE", "" + emvApp.appTerminalType);
        writeParameter(xml, index, tn, "APP_TERM_CAP", "" + emvApp.appTermCap);
        writeParameter(xml, index, tn, "APP_TERM_ADD_CAP", "" + emvApp.appTermAddCap);
        writeParameter(xml, index, tn, "EC_TRANS_LIMIT", "" + emvApp.ecTransLimit);
//        writeParameter(xml, index, tn, "", "" + emvApp.ctls_TAC_Denial);
//        writeParameter(xml, index, tn, "", "" + emvApp.ctls_TAC_Online);
//        writeParameter(xml, index, tn, "", "" + emvApp.ctls_TAC_Default);
        writeParameter(xml, index, tn, "CTLS_TRANS_LIMIT", "" + emvApp.ctlsTransLimit);
        writeParameter(xml, index, tn, "CTLS_FLOOR_LIMIT", "" + emvApp.ctlsFloorLimit);
        writeParameter(xml, index, tn, "CTLS_CVM_LIMIT", "" + emvApp.ctlsCVMLimit);
        writeParameter(xml, index, tn, "MASTER_AID", "" + emvApp.masterAID);
        writeParameter(xml, index, tn, "TTQ", "" + emvApp.terminalTransactionQualifiers);
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

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setVerNum(String verNum) {
        this.verNum = verNum;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAsi(String asi) {
        this.asi = asi;
    }

    public void setFloorLimit(String floorLimit) {
        this.floorLimit = floorLimit;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public void setTargetPercentage(String targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public void setMaxTargetPercentage(String maxTargetPercentage) {
        this.maxTargetPercentage = maxTargetPercentage;
    }

    public void setTac_Denial(String tac_Denial) {
        this.tac_Denial = tac_Denial;
    }

    public void setTac_Online(String tac_Online) {
        this.tac_Online = tac_Online;
    }

    public void setTac_Default(String tac_Default) {
        this.tac_Default = tac_Default;
    }

    public void setEmv_Application(String emv_Application) {
        this.emv_Application = emv_Application;
    }

    public void setDefaultTDOL(String defaultTDOL) {
        this.defaultTDOL = defaultTDOL;
    }

    public void setDefaultDDOL(String defaultDDOL) {
        this.defaultDDOL = defaultDDOL;
    }

    public void setMerchIdent(String merchIdent) {
        this.merchIdent = merchIdent;
    }

    public void setCountryCodeTerm(String countryCodeTerm) {
        this.countryCodeTerm = countryCodeTerm;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setAppTerminalType(String appTerminalType) {
        this.appTerminalType = appTerminalType;
    }

    public void setAppTermCap(String appTermCap) {
        this.appTermCap = appTermCap;
    }

    public void setAppTermAddCap(String appTermAddCap) {
        this.appTermAddCap = appTermAddCap;
    }

    public void setEcTransLimit(String ecTransLimit) {
        this.ecTransLimit = ecTransLimit;
    }

    public void setCtlsTransLimit(String ctlsTransLimit) {
        this.ctlsTransLimit = ctlsTransLimit;
    }

    public void setCtlsFloorLimit(String ctlsFloorLimit) {
        this.ctlsFloorLimit = ctlsFloorLimit;
    }

    public void setCtlsCVMLimit(String ctlsCVMLimit) {
        this.ctlsCVMLimit = ctlsCVMLimit;
    }

    public void setMasterAID(String masterAID) {
        this.masterAID = masterAID;
    }

    public void setFloorLimit_Intl(String floorLimit_Intl) {
        this.floorLimit_Intl = floorLimit_Intl;
    }

    public void setThreshold_Intl(String threshold_Intl) {
        this.threshold_Intl = threshold_Intl;
    }

    public void setTargetPercentage_Intl(String targetPercentage_Intl) {
        this.targetPercentage_Intl = targetPercentage_Intl;
    }

    public void setMaxTargetPercentage_Intl(String maxTargetPercentage_Intl) {
        this.maxTargetPercentage_Intl = maxTargetPercentage_Intl;
    }

    public void setCtls_TAC_Denial(String ctls_TAC_Denial) {
        this.ctls_TAC_Denial = ctls_TAC_Denial;
    }

    public void setCtls_TAC_Online(String ctls_TAC_Online) {
        this.ctls_TAC_Online = ctls_TAC_Online;
    }

    public void setCtls_TAC_Default(String ctls_TAC_Default) {
        this.ctls_TAC_Default = ctls_TAC_Default;
    }

    public String getAid() {
        return aid;
    }

    public String getVerNum() {
        return verNum;
    }

    public String getAppName() {
        return appName;
    }

    public String getAsi() {
        return asi;
    }

    public String getFloorLimit() {
        return floorLimit;
    }

    public String getThreshold() {
        return threshold;
    }

    public String getTargetPercentage() {
        return targetPercentage;
    }

    public String getMaxTargetPercentage() {
        return maxTargetPercentage;
    }

    public String getFloorLimit_Intl() {
        return floorLimit_Intl;
    }

    public String getThreshold_Intl() {
        return threshold_Intl;
    }

    public String getTargetPercentage_Intl() {
        return targetPercentage_Intl;
    }

    public String getMaxTargetPercentage_Intl() {
        return maxTargetPercentage_Intl;
    }

    public String getTac_Denial() {
        return tac_Denial;
    }

    public String getTac_Online() {
        return tac_Online;
    }

    public String getTac_Default() {
        return tac_Default;
    }

    public String getEmv_Application() {
        return emv_Application;
    }

    public String getDefaultTDOL() {
        return defaultTDOL;
    }

    public String getDefaultDDOL() {
        return defaultDDOL;
    }

    public String getMerchIdent() {
        return merchIdent;
    }

    public String getCountryCodeTerm() {
        return countryCodeTerm;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getAppTerminalType() {
        return appTerminalType;
    }

    public String getAppTermCap() {
        return appTermCap;
    }

    public String getAppTermAddCap() {
        return appTermAddCap;
    }

    public String getEcTransLimit() {
        return ecTransLimit;
    }

    public String getCtls_TAC_Denial() {
        return ctls_TAC_Denial;
    }

    public String getCtls_TAC_Online() {
        return ctls_TAC_Online;
    }

    public String getCtls_TAC_Default() {
        return ctls_TAC_Default;
    }

    public String getCtlsTransLimit() {
        return ctlsTransLimit;
    }

    public String getCtlsFloorLimit() {
        return ctlsFloorLimit;
    }

    public String getCtlsCVMLimit() {
        return ctlsCVMLimit;
    }

    public String getMasterAID() {
        return masterAID;
    }

    public String getTerminalTransactionQualifiers() {
        return terminalTransactionQualifiers;
    }

    public void setTerminalTransactionQualifiers(String terminalTransactionQualifiers) {
        this.terminalTransactionQualifiers = terminalTransactionQualifiers;
    }
}
