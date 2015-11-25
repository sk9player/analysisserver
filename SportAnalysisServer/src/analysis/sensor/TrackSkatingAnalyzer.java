package analysis.sensor;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import analysis.calc.Calculate;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;

public class TrackSkatingAnalyzer implements SensorDataAnalyzer{

	@Override
	public void analysis(SensorPersonalData per_data, int late_commu_num) {
		/****状態推定***/
		SensorDataAnalyzer categorizer=new SkatingCategorizeAnalyzer();
		//状態推定可能なデータ数がたまったかどうかチェック
		categorizer.analysis(per_data,late_commu_num);

		/****分析****/
		// 分析可能な特定状態データ数がたまったかどうかチェック
		if (per_data.count_normalized_same_state_data_num(SettingValues.ANALYSIS_SENSOR_DATA_NUM)) {
			analysisFeature(per_data);
			//分析回数をカウント
			per_data.analysis_count++;
		}else{
			per_data.act_para.setState(ModeValues.STOP_STATE);
		}

		Debug.debug_print("状態"+per_data.act_para.getState(),999999999);


	}

	private void analysisFeature(SensorPersonalData per_data){
		Debug.debug_print("分析開始",2);
		Debug.debug_print("最新時間（ミリ秒）:"+per_data.act_para.getLast_time(),99);



		//センサデータの配列を取得
		double[][] sensor_datas = per_data.get_sensor_datas(per_data,SettingValues.ANALYSIS_SENSOR_DATA_NUM,SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM);


		int state=per_data.act_para.getState();

		//パラメータをリセット
		per_data.act_para.resetParamater();

		//滑走状態による分析を実行
		switch (state) {
		case ModeValues.FAIL_STATE:

 			break;
		case ModeValues.STOP_STATE:
			stop_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.START_STATE:
			start_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.STRAIGHT_REST_STATE:

 			break;
		case ModeValues.CORNER_REST_STATE:
			corner_skating_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.STRAIGHT_SKATING_STATE:
			straight_skating_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.CORNER_SKATING_STATE:
			corner_skating_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.STRAIGHT_DASH_STATE:
			straight_skating_state_analysis(per_data,sensor_datas);
 			break;
		case ModeValues.CORNER_DASH_STATE:
			corner_skating_state_analysis(per_data,sensor_datas);
 			break;
		default:
			break;
		}
	}




	/**
	 * 開発中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * 静止状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void stop_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){

	}

	/**
	 * 開発中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * スタート状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void start_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){


		//動作特徴3：x軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEurx_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULX],SettingValues.ANALYSIS_SENSOR_DATA_NUM)-(double)per_data.first_act_data.getEulx()[0]);
		Debug.debug_print("平均上体傾斜:"+per_data.act_para.getEurx_ave(),99);



	}


	/**
	 * 開発中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * 直線惰行状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void straight_rest_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){
		//動作特徴1:平均速度を分析
		per_data.act_para.setAve_speed(Calculate.getAverage(sensor_datas[SensorActionData.SPEED],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		Debug.debug_print("平均速度（km/h）:"+per_data.act_para.getAve_speed(),4);

		//動作特徴3：x軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEurx_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULX],SettingValues.ANALYSIS_SENSOR_DATA_NUM)-(double)per_data.first_act_data.getEulx()[0]);
		Debug.debug_print("平均上体傾斜:"+per_data.act_para.getEurx_ave(),4);


	}


	/**
	 * 開発中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * コーナー惰行状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void corner_rest_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){
		//動作特徴1:平均速度を分析
		per_data.act_para.setAve_speed(Calculate.getAverage(sensor_datas[SensorActionData.SPEED],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		Debug.debug_print("平均速度（km/h）:"+per_data.act_para.getAve_speed(),4);

		//動作特徴3：x軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEurx_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULX],SettingValues.ANALYSIS_SENSOR_DATA_NUM)-(double)per_data.first_act_data.getEulx()[0]);
		Debug.debug_print("平均上体傾斜:"+per_data.act_para.getEurx_ave(),4);

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


	/**
	 * コーナー滑走状態での分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void corner_skating_state_analysis(SensorPersonalData per_data,double[][] sensor_datas){
		//動作特徴1:平均速度を分析
		per_data.act_para.setAve_speed(Calculate.getAverage(sensor_datas[SensorActionData.SPEED],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		Debug.debug_print("平均速度（km/h）:"+per_data.act_para.getAve_speed(),4);

		//動作特徴2：z軸オイラー角（上半身の向き）の周波数から1ストロークの長さ（秒）を分析
		//周期を求めるデータ
		int data4cycle=SensorActionData.EULZ;
//		int data4cycle=SensorActionData.LINZ;
		//ローパスフィルタ
		double[] eurz_array4cycle = Calculate.digital_low_pass_filter(sensor_datas[data4cycle], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);



		//ハイパスフィルタ
		eurz_array4cycle = Calculate.high_pass_filter(eurz_array4cycle, 0.3, SettingValues.ANALYSIS_SENSOR_DATA_NUM);


//		eurz_array4cycle = Calculate.digital_high_pass_filter(eurz_array4cycle, 0.2, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		//平均値を除算
		//sensor_datas[data4cycle] = Calculate.subAverage(sensor_datas[data4cycle],SettingValues.ANALYSIS_SENSOR_DATA_NUM);




		//窓関数を乗算
//		eurz_array4cycle = Calculate.mul_window_func_s(sensor_datas[data4cycle]);
		eurz_array4cycle = Calculate.mul_window_func_s(eurz_array4cycle);





		//　周期を取得し、パラメータに設定
		double d = Calculate.getCycle(eurz_array4cycle);
		per_data.act_para.setCycle(d);
		//　１周記のデータの個数を取得
		int num = (int)(d*1000);
		Debug.debug_print("ピッチ:"+per_data.act_para.getCycle(),4);

		//動作特徴3：x軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULX] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULX], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEurx_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULX],SettingValues.ANALYSIS_SENSOR_DATA_NUM)-(double)per_data.first_act_data.getEulx()[0]);
		Debug.debug_print("平均上体傾斜:"+per_data.act_para.getEurx_ave(),4);


		//動作特徴4:z軸オイラー角（上半身の向き）の最大値から最小値の差分の平均を取得
		int aves_num = SettingValues.ANALYSIS_SENSOR_DATA_NUM/num;
		double aves_sum=0;
		for(int i=0;i<aves_num;i++){
//			aves_sum+=Calculate.getRange(sensor_datas[SensorActionData.EULZ],i*num,num);
			aves_sum+=Calculate.getRange(sensor_datas[SensorActionData.EULZ],i*num,num);
		}
		per_data.act_para.setEurz_diff_ave(aves_sum/aves_num);
		Debug.debug_print("平均上体振り:"+per_data.act_para.getEurz_diff_ave(),4);

		//動作特徴5：y軸オイラー角（上半身の傾斜）の平均の取得
		//ローパスフィルタ
		sensor_datas[SensorActionData.EULY] = Calculate.digital_low_pass_filter(sensor_datas[SensorActionData.EULY], 5, SettingValues.ANALYSIS_SENSOR_DATA_NUM);
		per_data.act_para.setEury_ave(Calculate.getAverage(sensor_datas[SensorActionData.EULY],SettingValues.ANALYSIS_SENSOR_DATA_NUM));
		Debug.debug_print("平均上体左右傾斜:"+per_data.act_para.getEury_ave(),4);


	}

}
