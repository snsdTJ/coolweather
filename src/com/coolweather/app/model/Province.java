/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：Province.java<p>
 *
 *  创建时间	：2015年11月12日 上午10:43:40 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.model;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 上午10:43:40 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 上午10:43:40 	修改人：
 *  	描述	:
 ************************************************************/
public class Province {
	
	private int id;
	
	private String provinceName;
	
	private String provinceCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	

}
