/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��HttpCallbackListener.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����1:58:27 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.util;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��12�� ����1:58:27 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��12�� ����1:58:27 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public interface HttpCallbackListener {
	void onFinish(String response);
	
	void onError(Exception e);
}