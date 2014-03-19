package com.digione.zgb2b.fragment.workbench;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.workbench.OrderDetailShipmentAdapter;
import com.digione.zgb2b.bean.workbench.*;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.*;
import com.digione.zgb2b.widget.TitleView;

import java.util.ArrayList;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class OrdersDetailFragment extends CommonBaseFragment {

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
	private final int IS_CAN_CANCEL_PAY = 1;
	private ListView shipmentLV;
	private TextView orderStatusTV;
	private TextView orderNoTV;
	private TextView productCostTV;
	private TextView subtractPreferentialTV;
	private TextView carriageTV;
	private TextView amountTV;
	private TextView surfingPaymentTV;
	private TextView needPaymentTV;
	private TextView consignee;
	private TextView consigneeMobile;
	private TextView consigneeAddress;
	private TextView logisticsBusiness;
	private TextView invoiceType;
	private Button topPayBtn;
	private Button bottomPayBtn;
	private Button cancelOrderBtn;
	private LinearLayout payInfoLL;
	private AlertDialog mAlertDialog = null;
	private OrderDetailBean orderDetailBean;
	private OrderDetailShipmentAdapter shipmentAdapter;
    private TextView actWebIntegralTitle;
    private TextView actWebIntegralValue;
    private TextView actSaleIntegralTitle;
    private TextView actSaleIntegralValue;
    private TextView achievedWebIntegralTitle;
    private TextView achievedWebIntegralValue;
    private TextView achievedSaleIntegralTitle;
    private TextView achievedSaleIntegralValue;

	/**
	 * 翼支付支付工具类实例
	 */
	private BestpayUtil bestpayUtil;

	// 获取订单详细Handler
	private CustomerJsonHttpResponseHandler<OrderDetailBean> orderDetailHandler;

	private CustomerJsonHttpResponseHandler<OrderBean> cancelOrderHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (ZGApplication.isDevMode()) {
				Log.d(Constants.TAG, this.getClass().getSimpleName() + " handleMessage() msg.what:" + msg.what);
			}
			switch (msg.what) {
			case MsgCode.MOBILE_BESTPAY_ORDERDETAIL_RETURN: {
				ZGApplication.getInstance().dismissDialog();
				requestHttp();
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bestpayUtil = new BestpayUtil(this);

		// 获取订单详细Handler
		orderDetailHandler = new CustomerJsonHttpResponseHandler<OrderDetailBean>(activity,
				ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL, getArguments()) {

			@Override
			public void processCallSuccess(OrderDetailBean outBean, String msg) {
				orderDetailBean = outBean;
				orderStatusTV.setText(orderDetailBean.getOrderDetailStatus() + "\t(" + orderDetailBean.getOrderPayStatus()
						+ ")");
				orderNoTV.setText(orderDetailBean.getOrderNo());
                OrderPriceInfoBean orderPriceInfoBean = orderDetailBean.getOrderPriceInfo();
				productCostTV.setText(StringUtils.formatMoney(orderPriceInfoBean.getTotalProductPrice()));
				subtractPreferentialTV.setText(StringUtils.formatMoney(orderPriceInfoBean.getTotalActPrice()));
				carriageTV.setText(StringUtils.formatMoney(orderPriceInfoBean.getTotalTransCost()));
				amountTV.setText(StringUtils.formatMoney(orderPriceInfoBean.getTotalOrderAmount()));

                // 积分需要大于0才显示
                if (orderPriceInfoBean.getTotalOrderActWebIntegral() > 0){
                    actWebIntegralTitle.setVisibility(View.VISIBLE);
                    actWebIntegralValue.setVisibility(View.VISIBLE);
                    actWebIntegralValue.setText(orderPriceInfoBean.getTotalOrderActWebIntegral().toString());
                } else {
                    actWebIntegralTitle.setVisibility(View.GONE);
                    actWebIntegralValue.setVisibility(View.GONE);
                }
                if (orderPriceInfoBean.getTotalOrderActSalesIntegral() > 0){
                    actSaleIntegralTitle.setVisibility(View.VISIBLE);
                    actSaleIntegralValue.setVisibility(View.VISIBLE);
                    actSaleIntegralValue.setText(orderPriceInfoBean.getTotalOrderActSalesIntegral().toString());
                } else {
                    actSaleIntegralTitle.setVisibility(View.GONE);
                    actSaleIntegralValue.setVisibility(View.GONE);
                }
                if (orderPriceInfoBean.getTotalOrderAchievedWebIntegral() > 0){
                    achievedWebIntegralTitle.setVisibility(View.VISIBLE);
                    achievedWebIntegralValue.setVisibility(View.VISIBLE);
                    achievedWebIntegralValue.setText(orderPriceInfoBean.getTotalOrderAchievedWebIntegral().toString());
                } else {
                    achievedWebIntegralTitle.setVisibility(View.GONE);
                    achievedWebIntegralValue.setVisibility(View.GONE);
                }
                if (orderPriceInfoBean.getTotalOrderAchievedSalesIntegral() > 0){
                    achievedSaleIntegralTitle.setVisibility(View.VISIBLE);
                    achievedSaleIntegralValue.setVisibility(View.VISIBLE);
                    achievedSaleIntegralValue.setText(orderPriceInfoBean.getTotalOrderAchievedSalesIntegral().toString());
                } else {
                    achievedSaleIntegralTitle.setVisibility(View.GONE);
                    achievedSaleIntegralValue.setVisibility(View.GONE);
                }

				// surfingPaymentTV.setText(orderDetailBean.getPayDetailInfo());
				// 支付明细
				ArrayList<BookPayBean> tmpBookPayList = orderDetailBean.getPayDetailInfo().getBookPayList();
				StringBuilder payInfo = new StringBuilder();
				if (tmpBookPayList != null) {
                    for (BookPayBean bookPayBean: tmpBookPayList) {
						payInfo.append(bookPayBean.getBookPayTitle());
                        payInfo.append(StringUtils.formatMoney(bookPayBean.getBookPayAmount()));
                        payInfo.append("\n");
					}
				}

				OfflinePayBean offlinePay = orderDetailBean.getPayDetailInfo().getOfflinePay();
				if (offlinePay != null) {
					payInfo.append(offlinePay.getOfflinePayTitle());
                    payInfo.append(StringUtils.formatMoney(offlinePay.getOfflinePayAmount()));
				}

				OnlinePayBean onlinePay = orderDetailBean.getPayDetailInfo().getOnlinePay();
				if (onlinePay != null) {
					payInfo.append(onlinePay.getOnlinePayTitle());
                    payInfo.append(StringUtils.formatMoney(onlinePay.getOnlinePayAmount()));
				}

				if (payInfo.length() <= 0) {
					payInfoLL.setVisibility(View.GONE);
				} else {
					surfingPaymentTV.setText(payInfo.toString());
				}

				needPaymentTV.setText(StringUtils.formatMoney(orderDetailBean.getOrderPriceInfo().getNeedPayAmount()));

				consignee.setText(orderDetailBean.getLogisticsInfo().getConsignee());
				consigneeMobile.setText(orderDetailBean.getLogisticsInfo().getConsMobile());
				consigneeAddress.setText(orderDetailBean.getLogisticsInfo().getConsAddress());
				logisticsBusiness.setText(orderDetailBean.getLogisticsInfo().getLogisticsName());
				invoiceType.setText(orderDetailBean.getInvoiceTypeText());
				shipmentAdapter = new OrderDetailShipmentAdapter(activity, orderDetailBean.getSendList());
				shipmentLV.setAdapter(shipmentAdapter);
				// 是否显示取消按钮
				if (IS_CAN_CANCEL == orderDetailBean.getIsCanCancel()
						|| IS_CAN_CANCEL_PAY == orderDetailBean.getIsCanCancelPay()) {
					cancelOrderBtn.setVisibility(View.VISIBLE);
				} else {
					cancelOrderBtn.setVisibility(View.GONE);
				}
				// 是否显示支付按钮
				if (("1".equals(orderDetailBean.getPayStatus()) || "4".equals(orderDetailBean.getPayStatus()))
						&& !"1".equals(orderDetailBean.getOrderStatus())) {
					topPayBtn.setVisibility(View.VISIBLE);
					bottomPayBtn.setVisibility(View.VISIBLE);
				} else {
					topPayBtn.setVisibility(View.GONE);
					bottomPayBtn.setVisibility(View.GONE);
				}
				int canPay = orderDetailBean.getIsCanPay();
				if (IS_CAN_PAY == canPay) {
					topPayBtn.setBackgroundResource(R.drawable.button_background);
					topPayBtn.setTextColor(getResources().getColor(R.color.white));
					bottomPayBtn.setBackgroundResource(R.drawable.button_background);
					bottomPayBtn.setTextColor(getResources().getColor(R.color.white));
				} else {
					topPayBtn.setBackgroundResource(R.drawable.button_background_gray);
					topPayBtn.setTextColor(getResources().getColor(R.color.not_for_pay_text_color));
					bottomPayBtn.setBackgroundResource(R.drawable.button_background_gray);
					bottomPayBtn.setTextColor(getResources().getColor(R.color.not_for_pay_text_color));
				}

				SystemUtil.setListViewHeightBasedOnChildren(shipmentLV);
			}
		};

		cancelOrderHandler = new CustomerJsonHttpResponseHandler<OrderBean>(activity,
				ChangeFragmentUtil.WORKBENCH_ORDER_DETAIL, getArguments()) {

			@Override
			public void processCallSuccess(OrderBean outBean, String msg) {
				ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
				requestHttp();
			}
		};
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_detail, container, false);
        if (null == v) {
            return null;
        }

		// 顶部工具栏
		TitleView mTitle = (TitleView) v.findViewById(R.id.title);
		mTitle.setTitle(getArguments().getString(ArgName.OrdersDetail.TITLE));
		// 隐藏两边的button
		mTitle.hiddenButton();

		shipmentLV = (ListView) v.findViewById(R.id.order_detail_shipment_lv);
		orderStatusTV = (TextView) v.findViewById(R.id.order_detail_status_tv);
		orderNoTV = (TextView) v.findViewById(R.id.order_detail_no_tv);
		productCostTV = (TextView) v.findViewById(R.id.order_detail_product_cost_tv);
		subtractPreferentialTV = (TextView) v.findViewById(R.id.order_detail_subtract_preferential_tv);
		carriageTV = (TextView) v.findViewById(R.id.order_detail_carriage_tv);
		amountTV = (TextView) v.findViewById(R.id.order_detail_amount_tv);

        actWebIntegralTitle = (TextView) v.findViewById(R.id.order_detail_act_web_integral_title);
        actWebIntegralValue = (TextView) v.findViewById(R.id.order_detail_act_web_integral);
        actSaleIntegralTitle = (TextView) v.findViewById(R.id.order_detail_act_sale_integral_title);
        actSaleIntegralValue = (TextView) v.findViewById(R.id.order_detail_act_sale_integral);
        achievedWebIntegralTitle = (TextView) v.findViewById(R.id.order_detail_achieved_web_integral_title);
        achievedWebIntegralValue = (TextView) v.findViewById(R.id.order_detail_achieved_web_integral);
        achievedSaleIntegralTitle = (TextView) v.findViewById(R.id.order_detail_achieved_sale_integral_title);
        achievedSaleIntegralValue = (TextView) v.findViewById(R.id.order_detail_achieved_sale_integral);


        surfingPaymentTV = (TextView) v.findViewById(R.id.order_detail_surfing_payment_tv);
		payInfoLL = (LinearLayout) v.findViewById(R.id.order_detail_surfing_payment_ll);
		needPaymentTV = (TextView) v.findViewById(R.id.order_detail_need_payment_tv);
		consignee = (TextView) v.findViewById(R.id.order_detail_consignee_tv);
		consigneeMobile = (TextView) v.findViewById(R.id.order_detail_consignee_mobile_tv);
		consigneeAddress = (TextView) v.findViewById(R.id.order_detail_consignee_address_tv);
		logisticsBusiness = (TextView) v.findViewById(R.id.order_detail_logistics_business_tv);
		invoiceType = (TextView) v.findViewById(R.id.order_detail_invoice_type_tv);

		// 上面付款按钮
		topPayBtn = (Button) v.findViewById(R.id.order_detail_pay_top_btn);
		topPayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (orderDetailBean.getIsCanPay() == 1) {
					Log.i(Constants.TAG, "开始付款");
					bestpayUtil.makeOrderToBestpay(orderDetailBean.getOrderId());
				} else {
					ToastUtil.showToast(activity, getString(R.string.not_support_bestpay_tips), ToastUtil.LENGTH_LONG);
				}
			}
		});
		// 下面付款按钮
		bottomPayBtn = (Button) v.findViewById(R.id.order_detail_pay_bottom_btn);
		bottomPayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (orderDetailBean.getIsCanPay() == 1) {
					Log.i(Constants.TAG, "开始付款");
					bestpayUtil.makeOrderToBestpay(orderDetailBean.getOrderId());
				} else {
					ToastUtil.showToast(activity, getString(R.string.not_support_bestpay_tips), ToastUtil.LENGTH_LONG);
				}
			}
		});
		// 取消订单按钮
		cancelOrderBtn = (Button) v.findViewById(R.id.order_detail_order_cancel_btn);
		cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle(getString(R.string.confirm));
				builder.setMessage(getString(R.string.order_cancel_confirm));
				builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface mDialogInterface, int which) {
						if (orderDetailBean != null) {
							String url = Constants.Url.ORDER_CANCEL_030.replace("${id}", orderDetailBean.getOrderId()
									.toString());
							cancelOrderBtn.setEnabled(false);
							HttpClient client = HttpClient.getInstall(activity);
							client.postAsync(url, null, cancelOrderHandler);
						}
					}
				});
				builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

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

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		requestHttp();
	}

	/**
     *
     */
	private void requestHttp() {
		HttpClient orderClient = HttpClient.getInstall(activity);
		Integer orderId = getArguments().getInt(ArgName.OrdersDetail.ORDER_ID);
		String url = Constants.Url.ORDER_DETAIL_028.replace("${id}", orderId.toString());
		orderClient.postAsync(url, null, orderDetailHandler);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BestpayUtil.BESTPAY_REQUEST_CODE) {
			switch (resultCode) {
			case 0: { // 处理订单，从平台获取发货信息并通知用户
				Log.i(Constants.TAG, "从翼支付apk返回，处理订单，从平台获取发货信息并通知用户");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERDETAIL_RETURN);
				break;
			}
			case 1: { // 支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息
				Log.i(Constants.TAG, "从翼支付apk返回，支付情况未知，第三方客户端需从支付应用平台获取交易和发货信息");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERDETAIL_RETURN);
				break;
			}
			case 2: { // 用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付
				Log.i(Constants.TAG, "从翼支付apk返回，用户点击了返回键放弃支付，业务管理平台此笔订单仍可支付");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERDETAIL_RETURN);
				break;
			}
			default:
				Log.i(Constants.TAG, "从翼支付apk返回");
				mHandler.sendEmptyMessage(MsgCode.MOBILE_BESTPAY_ORDERDETAIL_RETURN);
			}
		}
	}
}