package com.digione.zgb2b.fragment.shopcart;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.shopcart.ShopCartOrderSucAdapter;
import com.digione.zgb2b.bean.shopcart.OrderInfoBean;
import com.digione.zgb2b.bean.shopcart.OrderSucBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.BestpayUtil;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;

public class ShopCartOrderFragment extends CommonBaseFragment {

	private View view;
	private ListView listView;
	private OrderSucBean orderBean;
	private ShopCartOrderSucAdapter adapter;

	/**
	 * 翼支付支付工具类实例
	 */
	private BestpayUtil bestpayUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bestpayUtil = new BestpayUtil(this);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " handleMessage() msg.what:" + msg.what);
			}
			switch (msg.what) {
			case MsgCode.SHOP_CART_ORDER_SUCCESS:
				initData();
				break;
			case MsgCode.READ_SHOP_CART_FAILURE: {
				ToastUtil.showToast(activity, "系统读取购物车信息异常！", ToastUtil.LENGTH_LONG);
				break;
			}
			case MsgCode.MOBILE_BESTPAY_SHOPCART_RETURN: {
				Bundle bundle = new Bundle();
				bundle.putString(ArgName.OrdersDetail.TITLE, getString(R.string.order_detail));
				PersistentServiceImpl persistentService = new PersistentServiceImpl();
				String orderIdString = persistentService.getStringBySharePreference(activity,
						Constants.DataFile.COMMON_INFO, Constants.DataFile.CommonInfoKey.FOR_BESTPAY_ORDER_ID);
				bundle.putInt(ArgName.OrdersDetail.ORDER_ID, Integer.valueOf(orderIdString));
				ZGApplication.getInstance().dismissDialog();
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL, bundle);
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shop_cart_suc, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (view != null) {
			Bundle bundle = getArguments();
			orderBean = (OrderSucBean) bundle.getSerializable(ArgName.ShopCartOrder.ORDER_BEAN);
			findViewById();
			initData();
		}
	}

	private void findViewById() {
		listView = (ListView) view.findViewById(R.id.shop_cart_suc_list);
		TitleView titleView = (TitleView) view.findViewById(R.id.title);
		titleView.setTitle(getResources().getString(R.string.shop_cart_order_suc));
	}

	private void initData() {
		if (orderBean == null) {
			return;
		}
		List<OrderInfoBean> orderList = orderBean.getOrderInfoList();
		if (orderList != null) {
			adapter = new ShopCartOrderSucAdapter(activity, orderList, bestpayUtil);
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BestpayUtil.BESTPAY_REQUEST_CODE) {
			switch (resultCode) {
			case 0: { // 处理订单，从平台获取发货信息并通知用户
				Log.i(Constants.TAG, "从翼支付apk返回，处理订单，从平台获取发货信息并通知用户");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_SHOPCART_RETURN);
				break;
			}
			case 1: { // 支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息
				Log.i(Constants.TAG, "从翼支付apk返回，支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_SHOPCART_RETURN);
				break;
			}
			case 2: { // 用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付
				Log.i(Constants.TAG, "从翼支付apk返回，用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付");
				// 不做任何处理
				break;
			}
			default:
				Log.i(Constants.TAG, "从翼支付apk返回");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_SHOPCART_RETURN);
				break;
			}
		}
	}
}
