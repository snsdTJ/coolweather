/************************************************************
 *	��Ȩ����  (c)2011,   hxf<p>	
 *  �ļ�����	��County.java<p>
 *
 *  ����ʱ��	��2015��11��12�� ����10:49:10 
 *  ��ǰ�汾�ţ�v1.0
 ************************************************************/
package com.coolweather.app.model;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��hxf
 *  ����ʱ��	��2015��11��12�� ����10:49:10 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2015��11��12�� ����10:49:10 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class County {
	
	private int id;
	
	private String countyName;
	
	private String countyCode;
	
	private int cityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	

}
