package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;
import java.util.List;

public class SendList implements Serializable {

	private static final long serialVersionUID = -1L;

	private String sendTitle;// 发货单标题
	private String sendAddress;// /发货地址
	private List<COrderItemList> list;// 发货单产品列表。

	public String getSendTitle() {
		return sendTitle;
	}

	public void setSendTitle(String sendTitle) {
		this.sendTitle = sendTitle;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public List<COrderItemList> getList() {
		return list;
	}

	public void setList(List<COrderItemList> list) {
		this.list = list;
	}

}
