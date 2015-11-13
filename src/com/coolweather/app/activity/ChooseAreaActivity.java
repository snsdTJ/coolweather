/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：ChooseAreaActivity.java<p>
 *
 *  创建时间	：2015年11月12日 下午7:07:37 
 *  当前版本号：v1.0
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
 * 内容摘要 ：
 * <p>
 *
 * 作者 ：hxf 创建时间 ：2015年11月12日 下午7:07:37 当前版本号：v1.0 历史记录 : 日期 : 2015年11月12日
 * 下午7:07:37 修改人： 描述 :
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
	 * 省列表
	 */
	private List<Province> provinceList;
	/*
	 * 省列表
	 */
	private List<City> cityList;
	/*
	 * 省列表
	 */
	private List<County> countyList;

	/*
	 * 选中的省份
	 */
	private Province selectedProvince;
	/*
	 * 选中的城市
	 */
	private City selectedCity;

	/*
	 * 当前选中的级别
	 */
	private int currentLevel;
	
	/*
	 * 是否从WeatherActivity中跳转过来
	 */
	private boolean isFromWeaterActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		isFromWeaterActivity = getIntent().getBooleanExtra("from_weather_activity", false);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//已经选择了城市切不是从WeatherActivity跳转过来，才会直接跳转到WeatherActivity
		if (prefs.getBoolean("city_selected", false)&&!isFromWeaterActivity) {
			Intent intent = new Intent(this, WeatherAcitivty.class);
			startActivity(intent);
			finish();
			return;
		}
		
		
		
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		// 初始化组件
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.text_view);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);

		// 设置ListView的点击事件
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

		// 加载省级数据
		queryProvinces();
	}

	/**
	 * 函数名称 : queryFromServer 功能描述 : 根据传入的代号和类型从服务器上查询省，市 或县的数据 第一个参数是代号， 第二个参数是
	 * 类型
	 */
	private void queryFromServer(String code, final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {// 当查询市或县时
			address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";// 当查询省时
		}
		Log.i("tag1", "tag1");
		showProgressDialog();

		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				/*
				 * 把从服务器上下载的数据存入数据库
				 */
				boolean result = false;
				if ("province".equals(type)) {// 把数据response存入数据库相应的表
					result = Utility.handleProvincesResponse(coolWeatherDB, response);
				} else if ("city".equals(type)) {// 把数据response存入数据库相应的表
					result = Utility.handleCityiesResponse(coolWeatherDB, response, selectedProvince.getId());
				} else if ("county".equals(type)) {// 把数据response存入数据库相应的表
					result = Utility.handleCountiesResponse(coolWeatherDB, response, selectedCity.getId());
				}

				/*
				 * 如果上一步存储数据数据库成功的话，那么就从数据库中读取数据
				 */
				if (result) {
					// Activity.runOnUiThread(Runnable action)
					// 是Activity类的一个方法，该方法里封装了Handler，
					// 用于让action在主线程中运行
					// 通过runOnUiThread（）方法回到主线程处理逻辑
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							// 既然已经把数据存入表，那么就可以从表中读取出数据给ListView的数据源
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
				// 通过runOnUiThread（）方法回到主线程处理逻辑
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});

	}

	/**
	 * 函数名称 : showProgressDialog 功能描述 : 显示进度对话框
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载 . . . ");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/*
	 * 关闭进度条对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/*
	 * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表，省列表，还是直接退出
	 */
	/**
	 * 函数名称 ：onBackPressed 功能描述 ： 参数说明 ： 返回值：
	 * 
	 * 修改记录： 日期 ：2015年11月12日 下午8:59:12 修改人：hxf 描述 ：
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
	 * 函数名称 : queryCounties 功能描述 : 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
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
	 * 函数名称 : queryCities 功能描述 : 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
	 * 然后更新ListView的数据源，并刷新。
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
	 * 函数名称 : queryProvinces 功能描述 : 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
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
			listView.setSelection(0);// 不知道什么意思
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			queryFromServer(null, "province");
		}

	}
	


}
