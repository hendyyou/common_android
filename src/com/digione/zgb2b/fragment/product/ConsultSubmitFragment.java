package com.digione.zgb2b.fragment.product;

import java.util.HashMap;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.CreateConsultOutBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;

/**
 * 产品导购--提交咨询
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ConsultSubmitFragment extends CommonBaseFragment {
	private EditText contentEditText; // 提交的内容
	private Button submitButton; // 提交按钮
	private CustomerJsonHttpResponseHandler<CreateConsultOutBean> jsonHttpResponseHandler;

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!ZGApplication.getInstance().isLogin()) {
			Bundle bundle = getArguments();
			if (bundle.getBoolean(ArgName.ConsultSubmit.IS_POP_BACK)) {
				bundle.putBoolean(ArgName.ConsultSubmit.IS_POP_BACK, false);
				ChangeFragmentUtil.removeFragment();
				return null;
			}
			bundle.putBoolean(ArgName.ConsultSubmit.IS_POP_BACK, true);
			ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.PRODUCT_CONSULT_SUBMIT, activity, null, bundle);
			return null;
		}

		View v = inflater.inflate(R.layout.product_consult_submit, container, false);
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				SystemUtil.closeSoftInput(activity);
				return false;
			}
		});
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<CreateConsultOutBean>(activity,
				ChangeFragmentUtil.PRODUCT_CONSULT_SUBMIT, getArguments()) {

			@Override
			public void processCallSuccess(CreateConsultOutBean outBean, String msg) {
				Bundle args = getArguments();
				args.putBoolean(ArgName.ProductBrief.FROM_CONSULT, true);
				ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
				ChangeFragmentUtil.removeFragment();
			}
		};

		TitleView titleTextView = (TitleView) v.findViewById(R.id.title);
		titleTextView.setTitle(getArguments().getString("title"));
		titleTextView.getmTitle().setGravity(Gravity.LEFT);
		titleTextView.goneLeftButton();
		titleTextView.getmTitle().setPadding(SystemUtil.dip2px(titleTextView.getContext(), 10), 0, 0, 0);
		submitButton = (Button) v.findViewById(R.id.product_submit_btn);
		contentEditText = (EditText) v.findViewById(R.id.product_qcontent_ev);
		contentEditText.setGravity(Gravity.TOP);
		// 判断是否超出150字
		contentEditText.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private boolean isEdit = true;

			@Override
			public void afterTextChanged(Editable s) {
				if (temp.length() > 150) {
					isEdit = false;
					s.delete(temp.length() - 1, temp.length());
					contentEditText.setText(s);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				if (isEdit == false) {
					ToastUtil.showToast(activity, getString(R.string.consult_too_much_word), Toast.LENGTH_SHORT);
					Editable etext = contentEditText.getText();
					int pos = etext.length();
					Selection.setSelection(etext, pos);
				}
			}
		});

		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 关闭软键盘
				SystemUtil.closeSoftInput(activity);
				// 请求网络,获取用户信息
				if (contentEditText.getText() == null || "".equals(contentEditText.getText().toString().trim())) {
					ToastUtil.showToast(activity, getString(R.string.product_consult_tip), ToastUtil.LENGTH_SHORT);
					return;
				}
				HttpClient userClient = HttpClient.getInstall(activity);
				Integer productId = getArguments().getInt(ArgName.ConsultSubmit.PRODUCT_ID);

				String url = Constants.Url.COUSULTING_CREATE_033.replace("${id}", productId.toString());
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("question", contentEditText.getText().toString());
				RequestParams params = new RequestParams(paramMap);
				userClient.postAsync(url, params, jsonHttpResponseHandler);
			}
		});

		return v;
	}

}