package com.digione.zgb2b.adapter.more;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.search.SearchBean;

public class SearchAdapter extends BaseAdapter {

	private ArrayList<String> mList;
	private LayoutInflater inflater;

	public SearchAdapter(Context context, ArrayList<String> beans) {
		this.mList = beans;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SViewHolder holder;
		if (convertView == null) {
			holder = new SViewHolder();
			convertView = inflater.inflate(R.layout.search_hot, null);
			holder.btn = (Button) convertView.findViewById(R.id.search_hot_words);
			convertView.setTag(holder);
		} else {
			holder = (SViewHolder) convertView.getTag();
		}
		holder.btn.setText(mList.get(position));
		return convertView;
	}

	class SViewHolder {
		Button btn;
	}
}
