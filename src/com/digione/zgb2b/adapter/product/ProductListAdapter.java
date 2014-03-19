package com.digione.zgb2b.adapter.product;

import java.util.List;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.product.ProductBean;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 产品列表adapter
 * 
 * @author zhangqr
 * 
 */
public class ProductListAdapter extends BaseAdapter {

	private List<ProductBean> data;
	private Context context;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	@Override
	public int getCount() {

		return data.size();

	}

	public ProductListAdapter(List<ProductBean> data, Context context) {
		this.data = data;
		this.context = context;
		this.options = DisplayImageOptionsUtil.getDisplayImageOptions();
		this.imageLoader = ImageLoader.getInstance();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addItem(final ProductBean item) {
		data.add(item);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.product_listitem, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.product_image);

			holder.imageView.setScaleType(ScaleType.CENTER_CROP);
			// holder.imageView.setAdjustViewBounds(true);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.sell_spec_tv = (TextView) convertView.findViewById(R.id.sale_spec);
			holder.price1_tv = (TextView) convertView.findViewById(R.id.product_price1);
			holder.price2_tv = (TextView) convertView.findViewById(R.id.product_price2);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
			holder.imageView.setTag(holder.progressBar);
			holder.brownLineImageView = (ImageView) convertView.findViewById(R.id.downbrownline);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		// 获取ViewHolder中所填入的数据

		ProductBean itembean = (ProductBean) data.get(position);

		holder.imageView.setImageDrawable(null);

		String url = itembean.getPicPath1Url();

		imageLoader.displayImage(url, holder.imageView, options, DisplayImageOptionsUtil.getImageLoadingListener());

		// 解决中文不能加粗的办法
		TextPaint tp = holder.name.getPaint();
		tp.setFakeBoldText(true);

		holder.name.setText(itembean.getBrandName() + " " + itembean.getPattern() + " " + itembean.getColorSpec()+(itembean.getIsGovEnterprise().equals(1) ? context.getString(R.string.govEnterprise) : ""));

		holder.sell_spec_tv.setText(StringUtils.relpaceHtmlTag(itembean.getSaleSpec()));

		// 有sys，则是促销价和直供价

		if (itembean.getCustPriceTitle() != null && itembean.getIsSpecial() == 1) {
			holder.price1_tv.setTextColor(context.getResources().getColor(R.color.orange));
			holder.price1_tv.setText(context.getString(R.string.cust_price)
					+ StringUtils.formatMoney(itembean.getCustPrice()));
			holder.price2_tv.setTextColor(context.getResources().getColor(R.color.glass_green));
			holder.price2_tv.setText(context.getString(R.string.syscust_price)
					+ StringUtils.formatMoney(itembean.getSysCustPrice()));
		} else {// 没有sys，则只剩下直供价和零售价
			holder.price1_tv.setTextColor(context.getResources().getColor(R.color.glass_green));
			holder.price1_tv.setText(context.getString(R.string.syscust_price)
					+ (itembean.getCustPrice() == null ? context.getString(R.string.after_login) : StringUtils
							.formatMoney(itembean.getCustPrice())));
			holder.price2_tv.setTextColor(context.getResources().getColor(R.color.detail_spec_color));
			holder.price2_tv.setText(context.getString(R.string.advice_price)
					+ StringUtils.formatMoney(itembean.getAdvicePrice()));
		}

		if (!data.isEmpty() && itembean.getProductId() == data.get(0).getProductId()) {
			convertView.setBackgroundResource(R.drawable.productlist_headmenu_selector);
			holder.brownLineImageView.setVisibility(View.VISIBLE);
		} else {
			convertView.setBackgroundResource(R.drawable.productlist_menu_selector);
		}

		if ((position == data.size() - 1) && data.size() > 1) {
			holder.brownLineImageView.setVisibility(View.GONE);
		}

		return convertView;

	}

	private static class ViewHolder {
		// ImageView icontjView;
		ImageView brownLineImageView;
		ImageView imageView;
		ProgressBar progressBar;
		TextView name; // 品牌+颜色
		TextView sell_spec_tv; // 卖点
		TextView price1_tv;
		TextView price2_tv;
	}

}
