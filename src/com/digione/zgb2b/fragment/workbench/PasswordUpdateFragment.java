package com.digione.zgb2b.fragment.workbench;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.JsonNoneOutBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.HttpUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;

/**
 * 用户登录界面
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-11
 */
public class PasswordUpdateFragment extends CommonBaseFragment {

	private View v;
	private EditText oldPasswordET;
	private EditText oneNewPasswrodET;
	private EditText twoNewPasswrodET;
	private Button submitBtn;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> jsonHttpResponseHandler;

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.password_update, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (v != null) {

			jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(activity,
					ChangeFragmentUtil.WORKBENCH_UPDATE_PASSWORD, getArguments()) {

				@Override
				public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
					ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_CLASSIFY, new Bundle());
				}
			};

			// 顶部工具栏
			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.update_password);
			// 隐藏两边的button
			mTitle.hiddenButton();

			// 旧密码
			oldPasswordET = (EditText) v.findViewById(R.id.workbench_old_password_et);

			// 新密码一
			oneNewPasswrodET = (EditText) v.findViewById(R.id.workbench_new_one_password_et);

			// 新密码二
			twoNewPasswrodET = (EditText) v.findViewById(R.id.workbench_new_two_password_et);

			twoNewPasswrodET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {

					if (R.id.workbench_new_two_password_et == v.getId() && !hasFocus) {
						String newPassword = oneNewPasswrodET.getText().toString();
						String confirePassword = twoNewPasswrodET.getText().toString();
						// 判断两次输入的密码是否一致
						if (!newPassword.equals(confirePassword)) {
							ToastUtil.showToast(activity, R.string.input_two_diffent_password, ToastUtil.LENGTH_SHORT);
							oneNewPasswrodET.setFocusable(true);
							oneNewPasswrodET.setText("");
							twoNewPasswrodET.setText("");
						}
					}
				}
			});
			// 提交按钮
			submitBtn = (Button) v.findViewById(R.id.workbench_uppassword_submint_btn);

			// 注册事件
			submitBtn.setOnClickListener(new BtnOnClickListener());

		}
	}

	/**
	 * 点击商品分类，进入每个类别的明细界面
	 */
	class BtnOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			String oldPassword = oldPasswordET.getText().toString().trim();
			String newPassword = oneNewPasswrodET.getText().toString().trim();
			String confirePassword = twoNewPasswrodET.getText().toString().trim();
			// 关闭软键盘
			SystemUtil.closeSoftInput(activity);

			if (oldPassword.equals("")) {
				ToastUtil.showToast(activity, R.string.old_password_null, ToastUtil.LENGTH_SHORT);
				oldPasswordET.setFocusable(true);
				oldPasswordET.setText("");
				oneNewPasswrodET.setText("");
				twoNewPasswrodET.setText("");
				return;
			}

			if (newPassword.equals("")) {
				ToastUtil.showToast(activity, R.string.new_password_null, ToastUtil.LENGTH_SHORT);
				oneNewPasswrodET.setFocusable(true);
				oneNewPasswrodET.setText("");
				twoNewPasswrodET.setText("");
				return;
			}

			if (confirePassword.equals("")) {
				ToastUtil.showToast(activity, R.string.confirm_password_null, ToastUtil.LENGTH_SHORT);
				twoNewPasswrodET.setFocusable(true);
				twoNewPasswrodET.setText("");
				return;
			}

			// 判断新密码和确认密码是否相同
			if (!newPassword.equals(confirePassword)) {
				ToastUtil.showToast(activity, R.string.input_two_diffent_password, ToastUtil.LENGTH_SHORT);
				oneNewPasswrodET.setFocusable(true);
				oneNewPasswrodET.setText("");
				twoNewPasswrodET.setText("");
				return;
			}

			// 判断新密码和旧密码是否相同
			if (oldPassword.equals(confirePassword)) {
				ToastUtil.showToast(activity, R.string.old_new_diffent_password, ToastUtil.LENGTH_SHORT);
				oldPasswordET.setFocusable(true);
				oldPasswordET.setText("");
				oneNewPasswrodET.setText("");
				twoNewPasswrodET.setText("");
				return;
			}

			if (HttpUtil.isHasNetwork(activity)) {

				String url = Constants.Url.PASSWORD_UP_004;
				HttpClient client = HttpClient.getInstall(activity);
				// 存放参数
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("oldPassword", oldPassword);
				paramMap.put("newPassword", newPassword);

				RequestParams params = new RequestParams(paramMap);

				client.postAsync(url.toString(), params, jsonHttpResponseHandler);
			} else {
				ToastUtil.showToast(activity, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);
				return;
			}

		}
	}

}