package com.digione.zgb2b.fragment.shopcart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.MyAlertConfigDialogFragment;
import com.digione.zgb2b.widget.TitleView;

public class ShopCartChannelFragment extends CommonBaseFragment {

	private View view;
	private TitleView titleView;
	private EditText channelId;
	private EditText channelRemark;
	private Button okBtn;
	private Button skipBtn;
	private boolean isChannelId = false;
	private TextView tipsView;
	private TextView infoNomalView;
	private TextView remarkInfoView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shop_cart_acount, container, false);
		titleView = (TitleView) view.findViewById(R.id.shop_cart_channel_title);
		channelId = (EditText) view.findViewById(R.id.shop_cart_channel_num);
		channelRemark = (EditText) view.findViewById(R.id.shop_cart_channel_info);
		okBtn = (Button) view.findViewById(R.id.shop_cart_channel_btn);
		skipBtn = (Button) view.findViewById(R.id.shop_cart_channel_skip);
		tipsView = (TextView) view.findViewById(R.id.shop_cart_channel_tips);
		tipsView.getPaint().setFakeBoldText(true);
		infoNomalView = (TextView) view.findViewById(R.id.shop_cart_channel_info_nomal);
		infoNomalView.getPaint().setFakeBoldText(true);
		remarkInfoView = (TextView) view.findViewById(R.id.shop_cart_channel_remark_info);
		remarkInfoView.getPaint().setFakeBoldText(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (ZGApplication.getInstance().getUser().isInputChannelInfo()) {
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, null);
			return;
		}

		titleView.setTitle(getString(R.string.shop_cart_channel));
		channelId.addTextChangedListener(limitText);
		channelId.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					isChannelId = true;
				} else {
					isChannelId = false;
				}
			}
		});
		channelRemark.addTextChangedListener(limitText);
		okBtn.setOnClickListener(mOnClickListener);
		skipBtn.setOnClickListener(mOnClickListener);
	}

	private TextWatcher limitText = new TextWatcher() {
		private CharSequence temp;
		private int selectionStart;
		private int selectionEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (isChannelId) {
				selectionStart = channelId.getSelectionStart();
				selectionEnd = channelId.getSelectionEnd();
			} else {
				selectionStart = channelRemark.getSelectionStart();
				selectionEnd = channelRemark.getSelectionEnd();
			}

			if (temp.length() > 64) {
				s.delete(selectionStart - 1, selectionEnd);
				int tempSelection = selectionStart;
				if (isChannelId) {
					channelId.setText(s);
					channelId.setSelection(tempSelection);
				} else {
					channelRemark.setText(s);
					channelRemark.setSelection(tempSelection);
				}
			}
		}
	};

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 关闭软键盘
			SystemUtil.closeSoftInput(activity);
			if (v.getId() == R.id.shop_cart_channel_btn) {
				String channelIdStr = channelId.getText().toString();
				String channelRemarkStr = channelRemark.getText().toString();
				if (channelRemarkStr.equals("") && channelIdStr.equals("")) {
					ToastUtil.showToast(activity, getResources().getString(R.string.shop_cart_channel_no_content), 1000);
					return;
				}
				String info = "";
				if (channelRemarkStr.length() > 0 && channelIdStr.length() == 0) {
					info = getResources().getString(R.string.shop_cart_channel_remark_info) + channelRemarkStr
							+ getResources().getString(R.string.shop_cart_channel_suc_edit);
				} else if (channelRemarkStr.length() > 0 && channelIdStr.length() > 0) {
					info = getResources().getString(R.string.shop_cart_channel_info_nomal) + channelIdStr
							+ getResources().getString(R.string.shop_cart_channel_remark) + channelRemarkStr
							+ getResources().getString(R.string.shop_cart_channel_suc_edit);
				} else if (channelRemarkStr.length() == 0 && channelIdStr.length() > 0) {
					info = getResources().getString(R.string.shop_cart_channel_info_nomal) + channelIdStr
							+ getResources().getString(R.string.shop_cart_channel_suc_edit);
				}
				MyAlertConfigDialogFragment newFragment = MyAlertConfigDialogFragment.newInstance(info, channelIdStr,
						channelRemarkStr);
				newFragment.show(getChildFragmentManager(), "dialog");
			} else if (v.getId() == R.id.shop_cart_channel_skip) {
				String info = getResources().getString(R.string.shop_cart_channel_skip);
				MyAlertConfigDialogFragment newFragment = MyAlertConfigDialogFragment.newInstance(info, "", "");
				newFragment.show(getChildFragmentManager(), "dialog");
			}
		}
	};

}
