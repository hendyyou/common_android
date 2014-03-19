/**
 *  百分之百版权所有
 *
 */
package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;
import java.util.List;

/**
 * @author 尤振华
 * @version 1.0
 * @ClassName: OrderBean
 * @Description: 订单对应的实体
 * @date 2012-10-23 上午9:18:00
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 订单ID
	private Integer orderId;
	// 订单号
	private String orderNo;
	// 下单时间
	private String orderTime;
	// 订单状态
	private String orderListStatus;
	private String orderStatus;
	private String payStatus;
	private String sendStatus;
	private String receiveStatus;
	/**
	 * 是否可以发起手机翼支付的标识。 0:不可以发起支付 1:可发起支付
	 */
	private Integer isCanPay;

	/**
	 * 是否可以发起取消手机翼支付的标识。 0:不可以发起 1:可发起
	 */
	private Integer isCanCancelPay;
	/**
	 * 是否可以发起取消订单的标识。 0:不可以发起 1:可发起
	 */
	private Integer isCanCancel;
	// 订单金额
	private Double orderTotalAmount;

	/**
	 * 订单生成来源
	 */
	private Integer orderSource;
	/**
	 * 订单生成来源中文
	 */
	private String orderSourceText;

	// 商品列表
	private List<SimpleProductBean> list;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 默认的构造函数
	 * </p>
	 */
	public OrderBean() {

	}

	/**
	 * @return orderNumber
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNumber to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo.toString();
	}

	/**
	 * @return orderAmount
	 */
	public Double getOrderTotalAmount() {
		return orderTotalAmount;
	}

	/**
	 * @param orderTotalAmount
	 *            the orderAmount to set
	 */
	public void setOrderTotalAmount(Double orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	/**
	 * @return orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime.toString();
	}

	/**
	 * @return orderStatus
	 */
	public String getOrderListStatus() {
		return orderListStatus;
	}

	/**
	 * @param orderListStatus
	 *            the orderStatus to set
	 */
	public void setOrderListStatus(String orderListStatus) {
		this.orderListStatus = orderListStatus.toString();
	}

	/**
	 * @return orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Integer getIsCanPay() {
		return isCanPay;
	}

	public void setIsCanPay(Integer canPay) {
		isCanPay = canPay;
	}

	public Integer getIsCanCancelPay() {
		return isCanCancelPay;
	}

	public void setIsCanCancelPay(Integer canCancelPay) {
		isCanCancelPay = canCancelPay;
	}

	public Integer getIsCanCancel() {
		return isCanCancel;
	}

	public void setIsCanCancel(Integer canCancel) {
		isCanCancel = canCancel;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderSourceText() {
		return orderSourceText;
	}

	public void setOrderSourceText(String orderSourceText) {
		this.orderSourceText = orderSourceText;
	}

	public List<SimpleProductBean> getList() {
		return list;
	}

	public void setList(List<SimpleProductBean> list) {
		this.list = list;
	}

	/**
	 * Title: hashCode Description:
	 * 
	 * @return
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orderTime == null) ? 0 : orderTime.hashCode());
		return result;
	}

	/**
	 * <p>
	 * Title: equals
	 * </p>
	 * <p>
	 * Description:根据订单号和下单时间重写equals 方法
	 * </p>
	 * 
	 * @param obj
	 * @return 方法两对象是否相等。
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orderTime == null) {
			if (other.orderTime != null)
				return false;
		} else if (!orderTime.equals(other.orderTime))
			return false;
		return true;
	}
}
