/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：HttpCallbackListener.java<p>
 *
 *  创建时间	：2015年11月12日 下午1:58:27 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.util;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 下午1:58:27 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 下午1:58:27 	修改人：
 *  	描述	:
 ************************************************************/
public interface HttpCallbackListener {
	void onFinish(String response);
	
	void onError(Exception e);
}
