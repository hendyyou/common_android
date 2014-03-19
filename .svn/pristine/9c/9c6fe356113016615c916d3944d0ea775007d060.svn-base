package com.digione.zgb2b.fragment.shopcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.widget.TitleView;

public class ShopCartNoNormalFragment extends CommonBaseFragment {

	private TextView tView;
	private Button button;
	private TitleView titleView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shop_cart_no_normal, container, false);
		tView = (TextView) view.findViewById(R.id.shop_cart_no_normal_tv);
		button = (Button) view.findViewById(R.id.shop_cart_no_normal_btn);
		titleView = (TitleView) view.findViewById(R.id.title);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tView.setText(getResources().getString(R.string.shop_cart_no_data_now));
		titleView.setTitle(getResources().getString(R.string.shop_cart));
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChangeFragmentUtil.performProductTabClick();
			}
		});
	}
}
