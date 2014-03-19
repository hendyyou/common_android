package com.digione.zgb2b.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.bean.home.DiginewsBean;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;

public class WebFragment extends CommonBaseFragment {

	private WebView webView;
	private TextView title;
	private TextView time;
	private String url;
	private DiginewsBean bean;
	private String baseUrl;
	private CustomerJsonHttpResponseHandler<DiginewsBean> jsonHttpResponseHandler;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.DIGINEWS_DETAIL_SUCCESS:
				initData();
				break;

			default:
				break;
			}
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_web, container, false);
		webView = (WebView) view.findViewById(R.id.home_web);
		title = (TextView) view.findViewById(R.id.home_web_title);
		time = (TextView) view.findViewById(R.id.home_web_time);
		webView.setBackgroundColor(getResources().getColor(R.color.backgroud_color));
		WebSettings web = webView.getSettings();
		web.setJavaScriptEnabled(true);
		web.setSupportZoom(false);
		web.setTextSize(TextSize.NORMAL);
		Bundle bundle = getArguments();
		if (bundle != null && bundle.get(ArgName.Web.URL) != null) {
			url = bundle.getString(ArgName.Web.URL);
		}
		return view;
	}

	private void initData() {
		if (bean == null)
			return;
		webView.loadDataWithBaseURL(baseUrl, bean.getContent(), "text/html", "utf-8", null);
		title.setText(bean.getTitle());
		time.setText(bean.getCreateTime());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<DiginewsBean>(activity,ChangeFragmentUtil.HOME_WEB,getArguments()) {

			@Override
			public void processCallSuccess(DiginewsBean outBean, String msg) {
				bean = outBean;
				mHandler.sendEmptyMessage(MsgCode.DIGINEWS_DETAIL_SUCCESS);
			}
		};
		HttpClient httpClient = HttpClient.getInstall(activity);
		baseUrl = httpClient.getBaseUrl();
		httpClient.postAsync(url, null, jsonHttpResponseHandler);

	}

}
