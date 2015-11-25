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
	 * スマホ運動データ(生データ)
	 */
	public SmartPhoneActionData act_data;

	/**
	 * スマホ運動データ（時間正規化後）の配列
	 */
	public SmartPhoneActionData[] act_data_normalized_array = new SmartPhoneActionData[256];
	
	/**
	 * スマホ運動パラメータ
	 */
	public SmartPhoneActionParameter act_para = new SmartPhoneActionParameter();

	/**
	 * 比較対象のスマホ運動パラメータ
	 */
	public SmartPhoneActionParameter compare_act_para = new SmartPhoneActionParameter();

	/**
	 * 運動データ（時間正規化後）の最新通信ナンバー
	 */
	public int normalized_data_commu_num = 0;
	
	/**
	 * 運動データ（時間正規化後）出力テキストインデックス
	 */
	public int txt_index = 0;
	
	/**
	 * 個人データオブジェクトコンストラクタ
	 * @param user_name ユーザ名
	 * @param act_data 運動データ
	 */
	public SmartPhonePersonalData(String name, String coach_mode,SmartPhoneActionData act_data){
		super(name,coach_mode);
		this.act_data = act_data;
	}

	
	/**
	 * スマホ運動データに値を設定。一定数を超えたら分析。最大数を超えたら運動データを初期化してテキスト出力。
	 * @param set_act_data
	 */
	public void setdata(SmartPhoneActionData set_act_data){
		Debug.debug_print("SmartPhonePersonalData.setdata(ActionData set_act_data)",1);

		//運動データがいっぱいになったらテキストを出力して初期化
		if(act_data.getIndex()+set_act_data.getIndex()>act_data.getMax_index()){
			//テキストを出力
			if(OutPutText.output_txt("actdata",act_data.toString(),txt_index,false))txt_index++;
			//運動データを初期化
			act_data = new SmartPhoneActionData(SettingValues.STORE_DATA_NUM);
		}
		
		//取得した運動データをこのユーザの運動データに設定
		act_data.setData(set_act_data);
		
		//Analysisに機能を移したので削除すること
		set_now_position();
	}
	
	
	/**
	 * ユーザの運動データを返却
	 * @return
	 */
	public ActionData getSmartPhoneActionData(){
		return act_data;
	}
	
	/**
	 * 現在位置を運動パラメータに設定
	 */
	public void set_now_position(){
		act_para.setNow_latitude(act_data.getNowLatitude());
		act_para.setNow_longitude(act_data.getNowLongitude());
	}
}
