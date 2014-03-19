package com.digione.zgb2b.adapter.workbench;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digione.zgb2b.R;

/**
 * 
 * @ClassName: MyBaiGongAdapter
 * @Description: 我的百供数据适配器
 * @author 尤振华
 * @date 2012-10-22 下午3:46:10
 * @version 1.0
 */
public class MyDirectSuppleyAdapter extends BaseAdapter {

	private String[] data;

	private Context context;

	private LayoutInflater inflater;

	public MyDirectSuppleyAdapter(String[] data, Context context) {
		this.data = data;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.my_direct_supply_item, null);
			holder.titleName = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titleName.setText(data[position]);

		return convertView;
	}

	class ViewHolder {
		TextView titleName;
	}
}
