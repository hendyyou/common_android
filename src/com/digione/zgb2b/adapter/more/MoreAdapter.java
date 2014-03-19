package com.digione.zgb2b.adapter.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.digione.zgb2b.R;

/**
 * 
 * @ClassName: MoreAdapter
 * @Description: 更多MoreAdapter适配器
 * @author 尤振华
 * @date 2012-10-22 下午3:46:10
 * @version 1.0
 */
public class MoreAdapter extends BaseAdapter {

	// 图片
	private int[] icons;

	// 标题
	private int[] titles;

	private Context mContext;

	private LayoutInflater inflater;

	public MoreAdapter(Context context, int[] titles, int[] icons) {
		this.icons = icons;
		this.titles = titles;
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder = null;

		if (convertView == null) {
			mViewHolder = new ViewHolder();

			convertView = inflater.inflate(R.layout.more_main_item, null);
			mViewHolder.iconIV = (ImageView) convertView.findViewById(R.id.more_main_icon_iv);
			mViewHolder.titleTV = (TextView) convertView.findViewById(R.id.more_main_title_tv);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.iconIV.setImageResource(icons[position]);
		mViewHolder.titleTV.setText(titles[position]);

		if (position == titles.length - 1) {
			convertView.findViewById(R.id.downbrownline).setVisibility(View.GONE);
		}
		if (position == 0) {
			convertView.setBackgroundResource(R.drawable.productlist_headmenu_selector);
			convertView.findViewById(R.id.downbrownline).setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView iconIV;
		TextView titleTV;
	}
}
