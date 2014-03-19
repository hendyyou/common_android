package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;

public class SConsigneeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353726003652618833L;

	private Integer consId;
	private String consignee;
	private String consMobile;
	private String consAddress;

	public Integer getConsId() {
		return consId;
	}

	public void setConsId(Integer consId) {
		this.consId = consId;
	}

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

}
