/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：City.java<p>
 *
 *  创建时间	：2015年11月12日 上午10:47:53 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 上午10:47:53 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 上午10:47:53 	修改人：
 *  	描述	:
 ************************************************************/
public class City {
	
	private int id;
	
	private String cityName;
	
	private String cityCode;
	
	private int provinceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	
	

}
