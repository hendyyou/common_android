package com.digione.zgb2b.fragment.more;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.JsonNoneOutBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.HttpUtil;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class FeedbackFragment extends CommonBaseFragment {

	private View v;
	private Spinner feedbackTypeSP;
	private Button select_btn;
	private EditText feedbackContentET;
	private CustomerJsonHttpResponseHandler<JsonNoneOutBean> jsonHttpResponseHandler;

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!ZGApplication.getInstance().isLogin()) {
			Bundle bundle = getArguments();
			if (bundle.getBoolean(ArgName.Feedback.IS_POP_BACK)) {
				bundle.putBoolean(ArgName.Feedback.IS_POP_BACK, false);
				ChangeFragmentUtil.removeFragment();
				return null;
			}
			bundle.putBoolean(ArgName.Feedback.IS_POP_BACK, true);
			ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.PRODUCT_LIST, activity, getResources()
					.getString(R.string.zg_login_hit), bundle);
			return null;
		}

		v = inflater.inflate(R.layout.more_feedback, container, false);
		if (v != null) {
			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.feedback);
			// 隐藏按钮
			mTitle.hiddenButton();
			/**
			 * 隐藏输入键盘
			 */
			v.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					SystemUtil.closeSoftInput(activity);
					return false;
				}
			});

			feedbackTypeSP = (Spinner) v.findViewById(R.id.more_feedback_type_sp);
			select_btn = (Button) v.findViewById(R.id.color_select_btn2);
			select_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					feedbackTypeSP.performClick();
				}

			});
			feedbackContentET = (EditText) v.findViewById(R.id.more_feedback_context_et);
			Button sumbitBtn = (Button) v.findViewById(R.id.more_feedback_submit_btn);

			sumbitBtn.setOnClickListener(new SubmitOnClinck());

			jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<JsonNoneOutBean>(activity,
					ChangeFragmentUtil.MORE_FEEDBACK, getArguments()) {

				@Override
				public void processCallSuccess(JsonNoneOutBean outBean, String msg) {
					ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
					ChangeFragmentUtil.removeFragment();
				}
			};
		}
		return v;
	}

	class SubmitOnClinck implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			// 关闭软键盘
			SystemUtil.closeSoftInput(activity);

			String feedbackType = feedbackTypeSP.getSelectedItem().toString();
			String feedbackContent = feedbackContentET.getText().toString();

			// 网络验证，如果有网络就进行网络请求
			if (HttpUtil.isHasNetwork(activity)) {

				if (feedbackType.equals("")) {
					ToastUtil.showToast(activity, getString(R.string.feedback_type_error), ToastUtil.LENGTH_SHORT);
					return;
				}

				if (feedbackContent.equals("")) {
					ToastUtil.showToast(activity, getString(R.string.feedback_context_error), ToastUtil.LENGTH_SHORT);
					return;
				}

				HttpClient client = HttpClient.getInstall(activity);
				String url = Constants.Url.ADD_FREEBACK_027;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("feedback.type", (feedbackTypeSP.getSelectedItemId() + 1L) + "");
				map.put("feedback.content", feedbackContent);
				RequestParams params = new RequestParams(map);
				client.postAsync(url, params, jsonHttpResponseHandler);

			} else {
				ToastUtil.showToast(activity, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);
				return;
			}
		}
	}
}