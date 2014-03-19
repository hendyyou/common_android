package com.digione.zgb2b.bean.product;

import java.io.Serializable;

/**
 * 图片信息实体类
 * 
 * @author zhangqr
 * 
 */
public class PicInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066667573012603449L;

	/**
	 * 图片ID
	 */
	private Integer picId;

	/**
	 * 原始图片URL
	 */
	private String picUrl;

	/**
	 * 小图片URL
	 */
	private String picSmallUrl;

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicSmallUrl() {
		return picSmallUrl;
	}

	public void setPicSmallUrl(String picSmallUrl) {
		this.picSmallUrl = picSmallUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
