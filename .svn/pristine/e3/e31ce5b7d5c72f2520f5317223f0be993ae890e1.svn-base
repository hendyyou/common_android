package com.digione.zgb2b.fragment.more;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.digione.zgb2b.R;
import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.adapter.more.MoreAdapter;
import com.digione.zgb2b.common.Constants.ArgName;
import com.digione.zgb2b.common.Constants.MsgCode;
import com.digione.zgb2b.common.ExitAppHandler;
import com.digione.zgb2b.common.HttpClient;
import com.digione.zgb2b.fragment.CommonBaseFragment;
import com.digione.zgb2b.utils.ChangeFragmentUtil;
import com.digione.zgb2b.utils.ShopCartUtils;
import com.digione.zgb2b.utils.ToastUtil;
import com.digione.zgb2b.widget.TitleView;

/**
 * 我的直供
 * 
 * @author youzh
 * @version V1.0
 * @create date: 2013-4-9
 */
public class MoreFragment extends CommonBaseFragment {

	private View v;
	private int[] titles;
	private int[] icons;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MsgCode.READ_SHOP_CART_FAILURE: {
				ToastUtil.showToast(activity, "系统读取购物车信息异常！", ToastUtil.LENGTH_LONG);
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.more_main, container, false);
		if (v != null) {
			// 标题
			if (ZGApplication.getInstance().isLogin()) {
				titles = new int[] { R.string.search, R.string.browse_records, R.string.customer_hotline, R.string.feedback,
						R.string.about, R.string.exit };

				icons = new int[] { R.drawable.more_search, R.drawable.browse_records, R.drawable.customer_hotline,
						R.drawable.feedback, R.drawable.about, R.drawable.exit };
			} else {
				titles = new int[] { R.string.search, R.string.browse_records, R.string.customer_hotline, R.string.feedback,
						R.string.about };

				icons = new int[] { R.drawable.more_search, R.drawable.browse_records, R.drawable.customer_hotline,
						R.drawable.feedback, R.drawable.about };
			}

			MoreAdapter moreAdapter = new MoreAdapter(activity, titles, icons);

			ListView moreLV = (ListView) v.findViewById(R.id.more_main_lv);
			moreLV.setAdapter(moreAdapter);
			// moreAdapter.notifyDataSetChanged();

			moreLV.setOnItemClickListener(new MoreOnClickListener());

			TitleView mTitle = (TitleView) v.findViewById(R.id.title);
			mTitle.setTitle(R.string.more);
			// 隐藏按钮
			mTitle.hiddenButton();
		}
		return v;
	}

	/**
	 * 更多，进入每个类别的明细界面
	 */
	class MoreOnClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Bundle bundle = new Bundle();
			switch (titles[position]) {
			case R.string.exit: // 注销
				// 先退出登录
				ExitAppHandler exitAppHandler = new ExitAppHandler(activity);
				exitAppHandler.exit();
				ZGApplication.getInstance().setLogin(false);
				ShopCartUtils.getInstance(mHandler).getShopCartNum(activity);
				ZGApplication.getInstance().setUser(null);
				ZGApplication.getInstance().setResetShopCart(false);
				// 重置保存首页广告标识
				ZGApplication.getInstance().setSaveGHomeBean(false);
				HttpClient.clearCookie();
				// 界面跳转到首页界面
				ChangeFragmentUtil.performHomeTabClick();
				break;
			case R.string.about: // 关于
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.MORE_ABOUT, bundle);
				break;
			case R.string.feedback: // 意见反馈
				// 需要先登录再反馈意见
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.MORE_FEEDBACK, bundle);
				break;
			case R.string.customer_hotline: // 客服热线
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.MORE_CUSTOMER_HOTLINE, bundle);
				break;
			case R.string.browse_records:// 近期浏览
				bundle.putString(ArgName.ProductList.TITLE, getString(R.string.browse_records));
				bundle.putBoolean(ArgName.ProductList.SHOW_SORT, false);
				bundle.putBoolean(ArgName.ProductList.IS_LOCAL_DATA, true);
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.PRODUCT_LIST, bundle);
				break;
			case R.string.search:// 搜索
				ChangeFragmentUtil.changeFragment(ChangeFragmentUtil.MORE_SEARCH, bundle);
				break;
			}
		}
	}
}