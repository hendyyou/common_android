package com.digione.zgb2b.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;

import com.digione.zgb2b.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public final class DisplayImageOptionsUtil {

	/**
	 * 获取图片显示选项对象实体
	 * 
	 * @return 图片显示选项
	 */
	public static DisplayImageOptions getDisplayImageOptions() {

		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.no_picture)
				.showImageOnFail(R.drawable.no_picture).cacheInMemory().cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new SimpleBitmapDisplayer()).resetViewBeforeLoading().build();

		return options;
	}

	/**
	 * 获取图片显示Listener
	 * 
	 * @return Listener实体
	 */
	public static ImageLoadingListener getImageLoadingListener() {
		return new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				if (arg1 != null && arg1.getTag() != null && arg1.getTag() instanceof ProgressBar) {
					((ProgressBar) arg1.getTag()).setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				if (arg1 != null && arg1.getTag() != null && arg1.getTag() instanceof ProgressBar) {
					((ProgressBar) arg1.getTag()).setVisibility(View.GONE);
				}
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (arg1 != null && arg1.getTag() != null && arg1.getTag() instanceof ProgressBar) {
					((ProgressBar) arg1.getTag()).setVisibility(View.GONE);
				}
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				if (arg1 != null && arg1.getTag() != null && arg1.getTag() instanceof ProgressBar) {
					((ProgressBar) arg1.getTag()).setVisibility(View.GONE);
				}
			}
		};
	}

}
