package com.digione.zgb2b.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.chinatelecom.bestpayclientlite.jar.OrderInfo;
import com.chinatelecom.bestpayclientlite.jar.PayAction;
import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.workbench.BestpayOrderBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;

public class BestpayUtil {
	/**
	 * 发起翼支付的请求编码：999
	 */
	public static final int BESTPAY_REQUEST_CODE = 999;

	/**
	 * 当前操作Fragment的context
	 */
	private Fragment fragmentContext;

	private Activity activity;

	private CustomerJsonHttpResponseHandler<BestpayOrderBean> bestpayJsonHttpResponseHandler;

	public Fragment getFragmentContext() {
		return fragmentContext;
	}

	public void setFragmentContext(Fragment fragmentContext) {
		this.fragmentContext = fragmentContext;
		this.activity = fragmentContext.getActivity();
	}

	public BestpayUtil(Fragment fragmentContext) {
		this.fragmentContext = fragmentContext;
		this.activity = fragmentContext.getActivity();
	}

	/**
	 * 根据订单ID发起手机翼支付
	 * 
	 * @param orderId
	 *            订单ID
	 */
	public void makeOrderToBestpay(final Integer orderId) {
		Bundle bundle = new Bundle();
		bundle.putString(ArgName.OrdersDetail.TITLE, fragmentContext.getString(R.string.order_detail));
		bundle.putInt(ArgName.OrdersDetail.ORDER_ID, orderId);
		bestpayJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<BestpayOrderBean>(activity,
				ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL, bundle) {

			@Override
			public void processCallSuccess(BestpayOrderBean outBean, String msg) {
				Log.i(Constants.TAG, "返回entity数据：" + getEntityJsonString());

				PersistentService persistentService = new PersistentServiceImpl();
				persistentService.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
						Constants.DataFile.CommonInfoKey.FOR_BESTPAY_ORDER_ID, orderId.toString());

				// 生成翼支付使用的订单支付信息
				OrderInfo orderinfo = new OrderInfo(outBean.getPartnerid(), outBean.getPartnername(),
						outBean.getPartnerid(), outBean.getPartnerid(), outBean.getPartnerid(), "", outBean.getProductno(),
						outBean.getPartnerorderid(), outBean.getOrderid(), outBean.getTxnamount(), outBean.getRating(),
						outBean.getGoodsname(), outBean.getGoodscount(), outBean.getSig());
				PayAction payAction = new PayAction();
				Intent intent = payAction.getIntent(orderinfo, R.raw.bestpayclientlite, activity);
				if (intent != null) {
					// 启动翼支付界面
					fragmentContext.startActivityForResult(intent, BESTPAY_REQUEST_CODE);
				}
			}
		};

		// 创建HTTPCLIENT
		HttpClient httpClient = HttpClient.getInstall(activity);
		String makeBestpayUrl = Constants.Url.MAKE_BESTPAY_ORDER_047.replace("${id}", orderId.toString());
		// 调用订单生成接口
		httpClient.postAsync(makeBestpayUrl, null, bestpayJsonHttpResponseHandler);
	}
}
