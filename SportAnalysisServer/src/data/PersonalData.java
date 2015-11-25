package data;

import data.ActionData;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;

public class PersonalData {

	/**
	 * ���[�U��
	 */
	public String name="";
	

	/**
	 * ��������
	 */
	public long first_time=0;
	

	
	
	/**
	 * �R�[�`���O���[�h
	 */
	public int coach_mode = ModeValues.NO_COACH_MODE;

	
	/**
	 * �l�f�[�^�I�u�W�F�N�g�R���X�g���N�^
	 * @param user_name ���[�U��
	 * @param act_data �^���f�[�^
	 */
	public PersonalData(String name,String coach_mode){
		this.name = name;
		this.coach_mode = Integer.parseInt(coach_mode);
	}

}
