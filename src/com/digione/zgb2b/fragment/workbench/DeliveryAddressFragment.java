package com.digione.zgb2b.fragment.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.workbench.AreaBean;
import com.digione.zgb2b.bean.workbench.DeliveryAddressBean;
import com.digione.zgb2b.bean.workbench.DeliveryAddressOutBean;
import com.digione.zgb2b.bean.workbench.ProviderBean;
import com.digione.zgb2b.bean.workbench.UserBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.common.CustomerJsonHttpResponseHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.HttpUtil;
import com.digione.zgb2b.utils.StringUtils;
import com.digione.zgb2b.utils.SystemUtil;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;
import com.loopj.android.http.RequestParams;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class DeliveryAddressFragment extends CommonBaseFragment {

	private final int DEFAULT_ADDRESS_INDEX = 1;
	private final int ADD = 1;
	private final int UPDATE = 2;
	private View v;
	// ListView 控件
	private ListView addressLV;
	private EditText consigneedET;
	private EditText consMobileET;
	private Button provinceBtn;
	private Button cityBtn;
	private Button countyBtn;
	private EditText consAddressET;
	private EditText warehouseET;
	private Button logistictsBtn;
	private Button addAddressBtn;
	private Button updateAddressBtn;
	private DeliveryAddressAdapter addressAdapter;
	// 地址列表
	private List<DeliveryAddressBean> addressList;
	private List<Integer> cityIds;
	private List<String> cityNames;
	// 县区列表
	private List<Integer> countyIds;
	private List<String> countyNames;
	private List<Integer> logistictsIds;
	private List<String> logistictsNames;
	private int defaultPostion = 0;
	private boolean isFromShopCat = false;
	private int consIdFromShopCat = -1;
	private DeliveryAddressBean mAddressBean;
	private Bundle bundle;
	// 获取收货地址列表handler
	private CustomerJsonHttpResponseHandler<ArrayList<DeliveryAddressBean>> addrListResponseHandler;
	// 新增handler
	private CustomerJsonHttpResponseHandler<DeliveryAddressOutBean> addrAddResponseHandler;
	// 市区handler
	private CustomerJsonHttpResponseHandler<ArrayList<AreaBean>> cityResponseHandler;
	// 县区handler
	private CustomerJsonHttpResponseHandler<ArrayList<AreaBean>> countyResponseHandler;
	// 物流商handler
	private CustomerJsonHttpResponseHandler<ArrayList<ProviderBean>> logisticsResponseHandler;

	private DialogInterface.OnClickListener cityListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (cityIds != null) {
				Integer tmpId = cityIds.get(which);
				if (mAddressBean.getCity() != null) {
					if (!tmpId.equals(mAddressBean.getCity())) {
						countyBtn.setText(getString(R.string.please_select));
						// 请求县区
						HttpClient countyClient = HttpClient.getInstall(activity);
						String countyUrl = Constants.Url.LOAD_AREA_014.replace("${id}", tmpId.toString());
						countyClient.postAsync(countyUrl, null, countyResponseHandler);
					}
				}
				mAddressBean.setCity(tmpId);
				mAddressBean.setCityName(cityNames.get(which));
				cityBtn.setText(mAddressBean.getCityName());

			}
			dialog.dismiss();
		}
	};
	private DialogInterface.OnClickListener countryListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (countyIds != null) {
				mAddressBean.setCounty(countyIds.get(which));
				mAddressBean.setCountyName(countyNames.get(which));
				countyBtn.setText(mAddressBean.getCountyName());
			}
			dialog.dismiss();
		}

	};
	private DialogInterface.OnClickListener logisticsListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (logistictsIds != null) {
				mAddressBean.setLogisticsId(logistictsIds.get(which));
				mAddressBean.setLogisticsName(logistictsNames.get(which));
				logistictsBtn.setText(mAddressBean.getLogisticsName());
			}
			dialog.dismiss();
		}

	};

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAddressBean = null;
		addressList = new ArrayList<DeliveryAddressBean>();
		countyIds = new ArrayList<Integer>();
		countyNames = new ArrayList<String>();
		cityIds = new ArrayList<Integer>();
		cityNames = new ArrayList<String>();
		logistictsIds = new ArrayList<Integer>();
		logistictsNames = new ArrayList<String>();

		cityResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<AreaBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<AreaBean> outBean, String msg) {
				if (cityIds == null) {
					cityIds = new ArrayList<Integer>();
				} else {
					cityIds.clear();
				}
				if (cityNames == null) {
					cityNames = new ArrayList<String>();
				} else {
					cityNames.clear();
				}
				if (outBean != null) {
					for (AreaBean areaBean : outBean) {
						cityIds.add(areaBean.getAreaId());
						cityNames.add(areaBean.getAreaName());
					}
				}
			}
		};

		countyResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<AreaBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<AreaBean> outBean, String msg) {
				if (countyIds == null) {
					countyIds = new ArrayList<Integer>();
				} else {
					countyIds.clear();
				}
				if (countyNames == null) {
					countyNames = new ArrayList<String>();
				} else {
					countyNames.clear();
				}
				if (outBean != null) {
					for (AreaBean areaBean : outBean) {
						countyIds.add(areaBean.getAreaId());
						countyNames.add(areaBean.getAreaName());
					}
				}
			}
		};

		logisticsResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<ProviderBean>>(activity) {

			@Override
			public void processCallSuccess(ArrayList<ProviderBean> outBean, String msg) {
				if (logistictsIds == null) {
					logistictsIds = new ArrayList<Integer>();
				} else {
					logistictsIds.clear();
				}

				if (logistictsNames == null) {
					logistictsNames = new ArrayList<String>();
				} else {
					logistictsNames.clear();
				}
				if (outBean != null) {
					for (ProviderBean providerBean : outBean) {
						logistictsIds.add(providerBean.getProvideId());
						logistictsNames.add(providerBean.getProvName());
					}
				}
			}
		};

		addrListResponseHandler = new CustomerJsonHttpResponseHandler<ArrayList<DeliveryAddressBean>>(activity,
				ChangeFragmentUtil.WORKBENCH_RECEIVING_ADDRESS, getArguments()) {

			@Override
			public void processCallSuccess(ArrayList<DeliveryAddressBean> outBean, String msg) {
				if (outBean != null && outBean.size() > 0) {
					addressList = outBean;
					int index = 0;
					boolean isDefaultFound = false;
					mAddressBean = new DeliveryAddressBean();
					for (DeliveryAddressBean deliveryAddressBean : outBean) {

						if (isFromShopCat) {
							if (consIdFromShopCat == deliveryAddressBean.getConsId()) {
								mAddressBean = deliveryAddressBean.cloneBean();
								defaultPostion = index;
								isDefaultFound = true;
								break;
							}
						} else {
							if (deliveryAddressBean.getIsdefault() == DEFAULT_ADDRESS_INDEX) {
								mAddressBean = deliveryAddressBean.cloneBean();
								defaultPostion = index;
								isDefaultFound = true;
								break;
							}
						}
						index++;
					}
					if (!isDefaultFound) {
						defaultPostion = 0;
						mAddressBean = outBean.get(defaultPostion).cloneBean();
					}

					// 把list数据设置到Adapter中
					addressAdapter = new DeliveryAddressAdapter(addressList, activity);
					addressLV.setAdapter(addressAdapter);
					setListViewHeightBasedOnChildren(addressLV);
					addressLV.setItemChecked(defaultPostion, true);
					// 设置默认选择地址
					consigneedET.setText(mAddressBean.getConsigneed());
					consMobileET.setText(mAddressBean.getConsMobile());
					provinceBtn.setText(mAddressBean.getProvinceName());
					cityBtn.setText(mAddressBean.getCityName());
					countyBtn.setText(mAddressBean.getCountyName());
					consAddressET.setText(mAddressBean.getConsAddress());
					warehouseET.setText(mAddressBean.getShopName());
					logistictsBtn.setText(mAddressBean.getLogisticsName());
				} else {
					ToastUtil.showToast(activity, getString(R.string.delivery_info_no_data), ToastUtil.LENGTH_SHORT);
					UserBean userBean = ZGApplication.getInstance().getUser();
					consigneedET.setText(userBean.getUserName());
					consMobileET.setText(userBean.getCustMobile());
					provinceBtn.setText(userBean.getProvinceName());
					cityBtn.setText(userBean.getCityName());
					countyBtn.setText(userBean.getAreaName());
					consAddressET.setText(userBean.getCustConsAddress());
					warehouseET.setText("");
					if (null != logistictsNames && logistictsNames.size() > 0) {
						logistictsBtn.setText(logistictsNames.get(0));
						mAddressBean.setLogisticsId(logistictsIds.get(0));
						mAddressBean.setLogisticsName(logistictsNames.get(0));
					} else {
						logistictsBtn.setText("");
						mAddressBean.setLogisticsId(null);
						mAddressBean.setLogisticsName(null);
					}
					mAddressBean.setCity(userBean.getCityId());
					mAddressBean.setCityName(userBean.getCityName());
					mAddressBean.setCounty(userBean.getAreaId());
					mAddressBean.setCountyName(userBean.getAreaName());
					mAddressBean.setProvince(userBean.getZoneId());
					mAddressBean.setProvinceName(userBean.getProvinceName());
					mAddressBean.setConsigneed(userBean.getUserName());
					mAddressBean.setConsMobile(userBean.getCustMobile());
					mAddressBean.setConsAddress(userBean.getCustConsAddress());
				}

				// 请求地市
				HttpClient cityClient = HttpClient.getInstall(activity);
				String cityUrl = Constants.Url.LOAD_AREA_014.replace("${id}", mAddressBean.getProvince().toString());
				cityClient.postAsync(cityUrl, null, cityResponseHandler);

				// 请求县区
				HttpClient countyClient = HttpClient.getInstall(activity);
				String countyUrl = Constants.Url.LOAD_AREA_014.replace("${id}", mAddressBean.getCity().toString());
				countyClient.postAsync(countyUrl, null, countyResponseHandler);

			}
		};

		addrAddResponseHandler = new CustomerJsonHttpResponseHandler<DeliveryAddressOutBean>(activity,
				ChangeFragmentUtil.WORKBENCH_RECEIVING_ADDRESS, getArguments()) {

			@Override
			public void processCallSuccess(DeliveryAddressOutBean outBean, String msg) {
				if (isFromShopCat) {
					mAddressBean.setConsId(outBean.getConsId());
					ZGApplication.getInstance().setAddressBean(mAddressBean);
					ChangeFragmentUtil.removeFragment();
				} else {
					ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.WORKBENCH_CLASSIFY, bundle);
				}
				ToastUtil.showToast(activity, msg, ToastUtil.LENGTH_SHORT);
			}
		};

		// 物流商
		HttpClient logistictsClient = HttpClient.getInstall(activity);
		String logistictsUrl = Constants.Url.LOAD_LOGISTICS_021;
		logistictsClient.postAsync(logistictsUrl, null, logisticsResponseHandler);

	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.delivery_addr_info, container, false);
		/**
		 * 隐藏输入键盘
		 */
		v.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				SystemUtil.closeSoftInput(activity);
				return false;
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (v != null) {

			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.my_direct_supply);
			// 隐藏按钮
			mTitle.hiddenButton();

			addressLV = (ListView) v.findViewById(R.id.workbench_delivery_info_lv);
			consigneedET = (EditText) v.findViewById(R.id.workbench_delivery_consigneed_et);
			consMobileET = (EditText) v.findViewById(R.id.workbench_modify_delivery_moible_et);

			provinceBtn = (Button) v.findViewById(R.id.workbench_delivery_province_btn);

			cityBtn = (Button) v.findViewById(R.id.workbench_delivery_city_btn);
			// 拆分的按钮响应同一个事件
			Button cityBtn2 = (Button) v.findViewById(R.id.workbench_delivery_city_btn2);
			cityBtn2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cityBtn.performClick();
				}
			});

			cityBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (null != mAddressBean.getProvince()) {
						dlgselectCity();
					} else {
						ToastUtil.showToast(activity, getString(R.string.please_select), ToastUtil.LENGTH_SHORT);
					}
				}
			});
			countyBtn = (Button) v.findViewById(R.id.workbench_delivery_county_btn);
			countyBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (null != mAddressBean.getCity()) {
						dlgselectCountry();
					} else {
						ToastUtil.showToast(activity, getString(R.string.please_select), ToastUtil.LENGTH_SHORT);
					}
				}
			});
			// 拆分的按钮响应同一个事件
			Button countyBtn2 = (Button) v.findViewById(R.id.workbench_delivery_county_btn2);
			countyBtn2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					countyBtn.performClick();
				}
			});

			consAddressET = (EditText) v.findViewById(R.id.workbench_delivery_detailed_et);
			logistictsBtn = (Button) v.findViewById(R.id.workbench_delivery_logistics_business_btn);
			logistictsBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dlgselectLogisticts();
				}
			});
			// 拆分的按钮响应相同事件
			Button logistictsBtn2 = (Button) v.findViewById(R.id.workbench_delivery_logistics_business_btn2);
			logistictsBtn2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					logistictsBtn.performClick();
				}
			});

			warehouseET = (EditText) v.findViewById(R.id.workbench_delivery_warehouse_et);

			updateAddressBtn = (Button) v.findViewById(R.id.workbench_delivery_update_btn);
			updateAddressBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					submitAddress(UPDATE);
				}
			});

			addAddressBtn = (Button) v.findViewById(R.id.workbench_delivery_add_btn);
			addAddressBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					submitAddress(ADD);
				}
			});
			consIdFromShopCat = getArguments().getInt(ArgName.DeliveryAddress.CONS_ID);
			isFromShopCat = getArguments().getBoolean(ArgName.DeliveryAddress.IS_FROM_SHOP_CART);

			// 请求收获地址列表
			HttpClient addrListClient = HttpClient.getInstall(activity);
			String url = Constants.Url.DELIVERY_ADDR_LIST_022;
			addrListClient.postAsync(url, null, addrListResponseHandler);

		}
	}

	private void submitAddress(int operatorType) {
		// 关闭软键盘
		SystemUtil.closeSoftInput(activity);

		String consigneed = consigneedET.getText().toString().trim();
		String consMobile = consMobileET.getText().toString().trim();
		String province = provinceBtn.getText().toString().trim();
		String city = cityBtn.getText().toString().trim();
		String county = countyBtn.getText().toString().trim();
		String detail = consAddressET.getText().toString().trim();
		String warehouse = warehouseET.getText().toString().trim();
		String logisticts = logistictsBtn.getText().toString().trim();

		if (StringUtils.isEmpty(consigneed)) {
			ToastUtil.showToast(activity, getString(R.string.empty_consigneed), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(consMobile)) {
			ToastUtil.showToast(activity, getString(R.string.empty_consMobile), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(province) || null == mAddressBean.getProvince()
				|| StringUtils.isEmpty(mAddressBean.getProvinceName())) {
			ToastUtil.showToast(activity, getString(R.string.empty_province), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(city) || null == mAddressBean.getCity() || StringUtils.isEmpty(mAddressBean.getCityName())) {
			ToastUtil.showToast(activity, getString(R.string.empty_city), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(county) || null == mAddressBean.getCounty()
				|| StringUtils.isEmpty(mAddressBean.getCountyName()) || county.equals(getString(R.string.please_select))) {
			ToastUtil.showToast(activity, getString(R.string.empty_county), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(detail)) {
			ToastUtil.showToast(activity, getString(R.string.empty_address_detail), ToastUtil.LENGTH_SHORT);
			return;
		}

		if (StringUtils.isEmpty(logisticts) || null == mAddressBean.getLogisticsId()
				|| StringUtils.isEmail(mAddressBean.getLogisticsName())) {
			ToastUtil.showToast(activity, getString(R.string.empty_logisticts), ToastUtil.LENGTH_SHORT);
			return;
		}

		// 验证手机号码
		if (!StringUtils.isMobileNO(consMobile)) {
			ToastUtil.showToast(activity, R.string.consignee_mobile_fomtter_error, ToastUtil.LENGTH_SHORT);
			return;
		}
		mAddressBean.setShopName(warehouse);
		mAddressBean.setConsigneed(consigneed);
		mAddressBean.setConsMobile(consMobile);
		mAddressBean.setFullConsAddress(mAddressBean.getProvinceName() + city + county + detail);
		mAddressBean.setConsAddress(detail);
		Map<String, String> map = new HashMap<String, String>();
		map.put("custConsignee.consigneed", mAddressBean.getConsigneed());
		map.put("custConsignee.consAddress", mAddressBean.getConsAddress());
		map.put("custConsignee.consMobile", mAddressBean.getConsMobile());
		map.put("custConsignee.provinceId", mAddressBean.getProvince().toString());
		map.put("custConsignee.cityId", mAddressBean.getCity().toString());
		map.put("custConsignee.countryId", mAddressBean.getCounty().toString());
		map.put("custConsignee.shopName", mAddressBean.getShopName());
		map.put("logisticsId", mAddressBean.getLogisticsId().toString());
		String addAddressUrl = Constants.Url.ADD_DELIVERY_024;
		if (operatorType == UPDATE) {
			map.put("custConsignee.consId", mAddressBean.getConsId().toString());
			addAddressUrl = Constants.Url.UPDATE_DELIVERY_025;
		}

		if (HttpUtil.isHasNetwork(activity)) {
			HttpClient addAddressUClient = HttpClient.getInstall(activity);

			RequestParams params = new RequestParams(map);
			addAddressUClient.postAsync(addAddressUrl, params, addrAddResponseHandler);
		} else {
			ToastUtil.showToast(activity, getString(R.string.msg_networkfail), ToastUtil.LENGTH_SHORT);
			return;
		}
	}

	private String[] listToArray(List<String> list) {
		if (list != null) {
			String[] array = new String[list.size()];
			return list.toArray(array);
		}
		return null;
	}

	private void dlgselectCity() {
		if (cityNames != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			// builder.setTitle(getString(R.string.please_select_city));
			int selectIndex = 0;
			if (cityIds != null && mAddressBean.getCity() != null) {
				selectIndex = cityIds.indexOf(mAddressBean.getCity());
			}
			builder.setSingleChoiceItems(listToArray(cityNames), selectIndex, cityListener);
			builder.create().show();
		} else {
			ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
		}
	}

	private void dlgselectCountry() {
		if (countyNames != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			int selectIndex = 0;
			if (mAddressBean.getCounty() != null && countyIds != null) {
				selectIndex = countyIds.indexOf(mAddressBean.getCounty());
			}
			builder.setSingleChoiceItems(listToArray(countyNames), selectIndex, countryListener);
			builder.create().show();
		} else {
			ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
		}
	}

	private void dlgselectLogisticts() {
		if (logistictsNames != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			int selectIndex = 0;
			if (mAddressBean.getLogisticsId() != null && null != logistictsIds) {
				selectIndex = logistictsIds.indexOf(mAddressBean.getLogisticsId());
			}
			builder.setSingleChoiceItems(listToArray(logistictsNames), selectIndex, logisticsListener);
			builder.create().show();
		} else {
			ToastUtil.showToast(activity, getString(R.string.nodata), ToastUtil.LENGTH_SHORT);
		}
	}

	/**
	 * @param listView
	 */
	private void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private void setCompValue(int position) {
		if (addressList != null && addressList.size() > 0) {
			DeliveryAddressBean tempAddressBean = addressList.get(position);
			if (!tempAddressBean.getProvince().equals(mAddressBean.getProvince())) {
				// 请求地市
				HttpClient cityClient = HttpClient.getInstall(activity);
				String cityUrl = Constants.Url.LOAD_AREA_014.replace("${id}", tempAddressBean.getProvince().toString());
				cityClient.postAsync(cityUrl, null, cityResponseHandler);
			}
			if (!tempAddressBean.getCity().equals(mAddressBean.getCity())) {
				// 请求县区
				HttpClient countyClient = HttpClient.getInstall(activity);
				String countyUrl = Constants.Url.LOAD_AREA_014.replace("${id}", tempAddressBean.getCity().toString());
				countyClient.postAsync(countyUrl, null, countyResponseHandler);
			}

			mAddressBean = tempAddressBean.cloneBean();
			defaultPostion = position;
			consigneedET.setText(mAddressBean.getConsigneed());
			consMobileET.setText(mAddressBean.getConsMobile());
			provinceBtn.setText(mAddressBean.getProvinceName());
			cityBtn.setText(mAddressBean.getCityName());
			countyBtn.setText(mAddressBean.getCountyName());
			consAddressET.setText(mAddressBean.getConsAddress());
			warehouseET.setText(mAddressBean.getShopName());
			logistictsBtn.setText(mAddressBean.getLogisticsName());
		}
	}

	class DeliveryAddressAdapter extends BaseAdapter {

		private List<DeliveryAddressBean> data;
		private Activity context;
		private LayoutInflater inflater;
		private int temp = -1;

		public DeliveryAddressAdapter(List<DeliveryAddressBean> data, Activity context) {
			this.data = data;
			this.context = context;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.delivery_addr_info_item, null);
				holder.consigneedRB = (RadioButton) convertView.findViewById(R.id.workbench_delivery_consigneed_rb);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.consigneedRB.setText(data.get(position).getConsigneed() + "\t" + data.get(position).getFullConsAddress());
			holder.consigneedRB.setId(position);
			holder.consigneedRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						if (temp != -1) {
							RadioButton tempButton = (RadioButton) context.findViewById(temp);
							if (tempButton != null) {
								tempButton.setChecked(false);
							}
						}
						temp = buttonView.getId();
						setCompValue(temp);
					}
				}
			});

			if (position == temp || defaultPostion == position) {
				holder.consigneedRB.setChecked(true);
			} else {
				holder.consigneedRB.setChecked(false);
			}

			return convertView;
		}

		class ViewHolder {
			RadioButton consigneedRB;
		}
	}
}