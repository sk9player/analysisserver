package analysis.sensor;

import setting.ModeValues;
import sub.Debug;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;

public class CreateSensorParameterAnalysis {

	SensorDataAnalyzer analyzer;

	/**
	 * センサ運動データを分析して運動パラメータを返却
	 * @param per_data
	 * @return 分析回数を返却
	 */
	public int createFeature(SensorPersonalData per_data) {
		Debug.debug_print("CreateSensorParameterAnalysis.createFeature(PersonalData per_data)",1);

		int late_commu_num = per_data.normalized_data_commu_num;

		//特殊パラメータ1：最新現在位置をパラメータに設定
		SensorActionData late_data = per_data.act_data_normalized_array[late_commu_num];
		set_now_position(per_data,late_data);

		//特殊パラメータ2:最新の時間を取得し設定
		per_data.act_para.setLast_time(late_data.getTime()[late_data.getIndex()-1]);



		//モードによるコーチングを実施
		if(per_data.coach_mode==ModeValues.GENERAL_COACH_MODE){
			analyzer=new TrackSkatingAnalyzer();
		}else if(per_data.coach_mode==ModeValues.STRAIGHT_COACH_MODE){
			analyzer=new StraightSkatingAnalyzer();
		}else if(per_data.coach_mode==ModeValues.STATE_MODE){
			analyzer=new SkatingCategorizeAnalyzer();
		}else if(per_data.coach_mode==ModeValues.CYCLE_RETURN_MODE||per_data.coach_mode==ModeValues.LAST_CYCLE_COMPARE_MODE||per_data.coach_mode==ModeValues.SET_CYCLE_COMPARE_MODE){
			analyzer=new CYCLEAnalyzer();
		}
		//状態推定可能なデータ数がたまったかどうかチェック
		analyzer.analysis(per_data,late_commu_num);

		return per_data.analysis_count;

	}







	/**
	 * 現在位置のパラメータを設定
	 *
	 * @param per_data
	 */
	private void set_now_position(SensorPersonalData per_data,SensorActionData act_data) {
		Debug.debug_print("CreateParameterAnalysis.set_now_position(PersonalData per_data)",1);
		per_data.act_para.setNow_latitude(act_data.getNowLatitude());
		per_data.act_para.setNow_longitude(act_data.getNowLongitude());
	}


}
