/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：County.java<p>
 *
 *  创建时间	：2015年11月12日 上午10:49:10 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 上午10:49:10 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 上午10:49:10 	修改人：
 *  	描述	:
 ************************************************************/
public class County {
	
	private int id;
	
	private String countyName;
	
	private String countyCode;
	
	private int cityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	

}
