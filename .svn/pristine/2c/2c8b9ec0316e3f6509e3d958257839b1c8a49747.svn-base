/**
 *  百分之百版权所有
 *
 */
package com.digione.zgb2b.adapter.workbench;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.workbench.OrderBean;
import com.digione.zgb2b.bean.workbench.SimpleProductBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.utils.BestpayUtil;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;

/**
 * @ClassName: OrderAdapter
 * @Description: 订单适配器
 * @author 尤振华
 * @date 2012-10-22 下午8:17:41
 * @version 1.0
 */
public class OrdersAdapter extends BaseAdapter {

	// 存放订单数据
	private ArrayList<OrderBean> data;
	// 上下文
	private Activity context;

	private LayoutInflater inflater;

	private AlertDialog mAlertDialog = null;

	/**
	 * 是否可以发起手机翼支付的标识。 0:不可以发起支付 1:可发起支付
	 */
	private final int IS_CAN_PAY = 1;
	/**
	 * 是否可以发起取消订单的标识。 0:不可以发起 1:可发起
	 */
	private final int IS_CAN_CANCEL = 1;
	/**
	 * 是否可以发起取消手机翼支付的标识。 0:不可以发起 1:可发起
	 */

	/**
	 * 选中的订单ID
	 */
	private Integer selectedOrderId;

	/**
	 * 翼支付支付工具类实例
	 */
	private BestpayUtil bestpayUtil;

	private CustomerJsonHttpResponseHandler<OrderBean> cancelOrderHandler;

	/**
	 * Title: getCount Description:总共几条数据
	 * 
	 * @return 总数据个数
	 * @see android.widget.Adapter#getCount()
	 */

	public OrdersAdapter(Fragment fragment, ArrayList<OrderBean> data, int orderType, BestpayUtil bestpayUtil) {
		this.data = data;
		this.context = fragment.getActivity();
		this.bestpayUtil = bestpayUtil;
		this.inflater = LayoutInflater.from(context);
		int fragmentId = ChangeFragmentUtil.WORKBENCH_ALL_ORDERS;
		if (orderType == Constants.OrderType.PENDING_ORDER) {
			fragmentId = ChangeFragmentUtil.WORKBENCH_PENDING_PAYMENT_ORDERS;
		}
		Bundle bundle = fragment.getArguments();
		bundle.putInt(ArgName.Orders.ORDER_TYPE, orderType);
		cancelOrderHandler = new CustomerJsonHttpResponseHandler<OrderBean>(context, fragmentId, bundle) {

			@Override
			public void processCallSuccess(OrderBean outBean, String msg) {
				for (int i = 0; i < OrdersAdapter.this.data.size(); i++) {
					OrderBean orderBean = OrdersAdapter.this.data.get(i);
					if (orderBean.getOrderId().equals(outBean.getOrderId())) {
						OrdersAdapter.this.data.set(i, outBean);
						notifyDataSetChanged();
						break;
					}
				}
				ToastUtil.showToast(getContext(), msg, ToastUtil.LENGTH_SHORT);
			}
		};
	}

	@Override
	public int getCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	/**
	 * Title: getItem Description:
	 * 
	 * @param position
	 *            索引
	 * @return 返回position对应的对象
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (data == null) {
			return null;
		}
		return data.get(position);
	}

	/**
	 * Title: getItemId Description:
	 * 
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * Title: getView Description:
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder;
		try {
			if (convertView == null) {
				mViewHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.orders_item, null);
				mViewHolder.orderNoTV = (TextView) convertView.findViewById(R.id.tv_order_number);
				mViewHolder.orderTotalAmountTV = (TextView) convertView.findViewById(R.id.tv_order_amount);
				mViewHolder.orderTimeTV = (TextView) convertView.findViewById(R.id.tv_order_time);
				mViewHolder.producrsLV = (ListView) convertView.findViewById(R.id.mybaigong_order_goods_lv);
				mViewHolder.orderStatusTV = (TextView) convertView.findViewById(R.id.tv_order_status);
				mViewHolder.payBtn = (Button) convertView.findViewById(R.id.workbench_pay_order_btn);
				mViewHolder.cancelBtn = (Button) convertView.findViewById(R.id.workbench_cancel_order_btn);
				mViewHolder.simpleProductLV = (ListView) convertView.findViewById(R.id.mybaigong_order_goods_lv);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			setConvertViewData(position, mViewHolder);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			Log.e("memory", "out of memory OutOfMemoryError", e);
		} catch (Exception e) {
			Log.e("memory", "Exception Error", e);
		}
		return convertView;
	}

	private void setConvertViewData(int position, ViewHolder mViewHolder) {
		if (data != null && data.size() > 0) {
			final OrderBean mOrderBean = data.get(position);
			// 设置显示值
			mViewHolder.orderNoTV.setText(context.getString(R.string.order_number) + mOrderBean.getOrderNo());
			// 设置金额为红色字体
			String amountString = context.getString(R.string.order_amount)
					+ StringUtils.formatMoney(mOrderBean.getOrderTotalAmount());
			mViewHolder.orderTotalAmountTV.setText(amountString);

			mViewHolder.orderTimeTV.setText(context.getString(R.string.order_time) + mOrderBean.getOrderTime());
			TextPaint tp = mViewHolder.orderStatusTV.getPaint();
			tp.setFakeBoldText(true);
			mViewHolder.producrsLV.setAdapter(new OrdersSimpleProductAdapter(context, mOrderBean.getList()));
			setListViewHeightBasedOnChildren(mViewHolder.producrsLV);
			SystemUtil.setListViewHeightBasedOnChildren(mViewHolder.producrsLV);

			mViewHolder.simpleProductLV.setOnItemClickListener(new SimpleProductOnClickListener(position));
			if (("1".equals(mOrderBean.getPayStatus()) || "4".equals(mOrderBean.getPayStatus()))
					&& !"1".equals(mOrderBean.getOrderStatus())) {
				mViewHolder.payBtn.setVisibility(View.VISIBLE);
			} else {
				mViewHolder.payBtn.setVisibility(View.GONE);
			}
			if (mOrderBean.getIsCanPay() == IS_CAN_PAY) {
				mViewHolder.payBtn.setBackgroundResource(R.drawable.button_background);
				mViewHolder.payBtn.setTextColor(context.getResources().getColor(R.color.white));
			} else {
				mViewHolder.payBtn.setBackgroundResource(R.drawable.button_background_gray);
				mViewHolder.payBtn.setTextColor(context.getResources().getColor(R.color.not_for_pay_text_color));
			}
			mViewHolder.payBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOrderBean.getIsCanPay() == 1) {
						Log.i(Constants.TAG, "开始付款");
						selectedOrderId = mOrderBean.getOrderId();
						bestpayUtil.makeOrderToBestpay(selectedOrderId);
					} else {
						ToastUtil.showToast(context, context.getString(R.string.not_support_bestpay_tips),
								ToastUtil.LENGTH_LONG);
					}
				}
			});
			mViewHolder.orderStatusTV.setText(mOrderBean.getOrderListStatus());

			if (mOrderBean.getIsCanCancel() == IS_CAN_CANCEL) {
				mViewHolder.cancelBtn.setVisibility(View.VISIBLE);
				final Button cancelBtn = mViewHolder.cancelBtn;
				// 取消订单
				cancelBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setTitle(context.getString(R.string.confirm));
						builder.setMessage(context.getString(R.string.order_cancel_confirm));
						builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface mDialogInterface, int which) {
								String url = Constants.Url.ORDER_CANCEL_030.replace("${id}", mOrderBean.getOrderId()
										.toString());
								cancelBtn.setEnabled(false);
								HttpClient client = HttpClient.getInstall(context);
								client.postAsync(url, null, cancelOrderHandler);
							}
						});
						builder.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface mDialogInterface, int which) {
								if (mAlertDialog != null) {
									mAlertDialog.dismiss();
								}
							}
						});
						mAlertDialog = builder.create();
						mAlertDialog.show();
					}
				});
			} else {
				mViewHolder.cancelBtn.setVisibility(View.GONE);
			}
		}
	}

	class ViewHolder {
		TextView orderNoTV;
		TextView orderTotalAmountTV;
		TextView orderTimeTV;
		ListView producrsLV;
		TextView orderStatusTV;
		Button payBtn;
		Button cancelBtn;
		// 订单明细
		ListView simpleProductLV;
	}

	class SimpleProductOnClickListener implements AdapterView.OnItemClickListener {

		private int position;

		public SimpleProductOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
			OrderBean itembean = (OrderBean) data.get(position);
			SimpleProductBean simpleProductBean = itembean.getList().get(index);
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, "onItemClick 点击事件url=" + simpleProductBean.getProductDetailUrl());
			}
			String url = simpleProductBean.getProductDetailUrl();
			String title = simpleProductBean.getBrandName() + " " + simpleProductBean.getPattern();
			String picUrl = simpleProductBean.getPicPath1Url();
			Bundle bundle = new Bundle();
			bundle.putString(ArgName.ProductDetail.URL, url);
			bundle.putString(ArgName.ProductDetail.TITLE, title);
			bundle.putString(ArgName.ProductDetail.PIC_URL, picUrl);
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_DETAIL, bundle);
		}
	}

	public Integer getSelectedOrderId() {
		return selectedOrderId;
	}

}