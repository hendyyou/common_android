package com.digione.zgb2b.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.digione.zgb2b.bean.home.GHomeBean;
import com.digione.zgb2b.bean.home.RHomeBean;
import com.digione.zgb2b.bean.home.THomeBean;
import com.digione.zgb2b.bean.search.SearchBean;
import com.digione.zgb2b.common.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONData {
	// 根据JSON数据，返回首页的广告条网络数据
	public static List<GHomeBean> parseGHomeBeanData(String data) {
		List<GHomeBean> mList = new ArrayList<GHomeBean>();
		if (data != null && !"".equals(data)) {
			try {
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<GHomeBean>>() {
				}.getType();
				mList = gson.fromJson(data, listType);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(Constants.TAG, e.toString());
			}
		}
		return mList;
	}

	// 获取JSON数据，返回首页直供网推荐
	public static ArrayList<THomeBean> parseTHomeBeanData(String data) {
		ArrayList<THomeBean> mList = new ArrayList<THomeBean>();
		if (data != null && !"".equals(data)) {
			try {
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<THomeBean>>() {
				}.getType();
				mList = gson.fromJson(data, listType);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(Constants.TAG, e.toString());
			}
		}
		return mList;
	}

	// 获取热门推荐数据
	public static ArrayList<RHomeBean> parseRHomeBeanData(String data) {
		ArrayList<RHomeBean> mList = new ArrayList<RHomeBean>();
		if (data != null && !"".equals(data)) {
			try {
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<RHomeBean>>() {
				}.getType();
				mList = gson.fromJson(data, listType);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(Constants.TAG, e.toString());
			}
		}
		return mList;
	}

	public static ArrayList<SearchBean> jsonSearchHotData(JSONObject data) {
		ArrayList<SearchBean> mList = new ArrayList<SearchBean>();
		if (data != null && !"".equals(data)) {
			try {
				String entity = data.getString("entity");
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<SearchBean>>() {
				}.getType();
				mList = gson.fromJson(entity, listType);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e(Constants.TAG, e.toString());
			}
		}
		return mList;
	}

}
