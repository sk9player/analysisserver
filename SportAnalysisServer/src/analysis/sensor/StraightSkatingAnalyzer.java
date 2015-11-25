package analysis.sensor;

import setting.SettingValues;
import sub.Debug;
import analysis.calc.Calculate;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;

public class StraightSkatingAnalyzer implements SensorDataAnalyzer{

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


		straight_skating_state_analysis(per_data,sensor_datas);
	}

	/**
	 * ストレート滑走状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void straight_skating_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){
		//動作特徴1:平均速度を分析
		per_data.act_para.setAve_speed(Calculate.getAverage(sensor_datas[SensorActionData.SPEED],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		Debug.debug_print("平均速度（km/h）:"+per_data.act_para.getAve_speed(),4);

		//動作特徴2：z軸オイラー角（上半身の向き）の周波数から1ストロークの長さ（秒）を分析
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULZ] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULZ], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//平均値を除算
		sensor_datas[SensorActionData.EULZ] = Calculate.subAverage(sensor_datas[SensorActionData.EULZ],SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//窓関数を乗算
		double[] eurz_array4cycle = Calculate.mul_window_func_s(sensor_datas[SensorActionData.EULZ]);
		//　周期を取得し、パラメータに設定
		double d = Calculate.getCycle(eurz_array4cycle);
		per_data.act_para.setCycle(d);
		//　１周記のデータの個数を取得
		int num = (int)(d*1000);
		Debug.debug_print("ピッチ:"+per_data.act_para.getCycle(),99);

		//動作特徴3：x軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEurx_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULX],SettingValues.ANALYSIS_SENSOR_DATA_NUM)-(double)per_data.first_act_data.getEulx()[0]);
		Debug.debug_print("平均上体傾斜:"+per_data.act_para.getEurx_ave(),99);


		//動作特徴4:z軸オイラー角（上半身の向き）の最大値から最小値の差分の平均を取得
		int aves_num = SettingValues.ANALYSIS_SENSOR_DATA_NUM/num;
		double aves_sum=0;
		for(int i=0;i<aves_num;i++){
			aves_sum+=Calculate.getRange(sensor_datas[SensorActionData.EULZ],i*num,num);
		}
		per_data.act_para.setEurz_diff_ave(aves_sum/aves_num);
		Debug.debug_print("平均上体振り:"+per_data.act_para.getEurz_diff_ave(),99);

		//動作特徴5:z軸方向運動量（から質量を除いた値）を取得。z軸加速度の絶対値の積分。
		//sensor_datas[SensorActionData.WLINZ] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.WLINZ], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//per_data.act_para.setMove_z(Calculate.getAbsSum(sensor_datas[SensorActionData.WLINZ],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		//Debug.debug_print("上下運動量:"+per_data.act_para.getMove_z(),4);

		//動作特徴6:水平方向運動量（から質量を除いた値）を取得。x軸y軸合成の加速度の絶対値の積分。
		//sensor_datas[SensorActionData.WLINX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.WLINX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//sensor_datas[SensorActionData.WLINY] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.WLINY], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//double[] linxy_array = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM];
		//for(int i=0;i<SettingValues.ANALYSIS_SENSOR_DATA_NUM;i++){
		//	linxy_array[i] = Math.sqrt((sensor_datas[SensorActionData.WLINX][i] * sensor_datas[SensorActionData.WLINX][i]) + (sensor_datas[SensorActionData.WLINY][i] * sensor_datas[SensorActionData.WLINY][i]));
		//}
		//per_data.act_para.setMove_xy(Calculate.getAbsSum(linxy_array,SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		//Debug.debug_print("水平運動量:"+per_data.act_para.getMove_xy(),4);

		//動作特徴7:左右方向運動量
		//sensor_datas[SensorActionData.LINX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.LINX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//per_data.act_para.setMove_x(Calculate.getAbsSum(sensor_datas[SensorActionData.LINX],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		//Debug.debug_print("左右運動量:"+per_data.act_para.getMove_x(),4);
	}
}
