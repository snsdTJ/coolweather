/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��CoolWeatherOpenHelper.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����10:21:45 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��12�� ����10:21:45 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��12�� ����10:21:45 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	
	/**
	 * ���캯����CoolWeatherOpenHelper
	 * ��������:
	 * ����˵����
	 * 		@param context
	 * 		@param name
	 * 		@param factory
	 * 		@param version
	 */
	public CoolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/**
	 *  �������� ��onCreate
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param db
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��11��12�� ����10:21:46	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	/**
	 *  �������� ��onUpgrade
	 *  �������� ��  
	 *  ����˵�� ��
	 *  	@param db
	 *  	@param oldVersion
	 *  	@param newVersion
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2015��11��12�� ����10:21:46	�޸��ˣ�hxf
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
