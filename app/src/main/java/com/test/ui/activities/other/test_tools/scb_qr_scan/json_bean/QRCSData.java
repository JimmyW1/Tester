package com.test.ui.activities.other.test_tools.scb_qr_scan.json_bean;

public class QRCSData extends BaseData {
    private String amount;
    private String transid;
    private String merchantpan;
    private String authorizecode;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getMerchantpan() {
        return merchantpan;
    }

    public void setMerchantpan(String merchantpan) {
        this.merchantpan = merchantpan;
    }

    public String getAuthorizecode() {
        return authorizecode;
    }

    public void setAuthorizecode(String authorizecode) {
        this.authorizecode = authorizecode;
    }
}
