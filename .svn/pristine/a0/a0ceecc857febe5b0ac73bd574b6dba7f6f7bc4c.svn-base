package com.digione.zgb2b.fragment.home;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.home.GridViewAdapter;
import com.digione.zgb2b.adapter.home.HomeTopAdapter;
import com.digione.zgb2b.bean.home.GHomeBean;
import com.digione.zgb2b.bean.home.THomeBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.fragment.home.GridViewFragment.GridViewMethod;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.JSONData;
import com.digione.zgb2b.utils.PersistentService;
import com.digione.zgb2b.utils.PersistentServiceImpl;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.widget.MyGallery;
import com.digione.zgb2b.widget.MyGridView;

public class HomeFragment extends CommonBaseFragment {

	private View view;
	private LinearLayout search;
	private Button load;
	private MyGallery mGallery;// 顶部图片
	private LinearLayout pointLayout;// 显示当前页的图片
	private MyGridView mGridView;
	private List<GHomeBean> mList;
	private ArrayList<THomeBean> tList;
	private HomeTopAdapter hAdapter;
	private int curPage = 0;
	private boolean mAutoScroll = true;
	private boolean isTouch = false;
	private boolean isStop = false;
	private GridViewFragment rGridViewFragment;
	private PersistentService service;
	private Drawable drawable;

	private CustomerJsonHttpResponseHandler<ArrayList<GHomeBean>> gHttpResponseHandler;

	private CustomerJsonHttpResponseHandler<ArrayList<THomeBean>> tJsonHttpResponseHandler;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MsgCode.GET_IMAGE_ROTATE_SUCCESS:
				initTopImg();
				break;
			case MsgCode.GET_ASSIGN_PRODUCT_SUCCESS:
				recommendImg();// 推荐
				break;
			case MsgCode.IMAGE_ROTATE_NEXT:
				mGallery.setSelection(curPage);
				setCurPage(curPage);
				break;
			case MsgCode.IMAGE_ROTATE_SELECTED:
				setCurPage(curPage);
				break;
			}

		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawable = getResources().getDrawable(R.drawable.bg_img_item_true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home, container, false);
		gHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<GHomeBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<GHomeBean> outBean, String msg) {
				mList = outBean;
				if (mList != null && mList.size() != 0) {
					service.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
							Constants.DataFile.CommonInfoKey.HOME_ADVERT_DATA, getEntityJsonString());
					ZGApplication.getInstance().setSaveGHomeBean(true);
					mHandler.sendEmptyMessage(MsgCode.GET_IMAGE_ROTATE_SUCCESS);
				}
			}
		};

		tJsonHttpResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<THomeBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<THomeBean> outBean, String msg) {
				tList = outBean;
				if (tList != null && tList.size() != 0) {
					ZGApplication.getInstance().setSaveTHomeBean(true);
					service.saveBySharePreference(activity, Constants.DataFile.COMMON_INFO,
							Constants.DataFile.CommonInfoKey.HOME_RECOMMEND_DATA, getEntityJsonString());
					mHandler.sendEmptyMessage(MsgCode.GET_ASSIGN_PRODUCT_SUCCESS);
				}
			}
		};
		FragmentManager fragmentManager = getChildFragmentManager();
		rGridViewFragment = (GridViewFragment) fragmentManager.findFragmentById(R.id.home_renmen);
		if (null == rGridViewFragment) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			rGridViewFragment = new GridViewFragment();
			ft.add(R.id.home_renmen, rGridViewFragment);
			ft.commit();
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (view != null) {
			service = new PersistentServiceImpl();
			findViewById();
			initData();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mAutoScroll = true;
		isStop = false;
	}

	@Override
	public void onPause() {
		super.onPause();
		mAutoScroll = false;
		isStop = true;
	}

	private void findViewById() {
		load = (Button) view.findViewById(R.id.home_load);
		search = (LinearLayout) view.findViewById(R.id.search_btn);
		mGallery = (MyGallery) view.findViewById(R.id.home_top_img);
		if (SystemUtil.getDeviceWidth(activity) >= 720) {
			mGallery.setLayoutParams(new FrameLayout.LayoutParams(SystemUtil.getDeviceWidth(activity), 278));
		}
		pointLayout = (LinearLayout) view.findViewById(R.id.home_point_img);
		mGridView = (MyGridView) view.findViewById(R.id.home_recommend);
		if (!ZGApplication.getInstance().isLogin()) {
			load.setVisibility(View.VISIBLE);
		} else {
			load.setVisibility(View.GONE);
		}
		setListener();
	}

	private void setListener() {

		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.MORE_SEARCH, new Bundle());
			}
		});
		load.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChangeFragmentUtil.changeToUserLoginFragment(ChangeFragmentUtil.HOME_CLASSIFY, activity, null,
						getArguments());
			}
		});
	}

	private void initData() {
		if (ZGApplication.getInstance().isSaveGHomeBean()) {
			String data = service.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.HOME_ADVERT_DATA);
			mList = JSONData.parseGHomeBeanData(data);
			initTopImg();
		} else {
			HttpClient httpClient = HttpClient.getInstall(activity);
			httpClient.postAsync(Constants.Url.HOME_GUANGGAO_IMG_006, null, gHttpResponseHandler);
		}

		if (ZGApplication.getInstance().isSaveTHomeBean()) {
			String data = service.getStringBySharePreference(activity, Constants.DataFile.COMMON_INFO,
					Constants.DataFile.CommonInfoKey.HOME_RECOMMEND_DATA);
			tList = JSONData.parseTHomeBeanData(data);
			if (tList != null && tList.size() != 0) {
				mHandler.sendEmptyMessage(MsgCode.GET_ASSIGN_PRODUCT_SUCCESS);
			}
		} else {
			HttpClient mHttpClient = HttpClient.getInstall(activity);
			mHttpClient.postAsync(Constants.Url.HOME_TUIJIAN_IMG_007, null, tJsonHttpResponseHandler);
		}

	}

	private void initTopImg() {
		if (mList == null || mList.size() == 0)
			return;
		setCurPage(curPage);
		startAutoScroll();
		hAdapter = new HomeTopAdapter(activity, mList);
		mGallery.setAdapter(hAdapter);
		mGallery.setSpacing(1);
		mGallery.setOnItemClickListener(itemClickListener);
		mGallery.setOnItemSelectedListener(itemSelectListener);
		mGallery.setOnTouchListener(touchListener);
		// mGallery.setCallbackDuringFling(false);
	}

	// 更新当前页码
	public void setCurPage(int page) {
		pointLayout.removeAllViews();

		for (int i = 0; i < mList.size(); i++) {
			ImageView imageView = (ImageView) LayoutInflater.from(activity).inflate(R.layout.home_image, null);
			if (i == page) {
				imageView.setImageDrawable(drawable);
			}
			pointLayout.addView(imageView);
		}
	}

	private void startAutoScroll() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while (mAutoScroll) {
					count = 0;
					while (count < 100 && !isStop) {
						count++;
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (isTouch) {// 用戶手动滑动时，停止自动滚动
							count = 0;
						}
					}
					curPage++;
					if (curPage > mList.size() - 1) {
						curPage = 0;
					}
					if (mAutoScroll) {
						mHandler.sendEmptyMessage(MsgCode.IMAGE_ROTATE_NEXT);
					}
				}
			}
		}).start();
	}

	private OnItemSelectedListener itemSelectListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mList != null && mList.size() != 0) {
				curPage = arg2 % mList.size();
				mHandler.sendEmptyMessage(MsgCode.IMAGE_ROTATE_SELECTED);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mList != null && mList.size() > 0) {
				GHomeBean gHomeBean = mList.get(arg2 % mList.size());
				int linkType = gHomeBean.getLinkType();
				String linkUrl = gHomeBean.getLinkUrl();
				String titleName = gHomeBean.getTitleName();
				String imgUrl = gHomeBean.getImageUrl();
				SystemUtil.proItemOnClickListener(linkType, linkUrl, titleName, imgUrl);
			}
		}
	};

	private OnTouchListener touchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				isTouch = true;
			} else if (action == MotionEvent.ACTION_UP) {
				isTouch = false;
			}
			return false;
		}
	};

	private void recommendImg() {
		if (tList == null)
			return;
		GridViewAdapter<THomeBean> adapter = new GridViewAdapter<THomeBean>(activity, GridViewMethod.zhigong,
				R.layout.home_img_and_txt_layout);
		adapter.setList(tList);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (tList != null && tList.size() > 0) {
					THomeBean tHomeBean = tList.get(arg2);
					String linkUrl = tHomeBean.getProductDetailUrl();
					String titleName = tHomeBean.getBrandName() + "  " + tHomeBean.getPattern() + "  "
							+ tHomeBean.getColorSpec();
					String imgUrl = tHomeBean.getPicPath1Url();
					SystemUtil.proItemOnClickListener(2, linkUrl, titleName, imgUrl);
				}
			}
		});
	}

}
