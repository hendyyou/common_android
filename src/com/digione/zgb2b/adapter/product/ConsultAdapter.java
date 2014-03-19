package com.digione.zgb2b.adapter.product;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.product.ConsultItemBean;

/**
 * 产品列表adapter
 * 
 * @author zhangqr
 * 
 */
public class ConsultAdapter extends BaseAdapter {

	private List<ConsultItemBean> data;
	private Context context;

	@Override
	public int getCount() {

		return data.size();

	}

	public ConsultAdapter(List<ConsultItemBean> data, Context context) {
		this.data = data;
		this.context = context;

	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addItem(final ConsultItemBean item) {
		data.add(item);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.consult_item, null);

			holder.consulter_tv = (TextView) convertView.findViewById(R.id.product_consult_consulter);
			holder.consulttime_tv = (TextView) convertView.findViewById(R.id.product_consult_time);
			holder.question_tv = (TextView) convertView.findViewById(R.id.product_consult_question);
			holder.answer_tv = (TextView) convertView.findViewById(R.id.product_consult_answer);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		// 获取ViewHolder中所填入的数据

		ConsultItemBean itembean = data.get(position);
		holder.consulter_tv.setText(itembean.getCustNo());
		holder.consulttime_tv.setText(itembean.getConsultingTime());
		holder.consulter_tv.setTextSize(13);
		holder.consulttime_tv.setTextSize(11);
		holder.question_tv.setTextSize(18);
		holder.question_tv.setText(itembean.getQuestion());

		holder.answer_tv.setTextColor(context.getResources().getColor(R.color.black));
		holder.answer_tv.setTextSize(18);
		holder.answer_tv.setText(itembean.getReply());

		return convertView;

	}

	private static class ViewHolder {

		TextView consulter_tv; // 客服名称
		TextView consulttime_tv; // 询问顾客
		TextView question_tv; // 咨询的问题
		TextView answer_tv; // 答案
	}

}
