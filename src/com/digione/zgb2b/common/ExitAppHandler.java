package com.digione.zgb2b.common;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.digione.zgb2b.ZGApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * User: youzh Date: 13-4-10 Time: 下午3:34 退出应用程序时退出登录
 */
public class ExitAppHandler extends JsonHttpResponseHandler {

	private Context mContext;

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public ExitAppHandler(Context context) {
		mContext = context;
	}

	/**
	 * 退出登录
	 */
	public void exit() {
		if (ZGApplication.getInstance().isLogin()) {
			HttpClient client = HttpClient.getInstall(mContext);
			String url = Constants.Url.LOGOUT_002;
			client.postAsync(url, null, this);
		}
	}

	@Override
	public void onSuccess(JSONObject jsonObject) {
		super.onSuccess(jsonObject);
		try {
			int msgCode = jsonObject.getInt("msgCode");
			if (Constants.IntfMsgCode.SUCCESS == msgCode) {
				ZGApplication.getInstance().setLogin(false);
				ZGApplication.getInstance().setResetShopCart(false);
				HttpClient.clearCookie();
				ZGApplication.getInstance().setUser(null);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(Constants.TAG, "退出解析json出错了", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(Throwable throwable, JSONObject jsonObject) {
		super.onFailure(throwable, jsonObject);

	}
}
