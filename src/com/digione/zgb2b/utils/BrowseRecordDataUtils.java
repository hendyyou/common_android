package com.digione.zgb2b.utils;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.ProductBean;
import com.digione.zgb2b.common.Constants;
import com.digione.zgb2b.database.DBHelper;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 13-4-18 Time: 下午4:17
 */
public class BrowseRecordDataUtils {
	private DBHelper db = null;
	private static BrowseRecordDataUtils browseRecordDataUtils;

	private BrowseRecordDataUtils(Activity activity) {
		db = new DBHelper(activity);
	}

	public static BrowseRecordDataUtils getInstall(Activity activity) {
		if (browseRecordDataUtils == null) {
			browseRecordDataUtils = new BrowseRecordDataUtils(activity);
		}
		return browseRecordDataUtils;
	}

	/**
	 * 返回近期浏览记录List
	 * 
	 * @return
	 */
	public ArrayList<ProductBean> getBrowseRecords() {
		ArrayList<ProductBean> productList = null;
		if (db != null && ZGApplication.getInstance().isLogin() && ZGApplication.getInstance().getUser() != null) {
			productList = new ArrayList<ProductBean>();

			// 查询所有收藏数据
			Cursor cursor = null;
			try {
				cursor = db.selectAll();
				while (cursor.moveToNext()) {
					ProductBean productBean = new ProductBean();

					int productId = cursor.getColumnIndex("productId");
					int dictId1 = cursor.getColumnIndex("dictId1");
					int productDetailUrl = cursor.getColumnIndex("productDetailUrl");
					int picPath1Url = cursor.getColumnIndex("picPath1Url");
					int brandName = cursor.getColumnIndex("brandName");
					int pattern = cursor.getColumnIndex("pattern");
					int colorSpec = cursor.getColumnIndex("colorSpec");
					int saleSpec = cursor.getColumnIndex("saleSpec");
					int configSpec = cursor.getColumnIndex("configSpec");
					int isSpecial = cursor.getColumnIndex("isSpecial");
					int advicePrice = cursor.getColumnIndex("advicePrice");
					int custPrice = cursor.getColumnIndex("custPrice");
					int isForesee = cursor.getColumnIndex("isForesee");
					int provName = cursor.getColumnIndex("provName");
					int advicePriceTitle = cursor.getColumnIndex("advicePriceTitle");
					int sysCustPriceTitle = cursor.getColumnIndex("sysCustPriceTitle");
					int custPriceTitle = cursor.getColumnIndex("custPriceTitle");
					int sysCustPrice = cursor.getColumnIndex("sysCustPrice");
                    int isGovEnterprise = cursor.getColumnIndex("isGovEnterprise");
					productBean.setProductId(cursor.getInt(productId));
					productBean.setDictId1(cursor.getInt(dictId1));
					productBean.setProductDetailUrl(cursor.getString(productDetailUrl));
					productBean.setPicPath1Url(cursor.getString(picPath1Url));
					productBean.setBrandName(cursor.getString(brandName));
					productBean.setPattern(cursor.getString(pattern));
					productBean.setColorSpec(cursor.getString(colorSpec));
					productBean.setSaleSpec(cursor.getString(saleSpec));
					productBean.setConfigSpec(cursor.getString(configSpec));
					productBean.setIsSpecial(cursor.getInt(isSpecial));
					productBean.setAdvicePrice(cursor.getDouble(advicePrice));
					productBean.setCustPrice(cursor.getDouble(custPrice));
					productBean.setIsForesee(cursor.getString(isForesee));
					productBean.setProvName(cursor.getString(provName));
					productBean.setAdvicePriceTitle(cursor.getString(advicePriceTitle));
					productBean.setSysCustPriceTitle(cursor.getString(sysCustPriceTitle));
					productBean.setCustPriceTitle(cursor.getString(custPriceTitle));
					productBean.setSysCustPrice(cursor.getDouble(sysCustPrice));
                    productBean.setIsGovEnterprise(cursor.getInt(isGovEnterprise));
					productList.add(productBean);
				}
			} catch (Exception e) {
				Log.e(Constants.TAG, e.getMessage());
				e.printStackTrace();
			} finally {
				if (null != cursor) {
					cursor.close();
				}
				db.close();
			}
		}
		return productList;
	}

	/**
	 * 添加近期浏览记录
	 * 
	 * @param productBean
	 */
	public void addBrowseRecord(ProductBean productBean) {
		// 保存GoodsBean 到sdcard上
		if (db != null && ZGApplication.getInstance().isLogin() && ZGApplication.getInstance().getUser() != null) {
			// 查询所有的记录，然后计算记录的总数，如果超过20条，删除ID最小的记录
			Cursor cursor = null;
			try {
				cursor = db.selectAll();
				int totalCount = cursor.getCount();
				while (totalCount >= Constants.MAXBROWSECOUNT) {
					db.deleteMinID();
					cursor.close();
					cursor = null;

					cursor = db.selectAll();
					totalCount = cursor.getCount();
				}
				cursor.close();
				cursor = null;

				// 查询该产品是否已经已经浏览过
				cursor = db.selectByWhere(productBean.getProductDetailUrl());
				if (cursor.getCount() < 1) {
					db.insert(productBean);
				}
			} catch (Exception e) {
				Log.e(Constants.TAG, e.getMessage());
				e.printStackTrace();
			} finally {
				if (null != cursor) {
					cursor.close();
				}
				db.close();
			}
		}
	}
}
