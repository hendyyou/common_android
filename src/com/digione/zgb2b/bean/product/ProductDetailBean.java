package com.digione.zgb2b.bean.product;

import java.util.List;

/**
 * 产品详情界面数据bean
 * 
 * @author zhangqr
 * 
 */
public class ProductDetailBean extends ProductBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666397447164608885L;

	/**
	 * 产品VMI供应商ID
	 */
	private Integer vmiProvideId;

	/**
	 * VMI供应商标题
	 */
	private String provNameTitle;

	/**
	 * 库存状态模式。0:无货 ; 大于0:有货
	 */
	private Integer quantity;

	/**
	 * 剩余库存数量
	 */
	private Integer stockCount;

	/**
	 * 库存状态标题
	 */
	private String stockCountTitle;

	/**
	 * 产品简介信息。
	 */
	private BriefInfoBean briefInfo;

	/**
	 * 产品参与的活动信息
	 */
	private ActInfoBean actInfo;

	/**
	 * 产品颜色信息
	 */
	private ColorInfoBean colorInfo;

	/**
	 * 图片信息
	 */
	private List<PicInfoBean> picList;

	/**
	 * 赠品信息
	 */
	private String giftInfo;

	public Integer getVmiProvideId() {
		return vmiProvideId;
	}

	public void setVmiProvideId(Integer vmiProvideId) {
		this.vmiProvideId = vmiProvideId;
	}

	public String getProvNameTitle() {
		return provNameTitle;
	}

	public void setProvNameTitle(String provNameTitle) {
		this.provNameTitle = provNameTitle;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public String getStockCountTitle() {
		return stockCountTitle;
	}

	public void setStockCountTitle(String stockCountTitle) {
		this.stockCountTitle = stockCountTitle;
	}

	public BriefInfoBean getBriefInfo() {
		return briefInfo;
	}

	public void setBriefInfo(BriefInfoBean briefInfo) {
		this.briefInfo = briefInfo;
	}

	public ActInfoBean getActInfo() {
		return actInfo;
	}

	public void setActInfo(ActInfoBean actInfo) {
		this.actInfo = actInfo;
	}

	public ColorInfoBean getColorInfo() {
		return colorInfo;
	}

	public void setColorInfo(ColorInfoBean colorInfo) {
		this.colorInfo = colorInfo;
	}

	public List<PicInfoBean> getPicList() {
		return picList;
	}

	public void setPicList(List<PicInfoBean> picList) {
		this.picList = picList;
	}

	public String getGiftInfo() {
		return giftInfo;
	}

	public void setGiftInfo(String giftInfo) {
		this.giftInfo = giftInfo;
	}

}
