package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;
import java.util.List;

public class OrderSucBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer isIntegralFlag;// 是否积分模式的标识。0:非积分订单模式 1:积分
	private Integer isUseAfterSale;// 是否显示售后的标识。0:不显示 1:显示
	private Integer expectCount;// 提交的订单数量
	private Integer succesCount;// 生成成功的订单数量
	private Integer failedCount;// 生成失败的订单数量
	private List<OrderInfoBean> orderInfoList;// 订单列表Map->List（orderInfoList）

	public Integer getIsIntegralFlag() {
		return isIntegralFlag;
	}

	public void setIsIntegralFlag(Integer isIntegralFlag) {
		this.isIntegralFlag = isIntegralFlag;
	}

	public Integer getIsUseAfterSale() {
		return isUseAfterSale;
	}

	public void setIsUseAfterSale(Integer isUseAfterSale) {
		this.isUseAfterSale = isUseAfterSale;
	}

	public Integer getExpectCount() {
		return expectCount;
	}

	public void setExpectCount(Integer expectCount) {
		this.expectCount = expectCount;
	}

	public Integer getSuccesCount() {
		return succesCount;
	}

	public void setSuccesCount(Integer succesCount) {
		this.succesCount = succesCount;
	}

	public Integer getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	public List<OrderInfoBean> getOrderInfoList() {
		return orderInfoList;
	}

	public void setOrderInfoList(List<OrderInfoBean> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}

}
