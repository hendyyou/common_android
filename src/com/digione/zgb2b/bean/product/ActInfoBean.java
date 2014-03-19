package com.digione.zgb2b.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * 活动信息实体类
 * 
 * @author zhangqr
 * 
 */
public class ActInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964360432333766090L;

	/**
	 * 活动信息标题
	 */
	private String actInfoTitle;

	/**
	 * 活动结束时间
	 */
	private String actEndTime;

	/**
	 * 服务器当前时间
	 */
	private String nowDateTime;

	public String getNowDateTime() {
		return nowDateTime;
	}

	public void setNowDateTime(String nowDateTime) {
		this.nowDateTime = nowDateTime;
	}

	/**
	 * 团购信息
	 */
	private ActGroupInfoBean actGroupInfo;

	/**
	 * 产品级活动信息列表
	 */
	private List<ActItemInfo> actMainRuleList;

	/**
	 * 订单级活动列表
	 */
	private ActOrderLevelBean actOrderLevelInfo;

	public String getActInfoTitle() {
		return actInfoTitle;
	}

	public void setActInfoTitle(String actInfoTitle) {
		this.actInfoTitle = actInfoTitle;
	}

	public String getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(String actEndTime) {
		this.actEndTime = actEndTime;
	}

	public ActGroupInfoBean getActGroupInfo() {
		return actGroupInfo;
	}

	public void setActGroupInfo(ActGroupInfoBean actGroupInfo) {
		this.actGroupInfo = actGroupInfo;
	}

	public List<ActItemInfo> getActMainRuleList() {
		return actMainRuleList;
	}

	public void setActMainRuleList(List<ActItemInfo> actMainRuleList) {
		this.actMainRuleList = actMainRuleList;
	}

	public ActOrderLevelBean getActOrderLevelInfo() {
		return actOrderLevelInfo;
	}

	public void setActOrderLevelInfo(ActOrderLevelBean actOrderLevelInfo) {
		this.actOrderLevelInfo = actOrderLevelInfo;
	}

}
