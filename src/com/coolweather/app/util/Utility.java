/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：Utility.java<p>
 *
 *  创建时间	：2015年11月12日 下午6:41:51 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 下午6:41:51 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 下午6:41:51 	修改人：
 *  	描述	:
 ************************************************************/
public class Utility {
	
	/*
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces!=null&allProvinces.length>0) {
				for(String p:allProvinces){
					Log.i("allProvinces", ""+p);
					String[] provinceArray = p.split("\\|");
					Log.i("provinceArray", ""+provinceArray.length);
					Province province = new Province();
					province.setProvinceCode(provinceArray[0]);
					province.setProvinceName(provinceArray[1]);
					//将解析出来的数据存储到Province表
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCityiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities!=null&allCities.length>0) {
				for(String c:allCities){
					String[] cityArray = c.split("\\|");
					City city = new City();
					city.setCityCode(cityArray[0]);
					city.setCityName(cityArray[1]);
					city.setProvinceId(provinceId);
					//将解析出来的数据存储到City表
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties!=null&allCounties.length>0) {
				for(String c:allCounties){
					String[] countyArray = c.split("\\|");
					County county = new County();
					county.setCountyCode(countyArray[0]);
					county.setCountyName(countyArray[1]);
					county.setCityId(cityId);
					//将解析出来的数据存储到County表
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * 解析服务器返回的JSON数据， 并将解析出的数据存储到本地
	 */
	public static void handleWeatherResponse(Context context, String response){
		
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中
	 */
	public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1,
			String temp2, String weatherDesp, String publishTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);//注意Locale.CHINA
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	
	}
	
	
	
	
	
	
	
	
	
	

}
