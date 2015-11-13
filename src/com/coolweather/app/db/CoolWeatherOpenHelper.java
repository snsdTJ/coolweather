/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��CoolWeatherOpenHelper.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����10:21:45 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.coolweather.app.R;

import android.content.Context;
import android.content.res.Resources;
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

	private Context context;
	
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
		this.context = context;
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
		Resources res = context.getResources();	
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = res.openRawResource(R.raw.manage_db);
			br = new BufferedReader(new InputStreamReader(is));
			String strLine = null;
			while((strLine = br.readLine())!=null){
				db.execSQL(strLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

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
