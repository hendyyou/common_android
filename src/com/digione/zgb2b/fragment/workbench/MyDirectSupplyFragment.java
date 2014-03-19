package com.digione.zgb2b.fragment.workbench;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.JsonNoneOutBean;
import com.digione.zgb2b.bean.workbench.UserBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.HttpUtil;
import com.digione.zgb2b.utils.ShopCartUtils;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class MyDirectSupplyFragment extends CommonBaseFragment {
	private View v;
	// 用户名
	private TextView userNameTV;
	// 用户级别
	private TextView userLevelTV;
	// 上次登录时间
	private TextView lastLoginTimeTV;
	private LinearLayout allOdersLL;
	private LinearLayout pendingPaymentOrdersLL;
	private LinearLayout myFavoriteLL;
	private LinearLayout deliveryAddressLL;
	private LinearLayout passwordUpLL;
	// 获取用户信息handler
	private CustomerJsonHttpResponseHandler<UserBean> userHttpResponseHandler;
	// 退出handler
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> exitResponseHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.READ_SHOP_CART_FAILURE: {
				ToastUtil.showToast(activity, "系统读取购物车信息异常！", ToastUtil.LENGTH_LONG);
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.my_direct_supply, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (v != null) {

			if (!ZGApplication.getInstance().isLogin()) {
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_USER_LOGIN, getArguments());
				return;
			}

			userHttpResponseHandler = new CustomerJsonHttpResponseHandler<UserBean>(activity,
					ChangeFragmentUtil.WORKBENCH_CLASSIFY, getArguments()) {

				@Override
				public void processCallSuccess(UserBean outBean, String msg) {
					if (outBean != null) {
						ZGApplication.getInstance().setUser(outBean);
						setValue(outBean);
					}
				}

				@Override
				public void processCallFailure(UserBean outBean, String failureCode, String msg) {
					super.processCallFailure(outBean, failureCode, msg);
					ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.WORKBENCH_CLASSIFY, activity, msg,
							getArguments());
				}
			};

			exitResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(activity) {

				@Override
				public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
					ZGApplication.getInstance().setLogin(false);
					ShopCartUtils.getInstance(mHandler).getShopCartNum(activity);
					ZGApplication.getInstance().setUser(null);
					ZGApplication.getInstance().setResetShopCart(false);
					// 重置保存首页广告标识
					ZGApplication.getInstance().setSaveGHomeBean(false);
					HttpClient.clearCookie();
					ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
					ChangeFragmentUtil.performHomeTabClick();
				}

				@Override
				public void processCallSuccessException(Exception e) {
					ZGApplication.getInstance().setLogin(false);
					ZGApplication.getInstance().setUser(null);
					// 重置保存首页广告标识
					ZGApplication.getInstance().setSaveGHomeBean(false);
					super.processCallSuccessException(e);
				}
			};

			allOdersLL = (LinearLayout) v.findViewById(R.id.workbench_category_all_orders_ll);
			allOdersLL.setOnClickListener(new CategoryOnClickListener());

			pendingPaymentOrdersLL = (LinearLayout) v.findViewById(R.id.workbench_category_pending_payment_orders_ll);
			pendingPaymentOrdersLL.setOnClickListener(new CategoryOnClickListener());

			myFavoriteLL = (LinearLayout) v.findViewById(R.id.workbench_category_my_collection_ll);
			myFavoriteLL.setOnClickListener(new CategoryOnClickListener());

			deliveryAddressLL = (LinearLayout) v.findViewById(R.id.workbench_category_delivery_address_ll);
			deliveryAddressLL.setOnClickListener(new CategoryOnClickListener());

			passwordUpLL = (LinearLayout) v.findViewById(R.id.workbench_category_password_update_ll);
			passwordUpLL.setOnClickListener(new CategoryOnClickListener());
			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.my_direct_supply);

			// 隐藏按钮
			mTitle.hiddenLeftButton();

			mTitle.setRightButton(R.string.exit, new TitleView.OnRightButtonClickListener() {

				@Override
				public void onClick(View button) {
					if (ZGApplication.getInstance().isLogin()) {
						String url = Constants.Url.LOGOUT_002;
						HttpClient client = HttpClient.getInstall(activity);
						client.postAsync(url, null, exitResponseHandler);
					}
				}
			});
			userNameTV = (TextView) v.findViewById(R.id.workbench_username_tv);
			userNameTV.getPaint().setFakeBoldText(true);
			userLevelTV = (TextView) v.findViewById(R.id.workbench_userlevel_tv);
			lastLoginTimeTV = (TextView) v.findViewById(R.id.workbench_last_logon_time_tv);

			// 如果存在用户信息,就不请求网络
			if (ZGApplication.getInstance().getUser() != null) {
				setValue(ZGApplication.getInstance().getUser());
				if (ZGApplication.isDevMode()) {
					Log.d(Constants.TAG, "用户信息已经存在直接从ZGApplication.user 读");
				}
			} else {
				if (HttpUtil.isHasNetwork(activity)) {
					// 请求网络,获取用户信息
					HttpClient userClient = HttpClient.getInstall(activity);
					String url = Constants.Url.USER_INFO_005;
					userClient.postAsync(url, null, userHttpResponseHandler);
				} else {
					ToastUtil.showToast(activity, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);
					return;
				}
			}

		}
	}

	private void setValue(UserBean userBean) {
		userNameTV.setText(userBean.getUserName());
		userLevelTV.setText(userBean.getLevelName());
		lastLoginTimeTV.setText(userBean.getLastLoginTime());
	}

	/**
	 * 点击商品分类，进入每个类别的明细界面
	 */
	class CategoryOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			Bundle bundle = new Bundle();
			int fragmentId = ChangeFragmentUtil.WORKBENCH_ALL_ORDERS;
			switch (view.getId()) {
			case R.id.workbench_category_all_orders_ll:
				fragmentId = ChangeFragmentUtil.WORKBENCH_ALL_ORDERS;
				bundle.putInt(ArgName.Orders.ORDER_TYPE, 1);
				bundle.putString("title", getString(R.string.all_orders));
				break;
			case R.id.workbench_category_pending_payment_orders_ll:
				fragmentId = ChangeFragmentUtil.WORKBENCH_PENDING_PAYMENT_ORDERS;
				bundle.putInt(ArgName.Orders.ORDER_TYPE, 3);
				bundle.putString("title", getString(R.string.pending_payment_orders));
				break;
			case R.id.workbench_category_my_collection_ll:
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("targetpage", String.valueOf(1));
				bundle.putString(ArgName.ProductList.URL, Constants.Url.MY_FAVORITES_008);
				bundle.putString(ArgName.ProductList.TITLE, getString(R.string.my_collection));
				bundle.putBoolean(ArgName.ProductList.SHOW_SORT, false);
				bundle.putSerializable(ArgName.ProductList.PARAM, param);
				fragmentId = ChangeFragmentUtil.PRODUCT_LIST;
				break;
			case R.id.workbench_category_delivery_address_ll:
				fragmentId = ChangeFragmentUtil.WORKBENCH_RECEIVING_ADDRESS;
				break;
			case R.id.workbench_category_password_update_ll:
				fragmentId = ChangeFragmentUtil.WORKBENCH_UPDATE_PASSWORD;
				break;
			default:
				Toast.makeText(activity, view.getId() + "," + v.getId() + ",", Toast.LENGTH_SHORT).show();

			}
			ChangeFragmentUtil.changeFragment(fragmentId, bundle);
		}
	}
}