package com.digione.zgb2b.fragment.product;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.widget.TitleView;

/**
 * 产品导购第一级页面
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductClassyFragment extends CommonBaseFragment {

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.product_classify, container, false);
		LinearLayout phoneLayout = (LinearLayout) v.findViewById(R.id.product_phone_ll);
		LinearLayout attachmentLayout = (LinearLayout) v.findViewById(R.id.product_attachment_ll);
		LinearLayout cloudcardLayout = (LinearLayout) v.findViewById(R.id.product_cloudcard_ll);
		OnClickListener listener = new ProductkListener();
		phoneLayout.setOnClickListener(listener);
		attachmentLayout.setOnClickListener(listener);
		cloudcardLayout.setOnClickListener(listener);
		TitleView titleView = (TitleView) v.findViewById(R.id.title);
		titleView.goneButton();
		titleView.setTitle(R.string.product_guide);
		return v;
	}

	/**
	 * 点击商品分类，进入每个类别的下一级界面
	 */
	class ProductkListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String url = Constants.Url.PRODUCT_SEARCH_020;
			Bundle bundle = new Bundle();
			if (v.getId() == R.id.product_phone_ll) {
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_PHONE, bundle);
			} else if (v.getId() == R.id.product_attachment_ll) {
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("typeName", "accessory");
				bundle.putString(ArgName.ProductList.TITLE, getString(R.string.attachment));
				bundle.putString(ArgName.ProductList.URL, url); // 请求的URL
				bundle.putBoolean(ArgName.ProductList.SHOW_SORT, false);// 终端配饰不排序
				bundle.putSerializable(ArgName.ProductList.PARAM, paramMap); // 需要带的参数
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
			} else {
				bundle.putString(ArgName.ProductList.TITLE, getString(R.string.cloudcard));
				bundle.putString(ArgName.ProductList.URL, getString(R.string.clould_url)); // 请求的URL
				bundle.putBoolean(ArgName.ProductList.SHOW_SORT, false);// 天翼云卡不能排序
				HashMap<String, String> paramMap = new HashMap<String, String>();
				bundle.putSerializable(ArgName.ProductList.PARAM, paramMap); // 需要带的参数
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
			}
		}

	}

}