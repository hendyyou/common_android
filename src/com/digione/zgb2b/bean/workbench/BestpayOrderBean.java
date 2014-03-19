package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

public class BestpayOrderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8399072353703879736L;

	/**
	 * 响应码，必填，最大6位<br>
	 * 000000：订单生成成功<br>
	 * 010011：参数不能为空<br>
	 * 010006：违反数据唯一性约束<br>
	 * 010012：参数只能为数字<br>
	 * 010018：业务管理平台商户编码不存在<br>
	 * 010032：商户IP地址错误<br>
	 */
	private String responsecode = "";

	/**
	 * 响应码描述，当响应码不为000000时有值，最大128位
	 */
	private String responsecontent = "";

	/**
	 * 业管平台商户编码，必填，最大15位
	 */
	private String partnerid = "";

	/**
	 * 业管平台商户名称，必填，最大30位<br>
	 * 商户在翼支付业务 管理平台的注册名称
	 */
	private String partnername = "";

	/**
	 * 用户手机号，必填，最大15位
	 */
	private String productno = "";

	/**
	 * 商户订单号，必填，最大15位
	 */
	private String partnerorderid = "";

	/**
	 * 业管理平台订单号，必填，最大15位<br>
	 * 业务管理平台生成的该笔交易的编号
	 */
	private String orderid = "";

	/**
	 * 交易金额，必填，最大12位，以分为单位
	 */
	private String txnamount = "";

	/**
	 * 批价，必填，最大12位，以分为单位
	 */
	private String rating = "";

	/**
	 * 商品名称，必填，最大30位
	 */
	private String goodsname = "";

	/**
	 * 商品数量，必填，最大6位
	 */
	private String goodscount = "";

	/**
	 * 签名，必填，最大64位<br>
	 * PARTNERID、 PARTNERNAME 、 PRODUCTNO 、PARTNERORDERID、ORDERID、TXNAMOUNT、RATING、GOODSNAME、GOODSCOUNT字段的数字签名。
	 */
	private String sig;

	public String getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}

	public String getResponsecontent() {
		return responsecontent;
	}

	public void setResponsecontent(String responsecontent) {
		this.responsecontent = responsecontent;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPartnername() {
		return partnername;
	}

	public void setPartnername(String partnername) {
		this.partnername = partnername;
	}

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getPartnerorderid() {
		return partnerorderid;
	}

	public void setPartnerorderid(String partnerorderid) {
		this.partnerorderid = partnerorderid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTxnamount() {
		return txnamount;
	}

	public void setTxnamount(String txnamount) {
		this.txnamount = txnamount;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(String goodscount) {
		this.goodscount = goodscount;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

}
