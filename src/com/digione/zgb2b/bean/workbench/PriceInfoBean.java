package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 振华
 * Date: 13-4-19
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
public class PriceInfoBean implements Serializable {
    private static final long serialVersionUID = -1114442349954227341L;
    private Double totalPrice;
    private Double transCost;
    private Double totalAfterserPrice;
    private Double actPrice;
    private Double sendTotalAmount;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTransCost() {
        return transCost;
    }

    public void setTransCost(Double transCost) {
        this.transCost = transCost;
    }

    public Double getTotalAfterserPrice() {
        return totalAfterserPrice;
    }

    public void setTotalAfterserPrice(Double totalAfterserPrice) {
        this.totalAfterserPrice = totalAfterserPrice;
    }

    public Double getActPrice() {
        return actPrice;
    }

    public void setActPrice(Double actPrice) {
        this.actPrice = actPrice;
    }

    public Double getSendTotalAmount() {
        return sendTotalAmount;
    }

    public void setSendTotalAmount(Double sendTotalAmount) {
        this.sendTotalAmount = sendTotalAmount;
    }
}
