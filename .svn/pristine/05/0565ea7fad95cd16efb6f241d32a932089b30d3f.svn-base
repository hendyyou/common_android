package com.digione.zgb2b.adapter.home;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.home.GHomeBean;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeTopAdapter extends BaseAdapter {

	private Context mContext;
	private List<GHomeBean> mList;
	private LayoutInflater inflater;
	private int count;
	DisplayImageOptions options;

	public HomeTopAdapter(Context context, List<GHomeBean> list) {
		this.mContext = context;
		this.mList = list;
		inflater = LayoutInflater.from(context);
		count = list.size();
		options = DisplayImageOptionsUtil.getDisplayImageOptions();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		if (count != 0)
			return mList.get(position % count);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_gallery_img, null);
			holder.img = (ImageView) convertView.findViewById(R.id.home_gallery_img);
			holder.img.setAdjustViewBounds(true);
			holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
			holder.img.setLayoutParams(new RelativeLayout.LayoutParams(SystemUtil.getDeviceWidth(mContext),
					RelativeLayout.LayoutParams.FILL_PARENT));
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
			holder.img.setTag(holder.progressBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (count != 0) {
			ImageLoader.getInstance().displayImage(mList.get(position % count).getImageUrl(), holder.img, options,
					DisplayImageOptionsUtil.getImageLoadingListener());
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		ProgressBar progressBar;
	}
}
