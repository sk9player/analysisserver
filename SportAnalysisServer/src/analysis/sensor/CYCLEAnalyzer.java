package analysis.sensor;

import setting.SettingValues;
import sub.Debug;
import analysis.calc.Calculate;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;

public class CYCLEAnalyzer implements SensorDataAnalyzer {

	@Override
	public void analysis(SensorPersonalData per_data, int late_commu_num) {
		if (per_data.count_normalized_same_state_data_num(SettingValues.ANALYSIS_SENSOR_DATA_NUM)) {
			analysisFeature(per_data);
			//分析回数をカウント
			per_data.analysis_count++;
		}

	}

	private void analysisFeature(SensorPersonalData per_data){
		Debug.debug_print("分析開始",2);
		Debug.debug_print("最新時間（ミリ秒）:"+per_data.act_para.getLast_time(),99);

		//センサデータの配列を取得
		double[][] sensor_datas = per_data.get_sensor_datas(per_data,SettingValues.ANALYSIS_SENSOR_DATA_NUM,SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM);


		cycle_analysis(per_data,sensor_datas);
	}

	/**
	 * ストレート滑走状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void cycle_analysis(SensorPersonalData per_data,double[][] sensor_datas){
		//動作特徴2：z軸オイラー角（上半身の向き）の周波数から1ストロークの長さ（秒）を分析
		//ローパスフィルタ
		int data_axis=SensorActionData.WLINZ;
		sensor_datas[data_axis] = Calculate.digital_low_pass_filter(sensor_datas[data_axis], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//平均値を除算
		sensor_datas[data_axis] = Calculate.subAverage(sensor_datas[data_axis],SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//窓関数を乗算
		double[] eurz_array4cycle = Calculate.mul_window_func_s(sensor_datas[data_axis]);
		//　周期を取得し、パラメータに設定
		double d = Calculate.getCycle(eurz_array4cycle);
		per_data.act_para.setCycle(d);
		//　１周記のデータの個数を取得
		int num = (int)(d*1000);
		Debug.debug_print("ピッチ:"+per_data.act_para.getCycle(),999999);

	}
}
