/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：WeatherAcitivty.java<p>
 *
 *  创建时间	：2015年11月13日 上午11:24:56 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.activity;


import com.coolweather.app.R;
import com.coolweather.app.service.AutoUpdateService;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月13日 上午11:24:56 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月13日 上午11:24:56 	修改人：
 *  	描述	:
 ************************************************************/
public class WeatherAcitivty extends Activity implements OnClickListener {
	
	private LinearLayout weatherInfoLayout;
	
	private TextView cityNameText;//用于显示城市名
	
	private TextView publishText;//用于显示发布时间
	
	private TextView weatherDespText;//用于显示天气描述信息
	
	private TextView temp1Text;//用于显示气温1
	
	private TextView temp2Text;//用于显示气温2
	
	private TextView currentDateText;//用于显示当前日期
	
	private Button switchCity;//切换城市
	
	private Button refreshWeather;//更新天气按钮
	
	
	/**
	 *  函数名称 ：onCreate
	 *  功能描述 ：  
	 *  参数说明 ：
	 *  	@param savedInstanceState
	 *  返回值：
	 *  	
	 *  修改记录：
	 *  日期 ：2015年11月13日 上午11:30:32	修改人：hxf
	 *  描述 ：
	 * 					
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.weather_layout);
		
		//初始化各控件
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		switchCity = (Button) findViewById(R.id.switch_city);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);
		String countyCode = getIntent().getStringExtra("county_code");
		if (!TextUtils.isEmpty(countyCode)) {
			//有县级代号时就去查询天气
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}else {
			//没有县级代号就直接显示本地天气
			showWeather();
		}
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

	}
	

	
	/**
	 *  函数名称 : queryWeatherCode
	 *  功能描述 :  查询县级代号所对应的天气代号
	 * 					
	 */
	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,"countyCode");
		
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("同步中...");
			//需要刷新天气时，可以从之前保存在SharedPreferences中的weatherCode取出，然后用这个代码去请求服务器天气数据
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("weather_code", "");
			 if (!TextUtils.isEmpty(weatherCode)) {
				queryWeatherInfo(weatherCode);
			}
			break;

		default:
			break;
		}
		
	}



	/**
	 *  函数名称 : queryWeatherInfo
	 *  功能描述 : 查询天气代号所对应的天气 
	 */
	private void queryWeatherInfo(String weatherCode) {
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address,"weatherCode");
		
	}



	/**
	 *  函数名称 : queryFromServer
	 *  功能描述 : 根据传入的地址和类型去向服务器查询天气代号或者天气信息 
	 */
	private void queryFromServer(final String address, final String type) {
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if ("countyCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						//从服务器返回的数据中解析出天气代号
						String[] array = response.split("\\|");
						if (array!=null&array.length==2) {
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if ("weatherCode".equals(type)) {
					//处理服务器返回的天气信息
					Utility.handleWeatherResponse(WeatherAcitivty.this, response);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							showWeather();
							
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						publishText.setText("同步失败");
					}
				});
				
			}
		});
	}
	
	/*
	 * 从SharedPreferences文件中读取存储的天气信息，并显示在界面上
	 * 
	 */
	private void showWeather(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("今天" + prefs.getString("publish_time", "")+"发布");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		
		//只要一旦选中了某个城市并成功更新天气之后，AutoUpdateService就会一直在后台运行，并保证没8小时更新一次天气
		Intent serviceIntent = new Intent(this, AutoUpdateService.class);
		startService(serviceIntent);
	}

	
}
