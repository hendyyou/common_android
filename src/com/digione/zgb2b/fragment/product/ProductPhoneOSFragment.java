package com.digione.zgb2b.fragment.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.OSBean;
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
 * 产品导购第二级页面（操作系统）
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductPhoneOSFragment extends CommonBaseFragment {
	private ArrayList<OSBean> osList;
	private TableLayout tlLayout;
	private CustomerJsonHttpResponseHandler<ArrayList<OSBean>> jsonHttpResponseHandler;

	private void initData() {
		ProductkListener listener = new ProductkListener();
		TableRow row1 = new TableRow(activity);
		TableRow row2 = new TableRow(activity);

		for (int i = 0; i < osList.size(); i++) {
			TextView tView = new TextView(activity);
			tView.setText(osList.get(i).getOperatingSystemName());
			tView.setTextSize(14);
			tView.setTextColor(getResources().getColor(R.color.font_color));
			tView.setGravity(Gravity.CENTER);
			tView.setBackgroundResource(R.drawable.graybuttom_selector);
			tView.setTag(osList.get(i));
			tView.setOnClickListener(listener);
			int j = i % 2;
			if (j == 0) {
				row1.addView(tView);
			} else if (j == 1) {
				row2.addView(tView);
			}
		}
		tlLayout.addView(row1);
		tlLayout.addView(row2);
	}

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<OSBean>>(activity, true) {

			@Override
			public void processCallSuccess(ArrayList<OSBean> outBean, String msg) {
				if (null != outBean) {
					osList = outBean;
					if (!ZGApplication.getInstance().isSaveOsData()) {
						PersistentService persistentService = new PersistentServiceImpl();
						persistentService.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
								Constants.DataFile.CommonInfoKey.OS_DATA, getEntityJsonString());
						ZGApplication.getInstance().setSaveOsData(true);
					}
					initData();
				} else {
					ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);

				}
			}
		};

		View v = inflater.inflate(R.layout.product_phone_fragment_item, container, false);
		tlLayout = (TableLayout) v.findViewById(R.id.product_link_tl);
		TextView titleTextView = (TextView) v.findViewById(R.id.phone_title_tv);
		titleTextView.setText(R.string.operation_system);

		if (ZGApplication.getInstance().isSaveOsData()) {
			PersistentService persistentService = new PersistentServiceImpl();
			String dataString = persistentService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.OS_DATA);
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<OSBean>>() {
			}.getType();
			osList = gson.fromJson(dataString, listType);
			initData();
		} else {
			// 请求网络,获取用户信息
			HttpClient userClient = HttpClient.getInstall(activity);
			String url = Constants.Url.LOAD_OPERATING_SYTEM_016;
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
			OSBean bean = (OSBean) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putString(ArgName.ProductList.TITLE, getString(R.string.phone) + "(" + bean.getOperatingSystemName()
					+ ")");
			bundle.putString(ArgName.ProductList.URL, bean.getOperatingSystemSearchUrl()); // 请求的URL
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
		}

	}

}