/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：Utility.java<p>
 *
 *  创建时间	：2015年11月12日 下午6:41:51 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.util;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

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
	
	
	
	
	
	
	
	
	
	
	
	
	

}
