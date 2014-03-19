package com.digione.zgb2b.bean.shopcart;

import java.io.Serializable;

public class SInvoiceInfo implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer invoiceType;
	private String invoiceTypeName;
    private String incoiceReceiverAddress;

	public SInvoiceInfo(Integer invoiceType, String invoiceTypeName) {
		this.invoiceType = invoiceType;
		this.invoiceTypeName = invoiceTypeName;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

    public String getIncoiceReceiverAddress() {
        return incoiceReceiverAddress;
    }

    public void setIncoiceReceiverAddress(String incoiceReceiverAddress) {
        this.incoiceReceiverAddress = incoiceReceiverAddress;
    }
}
