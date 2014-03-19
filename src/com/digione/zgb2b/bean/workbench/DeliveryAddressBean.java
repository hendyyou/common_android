package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;

import com.digione.zgb2b.common.Constants;

import android.util.Log;

/**
 * User: youzh Date: 13-4-12 Time: 下午6:26
 */

public class DeliveryAddressBean implements Serializable, Cloneable {

	private static final long serialVersionUID = 197473417289783364L;

	public DeliveryAddressBean cloneBean() {
		try {
			return (DeliveryAddressBean) super.clone();
		} catch (CloneNotSupportedException e) {
			Log.e(Constants.TAG, "Cloning not allowed.");
			return this;
		}
	}

	// 门店名称
	private String shopName;

	// 门店ID
	private Integer consId;

	// 收货人
	private String consigneed;

	// 电话
	private String consMobile;

	// 完整的收货地址
	private String fullConsAddress;

	// 省份ID
	private Integer province;

	private String provinceName;

	// 地市ID
	private Integer city;
	private String cityName;

	// 县区ID
	private Integer county;
	private String countyName;
	// 收货地址
	private String consAddress;

	// 物流商ID
	private Integer logisticsId;

	private String logisticsName;

	// 是否是默认地址
	private Integer isdefault;

	// 是否有效
	private Integer flag;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getConsId() {
		return consId;
	}

	public void setConsId(Integer consId) {
		this.consId = consId;
	}

	public String getConsigneed() {
		return consigneed;
	}

	public void setConsigneed(String consigneed) {
		this.consigneed = consigneed;
	}

	public String getFullConsAddress() {
		return fullConsAddress;
	}

	public void setFullConsAddress(String fullConsAddress) {
		this.fullConsAddress = fullConsAddress;
	}

	public String getConsMobile() {
		return consMobile;
	}

	public void setConsMobile(String consMobile) {
		this.consMobile = consMobile;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getCounty() {
		return county;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getConsAddress() {
		return consAddress;
	}

	public void setConsAddress(String consAddress) {
		this.consAddress = consAddress;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
