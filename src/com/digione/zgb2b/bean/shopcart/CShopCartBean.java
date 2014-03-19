package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;
import java.util.List;

public class CShopCartBean implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer isIntegralFlag; // 是否积分模式的标识。0:非积分订单模式1:积分订单模式
	private Integer isUseAfterSale; // 是否显示售后的标识。0:不显示1:显示
	private List<COrderListBean> orderList; // 订单列表。Map->List（orderList）

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

	public List<COrderListBean> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<COrderListBean> orderList) {
		this.orderList = orderList;
	}

}
