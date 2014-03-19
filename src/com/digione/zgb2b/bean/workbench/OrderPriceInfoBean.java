package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 振华
 * Date: 13-4-19
 * Time: 下午11:13
 * To change this template use File | Settings | File Templates.
 */
public class OrderPriceInfoBean implements Serializable {
    private static final long serialVersionUID = -2434185192527352070L;
    private Double totalProductPrice;
    private Double totalTransCost;
    private Double afterSalePrice;
    private Double totalActPrice;
    private String isShowTax;
    private Double totalTax;
    private Integer totalTaxPercent;
    private Double totalOrderAmount;
    private Double needPayAmount;
    private Integer totalOrderActWebIntegral;
    private Integer totalOrderActSalesIntegral;
    private Integer totalOrderAchievedWebIntegral;
    private Integer totalOrderAchievedSalesIntegral;

    public Double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(Double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public Double getTotalTransCost() {
        return totalTransCost;
    }

    public void setTotalTransCost(Double totalTransCost) {
        this.totalTransCost = totalTransCost;
    }

    public Double getAfterSalePrice() {
        return afterSalePrice;
    }

    public void setAfterSalePrice(Double afterSalePrice) {
        this.afterSalePrice = afterSalePrice;
    }

    public Double getTotalActPrice() {
        return totalActPrice;
    }

    public void setTotalActPrice(Double totalActPrice) {
        this.totalActPrice = totalActPrice;
    }

    public String getIsShowTax() {
        return isShowTax;
    }

    public void setIsShowTax(String isShowTax) {
        this.isShowTax = isShowTax;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Integer getTotalTaxPercent() {
        return totalTaxPercent;
    }

    public void setTotalTaxPercent(Integer totalTaxPercent) {
        this.totalTaxPercent = totalTaxPercent;
    }

    public Double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(Double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Double getNeedPayAmount() {
        return needPayAmount;
    }

    public void setNeedPayAmount(Double needPayAmount) {
        this.needPayAmount = needPayAmount;
    }

    public Integer getTotalOrderActWebIntegral() {
        return totalOrderActWebIntegral;
    }

    public void setTotalOrderActWebIntegral(Integer totalOrderActWebIntegral) {
        this.totalOrderActWebIntegral = totalOrderActWebIntegral;
    }

    public Integer getTotalOrderActSalesIntegral() {
        return totalOrderActSalesIntegral;
    }

    public void setTotalOrderActSalesIntegral(Integer totalOrderActSalesIntegral) {
        this.totalOrderActSalesIntegral = totalOrderActSalesIntegral;
    }

    public Integer getTotalOrderAchievedWebIntegral() {
        return totalOrderAchievedWebIntegral;
    }

    public void setTotalOrderAchievedWebIntegral(Integer totalOrderAchievedWebIntegral) {
        this.totalOrderAchievedWebIntegral = totalOrderAchievedWebIntegral;
    }

    public Integer getTotalOrderAchievedSalesIntegral() {
        return totalOrderAchievedSalesIntegral;
    }

    public void setTotalOrderAchievedSalesIntegral(Integer totalOrderAchievedSalesIntegral) {
        this.totalOrderAchievedSalesIntegral = totalOrderAchievedSalesIntegral;
    }
}
