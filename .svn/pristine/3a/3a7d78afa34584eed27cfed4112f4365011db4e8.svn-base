package com.digione.zgb2b.fragment.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.BrandBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.PersistentService;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

/**
 * 产品导购第二级页面（手机品牌）
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductPhoneBrandFragment extends CommonBaseFragment {
	private ArrayList<BrandBean> brandList;
	private TableLayout tlLayout;
	private HorizontalScrollView scrowview;
	private ProductkListener listener;
	private CustomerJsonHttpResponseHandler<ArrayList<BrandBean>> jsonHttpResponseHandler;

	private void initData() {
		TableRow row1 = new TableRow(activity);
		TableRow row2 = new TableRow(activity);
		TableRow row3 = new TableRow(activity);
		TableRow row4 = new TableRow(activity);
		TableRow row5 = new TableRow(activity);

		for (int i = 0; i < brandList.size(); i++) {
			TextView tView = new TextView(activity);
			tView.setText(brandList.get(i).getBrandName());
			tView.setTextSize(14);
			tView.setTextColor(getResources().getColor(R.color.font_color));
			tView.setGravity(Gravity.CENTER);
			tView.setBackgroundResource(R.drawable.graybuttom_selector);
			tView.setTag(brandList.get(i));
			tView.setOnClickListener(listener);
			int j = i % 5;
			if (j == 0) {
				row1.addView(tView);
			} else if (j == 1) {
				row2.addView(tView);
			} else if (j == 2) {
				row3.addView(tView);
			} else if (j == 3) {
				row4.addView(tView);
			} else if (j == 4) {
				row5.addView(tView);
			}

		}
		tlLayout.addView(row1);
		tlLayout.addView(row2);
		tlLayout.addView(row3);
		tlLayout.addView(row4);
		tlLayout.addView(row5);
		// 自动滚动到最右边
		new Handler().postDelayed(new Runnable() {
			public void run() {
				// scrowview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
				scrowview.smoothScrollBy(150, 0); // 横向滑动150px
			}
		}, 800L);
	}

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<BrandBean>>(activity, true) {

			@Override
			public void processCallSuccess(ArrayList<BrandBean> outBean, String msg) {
				if (null != outBean) {
					brandList = outBean;
					if (!ZGApplication.getInstance().isSaveBrandData()) {
						PersistentService persistentService = new PersistentServiceImpl();
						persistentService.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
								Constants.DataFile.CommonInfoKey.BRAND_DATA, getEntityJsonString());
						ZGApplication.getInstance().setSaveBrandData(true);
					}
					initData();
				} else {
					ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}

		};

		View v = inflater.inflate(R.layout.product_phone_fragment_item, container, false);
		tlLayout = (TableLayout) v.findViewById(R.id.product_link_tl);
		scrowview = (HorizontalScrollView) v.findViewById(R.id.scrowview);
		TextView titleTextView = (TextView) v.findViewById(R.id.phone_title_tv);
		titleTextView.setText(R.string.product_brand);
		listener = new ProductkListener();

		if (ZGApplication.getInstance().isSaveBrandData()) {
			PersistentService persistentService = new PersistentServiceImpl();
			String dataString = persistentService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.BRAND_DATA);
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<BrandBean>>() {
			}.getType();
			brandList = gson.fromJson(dataString, listType);
			initData();
		} else {
			// 请求网络,获取用户信息
			HttpClient userClient = HttpClient.getInstall(activity);
			String url = Constants.Url.LOAD_BRAND_015;
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("returnSearchUrlFlag", "1");
			RequestParams params = new RequestParams(paramMap);
			userClient.postAsync(url, params, jsonHttpResponseHandler);
		}

		return v;
	}

	/**
	 * 点击商品分类，进入每个类别的明细界面
	 */
	class ProductkListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			BrandBean bean = (BrandBean) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString(ArgName.ProductList.TITLE, getString(R.string.phone) + "(" + bean.getBrandName() + ")");
			bundle.putString(ArgName.ProductList.URL, bean.getBrandSearchUrl()); // 请求的URL
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
		}
	}

}