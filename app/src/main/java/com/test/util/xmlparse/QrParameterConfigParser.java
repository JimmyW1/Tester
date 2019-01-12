package com.test.util.xmlparse;

import android.os.Environment;

import com.test.util.xmlparse.beans.QrParameter;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QrParameterConfigParser implements ConfigParser<QrParameter> {
    private final String fileName = "/xml/QrParameter.xml";
    private final String beanTAG = "QrParameter";


    public QrParameterConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<QrParameter> getBeanList() {
        return new ArrayList<QrParameter>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public QrParameter createBean() {
        return new QrParameter();
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

    @Override
    public void setBeanData(QrParameter bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "QR_AUTO_INQUIRY_TIMES":
                    bean.setQrAutoInquiryTimes(parseIntValue(value));
                    break;
                case "QR_INTERVAL_TIME":
                    bean.setQrIntervalTime(parseIntValue(value));
                    break;
                case "QR_INQUIRY_TIME":
                    bean.setQrInquiryTime(parseIntValue(value));
                    break;
                case "QR_MANUAL_INQUIRY_TIME":
                    bean.setQrManualInquiryTime(parseIntValue(value));
                    break;
                case "ALIWECH_BSCANC_AUTO_INQUIRY_TIMES":
                    bean.setAliWeAutoInquiryTimes(parseIntValue(value));
                    break;
                case "ALIWECH_BSCANC_INQUIRY_TIME":
                    bean.setAliWeInquiryTime(parseIntValue(value));
                    break;
                case "QR_API_VERSION":
                    bean.setQrApiVersion(value);
                    break;
                case "QR_FORCE_PASSWORD":
                    bean.setQrForcePassword(value);
                    break;
                case "QR_BILLERID_ONLY_REF1":
                    bean.setQrBillerIdOnlyRef1(value);
                    break;
                case "QR_BILLERID_WITH_REF2":
                    bean.setQrBillerIdWithRef2(value);
                    break;
                case "QR_SUPPORT_REF2":
                    bean.setQrSupportRef2(parseBooleanValue(value));
                    break;
                case "QR_MERCHANT_AID":
                    bean.setQrMerchantAID(value);
                    break;
                case "QR_SWITCH_B_SCAN_C":
                    bean.setQrSwitchBscanC(parseBooleanValue(value));
                    break;
                case "QR_SWITCH_C_SCAN_B":
                    bean.setQrSwitchCscanB(parseBooleanValue(value));
                    break;
                case "QR_MANUAL_INPUT_REF2":
                    bean.setQrManualInputRef2(parseBooleanValue(value));
                    break;
                case "ALIPAY_SWITCH_B_SCAN_C":
                    bean.setQrAliSwitchBscanC(parseBooleanValue(value));
                    break;
                case "ALIPAY_SWITCH_C_SCAN_B":
                    bean.setQrAliSwitchCscanB(parseBooleanValue(value));
                    break;
                case "WECHAT_SWITCH_B_SCAN_C":
                    bean.setQrWeChSwitchBscanC(parseBooleanValue(value));
                    break;
                case "WECHAT_SWITCH_C_SCAN_B":
                    bean.setQrWeChSwitchCscanB(parseBooleanValue(value));
                    break;
                case "QR_DEFAULT_REF2":
                    bean.setQrDefaultRef2(value);
                    break;
            }
        }
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
    public void doFinishProcess(List<QrParameter> beanList, XmlSerializer xmlSerializer) {
        if (beanList.size() <= 0) {
            return;
        }

        Iterator<QrParameter> iterator = beanList.iterator();

        while (iterator.hasNext()) {
            QrParameter qrParameter = iterator.next();
            qrParameter.toTmsXml(xmlSerializer, qrParameter, "");
        }
    }
}
