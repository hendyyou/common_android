package com.digione.zgb2b.bean.product;

import java.io.Serializable;

/**
 * 团购信息
 * 
 * @author zhangqr
 * 
 */
public class ActGroupInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 40509736639684328L;

	/**
	 * 团购提示信息
	 */
	private String actGroupTips;

	/**
	 * 最低成团数量
	 */
	private Integer minCount;

	/**
	 * 最大团数量
	 */
	private Integer maxCount;

	/**
	 * 已团购数量
	 */
	private Integer orderCount;

	public String getActGroupTips() {
		return actGroupTips;
	}

	public void setActGroupTips(String actGroupTips) {
		this.actGroupTips = actGroupTips;
	}

	public Integer getMinCount() {
		return minCount;
	}

	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

}
