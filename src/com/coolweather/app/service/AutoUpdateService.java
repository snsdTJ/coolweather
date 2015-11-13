/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AutoUpdateService.java<p>
 *
 *  ����ʱ��	��2015��11��13�� ����6:18:24 
 *  ��ǰ�汾�ţ�v1.0
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
 * ����ժҪ ���������Service��ÿ��8Сʱ���ʹӷ����������أ�SharedPreferences�������ص��أ����������룬����Ӧ��������Ϣ��
 *          ��������SharedPreferences����
 *         ���ڴ�Ӧ��ʱ�Ǵ�SharedPreferences�ж�ȡ������Ϣ��������Ϳ��Ա���������Ϣ�Ƚ���
 *
 * ���� ��hxf ����ʱ�� ��2015��11��13�� ����6:18:24 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��11��13��
 * ����6:18:24 �޸��ˣ� ���� :
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
				updateWeather();//��ʵ�������ֻ�ǣ� ��������Ϣ���أ���������SharedPreferences��
			}
		}).start();
		
		//ÿ��8Сʱ�ͷ���һ���㲥
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000; // ����8Сʱ�ĺ�����
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * �������� : updateWeather �������� : ����������ֵ˵����
	 *
	 * �������ֻ�ǰ�������Ϣ���أ���������SharedPreferences��
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
				
					// ������������ص�������Ϣ����������SharedPreferences��
					Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}

			@Override
			public void onError(Exception e) {

			}
		});
	}

}
