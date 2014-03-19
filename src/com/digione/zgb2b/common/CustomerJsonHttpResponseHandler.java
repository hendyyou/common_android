package com.digione.zgb2b.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.JsonCommonOutBean;
import com.digione.zgb2b.bean.RequestErrorBean;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 自定义JsonHttp返回的Handler
 * 
 * @author Simpson
 * 
 * @param <T>
 *            接口的entity返回的Bean类型
 */
public abstract class CustomerJsonHttpResponseHandler<T> extends JsonHttpResponseHandler implements
		JsonHttpResponseInterface<T> {

	private Activity context;

	private Bundle bundle;

	private RequestErrorBean errorBean;

	private boolean isTimeOutCheck;

	private int currentFragmentId;

	private Type entityType;

	private boolean isExecuteOnSuccess;

	private String entityJsonString;

	private boolean isCloseSingle;

	private boolean isFinished;

	/**
	 * 通用的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param errorBean
	 *            错误信息的Bean
	 * @param isTimeOutCheck
	 *            是否检查超时的标识。true：检查；false：不检查
	 * @param currentFragmentId
	 *            当前Fragment的ID
	 * @param bundle
	 *            传递的参数
	 * @param isCloseSingle
	 *            是否关闭所有等待窗口的标识。true：关闭单个；false：关闭所有
	 */
	public CustomerJsonHttpResponseHandler(Activity context, RequestErrorBean errorBean, boolean isTimeOutCheck,
			int currentFragmentId, Bundle bundle, boolean isCloseSingle) {
		this.context = context;
		this.errorBean = errorBean;
		this.isTimeOutCheck = isTimeOutCheck;
		this.bundle = bundle;
		this.currentFragmentId = currentFragmentId;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = isCloseSingle;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 默认关闭所有等待窗口的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param errorBean
	 *            错误信息的Bean
	 * @param isTimeOutCheck
	 *            是否检查超时的标识。true：检查；false：不检查
	 * @param currentFragmentId
	 *            当前Fragment的ID
	 * @param bundle
	 *            传递的参数
	 */
	public CustomerJsonHttpResponseHandler(Activity context, RequestErrorBean errorBean, boolean isTimeOutCheck,
			int currentFragmentId, Bundle bundle) {
		this.context = context;
		this.errorBean = errorBean;
		this.isTimeOutCheck = isTimeOutCheck;
		this.bundle = bundle;
		this.currentFragmentId = currentFragmentId;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = false;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 默认需要检查超时的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param currentFragmentId
	 *            当前Fragment的ID
	 * @param bundle
	 *            传递的参数
	 * @param isCloseSingle
	 *            是否关闭所有等待窗口的标识。true：关闭单个；false：关闭所有
	 */
	public CustomerJsonHttpResponseHandler(Activity context, int currentFragmentId, Bundle bundle, boolean isCloseSingle) {
		this.context = context;
		this.errorBean = null;
		this.isTimeOutCheck = true;
		this.bundle = bundle;
		this.currentFragmentId = currentFragmentId;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = isCloseSingle;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 默认关闭所有等待窗口默认需要检查超时的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param currentFragmentId
	 *            当前Fragment的ID
	 * @param bundle
	 *            传递的参数
	 */
	public CustomerJsonHttpResponseHandler(Activity context, int currentFragmentId, Bundle bundle) {
		this.context = context;
		this.errorBean = null;
		this.isTimeOutCheck = true;
		this.bundle = bundle;
		this.currentFragmentId = currentFragmentId;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = false;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 不检查超时需要错误信息bean的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param errorBean
	 *            错误信息的Bean
	 * @param isCloseSingle
	 *            是否关闭所有等待窗口的标识。true：关闭单个；false：关闭所有
	 */
	public CustomerJsonHttpResponseHandler(Activity context, RequestErrorBean errorBean, boolean isCloseSingle) {
		this.context = context;
		this.errorBean = errorBean;
		this.isTimeOutCheck = false;
		this.bundle = null;
		this.currentFragmentId = -1;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = isCloseSingle;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 默认关闭所有等待窗口不检查超时需要错误信息bean的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param errorBean
	 *            错误信息的Bean
	 */
	public CustomerJsonHttpResponseHandler(Activity context, RequestErrorBean errorBean) {
		this.context = context;
		this.errorBean = errorBean;
		this.isTimeOutCheck = false;
		this.bundle = null;
		this.currentFragmentId = -1;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = false;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 不检查超时的构造器
	 * 
	 * @param context
	 *            上下文
	 * @param isCloseSingle
	 *            是否关闭所有等待窗口的标识。true：关闭单个；false：关闭所有
	 */
	public CustomerJsonHttpResponseHandler(Activity context, boolean isCloseSingle) {
		this.context = context;
		this.errorBean = null;
		this.isTimeOutCheck = false;
		this.bundle = null;
		this.currentFragmentId = -1;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = isCloseSingle;
		this.isFinished = false;
		setEntityClass();
	}

	/**
	 * 默认关闭所有等待窗口不检查超时的构造器
	 * 
	 * @param context
	 *            上下文
	 */
	public CustomerJsonHttpResponseHandler(Activity context) {
		this.context = context;
		this.errorBean = null;
		this.isTimeOutCheck = false;
		this.bundle = null;
		this.currentFragmentId = -1;
		this.isExecuteOnSuccess = false;
		this.isCloseSingle = false;
		this.isFinished = false;
		setEntityClass();
	}

	private void setEntityClass() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		this.entityType = params[0];
	}

	@Override
	public void onStart() {
		isExecuteOnSuccess = false;
		this.isFinished = false;
		processHttpStart(errorBean);
		super.onStart();
	}

	@Override
	public void onFinish() {
		super.onFinish();
		processHttpFinish(errorBean);
		this.isFinished = true;
	}

	@Override
	public void onSuccess(JSONObject response) {
		JsonCommonOutBean<T> outBean = new JsonCommonOutBean<T>();
		try {
			super.onSuccess(response);
			isExecuteOnSuccess = true;
			processBeforeHttpSuccess();

			Gson gson = new Gson();
			Type comonType = new TypeToken<JsonCommonOutBean<T>>() {
			}.getType();
			outBean = gson.fromJson(response.toString(), comonType);
			if (response.get("entity") != JSONObject.NULL) {
				entityJsonString = response.getString("entity");
				@SuppressWarnings("unchecked")
				T outEntityBean = (T) gson.fromJson(entityJsonString, entityType);
				outBean.setEntity(outEntityBean);
			} else {
				outBean.setEntity(null);
			}
			processHttpSuccess(outBean);

		} catch (Exception e) {
			processCallSuccessException(e);
		}
		processAfterHttpSuccess(outBean.getEntity(), outBean.getMsgCode(), outBean.getFailureCode(), outBean.getMsg());
	}

	@Override
	public void onFailure(Throwable e, JSONObject response) {
		super.onFailure(e, response);
		processHttpFailure(e, errorBean);
	}

	@Override
	public void processHttpSuccess(JsonCommonOutBean<T> outBean) {
		if (Constants.IntfMsgCode.SUCCESS == outBean.getMsgCode()) {
			processCallSuccess(outBean.getEntity(), outBean.getMsg());
		}
		if (Constants.IntfMsgCode.TIMEOUT == outBean.getMsgCode() && isTimeOutCheck) {
			processCallTimeout(outBean.getEntity(), outBean.getFailureCode(), outBean.getMsg());
		}
		if (Constants.IntfMsgCode.FAILURE == outBean.getMsgCode()) {
			processCallFailure(outBean.getEntity(), outBean.getFailureCode(), outBean.getMsg());
		}
	}

	@Override
	public void processHttpFailure(Throwable e, RequestErrorBean outBean) {
		if (null != context) {
			ToastUtil.showToast(context, context.getString(R.string.msg_servicefail), ToastUtil.LENGTH_SHORT);
		}
	}

	@Override
	public void processHttpStart(RequestErrorBean outBean) {
		if (null != context) {
			ZGApplication.getInstance().showDialog(context);
		}
	}

	@Override
	public void processHttpFinish(RequestErrorBean outBean) {
		if (null != context) {
			if (isCloseSingle) {
				ZGApplication.getInstance().dismissSingleDialog();
			} else {
				ZGApplication.getInstance().dismissDialog();
			}
			if (!isExecuteOnSuccess && !context.isFinishing()) {
				ToastUtil.showToast(context, context.getString(R.string.network_cannot_access), ToastUtil.LENGTH_SHORT);
			}
		}
	}

	@Override
	public void processCallFailure(T outBean, String failureCode, String msg) {
		ToastUtil.showToast(context, msg, ToastUtil.LENGTH_SHORT);
	}

	@Override
	public void processCallTimeout(T outBean, String failureCode, String msg) {
		ChangeFragmentUtil.changeToUserLoginFragment(currentFragmentId, context, msg, bundle);
	}

	@Override
	public void processCallSuccessException(Exception e) {
		Log.e(Constants.TAG, "解析json出错了", e);
		ToastUtil.showToast(context, "系统错误，解析返回数据出错了，请与系统管理员联系！", ToastUtil.LENGTH_LONG);
		e.printStackTrace();
	}

	@Override
	public void processBeforeHttpSuccess() {

	}

	@Override
	public void processAfterHttpSuccess(T outBean, Integer msgCode, String failureCode, String msg) {

	}

	public Activity getContext() {
		return context;
	}

	public void setContext(Activity context) {
		this.context = context;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public RequestErrorBean getErrorBean() {
		return errorBean;
	}

	public void setErrorBean(RequestErrorBean errorBean) {
		this.errorBean = errorBean;
	}

	public boolean isTimeOutCheck() {
		return isTimeOutCheck;
	}

	public void setTimeOutCheck(boolean isTimeOutCheck) {
		this.isTimeOutCheck = isTimeOutCheck;
	}

	public int getCurrentFragmentId() {
		return currentFragmentId;
	}

	public void setCurrentFragmentId(int currentFragmentId) {
		this.currentFragmentId = currentFragmentId;
	}

	public Type getEntityType() {
		return entityType;
	}

	public boolean isExecuteOnSuccess() {
		return isExecuteOnSuccess;
	}

	public String getEntityJsonString() {
		return entityJsonString;
	}

	public boolean isCloseSingle() {
		return isCloseSingle;
	}

	public void setCloseSingle(boolean isCloseSingle) {
		this.isCloseSingle = isCloseSingle;
	}

	public boolean isFinished() {
		return isFinished;
	}

}
