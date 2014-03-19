package com.digione.zgb2b.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersistentServiceImpl implements PersistentService {

	@Override
	public void saveBySharePreference(Context context, String fileName, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor saveEditor = sp.edit();
		saveEditor.putString(key, value);
		saveEditor.commit();
	}

	@Override
	public String getStringBySharePreference(Context context, String fileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sp.getString(key, null);

	}

	@Override
	public void clearAllDataSharePreference(Context context, String fileName) {
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor saveEditor = sp.edit();
		saveEditor.clear();
		saveEditor.commit();
	}

	@Override
	public void clearSharePreference(Context context, String fileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor saveEditor = sp.edit();
		saveEditor.remove(key);
		saveEditor.commit();
	}

}
