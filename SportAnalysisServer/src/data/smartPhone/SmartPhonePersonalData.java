package data.smartPhone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import data.ActionData;
import data.PersonalData;
import data.sensor.SensorActionData;

import analysis.AnalysisManager;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;

public class SmartPhonePersonalData extends PersonalData{

	
	/**
	 * �X�}�z�^���f�[�^(���f�[�^)
	 */
	public SmartPhoneActionData act_data;

	/**
	 * �X�}�z�^���f�[�^�i���Ԑ��K����j�̔z��
	 */
	public SmartPhoneActionData[] act_data_normalized_array = new SmartPhoneActionData[256];
	
	/**
	 * �X�}�z�^���p�����[�^
	 */
	public SmartPhoneActionParameter act_para = new SmartPhoneActionParameter();

	/**
	 * ��r�Ώۂ̃X�}�z�^���p�����[�^
	 */
	public SmartPhoneActionParameter compare_act_para = new SmartPhoneActionParameter();

	/**
	 * �^���f�[�^�i���Ԑ��K����j�̍ŐV�ʐM�i���o�[
	 */
	public int normalized_data_commu_num = 0;
	
	/**
	 * �^���f�[�^�i���Ԑ��K����j�o�̓e�L�X�g�C���f�b�N�X
	 */
	public int txt_index = 0;
	
	/**
	 * �l�f�[�^�I�u�W�F�N�g�R���X�g���N�^
	 * @param user_name ���[�U��
	 * @param act_data �^���f�[�^
	 */
	public SmartPhonePersonalData(String name, String coach_mode,SmartPhoneActionData act_data){
		super(name,coach_mode);
		this.act_data = act_data;
	}

	
	/**
	 * �X�}�z�^���f�[�^�ɒl��ݒ�B��萔�𒴂����番�́B�ő吔�𒴂�����^���f�[�^�����������ăe�L�X�g�o�́B
	 * @param set_act_data
	 */
	public void setdata(SmartPhoneActionData set_act_data){
		Debug.debug_print("SmartPhonePersonalData.setdata(ActionData set_act_data)",1);

		//�^���f�[�^�������ς��ɂȂ�����e�L�X�g���o�͂��ď�����
		if(act_data.getIndex()+set_act_data.getIndex()>act_data.getMax_index()){
			//�e�L�X�g���o��
			if(OutPutText.output_txt("actdata",act_data.toString(),txt_index,false))txt_index++;
			//�^���f�[�^��������
			act_data = new SmartPhoneActionData(SettingValues.STORE_DATA_NUM);
		}
		
		//�擾�����^���f�[�^�����̃��[�U�̉^���f�[�^�ɐݒ�
		act_data.setData(set_act_data);
		
		//Analysis�ɋ@�\���ڂ����̂ō폜���邱��
		set_now_position();
	}
	
	
	/**
	 * ���[�U�̉^���f�[�^��ԋp
	 * @return
	 */
	public ActionData getSmartPhoneActionData(){
		return act_data;
	}
	
	/**
	 * ���݈ʒu���^���p�����[�^�ɐݒ�
	 */
	public void set_now_position(){
		act_para.setNow_latitude(act_data.getNowLatitude());
		act_para.setNow_longitude(act_data.getNowLongitude());
	}
}
