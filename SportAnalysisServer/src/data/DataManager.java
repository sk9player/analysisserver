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
 * �f�[�^�I�u�W�F�N�g�Ǘ��N���X
 * @author OZAKI
 *
 */
public class DataManager {

	/**
	 * �f�[�^�𕪐͂��ăp�����[�^�𐶐�����N���X
	 */
	public static TimeCompAnalysis time_comp = new TimeCompAnalysis();


	/**
	 * �X�}�z�l�f�[�^�̃n�b�V���}�b�v
	 */
	public static HashMap<String,SmartPhonePersonalData> personal_map = new HashMap<String,SmartPhonePersonalData>();

	/**
	 * �Z���T�l�f�[�^�̃n�b�V���}�b�v
	 */
	public static HashMap<String,SensorPersonalData> sensor_personal_map = new HashMap<String,SensorPersonalData>();

	/**
	 * �����Z���T�l�f�[�^�̃n�b�V���}�b�v
	 */
	public static HashMap<String,MultiSensorPersonalData> multi_sensor_personal_map = new HashMap<String,MultiSensorPersonalData>();


	/**
	 * �X�}�z�^���f�[�^���w�肳�ꂽ���[�U���̌l�f�[�^�ɐݒ肵�A���ݐݒ肵���^���f�[�^�I�u�W�F�N�g��ԋp
	 * @param user_name �l�f�[�^�̃��[�U��
	 * @param data �^���f�[�^
	 * @return
	 * @throws IOException
	 */
	public static SmartPhoneActionData create_smart_phone_data(String user_name,String mode,String data) throws IOException{
		Debug.debug_print("DataManager.create_smart_phone_data(String user_name,String mode,String data)",1);
		//��M�����f�[�^�̓���f�[�^�I�u�W�F�N�g�𐶐�
		SmartPhoneActionData received_data = new SmartPhoneActionData(SettingValues.ACC_DATA_NUM);
		received_data.setData(data);

		//���[�U�����o�^�ς��ǂ����`�F�b�N
		if(personal_map.containsKey(user_name)){
			//�o�^�ς̏ꍇ�o�^���ꂽ���[�U�̃f�[�^�ɉ^���f�[�^��ݒ�
			personal_map.get(user_name).setdata(received_data);
		}else{
			//�o�^����Ă��Ȃ��ꍇ���[�U�̃f�[�^��V�K�쐬���ĉ^���f�[�^��ݒ�
			personal_map.put(user_name, new SmartPhonePersonalData(user_name,mode,new SmartPhoneActionData(SettingValues.STORE_DATA_NUM)));
			personal_map.get(user_name).setdata(received_data);
		}
		return received_data;
	}

	/**
	 * �Z���T�^���f�[�^���w�肳�ꂽ���[�U���̌l�f�[�^�ɐݒ肵�A���ݐݒ肵���^���f�[�^�I�u�W�F�N�g��ԋp
	 * @param user_name �l�f�[�^�̃��[�U��
	 * @param data �^���f�[�^
	 * @return
	 * @throws IOException
	 */
	public static SensorActionData create_sensor_data(String user_name,String mode,String data) throws IOException{
		Debug.debug_print("DataManager.create_sensor_data(String user_name,String mode,String data)",1);
		//��M�����f�[�^�̓���f�[�^�I�u�W�F�N�g�𐶐�
		SensorActionData received_data = new SensorActionData(SettingValues.ACC_DATA_NUM_S);
		received_data.setData(data);

		//���[�U�����o�^�ς��ǂ����`�F�b�N
		if(!sensor_personal_map.containsKey(user_name)){
			//�o�^����Ă��Ȃ��ꍇ���[�U�̃f�[�^��V�K�쐬���ĉ^���f�[�^��ݒ�
			sensor_personal_map.put(user_name, new SensorPersonalData(user_name,mode));
			sensor_personal_map.get(user_name).first_act_data.setFirstData(received_data);
		}
		return received_data;
	}

	/**
	 * �����Z���T�̃f�[�^��ID���画�ʂ��Ċe�I�u�W�F�N�g�Ƀf�[�^��ݒ�
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
	 * �����Z���T���̃��[�U�f�[�^���쐬���A���ԍ����f�[�^��ݒ�
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
			//�o�^����Ă��Ȃ��ꍇ���[�U�̃f�[�^��V�K�쐬���ĉ^���f�[�^��ݒ�
			multi_sensor_personal_map.put(user_name, new MultiSensorPersonalData(user_name,ModeValues.MULTI_SENSOR_COACH_MODE,sensor_num));
		}
		multi_sensor_personal_map.get(user_name).set_sensor_diff_times(byte_data);
	}

	/**
	 * �_�~�[�����p
	 * �Z���T�^���f�[�^���w�肳�ꂽ���[�U���̌l�f�[�^�ɐݒ肵�A���ݐݒ肵���^���f�[�^�I�u�W�F�N�g��ԋp
	 * @param user_name �l�f�[�^�̃��[�U��
	 * @param data �^���f�[�^
	 * @return
	 * @throws IOException
	 */
	public static SensorActionData create_sensor_data(String user_name,String mode,SensorActionData received_data) throws IOException{
		Debug.debug_print("DataManager.create_sensor_data(String user_name,String mode,String data)",1);


		//���[�U�����o�^�ς��ǂ����`�F�b�N
		if(!sensor_personal_map.containsKey(user_name)){
			//�o�^����Ă��Ȃ��ꍇ���[�U�̃f�[�^��V�K�쐬���ĉ^���f�[�^��ݒ�
			sensor_personal_map.put(user_name, new SensorPersonalData(user_name,mode));
			sensor_personal_map.get(user_name).first_act_data.setFirstData(received_data);
		}
		return received_data;
	}

	public static long get_last_time(String user_name){
		return sensor_personal_map.get(user_name).act_para.getLast_time();
	}
}
