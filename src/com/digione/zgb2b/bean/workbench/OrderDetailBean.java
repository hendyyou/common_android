package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: 振华 Date: 13-4-20 Time: 上午10:39
 */
public class OrderDetailBean implements Serializable {
	private static final long serialVersionUID = 877464088575481966L;

	private Integer orderId;
	private String orderNo;
	private String orderTime;
	private String priceTime;
	private String sendTime;
	private String receiveTime;
	private String orderDetailStatus;
	private String orderPayStatus;
	private String orderStatus;
	private String payStatus;
	private String sendStatus;
	private String receiveStatus;
	private Integer isCanPay;
	private Integer isCanCancelPay;
	private Integer isCanCancel;
	private String invoiceTypeText;
	private Integer isUseAfterSale;

	/**
	 * 订单生成来源
	 */
	private Integer orderSource;
	/**
	 * 订单生成来源中文
	 */
	private String orderSourceText;

	private OrderPriceInfoBean orderPriceInfo;
	private PayDetailInfoBean payDetailInfo;
	private LogisticsInfoBean logisticsInfo;
	private ArrayList<SendListBean> sendList;

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

	public String getPriceTime() {
		return priceTime;
	}

	public void setPriceTime(String priceTime) {
		this.priceTime = priceTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}

	public String getOrderPayStatus() {
		return orderPayStatus;
	}

	public void setOrderPayStatus(String orderPayStatus) {
		this.orderPayStatus = orderPayStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Integer getIsCanPay() {
		return isCanPay;
	}

	public void setIsCanPay(Integer isCanPay) {
		this.isCanPay = isCanPay;
	}

	public Integer getIsCanCancelPay() {
		return isCanCancelPay;
	}

	public void setIsCanCancelPay(Integer isCanCancelPay) {
		this.isCanCancelPay = isCanCancelPay;
	}

	public Integer getIsCanCancel() {
		return isCanCancel;
	}

	public void setIsCanCancel(Integer isCanCancel) {
		this.isCanCancel = isCanCancel;
	}

	public String getInvoiceTypeText() {
		return invoiceTypeText;
	}

	public void setInvoiceTypeText(String invoiceTypeText) {
		this.invoiceTypeText = invoiceTypeText;
	}

	public Integer getIsUseAfterSale() {
		return isUseAfterSale;
	}

	public void setIsUseAfterSale(Integer isUseAfterSale) {
		this.isUseAfterSale = isUseAfterSale;
	}

	public OrderPriceInfoBean getOrderPriceInfo() {
		return orderPriceInfo;
	}

	public void setOrderPriceInfo(OrderPriceInfoBean orderPriceInfo) {
		this.orderPriceInfo = orderPriceInfo;
	}

	public PayDetailInfoBean getPayDetailInfo() {
		return payDetailInfo;
	}

	public void setPayDetailInfo(PayDetailInfoBean payDetailInfo) {
		this.payDetailInfo = payDetailInfo;
	}

	public LogisticsInfoBean getLogisticsInfo() {
		return logisticsInfo;
	}

	public void setLogisticsInfo(LogisticsInfoBean logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}

	public ArrayList<SendListBean> getSendList() {
		return sendList;
	}

	public void setSendList(ArrayList<SendListBean> sendList) {
		this.sendList = sendList;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderSourceText() {
		return orderSourceText;
	}

	public void setOrderSourceText(String orderSourceText) {
		this.orderSourceText = orderSourceText;
	}

}
