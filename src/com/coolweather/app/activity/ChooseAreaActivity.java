/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��ChooseAreaActivity.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����7:07:37 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.db.CoolWeatherOpenHelper;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/************************************************************
 * ����ժҪ ��
 * <p>
 *
 * ���� ��hxf ����ʱ�� ��2015��11��12�� ����7:07:37 ��ǰ�汾�ţ�v1.0 ��ʷ��¼ : ���� : 2015��11��12��
 * ����7:07:37 �޸��ˣ� ���� :
 ************************************************************/
public class ChooseAreaActivity extends Activity {
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	/*
	 * ʡ�б�
	 */
	private List<Province> provinceList;
	/*
	 * ʡ�б�
	 */
	private List<City> cityList;
	/*
	 * ʡ�б�
	 */
	private List<County> countyList;

	/*
	 * ѡ�е�ʡ��
	 */
	private Province selectedProvince;
	/*
	 * ѡ�еĳ���
	 */
	private City selectedCity;

	/*
	 * ��ǰѡ�еļ���
	 */
	private int currentLevel;
	
	/*
	 * �Ƿ��WeatherActivity����ת����
	 */
	private boolean isFromWeaterActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		isFromWeaterActivity = getIntent().getBooleanExtra("from_weather_activity", false);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//�Ѿ�ѡ���˳����в��Ǵ�WeatherActivity��ת�������Ż�ֱ����ת��WeatherActivity
		if (prefs.getBoolean("city_selected", false)&&!isFromWeaterActivity) {
			Intent intent = new Intent(this, WeatherAcitivty.class);
			startActivity(intent);
			finish();
			return;
		}
		
		
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		// ��ʼ�����
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.text_view);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);

		// ����ListView�ĵ���¼�
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(position);
					queryCities();

				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounties();
				}else if (currentLevel == LEVEL_COUNTY) {
					String countyCode = countyList.get(position).getCountyCode();
					Intent intent = new Intent(ChooseAreaActivity.this, WeatherAcitivty.class);
					intent.putExtra("county_code", countyCode);
					startActivity(intent);
					finish();
				}

			}
		});

		// ����ʡ������
		queryProvinces();
	}

	/**
	 * �������� : queryFromServer �������� : ���ݴ���Ĵ��ź����ʹӷ������ϲ�ѯʡ���� ���ص����� ��һ�������Ǵ��ţ� �ڶ���������
	 * ����
	 */
	private void queryFromServer(String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {// ����ѯ�л���ʱ
			address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";// ����ѯʡʱ
		}
		Log.i("tag1", "tag1");
		showProgressDialog();

		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				/*
				 * �Ѵӷ����������ص����ݴ������ݿ�
				 */
				boolean result = false;
				if ("province".equals(type)) {// ������response�������ݿ���Ӧ�ı�
					result = Utility.handleProvincesResponse(coolWeatherDB, response);
				} else if ("city".equals(type)) {// ������response�������ݿ���Ӧ�ı�
					result = Utility.handleCityiesResponse(coolWeatherDB, response, selectedProvince.getId());
				} else if ("county".equals(type)) {// ������response�������ݿ���Ӧ�ı�
					result = Utility.handleCountiesResponse(coolWeatherDB, response, selectedCity.getId());
				}

				/*
				 * �����һ���洢�������ݿ�ɹ��Ļ�����ô�ʹ����ݿ��ж�ȡ����
				 */
				if (result) {
					// Activity.runOnUiThread(Runnable action)
					// ��Activity���һ���������÷������װ��Handler��
					// ������action�����߳�������
					// ͨ��runOnUiThread���������ص����̴߳����߼�
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							// ��Ȼ�Ѿ������ݴ������ô�Ϳ��Դӱ��ж�ȡ�����ݸ�ListView������Դ
							if ("province".equals(type)) {
								queryProvinces();
							} else if ("city".equals(type)) {
								queryCities();
							} else if ("county".equals(type)) {
								queryCounties();
							}
						}
					});
				}

			}

			@Override
			public void onError(Exception e) {
				// ͨ��runOnUiThread���������ص����̴߳����߼�
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});

	}

	/**
	 * �������� : showProgressDialog �������� : ��ʾ���ȶԻ���
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("���ڼ��� . . . ");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/*
	 * �رս������Ի���
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/*
	 * ����Back���������ݵ�ǰ�ļ������жϣ���ʱӦ�÷������б�ʡ�б�����ֱ���˳�
	 */
	/**
	 * �������� ��onBackPressed �������� �� ����˵�� �� ����ֵ��
	 * 
	 * �޸ļ�¼�� ���� ��2015��11��12�� ����8:59:12 �޸��ˣ�hxf ���� ��
	 * 
	 */
	@Override
	public void onBackPressed() {
		if (currentLevel == LEVEL_COUNTY) {
			queryCities();
		} else if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		} else {
			if (isFromWeaterActivity) {
				Intent intent = new Intent(this, WeatherAcitivty.class);
				startActivity(intent);
			}
			finish();
		}
	}

	/**
	 * �������� : queryCounties �������� : ��ѯѡ���������е��أ����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 */
	protected void queryCounties() {
		countyList = coolWeatherDB.loadCounties(selectedCity.getId());
		if (countyList.size() > 0) {
			dataList.clear();
			for (County c : countyList) {
				dataList.add(c.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			queryFromServer(selectedCity.getCityCode(), "county");
		}
	}

	/**
	 * �������� : queryCities �������� : ��ѯѡ��ʡ�����е��У����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 * Ȼ�����ListView������Դ����ˢ�¡�
	 */
	protected void queryCities() {

		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City c : cityList) {
				dataList.add(c.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvinceCode(), "city");
		}

	}

	/**
	 * �������� : queryProvinces �������� : ��ѯȫ�����е�ʡ�����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 */
	private void queryProvinces() {

		provinceList = coolWeatherDB.loadProvince();
		Log.i("tag2", ""+provinceList.size());
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province p : provinceList) {
				dataList.add(p.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);// ��֪��ʲô��˼
			titleText.setText("�й�");
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}

	}
	


}
