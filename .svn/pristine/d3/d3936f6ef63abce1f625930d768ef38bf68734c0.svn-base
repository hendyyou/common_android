package com.digione.zgb2b.fragment.shopcart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.ActItemInfo;
import com.digione.zgb2b.bean.shopcart.*;
import com.digione.zgb2b.bean.workbench.DeliveryAddressBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.*;
import com.digione.zgb2b.widget.MyAlertDialogFragment;
import com.digione.zgb2b.widget.TitleView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ShopCartFragement extends CommonBaseFragment {

	private View view;
	private TitleView titleView;
	private LinearLayout layout;
	private TextView consignee;
	private TextView consMobile;
	private TextView consAddress;
	private LinearLayout addressLayout;
	private Button okBtn;
	private TextView invoiceTypeName;
    private  TextView  invoiceTypeAdress;
	private ShopCartBean shopCartBean;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	private TextView currentItemMoneyTextView;
	private TextView currentItemNumEditTextView;
	private SProductBean currentProductBean;
	private Integer currentItemQuantity;

	private static final String NO_CART_DATA_ERROR_CODE = "00";

	private CustomerJsonHttpResponseHandler<ShopCartBean> jsonHttpResponseHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.SHOP_CART_INDEX_SUCCESS:
				initData();
				break;
			case MsgCode.EDIT_SHOP_CART_SUCCESS: {
				currentItemNumEditTextView.setText(currentItemQuantity.toString());
				Double priceStr = currentProductBean.getCurrentPrice();
				currentItemMoneyTextView.setText(getResources().getString(R.string.product_cur_cost)
						+ StringUtils.formatMoney(currentItemQuantity * priceStr));
				break;
			}
			case MsgCode.EDIT_SHOP_CART_FAILURE:
				break;

			case MsgCode.REMOVE_SHOP_CART_SUCCESS: {
				if (ShopCartUtils.getInstance(mHandler).readShopCartFile(activity).isEmpty()) {
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_NO_NOMAL, null);
				} else {
					HttpClient httpClient = HttpClient.getInstall(activity);
					httpClient.postAsync(Constants.Url.SHOP_CART_INDEX_042, null, jsonHttpResponseHandler);
				}
				break;
			}
			case MsgCode.REMOVE_SHOP_CART_FAILURE: {

				break;
			}
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shopcart, container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ShopCartBean>(activity,
				ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, getArguments()) {

			@Override
			public void processCallSuccess(ShopCartBean outBean, String msg) {
				shopCartBean = outBean;
				ZGApplication.getInstance().setShopCartBean(shopCartBean);
				mHandler.sendEmptyMessage(MsgCode.SHOP_CART_INDEX_SUCCESS);
			}

			@Override
			public void processCallFailure(ShopCartBean outBean, String failureCode, String msg) {
				super.processCallFailure(outBean, failureCode, msg);
				if (NO_CART_DATA_ERROR_CODE.equals(failureCode)) {
					// 购物车为空
					ShopCartUtils.getInstance(mHandler).clearShopCartData(activity);
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_NO_NOMAL, null);
				}
			}
		};
		if (view != null) {
			if (ZGApplication.getInstance().isLogin()
					&& ShopCartUtils.getInstance(mHandler).readShopCartFile(activity).isEmpty()) {
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_NO_NOMAL, null);
				return;
			}

			if (!ZGApplication.getInstance().isLogin()) {
				ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, activity, null,
						new Bundle());
				return;
			}

			findViewById();
			initListener();
		}
	}

	private void findViewById() {
		titleView = (TitleView) view.findViewById(R.id.title);
		titleView.setTitle(R.string.shop_cart);
		layout = (LinearLayout) view.findViewById(R.id.shop_cart_body_layout);
		consignee = (TextView) view.findViewById(R.id.shop_cart_consignee);
		consMobile = (TextView) view.findViewById(R.id.shop_cart_consMobile);
		consAddress = (TextView) view.findViewById(R.id.shop_cart_consAddress);
		addressLayout = (LinearLayout) view.findViewById(R.id.shop_cart_address_layout);
		okBtn = (Button) view.findViewById(R.id.shop_cart_pay_btn);
		invoiceTypeName = (TextView) view.findViewById(R.id.shop_cart_invoiceTypeName);
        invoiceTypeAdress = (TextView) view.findViewById(R.id.shop_cart_invoiceTypeAddress);
		TextView consig = (TextView) view.findViewById(R.id.shop_cart_consig_info);
		TextPaint tPaint = consig.getPaint();
		tPaint.setFakeBoldText(true);
		TextView invoice = (TextView) view.findViewById(R.id.shop_cart_invoice_type);
		TextPaint mPaint = invoice.getPaint();
		mPaint.setFakeBoldText(true);
		imageLoader = ImageLoader.getInstance();
		options = DisplayImageOptionsUtil.getDisplayImageOptions();
	}

	private void initListener() {
		addressLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (shopCartBean != null) {
					Integer consId = -1;
					if (null != shopCartBean.getConsigneeInfo()) {
						consId = shopCartBean.getConsigneeInfo().getConsId();
					}
					Bundle bundle = new Bundle();
					bundle.putInt(ArgName.DeliveryAddress.CONS_ID, consId);
					bundle.putBoolean(ArgName.DeliveryAddress.IS_FROM_SHOP_CART, true);
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_RECEIVING_ADDRESS, bundle);
				}
			}
		});
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (shopCartBean != null) {
					if (null != shopCartBean.getInvoiceInfo() && null == shopCartBean.getInvoiceInfo().getInvoiceType()) {
						ToastUtil.showToast(activity, shopCartBean.getInvoiceInfo().getInvoiceTypeName(),
								ToastUtil.LENGTH_LONG);
						return;
					}
					if (shopCartBean.getIsNeedInputChannelId() == 0) {
						ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_ACOUNT, null);
					} else {
						ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_CHANNEL, null);
					}
				}
			}
		});
		if (ZGApplication.getInstance().getAddressBean() == null) {
			HttpClient httpClient = HttpClient.getInstall(activity);
			httpClient.postAsync(Constants.Url.SHOP_CART_INDEX_042, null, jsonHttpResponseHandler);
		} else {
			shopCartBean = ZGApplication.getInstance().getShopCartBean();
			resetAddressData();// 更新地址
			initData();
		}
	}

	private void initData() {
		layout.removeAllViews();
		if (shopCartBean == null) {
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_NO_NOMAL, null);
			return;
		}
		List<SOrderListBean> orderList = shopCartBean.getOrderList();
		if (orderList != null && orderList.size() != 0) {
			for (int i = 0; i < orderList.size(); i++) {
				SOrderListBean orderListBean = orderList.get(i);
				List<SProductListBean> mList = orderListBean.getList();
				View v = insertTextView(orderListBean.getOrderTitle());
				for (int j = 0; j < mList.size(); j++) {
					SProductListBean listBean = mList.get(j);
					View mView;
					if (j != 0) {
						mView = insertTextView(getString(R.string.provtitle) + listBean.getProvName(), true);
					} else {
						mView = insertTextView(getString(R.string.provtitle) + listBean.getProvName(), false);
					}
					List<SProductBean> kList = listBean.getList();
					for (int m = 0; m < kList.size(); m++) {
						SProductBean bean = kList.get(m);
						if (m > 0 && (m < kList.size() - 1)) {
							View sView = shopCatItemView(bean, true);
							((LinearLayout) mView).addView(sView);
						} else {
							View sView = shopCatItemView(bean, false);
							((LinearLayout) mView).addView(sView);
						}
					}
					((LinearLayout) v).addView(mView);
				}
				v.setBackgroundResource(R.drawable.rounded_background);
				LinearLayout mLayout = new LinearLayout(activity);
				mLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, SystemUtil.px2dip(activity,
						8)));
				layout.addView(v);
				layout.addView(mLayout);
			}
			if (ZGApplication.getInstance().getAddressBean() != null) {
				resetAddressData();
			}
			if (shopCartBean.getConsigneeInfo() != null) {
				ZGApplication.getInstance().setConsId(shopCartBean.getConsigneeInfo().getConsId());
				consignee.setText(shopCartBean.getConsigneeInfo().getConsignee());
				consMobile.setText(shopCartBean.getConsigneeInfo().getConsMobile());
				consAddress.setText(shopCartBean.getConsigneeInfo().getConsAddress());
			}
			invoiceTypeName.setText(shopCartBean.getInvoiceInfo().getInvoiceTypeName());
            String address =    shopCartBean.getInvoiceInfo().getIncoiceReceiverAddress();
            if (address !=null && address.length()>0){
                invoiceTypeAdress.setText(invoiceTypeAdress.getText()+address);
            } else {
                 invoiceTypeAdress.setVisibility(View.GONE);
            }
		}
	}

	private View insertTextView(String arg) {
		View textView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_text_view, null);
		TextView tView = (TextView) textView.findViewById(R.id.shop_cart_head_pro);
		tView.setText(arg);
		TextPaint tp = tView.getPaint();
		tp.setFakeBoldText(true);
		return textView;
	}

	private View insertTextView(String arg, boolean flag) {
		View textView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_txt_and_img, null);
		TextView tView = (TextView) textView.findViewById(R.id.shop_cart_head_pro);
		ImageView iView = (ImageView) textView.findViewById(R.id.shop_cart_head_img);
		tView.setText(arg);
		if (flag) {
			iView.setVisibility(View.VISIBLE);
		} else {
			iView.setVisibility(View.GONE);
		}
		return textView;
	}

	private View shopCatItemView(final SProductBean bean, final boolean flag) {
		View mView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_list_item, null);
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.shop_cart_list_item_pro_layout);
		TextView name = (TextView) mView.findViewById(R.id.shop_cart_item_name);
		ImageView itemImg = (ImageView) mView.findViewById(R.id.shop_cart_item_pic);
		final TextView price = (TextView) mView.findViewById(R.id.shop_cart_item_price);
		final TextView money = (TextView) mView.findViewById(R.id.shop_cart_item_money);
		Button remove = (Button) mView.findViewById(R.id.shop_cart_item_num_remove);
		final TextView numEditText = (TextView) mView.findViewById(R.id.shop_cart_item_num);
		Button add = (Button) mView.findViewById(R.id.shop_cart_item_num_add);
		Button del = (Button) mView.findViewById(R.id.shop_cart_item_del);
		TextView act = (TextView) mView.findViewById(R.id.shop_cart_item_act);
		ImageView img = (ImageView) mView.findViewById(R.id.shop_cart_item_img);
		if (flag) {
			img.setVisibility(View.VISIBLE);
		} else {
			img.setVisibility(View.GONE);
		}
		ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progress);
		itemImg.setTag(progressBar);
		imageLoader.displayImage(bean.getProductInfo().getPicPath1Url(), itemImg, options,
				DisplayImageOptionsUtil.getImageLoadingListener());
		Log.d("bean", "");
		final String titleName = bean.getProductInfo().getBrandName() + "  " + bean.getProductInfo().getPattern() + "  "
				+ bean.getProductInfo().getColorSpec()+(bean.getProductInfo().getIsGovEnterprise().equals(1) ? getString(R.string.govEnterprise) : "");
		name.setText(titleName);
		TextPaint tp = name.getPaint();
		tp.setFakeBoldText(true);
		price.setText(getResources().getString(R.string.unit_price) + StringUtils.formatMoney(bean.getCurrentPrice()));
		money.setText(getResources().getString(R.string.product_cur_cost) + StringUtils.formatMoney(bean.getTotalPrice()));
		numEditText.setText(bean.getQuantity() + "");
		List<ActItemInfo> infoBean = bean.getItemActInfoList();
		StringBuilder sBuilder = new StringBuilder();
		String argString = "";
		if (infoBean != null && infoBean.size() != 0) {
			for (int i = 0; i < infoBean.size(); i++) {
				sBuilder.append(",").append(infoBean.get(i).getActInfo()).append("\n");
			}
			argString = sBuilder.toString().replaceFirst(",", "").toString();
		}
		if (argString == null || argString.equals("")) {
			act.setVisibility(View.GONE);
		} else {
			act.setVisibility(View.VISIBLE);
			act.setText(argString);
		}
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SystemUtil.proItemOnClickListener(2, bean.getProductInfo().getProductDetailUrl(), titleName, bean
						.getProductInfo().getPicPath5Url());
			}
		});
		numEditText.setKeyListener(null);
		numEditText.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					MyAlertDialogFragment newFragment = new MyAlertDialogFragment();
					newFragment.setLinkQuantityTV(numEditText);
					newFragment.setLinkMoneyTV(money);
					newFragment.setPrice(bean.getCurrentPrice());
					newFragment.setProductId(bean.getProductInfo().getProductId());
					newFragment.setQuantity(Integer.parseInt(numEditText.getText().toString()));
					newFragment.show(getChildFragmentManager(), "dialog");
				}
				return false;
			}
		});
		remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int arg = Integer.parseInt(numEditText.getText().toString());
				if (arg > 1) {
					currentItemQuantity = arg - 1;
					currentProductBean = bean;
					currentItemMoneyTextView = money;
					currentItemNumEditTextView = numEditText;
					ShopCartUtils.getInstance(mHandler).updateShopCart(activity,
							currentProductBean.getProductInfo().getProductId() + "", currentItemQuantity);
				}
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int arg = Integer.parseInt(numEditText.getText().toString());
				if (arg < 999 && arg >= 0) {
					currentItemQuantity = arg + 1;
					currentProductBean = bean;
					currentItemMoneyTextView = money;
					currentItemNumEditTextView = numEditText;
					ShopCartUtils.getInstance(mHandler).updateShopCart(activity,
							currentProductBean.getProductInfo().getProductId() + "", currentItemQuantity);
				}
			}
		});
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShopCartUtils.getInstance(mHandler).removeShopCart(activity, bean.getProductInfo().getProductId() + "");
			}
		});
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void resetAddressData() {
		DeliveryAddressBean addressBean = ZGApplication.getInstance().getAddressBean();
		SConsigneeInfo consigneeInfo = shopCartBean.getConsigneeInfo();
		if (null == consigneeInfo) {
			consigneeInfo = new SConsigneeInfo();
		}
		consigneeInfo.setConsId(addressBean.getConsId());
		consigneeInfo.setConsAddress(addressBean.getFullConsAddress());
		consigneeInfo.setConsignee(addressBean.getConsigneed());
		consigneeInfo.setConsMobile(addressBean.getConsMobile());
		shopCartBean.setConsigneeInfo(consigneeInfo);
		ZGApplication.getInstance().setAddressBean(null);
		initData();
	}
}
