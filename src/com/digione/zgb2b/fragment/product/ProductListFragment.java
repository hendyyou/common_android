package com.digione.zgb2b.fragment.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.product.ProductListAdapter;
import com.digione.zgb2b.bean.JsonPagerEntityBean;
import com.digione.zgb2b.bean.product.ProductBean;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.BrowseRecordDataUtils;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * 产品导购第三级页面（产品列表）
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductListFragment extends CommonBaseFragment {
	private List<ProductBean> productList;
	private int count = 1; // 请求的页数
	private int totalcount; // 商品总数
	private int pageCount; // 商品总页数
	private int selectposition = 0; // 滚动加载后的焦点位置
	private ListView list; // 数据内容

	// 排序按钮的背景
	private LinearLayout productSailLayout;
	private LinearLayout productPriceLayout;
	private LinearLayout productTimeLayout;

	private TextView sailButton; // 销量排序按钮
	private TextView priceButton; // 价格排序按钮
	private TextView timeButton; // 上市时间排序按钮
	private int currentButtonIndex = 0;

	private ImageView sailArrowView;// 销量箭头
	private ImageView priceArrowView;// 价格箭头
	private ImageView timeArrowView;// 上市时间箭头
	private LinearLayout productSortLayout; // 整个排序按钮区域
	private String[] sorts = { "desc", "asc", "desc" };
	private int preCheckId = R.id.product_sail_layout;
	private ProductListAdapter adapter;
	private String url; // 本界面请求的URL
	private RequestParams params = new RequestParams(); // 本界面请求所需带的参数
	private CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ProductBean>> jsonHttpResponseHandler;

	/**
	 * 滚动加载监听器。列表被滑动时触发
	 */
	private OnScrollListener scrolllistener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// 当不滚动时
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				// 判断是否滚动到底部
				if (view.getLastVisiblePosition() == view.getCount() - 1) {
					if (count < pageCount && pageCount != 1) {
						selectposition = list.getFirstVisiblePosition();
						params.put("targetpage", String.valueOf(++count));
						HttpClient userClient = HttpClient.getInstall(activity);
						userClient.postAsync(url, params, jsonHttpResponseHandler);
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};
	private OnClickListener onSortListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			productList.clear();
			count = 1;
			selectposition = 0;
			params.put("targetpage", "1");
			productSailLayout.setBackgroundResource(R.color.transparent);
			productPriceLayout.setBackgroundResource(R.color.transparent);
			productTimeLayout.setBackgroundResource(R.color.transparent);

			if (v == productSailLayout || v == sailButton) {
				// 选中销量排序
				// ---

				if (preCheckId == R.id.product_sail_layout) {
					changeSort(0, sailArrowView); // 是否要换排序
				}
				preCheckId = R.id.product_sail_layout;

				params.put("orderBy", "rankingsale");
				params.put("sortType", sorts[0]);

				productSailLayout.setBackgroundResource(R.drawable.product_sort_btn);
				currentButtonIndex = 0;
			} else if (v == productPriceLayout || v == priceButton) {

				// 选中价格排序
				// -----

				if (preCheckId == R.id.product_price_layout) {
					changeSort(1, priceArrowView); // 是否要换排序
				}
				preCheckId = R.id.product_price_layout;

				productPriceLayout.setBackgroundResource(R.drawable.product_sort_btn);

				params.put("orderBy", "price");
				params.put("sortType", sorts[1]);
				currentButtonIndex = 1;

			} else if (v == productTimeLayout || v == timeButton) {

				// 选中上市时间排序
				// ------

				if (preCheckId == R.id.product_time_layout) {
					changeSort(2, timeArrowView); // 是否要换排序
				}
				preCheckId = R.id.product_time_layout;

				productTimeLayout.setBackgroundResource(R.drawable.product_sort_btn);

				params.put("orderBy", "marketTime");
				params.put("sortType", sorts[2]);
				currentButtonIndex = 2;

			}
			HttpClient userClient = HttpClient.getInstall(activity);
			userClient.postAsync(url, params, jsonHttpResponseHandler);
		}

	};

	private void changeSort(int index, ImageView arrow) {
		if ("asc".equals(sorts[index])) {
			sorts[index] = "desc";
			arrow.setImageResource(R.drawable.desc_arrow);
		} else {
			sorts[index] = "asc";
			arrow.setImageResource(R.drawable.asc_arrow);
		}
	}

	// 重置当前按钮状态。用于back返回
	private void setCurrentButton() {
		LinearLayout[] layouts = { productSailLayout, productPriceLayout, productTimeLayout };
		for (int j = 0; j < layouts.length; j++) {
			if (j == currentButtonIndex) {
				layouts[j].setBackgroundResource(R.drawable.product_sort_btn);
			} else {
				layouts[j].setBackgroundResource(R.color.transparent);
			}
		}
		ImageView[] arrowsImageViews = { sailArrowView, priceArrowView, timeArrowView };
		for (int i = 0; i < sorts.length; i++) {
			if ("asc".equals(sorts[i])) {
				arrowsImageViews[i].setImageResource(R.drawable.asc_arrow);
			} else {
				arrowsImageViews[i].setImageResource(R.drawable.desc_arrow);
			}
		}
	}

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		boolean isLocalData = bundle.getBoolean(ArgName.ProductList.IS_LOCAL_DATA);
		if (isLocalData && !ZGApplication.getInstance().isLogin()) {
			if (bundle.getBoolean(ArgName.ProductList.IS_POP_BACK)) {
				bundle.putBoolean(ArgName.ProductList.IS_POP_BACK, false);
				ChangeFragmentUtil.removeFragment();
				return null;
			}
			bundle.putBoolean(ArgName.ProductList.IS_POP_BACK, true);
			ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.PRODUCT_LIST, activity, getResources()
					.getString(R.string.zg_login_hit), bundle);
			return null;
		}

		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ProductBean>>(activity,
				ChangeFragmentUtil.PRODUCT_LIST, bundle) {

			@Override
			public void processCallSuccess(JsonPagerEntityBean<ProductBean> outBean, String msg) {
				if (null != outBean) {
					totalcount = outBean.getTotalCount();
					pageCount = outBean.getPageCount();
					if (outBean.getList() != null) {
						productList.addAll(outBean.getList());
					}
					if (productList.isEmpty()) {
						count = 1;
					}
					if (productList.size() != 0) {
						if (adapter != null) {
							adapter = null;
						}
						setListViewAdapter();
					}
				} else {
					ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}
		};

		View v = inflater.inflate(R.layout.product_list, container, false);

		TitleView titleTextView = (TitleView) v.findViewById(R.id.title);
		titleTextView.goneButton();
		titleTextView.setTitle(bundle.getString(ArgName.ProductList.TITLE));

		productSortLayout = (LinearLayout) v.findViewById(R.id.product_sort_rl);
		list = (ListView) v.findViewById(R.id.MyListView);
		list.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true, scrolllistener));

		productSailLayout = (LinearLayout) v.findViewById(R.id.product_sail_layout);
		productPriceLayout = (LinearLayout) v.findViewById(R.id.product_price_layout);
		productTimeLayout = (LinearLayout) v.findViewById(R.id.product_time_layout);

		productSailLayout.setOnClickListener(onSortListener);
		productPriceLayout.setOnClickListener(onSortListener);
		productTimeLayout.setOnClickListener(onSortListener);

		sailButton = (TextView) v.findViewById(R.id.sail_rb);
		priceButton = (TextView) v.findViewById(R.id.price_rb);
		timeButton = (TextView) v.findViewById(R.id.time_rb);
		// 三个箭头
		sailArrowView = (ImageView) v.findViewById(R.id.sail_arrow);
		priceArrowView = (ImageView) v.findViewById(R.id.price_arrow);
		timeArrowView = (ImageView) v.findViewById(R.id.time_arrow);

		// 如果不需要排序功能
		if (!bundle.getBoolean(ArgName.ProductList.SHOW_SORT, true)) {
			productSortLayout.setVisibility(View.GONE);
		} else {
			productSortLayout.setVisibility(View.VISIBLE);
		}
		if (productList == null || productList.isEmpty()) {
			productList = new ArrayList<ProductBean>();
			count = 1;
			selectposition = 0;
		}
		if (!isLocalData) {
			if (!productList.isEmpty()) {
				setListViewAdapter();
			} else {
				// 请求网络,获取用户信息
				HttpClient userClient = HttpClient.getInstall(activity);
				url = bundle.getString(ArgName.ProductList.URL); // 产品列表界面请求的URL。必填再
				if (bundle.containsKey(ArgName.ProductList.PARAM)) { // 如需带参数，则参数存放在map中，并用“param”的key保存。此处会读取出来
					params = new RequestParams((Map<String, String>) bundle.getSerializable(ArgName.ProductList.PARAM));
				}
				userClient.postAsync(url, params, jsonHttpResponseHandler);
			}
		} else {
			selectposition = 0;
			productList = BrowseRecordDataUtils.getInstall(activity).getBrowseRecords();
			setListViewAdapter();
		}
		setCurrentButton();
		return v;
	}

	private void setListViewAdapter() {
		adapter = new ProductListAdapter(productList, activity);

		registerForContextMenu(list);

		list.setAdapter(adapter);

		list.setSelection(selectposition);

		list.setOnItemClickListener(new DetailListener(productList));
	}

	/**
	 * 列表监听器，用于点击商品条目查看明细
	 */
	class DetailListener implements OnItemClickListener {

		private Context context;
		private List<ProductBean> data;

		public DetailListener(List<ProductBean> data) {
			this.setData(data);
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			ProductBean itembean = (ProductBean) adapter.getItem(position);
			Bundle bundle = new Bundle();
			bundle.putString(ArgName.ProductDetail.URL, itembean.getProductDetailUrl());
			bundle.putString(ArgName.ProductDetail.TITLE, itembean.getBrandName() + " " + itembean.getPattern() + " "
					+ itembean.getColorSpec()+(itembean.getIsGovEnterprise().equals(1) ? getString(R.string.govEnterprise) : ""));
			bundle.putString(ArgName.ProductDetail.PIC_URL, itembean.getPicPath1Url());
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_DETAIL, bundle);
		}

		public List<ProductBean> getData() {
			return data;
		}

		public void setData(List<ProductBean> data) {
			this.data = data;
		}

	}

}