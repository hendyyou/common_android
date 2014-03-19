package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 振华
 * Date: 13-4-19
 * Time: 下午11:28
 * To change this template use File | Settings | File Templates.
 */
public class LogisticsInfoBean implements Serializable {
    private static final long serialVersionUID = -1795230240674070036L;
    private String consignee;
    private String consMobile;
    private String consAddress;
    private String logisticsName;
    private String website;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsMobile() {
        return consMobile;
    }

    public void setConsMobile(String consMobile) {
        this.consMobile = consMobile;
    }

    public String getConsAddress() {
        return consAddress;
    }

    public void setConsAddress(String consAddress) {
        this.consAddress = consAddress;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
