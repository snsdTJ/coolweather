/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��AutoUpdateReceiver.java<p>
 *
 *  ����ʱ��	��2015��11��13�� ����6:43:46 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.receiver;

import com.coolweather.app.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��13�� ����6:43:46 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��13�� ����6:43:46 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class AutoUpdateReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {//���intent�ǽ��յ���intent������������㲥��������
		
		Intent serviceIntent = new Intent(context, AutoUpdateService.class);
		context.startService(serviceIntent);

	}

}
