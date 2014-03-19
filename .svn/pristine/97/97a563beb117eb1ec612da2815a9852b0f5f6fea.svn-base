package com.digione.zgb2b.widget;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.JsonNoneOutBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.loopj.android.http.RequestParams;

public class MyAlertConfigDialogFragment extends DialogFragment {

	private Dialog dialog;
	private Activity mActivity;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> responseHandler;

	public static MyAlertConfigDialogFragment newInstance(String title, String channelId, String channelRemark) {
		MyAlertConfigDialogFragment frag = new MyAlertConfigDialogFragment();
		Bundle args = new Bundle();
		args.putString(ArgName.MyAlertConfigDialog.TITLE, title);
		args.putString(ArgName.MyAlertConfigDialog.CHANNEL_ID, channelId);
		args.putString(ArgName.MyAlertConfigDialog.CHANNEL_REMARK, channelRemark);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString(ArgName.MyAlertConfigDialog.TITLE);
		final String channelId = getArguments().getString(ArgName.MyAlertConfigDialog.CHANNEL_ID);
		final String channelRemark = getArguments().getString(ArgName.MyAlertConfigDialog.CHANNEL_REMARK);
		View view = LayoutInflater.from(mActivity).inflate(R.layout.my_alert_config_dialog, null);
		TextView tView = (TextView) view.findViewById(R.id.my_alert_config_dialog_tv);
		Button ok = (Button) view.findViewById(R.id.my_alert_config_dialog_ok);
		Button cancel = (Button) view.findViewById(R.id.my_alert_config_dialog_cancel);
		dialog = new Dialog(mActivity, R.style.MyDialog);
		dialog.setContentView(view);
		tView.setText(title);
		Window dialogWindow = dialog.getWindow();
		WindowManager m = dialogWindow.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.95
		dialogWindow.setAttributes(p);

		responseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(mActivity,
				ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, getArguments()) {

			@Override
			public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
				if (!"".equals(channelId)) {
					ZGApplication.getInstance().getUser().setInputChannelInfo(true);
				}
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_ACOUNT, null);
			}
		};

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				if (channelId.equals("") && channelRemark.equals("")) {
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_ACOUNT, null);
				} else {
					initData(channelId, channelRemark);
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});

		return dialog;
	}

	private void initData(String channelId, String channelRemark) {
		HttpClient httpClient = HttpClient.getInstall(mActivity);
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", channelId);
		map.put("channelRemark", channelRemark);
		RequestParams params = new RequestParams(map);
		httpClient.postAsync(Constants.Url.SHOP_CART_CHANNEL_038, params, responseHandler);
	}

}
