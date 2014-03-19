package com.digione.zgb2b.bean.product;

import java.io.Serializable;

public class SpecContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2167542811952786838L;

	/**
	 * 规格标题
	 */
	private String specTitle;

	/**
	 * 规格内容
	 */
	private String specContent;

	public String getSpecTitle() {
		return specTitle;
	}

	public void setSpecTitle(String specTitle) {
		this.specTitle = specTitle;
	}

	public String getSpecContent() {
		return specContent;
	}

	public void setSpecContent(String specContent) {
		this.specContent = specContent;
	}

}
