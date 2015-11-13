/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：AutoUpdateService.java<p>
 *
 *  创建时间	：2015年11月13日 下午6:18:24 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.service;

import com.coolweather.app.activity.WeatherAcitivty;
import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/************************************************************
 * 内容摘要 ：这个服务Service是每隔8小时，就从服务器上下载，SharedPreferences中所记载的县，的天气代码，所对应的天气信息，
 *          并保存在SharedPreferences里面
 *         由于打开应用时是从SharedPreferences中读取天气信息，所有这就可以保持天气信息比较新
 *
 * 作者 ：hxf 创建时间 ：2015年11月13日 下午6:18:24 当前版本号：v1.0 历史记录 : 日期 : 2015年11月13日
 * 下午6:18:24 修改人： 描述 :
 ************************************************************/
public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		new Thread(new Runnable() {
			public void run() {
				updateWeather();//其实这个方法只是： 把天气信息下载，并保存在SharedPreferences中
			}
		}).start();
		
		//每隔8小时就发送一条广播
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 函数名称 : updateWeather 功能描述 : 参数及返回值说明：
	 *
	 * 这个方法只是把天气信息下载，并保存在SharedPreferences中
	 *   
	 */
	protected void updateWeather() {
		// TODO Auto-generated method stub
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = sp.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				
					// 处理服务器返回的天气信息，并保存在SharedPreferences中
					Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}

			@Override
			public void onError(Exception e) {

			}
		});
	}

}
