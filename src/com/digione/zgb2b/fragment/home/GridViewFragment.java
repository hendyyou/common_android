package com.digione.zgb2b.fragment.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.home.GridViewAdapter;
import com.digione.zgb2b.bean.home.RHomeBean;
import com.digione.zgb2b.bean.home.THomeBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.JSONData;
import com.digione.zgb2b.utils.PersistentService;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.widget.MyGridView;

public class GridViewFragment extends CommonBaseFragment {
	public enum GridViewMethod {
		zhigong, renmen;
	}

	private TextView gridTV;
	private LinearLayout gridLayout;
	private MyGridView gridView;
	// 首页添加了两个，所以得设两个参数
	private GridViewMethod gridViewMethod;
	private GridViewMethod mGridViewFragment;
	public ArrayList<THomeBean> mList;
	private ArrayList<RHomeBean> rList;
	private PersistentService service;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MsgCode.GET_HOT_ZONE_SUCCESS:
				initData();
				break;

			default:
				break;
			}
		};
	};

	private CustomerJsonHttpResponseHandler<ArrayList<RHomeBean>> jsonHttpResponseHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.grid_img_txt, container, false);
		gridLayout = (LinearLayout) view.findViewById(R.id.grid_img_txt_bg);
		gridTV = (TextView) view.findViewById(R.id.grid_img_head_txt);
		gridView = (MyGridView) view.findViewById(R.id.grid_img_txt_gridview);

		gridViewMethod = GridViewMethod.renmen;
		gridLayout.setBackgroundResource(R.drawable.home_news);
		service = new PersistentServiceImpl();

		jsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<RHomeBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<RHomeBean> outBean, String msg) {
				if (gridViewMethod == GridViewMethod.renmen) {
					rList = outBean;
					if (rList != null && rList.size() != 0) {
						service.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
								Constants.DataFile.CommonInfoKey.HOME_HOTZONE_DATA, getEntityJsonString());
						ZGApplication.getInstance().setSaveRHomeBean(true);
						mHandler.sendEmptyMessage(MsgCode.GET_HOT_ZONE_SUCCESS);
					}
				}
			}
		};

		setData(gridViewMethod);
		return view;
	}

	public synchronized void setData(GridViewMethod method) {
		// 网络请求
		if (ZGApplication.getInstance().isSaveRHomeBean()) {
			String data = service.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.HOME_HOTZONE_DATA);
			rList = JSONData.parseRHomeBeanData(data);
			initData();
		} else {
			HttpClient client = HttpClient.getInstall(activity);
			String url = null;
			if (gridViewMethod == GridViewMethod.renmen) {
				gridTV.setText(getResources().getString(R.string.renmen));
				url = Constants.Url.HOME_HOTZONE_IMG_029;
			}
			client.postAsync(url, null, jsonHttpResponseHandler);
		}
	}

	private void initData() {
		if (gridViewMethod == GridViewMethod.zhigong) {
			GridViewAdapter<THomeBean> adapter = new GridViewAdapter<THomeBean>(activity, gridViewMethod,
					R.layout.home_img_and_txt_layout);
			adapter.setList(mList);
			gridView.setAdapter(adapter);
		} else if (gridViewMethod == GridViewMethod.renmen) {
			GridViewAdapter<RHomeBean> adapter = new GridViewAdapter<RHomeBean>(activity, gridViewMethod,
					R.layout.img_and_txt_layout);
			adapter.setList(rList);
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(onItemClickListener);
		}
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (gridViewMethod == GridViewMethod.renmen) {
				if (rList != null && rList.size() > 0) {
					RHomeBean rBean = rList.get(arg2);
					int linkType = rBean.getLinkType();
					String linkUrl = rBean.getLinkUrl();
					String titleName = rBean.getTitleName();
					String imgUrl = rBean.getImageUrl();
					SystemUtil.proItemOnClickListener(linkType, linkUrl, titleName, imgUrl);
				}
			}
		}
	};

}
