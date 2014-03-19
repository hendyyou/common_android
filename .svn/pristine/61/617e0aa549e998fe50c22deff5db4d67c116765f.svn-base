/**
 *  百分之百版权所有
 *
 */
package com.digione.zgb2b.adapter.workbench;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.workbench.InvoiceListOfProductsBean;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.SystemUtil;

/**
 * @author 尤振华
 * @version 1.0
 * @ClassName: OrderAdapter
 * @Description: 订单明细商品列表适配器
 * @date 2012-10-22 下午8:17:41
 */
public class OrderDetailVendorAdapter extends BaseAdapter {

	// 上下文
	private final Context context;
	// 存放订单数据
	private List<InvoiceListOfProductsBean> data;
	// 异步图片下载
	// private AsyncImageLoader imgloader = new AsyncImageLoader();
	// private ImageLoader imageLoader;
	private LayoutInflater inflater;

	/**
	 * Title: getCount Description:总共几条数据
	 * 
	 * @return 总数据个数
	 * @see android.widget.Adapter#getCount()
	 */

	public OrderDetailVendorAdapter(Context context, List<InvoiceListOfProductsBean> data) {
		this.data = data;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
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
		return data.get(position);
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
				convertView = inflater.inflate(R.layout.order_detail_vendor_item, null);
				mViewHolder.vendorNameTV = (TextView) convertView.findViewById(R.id.order_detail_vendor_item_vendorname_tv);
				mViewHolder.pruductLV = (ListView) convertView.findViewById(R.id.order_detail_vendor_item_product_lv);
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
	private void setConvertViewData(final int position, ViewHolder mViewHolder) {
		final InvoiceListOfProductsBean mInvoiceListOfProductsBean = data.get(position);
		String vendor = context.getString(R.string.provtitle);
		mViewHolder.vendorNameTV.setText(vendor + mInvoiceListOfProductsBean.getProvName());
		OrderDetailSimpleProductAdapter adapter = new OrderDetailSimpleProductAdapter(context,
				mInvoiceListOfProductsBean.getList());
		mViewHolder.pruductLV.setAdapter(adapter);
		// 点击事件
		mViewHolder.pruductLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				String url = mInvoiceListOfProductsBean.getList().get(position).getProductDetailUrl();
				String title = mInvoiceListOfProductsBean.getList().get(position).getBrandName() + " "
						+ mInvoiceListOfProductsBean.getList().get(position).getPattern();
				String picUrl = mInvoiceListOfProductsBean.getList().get(position).getPicPath1Url();
				Bundle bundle = new Bundle();
				bundle.putString(ArgName.ProductDetail.URL, url);
				bundle.putString(ArgName.ProductDetail.TITLE, title);
				bundle.putString(ArgName.ProductDetail.PIC_URL, picUrl);
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_DETAIL, bundle);
			}
		});
		SystemUtil.setListViewHeightBasedOnChildren(mViewHolder.pruductLV);
	}

	class ViewHolder {
		TextView vendorNameTV;
		ListView pruductLV;
	}

}
