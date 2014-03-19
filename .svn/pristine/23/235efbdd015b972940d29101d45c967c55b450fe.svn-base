/**   
 *  百分之百版权所有
 *
 */
package com.digione.zgb2b.adapter.workbench;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.workbench.ProductsOfListBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @ClassName: OrderAdapter
 * @Description: 订单明细商品列表适配器
 * @author 尤振华
 * @date 2012-10-22 下午8:17:41
 * @version 1.0
 */
public class OrderDetailSimpleProductAdapter extends BaseAdapter {

	// 存放订单数据
	private List<ProductsOfListBean> data;
	private ImageLoader imageLoader;
	private LayoutInflater inflater;

	/**
	 * Title: getCount Description:总共几条数据
	 * 
	 * @return 总数据个数
	 * @see android.widget.Adapter#getCount()
	 */

	public OrderDetailSimpleProductAdapter(Context context, List<ProductsOfListBean> data) {
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = ImageLoader.getInstance();
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
	 * @param pView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View pView, ViewGroup parent) {
		ViewHolder mViewHolder;
		try {
			if (pView == null) {
				mViewHolder = new ViewHolder();
				pView = inflater.inflate(R.layout.order_detail_simple_product_item, null);
				mViewHolder.productImageIV = (ImageView) pView
						.findViewById(R.id.order_detail_simple_product_item_picture_lv);
				mViewHolder.descTV = (TextView) pView.findViewById(R.id.order_detail_simple_product_item_desc_tv);
				mViewHolder.quantityTV = (TextView) pView.findViewById(R.id.order_detail_simple_product_item_quantity_tv);
				mViewHolder.unitPriceTV = (TextView) pView.findViewById(R.id.order_detail_simple_product_item_unit_price_tv);
				mViewHolder.preferentialTV = (TextView) pView
						.findViewById(R.id.order_detail_simple_product_item_preferential_tv);
				mViewHolder.progressBar = (ProgressBar) pView.findViewById(R.id.progress);
				mViewHolder.productImageIV.setTag(mViewHolder.progressBar);
				pView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) pView.getTag();
			}

			setConvertViewData(position, mViewHolder);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			Log.e(Constants.TAG, "out of memory OutOfMemoryError", e);
		} catch (Exception e) {
			Log.e(Constants.TAG, "Exception Error", e);
		}

		return pView;
	}

	/**
	 * @Title: setConvertViewData
	 * @Description: 设置convertview的数据
	 * @param mViewHolder
	 * @param position
	 *            位置
	 * @return void 返回类型
	 */
	private void setConvertViewData(int position, ViewHolder mViewHolder) {
		final ProductsOfListBean mProductsOfListBean = data.get(position);
        String isGovEnterprise  = "";
        if (mProductsOfListBean.getIsGovEnterprise() == 1){
            isGovEnterprise =  inflater.getContext().getString(R.string.govEnterprise);
        }

		imageLoader.displayImage(mProductsOfListBean.getPicPath1Url(), mViewHolder.productImageIV,
				DisplayImageOptionsUtil.getDisplayImageOptions(), DisplayImageOptionsUtil.getImageLoadingListener());
		mViewHolder.descTV.setText(mProductsOfListBean.getBrandName() + "\t" + mProductsOfListBean.getPattern() + "\t" +
                                   mProductsOfListBean.getColorSpec() +isGovEnterprise);
		mViewHolder.quantityTV.setText(mViewHolder.quantityTV.getText().toString() + mProductsOfListBean.getQuantity());
		mViewHolder.unitPriceTV.setText(mViewHolder.unitPriceTV.getText().toString()
				+ StringUtils.formatMoney(mProductsOfListBean.getCurrentPrice()));
		mViewHolder.preferentialTV.setText(mViewHolder.preferentialTV.getText().toString()
				+ StringUtils.formatMoney(mProductsOfListBean.getActPrice()));
	}

	class ViewHolder {
		ImageView productImageIV;
		TextView descTV;
		TextView quantityTV;
		TextView unitPriceTV;
		TextView preferentialTV;
		ProgressBar progressBar;
	}

}
