package com.digione.zgb2b.adapter.product;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.product.PicInfoBean;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 商品详情顶端图片适配器
 * 
 * @author zhangqr
 * 
 */
public class ProductGalleryAdapter extends BaseAdapter {

	private List<PicInfoBean> smallImgList;
	private Context mContext;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ProductGalleryAdapter(Context context, List<PicInfoBean> list) {
		this.mContext = context;
		this.smallImgList = list;
		this.options = DisplayImageOptionsUtil.getDisplayImageOptions();
		this.imageLoader = ImageLoader.getInstance();
		if (smallImgList == null) {
			smallImgList = new ArrayList<PicInfoBean>();
		}
	}

	@Override
	public int getCount() {
		return smallImgList.size();
	}

	@Override
	public Object getItem(int position) {
		return smallImgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			convertView = new View(mContext);
			holder = new ViewHolder();
			holder.imageView = new ImageView(mContext);
			LayoutParams glp = new LayoutParams(SystemUtil.dip2px(mContext, 127), SystemUtil.dip2px(mContext, 127));
			holder.imageView.setLayoutParams(glp);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			holder.imageView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			holder.progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);

			RelativeLayout.LayoutParams plp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			plp.addRule(RelativeLayout.CENTER_IN_PARENT);
			holder.progressBar.setLayoutParams(plp);
			holder.progressBar.setVisibility(View.VISIBLE);
			holder.imageView.setTag(holder.progressBar);
			holder.borderImg = new RelativeLayout(mContext);
			holder.borderImg.setPadding(3, 15, 3, 15);
			holder.borderImg.addView(holder.imageView);
			holder.borderImg.addView(holder.progressBar);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		PicInfoBean pBean = smallImgList.get(position);
		imageLoader.displayImage(pBean == null ? "" : pBean.getPicSmallUrl(), holder.imageView, options,
				DisplayImageOptionsUtil.getImageLoadingListener());

		return holder.borderImg;

	}

	private static class ViewHolder {
		// ImageView icontjView;
		RelativeLayout borderImg;
		ImageView imageView;
		ProgressBar progressBar;
	}

}
