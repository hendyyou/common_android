package com.digione.zgb2b.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.digione.zgb2b.ZGApplication;

public final class HttpUtil {

	// 判断是否为wifi连接
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (ni.getState() == NetworkInfo.State.CONNECTED) {
			// Resource.bWifiConnected = true;
			return true;
		}
		return false;
	}

	// 判断当前是否有网络
	public static boolean isHasNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()) {
			return false;
		}
		return true;
	}

	// 判断当前是否是wap网络
	public static boolean isWapConnected(Context context) {
		if (isWifiConnected(context)) {
			return false;
		}
		Uri contentUri = Uri.parse("content://telephony/carriers/preferapn");
		Cursor cursor = null;
		ContentResolver resolver = context.getContentResolver();
		try {
			cursor = resolver.query(contentUri, new String[] { "proxy", "port" }, null, null, null);
			if (cursor != null) {
				String proxy = null;
				int port = 0;
				if (cursor.moveToFirst()) {
					proxy = cursor.getString(0);
					String strPort = cursor.getString(1);
					if (strPort != null && strPort.length() > 0)
						port = Integer.valueOf(strPort);
				}

				if (!TextUtils.isEmpty(proxy)) {
					if (port <= 0) {
						port = 80;
					}
					/*
					 * Resource.isProxy = true; Resource.proxyHost = proxy; Resource.proxyPort = port;
					 */
					ZGApplication.setProxy(true);
					ZGApplication.setProxyHost(proxy);
					ZGApplication.setProxyPort(port);
					return true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}

		return false;
	}
}
