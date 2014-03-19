package com.digione.zgb2b.fragment.shopcart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.ActItemInfo;
import com.digione.zgb2b.bean.shopcart.*;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.*;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCartConfigFragment extends CommonBaseFragment {

	private View view;
	private LinearLayout layout;
	private CShopCartBean shopCartBean;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private CustomerJsonHttpResponseHandler<CShopCartBean> jsonHttpResponseHandler;
	private CustomerJsonHttpResponseHandler<OrderSucBean> orderJsonHttpResponseHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.SHOP_CART_COMFIRM_SUCCESS:
				initData();
				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shop_cart_config, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<CShopCartBean>(activity,
				ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, getArguments()) {

			@Override
			public void processCallSuccess(CShopCartBean outBean, String msg) {
				shopCartBean = outBean;
				mHandler.sendEmptyMessage(MsgCode.SHOP_CART_COMFIRM_SUCCESS);
			}
		};
		if (view != null) {
			findViewById();
		}
	}

	private void findViewById() {
        TitleView titleView = (TitleView) view.findViewById(R.id.title);
		titleView.setTitle(getResources().getString(R.string.shop_cart_order));
		layout = (LinearLayout) view.findViewById(R.id.shop_cart_config_layout);
        Button okBtn = (Button) view.findViewById(R.id.shop_cart_config_btn);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<OrderSucBean>(activity,
						ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, getArguments()) {
					@Override
					public void processCallSuccess(OrderSucBean outBean, String msg) {
						Bundle bundle = new Bundle();
						bundle.putSerializable(ArgName.ShopCartOrder.ORDER_BEAN, outBean);
						ShopCartUtils.getInstance(mHandler).clearShopCartData(activity);
						ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.SHOPPINGCART_ORDER_SUC, bundle);
					}
				};
				HttpClient httpClient = HttpClient.getInstall(activity);
				httpClient.postAsync(Constants.Url.SHOP_CART_ORDER_044, null, orderJsonHttpResponseHandler);
			}
		});
		imageLoader = ImageLoader.getInstance();
		options = DisplayImageOptionsUtil.getDisplayImageOptions();
		HttpClient httpClient = HttpClient.getInstall(activity);
		Map<String, String> map = new HashMap<String, String>();
		map.put("consId", String.valueOf(ZGApplication.getInstance().getConsId()));
		RequestParams params = new RequestParams(map);
		httpClient.postAsync(Constants.Url.SHOP_CART_COMFIRM_043, params, jsonHttpResponseHandler);
	}

	private void initData() {
		layout.removeAllViews();
		if (shopCartBean == null) {
			return;
		}
		List<COrderListBean> orderList = shopCartBean.getOrderList();
		if (orderList != null && orderList.size() != 0) {
			for (int i = 0; i < orderList.size(); i++) {
				COrderPriceInfo orderPriceInfo = orderList.get(i).getOrderPriceInfo();
				View v = insertTextView(orderList.get(i).getOrderTitle(), true);
				List<SendList> mList = orderList.get(i).getSendList();
				for (int j = 0; j < mList.size(); j++) {
					SendList sendList = mList.get(j);
					List<COrderItemList> itemList = sendList.getList();
					View v2=insertTextView(sendList.getSendTitle(), true);
					((LinearLayout) v).addView(v2);
					for (int k = 0; k < itemList.size(); k++) {
						View mv = insertTextView(getResources().getString(R.string.provtitle)
								+ itemList.get(k).getProvName(), false);
						List<COrderItem> items = itemList.get(k).getList();
						for (int n = 0; n < items.size(); n++) {
							COrderItem item = items.get(n);
							View nv = orderItemView(item);
							((LinearLayout) mv).addView(nv);
						}
						((LinearLayout) v).addView(mv);
					}
				}
				List<ActItemInfo> itemInfos = orderList.get(i).getActGiftInfoList();
				StringBuilder sb = new StringBuilder();
				String actInfo = "";
				if (itemInfos != null) {
					for (int m = 0; m < itemInfos.size(); m++) {
						sb.append(",").append(itemInfos.get(m).getActInfo()).append("/n");
					}
					actInfo = sb.toString().replaceFirst(",", "").toString();
				}
				View actView = insertActView(actInfo, orderPriceInfo);
				LinearLayout mLayout = new LinearLayout(activity);
				mLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				mLayout.setBackgroundResource(R.drawable.rounded_background);
				mLayout.setOrientation(LinearLayout.VERTICAL);
				mLayout.addView(v);
				mLayout.addView(actView);
				LinearLayout nLayout = new LinearLayout(activity);
				nLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, SystemUtil.px2dip(activity,
						8)));
				layout.addView(mLayout);
				layout.addView(nLayout);
				// layout.addView(actView);
			}
		}
	}

	private View insertTextView(String arg, boolean flag) {
		View textView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_text_view, null);
        if (null == textView) {
            return null;
        }
		TextView tView = (TextView) textView.findViewById(R.id.shop_cart_head_pro);
		tView.setText(arg);
		// 字体是否加粗
		if (flag) {
			TextPaint tpPaint = tView.getPaint();
			tpPaint.setFakeBoldText(true);
		}
		return textView;
	}

	private View orderItemView(COrderItem bean) {
		View mView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_config_item, null);
        if (null == mView) {
            return null;
        }
		ImageView img = (ImageView) mView.findViewById(R.id.shop_cart_config_item_pic);
		TextView name = (TextView) mView.findViewById(R.id.shop_cart_config_item_name);
		TextView num = (TextView) mView.findViewById(R.id.shop_cart_config_item_num);
		TextView price = (TextView) mView.findViewById(R.id.shop_cart_config_item_price);
		TextView actPrice = (TextView) mView.findViewById(R.id.shop_cart_config_item_actPrice);
		ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progress);
		img.setTag(progressBar);
		imageLoader.displayImage(bean.getPicPath1Url(), img, options, DisplayImageOptionsUtil.getImageLoadingListener());
		String titleName = bean.getBrandName() + "  " + bean.getPattern() + "  " + bean.getColorSpec()+(bean.getIsGovEnterprise().equals(1) ? getString(R.string.govEnterprise) : "");
		name.setText(titleName);
		TextPaint tPaint = name.getPaint();
		tPaint.setFakeBoldText(true);
		num.setText(getResources().getString(R.string.quantity) + bean.getQuantity());
		price.setText(getResources().getString(R.string.unit_price) + StringUtils.formatMoney(bean.getCurrentPrice()));
		actPrice.setText(getResources().getString(R.string.preferential) + StringUtils.formatMoney(bean.getActPrice()));
		return mView;
	}

	private View insertActView(String infoStr, COrderPriceInfo bean) {
		View mView = LayoutInflater.from(activity).inflate(R.layout.shop_cart_config_order_price, null);
        if (null == mView) {
            return null;
        }
		TextView actInfo = (TextView) mView.findViewById(R.id.shop_cart_config_act);
		TextView total = (TextView) mView.findViewById(R.id.shop_cart_config_price_total);
		TextView trans = (TextView) mView.findViewById(R.id.shop_cart_config_price_trans);
		TextView actPrice = (TextView) mView.findViewById(R.id.shop_cart_config_price_act);
		TextView amount = (TextView) mView.findViewById(R.id.shop_cart_config_price_amount);
		if (infoStr == null || infoStr.equals("")) {
			actInfo.setVisibility(View.GONE);
		} else {
			actInfo.setVisibility(View.VISIBLE);
			actInfo.setText(infoStr);
		}
		total.setText(StringUtils.formatMoney(bean.getTotalProductPrice()));
		trans.setText(StringUtils.formatMoney(bean.getTotalTransCost()));
		actPrice.setText(StringUtils.formatMoney(bean.getTotalActPrice()));
		amount.setText(StringUtils.formatMoney(bean.getTotalOrderAmount()));

        // 积分需要大于0才显示
        TextView actWebIntegralTitle = (TextView) mView.findViewById(R.id.shop_cart_config_price_act_web_integral_title);
        TextView actWebIntegralValue = (TextView) mView.findViewById(R.id.shop_cart_config_price_act_web_integral);
        if (bean.getTotalOrderActWebIntegral() > 0){
            actWebIntegralTitle.setVisibility(View.VISIBLE);
            actWebIntegralValue.setVisibility(View.VISIBLE);
            actWebIntegralValue.setText(bean.getTotalOrderActWebIntegral().toString());
        } else {
            actWebIntegralTitle.setVisibility(View.GONE);
            actWebIntegralValue.setVisibility(View.GONE);
        }
        TextView actSaleIntegralTitle = (TextView) mView.findViewById(R.id.shop_cart_config_price_act_sale_integral_title);
        TextView actSaleIntegralValue = (TextView) mView.findViewById(R.id.shop_cart_config_price_act_sale_integral);
        if (bean.getTotalOrderActSalesIntegral() > 0){
            actSaleIntegralTitle.setVisibility(View.VISIBLE);
            actSaleIntegralValue.setVisibility(View.VISIBLE);
            actSaleIntegralValue.setText(bean.getTotalOrderActSalesIntegral().toString());
        } else {
            actSaleIntegralTitle.setVisibility(View.GONE);
            actSaleIntegralValue.setVisibility(View.GONE);
        }
        TextView achievedWebIntegralTitle = (TextView) mView.findViewById(R.id.shop_cart_config_price_achieved_web_integral_title);
        TextView achievedWebIntegralValue = (TextView) mView.findViewById(R.id.shop_cart_config_price_achieved_web_integral);
        if (bean.getTotalOrderAchievedWebIntegral() > 0){
            achievedWebIntegralTitle.setVisibility(View.VISIBLE);
            achievedWebIntegralValue.setVisibility(View.VISIBLE);
            achievedWebIntegralValue.setText(bean.getTotalOrderAchievedWebIntegral().toString());
        } else {
            achievedWebIntegralTitle.setVisibility(View.GONE);
            achievedWebIntegralValue.setVisibility(View.GONE);
        }
        TextView achievedSaleIntegralTitle = (TextView) mView.findViewById(R.id.shop_cart_config_price_achieved_sale_integral_title);
        TextView achievedSaleIntegralValue = (TextView) mView.findViewById(R.id.shop_cart_config_price_achieved_sale_integral);
        if (bean.getTotalOrderAchievedSalesIntegral() > 0){
            achievedSaleIntegralTitle.setVisibility(View.VISIBLE);
            achievedSaleIntegralValue.setVisibility(View.VISIBLE);
            achievedSaleIntegralValue.setText(bean.getTotalOrderAchievedSalesIntegral().toString());
        } else {
            achievedSaleIntegralTitle.setVisibility(View.GONE);
            achievedSaleIntegralValue.setVisibility(View.GONE);
        }
		return mView;
	}

}
