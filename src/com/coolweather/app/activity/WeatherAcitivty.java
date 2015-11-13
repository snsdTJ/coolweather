/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��WeatherAcitivty.java<p>
 *
 *  ����ʱ��	��2015��11��13�� ����11:24:56 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.activity;


import com.coolweather.app.R;
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��13�� ����11:24:56 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��13�� ����11:24:56 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class WeatherAcitivty extends Activity implements OnClickListener {
	
	private LinearLayout weatherInfoLayout;
	
	private TextView cityNameText;//������ʾ������
	
	private TextView publishText;//������ʾ����ʱ��
	
	private TextView weatherDespText;//������ʾ����������Ϣ
	
	private TextView temp1Text;//������ʾ����1
	
	private TextView temp2Text;//������ʾ����2
	
	private TextView currentDateText;//������ʾ��ǰ����
	
	private Button switchCity;//�л�����
	
	private Button refreshWeather;//����������ť
	
	
	/**
	 *  �������� ��onCreate
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param savedInstanceState
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��11��13�� ����11:30:32	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.weather_layout);
		
		//��ʼ�����ؼ�
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
			//���ؼ�����ʱ��ȥ��ѯ����
			publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}else {
			//û���ؼ����ž�ֱ����ʾ��������
			showWeather();
		}
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

	}
	

	
	/**
	 *  �������� : queryWeatherCode
	 *  �������� :  ��ѯ�ؼ���������Ӧ����������
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
			publishText.setText("ͬ����...");
			//��Ҫˢ������ʱ�����Դ�֮ǰ������SharedPreferences�е�weatherCodeȡ����Ȼ�����������ȥ�����������������
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
	 *  �������� : queryWeatherInfo
	 *  �������� : ��ѯ������������Ӧ������ 
	 */
	private void queryWeatherInfo(String weatherCode) {
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address,"weatherCode");
		
	}



	/**
	 *  �������� : queryFromServer
	 *  �������� : ���ݴ���ĵ�ַ������ȥ���������ѯ�������Ż���������Ϣ 
	 */
	private void queryFromServer(final String address, final String type) {
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if ("countyCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						//�ӷ��������ص������н�������������
						String[] array = response.split("\\|");
						if (array!=null&array.length==2) {
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if ("weatherCode".equals(type)) {
					//�������������ص�������Ϣ
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
						publishText.setText("ͬ��ʧ��");
					}
				});
				
			}
		});
	}
	
	/*
	 * ��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ�ڽ�����
	 * 
	 */
	private void showWeather(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("����" + prefs.getString("publish_time", "")+"����");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}

	
}