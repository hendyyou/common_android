/**
 *  百分之百版权所有
 *
 */
package com.digione.zgb2b.adapter.workbench;

import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.workbench.GiftListBean;
import com.digione.zgb2b.bean.workbench.SendListBean;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 尤振华
 * @version 1.0
 * @ClassName: OrderAdapter
 * @Description: 订单明细商品列表适配器
 * @date 2012-10-22 下午8:17:41
 */
public class OrderDetailShipmentAdapter extends BaseAdapter {

	// 存放订单数据
	private List<SendListBean> sendList;
	// 上下文
	private Context context;
	private LayoutInflater inflater;

	/**
	 * Title: getCount Description:总共几条数据
	 * 
	 * @return 总数据个数
	 * @see android.widget.Adapter#getCount()
	 */

	public OrderDetailShipmentAdapter(Context context, List<SendListBean> sendList) {
		this.sendList = sendList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.sendList.size();
	}

	/**
	 * Title: getItem Description: 订单明细商品列表适配器
	 * 
	 * @param position
	 *            索引
	 * @return 返回position对应的对象
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.sendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
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
				convertView = inflater.inflate(R.layout.order_detail_item, null);
				mViewHolder.shipmentNoTitleTV = (TextView) convertView
						.findViewById(R.id.order_detail_item_shipment_no_title_tv);
				TextPaint tp = mViewHolder.shipmentNoTitleTV.getPaint();
				tp.setFakeBoldText(true);
				mViewHolder.shipmentNoTV = (TextView) convertView.findViewById(R.id.order_detail_item_shipment_no_tv);
				mViewHolder.airWayBillNoTV = (TextView) convertView.findViewById(R.id.order_detail_item_air_way_bill_no_tv);
				mViewHolder.productCostTV = (TextView) convertView.findViewById(R.id.order_detail_item_product_cost_tv);
				mViewHolder.subtractPreferentialTV = (TextView) convertView
						.findViewById(R.id.order_detail_item_subtract_preferential_tv);
				mViewHolder.carriageTV = (TextView) convertView.findViewById(R.id.order_detail_item_carriage_tv);
				mViewHolder.subTotalTV = (TextView) convertView.findViewById(R.id.order_detail_item_sub_total_tv);
				mViewHolder.descriptionOfGiftsTV = (TextView) convertView
						.findViewById(R.id.order_detail_item_description_of_gifts_tv);
				mViewHolder.descriptionOfGiftsLL = (LinearLayout) convertView
						.findViewById(R.id.order_detail_item_description_of_gifts_ll);
				mViewHolder.simpleProductLV = (ListView) convertView.findViewById(R.id.order_detail_item_simple_product_lv);

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

	/**
	 * @param mViewHolder
	 * @param position
	 *            位置
	 * @return void 返回类型
	 * @Title: setConvertViewData
	 * @Description: 设置convertview的数据
	 */
	private void setConvertViewData(int position, ViewHolder mViewHolder) {
		final SendListBean sendListBean = sendList.get(position);
		mViewHolder.shipmentNoTV.setText(sendListBean.getSendId() + "\t(" + sendListBean.getSendStatusName() + ")");
		TextPaint tp = mViewHolder.shipmentNoTV.getPaint();
		tp.setFakeBoldText(true);

		mViewHolder.airWayBillNoTV.setText(sendListBean.getLogisticsSend().getOrderEms());
		mViewHolder.productCostTV.setText(StringUtils.formatMoney(sendListBean.getPriceInfo().getTotalPrice()));
		mViewHolder.subtractPreferentialTV.setText(StringUtils.formatMoney(sendListBean.getPriceInfo().getActPrice()));
		mViewHolder.carriageTV.setText(StringUtils.formatMoney(sendListBean.getPriceInfo().getTransCost()));
		mViewHolder.subTotalTV.setText(StringUtils.formatMoney(sendListBean.getPriceInfo().getSendTotalAmount()));
		ArrayList<GiftListBean> giftList = sendListBean.getGiftList();
		if (giftList != null) {
			StringBuffer gifStr = new StringBuffer();
			for (int i = 0; i < giftList.size(); i++) {
				GiftListBean tmp = giftList.get(i);
				gifStr.append("-" + tmp.getBrandName() + tmp.getPattern() + context.getString(R.string.quantity)
						+ tmp.getQuantity());
			}
			mViewHolder.descriptionOfGiftsTV.setText(gifStr.toString());
		} else {
			mViewHolder.descriptionOfGiftsLL.setVisibility(View.GONE);
		}
		mViewHolder.simpleProductLV.setAdapter(new OrderDetailVendorAdapter(context, sendListBean.getList()));
		SystemUtil.setListViewHeightBasedOnChildren(mViewHolder.simpleProductLV);
	}

	class ViewHolder {
		TextView shipmentNoTitleTV;
		TextView shipmentNoTV;
		TextView airWayBillNoTV;
		TextView productCostTV;
		TextView subtractPreferentialTV;
		TextView carriageTV;
		TextView subTotalTV;
		TextView descriptionOfGiftsTV;
		LinearLayout descriptionOfGiftsLL;
		ListView simpleProductLV;
	}

}
