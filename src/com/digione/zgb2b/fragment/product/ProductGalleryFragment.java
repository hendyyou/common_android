package com.digione.zgb2b.fragment.product;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.PicInfoBean;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.widget.MyGallery;
import com.digione.zgb2b.widget.TitleView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 产品详情页面顶端图片列表
 * 
 * @author zhangqr
 * @version V1.0
 * @create date: 2013-3-27
 */
public class ProductGalleryFragment extends CommonBaseFragment implements AdapterView.OnItemSelectedListener {
	private MyGallery myGallery;
	private Gallery smallGallery;
	private List<PicInfoBean> picInfoList;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	/**
	 * 真正创建界面的方法
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.image_switcher, container, false);
		TitleView titleTextView = (TitleView) v.findViewById(R.id.title);

		titleTextView.goneLeftButton();
		titleTextView.getmTitle().setGravity(Gravity.LEFT);
		titleTextView.setTitle("  " + getArguments().getString(ArgName.ProductGallery.TITLE));
		options = DisplayImageOptionsUtil.getDisplayImageOptions();

		imageLoader = ImageLoader.getInstance();

		picInfoList = (List<PicInfoBean>) getArguments().getSerializable(ArgName.ProductGallery.PIC_LIST);

		ZGApplication.getInstance().showDialog(activity);
		myGallery = (MyGallery) v.findViewById(R.id.mygallery);
		myGallery.setAdapter(new ImageAdapter(activity, false));
		myGallery.setSpacing(1);
		myGallery.setOnItemSelectedListener(this);
		myGallery.setSelection(getArguments().getInt(ArgName.ProductGallery.POSITION));

		smallGallery = (Gallery) v.findViewById(R.id.gallery);
		smallGallery.setAdapter(new ImageAdapter(activity, true));
		smallGallery.setOnItemSelectedListener(this);

		smallGallery.setSelection(getArguments().getInt(ArgName.ProductGallery.POSITION));

		ZGApplication.getInstance().dismissDialog();
		return v;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		myGallery.setSelection(arg2);
		smallGallery.setSelection(arg2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private boolean isSmall;

		public ImageAdapter(Context c, boolean isSmall) {
			mContext = c;
			this.isSmall = isSmall;
		}

		public int getCount() {
			return picInfoList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {
				convertView = new View(mContext);
				holder = new ViewHolder();
				holder.imageView = new ImageView(mContext);

				if (isSmall) {
					holder.imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					holder.progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);

				} else {
					holder.imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,
							LayoutParams.FILL_PARENT));
					holder.progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge);
				}

				RelativeLayout.LayoutParams plp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				plp.addRule(RelativeLayout.CENTER_IN_PARENT);
				holder.progressBar.setLayoutParams(plp);
				holder.progressBar.setVisibility(View.VISIBLE);
				holder.imageView.setTag(holder.progressBar);
				holder.borderImg = new RelativeLayout(mContext);
				holder.borderImg.addView(holder.imageView);
				holder.borderImg.addView(holder.progressBar);
				convertView.setTag(holder);
			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			if (isSmall) {
				holder.imageView.setAdjustViewBounds(true);

				imageLoader.displayImage(
						picInfoList.get(position) == null ? "" : picInfoList.get(position).getPicSmallUrl(),
						holder.imageView, options, DisplayImageOptionsUtil.getImageLoadingListener());
			} else {
				holder.imageView.setAdjustViewBounds(false);
				holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageLoader.displayImage(picInfoList.get(position) == null ? "" : picInfoList.get(position).getPicUrl(),
						holder.imageView, options, DisplayImageOptionsUtil.getImageLoadingListener());
			}

			return holder.borderImg;
		}

	}

	private static class ViewHolder {

		RelativeLayout borderImg;
		ImageView imageView;
		ProgressBar progressBar;
	}

}