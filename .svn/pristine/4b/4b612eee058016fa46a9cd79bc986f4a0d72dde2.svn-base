package com.digione.zgb2b.fragment.workbench;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.workbench.OrdersAdapter;
import com.digione.zgb2b.bean.JsonPagerEntityBean;
import com.digione.zgb2b.bean.workbench.OrderBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.BestpayUtil;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class OrdersFragment extends CommonBaseFragment {

	// 页码
	private int targetpage = 1;
	private View v;
	private LinearLayout workbenchOrderTimeLL;
	private Button nearestOrderRB;
	private Button previousOrderRB;
	private ListView orderdLV;
	private int pageSize = 12;
	private int pageCount = -1;
	private OrdersAdapter ordersAdapter;
	private ArrayList<OrderBean> orderList;
	private int selectposition = 0;
	/**
	 * 1:查询近一个月的订单 2:查询一个月前的订单 3:查询待付款的订单
	 */
	private int orderType = Constants.OrderType.NEAREST_ORDER;

	/**
	 * 翼支付支付工具类实例
	 */
	private BestpayUtil bestpayUtil;

	// 获取订单列表handler
	private CustomerJsonHttpResponseHandler<JsonPagerEntityBean<OrderBean>> orderHandler;

	/**
	 * 滚动加载监听器。列表被滑动时触发
	 */
	private AbsListView.OnScrollListener scrolllistener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// 当不滚动时
			if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
				// 判断是否滚动到底部
				if (view.getLastVisiblePosition() == view.getCount() - 1) {
					if (targetpage < pageCount && pageCount != 1) {
						selectposition = orderList.size() - 1;
						int pageNumber = targetpage++;
						Log.i(Constants.TAG, "selectposition=" + selectposition + ",pageNumber=" + pageNumber + ","
								+ "orderType=" + orderType);
						requestHttp(orderType, pageNumber, pageSize);
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " handleMessage() msg.what:" + msg.what);
			}
			switch (msg.what) {
			case MsgCode.MOBILE_BESTPAY_ORDERLIST_RETURN: {
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
		}
	};

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderList = new ArrayList<OrderBean>();
		bestpayUtil = new BestpayUtil(this);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.orders, container, false);
		workbenchOrderTimeLL = (LinearLayout) v.findViewById(R.id.workbench_order_time_ll);
		nearestOrderRB = (Button) v.findViewById(R.id.workbench_order_nearest_month_rb);
		previousOrderRB = (Button) v.findViewById(R.id.workbench_order_previous_month_rb);

		nearestOrderRB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				nearestOrderRB.setBackgroundResource(R.drawable.left_btn_after);
				previousOrderRB.setBackgroundResource(R.drawable.rigth_btn);
				targetpage = 1;
				selectposition = 0;
				orderType = Constants.OrderType.NEAREST_ORDER;
				if (orderList != null && orderList.size() > 0) {
					orderList.clear();
				}
				requestHttp(Constants.OrderType.NEAREST_ORDER, targetpage, pageSize);
			}
		});
		previousOrderRB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				nearestOrderRB.setBackgroundResource(R.drawable.left_btn);
				previousOrderRB.setBackgroundResource(R.drawable.right_btn_after);
				targetpage = 1;
				orderType = Constants.OrderType.PREVIOUS_ORDER;
				selectposition = 0;
				if (orderList != null && orderList.size() > 0) {
					orderList.clear();
				}
				requestHttp(Constants.OrderType.PREVIOUS_ORDER, targetpage, pageSize);
			}
		});
		orderdLV = (ListView) v.findViewById(R.id.workbench_order_lv);
		// getArguments().get("id");
		// 顶部工具栏
		TitleView mTitle = (TitleView) v.findViewById(R.id.title);
		mTitle.setTitle(getArguments().getString(ArgName.Orders.TITLE));
		// 隐藏两边的button
		mTitle.hiddenButton();
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		orderType = getArguments().getInt(ArgName.Orders.ORDER_TYPE);
		targetpage = 1;
		int fragmentId = ChangeFragmentUtil.WORKBENCH_ALL_ORDERS;

		/**
		 * 待付款订单，没有顶部的两个button
		 */
		if (orderType == Constants.OrderType.PENDING_ORDER) {
			workbenchOrderTimeLL.setVisibility(View.GONE);
			fragmentId = ChangeFragmentUtil.WORKBENCH_PENDING_PAYMENT_ORDERS;
		} else if (orderType == Constants.OrderType.NEAREST_ORDER) {
			nearestOrderRB.setBackgroundResource(R.drawable.left_btn_after);
			previousOrderRB.setBackgroundResource(R.drawable.rigth_btn);
		} else if (orderType == Constants.OrderType.PREVIOUS_ORDER) {
			nearestOrderRB.setBackgroundResource(R.drawable.left_btn);
			previousOrderRB.setBackgroundResource(R.drawable.right_btn_after);
		}

		orderHandler = new CustomerJsonHttpResponseHandler<JsonPagerEntityBean<OrderBean>>(activity, fragmentId,
				getArguments()) {

			@Override
			public void processCallSuccess(JsonPagerEntityBean<OrderBean> outBean, String msg) {
				if (outBean != null) {
					pageSize = outBean.getPageSize();
					pageCount = outBean.getPageCount();

					if (null != outBean.getList() && !outBean.getList().isEmpty()) {

						if (orderList != null && !orderList.isEmpty() && targetpage == 1) {
							orderList.clear();
						}
						orderList.addAll(outBean.getList());
					}

					// 设置数据适配器
					ordersAdapter = new OrdersAdapter(OrdersFragment.this, orderList, orderType, bestpayUtil);
					orderdLV.setAdapter(ordersAdapter);
					orderdLV.setSelection(selectposition);
					orderdLV.setOnScrollListener(scrolllistener);
					orderdLV.setOnItemClickListener(new OrderListOnClickListener());
				} else {
					if (ordersAdapter != null) {
						ordersAdapter.notifyDataSetChanged();
					}
					ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}
		};

		requestHttp(orderType, targetpage, pageSize);
	}

	/**
     *
     */
	private void requestHttp(int orderType, int targetpage, int pagesize) {
		HttpClient orderClient = HttpClient.getInstall(activity);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("targetpage", String.valueOf(targetpage));
		map.put("pagesize", String.valueOf(pagesize));
		RequestParams params = new RequestParams(map);
		String url = Constants.Url.ORDER_LIST_026.replace("${id}", "" + orderType);
		orderClient.postAsync(url, params, orderHandler);
	}

	/**
	 * 点击商品分类，进入每个类别的明细界面
	 */
	class OrderListOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			OrderBean itembean = (OrderBean) orderList.get(position);
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, "onItemClick  点击事件 orderId=" + itembean.getOrderId());
			}
			Bundle bundle = new Bundle();
			bundle.putString(ArgName.OrdersDetail.TITLE, getString(R.string.order_detail));
			bundle.putInt(ArgName.OrdersDetail.ORDER_ID, itembean.getOrderId());
			int fragmentId = ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL;
			ChangeFragmentUtil.changeFragment(fragmentId, bundle);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BestpayUtil.BESTPAY_REQUEST_CODE) {
			switch (resultCode) {
			case 0: { // 处理订单，从平台获取发货信息并通知用户
				Log.i(Constants.TAG, "从翼支付apk返回，处理订单，从平台获取发货信息并通知用户");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERLIST_RETURN);
				break;
			}
			case 1: { // 支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息
				Log.i(Constants.TAG, "从翼支付apk返回，支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERLIST_RETURN);
				break;
			}
			case 2: { // 用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付
				Log.i(Constants.TAG, "从翼支付apk返回，用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付");
				// 不做任何处理
				break;
			}
			default:
				Log.i(Constants.TAG, "从翼支付apk返回");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERLIST_RETURN);
			}
		}
	}
}
