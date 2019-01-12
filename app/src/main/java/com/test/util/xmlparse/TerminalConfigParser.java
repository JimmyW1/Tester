package com.test.util.xmlparse;

import android.os.Environment;
import android.util.Xml;

import com.test.util.xmlparse.beans.Terminal;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TerminalConfigParser implements ConfigParser<Terminal>{
    private final String fileName = "/xml/Terminal.xml";
    private final String beanTAG = "Terminal";

    public TerminalConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<Terminal> getBeanList() {
        return new ArrayList<Terminal>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public Terminal createBean() {
        return new Terminal();
    }

    @Override
    public List<String> getBeanAttributes() {
        return null;
    }

    private int parseIntValue(String valueStr) {
        int value = 0;
        try {
            value = Integer.parseInt(valueStr);
        } catch (Exception e) {
            e.printStackTrace();
            value = 0;
        }

        return value;
    }

    private boolean parseBooleanValue(String valueBoolean) {
        boolean value = false;

        try {
            value = Boolean.parseBoolean(valueBoolean);
        } catch (Exception e) {
            value = false;
        }

        return value;
    }

    @Override
    public void setBeanData(Terminal bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "VERDOR_ID":
                    bean.setVendorID(value);
                    break;
                case "SUB_VERDOR_ID":
                    bean.setSubVendorID(value);
                    break;
                case "CONNECT_TIMEOUT":
                    bean.setCommTimeout(parseIntValue(value));
                    break;
                case "CONNECT_RETRY_TIMES":
                    bean.setCommRetryTimes(parseIntValue(value));
                    break;
                case "TRACE_NUMBER":
                    bean.setSysTrace(value);
                    break;
                case "OPERATION_TIMEOUT":
                    bean.setOperatorTimeout(parseIntValue(value));
                    break;
                case "IsSupportFallback":
                    bean.setSupportFallback(parseBooleanValue(value));
                    break;
                case "IsTipAllowed":
                    bean.setSupportTip(parseBooleanValue(value));
                    break;
                case "IsSupportRefundVoid":
                    bean.setSupRefundVoid(parseBooleanValue(value));
                    break;
                case "IsSupportUpiRefundVoid":
                    bean.setSupUpiRefundVoid(parseBooleanValue(value));
                    break;
                case "IsSupportPreAuthCompleteRefundVoid":
                    bean.setSupPreAuthCompleteRefundVoid(parseBooleanValue(value));
                    break;
                case "IsScbRefundUseEmvFlowEnabled":
                    bean.setScbRefundSupFullEmv(parseBooleanValue(value));
                    break;
                case "IsUnlockForceSettlementEnabled":
                    bean.setUnlockForceSettlement(parseBooleanValue(value));
                    break;
                case "IsTrainingModeEnabled":
                    bean.setTrainingMode(parseBooleanValue(value));
                    break;
                case "IsIsoLogOnEnabled":
                    bean.setIsoLogOn(parseBooleanValue(value));
                    break;
                case "AUTO_INQUIRY_TIMES":
                    bean.setAutoInquiryTimes(parseIntValue(value));
                    break;
                case "IsAutoSettlemntEnabled":
                    bean.setAutoSettlemnt(parseBooleanValue(value));
                    break;
                case "AUTO_SETTLEMENT_DATE":
                    bean.setAutoSettlementTime(value);
                    break;
                case "AUTO_SETTLEMENT_FAILED_RETRY_TIME_MINS":
                    bean.setAutoSettlementFailedRetryTimeMins(value);
                    break;
                case "IsForceSettlementEnabled":
                    bean.setForceSettlement(parseBooleanValue(value));
                    break;
                case "FORCE_SETTLEMENT_TIME":
                    bean.setForceSettlementTime(value);
                    break;
                case "QR_REACH_INTERVAL_TIME":
                    bean.setIntervalTime(parseIntValue(value));
                    break;
                case "QR_INQUIRY_TIME":
                    bean.setInquiryTime(parseIntValue(value));
                    break;
                case "QR_MANUAL_INQUIRY_TIME":
                    bean.setManualInquiryTime(parseIntValue(value));
                    break;
                case "ReceiptHeaderLine1":
                    bean.setReceiptHeadLine1(value);
                    break;
                case "ReceiptHeaderLine2":
                    bean.setReceiptHeadLine2(value);
                    break;
                case "ReceiptHeaderLine3":
                    bean.setReceiptHeadLine3(value);
                    break;
                case "ADMIN_DEFAULT_PWD":
                    bean.setAdminDefaultPwd(value);
                    break;
                case "TRANS_MANAGE_PWD":
                    bean.setTransManagePwd(value);
                    break;
                case "IsSkipUserLogin":
                    bean.setSkipUserLogin(parseBooleanValue(value));
                    break;
                case "IsSupportSaleInputCardNum":
                    bean.setSupportSaleInputCardNum(parseBooleanValue(value));
                    break;
                case "IsRefundNeedInputRefNum":
                    bean.setRefundNeedInputRefNum(parseBooleanValue(value));
                    break;
                case "IsRefundNeedInputApprovalCode":
                    bean.setRefundNeedInputApprovalCode(parseBooleanValue(value));
                    break;
                case "IsSupportTCUpload":
                    bean.setSupportTCUpload(parseBooleanValue(value));
                    break;
                case "IsDCCAllowed":
                    bean.setDCCAllowed(parseBooleanValue(value));
                    break;
            }
        }
    }

    @Override
    public void doFinishProcess(List<Terminal> beanList, XmlSerializer xmlSerializer) {
        if (beanList.size() <= 0) {
            return;
        }

        Iterator<Terminal> iterator = beanList.iterator();
        while (iterator.hasNext()) {
            Terminal terminal = iterator.next();
            terminal.toTmsXml(xmlSerializer, terminal, "");
        }
    }
}
