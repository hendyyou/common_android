package com.digione.zgb2b.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * 产品简介实体类
 * 
 * @author zhangqr
 * 
 */
public class BriefInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8151599060249713351L;

	/**
	 * 上市时间标题。为null不显示。
	 */
	private String marketTimeTitle;

	/**
	 * 上市时间。为null不显示。
	 */
	private String marketTime;

	/**
	 * 主要功能标题。为null不显示。
	 */
	private String functionTitle;

	/**
	 * 产品功能和图片集合
	 */
	private List<FunctionAndSopportBean> functionList;

	/**
	 * 支持业务标题。为null不显示。
	 */
	private String businessSupportTitle;

	/**
	 * 支持业务和图片集合
	 */
	private List<FunctionAndSopportBean> businessSupportList;

	/**
	 * 产品属性标题。为null不显示。
	 */
	private String saleSpecTitle;

	/**
	 * 产品属性
	 */
	private String saleSpec;

	/**
	 * 产品定位标题。为null则不显示
	 */
	private String locationTitle;

	/**
	 * 产品定位
	 */
	private String location;

	/**
	 * 标准配置标题。为null不显示。
	 */
	private String configSpecTitle;

	/**
	 * 标准配置。为null不显示。
	 */
	private String configSpec;

	public String getMarketTimeTitle() {
		return marketTimeTitle;
	}

	public void setMarketTimeTitle(String marketTimeTitle) {
		this.marketTimeTitle = marketTimeTitle;
	}

	public String getMarketTime() {
		return marketTime;
	}

	public void setMarketTime(String marketTime) {
		this.marketTime = marketTime;
	}

	public String getFunctionTitle() {
		return functionTitle;
	}

	public void setFunctionTitle(String functionTitle) {
		this.functionTitle = functionTitle;
	}

	public List<FunctionAndSopportBean> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionAndSopportBean> functionList) {
		this.functionList = functionList;
	}

	public String getBusinessSupportTitle() {
		return businessSupportTitle;
	}

	public void setBusinessSupportTitle(String businessSupportTitle) {
		this.businessSupportTitle = businessSupportTitle;
	}

	public List<FunctionAndSopportBean> getBusinessSupportList() {
		return businessSupportList;
	}

	public void setBusinessSupportList(List<FunctionAndSopportBean> businessSupportList) {
		this.businessSupportList = businessSupportList;
	}

	public String getSaleSpecTitle() {
		return saleSpecTitle;
	}

	public void setSaleSpecTitle(String saleSpecTitle) {
		this.saleSpecTitle = saleSpecTitle;
	}

	public String getSaleSpec() {
		return saleSpec;
	}

	public void setSaleSpec(String saleSpec) {
		this.saleSpec = saleSpec;
	}

	public String getLocationTitle() {
		return locationTitle;
	}

	public void setLocationTitle(String locationTitle) {
		this.locationTitle = locationTitle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getConfigSpecTitle() {
		return configSpecTitle;
	}

	public void setConfigSpecTitle(String configSpecTitle) {
		this.configSpecTitle = configSpecTitle;
	}

	public String getConfigSpec() {
		return configSpec;
	}

	public void setConfigSpec(String configSpec) {
		this.configSpec = configSpec;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
