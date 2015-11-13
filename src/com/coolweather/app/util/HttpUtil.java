/************************************************************
 *	版权所有  (c)2011,   hxf<p>	
 *  文件名称	：HttpUtil.java<p>
 *
 *  创建时间	：2015年11月12日 下午1:28:20 
 *  当前版本号：v1.0
 ************************************************************/
package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.sax.StartElementListener;
import android.util.Log;

/************************************************************
 *  内容摘要	：<p>
 *
 *  作者	：hxf
 *  创建时间	：2015年11月12日 下午1:28:20 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2015年11月12日 下午1:28:20 	修改人：
 *  	描述	:
 ************************************************************/
public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			public void run() {
				HttpURLConnection connection = null;
				
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line = null;
					while((line = br.readLine())!=null){
						response.append(line);
					}
					Log.i("response", response.toString());
					System.out.println(response.toString());
					if (listener!=null) {
						//回调onFinish（）方法
						listener.onFinish(response.toString());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if (listener!=null) {
						//回调onError（）方法
						listener.onError(e);
					}
					System.out.println("异常");
				}finally {
					if (connection!=null) {
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
}
