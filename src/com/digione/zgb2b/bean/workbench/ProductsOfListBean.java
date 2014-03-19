package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: 振华 Date: 13-4-20 Time: 下午1:11
 */
public class ProductsOfListBean implements Serializable {
	private static final long serialVersionUID = 7191047318429445466L;
	private Integer productId;
	private Integer dictId1;
	private String productDetailUrl;
	private String picPath5Url;
	private String picPath1Url;
	private String brandName;
	private String pattern;
	private String colorSpec;
	private Integer quantity;
	private Double currentPrice;
	private Double totalPrice;
	private Integer isAfterser;
	private Double afterserPrice;
	private Double actPrice;

    /**
     * 是否政企版   1：政企版   0：不是
     */
    private Integer isGovEnterprise;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getDictId1() {
		return dictId1;
	}

	public void setDictId1(Integer dictId1) {
		this.dictId1 = dictId1;
	}

	public String getProductDetailUrl() {
		return productDetailUrl;
	}

	public void setProductDetailUrl(String productDetailUrl) {
		this.productDetailUrl = productDetailUrl;
	}

	public String getPicPath5Url() {
		return picPath5Url;
	}

	public void setPicPath5Url(String picPath5Url) {
		this.picPath5Url = picPath5Url;
	}

	public String getPicPath1Url() {
		return picPath1Url;
	}

	public void setPicPath1Url(String picPath1Url) {
		this.picPath1Url = picPath1Url;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getColorSpec() {
		return colorSpec;
	}

	public void setColorSpec(String colorSpec) {
		this.colorSpec = colorSpec;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getAfterser() {
		return isAfterser;
	}

	public void setAfterser(Integer afterser) {
		isAfterser = afterser;
	}

	public Double getAfterserPrice() {
		return afterserPrice;
	}

	public void setAfterserPrice(Double afterserPrice) {
		this.afterserPrice = afterserPrice;
	}

	public Double getActPrice() {
		return actPrice;
	}

	public void setActPrice(Double actPrice) {
		this.actPrice = actPrice;
	}

    public Integer getIsGovEnterprise() {
        return isGovEnterprise;
    }

    public void setIsGovEnterprise(Integer isGovEnterprise) {
        this.isGovEnterprise = isGovEnterprise;
    }
}
