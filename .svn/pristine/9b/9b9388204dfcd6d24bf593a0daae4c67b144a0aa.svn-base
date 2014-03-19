package com.digione.zgb2b.utils;

import android.content.Context;

public interface PersistentService {
	/**
	 * 通过XML保存本地数据
	 * 
	 * @param context
	 * @param fileName
	 *            指定一个xml文件名
	 * @param key
	 *            指定数据的key
	 * @param value
	 *            数据的值
	 */
	public void saveBySharePreference(Context context, String fileName, String key, String value);

	/**
	 * 从xml中读取保存的本地数据
	 * 
	 * @param context
	 * @param fileName
	 *            指定读取的xml文件名
	 * @param key
	 *            指定数据的key
	 * @return
	 */
	public String getStringBySharePreference(Context context, String fileName, String key);

	/**
	 * 从xml中清空本地数据
	 * 
	 * @param context
	 * @param fileName
	 *            指定清空的xml文件名
	 * @return
	 */
	public void clearAllDataSharePreference(Context context, String fileName);

	/**
	 * 从xml中清空本地数据
	 * 
	 * @param context
	 * @param fileName
	 *            指定清空的xml文件名
	 * @param key
	 *            指定数据的key
	 * @return
	 */
	public void clearSharePreference(Context context, String fileName, String key);

}
