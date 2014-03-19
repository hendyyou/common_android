package com.digione.zgb2b.fragment.workbench;

import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.RequestErrorBean;
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
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

/**
 * 用户登录界面
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class UserLoginFragment extends CommonBaseFragment {

	private View v;
	private EditText usernameET;
	private EditText passwordET;
	private CheckBox rememberPasswordCB;
	// 用户名
	private String username = "";

	// 密码
	private String password = "";
	private boolean isRememberPassword = false;
	// 创建SharePreference
	private SharedPreferences mSharedPreferences;

	private final String HAVAED_LOGINED = "07";

	private int fragmentId = -1;

	private UserBean currentUserBean;
	private String loginReturnMsg;

	private CustomerJsonHttpResponseHandler<UserBean> jsonHttpResponseHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.REFILL_SHOP_CART_SUCCESS: {
				// 记录登录状态
				ZGApplication.getInstance().setLogin(true);
				// 去获取用户信息
				ZGApplication.getInstance().setUser(currentUserBean);
				// 显示购物数量
				ShopCartUtils.getInstance(mHandler).getShopCartNum(activity);
				ToastUtil.showToast(activity, loginReturnMsg, ToastUtil.LENGTH_SHORT);
				// 重置保存首页广告标识
				ZGApplication.getInstance().setSaveGHomeBean(false);
				// 切换界面
				ChangeFragmentUtil.removeFragment();
				break;
			}
			case MsgCode.REFILL_SHOP_CART_FAILURE: {
				ChangeFragmentUtil.changeToUserLoginFragment(fragmentId, activity, null, null);
				break;
			}
			default:
				break;
			}
		}
	};

	private void loginSuceed(UserBean outBean, String msg) {
		currentUserBean = outBean;
		loginReturnMsg = msg;
		ShopCartUtils.getInstance(mHandler).resetShopcartData(activity, currentUserBean);
	}

	private void resetUserLoginStatus() {
		ZGApplication.getInstance().setLogin(false);
		ZGApplication.getInstance().setUser(null);
		ZGApplication.getInstance().setResetShopCart(false);
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = activity.getSharedPreferences(Constants.DataFile.COMMON_INFO, Activity.MODE_PRIVATE);
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.user_login, container, false);
		/**
		 * 隐藏输入键盘
		 */
		v.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				SystemUtil.closeSoftInput(activity);
				return false;
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (v != null) {

			jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<UserBean>(activity) {

				@Override
				public void processBeforeHttpSuccess() {
					super.processBeforeHttpSuccess();
					// 无论登录是否成功,都记住密码
					if (isRememberPassword) {
						SharedPreferences.Editor saveEditor = mSharedPreferences.edit();
						saveEditor.putString(Constants.DataFile.CommonInfoKey.LOGIN_PWD, password);
						saveEditor.putBoolean(Constants.DataFile.CommonInfoKey.IS_REMEMBER_PWD, isRememberPassword);
						saveEditor.commit();
					}
					fragmentId = getArguments().getInt(ArgName.UserLogin.FRAGMENT_ID);
					if (fragmentId <= 0) {
						fragmentId = ChangeFragmentUtil.WORKBENCH_CLASSIFY;
					}
				}

				@Override
				public void processCallSuccess(UserBean outBean, String msg) {
					loginSuceed(outBean, msg);
				}

				@Override
				public void processCallFailure(UserBean outBean, String failureCode, String msg) {
					// 根据错误状态做相应的处理
					if (failureCode.equals(HAVAED_LOGINED)) {
						// 用户已经登录，无需再次进行登录，但是需要重置所有登录的客户端状态
						msg = getString(R.string.login_success);
						loginSuceed(outBean, msg);
					} else {
						resetUserLoginStatus();
						ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
					}
				}

				@Override
				public void processAfterHttpSuccess(UserBean outBean, Integer msgCode, String failureCode, String msg) {
					super.processAfterHttpSuccess(outBean, msgCode, failureCode, msg);
				}

				@Override
				public void processHttpFailure(Throwable e, RequestErrorBean outBean) {
					resetUserLoginStatus();
					super.processHttpFailure(e, outBean);
				}
			};

			// 获取用户名
			usernameET = (EditText) v.findViewById(R.id.username_et);

			// 获取密码
			passwordET = (EditText) v.findViewById(R.id.password_et);

			// 是否选择记住密码
			rememberPasswordCB = (CheckBox) v.findViewById(R.id.remember_password_cb);
			rememberPasswordCB.setOnCheckedChangeListener(new MyCheckBoxListener());

			// 从文件获取用户名,然后设置到username EditText
			String uname = mSharedPreferences.getString(Constants.DataFile.CommonInfoKey.LOGIN_NAME, "").trim();
			usernameET.setText(uname);

			// 从文件中获取是否选择记住密码,如果记住密码,则把密码从文件取出,设置到EditText中
			boolean rememberPassword = mSharedPreferences
					.getBoolean(Constants.DataFile.CommonInfoKey.IS_REMEMBER_PWD, false);
			if (rememberPassword) {
				String passwd = mSharedPreferences.getString(Constants.DataFile.CommonInfoKey.LOGIN_PWD, "");
				passwordET.setText(passwd);
				rememberPasswordCB.setChecked(rememberPassword);
			}

			ImageButton loginBtn = (ImageButton) v.findViewById(R.id.login_ibtn);
			loginBtn.setOnClickListener(new BtnOnClickListener());

		}
	}

	/**
	 * 点击商品分类，进入每个类别的明细界面
	 */
	class BtnOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			username = usernameET.getText().toString().trim();
			password = passwordET.getText().toString().trim();
			isRememberPassword = rememberPasswordCB.isChecked();

			// 关闭软键盘
			SystemUtil.closeSoftInput(activity);

			// 用户为空验证
			if (username.equals("") || username == "") {
				ToastUtil.showToast(activity, R.string.input_username, ToastUtil.LENGTH_SHORT);
				return;
			}

			// 密码为空验证
			if (password.equals("") || password == "") {
				ToastUtil.showToast(activity, R.string.input_password, ToastUtil.LENGTH_SHORT);
				return;
			}

			if (HttpUtil.isHasNetwork(activity)) {
				// 保存用户名
				SharedPreferences.Editor saveEditor = mSharedPreferences.edit();
				saveEditor.putString(Constants.DataFile.CommonInfoKey.LOGIN_NAME, username);
				saveEditor.commit();

				String url = Constants.Url.LOGIN_001;
				HttpClient client = HttpClient.getInstall(activity);
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("loginName", username);
				paramMap.put("password", password);
				RequestParams params = new RequestParams(paramMap);

				client.postAsync(url, params, jsonHttpResponseHandler);
			} else {
				ToastUtil.showToast(activity, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);
				return;
			}

		}
	}

	/**
	 * CHeckBox 事件
	 */
	private class MyCheckBoxListener implements CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			SharedPreferences.Editor editor = mSharedPreferences.edit();
			// 删除密码
			if (!isChecked) {
				editor.remove(Constants.DataFile.CommonInfoKey.LOGIN_PWD);
				editor.remove(Constants.DataFile.CommonInfoKey.IS_REMEMBER_PWD);
				editor.commit();
			}

			// 保持密码
			if (isChecked && !password.equals("") && password != "") {
				editor.putString(Constants.DataFile.CommonInfoKey.LOGIN_PWD, password);
				editor.putBoolean(Constants.DataFile.CommonInfoKey.IS_REMEMBER_PWD, isRememberPassword);
				editor.commit();
			}
		}

	}

}