package com.digione.zgb2b.adapter.home;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.home.RHomeBean;
import com.digione.zgb2b.bean.home.THomeBean;
import com.digione.zgb2b.fragment.home.GridViewFragment.GridViewMethod;
import com.digione.zgb2b.utils.DisplayImageOptionsUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewAdapter<T> extends BaseAdapter {

	private Context context;
	private ArrayList<T> mList;
	private LayoutInflater inflater;
	private GridViewMethod gridViewMethod;
	private DisplayImageOptions options;
	private int layoutId;

	public GridViewAdapter(Context context, GridViewMethod method, int layoutId) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		gridViewMethod = method;
		options = DisplayImageOptionsUtil.getDisplayImageOptions();
		this.layoutId = layoutId;
	}

	public void setList(ArrayList<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public ArrayList<T> getList() {
		return mList;
	}

	@Override
	public int getCount() {
		if (mList != null)
			return mList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);
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
			convertView = inflater.inflate(layoutId, null);
			holder.img = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
			holder.img.setTag(holder.progressBar);
			holder.tView = (TextView) convertView.findViewById(R.id.imgName);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.grid_linear_layout);
			holder.imgLayout = (LinearLayout) convertView.findViewById(R.id.grid_linear_img_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (gridViewMethod == GridViewMethod.zhigong) {
			holder.layout.setBackgroundResource(R.drawable.home_bottom);
			holder.tView.setTextColor(0xff763015);
			holder.imgLayout.setBackgroundResource(R.color.white);
			holder.tView.setText(((THomeBean) mList.get(position)).getBrandName().toString());
			ImageLoader.getInstance().displayImage(((THomeBean) mList.get(position)).getPicPath1Url(), holder.img, options,
					DisplayImageOptionsUtil.getImageLoadingListener());
		} else if (gridViewMethod == GridViewMethod.renmen) {
			holder.tView.setTextColor(Color.WHITE);
			holder.tView.setText(((RHomeBean) mList.get(position)).getTitleName().toString());
			ImageLoader.getInstance().displayImage(((RHomeBean) mList.get(position)).getImageUrl(), holder.img, options,
					DisplayImageOptionsUtil.getImageLoadingListener());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView tView;
		LinearLayout layout;
		LinearLayout imgLayout;
		ProgressBar progressBar;
	}
}
