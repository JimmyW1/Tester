package com.test.util.xmlparse;

import android.os.Environment;
import android.util.Xml;

import com.test.util.LogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class XmlParser<T> {
    List<T> beanList = null;

    public XmlParser() {

    }

    public List<T> getBeanList() {
        return beanList;
    }

    public boolean parserConfigFile(ConfigParser<T> configParser, XmlSerializer tmsOutSerializer) {
        beanList = null;
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            File file = configParser.getConfigFilePath();
            if (file == null) {
                return false;
            }
            LogUtil.d("TAG", "File path=" + file.getPath());
            InputStream inputStream = new FileInputStream(file);
            xmlPullParser.setInput(inputStream, "UTF-8");

            beanList = configParser.getBeanList();
            if (beanList == null) {
                return false;
            }

            int type = xmlPullParser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                if (type == XmlPullParser.START_TAG && xmlPullParser.getName().equals(configParser.getBeanTag())) {
                    LogUtil.d("TAG", "tagName=" + xmlPullParser.getName());
                    T bean = configParser.createBean();
                    beanList.add(bean);
                    List<String> beanAttributes = configParser.getBeanAttributes();
                    if (beanAttributes != null && bean != null) {
                        for (String beanAttr : beanAttributes) {
                            String value = getAttrValue(xmlPullParser, beanAttr);
                            LogUtil.d("TAG", "beanAttr=" + beanAttr + " value=" + value);
                            configParser.setBeanData(bean, beanAttr, "" + value);
                        }
                    }

                    type = xmlPullParser.nextTag();
                    LogUtil.d("TAG", "type=" + type);
                    String tagName = xmlPullParser.getName();
                    LogUtil.d("TAG", "tagName=" + tagName);
                    while (!(type == XmlPullParser.END_TAG && tagName.equals("" + configParser.getBeanTag()))) {
                        if (type == XmlPullParser.START_TAG) {
                            String value = getNextText(xmlPullParser);
                            LogUtil.d("TAG", "tagName=" + tagName + " Text=" + value);
                            configParser.setBeanData(bean, tagName, "" + value);
                        }
                        type = xmlPullParser.nextTag();
                        tagName = xmlPullParser.getName();
                    }
                }
                type = xmlPullParser.next();
                LogUtil.d("TAG", "Type=" + type + " tagName=" + xmlPullParser.getName());
            }

            LogUtil.d("TAG", "Parser xml finished. beanList size=" + beanList.size());
            if (beanList.size() > 0) {
                configParser.doFinishProcess(beanList, tmsOutSerializer);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getAttrValue(XmlPullParser xmlPullParser, String beanAttr) {
        String value = "";
        if (xmlPullParser != null) {
            try {
                value = xmlPullParser.getAttributeValue(null, beanAttr);
            } catch (Exception e) {
                e.printStackTrace();
                value = "";
            }
        }

        return value;
    }

    private String getNextText(XmlPullParser xmlPullParser) {
        String value = "";

        if (xmlPullParser != null) {
            try {
                value = xmlPullParser.nextText();
            } catch (Exception e) {
                e.printStackTrace();
                value = "";
            }
        }

        return value;
    }
}
