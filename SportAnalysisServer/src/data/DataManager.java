package data;

import java.io.IOException;
import java.util.HashMap;

import setting.ModeValues;
import setting.SettingValues;
import sub.Base64;
import sub.Debug;
import data.multiSensor.MultiSensorActionData;
import data.multiSensor.MultiSensorPersonalData;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;
import data.timecomp.TimeCompAnalysis;


/**
 * データオブジェクト管理クラス
 * @author OZAKI
 *
 */
public class DataManager {

	/**
	 * データを分析してパラメータを生成するクラス
	 */
	public static TimeCompAnalysis time_comp = new TimeCompAnalysis();


	/**
	 * スマホ個人データのハッシュマップ
	 */
	public static HashMap<String,SmartPhonePersonalData> personal_map = new HashMap<String,SmartPhonePersonalData>();

	/**
	 * センサ個人データのハッシュマップ
	 */
	public static HashMap<String,SensorPersonalData> sensor_personal_map = new HashMap<String,SensorPersonalData>();

	/**
	 * 複数センサ個人データのハッシュマップ
	 */
	public static HashMap<String,MultiSensorPersonalData> multi_sensor_personal_map = new HashMap<String,MultiSensorPersonalData>();


	/**
	 * スマホ運動データを指定されたユーザ名の個人データに設定し、現在設定した運動データオブジェクトを返却
	 * @param user_name 個人データのユーザ名
	 * @param data 運動データ
	 * @return
	 * @throws IOException
	 */
	public static SmartPhoneActionData create_smart_phone_data(String user_name,String mode,String data) throws IOException{
		Debug.debug_print("DataManager.create_smart_phone_data(String user_name,String mode,String data)",1);
		//受信したデータの動作データオブジェクトを生成
		SmartPhoneActionData received_data = new SmartPhoneActionData(SettingValues.ACC_DATA_NUM);
		received_data.setData(data);

		//ユーザ名が登録済かどうかチェック
		if(personal_map.containsKey(user_name)){
			//登録済の場合登録されたユーザのデータに運動データを設定
			personal_map.get(user_name).setdata(received_data);
		}else{
			//登録されていない場合ユーザのデータを新規作成して運動データを設定
			personal_map.put(user_name, new SmartPhonePersonalData(user_name,mode,new SmartPhoneActionData(SettingValues.STORE_DATA_NUM)));
			personal_map.get(user_name).setdata(received_data);
		}
		return received_data;
	}

	/**
	 * センサ運動データを指定されたユーザ名の個人データに設定し、現在設定した運動データオブジェクトを返却
	 * @param user_name 個人データのユーザ名
	 * @param data 運動データ
	 * @return
	 * @throws IOException
	 */
	public static SensorActionData create_sensor_data(String user_name,String mode,String data) throws IOException{
		Debug.debug_print("DataManager.create_sensor_data(String user_name,String mode,String data)",1);
		//受信したデータの動作データオブジェクトを生成
		SensorActionData received_data = new SensorActionData(SettingValues.ACC_DATA_NUM_S);
		received_data.setData(data);

		//ユーザ名が登録済かどうかチェック
		if(!sensor_personal_map.containsKey(user_name)){
			//登録されていない場合ユーザのデータを新規作成して運動データを設定
			sensor_personal_map.put(user_name, new SensorPersonalData(user_name,mode));
			sensor_personal_map.get(user_name).first_act_data.setFirstData(received_data);
		}
		return received_data;
	}

	/**
	 * 複数センサのデータをIDから判別して各オブジェクトにデータを設定
	 *
	 * @param user_name
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static MultiSensorActionData create_multi_sensor_data(String user_name,String mode,String data){
		Debug.debug_print("DataManager.set_sensor_data(String user_name,String mode,String data)",1);

		MultiSensorActionData received_data = new MultiSensorActionData(SettingValues.ACC_DATA_NUM_S);
		received_data.setMultiData(data,multi_sensor_personal_map.get(user_name).sensorDiffTimes);

		return received_data;

	}

	/**
	 * 複数センサ時のユーザデータを作成し、時間差分データを設定
	 * @param name
	 * @param data
	 */
	public static void set_multi_sensor_time_diff(String user_name, String data){
		Debug.debug_print("DataManager.set_multi_sensor_time_diff(String user_name, String data)",1);
		final int LONG_NUM=8;
		byte[] byte_data = null;
		try {
			byte_data = Base64.decode(data, Base64.URL_SAFE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int sensor_num = byte_data.length / 2 / LONG_NUM;

		if(!multi_sensor_personal_map.containsKey(user_name)){
			//登録されていない場合ユーザのデータを新規作成して運動データを設定
			multi_sensor_personal_map.put(user_name, new MultiSensorPersonalData(user_name,ModeValues.MULTI_SENSOR_COACH_MODE,sensor_num));
		}
		multi_sensor_personal_map.get(user_name).set_sensor_diff_times(byte_data);
	}

	/**
	 * ダミー処理用
	 * センサ運動データを指定されたユーザ名の個人データに設定し、現在設定した運動データオブジェクトを返却
	 * @param user_name 個人データのユーザ名
	 * @param data 運動データ
	 * @return
	 * @throws IOException
	 */
	public static SensorActionData create_sensor_data(String user_name,String mode,SensorActionData received_data) throws IOException{
		Debug.debug_print("DataManager.create_sensor_data(String user_name,String mode,String data)",1);


		//ユーザ名が登録済かどうかチェック
		if(!sensor_personal_map.containsKey(user_name)){
			//登録されていない場合ユーザのデータを新規作成して運動データを設定
			sensor_personal_map.put(user_name, new SensorPersonalData(user_name,mode));
			sensor_personal_map.get(user_name).first_act_data.setFirstData(received_data);
		}
		return received_data;
	}


}
