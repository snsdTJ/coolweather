/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：CoolWeatherOpenHelper.java<p>
 *
 *  创建时间	：2015年11月12日 上午10:21:45 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.coolweather.app.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 上午10:21:45 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 上午10:21:45 	修改人：
 *  	描述	:
 ************************************************************/
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	private Context context;
	
	/**
	 * 构造函数：CoolWeatherOpenHelper
	 * 函数功能:
	 * 参数说明：
	 * 		@param context
	 * 		@param name
	 * 		@param factory
	 * 		@param version
	 */
	public CoolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 *  函数名称 ：onCreate
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param db
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年11月12日 上午10:21:46	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Resources res = context.getResources();	
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = res.openRawResource(R.raw.manage_db);
			br = new BufferedReader(new InputStreamReader(is));
			String strLine = null;
			while((strLine = br.readLine())!=null){
				db.execSQL(strLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 *  函数名称 ：onUpgrade
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param db
	 *  	@param oldVersion
	 *  	@param newVersion
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年11月12日 上午10:21:46	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
