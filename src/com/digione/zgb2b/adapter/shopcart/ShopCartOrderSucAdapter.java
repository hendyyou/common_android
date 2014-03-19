package com.digione.zgb2b.adapter.shopcart;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.shopcart.OrderInfoBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.utils.BestpayUtil;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.ToastUtil;

public class ShopCartOrderSucAdapter extends BaseAdapter {

	private List<OrderInfoBean> list;
	private LayoutInflater inflater;

	/**
	 * 选中的订单ID
	 */
	private Integer selectedOrderId;

	/**
	 * 翼支付支付工具类实例
	 */
	private BestpayUtil bestpayUtil;

	private Activity activity;

	public ShopCartOrderSucAdapter(Activity activity, List<OrderInfoBean> mList, BestpayUtil bestpayUtil) {
		this.list = mList;
		this.bestpayUtil = bestpayUtil;
		this.inflater = LayoutInflater.from(activity);
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.shop_cart_suc_item, null);
			holder.id = (TextView) convertView.findViewById(R.id.shop_cart_suc_item_id);
			holder.msg = (TextView) convertView.findViewById(R.id.shop_cart_suc_item_fail_msg);
			holder.num = (TextView) convertView.findViewById(R.id.shop_cart_suc_item_num);
			holder.payType = (TextView) convertView.findViewById(R.id.shop_cart_suc_item_pay_type);
			holder.money = (TextView) convertView.findViewById(R.id.shop_cart_suc_item_money);
			holder.payBtn = (Button) convertView.findViewById(R.id.shop_cart_suc_item_pay_btn);
			holder.proBtn = (Button) convertView.findViewById(R.id.shop_cart_suc_item_pro_btn);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.shop_cart_suc_item_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final OrderInfoBean orderInfoBean = list.get(position);
		holder.id.setText(orderInfoBean.getOrderTitle());
		if (orderInfoBean.getIsSucceed() == 1) {
			holder.num.setText(orderInfoBean.getOrderNo());
			holder.money.setText(StringUtils.formatMoney(orderInfoBean.getTotalOrderAmount()));
			holder.msg.setVisibility(View.GONE);
			holder.layout.setVisibility(View.VISIBLE);
			if (orderInfoBean.getIsCanPay() == 1) {
				holder.payBtn.setBackgroundResource(R.drawable.button_background);
				holder.payBtn.setTextColor(activity.getResources().getColor(R.color.white));
			} else {
				holder.payBtn.setBackgroundResource(R.drawable.button_background_gray);
				holder.payBtn.setTextColor(activity.getResources().getColor(R.color.not_for_pay_text_color));
			}
			holder.payBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (orderInfoBean.getIsCanPay() == 1) {
						Log.i(Constants.TAG, "开始付款");
						selectedOrderId = orderInfoBean.getOrderId();
						bestpayUtil.makeOrderToBestpay(selectedOrderId);
					} else {
						ToastUtil.showToast(activity, activity.getString(R.string.not_support_bestpay_tips),
								ToastUtil.LENGTH_LONG);
					}
				}
			});
			holder.payType.setText(orderInfoBean.getOrderPayStatus());
			holder.proBtn.setVisibility(View.VISIBLE);
			holder.proBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString(ArgName.OrdersDetail.TITLE, activity.getString(R.string.order_detail));
					bundle.putInt(ArgName.OrdersDetail.ORDER_ID, orderInfoBean.getOrderId());
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL, bundle);
				}
			});
		} else if (orderInfoBean.getIsSucceed() == 0) {
			holder.msg.setText(orderInfoBean.getFailedMsg());
			holder.msg.setVisibility(View.VISIBLE);
			holder.num.setVisibility(View.GONE);
			holder.layout.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView id;
		TextView msg;
		TextView num;
		TextView payType;
		TextView money;
		Button payBtn;
		Button proBtn;
		LinearLayout layout;
	}

	public Integer getSelectedOrderId() {
		return selectedOrderId;
	}

}
