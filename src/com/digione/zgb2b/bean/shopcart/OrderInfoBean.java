package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;

public class OrderInfoBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer isSucceed;// 是否成功生成订单的标识。0:失败1:成功
	private String orderTitle;// 订单标题
	private Integer orderId;// 订单ID。isSucceed=0时为null
	private String orderNo;// 订单编号。isSucceed=0时为null
	private String orderTime;// 订单下单时间。isSucceed=0时为null
	private String orderPayStatus;// 付款状态。isSucceed=0时为null
	private Integer isCanPay;// 是否可以发起手机翼支付的标识。isSucceed=0时为null 0:不可以发起支付
								// 1:可发起支付
	private Double totalOrderAmount;// 订单总金额。isSucceed=0时为null
	private Integer totalProductCount;// 订单产品数量。isSucceed=0时为null
	private String failedMsg;// 生成订单的错误信息。isSucceed=1时为null

	public Integer getIsSucceed() {
		return isSucceed;
	}

	public void setIsSucceed(Integer isSucceed) {
		this.isSucceed = isSucceed;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderPayStatus() {
		return orderPayStatus;
	}

	public void setOrderPayStatus(String orderPayStatus) {
		this.orderPayStatus = orderPayStatus;
	}

	public Integer getIsCanPay() {
		return isCanPay;
	}

	public void setIsCanPay(Integer isCanPay) {
		this.isCanPay = isCanPay;
	}

	public Double getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(Double totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public Integer getTotalProductCount() {
		return totalProductCount;
	}

	public void setTotalProductCount(Integer totalProductCount) {
		this.totalProductCount = totalProductCount;
	}

	public String getFailedMsg() {
		return failedMsg;
	}

	public void setFailedMsg(String failedMsg) {
		this.failedMsg = failedMsg;
	}

}
