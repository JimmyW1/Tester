package com.test.util.xmlparse.beans;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Card {
    String index = "";
    String cardRid = "";
    String cardType = "";
    String cardTypeName = "";
    String panRangeLow = "";
    String panRangeHigh = "";
    String issuerNumber = "";
    String acquierNumber = "";
    String hostIndex = "";


    public void toTmsXml(XmlSerializer xmlSerializer, Card card, String index) {
        final XmlSerializer xml = xmlSerializer;
        final String tn = "CARD_ISSUER";
//        writeParameter(xml, index, tn, "", "" + card.index);
        writeParameter(xml, index, tn, "RID", "" + card.cardRid);
        writeParameter(xml, index, tn, "ISSUER_TYPE", "" + card.cardType);
        writeParameter(xml, index, tn, "CARD_NAME", "" + card.cardTypeName);
        writeParameter(xml, index, tn, "RANGE_LOW", "" + card.panRangeLow);
        writeParameter(xml, index, tn, "RANGE_HIGH", "" + card.panRangeHigh);
//        writeParameter(xml, index, tn, "", "" + card.issuerNumber);
//        writeParameter(xml, index, tn, "", "" + card.acquierNumber);
        writeParameter(xml, index, tn, "HOST_TYPE", "" + card.hostIndex);
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

    public String getIndex() {
        return index;
    }

    public String getCardRid() {
        return cardRid;
    }

    public String getCardType() {
        return cardType;
    }

    public String getPanRangeLow() {
        return panRangeLow;
    }

    public String getPanRangeHigh() {
        return panRangeHigh;
    }

    public String getIssuerNumber() {
        return issuerNumber;
    }

    public String getAcquierNumber() {
        return acquierNumber;
    }

    public String getHostIndex() {
        return hostIndex;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setCardRid(String cardRid) {
        this.cardRid = cardRid;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setPanRangeLow(String panRangeLow) {
        this.panRangeLow = panRangeLow;
    }

    public void setPanRangeHigh(String panRangeHigh) {
        this.panRangeHigh = panRangeHigh;
    }

    public void setIssuerNumber(String issuerNumber) {
        this.issuerNumber = issuerNumber;
    }

    public void setAcquierNumber(String acquierNumber) {
        this.acquierNumber = acquierNumber;
    }

    public void setHostIndex(String hostIndex) {
        this.hostIndex = hostIndex;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }
}
