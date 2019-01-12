package com.test.util.xmlparse;

import android.os.Environment;

import com.test.util.xmlparse.beans.EmvKey;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EmvKeysConfigParser implements ConfigParser<EmvKey>{
    private final String fileName = "/xml/EMV_keys.xml";
    private final String beanTAG = "CapKey";

    public EmvKeysConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<EmvKey> getBeanList() {
        return new ArrayList<EmvKey>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public EmvKey createBean() {
        return new EmvKey();
    }

    @Override
    public List<String> getBeanAttributes() {
        List<String> beanAttrs = new ArrayList<>();
        beanAttrs.add("Index");
        beanAttrs.add("RID");
        return beanAttrs;
    }

    @Override
    public void setBeanData(EmvKey bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "Index":
                    bean.setKeyIndex(value);
                    break;
                case "RID":
                    bean.setRid(value);
                    break;
                case "Exponent":
                    bean.setExponent(value);
                    break;
                case "KeyLen":
                    bean.setKeyLen(value);
                    break;
                case "Key":
                    bean.setKey(value);
                    break;
                case "Hash":
                    bean.setHash(value);
                    break;
                case "HashAlgoIndicator":
                    bean.setHashAlgoIndicator(value);
                    break;
                case "PKAlgoIndicator":
                    bean.setPkAlgoIndicator(value);
                    break;
                case "ExpiryDate":
                    bean.setExpiryDate(value);
                    break;
            }
        }
    }

    @Override
    public void doFinishProcess(List<EmvKey> beanList, XmlSerializer xmlSerializer) {
        Iterator<EmvKey> iterator = beanList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            EmvKey emvKey = iterator.next();
            emvKey.toTmsXml(xmlSerializer, emvKey, "" + i);
        }
    }
}
