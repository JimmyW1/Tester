package com.test.util.xmlparse.beans;


import com.test.util.LogUtil;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class EmvKey {
    String keyIndex = "";
    String rid = "";
    String exponent = "";
    String keyLen = "";
    String key = "";
    String hash = "";
    String hashAlgoIndicator = "";
    String pkAlgoIndicator = "";
    String expiryDate = "";

    public void toTmsXml(XmlSerializer xmlSerializer, EmvKey emvKey, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "EMV_KEY";

        writeParameter(xml, index, tn, "KEY_INDEX", "" + emvKey.keyIndex);
        writeParameter(xml, index, tn, "RID", "" + emvKey.rid);
        writeParameter(xml, index, tn, "EXPONENT", "" + emvKey.exponent);
        writeParameter(xml, index, tn, "KEY_LEN", "" + emvKey.keyLen);
        writeParameter(xml, index, tn, "KEY", "" + emvKey.key);
        writeParameter(xml, index, tn, "HASH", "" + emvKey.hash);
        writeParameter(xml, index, tn, "HASH_ALGO_INDICATOR", "" + emvKey.hashAlgoIndicator);
        writeParameter(xml, index, tn, "PK_ALGO_INDICATOR", "" + emvKey.pkAlgoIndicator);
        writeParameter(xml, index, tn, "EXPIRY_DATE", "" + emvKey.expiryDate);
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

    public String getKeyIndex() {
        return keyIndex;
    }

    public String getRid() {
        return rid;
    }

    public String getExponent() {
        return exponent;
    }

    public String getKeyLen() {
        return keyLen;
    }

    public String getKey() {
        return key;
    }

    public String getHash() {
        return hash;
    }

    public String getHashAlgoIndicator() {
        return hashAlgoIndicator;
    }

    public String getPkAlgoIndicator() {
        return pkAlgoIndicator;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public void setKeyLen(String keyLen) {
        this.keyLen = keyLen;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setHashAlgoIndicator(String hashAlgoIndicator) {
        this.hashAlgoIndicator = hashAlgoIndicator;
    }

    public void setPkAlgoIndicator(String pkAlgoIndicator) {
        this.pkAlgoIndicator = pkAlgoIndicator;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
