package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: 振华 Date: 13-4-19 Time: 下午11:31 To change this template use File | Settings | File
 * Templates.
 */
public class BookPayBean implements Serializable {
	private static final long serialVersionUID = 6090297111437835220L;
	private String bookPayTitle;
	private Double bookPayAmount;
	private Integer bookPayCount;

	public String getBookPayTitle() {
		return bookPayTitle;
	}

	public void setBookPayTitle(String bookPayTitle) {
		this.bookPayTitle = bookPayTitle;
	}

	public Double getBookPayAmount() {
		return bookPayAmount;
	}

	public void setBookPayAmount(Double bookPayAmount) {
		this.bookPayAmount = bookPayAmount;
	}

	public Integer getBookPayCount() {
		return bookPayCount;
	}

	public void setBookPayCount(Integer bookPayCount) {
		this.bookPayCount = bookPayCount;
	}
}
