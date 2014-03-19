package com.digione.zgb2b.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.utils.ChangeFragmentUtil;

/**
 * 填充替换Fragment
 * 
 * @author simpson
 * 
 */
public class SimpleMainFragment extends CommonBaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stack, container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!ZGApplication.isCanClearPopBackStack()) {
			// 应用程序创建首次，不进行清除回退栈
			ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.HOME_CLASSIFY, null);
			ZGApplication.setCanClearPopBackStack(true);
		}
	}
}
