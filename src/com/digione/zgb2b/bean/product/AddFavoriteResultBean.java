package com.digione.zgb2b.bean.product;

import java.io.Serializable;

public class AddFavoriteResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2762880299287802778L;

	/**
	 * 返回添加结果。<br>
	 * 0:你已经收藏过了<br>
	 * 1:收藏成功<br>
	 */
	private Integer result;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
}
