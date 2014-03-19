package com.digione.zgb2b.bean.workbench;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: 振华
 * Date: 13-4-19
 * Time: 下午11:34
 * To change this template use File | Settings | File Templates.
 */
public class PayDetailInfoBean implements Serializable {
    private static final long serialVersionUID = -7979627874906329077L;
    private ArrayList<BookPayBean> bookPayList;
    private OfflinePayBean offlinePay;
    private OnlinePayBean onlinePay;

    public OnlinePayBean getOnlinePay() {
        return onlinePay;
    }

    public void setOnlinePay(OnlinePayBean onlinePay) {
        this.onlinePay = onlinePay;
    }

    public ArrayList<BookPayBean> getBookPayList() {
        return bookPayList;
    }

    public void setBookPayList(ArrayList<BookPayBean> bookPayList) {
        this.bookPayList = bookPayList;
    }

    public OfflinePayBean getOfflinePay() {
        return offlinePay;
    }

    public void setOfflinePay(OfflinePayBean offlinePay) {
        this.offlinePay = offlinePay;
    }
}
