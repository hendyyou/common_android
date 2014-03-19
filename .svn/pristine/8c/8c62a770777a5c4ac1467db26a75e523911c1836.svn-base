package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;
import java.util.List;

import com.digione.zgb2b.bean.product.ActItemInfo;

public class COrderListBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private String orderTitle; // 订单标题
	private String orderKey; // 订单Key
	private COrderPriceInfo orderPriceInfo; // Map订单费用信息。
											// Map->List->Map（orderPriceInfo）
	private List<ActItemInfo> actGiftInfoList; // 赠送说明信息列表。Map->List->List（actGiftInfoList）
	private List<SendList> sendList; // 订单发货单列表。 Map->List->List（sendList）

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public COrderPriceInfo getOrderPriceInfo() {
		return orderPriceInfo;
	}

	public void setOrderPriceInfo(COrderPriceInfo orderPriceInfo) {
		this.orderPriceInfo = orderPriceInfo;
	}

	public List<ActItemInfo> getActGiftInfoList() {
		return actGiftInfoList;
	}

	public void setActGiftInfoList(List<ActItemInfo> actGiftInfoList) {
		this.actGiftInfoList = actGiftInfoList;
	}

	public List<SendList> getSendList() {
		return sendList;
	}

	public void setSendList(List<SendList> sendList) {
		this.sendList = sendList;
	}

}
