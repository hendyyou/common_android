package com.digione.zgb2b.bean;

import java.io.Serializable;

public class JsonCommonOutBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4476830571570413784L;

	/**
	 * 返回结果。<br>
	 * 0:NOTIPS ()<br>
	 * 1:SUCCESS(成功)<br>
	 * 2:FAILURE(失败)<br>
	 * 3:TIMEOUT(超时)<br>
	 */
	private Integer msgCode;

	/**
	 * 返回结果描述
	 */
	private String msg;

	/**
	 * 自定义错误代码
	 */
	private String failureCode;

	/**
	 * 返回結果数据
	 */
	private T entity;

	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
