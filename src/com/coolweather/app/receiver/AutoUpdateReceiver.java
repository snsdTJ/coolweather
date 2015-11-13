/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AutoUpdateReceiver.java<p>
 *
 *  创建时间	：2015年11月13日 下午6:43:46 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.receiver;

import com.coolweather.app.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月13日 下午6:43:46 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月13日 下午6:43:46 	修改人：
 *  	描述	:
 ************************************************************/
public class AutoUpdateReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {//这个intent是接收到的intent，用于向这个广播传送数据
		
		Intent serviceIntent = new Intent(context, AutoUpdateService.class);
		context.startService(serviceIntent);

	}

}
