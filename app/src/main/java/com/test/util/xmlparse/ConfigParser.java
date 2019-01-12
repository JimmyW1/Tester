package com.test.util.xmlparse;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.List;

public interface ConfigParser<T> {
    File getConfigFilePath();
    List<T> getBeanList();
    String getBeanTag();
    T createBean();
    List<String> getBeanAttributes();
    void setBeanData(T bean, String beanTagName, String value);
    void doFinishProcess(List<T> beanList, XmlSerializer xmlSerializer);
}
