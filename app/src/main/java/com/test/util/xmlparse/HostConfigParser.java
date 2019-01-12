package com.test.util.xmlparse;

import android.os.Environment;


import com.test.util.xmlparse.beans.Host;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HostConfigParser implements ConfigParser<Host> {
    private final String fileName = "/xml/Hosts.xml";
    private final String beanTAG = "Host";

    public HostConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<Host> getBeanList() {
        return new ArrayList<Host>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public Host createBean() {
        return new Host();
    }

    @Override
    public List<String> getBeanAttributes() {
        List<String> beanAttrs = new ArrayList<>();
        beanAttrs.add("Index");
        return beanAttrs;
    }

    @Override
    public void setBeanData(Host bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "Index":
                    bean.setIndex(value);
                    break;
                case "HOST_NAME":
                    bean.setHostName(value);
                    break;
                case "MERCHANT_ID":
                    bean.setMerchantId(value);
                    break;
                case "TERMINAL_ID":
                    bean.setTerminalId(value);
                    break;
                case "BATCH_NUMBER":
                    bean.setBatchNum(value);
                    break;
                case "STAN": //6
                    bean.setTraceNum(value);
                    break;
                case "NII":
                    bean.setNII(value);
                    break;
                case "TLE_EDC_NII":
                    bean.setTleEdcNII(value);
                    break;
                case "TLE_KMS_NII":
                    bean.setTleKmsNII(value);
                    break;
                case "PRIMARY_HOST_IP":
                    bean.setPrimaryIP(value);
                    break;
                case "PRIMARY_HOST_PORT":
                    bean.setPrimaryPort(value);
                    break;
                case "SECOND_HOST_IP":
                    bean.setSecondaryIP(value);
                    break;
                case "SECOND_HOST_PORT": //11
                    bean.setSecondaryPort(value);
                    break;
                case "LTMK_ACQUIRER_ID":
                    bean.setLTMKAcquirerID(value);
                    break;
                case "ACQUIRER_ID":
                    bean.setAcquirerID(value);
                    break;
                case "TE_ID":
                    bean.setTE_ID(value);
                    break;
                case "TE_PIN": //16
                    bean.setTE_PIN(value);
                    break;
                case "QR_HOST_SERVER":
                    bean.setQrHostServer(value);
                    break;
                case "IsTLEAllowed":
                    bean.setIsTLEAllowed(parseBooleanValue(value));
                    break;
                case "QR_HOST_GET_TMK":
                    bean.setQrHostGetTmk(value);
                    break;
                case "QR_HOST_GET_TWK":
                    bean.setQrHostGetTwk(value);
                    break;
                case "QR_HOST_NORMAL":
                    bean.setQrHostNormal(value);
                    break;
                case "QR_VENDOR_ID":
                    bean.setQrVendorId(value);
                    break;
                case "QR_SUB_VENDOR_ID":
                    bean.setQrSubVendorId(value);
                    break;
                case "QR_ACQUIRER":
                    bean.setQrAcquirer(value);
                    break;
                case "QR_MERCHANT_NAME":
                    bean.setQrMerchantName(value);
                    break;
                case "QR_DEFAULT_SECRET_KEY":
                    bean.setQrDefaultSecretkey(value);
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
    public void doFinishProcess(List<Host> beanList, XmlSerializer xmlSerializer) {
        if (beanList.size() <= 0) {
            return;
        }

        Iterator<Host> iterator = beanList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Host host = iterator.next();
            host.toTmsXml(xmlSerializer, host, "" + i);
        }
    }
}
