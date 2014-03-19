package com.digione.zgb2b.bean.product;

import java.io.Serializable;

/**
 * 产品颜色信息单元实体
 * 
 * @author zhangqr
 * 
 */

public class ColorItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3270407012201496085L;

	/**
	 * 颜色标题
	 */
	private String titleName;

	/**
	 * 产品详情信息URL;
	 */
	private String detailUrl;

	/**
	 * 颜色产品ID
	 */
	private Integer productId;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
