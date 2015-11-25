package data;

import data.ActionData;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;

public class PersonalData {

	/**
	 * ユーザ名
	 */
	public String name="";
	

	/**
	 * 初期時間
	 */
	public long first_time=0;
	

	
	
	/**
	 * コーチングモード
	 */
	public int coach_mode = ModeValues.NO_COACH_MODE;

	
	/**
	 * 個人データオブジェクトコンストラクタ
	 * @param user_name ユーザ名
	 * @param act_data 運動データ
	 */
	public PersonalData(String name,String coach_mode){
		this.name = name;
		this.coach_mode = Integer.parseInt(coach_mode);
	}

}
