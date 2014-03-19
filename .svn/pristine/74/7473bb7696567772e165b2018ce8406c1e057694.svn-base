package com.digione.zgb2b.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.common.Constants;

public class CommonBaseFragment extends Fragment {
	protected Activity activity;

	@Override
	public void onAttach(Activity activity) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onAttach()");
		}
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onCreate()");
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onActivityCreated()");
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onActivityResult()");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onStart()");
		}
		super.onStart();
	}

	@Override
	public void onResume() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onResume()");
		}
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onSaveInstanceState()");
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onPause()");
		}
		super.onPause();
	}

	@Override
	public void onStop() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onStop()");
		}
		super.onStop();
	}

	@Override
	public void onDestroy() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onDestroy()");
		}
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		if (ZGApplication.isDevMode()) {
			Log.d(Constants.TAG, getClass().getSimpleName() + " onDetach()");
		}
		super.onDetach();
		this.activity = null;
	}

}
