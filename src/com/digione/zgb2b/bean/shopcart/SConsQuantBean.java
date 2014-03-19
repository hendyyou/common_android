package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;

public class SConsQuantBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer consId;
	private Integer isDefault;
	private Integer quantity;

	public Integer getConsId() {
		return consId;
	}

	public void setConsId(Integer consId) {
		this.consId = consId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
