package com.digione.zgb2b.bean.home;

import java.io.Serializable;

public class DiginewsBean implements Serializable {
	private static final long serialVersionUID = -1L;

	private String newsType;// 新闻类型。
	private String createTime;// 创建时间
	private String title;// 标题
	private String content;// 内容
	private Integer fileExists;// 是否附件。0:不存在 1:存在
	private String uploadpathUrl;// 附件URL

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFileExists() {
		return fileExists;
	}

	public void setFileExists(Integer fileExists) {
		this.fileExists = fileExists;
	}

	public String getUploadpathUrl() {
		return uploadpathUrl;
	}

	public void setUploadpathUrl(String uploadpathUrl) {
		this.uploadpathUrl = uploadpathUrl;
	}

}
