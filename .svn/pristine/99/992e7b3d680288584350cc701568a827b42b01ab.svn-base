package com.digione.zgb2b.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.RequestErrorBean;
import com.digione.zgb2b.bean.shopcart.OperCartOutBean;
import com.digione.zgb2b.bean.workbench.UserBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class ShopCartUtils {

	private PersistentService pService;

	/**
	 * 购物车使用的当前fragment的handler
	 */
	private Handler shopCartHandler;

	/**
	 * 添加购物车产品
	 */
	private static final int SHOP_CART_ADD = 1;
	/**
	 * 编辑购物车产品
	 */
	private static final int SHOP_CART_EDIT = 2;
	/**
	 * 删除购物车产品
	 */
	private static final int SHOP_CART_REMOVE = 3;

	private ShopCartUtils() {
		pService = new PersistentServiceImpl();
	}

	public static ShopCartUtils getInstance(Handler shopCartHandler) {
		ShopCartUtils shopCartUtils = new ShopCartUtils();
		shopCartUtils.shopCartHandler = shopCartHandler;
		return shopCartUtils;
	}

	private void getDataFormNet(final int operType, Activity activity, final String url, final RequestParams params,
			final String productId, final Integer vmiProvideId, final Integer quantity) {

		CustomerJsonHttpResponseHandler<OperCartOutBean> cartJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<OperCartOutBean>(
				activity, ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, null) {

			@Override
			public void processCallSuccess(OperCartOutBean outBean, String msg) {
				ToastUtil.showToast(getContext(), msg, ToastUtil.LENGTH_LONG);
				switch (operType) {
				case SHOP_CART_ADD: {
					Map<String, Map<String, Integer>> curMap = readShopCartFile(getContext());
					Map<String, Integer> itemMap = curMap.get(productId);
					if (itemMap == null) {
						itemMap = new HashMap<String, Integer>();
						itemMap.put("vmiProvideId", vmiProvideId);
						itemMap.put("quantity", 0);
					}
					Integer num = quantity + itemMap.get("quantity");
					itemMap.put("quantity", num);
					curMap.put(productId, itemMap);
					saveShopCartFile(getContext(), curMap);
					shopCartHandler.sendEmptyMessage(MsgCode.ADD_SHOP_CART_SUCCESS);
				}
					break;

				case SHOP_CART_EDIT: {
					Map<String, Map<String, Integer>> curMap = readShopCartFile(getContext());
					Map<String, Integer> itemMap = curMap.get(productId);
					itemMap.put("quantity", quantity);
					saveShopCartFile(getContext(), curMap);
					shopCartHandler.sendEmptyMessage(MsgCode.EDIT_SHOP_CART_SUCCESS);
				}
					break;
				case SHOP_CART_REMOVE: {
					Map<String, Map<String, Integer>> curMap = readShopCartFile(getContext());
					curMap.remove(productId);
					saveShopCartFile(getContext(), curMap);
					shopCartHandler.sendEmptyMessage(MsgCode.REMOVE_SHOP_CART_SUCCESS);
				}
					break;
				default:
					break;
				}
			}

			@Override
			public void processCallFailure(OperCartOutBean outBean, String failureCode, String msg) {
				super.processCallFailure(outBean, failureCode, msg);
				switch (operType) {
				case SHOP_CART_ADD: {
					shopCartHandler.sendEmptyMessage(MsgCode.ADD_SHOP_CART_FAILURE);
				}
					break;

				case SHOP_CART_EDIT: {
					shopCartHandler.sendEmptyMessage(MsgCode.EDIT_SHOP_CART_FAILURE);
				}
					break;
				case SHOP_CART_REMOVE: {
					shopCartHandler.sendEmptyMessage(MsgCode.REMOVE_SHOP_CART_FAILURE);
				}
					break;
				default:
					break;
				}
			}

			@Override
			public void processHttpFinish(RequestErrorBean outBean) {
				super.processHttpFinish(outBean);
				if (!isExecuteOnSuccess()) {
					switch (operType) {
					case SHOP_CART_ADD: {
						shopCartHandler.sendEmptyMessage(MsgCode.ADD_SHOP_CART_FAILURE);
					}
						break;

					case SHOP_CART_EDIT: {
						shopCartHandler.sendEmptyMessage(MsgCode.EDIT_SHOP_CART_FAILURE);
					}
						break;
					case SHOP_CART_REMOVE: {
						shopCartHandler.sendEmptyMessage(MsgCode.REMOVE_SHOP_CART_FAILURE);
					}
						break;
					default:
						break;
					}
				}
			}
		};
		HttpClient httpClient = HttpClient.getInstall(activity);
		httpClient.postAsync(url, params, cartJsonHttpResponseHandler);
	}

	// 添加到购物车
	public void addShopCart(Activity activity, String productId, Integer vmiProvideId, Integer quantity) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("vmiProvideId", vmiProvideId.toString());
		map.put("quantity", quantity.toString());
		RequestParams params = new RequestParams(map);

		getDataFormNet(SHOP_CART_ADD, activity, Constants.Url.ADD_SHOP_CART_039, params, productId, vmiProvideId, quantity);
	}

	// 删除购物车
	public void removeShopCart(Activity activity, String productId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		RequestParams params = new RequestParams(map);

		getDataFormNet(SHOP_CART_REMOVE, activity, Constants.Url.REMOVE_SHOP_CART_041, params, productId, null, null);
	}

	// 修改购物车
	public void updateShopCart(Activity activity, String productId, Integer quantity) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("quantity", quantity.toString());
		RequestParams params = new RequestParams(map);

		getDataFormNet(SHOP_CART_EDIT, activity, Constants.Url.UPDATE_SHOP_CART_040, params, productId, null, quantity);
	}

	// 登录时需要重新获购物车取数据
	public void resetShopcartData(Activity activity, UserBean bean) {
		if (!ZGApplication.getInstance().isResetShopCart()) {
			try {
				List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
				Map<String, Map<String, Integer>> curMap = readShopCartFileByLogin(activity, bean);
				if (null != curMap) {
					for (String str : curMap.keySet()) {
						Map<String, String> itemMap = new HashMap<String, String>();
						itemMap.put("productId", str);
						Map<String, Integer> valueMap = curMap.get(str);
						for (String valueString : valueMap.keySet()) {
							itemMap.put(valueString, valueMap.get(valueString).toString());
						}
						paramList.add(itemMap);
					}
				}
				Gson gsonParam = new Gson();
				String jsonString = gsonParam.toJson(paramList);
				resetShopcartDataFromNet(activity, jsonString);
			} catch (Exception e) {
				ToastUtil.showToast(activity, activity.getString(R.string.msg_servicefail), 1000);
				Log.e(Constants.TAG, "ee==" + e.toString());
				e.printStackTrace();
				shopCartHandler.sendEmptyMessage(MsgCode.REFILL_SHOP_CART_FAILURE);
			}
		}
	}

	private void resetShopcartDataFromNet(Activity activity, String arg) {
		CustomerJsonHttpResponseHandler<OperCartOutBean> cartJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<OperCartOutBean>(
				activity, ChangeFragmentUtil.SHOPPINGCART_CLASSIFY, null) {

			@Override
			public void processCallSuccess(OperCartOutBean outBean, String msg) {
				ZGApplication.getInstance().setResetShopCart(true);
				shopCartHandler.sendEmptyMessage(MsgCode.REFILL_SHOP_CART_SUCCESS);
			}

			@Override
			public void processCallFailure(OperCartOutBean outBean, String failureCode, String msg) {
				super.processCallFailure(outBean, failureCode, msg);
				shopCartHandler.sendEmptyMessage(MsgCode.REFILL_SHOP_CART_FAILURE);
			}

			@Override
			public void processHttpFinish(RequestErrorBean outBean) {
				super.processHttpFinish(outBean);
				if (!isExecuteOnSuccess()) {
					shopCartHandler.sendEmptyMessage(MsgCode.REFILL_SHOP_CART_FAILURE);
				}
			}
		};

		HttpClient httpClient = HttpClient.getInstall(activity);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("editCartJsonString", arg);
		RequestParams params = new RequestParams(paramsMap);
		httpClient.postAsync(Constants.Url.REFILL_SHOP_CART_046, params, cartJsonHttpResponseHandler);
	}

	private Map<String, Map<String, Integer>> readShopCartFileByLogin(Activity activity, UserBean bean) {
		String arg = pService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO, bean.getUserId()
				.toString());
		return readShopCartDataFile(arg);
	}

	public Map<String, Map<String, Integer>> readShopCartFile(Activity activity) {
		String arg = "";
		if (ZGApplication.getInstance().isLogin()) {
			arg = pService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO, ZGApplication.getInstance()
					.getUser().getUserId().toString());
		}
		return readShopCartDataFile(arg);
	}

	private Map<String, Map<String, Integer>> readShopCartDataFile(String arg) {
		Map<String, Map<String, Integer>> cartMap = new HashMap<String, Map<String, Integer>>();
		if (arg == null || arg.equals("")) {
			return cartMap;
		}
		try {
			JSONObject jsonObject = new JSONObject(arg);
			for (Iterator keyIterator = jsonObject.keys(); keyIterator.hasNext();) {
				String keyString = (String) keyIterator.next();
				JSONObject valueJsonObject = new JSONObject((String) jsonObject.get(keyString));
				Map<String, Integer> productMap = new HashMap<String, Integer>();
				for (Iterator iterator = valueJsonObject.keys(); iterator.hasNext();) {
					String valueKeyString = (String) iterator.next();
					productMap.put(valueKeyString, (Integer) valueJsonObject.get(valueKeyString));
				}
				cartMap.put(keyString, productMap);
			}
			return cartMap;
		} catch (JSONException e) {
			e.printStackTrace();
			shopCartHandler.sendEmptyMessage(MsgCode.READ_SHOP_CART_FAILURE);
		}
		return cartMap;
	}

	private void saveShopCartFile(final Activity activity, Map<String, Map<String, Integer>> map) {
		if (ZGApplication.getInstance().isLogin()) {
			JSONObject object = new JSONObject(map);
			pService.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO, ZGApplication.getInstance().getUser()
					.getUserId().toString(), object.toString());
		}
		getShopCartNum(activity);
	}

	// 清空购物车
	public void clearShopCartData(final Activity activity) {
		if (ZGApplication.getInstance().isLogin()) {
			pService.clearSharePreference(activity, Constants.DataFile.COMMON_INFO, ZGApplication.getInstance().getUser()
					.getUserId().toString());
		}
		getShopCartNum(activity);
	}

	// 获取购物车数量
	public void getShopCartNum(Activity activity) {
		int num = 0;
		Map<String, Map<String, Integer>> curMap = readShopCartFile(activity);
		for (String arg : curMap.keySet()) {
			Map<String, Integer> valueMap = curMap.get(arg);
			Integer curNum = valueMap.get("quantity");
			num += curNum;
		}

		TextView tvNum = (TextView) activity.findViewById(R.id.tab_host_cart_num);
		if (num == 0 || !ZGApplication.getInstance().isLogin()) {
			tvNum.setVisibility(View.GONE);
		} else if (num > 0 && ZGApplication.getInstance().isLogin()) {
			TextPaint tPaint = tvNum.getPaint();
			tPaint.setFakeBoldText(true);
			tvNum.setVisibility(View.VISIBLE);
			tvNum.setText(String.valueOf(num));
		}
		tvNum.invalidate();
	}

}
