package com.digione.zgb2b.fragment.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.product.ConsultAdapter;
import com.digione.zgb2b.bean.JsonPagerEntityBean;
import com.digione.zgb2b.bean.product.BriefInfoBean;
import com.digione.zgb2b.bean.product.ConsultItemBean;
import com.digione.zgb2b.bean.product.ProductSpecBean;
import com.digione.zgb2b.bean.product.SpecContent;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * 产品导购第三级页面（产品列表）
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductBriefFragment extends CommonBaseFragment {

	private TitleView titleTextView; // 顶部标题
	private RadioGroup radioGroup; // radio按钮组
	private RadioButton briefButton; // 简介按钮
	private RadioButton configButton; // 规格按钮
	private RadioButton consultButton; // 咨询按钮
	private LinearLayout product_profile_ly;

	private ConsultAdapter adapter;
	private String url; // 本界面请求的URL
	private RequestParams params; // 本界面请求所需带的参数
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private BriefInfoBean briefInfoBean; // 简介的结果集
	private List<ProductSpecBean> specBeanList; // 规格的结果集
	private LinearLayout product_consult_ly; // 咨询列表所在的layout
	private ListView consultListView; // 咨询列表
	private List<ConsultItemBean> consultItemBeanList; // 咨询结果列表
	private Integer targetpage = 1; // 显示的最大位置
	private int totalcount; // 商品总数
	private int pageCount; // 商品总页数
	private int selectposition = 0; // 滚动加载后的焦点位置

	// 处理规格返回的消息
	private CustomerJsonHttpResponseHandler<ArrayList<ProductSpecBean>> loadSpecHandler;

	// 处理咨询列表查询返回的消息
	private CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ConsultItemBean>> consultHandler;

	/**
	 * 设置咨询的adapter
	 */
	private void setListViewAdapter() {
		adapter = new ConsultAdapter(consultItemBeanList, activity);

		consultListView.setAdapter(adapter);

		consultListView.setSelection(selectposition);

	}

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
					if (targetpage < pageCount && pageCount != 1) {
						selectposition = consultItemBeanList.size() - 1;
						targetpage++;
						params.put("targetpage", targetpage.toString());
						HttpClient userClient = HttpClient.getInstall(activity);
						userClient.postAsync(url, params, consultHandler);
						// adapter.notifyDataSetChanged();
					}
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			product_profile_ly.removeAllViews();
			Integer productId = getArguments().getInt(ArgName.ProductBrief.PRODUCT_ID);
			switch (checkedId) {
			case R.id.brief_rb:
				titleTextView.goneButton();
				product_profile_ly.setVisibility(View.VISIBLE);
				product_consult_ly.setVisibility(View.GONE);
				// 选中简介
				loadPorfile(briefInfoBean);
				return;
			case R.id.config_rb:
				titleTextView.goneButton();
				product_profile_ly.setVisibility(View.VISIBLE);
				product_consult_ly.setVisibility(View.GONE);
				// 选中规格
				// 若存在，则无需请求网络
				if (specBeanList != null) {
					loadspecification();
					return;
				}
				url = Constants.Url.PRODUCT_SPEC_035.replace("${id}", productId.toString());
				HashMap<String, String> pmHashMap = new HashMap<String, String>();
				pmHashMap.put("typeId", String.valueOf(getArguments().getInt(ArgName.ProductBrief.TYPE)));
				params = new RequestParams(pmHashMap);
				HttpClient userClient = HttpClient.getInstall(activity);
				userClient.postAsync(url, params, loadSpecHandler);
				break;
			case R.id.cunsult_rb:
				// 选中咨询
				// 显示按钮
				titleTextView.setRightButton(R.string.product_consult_btntitle, rightListener);

				product_profile_ly.removeAllViews();
				product_profile_ly.setVisibility(View.GONE);
				product_consult_ly.setVisibility(View.VISIBLE);
				if (adapter != null) {
					return;
				}
				consultListView = new ListView(product_consult_ly.getContext());
				consultListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				consultListView.setOnScrollListener(scrolllistener);
				consultListView.setDivider(getResources().getDrawable(R.drawable.devider_line2));

				product_consult_ly.addView(consultListView);

				url = Constants.Url.COUSULTING_INDEX_032.replace("${id}", productId.toString());
				HashMap<String, String> pmMap = new HashMap<String, String>();
				pmMap.put("targetpage", targetpage.toString());
				params = new RequestParams(pmMap);
				userClient = HttpClient.getInstall(activity);
				userClient.postAsync(url, params, consultHandler);
				break;
			default:
				break;
			}

		}

	};

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		loadSpecHandler = new CustomerJsonHttpResponseHandler<ArrayList<ProductSpecBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<ProductSpecBean> outBean, String msg) {
				if (null != outBean && !outBean.isEmpty()) {
					specBeanList = outBean;
					loadspecification();
				} else {
					ToastUtil.showToast(getContext(), getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}
		};

		consultHandler = new CustomerJsonHttpResponseHandler<JsonPagerEntityBean<ConsultItemBean>>(activity) {

			@Override
			public void processCallSuccess(JsonPagerEntityBean<ConsultItemBean> outBean, String msg) {
				if (null != outBean) {
					totalcount = outBean.getTotalCount();
					pageCount = outBean.getPageCount();
					if (consultItemBeanList != null) {
						consultItemBeanList.addAll(outBean.getList());
					}
					if (consultItemBeanList == null || consultItemBeanList.isEmpty()) {
						consultItemBeanList = outBean.getList();
						targetpage = 1;
					}
					setListViewAdapter();
				} else {
					ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}
		};

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				// .showStubImage(R.drawable.ic_stub)
				// .showImageForEmptyUri(R.drawable.no_picture)
				// .showImageOnFail(R.drawable.no_picture)
				.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).displayer(new SimpleBitmapDisplayer()).build();

		View v = inflater.inflate(R.layout.product_brief, container, false);

		titleTextView = (TitleView) v.findViewById(R.id.title);

		titleTextView.getmTitle().setGravity(Gravity.LEFT);
		titleTextView.goneButton();
		titleTextView.setTitle("  " + getArguments().getString(ArgName.ProductBrief.TITLE));

		product_profile_ly = (LinearLayout) v.findViewById(R.id.product_brief_ly);

		radioGroup = (RadioGroup) v.findViewById(R.id.porduct_sort_group);

		briefButton = (RadioButton) v.findViewById(R.id.brief_rb);
		configButton = (RadioButton) v.findViewById(R.id.config_rb);
		consultButton = (RadioButton) v.findViewById(R.id.cunsult_rb);
		product_consult_ly = (LinearLayout) v.findViewById(R.id.product_consult_ly);
		briefInfoBean = (BriefInfoBean) getArguments().getSerializable(ArgName.ProductBrief.BRIEF_BEAN);
		if (getArguments().getBoolean(ArgName.ProductBrief.FROM_CONSULT)) {
			if (consultItemBeanList != null && !consultItemBeanList.isEmpty()) {// 清空咨询列表
				consultItemBeanList.clear();
				consultItemBeanList = null;
			}
			briefButton.setChecked(false);
			consultButton.setChecked(true);
			// 从咨询过来，直接跳到咨询列表
			titleTextView.setRightButton(R.string.product_consult_btntitle, rightListener);

			product_profile_ly.removeAllViews();
			product_profile_ly.setVisibility(View.GONE);
			product_consult_ly.setVisibility(View.VISIBLE);

			consultListView = new ListView(product_consult_ly.getContext());
			consultListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			consultListView.setOnScrollListener(scrolllistener);
			consultListView.setDivider(getResources().getDrawable(R.drawable.devider_line2));

			product_consult_ly.addView(consultListView);

			Integer productId = getArguments().getInt(ArgName.ProductBrief.PRODUCT_ID);
			url = Constants.Url.COUSULTING_INDEX_032.replace("${id}", productId.toString());
			HashMap<String, String> pmMap = new HashMap<String, String>();
			pmMap.put("targetpage", targetpage.toString());
			params = new RequestParams(pmMap);
			HttpClient userClient = HttpClient.getInstall(activity);
			userClient = HttpClient.getInstall(activity);
			userClient.postAsync(url, params, consultHandler);
		} else {
			loadPorfile(briefInfoBean);
		}
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);

		return v;
	}

	/**
	 * 加载简介
	 * 
	 * @param gBean
	 */
	private void loadPorfile(BriefInfoBean bfBean) {
		product_profile_ly.removeAllViews();

		List<String> nameList = new ArrayList<String>();
		ArrayList<String> valueList = new ArrayList<String>();
		// 上市时间
		valueList.add(bfBean.getMarketTime());
		nameList.add(bfBean.getMarketTimeTitle());
		// 主要功能
		nameList.add(bfBean.getFunctionTitle());
		valueList.add(""); // 为了与namelist对齐
		// 支持业务
		nameList.add(bfBean.getBusinessSupportTitle());
		valueList.add(""); // 为了与namelist对齐
		// 产品属性
		if (getArguments().getInt(ArgName.ProductBrief.TYPE) == 7 && bfBean.getSaleSpec() != null) {
			String[] spec = bfBean.getSaleSpec().split("\n");
			for (String specitemString : spec) {
				String[] subspecsStrings = null;
				if (specitemString.contains("：")) {
					subspecsStrings = specitemString.split("：");
				} else if (specitemString.contains(":")) {
					subspecsStrings = specitemString.split(":");
				}
				nameList.add(subspecsStrings[0] + "：");
				valueList.add(subspecsStrings[1]);
			}

		} else {
			nameList.add(bfBean.getSaleSpecTitle());
			valueList.add(StringUtils.relpaceHtmlTag(bfBean.getSaleSpec()));
		}

		// 产品定位
		nameList.add(bfBean.getLocationTitle());
		valueList.add(bfBean.getLocation());
		// 标准配置
		if (bfBean.getConfigSpecTitle() != null) {
			nameList.add(bfBean.getConfigSpecTitle());
			valueList.add(StringUtils.relpaceHtmlTag(bfBean.getConfigSpec()));
		}

		TableLayout tableLayout = new TableLayout(product_profile_ly.getContext());

		TextView toppad = new TextView(product_profile_ly.getContext());
		toppad.setHeight(Integer.parseInt(getString(R.string.profile_padsize)));
		product_profile_ly.addView(toppad);
		for (int i = 0; i < nameList.size(); i++) {
			if (nameList.get(i) != null) {
				// 一行数据
				TableRow tableRow = new TableRow(tableLayout.getContext());
				// 简介的每一行标题
				TextView titleTextView = new TextView(tableRow.getContext());
				titleTextView.setPadding(10, 0, 0, 0);
				titleTextView.setMaxWidth((int) (ZGApplication.getScreenWidth() * 0.27));
				titleTextView.setText(nameList.get(i));
				// tableLayout.setColumnShrinkable(0,true);
				tableRow.addView(titleTextView);
				// 主要功能和支持业务一行最多的图片数量
				int maxcount = 5;
				if (ZGApplication.getScreenWidth() >= 540) {
					maxcount = 10;
				} else if (ZGApplication.getScreenWidth() >= 480) {
					maxcount = 8;
				}
				// 主要功能
				if (bfBean.getFunctionTitle() != null && bfBean.getFunctionTitle().equals(nameList.get(i))) {
					LinearLayout linearLayout = new LinearLayout(tableRow.getContext());

					if (bfBean.getFunctionList() != null) {
						int count = 0;
						for (int j = 0; j < bfBean.getFunctionList().size(); j++) {
							ImageView imageView = new ImageView(linearLayout.getContext());
							imageView
									.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							imageLoader.displayImage(bfBean.getFunctionList().get(j).getPicUrl(), imageView, options);
							if (count != 0 && count % maxcount == 0) { // 一行显示的最大数目
								linearLayout.addView(imageView);
								tableRow.addView(linearLayout);
								tableLayout.addView(tableRow);

								// ---加一行间隔
								tableRow = new TableRow(tableLayout.getContext());
								TextView dividerView = new TextView(tableRow.getContext());
								dividerView.setBackgroundColor(tableRow.getContext().getResources()
										.getColor(R.color.backgroud_color));
								dividerView.setHeight(10);
								tableRow.addView(dividerView);
								tableLayout.addView(tableRow);

								count++;
								if (count < bfBean.getFunctionList().size()) {
									// ----还有图标，接着新起一行
									tableRow = new TableRow(tableLayout.getContext());
									tableRow.addView(new TextView(tableRow.getContext()));
									linearLayout = new LinearLayout(tableRow.getContext());
								}

							} else {
								linearLayout.addView(imageView);
								if (j == (bfBean.getFunctionList().size() - 1)) {
									tableRow.addView(linearLayout);
									tableLayout.addView(tableRow);

									// ---加一行间隔
									tableRow = new TableRow(tableLayout.getContext());
									TextView dividerView = new TextView(tableRow.getContext());
									dividerView.setBackgroundColor(tableRow.getContext().getResources()
											.getColor(R.color.backgroud_color));
									dividerView.setHeight(10);
									tableRow.addView(dividerView);
									tableLayout.addView(tableRow);
									// ----
								}
								count++;
							}
						}

					} else {
						tableLayout.addView(tableRow);
					}

					// 支持业务
				} else if (bfBean.getBusinessSupportTitle() != null
						&& bfBean.getBusinessSupportTitle().equals(nameList.get(i))) {
					LinearLayout linearLayout = new LinearLayout(tableRow.getContext());

					if (bfBean.getBusinessSupportList() != null) {
						int count = 0;
						for (int j = 0; j < bfBean.getBusinessSupportList().size(); j++) {
							ImageView imageView = new ImageView(linearLayout.getContext());
							imageView
									.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							imageLoader.displayImage(bfBean.getBusinessSupportList().get(j).getPicUrl(), imageView, options);
							if (count != 0 && count % maxcount == 0) { // 一行只显示8个
								linearLayout.addView(imageView);
								tableRow.addView(linearLayout);
								tableLayout.addView(tableRow);

								// ---加一行间隔
								tableRow = new TableRow(tableLayout.getContext());
								TextView dividerView = new TextView(tableRow.getContext());
								dividerView.setBackgroundColor(tableRow.getContext().getResources()
										.getColor(R.color.backgroud_color));
								dividerView.setHeight(10);
								tableRow.addView(dividerView);
								tableLayout.addView(tableRow);

								count++;
								if (count < bfBean.getBusinessSupportList().size()) {

									// ----如果还有图标，接着新起一行
									tableRow = new TableRow(tableLayout.getContext());
									tableRow.addView(new TextView(tableRow.getContext()));
									linearLayout = new LinearLayout(tableRow.getContext());
								}

							} else {
								linearLayout.addView(imageView);
								if (j == (bfBean.getBusinessSupportList().size() - 1)) {
									tableRow.addView(linearLayout);
									tableLayout.addView(tableRow);
									// ---加一行间隔
									tableRow = new TableRow(tableLayout.getContext());
									TextView dividerView = new TextView(tableRow.getContext());
									dividerView.setBackgroundColor(tableRow.getContext().getResources()
											.getColor(R.color.backgroud_color));
									dividerView.setHeight(10);
									tableRow.addView(dividerView);
									tableLayout.addView(tableRow);
									// ----
								}
								count++;
							}
						}

					} else {
						tableLayout.addView(tableRow);
					}

				} else {// 简介的其他内容
					TextView valueTextView = new TextView(tableRow.getContext());
					valueTextView.setGravity(Gravity.LEFT);
					valueTextView.setPadding(0, 0, 10, 0);
					valueTextView.setText(valueList.get(i));
					valueTextView.setLineSpacing(5, 1);
					tableRow.addView(valueTextView);
					valueTextView.setMaxWidth((int) (ZGApplication.getScreenWidth() * 0.72));
					if (ZGApplication.getScreenWidth() >= 720) {
						valueTextView.setMaxWidth((int) (ZGApplication.getScreenWidth() * 0.7));
					}
					tableLayout.addView(tableRow);
				}

			}

		}
		product_profile_ly.addView(tableLayout);
	}

	/**
	 * 加载规格
	 * 
	 * @param gBean
	 */
	private void loadspecification() {
		product_profile_ly.removeAllViews();

		if (specBeanList == null || specBeanList.isEmpty()) {
			return;
		}
		TextView toppad = new TextView(product_profile_ly.getContext());
		toppad.setHeight(3);
		product_profile_ly.addView(toppad);
		product_profile_ly.setGravity(Gravity.CENTER);
		LayoutInflater inflater = LayoutInflater.from(activity);
		TableLayout tableLayout = (TableLayout) inflater.inflate(R.layout.product_spec_table, null);

		product_profile_ly.addView(tableLayout);
		for (int i = 0; i < specBeanList.size(); i++) {
			if (specBeanList.get(i) != null && specBeanList.get(i).getTitleName() != null) {
				// 标题
				TableRow lltitle = (TableRow) inflater.inflate(R.layout.product_spec_row, null);

				TextView titleview = (TextView) lltitle.findViewById(R.id.spec_title_row);
				titleview.setGravity(Gravity.CENTER_VERTICAL);
				titleview.setBackgroundColor(getResources().getColor(R.color.backgroud_color));
				titleview.setTextSize(14);
				titleview.getPaint().setFakeBoldText(true);
				titleview.setText(specBeanList.get(i).getTitleName());
				tableLayout.addView(lltitle);

				// 每一组规格
				List<SpecContent> contentList = specBeanList.get(i).getList();
				if (contentList == null || contentList.isEmpty()) {
					continue;
				}
				int len = contentList.size();

				for (int j = 0; j < len; j++) {
					TableRow contentrow = new TableRow(tableLayout.getContext());

					if (contentList.get(j) != null) {
						TextView specSubTitleView = new TextView(contentrow.getContext());
						specSubTitleView.setBackgroundResource(R.drawable.briefspec_content_bg);
						specSubTitleView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
						specSubTitleView.setText(contentList.get(j).getSpecTitle());
						specSubTitleView.setTextSize(13);
						specSubTitleView.setPadding(0, 0, 10, 0);
						contentrow.addView(specSubTitleView);

						TextView specContentView = new TextView(contentrow.getContext());
						specContentView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
						specContentView.setBackgroundResource(R.drawable.briefspec_content_bg);
						specContentView.setTextSize(13);
						specContentView.setPadding(10, 0, 0, 0);
						specContentView.setText(contentList.get(j).getSpecContent());
						contentrow.addView(specContentView);

					}
					tableLayout.addView(contentrow);

				}
			}
		}
	}

	private TitleView.OnRightButtonClickListener rightListener = new TitleView.OnRightButtonClickListener() {

		@Override
		public void onClick(View button) {
			Bundle bundle = getArguments();
			bundle.putBoolean(ArgName.ProductBrief.FROM_CONSULT, true);
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_CONSULT_SUBMIT, bundle);
		}
	};

}