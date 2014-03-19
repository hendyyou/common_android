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
import com.digione.zgb2b.widget.TitleView;

public class HotLineFragment extends CommonBaseFragment {

	private View v;
	private TextView hotLineNumberTV;

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.more_hotline, container, false);
		if (v != null) {
			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.customer_hotline);
			// 隐藏按钮
			mTitle.hiddenButton();
			final String number = getString(R.string.customer_hotline_number);
			hotLineNumberTV = (TextView) v.findViewById(R.id.more_hotline_number_tv);
			hotLineNumberTV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent myIntentDial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
					activity.startActivity(myIntentDial);
				}
			});
		}
		return v;
	}

}
