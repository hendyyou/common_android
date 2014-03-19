package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;
import java.util.List;

public class SProductListBean implements Serializable {
	private static final long serialVersionUID = -1L;

	private String provName;
	private List<SProductBean> list;

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public List<SProductBean> getList() {
		return list;
	}

	public void setList(List<SProductBean> list) {
		this.list = list;
	}

}
