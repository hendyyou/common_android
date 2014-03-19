package com.digione.zgb2b.fragment.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.widget.TitleView;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class AboutFragment extends CommonBaseFragment {

	private View v;
	private TextView hotLineNumberTV;
	private TextView version;

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.more_about, container, false);
		if (v != null) {
			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.about);
			// 隐藏按钮
			mTitle.hiddenButton();
			final String title = getString(R.string.customer_hotline_number_title);
			final String number = getString(R.string.customer_hotline_number);
			hotLineNumberTV = (TextView) v.findViewById(R.id.customer_hotline_number_tv);
			hotLineNumberTV.setText(title + number);
			hotLineNumberTV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent myIntentDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
					activity.startActivity(myIntentDial);
				}
			});
			// 设置版本信息
			version = (TextView) v.findViewById(R.id.more_about_version_tv);
			version.setText(version.getText().toString() + SystemUtil.getVersion(activity));
		}
		return v;
	}

}