package com.digione.zgb2b.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * 产品颜色信息
 * 
 * @author zhangqr
 * 
 */
public class ColorInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1199521833851949553L;

	/**
	 * 颜色信息标题
	 */
	private String titleName;

	/**
	 * 颜色信息列表
	 */
	private List<ColorItemBean> colorList;

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<ColorItemBean> getColorList() {
		return colorList;
	}

	public void setColorList(List<ColorItemBean> colorList) {
		this.colorList = colorList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
