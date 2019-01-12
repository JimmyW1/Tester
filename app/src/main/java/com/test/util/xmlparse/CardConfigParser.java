package com.test.util.xmlparse;

import android.os.Environment;

import com.test.util.xmlparse.beans.Card;
import com.test.util.xmlparse.beans.Host;
import com.test.util.xmlparse.beans.HostType;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CardConfigParser implements ConfigParser<Card> {
    private final String fileName = "/xml/Cards.xml";
    private final String beanTAG = "Card";

    public CardConfigParser() {
    }

    @Override
    public File getConfigFilePath() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        return new File(sdcardPath + fileName);
    }

    @Override
    public List<Card> getBeanList() {
        return new ArrayList<>();
    }

    @Override
    public String getBeanTag() {
        return beanTAG;
    }

    @Override
    public Card createBean() {
        return new Card();
    }

    @Override
    public List<String> getBeanAttributes() {
        List<String> beanAttrs = new ArrayList<>();
        beanAttrs.add("Index");
        return beanAttrs;
    }

    @Override
    public void setBeanData(Card bean, String beanTagName, String value) {
        if (bean != null && beanTagName != null && value != null) {
            switch (beanTagName) {
                case "Index":
                    bean.setIndex(value);
                    break;
                case "CARD_NAME":
                    bean.setCardTypeName(value);
                    break;
                case "CARD_RID":
                    bean.setCardRid(value);
                    break;
                case "CARD_TYPE":
                    bean.setCardType(value);
                    break;
                case "PAN_RANGE_LOW":
                    bean.setPanRangeLow(value);
                    break;
                case "PAN_RANGE_HIGH":
                    bean.setPanRangeHigh(value);
                    break;
                case "ISSUER_NUMBER":
                    bean.setIssuerNumber(value);
                    break;
                case "ACQUIRER_NUMBER":
                    bean.setAcquierNumber(value);
                    break;
                case "HOST_INDEX":
                    bean.setHostIndex(value);
                    break;
            }
        }
    }

    @Override
    public void doFinishProcess(List<Card> beanList, XmlSerializer xmlSerializer) {
        if (beanList.size() <= 0) {
            return;
        }
        XmlParser xmlParser = new XmlParser();
        HostConfigParser hostConfigParser = new HostConfigParser();
        xmlParser.parserConfigFile(hostConfigParser, xmlSerializer);
        List<Host> hosts = xmlParser.getBeanList();
        if (hosts.size() <= 0) {
            return;
        }

        Iterator<Card> iterator = beanList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Card card = iterator.next();
            int hostType = getHostType(hosts, card.getHostIndex());
            card.setHostIndex("" + hostType);

            card.toTmsXml(xmlSerializer, card, "" + i);
        }
    }

    private int getHostType(List<Host> hosts, String hostIndex) {
        Iterator<Host> iterator = hosts.iterator();
        while (iterator.hasNext()) {
            Host host = iterator.next();
            if (host.getIndex().equals(hostIndex)) {
                return host.getHostType();
            }
        }

        return HostType.UNKOWN;
    }
}
