package com.digione.zgb2b;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.digione.zgb2b.bean.shopcart.ShopCartBean;
import com.digione.zgb2b.bean.workbench.DeliveryAddressBean;
import com.digione.zgb2b.bean.workbench.UserBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.ExceptionHandler;
import com.digione.zgb2b.common.ExitAppHandler;
import com.digione.zgb2b.common.HttpClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ZGApplication extends Application {

	private static ZGApplication instance;

	private Stack<Activity> activityStack;

	private ExceptionHandler exceptionHandler;

	// 是否登录标识
	private boolean isLogin;

	// 用户
	private UserBean user;

	private ProgressDialog dialog; // 忙等时的动画
	private static int dialogCount = 0; // 动画对话框的个数
	private FragmentManager fragmentManager;

	private Integer consId;// 门店ID

	private boolean isResetShopCart;// 登录后重新获取购物车数据

	private DeliveryAddressBean addressBean;// 传递修改地址

	private boolean isSaveGHomeBean;// 首页广告条是否已读取
	private boolean isSaveTHomeBean;// 首页推荐是否已读取
	private boolean isSaveRHomeBean;// 首页热门是否已读取

	private ShopCartBean shopCartBean;

	private boolean isSaveBrandData;
	private boolean isSaveNetTypeData;
	private boolean isSaveOsData;
	private boolean isSavePriceData;
	private boolean isSaveScreenData;

	// 是否需要代理
	private static boolean isProxy = false;
	// 网络连接代理ip
	private static String proxyHost;
	// 网络连接代理端口
	private static int proxyPort;
	private static int screenWidth;
	private static int screenHeight;
	private static int screenDensity;

	/**
	 * 开发模式定义：false
	 */
	private static boolean isDevMode = false;

	public static boolean isDevMode() {
		return isDevMode;
	}

	/**
	 * 记录是否可以进行清除回退键栈信息的标识
	 */
	private static boolean isCanClearPopBackStack = false;

	public static boolean isCanClearPopBackStack() {
		return isCanClearPopBackStack;
	}

	public static void setCanClearPopBackStack(boolean isCanClearPopBackStack) {
		ZGApplication.isCanClearPopBackStack = isCanClearPopBackStack;
	}

	public ZGApplication() {
		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " ZGApplication()");
		}
		isLogin = false;
		user = null;
		fragmentManager = null;
		consId = -1;// 门店ID
		isResetShopCart = false;// 登录后重新获取购物车数据
		addressBean = null;// 传递修改地址
		isSaveGHomeBean = false;// 首页广告条是否已读取
		isSaveTHomeBean = false;// 首页推荐是否已读取
		isSaveRHomeBean = false;// 首页热门是否已读取
		shopCartBean = null;
		isSaveBrandData = false;
		isSaveNetTypeData = false;
		isSaveOsData = false;
		isSavePriceData = false;
		isSaveScreenData = false;
		isProxy = false;
		proxyHost = null;
		proxyPort = 0;
		screenWidth = 0;
		screenHeight = 0;
		screenDensity = 0;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Context context = getApplicationContext();
		String devModeString = context.getResources().getString(R.string.dev_mode);
		isDevMode = "true".equals(devModeString);

		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onCreate()");
		}
		exceptionHandler = ExceptionHandler.getInstance();

		// 注册exceptionHandler
		exceptionHandler.init(context);
		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onCreate() 注册exceptionHandler");
		}

		// 发送以前没发送的报告(可选)
		new Thread() {
			public void run() {
				exceptionHandler.sendPreviousReportsToServer();
			}
		}.start();
		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onCreate() 发送以前没发送的报告");
		}

		initImageLoader(context);
		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onCreate() initImageLoader");
		}
		instance = this;
	}

	@Override
	public void onTerminate() {
		if (isDevMode) {
			Log.d(Constants.TAG, this.getClass().getSimpleName() + " onTerminate()");
		}
		// 退出登录
		ExitAppHandler exitAppHandler = new ExitAppHandler(instance);
		exitAppHandler.exit();
		super.onTerminate();
	}

	public static ZGApplication getInstance() {
		if (null == instance) {
			if (isDevMode) {
				Log.d(Constants.TAG, " getInstance()");
			}
			instance = new ZGApplication();

		}
		return instance;
	}

	/**
	 * - 退出栈顶Activity
	 * 
	 * @param activity
	 *            [Activity类]
	 */
	public void popActivity(Activity activity) {
		activity.finish();
		activityStack.remove(activity);
		activity = null;
	}

	/**
	 * 获得当前栈顶Activity
	 * 
	 * @return Activity
	 */
	public Activity currentActivity() {
		Activity activity = null;
		if (activityStack != null) {
			if (!activityStack.empty())
				activity = activityStack.lastElement();
		}
		return activity;
	}

	/**
	 * 将当前Activity推入栈中
	 * 
	 * @param activity
	 *            [Activity类]
	 */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);

	}

	public void exit() {
		// 退出登录
		ExitAppHandler exitAppHandler = new ExitAppHandler(instance);
		exitAppHandler.exit();
		HttpClient.clearCookie();
		if (activityStack != null) {
			for (Activity activity : activityStack) {
				activity.finish();
				activity = null;
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	public static void initImageLoader(Context context) {
		// 获取应用缓存大小
		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		memClass = memClass > 32 ? 32 : memClass;
		// 使用可用内存的1/8作为图片缓存
		final int cacheSize = 1024 * 1024 * memClass / 8;

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCacheSize(cacheSize).build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 显示进度条
	 * 
	 * @param context
	 */

	public void showDialog(Context context) {

		if (dialog == null || !dialog.isShowing()) {
			dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), true, true);
		}
		while (!dialog.isShowing()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dialogCount++;

	}

	/**
	 * 关闭所有进度条
	 */
	public void dismissDialog() {
		dialogCount = 0;
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	/**
	 * 关闭一个进度条
	 */
	public void dismissSingleDialog() {
		dialogCount--;
		if (dialogCount <= 0 && dialog != null) {
			dialog.dismiss();
		}
	}

	public Stack<Activity> getActivityStack() {
		return activityStack;
	}

	public void setActivityStack(Stack<Activity> activityStack) {
		this.activityStack = activityStack;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public ProgressDialog getDialog() {
		return dialog;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}

	public FragmentManager getFragmentManager() {
		return fragmentManager;
	}

	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

	public Integer getConsId() {
		return consId;
	}

	public void setConsId(Integer consId) {
		this.consId = consId;
	}

	public boolean isResetShopCart() {
		return isResetShopCart;
	}

	public void setResetShopCart(boolean isResetShopCart) {
		this.isResetShopCart = isResetShopCart;
	}

	public DeliveryAddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(DeliveryAddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public boolean isSaveGHomeBean() {
		return isSaveGHomeBean;
	}

	public void setSaveGHomeBean(boolean isSaveGHomeBean) {
		this.isSaveGHomeBean = isSaveGHomeBean;
	}

	public boolean isSaveTHomeBean() {
		return isSaveTHomeBean;
	}

	public void setSaveTHomeBean(boolean isSaveTHomeBean) {
		this.isSaveTHomeBean = isSaveTHomeBean;
	}

	public boolean isSaveRHomeBean() {
		return isSaveRHomeBean;
	}

	public void setSaveRHomeBean(boolean isSaveRHomeBean) {
		this.isSaveRHomeBean = isSaveRHomeBean;
	}

	public ShopCartBean getShopCartBean() {
		return shopCartBean;
	}

	public void setShopCartBean(ShopCartBean shopCartBean) {
		this.shopCartBean = shopCartBean;
	}

	public boolean isSaveBrandData() {
		return isSaveBrandData;
	}

	public void setSaveBrandData(boolean isSaveBrandData) {
		this.isSaveBrandData = isSaveBrandData;
	}

	public boolean isSaveNetTypeData() {
		return isSaveNetTypeData;
	}

	public void setSaveNetTypeData(boolean isSaveNetTypeData) {
		this.isSaveNetTypeData = isSaveNetTypeData;
	}

	public boolean isSaveOsData() {
		return isSaveOsData;
	}

	public void setSaveOsData(boolean isSaveOsData) {
		this.isSaveOsData = isSaveOsData;
	}

	public boolean isSavePriceData() {
		return isSavePriceData;
	}

	public void setSavePriceData(boolean isSavePriceData) {
		this.isSavePriceData = isSavePriceData;
	}

	public boolean isSaveScreenData() {
		return isSaveScreenData;
	}

	public void setSaveScreenData(boolean isSaveScreenData) {
		this.isSaveScreenData = isSaveScreenData;
	}

	public static boolean isProxy() {
		return isProxy;
	}

	public static void setProxy(boolean isProxy) {
		ZGApplication.isProxy = isProxy;
	}

	public static String getProxyHost() {
		return proxyHost;
	}

	public static void setProxyHost(String proxyHost) {
		ZGApplication.proxyHost = proxyHost;
	}

	public static int getProxyPort() {
		return proxyPort;
	}

	public static void setProxyPort(int proxyPort) {
		ZGApplication.proxyPort = proxyPort;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		ZGApplication.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		ZGApplication.screenHeight = screenHeight;
	}

	public static int getScreenDensity() {
		return screenDensity;
	}

	public static void setScreenDensity(int screenDensity) {
		ZGApplication.screenDensity = screenDensity;
	}

}
