package com.digione.zgb2b.utils;

import com.loopj.android.http.SyncHttpClient;

public class CustomSyncHttpClient extends SyncHttpClient {

	@Override
	public String onRequestFailed(Throwable error, String content) {

		return "";
	}

}
