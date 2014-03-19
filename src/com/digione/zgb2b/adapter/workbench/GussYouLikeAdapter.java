package com.digione.zgb2b.adapter.workbench;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.digione.zgb2b.bean.product.ProductBean;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class GussYouLikeAdapter extends BaseAdapter {
	// 定义Context
	private Context mContext;
	private ArrayList<ProductBean> productList;
	private ImageLoader imageLoader;

	public GussYouLikeAdapter(Context c, ArrayList<ProductBean> productList) {
		mContext = c;
		this.productList = productList;
		imageLoader = ImageLoader.getInstance();
	}

	// 获取图片的个数
	public int getCount() {
		return this.productList.size();
	}

	// 获取图片在库中的位置
	public Object getItem(int position) {
		return position;
	}

	// 获取图片ID
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			// 给ImageView设置资源
			imageView = new ImageView(mContext);
			int width = SystemUtil.getDeviceWidth(mContext);
			int height = SystemUtil.getDeviceHeight(mContext);
			// 设置布局 图片120×120显示
			imageView.setLayoutParams(new GridView.LayoutParams(width / 4, 80));
			// 设置显示比例类型
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		} else {
			imageView = (ImageView) convertView;
		}
		String url = this.productList.get(position).getPicPath1Url();
		imageLoader.displayImage(url, imageView, DisplayImageOptionsUtil.getDisplayImageOptions());
		return imageView;
	}

}
