package com.test.util.xmlparse;

import android.os.Environment;

import com.test.util.xmlparse.beans.EmvApplication;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmvApplicationsConfigParser implements ConfigParser<EmvApplication>{
    private final String fileName = "/xml/EMV_Applications.xml";
    private final String beanTAG = "Application";

    public EmvApplicationsConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<EmvApplication> getBeanList() {
        return new ArrayList<EmvApplication>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public EmvApplication createBean() {
        return new EmvApplication();
    }

    @Override
    public List<String> getBeanAttributes() {
        List<String> beanAttrs = new ArrayList<>();
        beanAttrs.add("AID");
        return beanAttrs;
    }

    @Override
    public void setBeanData(EmvApplication bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "AID":
                    bean.setAid(value);
                    break;
                case "VerNum":
                    bean.setVerNum(value);
                    break;
                case "AppName":
                    bean.setAppName(value);
                    break;
                case "ASI":
                    bean.setAsi(value);
                    break;
                case "FloorLimit":
                    bean.setFloorLimit(value);
                    break;
                case "Threshold":
                    bean.setThreshold(value);
                    break;
                case "TargetPercentage":
                    bean.setTargetPercentage(value);
                    break;
                case "MaxTargetPercentage":
                    bean.setMaxTargetPercentage(value);
                    break;
                case "FloorLimit_Intl":
                    bean.setFloorLimit_Intl(value);
                    break;
                case "Threshold_Intl":
                    bean.setThreshold_Intl(value);
                    break;
                case "TargetPercentage_Intl":
                    bean.setTargetPercentage_Intl(value);
                    break;
                case "MaxTargetPercentage_Intl":
                    bean.setMaxTargetPercentage_Intl(value);
                    break;
                case "TAC_Denial":
                    bean.setTac_Denial(value);
                    break;
                case "TAC_Online":
                    bean.setTac_Online(value);
                    break;
                case "TAC_Default":
                    bean.setTac_Default(value);
                    break;
                case "EMV_Application":
                    bean.setEmv_Application(value);
                    break;
                case "DefaultTDOL":
                    bean.setDefaultTDOL(value);
                    break;
                case "DefaultDDOL":
                    bean.setDefaultDDOL(value);
                    break;
                case "MerchIdent":
                    bean.setMerchIdent(value);
                    break;
                case "CountryCodeTerm":
                    bean.setCountryCodeTerm(value);
                    break;
                case "CurrencyCode":
                    bean.setCurrencyCode(value);
                    break;
                case "AppTerminalType":
                    bean.setAppTerminalType(value);
                    break;
                case "AppTermCap":
                    bean.setAppTermCap(value);
                    break;
                case "AppTermAddCap":
                    bean.setAppTermAddCap(value);
                    break;
                case "ECTransLimit":
                    bean.setEcTransLimit(value);
                    break;
                case "CTLS_TAC_Denial":
                    bean.setCtls_TAC_Denial(value);
                    break;
                case "CTLS_TAC_Online":
                    bean.setCtls_TAC_Online(value);
                    break;
                case "CTLS_TAC_Default":
                    bean.setCtls_TAC_Default(value);
                    break;
                case "CTLSTransLimit":
                    bean.setCtlsTransLimit(value);
                    break;
                case "CTLSFloorLimit":
                    bean.setCtlsFloorLimit(value);
                    break;
                case "CTLSCVMLimit":
                    bean.setCtlsCVMLimit(value);
                    break;
                case "TerminalTransctionQualifiers":
                    bean.setTerminalTransactionQualifiers(value);
                    break;
                case "MasterAID":
                    break;
            }
        }
    }

    @Override
    public void doFinishProcess(List<EmvApplication> beanList, XmlSerializer xmlSerializer) {
        Iterator<EmvApplication> iterator = beanList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            EmvApplication emvApplication = iterator.next();
            if (emvApplication != null) {
                i++;
                emvApplication.toTmsXml(xmlSerializer, emvApplication, "" + i);
            }
        }
    }
}
