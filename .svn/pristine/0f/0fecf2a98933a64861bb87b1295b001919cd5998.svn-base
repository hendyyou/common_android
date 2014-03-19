package com.digione.zgb2b.fragment.more;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.adapter.more.SearchAdapter;
import com.digione.zgb2b.bean.search.SearchBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.PersistentService;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.SystemUtil;
import com.loopj.android.http.RequestParams;

public class SearchFragment extends CommonBaseFragment {

	private View view;
	private EditText searchEText;
	private LinearLayout searchLayout;
	private GridView gridView;
	private Button history;
	private Button hot;
	private RelativeLayout emptyLayout;
	private boolean isHot = false;
	private ArrayList<String> mList = new ArrayList<String>();
	private ArrayList<SearchBean> sList;
	private SearchAdapter adapter;
	private PersistentService pService = new PersistentServiceImpl();
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MsgCode.LOAD_HOT_SEARCH_SUCCESS:
				setData();
				break;

			default:
				break;
			}
		};
	};
	private CustomerJsonHttpResponseHandler<ArrayList<SearchBean>> jsonHttpResponseHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search, container, false);
		searchEText = (EditText) view.findViewById(R.id.search_word);
		searchLayout = (LinearLayout) view.findViewById(R.id.search_btn);
		history = (Button) view.findViewById(R.id.search_history);
		hot = (Button) view.findViewById(R.id.search_hot);
		gridView = (GridView) view.findViewById(R.id.search_gridview);
		emptyLayout = (RelativeLayout) view.findViewById(R.id.search_no_content);
		setBtnBackground(isHot);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<SearchBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<SearchBean> outBean, String msg) {
				sList = outBean;
				mList.clear();
				if (sList != null && sList.size() != 0) {
					for (int i = 0; i < sList.size(); i++) {
						mList.add(sList.get(i).getDictName());
					}
				}
				mHandler.sendEmptyMessage(MsgCode.LOAD_HOT_SEARCH_SUCCESS);
			}
		};
		if (view != null) {
			initData();
		}
	}

	private void initData() {
		history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isHot = false;
				setBtnBackground(isHot);
				loadData();
			}
		});
		hot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isHot = true;
				setBtnBackground(isHot);
				loadData();
			}
		});
		searchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String argString = searchEText.getText().toString();
				if (argString.length() != 0 && !argString.equals("")) {
					// 关闭软键盘
					SystemUtil.closeSoftInput(activity);
					changeFragment(argString);
					saveHistoryWord(argString);
				}
			}
		});
		loadData();
	}

	private void setBtnBackground(boolean isHot) {
		if (isHot) {
			hot.setBackgroundResource(R.drawable.right_btn_after);
			history.setBackgroundResource(R.drawable.left_btn);
		} else {
			history.setBackgroundResource(R.drawable.left_btn_after);
			hot.setBackgroundResource(R.drawable.rigth_btn);
		}
	}

	private void loadData() {
		// 根据是否是热门推荐不同的逻辑处理
		if (isHot) {
			emptyLayout.setVisibility(View.GONE);
			gridView.setVisibility(View.VISIBLE);
			HttpClient client = HttpClient.getInstall(activity);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("returnSearchUrlFlag", "1");
			RequestParams params = new RequestParams(map);
			client.postAsync(Constants.Url.SEARCH_HOT_031, params, jsonHttpResponseHandler);
		} else {
			mList.clear();
			String history = pService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.SEARCHKEY_STRINGS);
			if (history != null && !history.equals("")) {
				String[] data = history.split(",");
				for (int i = 0; i < data.length; i++) {
					mList.add(data[i]);
				}
				setData();
			} else {
				gridView.setVisibility(View.GONE);
				emptyLayout.setVisibility(View.VISIBLE);
			}
		}
	}

	private void setData() {
		adapter = new SearchAdapter(activity, mList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				changeFragment(mList.get(arg2));
				saveHistoryWord(mList.get(arg2));
			}
		});
	}

	public void changeFragment(String arg) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("typeName", "keyword");
		param.put("keyword", arg);
		param.put("targetpage", String.valueOf(1));
		Bundle bundle = new Bundle();
		bundle.putString(ArgName.ProductList.URL, Constants.Url.PRODUCT_SEARCH_020);
		bundle.putString(ArgName.ProductList.TITLE, getResources().getString(R.string.search_result));
		bundle.putBoolean(ArgName.ProductList.SHOW_SORT, false);
		bundle.putSerializable(ArgName.ProductList.PARAM, param);
		ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
	}

	public void saveHistoryWord(String str) {
		if (str.equals(""))
			return;
		String history = pService.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
				Constants.DataFile.CommonInfoKey.SEARCHKEY_STRINGS);
		StringBuilder sb = new StringBuilder();
		try {
			if (history != null) {
				String[] data = history.split(",");
				List list = Arrays.asList(data);
				List arrayList = new ArrayList(list);
				for (int i = 0; i < arrayList.size(); i++) {
					if (str.equals(arrayList.get(i)) || arrayList.get(i).equals(""))
						arrayList.remove(i);
				}
				if (arrayList.size() == 12)
					arrayList.remove(11);
				arrayList.add(0, str);
				for (int j = arrayList.size() - 1; j >= 0; j--) {
					if (j == arrayList.size() - 1)
						sb.insert(0, arrayList.get(j));
					else {
						sb.insert(0, arrayList.get(j) + ",");
					}
				}
			} else {
				sb.append(str + ",");
			}
		} catch (Exception e) {
		}
		history = sb.toString();
		pService.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
				Constants.DataFile.CommonInfoKey.SEARCHKEY_STRINGS, history);
	}
}
