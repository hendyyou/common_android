package com.digione.zgb2b.fragment.product;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.product.ProductGalleryAdapter;
import com.digione.zgb2b.bean.product.ActItemInfo;
import com.digione.zgb2b.bean.product.AddFavoriteResultBean;
import com.digione.zgb2b.bean.product.ColorItemBean;
import com.digione.zgb2b.bean.product.ProductDetailBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.BrowseRecordDataUtils;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.ShopCartUtils;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;

/**
 * 产品详情页
 * 
 * @author zhangqr
 * 
 */
public class ProductDetailFragment extends CommonBaseFragment {
	private ProductDetailBean pdbean;
	private EditText buynumEdit; //
	private Gallery gallery;
	private TextView addCartView;
	private TextView favoritesView;
	private TextView productGiftTv; // 赠品信息
	private View MoreDetailsLayout;// 点击进入简介的区域
	private TextView provTvName;// 供应商标题
	private TextView provTv; // 供应商
	private TextView productSpecTv; // 产品卖点信息
	private TextView custPriceTv; // 促销价
	private TextView sysCustPriceTv;// 直供价
	private TextView advicePriceTv;// 零售价
	private View productActLy; // 活动区
	private TextView productActinfoTv; // 活动标题
	private TextView productActContentTv; // 活动内容
	private TextView colorbtn; // 颜色按钮
	private TextView colorbtn2;// 颜色按钮图标
	private int colorIndex; // 当前产品对应的颜色序号
	private ArrayList<String> colorTitles; // 颜色的
	private TitleView titleTextView; // 标题
	private TextView stockTv; // 库存信息
	private boolean stopCount = false; // 用于停止倒计时
	private long pretimelive; // 记录上一次倒计时。用于避免多个线程计时
	private String url; // 本页面请求的URL

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.ADD_SHOP_CART_SUCCESS: {
				break;
			}
			case MsgCode.ADD_SHOP_CART_FAILURE: {
				break;
			}
			case MsgCode.ACTIVITY_REMAINTIME_REFRESH: {// 909表示刷新倒计时
				productActinfoTv.setText(msg.getData().getString(ArgName.ProductDetail.SHOW_STRING));
			}
			default:
				break;
			}
		}
	};

	private CustomerJsonHttpResponseHandler<ProductDetailBean> jsonHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<AddFavoriteResultBean> addFavoritesHandler;

	@Override
	public void onResume() {
		super.onResume();
		stopCount = false;
		// 请求网络
		HttpClient userClient = HttpClient.getInstall(activity);
		url = getArguments().getString(ArgName.ProductDetail.URL);
		if (url == null) {
			url = Constants.Url.PRODUCT_DETAIL_034.replace("${id}", getArguments().getString(ArgName.ProductDetail.ID));
		}
		userClient.postAsync(url, null, jsonHttpResponseHandler);
	}

	@Override
	public void onPause() {
		super.onPause();
		stopCount = true;
	}

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ProductDetailBean>(activity) {

			@Override
			public void processCallSuccess(ProductDetailBean outBean, String msg) {
				if (null != outBean) {
					pdbean = outBean;

					gallery.setAdapter(new ProductGalleryAdapter(activity, pdbean.getPicList()));
					if (outBean.getGiftInfo() != null) {
						productGiftTv.setVisibility(View.VISIBLE);
						productGiftTv.setText(outBean.getGiftInfo());
					} else {
						productGiftTv.setVisibility(View.GONE);
					}
					productSpecTv.setText(StringUtils.relpaceHtmlTag(pdbean.getBriefInfo().getSaleSpec()));
					if (pdbean.getProvNameTitle() != null) {
						provTvName.setText(pdbean.getProvNameTitle());
						provTv.setText(pdbean.getProvName());
					}
					// 如果有活动信息
					if (pdbean.getActInfo() != null) {
						productActLy.setVisibility(View.VISIBLE);
						productActinfoTv.setVisibility(View.VISIBLE);
						productActContentTv.setVisibility(View.VISIBLE);
						stopCount = false;
						final long timeleave = SystemUtil.getTimeLeave(pdbean.getActInfo().getNowDateTime(), pdbean
								.getActInfo().getActEndTime());

						// 一个独立线程用于给主线程发送命令，刷新倒计时。
						new Thread() {

							long timeleaves = timeleave;

							@Override
							public void run() {
								while (!stopCount && timeleaves > 0) {
									pretimelive = timeleaves;
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if (!stopCount && timeleaves > 0) {
										if (pretimelive != timeleaves) {
											return;
										}
										Message message = new Message();
										Bundle bundle = new Bundle();
										timeleaves -= 500;
										String timeLeaveString = SystemUtil.getTimeLeaveString(timeleaves);
										bundle.putString(ArgName.ProductDetail.SHOW_STRING,
												activity.getString(R.string.procuct_actinfo) + timeLeaveString);
										message.setData(bundle);
										message.what = MsgCode.ACTIVITY_REMAINTIME_REFRESH;
										mHandler.sendMessage(message);
									}
								}
							}
						}.start();

						StringBuffer actContentbf = new StringBuffer();
						// 如果有团购活动
						if (pdbean.getActInfo().getActGroupInfo() != null) {
							actContentbf.append(pdbean.getActInfo().getActGroupInfo().getActGroupTips());
						}
						// 如果有产品级活动
						if (pdbean.getActInfo().getActMainRuleList() != null) {

							for (ActItemInfo actItemInfo : pdbean.getActInfo().getActMainRuleList()) {
								if (!(actContentbf.toString() == null || "".equals(actContentbf.toString()))) {
									actContentbf.append("\n");
								}
								actContentbf.append(actItemInfo.getActInfo());

							}
						}
						// 如果有订单级活动
						if (pdbean.getActInfo().getActOrderLevelInfo() != null) {
							if (!(actContentbf.toString() == null || "".equals(actContentbf.toString()))) {
								actContentbf.append("\n");
							}
							actContentbf.append(pdbean.getActInfo().getActOrderLevelInfo().getActOrderLevelTitle());
							for (ActItemInfo actItemInfo : pdbean.getActInfo().getActOrderLevelInfo().getActOrderLevelList()) {
								if (!(actContentbf.toString() == null || "".equals(actContentbf.toString()))) {
									actContentbf.append("\n");
								}
								actContentbf.append(pdbean.getActInfo().getActOrderLevelInfo().getActOrderLevelTitle()
										+ actItemInfo.getActInfo());
							}
						}
						productActContentTv.setText(actContentbf.toString());
					} else {
						// 沒有活动，相关组件应该清空
						stopCount = true;
						productActLy.setVisibility(View.GONE);
						productActinfoTv.setVisibility(View.GONE);
						productActContentTv.setVisibility(View.GONE);
					}

					if (pdbean.getSysCustPriceTitle() != null) {
						sysCustPriceTv.setText(pdbean.getSysCustPriceTitle()
								+ (pdbean.getSysCustPrice() == null ? "  " : StringUtils.formatMoney(pdbean
										.getSysCustPrice()) + "  "));
					}
					if (pdbean.getAdvicePriceTitle() != null) {
						advicePriceTv.setText(pdbean.getAdvicePriceTitle()
								+ (pdbean.getAdvicePrice() == null ? "" : StringUtils.formatMoney(pdbean.getAdvicePrice())));
					}

					if (pdbean.getCustPriceTitle() != null && pdbean.getIsSpecial() == 1) {// 如果是促销价
						custPriceTv.setVisibility(View.VISIBLE);
						custPriceTv.setText(pdbean.getCustPriceTitle()
								+ (pdbean.getCustPrice() == null ? "" : StringUtils.formatMoney(pdbean.getCustPrice())));
					} else if (pdbean.getCustPriceTitle() != null && pdbean.getIsSpecial() == 0) {
						// 如果不存在促销价，则该数值为直供价，应该显示在直供价所在的tv
						custPriceTv.setVisibility(View.GONE);
						sysCustPriceTv.setText(pdbean.getCustPriceTitle()
								+ (pdbean.getCustPrice() == null ? "  " : StringUtils.formatMoney(pdbean.getCustPrice())
										+ "  "));
					}

					// 颜色选框
					colorTitles.clear();
					for (int i = 0; i < pdbean.getColorInfo().getColorList().size(); i++) {
						ColorItemBean cItemBean = pdbean.getColorInfo().getColorList().get(i);
						if (cItemBean.getProductId().equals(pdbean.getProductId())) {
							colorbtn.setText(cItemBean.getTitleName());
							colorIndex = i;

						}
						colorTitles.add(cItemBean.getTitleName());

					}
					if (pdbean.getStockCount() != null) {
						stockTv.setVisibility(View.VISIBLE);
						stockTv.setText(pdbean.getStockCountTitle() + pdbean.getStockCount() + " "
								+ activity.getString(R.string.piece));
					}

					// 监听进入图片浏览
					gallery.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							Bundle bundle = new Bundle();
							bundle.putSerializable(ArgName.ProductGallery.PIC_LIST, (Serializable) pdbean.getPicList());
							bundle.putString(ArgName.ProductGallery.TITLE, pdbean.getBrandName() + " " + pdbean.getPattern()
									+ " " + pdbean.getColorSpec());
							bundle.putInt(ArgName.ProductGallery.POSITION, arg2);
							stopCount = true; // 停止倒计时
							ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_IMAGESWITCHER, bundle);
						}
					});
					// 监听进入简介界面
					MoreDetailsLayout.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Bundle bundle = new Bundle();
							bundle.putString(ArgName.ProductBrief.TITLE, pdbean.getBrandName() + " " + pdbean.getPattern()
									+ " " + pdbean.getColorSpec()+(pdbean.getIsGovEnterprise().equals(1) ? getString(R.string.govEnterprise) : ""));
							bundle.putInt(ArgName.ProductBrief.PRODUCT_ID, pdbean.getProductId());
							bundle.putInt(ArgName.ProductBrief.TYPE, pdbean.getDictId1());
							bundle.putSerializable(ArgName.ProductBrief.BRIEF_BEAN, pdbean.getBriefInfo());
							stopCount = true; // 停止倒计时
							ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_BRIEF, bundle);

						}
					});

					titleTextView.setTitle(pdbean.getBrandName() + " " + pdbean.getPattern() + " " + pdbean.getColorSpec()+(pdbean.getIsGovEnterprise().equals(1) ? getString(R.string.govEnterprise) : ""));

					// 设置添加购物车的监听器
					addCartView.setOnClickListener(new addCartListener());

					favoritesView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							SystemUtil.closeSoftInput(activity);
							HttpClient userClient = HttpClient.getInstall(activity);
							String url = Constants.Url.ADD_FAVORITE_009.replace("${id}", pdbean.getProductId().toString());
							userClient.postAsync(url, null, addFavoritesHandler);
						}
					});

					// 登录后,增加浏览记录
					if (ZGApplication.getInstance().isLogin()) {
						String picUrl = getArguments().getString(ArgName.ProductDetail.PIC_URL);
						pdbean.setSaleSpec(StringUtils.relpaceHtmlTag(pdbean.getBriefInfo().getSaleSpec()));
						pdbean.setPicPath1Url(picUrl);
						BrowseRecordDataUtils.getInstall(activity).addBrowseRecord(pdbean);
					}
				} else {
					ToastUtil.showToast(activity, activity.getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
				}
			}
		};

		addFavoritesHandler = new CustomerJsonHttpResponseHandler<AddFavoriteResultBean>(activity,
				ChangeFragmentUtil.PRODUCT_DETAIL, getArguments()) {

			@Override
			public void processCallSuccess(AddFavoriteResultBean outBean, String msg) {
				ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
			}
		};

		View v = inflater.inflate(R.layout.product_detail, container, false);
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				SystemUtil.closeSoftInput(activity);
				return false;
			}
		});
		titleTextView = (TitleView) v.findViewById(R.id.title);
		titleTextView.goneButton();
		titleTextView.getmTitle().setGravity(Gravity.LEFT);
		titleTextView.getmTitle().setPadding(SystemUtil.dip2px(activity, 10), 0, 0, 0);
		titleTextView.setTitle("  " + getArguments().getString(ArgName.ProductDetail.TITLE));

		buynumEdit = (EditText) v.findViewById(R.id.product_detail_buynum);
		buynumEdit.setSelectAllOnFocus(true);
		provTv = (TextView) v.findViewById(R.id.product_prov_tv);
		provTvName = (TextView) v.findViewById(R.id.product_prov_tvname);
		productSpecTv = (TextView) v.findViewById(R.id.product_spec_tv);
		productGiftTv = (TextView) v.findViewById(R.id.product_gift_tv);
		custPriceTv = (TextView) v.findViewById(R.id.product_custprice_tv);
		sysCustPriceTv = (TextView) v.findViewById(R.id.product_syscustprice_tv);
		advicePriceTv = (TextView) v.findViewById(R.id.product_adviceprice_tv);
		productActLy = v.findViewById(R.id.product_act_ly);
		productActinfoTv = (TextView) v.findViewById(R.id.product_actinfo_tv);
		productActContentTv = (TextView) v.findViewById(R.id.product_actcontent_tv);
		stockTv = (TextView) v.findViewById(R.id.product_stock);

		colorbtn = (TextView) v.findViewById(R.id.color_select_btn);
		colorbtn2 = (TextView) v.findViewById(R.id.color_select_btn2);
		colorTitles = new ArrayList<String>();
		View.OnClickListener vListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showProductColors();
			}

		};
		colorbtn.setOnClickListener(vListener);
		colorbtn2.setOnClickListener(vListener);

		addCartView = (TextView) v.findViewById(R.id.product_detail_addcart);

		favoritesView = (TextView) v.findViewById(R.id.product_detail_favorites);

		gallery = (Gallery) v.findViewById(R.id.product_detail_gallery);

		MoreDetailsLayout = v.findViewById(R.id.product_detail_morely);

		gallery.setSelection(gallery.getCount() >= 2 ? 1 : 0);

		return v;
	}

	@Override
	public void onDetach() {
		stopCount = true; // 停止倒计时
		super.onDetach();
	}

	private DialogInterface.OnClickListener colorListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == colorIndex) {
				dialog.dismiss();
				return;
			}
			url = pdbean.getColorInfo().getColorList().get(which).getDetailUrl();
			HttpClient userClient = HttpClient.getInstall(activity);
			userClient.postAsync(url, null, jsonHttpResponseHandler);
			dialog.dismiss();
		}
	};

	/**
	 * 弹出颜色选择菜单
	 */
	private void showProductColors() {
		if (pdbean != null && pdbean.getColorInfo().getColorList() != null) {

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setSingleChoiceItems(colorTitles.toArray(new String[colorTitles.size()]), colorIndex, colorListener);
			builder.create().show();
		} else {
			ToastUtil.showToast(activity, activity.getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
		}
	}

	/**
	 * 添加购物车从监听器
	 * 
	 * @author zhangqr
	 * 
	 */
	class addCartListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			SystemUtil.closeSoftInput(activity);
			if (ZGApplication.getInstance().isLogin()) {
				if (!StringUtils.isEmpty(buynumEdit.getText().toString())
						&& Integer.parseInt(buynumEdit.getText().toString()) >= 1) {
					ShopCartUtils.getInstance(mHandler).addShopCart(activity, pdbean.getProductId().toString(),
							pdbean.getVmiProvideId(), Integer.parseInt(buynumEdit.getText().toString()));
				} else {
					ToastUtil.showToast(activity, getResources().getString(R.string.num_mor_than_zeao), 1000);
				}
			} else {
				ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.PRODUCT_DETAIL, activity, "没有登录或者登录超时",
						getArguments());
			}

		}

	}

}