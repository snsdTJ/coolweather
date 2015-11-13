/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��HttpUtil.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����1:28:20 
 *  ��ǰ�汾�ţ�v1.0
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
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��12�� ����1:28:20 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��12�� ����1:28:20 	�޸��ˣ�
 *  	����	:
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
						//�ص�onFinish��������
						listener.onFinish(response.toString());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					if (listener!=null) {
						//�ص�onError��������
						listener.onError(e);
					}
					System.out.println("�쳣");
				}finally {
					if (connection!=null) {
						connection.disconnect();
					}
				}
				
			}
		}).start();
	}
}
