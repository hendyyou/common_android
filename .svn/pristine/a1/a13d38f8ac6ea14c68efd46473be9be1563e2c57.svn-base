package com.digione.zgb2b.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.digione.zgb2b.ZGApplication;
import com.digione.zgb2b.bean.product.ProductBean;
import com.digione.zgb2b.common.Constants;

/**
 * @author 尤振华
 * @version 1.0
 * @ClassName: DBHelper
 * @Description: DB帮助类
 * @date 2012-11-6 下午12:07:31
 */
public class DBHelper {
	// 数据库名称
	public static final String DB_NAME = "zhigong.db";
	public static final String T_BROWSE_RECORDS = "T_BROWSE_RECORDS";
	// 数据库版本
	public static final int DB_VERSION = 4;
	private final SqlLiteHelper helper;

	public DBHelper(Context context) {
		helper = new SqlLiteHelper(context);
	}

	public void insert(ProductBean productBean) {
		ContentValues values = new ContentValues();
		values.put("custMobile", ZGApplication.getInstance().getUser().getCustMobile());
		values.put("productId", productBean.getProductId());
		values.put("dictId1", productBean.getDictId1());
		values.put("productDetailUrl", productBean.getProductDetailUrl());
		values.put("picPath1Url", productBean.getPicPath1Url());
		values.put("brandName", productBean.getBrandName());
		values.put("pattern", productBean.getPattern());
		values.put("colorSpec", productBean.getColorSpec());
		values.put("saleSpec", productBean.getSaleSpec());
		values.put("configSpec", productBean.getConfigSpec());
		values.put("isSpecial", productBean.getIsSpecial());
		values.put("advicePrice", productBean.getAdvicePrice());
		values.put("custPrice", productBean.getCustPrice());
		values.put("isForesee", productBean.getIsForesee());
		values.put("provName", productBean.getProvName());
		values.put("advicePriceTitle", productBean.getAdvicePriceTitle());
		values.put("sysCustPriceTitle", productBean.getProductId());
		values.put("custPriceTitle", productBean.getCustPriceTitle());
		values.put("sysCustPrice", productBean.getSysCustPrice());
        values.put("isGovEnterprise",productBean.getIsGovEnterprise());
		helper.getWritableDatabase().insert("T_BROWSE_RECORDS", null, values);
	}

	public Cursor selectByWhere(String productdetailhref) {
		return helper.getReadableDatabase().query(T_BROWSE_RECORDS, new String[] { "productDetailUrl", },
				"productDetailUrl = ? and custMobile = ?",
				new String[] { productdetailhref, ZGApplication.getInstance().getUser().getCustMobile() }, null, null,
				"id desc");
	}

	public Cursor selectAll() {
		return helper.getReadableDatabase().query(
				T_BROWSE_RECORDS,
				new String[] { "productId", "dictId1", "productDetailUrl", "picPath1Url", "brandName", "pattern",
						"colorSpec", "saleSpec", "configSpec", "isSpecial", "advicePrice", "custPrice", "isForesee",
						"provName", "advicePriceTitle", "sysCustPriceTitle", "custPriceTitle", "sysCustPrice",
                        "isGovEnterprise"},
				"custMobile = ?", new String[] { ZGApplication.getInstance().getUser().getCustMobile() }, null, null,
				"id desc");

	}

	/**
	 * 删除记录
	 * 
	 * @param productdetailhref
	 */
	public void delete(String productdetailhref) {
		// 删除该商品productid的记录
		helper.getWritableDatabase().delete(T_BROWSE_RECORDS, "productDetailUrl = ? and custMobile = ?",
				new String[] { productdetailhref, ZGApplication.getInstance().getUser().getCustMobile() });
	}

	public void deleteMinID() {
		// 最小ID的记录
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select min(id) id from " + T_BROWSE_RECORDS + "  where  custMobile="
						+ ZGApplication.getInstance().getUser().getCustMobile(), null);

		if (cursor.moveToNext() && cursor.getCount() == 1) {
			String id = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0]));
			helper.getWritableDatabase().delete(T_BROWSE_RECORDS, " id=?  and  custMobile = ? ",
					new String[] { id, ZGApplication.getInstance().getUser().getCustMobile() });
		}

		cursor.close();
	}

	// 删除所有游览记录
	public void deleteAll() {
		helper.getWritableDatabase().delete(T_BROWSE_RECORDS, null, null);
	}

	/**
	 * 删除记录
	 * 
	 * @param productBean
	 */
	public void update(ProductBean productBean) {
		ContentValues values = new ContentValues();
		values.put("productId", productBean.getProductId());
		values.put("dictId1", productBean.getDictId1());
		values.put("productDetailUrl", productBean.getProductDetailUrl());
		values.put("picPath1Url", productBean.getPicPath1Url());
		values.put("brandName", productBean.getBrandName());
		values.put("pattern", productBean.getPattern());
		values.put("colorSpec", productBean.getConfigSpec());
		values.put("saleSpec", productBean.getSaleSpec());
		values.put("configSpec", productBean.getConfigSpec());
		values.put("isSpecial", productBean.getIsSpecial());
		values.put("advicePrice", productBean.getAdvicePrice());
		values.put("custPrice", productBean.getCustPrice());
		values.put("isForesee", productBean.getIsForesee());
		values.put("provName", productBean.getProvName());
		values.put("advicePriceTitle", productBean.getAdvicePriceTitle());
		values.put("sysCustPriceTitle", productBean.getProductId());
		values.put("custPriceTitle", productBean.getCustPriceTitle());
		values.put("sysCustPrice", productBean.getSysCustPrice());
        values.put("isGovEnterprise",productBean.getIsGovEnterprise());
		String productdetailhref = productBean.getProductDetailUrl();
		// 事务开始
		helper.getWritableDatabase().beginTransaction();
		// 执行更新
		helper.getWritableDatabase().update(T_BROWSE_RECORDS, values, "productDetailUrl=? and custMobile=?",
				new String[] { productdetailhref, ZGApplication.getInstance().getUser().getCustMobile() });
		// 事务结束
		helper.getWritableDatabase().endTransaction();
	}

	public void close() {
		if (helper != null) {
			helper.close();
		}
	}

	private static class SqlLiteHelper extends SQLiteOpenHelper {

		// 构造函数
		SqlLiteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE  " + T_BROWSE_RECORDS
					+ "  (id integer PRIMARY KEY,custMobile text,productId text,dictId1 text,productDetailUrl text,"
					+ "picPath1Url text,brandName text,pattern text,colorSpec text,saleSpec text,configSpec text,"
					+ "isSpecial text,advicePrice text,custPrice text,isForesee text,provName text,advicePriceTitle text"
					+ "sysCustPriceTitle text,sysCustPriceTitle text,custPriceTitle text,sysCustPrice text," +
                    "isGovEnterprise text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(Constants.TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL(" DROP TABLE IF EXISTS  " + T_BROWSE_RECORDS);
			onCreate(db);
		}
	}

}