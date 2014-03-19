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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.workbench.SimpleProductBean;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @ClassName: OrderAdapter
 * @Description: 订单适配器
 * @author 尤振华
 * @date 2012-10-22 下午8:17:41
 * @version 1.0
 */
public class OrdersSimpleProductAdapter extends BaseAdapter {

	// 存放订单数据
	private List<SimpleProductBean> data;

	private ImageLoader imageLoader;

	private DisplayImageOptions options;

	private LayoutInflater inflater;

	/**
	 * Title: getCount Description:总共几条数据
	 * 
	 * @return 总数据个数
	 * @see android.widget.Adapter#getCount()
	 */

	public OrdersSimpleProductAdapter(Context context, List<SimpleProductBean> data) {
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		options = DisplayImageOptionsUtil.getDisplayImageOptions();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * Title: getItem Description: 商品列表适配器
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
				convertView = inflater.inflate(R.layout.orders_item_simple_product_item, null);
				mViewHolder.productIV = (ImageView) convertView.findViewById(R.id.workbench_order_product_iv);
				mViewHolder.productDescTV = (TextView) convertView.findViewById(R.id.workbench_order_product_desc_tv);
				mViewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
				mViewHolder.productIV.setTag(mViewHolder.progressBar);
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
		final SimpleProductBean simpleProduct = data.get(position);
        String isGovEnterprise  = "";
        if (simpleProduct.getIsGovEnterprise() == 1){
            isGovEnterprise =  inflater.getContext().getString(R.string.govEnterprise);
        }

		mViewHolder.productDescTV.setText(simpleProduct.getBrandName() + "\t" + simpleProduct.getPattern() + "\t"
				+ simpleProduct.getColorSpec()+isGovEnterprise);
		TextPaint tp = mViewHolder.productDescTV.getPaint();
		tp.setFakeBoldText(true);
		mViewHolder.productIV.setImageDrawable(null);
		String url = simpleProduct.getPicPath1Url();
		imageLoader.displayImage(url, mViewHolder.productIV, options, DisplayImageOptionsUtil.getImageLoadingListener());
	}

	class ViewHolder {
		ImageView productIV;
		TextView productDescTV;
		ProgressBar progressBar;
	}

}
