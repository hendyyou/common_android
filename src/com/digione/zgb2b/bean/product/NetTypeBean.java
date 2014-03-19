package com.digione.zgb2b.bean.product;

import java.io.Serializable;

/**
 * 网络制式实体类
 * 
 * @author zhangqr
 * 
 */
public class NetTypeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8185512803532478711L;

	/**
	 * 网络制式ID
	 */
	private Integer mobileNetTypeId;

	/**
	 * 网络制式名称
	 */
	private String mobileNetTypeName;

	/**
	 * 网络制式产品列表URL
	 */
	private String mobileNetTypeSearchUrl;

	public Integer getMobileNetTypeId() {
		return mobileNetTypeId;
	}

	public void setMobileNetTypeId(Integer mobileNetTypeId) {
		this.mobileNetTypeId = mobileNetTypeId;
	}

	public String getMobileNetTypeName() {
		return mobileNetTypeName;
	}

	public void setMobileNetTypeName(String mobileNetTypeName) {
		this.mobileNetTypeName = mobileNetTypeName;
	}

	public String getMobileNetTypeSearchUrl() {
		return mobileNetTypeSearchUrl;
	}

	public void setMobileNetTypeSearchUrl(String mobileNetTypeSearchUrl) {
		this.mobileNetTypeSearchUrl = mobileNetTypeSearchUrl;
	}

}
