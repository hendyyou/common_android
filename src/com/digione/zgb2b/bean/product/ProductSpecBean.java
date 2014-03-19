package com.digione.zgb2b.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * 规格数据类
 * 
 * @author zhangqr
 * 
 */
public class ProductSpecBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2800538968687292166L;
	/**
	 * 规格标题
	 */
	private String titleName;
	/**
	 * 规格内容列表
	 */
	private List<SpecContent> list;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<SpecContent> getList() {
		return list;
	}

	public void setList(List<SpecContent> list) {
		this.list = list;
	}

}
