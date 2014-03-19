package com.digione.zgb2b.bean.product;

import java.io.Serializable;

/**
 * 咨询内容数据类
 * 
 * @author zhangqr
 * 
 */
public class ConsultItemBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5437251393659286627L;
	/**
	 * 客户编号
	 */
	private String custNo;
	/**
	 * 咨询内容
	 */
	private String question;
	/**
	 * 回复内容
	 */
	private String reply;
	/**
	 * 咨询时间
	 */
	private String consultingTime;
	/**
	 * 咨询ID
	 */
	private Integer consultingId;

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getConsultingTime() {
		return consultingTime;
	}

	public void setConsultingTime(String consultingTime) {
		this.consultingTime = consultingTime;
	}

	public Integer getConsultingId() {
		return consultingId;
	}

	public void setConsultingId(Integer consultingId) {
		this.consultingId = consultingId;
	}

}
