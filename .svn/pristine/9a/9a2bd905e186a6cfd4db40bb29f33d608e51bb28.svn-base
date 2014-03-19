package com.digione.zgb2b.fragment.product;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digione.zgb2b.R;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.widget.TitleView;

/**
 * 产品导购第二级页面（手机,整合了品牌、价格、屏幕等等多个fragment）
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductPhoneAllFragment extends CommonBaseFragment {
	View v;
	private ProductPhoneBrandFragment phoneFragment;
	private ProductPhonePriceFragment priceFragment;
	private ProductPhoneScreenFragment screenFragment;
	private ProductPhoneNetTypeFragment netTypeFragment;
	private ProductPhoneOSFragment osFragment;

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.product_phone_all, container, false);
		FragmentManager childFragmentManager = getChildFragmentManager();
		FragmentTransaction ft = childFragmentManager.beginTransaction();

		phoneFragment = (ProductPhoneBrandFragment) childFragmentManager.findFragmentById(R.id.product_phonebrand_fm);
		priceFragment = (ProductPhonePriceFragment) childFragmentManager.findFragmentById(R.id.product_phoneprice_fm);
		screenFragment = (ProductPhoneScreenFragment) childFragmentManager.findFragmentById(R.id.product_phonescreen_fm);
		netTypeFragment = (ProductPhoneNetTypeFragment) childFragmentManager.findFragmentById(R.id.product_phonenettype_fm);
		osFragment = (ProductPhoneOSFragment) childFragmentManager.findFragmentById(R.id.product_phoneos_fm);

		if (phoneFragment == null) {
			phoneFragment = new ProductPhoneBrandFragment();
			priceFragment = new ProductPhonePriceFragment();
			screenFragment = new ProductPhoneScreenFragment();
			netTypeFragment = new ProductPhoneNetTypeFragment();
			osFragment = new ProductPhoneOSFragment();
			ft.add(R.id.product_phonebrand_fm, phoneFragment).add(R.id.product_phoneprice_fm, priceFragment)
					.add(R.id.product_phonescreen_fm, screenFragment).add(R.id.product_phonenettype_fm, netTypeFragment)
					.add(R.id.product_phoneos_fm, osFragment).commit();
		}

		TitleView titleView = (TitleView) v.findViewById(R.id.title);
		titleView.goneButton();
		titleView.setTitle(R.string.phone);
		return v;
	}

}