package com.digione.zgb2b;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digione.zgb2b.bean.workbench.UserBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.ExitAppHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.OnFragmentChangeTabListener;
import com.digione.zgb2b.fragment.SimpleMainFragment;
import com.digione.zgb2b.fragment.home.HomeFragment;
import com.digione.zgb2b.fragment.home.WebFragment;
import com.digione.zgb2b.fragment.more.MoreFragment;
import com.digione.zgb2b.fragment.product.ProductClassyFragment;
import com.digione.zgb2b.fragment.shopcart.ShopCartFragement;
import com.digione.zgb2b.fragment.shopcart.ShopCartNoNormalFragment;
import com.digione.zgb2b.fragment.shopcart.ShopCartOrderFragment;
import com.digione.zgb2b.fragment.workbench.MyDirectSupplyFragment;
import com.digione.zgb2b.fragment.workbench.UserLoginFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.HttpUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 主界面
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class MainActivity extends FragmentActivity implements OnFragmentChangeTabListener {

	private LinearLayout tabHostLinearLayout;

	private ImageButton homeImageButton;

	private ImageButton productImageButton;

	private RelativeLayout shoppingcartRelativeLayout;

	private ImageButton workBenchImageButton;

	private ImageButton moreImageButton;

	private AlertDialog alertDialog;

	private String lastFragmentTag;

	private int lastTabIndex = -1;

	private int currentTabIndex;

	private boolean[] tabViewSelectFlags = { false, false, false, false, false };

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " handleMessage() msg.what:" + msg.what);
			}
			switch (msg.what) {
			case MsgCode.HOME_CLICK: {
				homeTabClickListener.onClick(null);
				break;
			}
			case MsgCode.PRODUCT_CLICK: {
				productTabClickListener.onClick(null);
				break;
			}
			case MsgCode.SHOPPINGCART_CLICK: {
				shopCartTabClickListener.onClick(null);
				break;
			}
			case MsgCode.WORKBENCH_CLICK: {
				workBenchTabClickListener.onClick(null);
				break;
			}
			case MsgCode.MORE_CLICK: {
				moreTabClickListener.onClick(null);
				break;
			}
			case MsgCode.SHOW_TAB: {
				if (tabHostLinearLayout.getVisibility() != View.VISIBLE) {
					tabHostLinearLayout.setVisibility(View.VISIBLE);
				}
				break;
			}
			case MsgCode.HIDE_TAB: {
				if (tabHostLinearLayout.getVisibility() != View.GONE) {
					tabHostLinearLayout.setVisibility(View.GONE);
				}
				break;
			}
			default:
				break;
			}
		}
	};

	private View.OnClickListener homeTabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " HOME onClick()");
			}
			setTabViewResource(Constants.TabIndex.HOME);
			changeTab(ChangeFragmentUtil.HOME_CLASSIFY);
		}
	};

	private View.OnClickListener productTabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " PRODUCT onClick()");
			}
			setTabViewResource(Constants.TabIndex.PRODUCT);
			changeTab(ChangeFragmentUtil.PRODUCT_CLASSIFY);
		}
	};

	private View.OnClickListener shopCartTabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " SHOPPINGCART onClick()");
			}
			setTabViewResource(Constants.TabIndex.SHOPPINGCART);
			changeTab(ChangeFragmentUtil.SHOPPINGCART_CLASSIFY);
		}
	};

	private View.OnClickListener workBenchTabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " WORKBENCH onClick()");
			}
			setTabViewResource(Constants.TabIndex.WORKBENCH);
			changeTab(ChangeFragmentUtil.WORKBENCH_CLASSIFY);
		}
	};

	private View.OnClickListener moreTabClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " MORE onClick()");
			}
			setTabViewResource(Constants.TabIndex.MORE);
			changeTab(ChangeFragmentUtil.MORE_CLASSIFY);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onCreate()");
		}
		ZGApplication.getInstance().pushActivity(this);

		// 设置Handler到切换类
		ChangeFragmentUtil.setMainActivityHandler(mHandler);

		// 获取设备高度和宽度
		if (ZGApplication.getScreenWidth() == 0) {
			WindowManager manage = getWindowManager();
			DisplayMetrics displayMetrics = new DisplayMetrics();
			manage.getDefaultDisplay().getMetrics(displayMetrics);
			ZGApplication.setScreenWidth(displayMetrics.widthPixels);
			ZGApplication.setScreenHeight(displayMetrics.heightPixels);
			ZGApplication.setScreenDensity(displayMetrics.densityDpi);
		}
		setContentView(R.layout.fragment_tabs);

		tabHostLinearLayout = (LinearLayout) findViewById(R.id.tabhost_ll);

		homeImageButton = (ImageButton) findViewById(R.id.tabhost_home_imagebutton);
		homeImageButton.setOnClickListener(homeTabClickListener);

		productImageButton = (ImageButton) findViewById(R.id.tabhost_product_imagebutton);
		productImageButton.setOnClickListener(productTabClickListener);

		shoppingcartRelativeLayout = (RelativeLayout) findViewById(R.id.tabhost_shoppingcart_rl);
		shoppingcartRelativeLayout.setOnClickListener(shopCartTabClickListener);

		workBenchImageButton = (ImageButton) findViewById(R.id.tabhost_workbench_imagebutton);
		workBenchImageButton.setOnClickListener(workBenchTabClickListener);

		moreImageButton = (ImageButton) findViewById(R.id.tabhost_more_imagebutton);
		moreImageButton.setOnClickListener(moreTabClickListener);

		setTabViewResource(Constants.TabIndex.HOME);

		// 设置install FragmentManager
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				ChangeFragmentUtil.refreshFragmentTag();
			}
		});
		ZGApplication.getInstance().setFragmentManager(fragmentManager);

		SimpleMainFragment simpleMainFragment = (SimpleMainFragment) fragmentManager
				.findFragmentByTag(SimpleMainFragment.class.getSimpleName());
		if (null == simpleMainFragment) {
			simpleMainFragment = new SimpleMainFragment();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.realtabcontent, simpleMainFragment, SimpleMainFragment.class.getSimpleName());
			fragmentTransaction.addToBackStack(SimpleMainFragment.class.getSimpleName());
			fragmentTransaction.commit();
		}

		// 启动心跳
		new HeartBitThread().start();
	}

	@Override
	protected void onStart() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onStart()");
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onStop()");
		}
		super.onStop();
	}

	@Override
	protected void onPause() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onPause()");
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onResume()");
		}
		super.onResume();
	}

	@Override
	protected void onRestart() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onRestart()");
		}
		super.onRestart();
	}

	private void changeTab(int targetFragmentId) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG,
					this.getClass().getSimpleName() + " changeTab() currentFragmentTag:"
							+ ChangeFragmentUtil.getCurrentFragmentTag() + " lastTabIndex:" + lastTabIndex);
		}
		Bundle bundle = new Bundle();
		switch (targetFragmentId) {
		case ChangeFragmentUtil.SIMPLE_FRAGMENT:
			if (!SimpleMainFragment.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;
		case ChangeFragmentUtil.HOME_CLASSIFY:
			if (!HomeFragment.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;
		case ChangeFragmentUtil.PRODUCT_CLASSIFY:
			if (!ProductClassyFragment.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())
					|| lastTabIndex != currentTabIndex) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;
		case ChangeFragmentUtil.SHOPPINGCART_CLASSIFY:
			if ((!(ShopCartFragement.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag()) || ShopCartNoNormalFragment.class
					.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())) && !UserLoginFragment.class
					.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag()))
					|| lastTabIndex != currentTabIndex) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;
		case ChangeFragmentUtil.WORKBENCH_CLASSIFY:
			if ((!MyDirectSupplyFragment.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag()) && !UserLoginFragment.class
					.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())) || lastTabIndex != currentTabIndex) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;
		case ChangeFragmentUtil.MORE_CLASSIFY:
			if (!MoreFragment.class.getSimpleName().equals(ChangeFragmentUtil.getCurrentFragmentTag())
					|| lastTabIndex != currentTabIndex) {
				ChangeFragmentUtil.changeFragment(targetFragmentId, bundle);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onDestroy()");
		}
		ExitAppHandler exitAppHandler = new ExitAppHandler(this);
		exitAppHandler.exit();
		super.onDestroy();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			lastFragmentTag = ChangeFragmentUtil.getCurrentFragmentTag();
			if (ZGApplication.isDevMode()) {
				Log.d("changeframgement", "onKeyUp currentFragmentTag:" + lastFragmentTag);
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (HomeFragment.class.getSimpleName().equals(lastFragmentTag)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.confirm));
			builder.setMessage(getString(R.string.confirm_exit));
			builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface mDialogInterface, int which) {
					ZGApplication.getInstance().setUser(null);
					ZGApplication.getInstance().setLogin(false);
					ZGApplication.getInstance().setResetShopCart(false);
					ZGApplication.getInstance().exit();
				}
			});
			builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface mDialogInterface, int which) {
					if (alertDialog != null) {
						alertDialog.dismiss();
					}
				}
			});
			alertDialog = builder.create();
			alertDialog.show();
		} else if (ProductClassyFragment.class.getSimpleName().equals(lastFragmentTag)
				|| ShopCartFragement.class.getSimpleName().equals(lastFragmentTag)
				|| ShopCartNoNormalFragment.class.getSimpleName().equals(lastFragmentTag)
				|| MyDirectSupplyFragment.class.getSimpleName().equals(lastFragmentTag)
				|| MoreFragment.class.getSimpleName().equals(lastFragmentTag)
				|| (UserLoginFragment.class.getSimpleName().equals(lastFragmentTag) && (currentTabIndex == Constants.TabIndex.SHOPPINGCART || currentTabIndex == Constants.TabIndex.WORKBENCH || currentTabIndex == Constants.TabIndex.HOME))) {
			homeTabClickListener.onClick(null);
		} else if (ShopCartOrderFragment.class.getSimpleName().equals(lastFragmentTag)) {
			// 不执行回退
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * 心跳线程
	 */
	private class HeartBitThread extends Thread {

		public void run() {
			while (true) {
				try {
					// 每隔8分钟发一次
					if (HttpUtil.isHasNetwork(MainActivity.this)) {
						HttpClient httpClient = HttpClient.getInstall(MainActivity.this);
						httpClient.postAsync(Constants.Url.HEART_BEAT_003, null, new JsonHttpResponseHandler());
						if (ZGApplication.isDevMode()) {
							Log.d(Constants.TAG, "Hear Bit Thread " + SystemUtil.getDateTime());
						}
						// 多少分钟执行一次,测试一分钟
						sleep(60 * 1000 * 8);
					}
				} catch (InterruptedException e) {
					// 如果线程异常退出，则重新开始
					new HeartBitThread().start();
				}
			}
		}
	}

	@Override
	public void onTabChanged(int targetFragmentId) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onTabChanged() " + targetFragmentId);
		}
		changeTab(targetFragmentId);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onSaveInstanceState() start");
		}
		super.onSaveInstanceState(outState);
		outState.putBoolean(ArgName.MainActivity.IS_LOGIN, ZGApplication.getInstance().isLogin());
		outState.putSerializable(ArgName.MainActivity.USER, ZGApplication.getInstance().getUser());
		outState.putBoolean(ArgName.MainActivity.IS_CAN_CLEAR_POP_BACK_STACK, ZGApplication.isCanClearPopBackStack());
		outState.putBooleanArray(ArgName.MainActivity.TAB_VIEW_SELECT_FLAGS, tabViewSelectFlags);
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onSaveInstanceState() end");
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onRestoreInstanceState() start");
		}
		super.onRestoreInstanceState(savedInstanceState);
		boolean isLogin = savedInstanceState.getBoolean(ArgName.MainActivity.IS_LOGIN);
		ZGApplication.getInstance().setLogin(isLogin);
		UserBean userBean = (UserBean) savedInstanceState.getSerializable(ArgName.MainActivity.USER);
		ZGApplication.getInstance().setUser(userBean);
		boolean isCanClearPopBackStack = savedInstanceState.getBoolean(ArgName.MainActivity.IS_CAN_CLEAR_POP_BACK_STACK);
		ZGApplication.setCanClearPopBackStack(isCanClearPopBackStack);
		tabViewSelectFlags = savedInstanceState.getBooleanArray(ArgName.MainActivity.TAB_VIEW_SELECT_FLAGS);
		refreshTabViewResource();
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onRestoreInstanceState() end");
		}
	}

	@Override
	protected void onResumeFragments() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onResumeFragments() start");
		}
		super.onResumeFragments();
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onResumeFragments() end");
		}
	}

	private void setTabViewResource(int index) {
		for (int i = 0; i < tabViewSelectFlags.length; i++) {
			if (tabViewSelectFlags[i]) {
				lastTabIndex = i;
				break;
			}
		}
		for (int i = 0; i < tabViewSelectFlags.length; i++) {
			tabViewSelectFlags[i] = (i == index);
		}
		refreshTabViewResource();
		currentTabIndex = index;
	}

	private void refreshTabViewResource() {
		int resourceId = 0;
		for (int i = 0; i < tabViewSelectFlags.length; i++) {
			switch (i) {
			case Constants.TabIndex.HOME:
				resourceId = tabViewSelectFlags[i] ? R.drawable.home_selected : R.drawable.home_unselet;
				homeImageButton.setBackgroundResource(resourceId);
				break;
			case Constants.TabIndex.PRODUCT:
				resourceId = tabViewSelectFlags[i] ? R.drawable.product_selected : R.drawable.product_unselect;
				productImageButton.setBackgroundResource(resourceId);
				break;
			case Constants.TabIndex.SHOPPINGCART:
				resourceId = tabViewSelectFlags[i] ? R.drawable.cart_selected : R.drawable.cart_unselect;
				shoppingcartRelativeLayout.setBackgroundResource(resourceId);
				break;
			case Constants.TabIndex.WORKBENCH:
				resourceId = tabViewSelectFlags[i] ? R.drawable.workbench_selected : R.drawable.workbench_unselect;
				workBenchImageButton.setBackgroundResource(resourceId);
				break;
			case Constants.TabIndex.MORE:
				resourceId = tabViewSelectFlags[i] ? R.drawable.more_selected : R.drawable.more_unselect;
				moreImageButton.setBackgroundResource(resourceId);
				break;

			default:
				break;
			}
		}
	}
}
