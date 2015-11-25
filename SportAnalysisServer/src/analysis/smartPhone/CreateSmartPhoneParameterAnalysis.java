package analysis.smartPhone;

import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;
import analysis.calc.Calculate;
import analysis.calc.spectrum.FFT.FFT;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;

public class CreateSmartPhoneParameterAnalysis {

	/**
	 * 出力テキストインデックス（現在非使用）
	 */
	private int txt_index = 0;

	/**
	 * スマホ運動データを分析して運動パラメータを返却
	 *
	 * @param per_data
	 */
	public void create(SmartPhonePersonalData per_data) {
		Debug.debug_print("CreateParameterAnalysis.sp_create(PersonalData per_data)",1);


		// パラメータ1：現在位置をパラメータに設定
		set_now_position(per_data);

		// 分析可能なデータ数がたまったかどうかチェック
		if (count_normalized_data_num(per_data) > SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM) {

			Debug.debug_print("分析開始",99);

			//パラメータ2:最新の時間を取得し設定 実装中（現在誤り）
			SmartPhoneActionData late_data = per_data.act_data_normalized_array[per_data.normalized_data_commu_num];
			per_data.act_para.setLast_time(late_data.getTime()[late_data.getIndex()-1]);
			Debug.debug_print("最新時間（ミリ秒）:"+per_data.act_para.getLast_time(),99);

			// パラメータ3：運動の周期のパラメータを取得し設定
			double[] accy_data=getAccYArray(per_data);
			//窓関数をかける
			accy_data = Calculate.mul_window_func_sp(accy_data);

			per_data.act_para.setCycle(getCycle(accy_data));
			Debug.debug_print("ピッチ:"+per_data.act_para.getCycle(),99);

			// パラメータ4：運動中の平均速度を取得
			per_data.act_para.setAve_speed(getAverage(getSpeedArray(per_data)));
			Debug.debug_print("平均速度（km/h）:"+per_data.act_para.getAve_speed(),99);

			// パラメータ5：運動のストライドを取得
			per_data.act_para.setStride(getStride(per_data));


			// ログテキストの出力
			OutPutText.output_txt(per_data.name+"_smart_phone_para", per_data.act_para.toString(), txt_index,true);
//			txt_index++;
		}
	}

	/**
	 * 最新の通信ナンバーの時間正規化運動データから前に連続した分析可能データ数をカウント
	 *
	 * @param per_data
	 * @return
	 */
	private int count_normalized_data_num(SmartPhonePersonalData per_data) {
		Debug.debug_print("CreateParameterAnalysis.count_normalized_data_num(PersonalData per_data)",1);
		int data_num = 0;

		for (int i = per_data.normalized_data_commu_num; i >= 0; i--) {

			// 連続した分析可能なデータがない場合、分析を行わずに終了
			if (per_data.act_data_normalized_array[i] == null)
				return 0;

			// 連続した分析可能なデータ数をカウント
			data_num += per_data.act_data_normalized_array[i].getMax_index();
		}
		return data_num;
	}

	/**
	 * 現在位置のパラメータを設定
	 *
	 * @param per_data
	 */
	private void set_now_position(SmartPhonePersonalData per_data) {
		Debug.debug_print("CreateParameterAnalysis.set_now_position(PersonalData per_data)",1);
		per_data.act_para.setNow_latitude(per_data.act_data.getNowLatitude());
		per_data.act_para.setNow_longitude(per_data.act_data.getNowLongitude());
	}


//	int debug_txt_index=0;

	/**
	 * y軸加速度の運動データからストロークのパラメータを取得
	 *
	 * @param act_data
	 * @return
	 */
	private double[] getAccYArray(SmartPhonePersonalData per_data) {
		Debug.debug_print("CreateParameterAnalysis.getCycleAccY(PersonalData per_data)",1);

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM * SettingValues.ANALYSIS_SMART_PHONE_DATA_MUL_NUM];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;
//		double[] debug_time_data = new double[SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM * SettingValues.ANALYSIS_SMART_PHONE_DATA_MUL_NUM];

		// 最新通信ナンバーを取得
		int late_commu_num_index = per_data.normalized_data_commu_num;
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM; i++) {
			// 値を格納
			data[i] = per_data.act_data_normalized_array[late_commu_num_index]
					.getAccy()[late_commu_data_num_index--];
//			debug_time_data[i] = per_data.act_data_normalized_array[late_commu_num_index]
//					.getTime()[late_commu_data_num_index];

			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				late_commu_num_index--;
				late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i;
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){
					double last_data = per_data.act_data_normalized_array[late_commu_num_index]
							.getAccy()[late_commu_data_num_index];
					i++;
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
//					debug_time_data[i] = debug_time_data[i-1] - 1;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
				}
			}
		}

//		OutPutText.output_txt("debug_time", debug_time_data, debug_txt_index, false);
//		OutPutText.output_txt("debug_data", data, debug_txt_index, false);
		return(data);
	}

	/**
	 *   平均速度を取得し返却
	 *
	 * @param per_data
	 * @return
	 */
	private double[] getSpeedArray(SmartPhonePersonalData per_data) {
		Debug.debug_print("CreateParameterAnalysis.getSpeedArray(PersonalData per_data)",1);

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM * SettingValues.ANALYSIS_SMART_PHONE_DATA_MUL_NUM];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;
//		double[] debug_time_data = new double[SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM * SettingValues.ANALYSIS_SMART_PHONE_DATA_MUL_NUM];

		// 最新通信ナンバーを取得
		int late_commu_num_index = per_data.normalized_data_commu_num;
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM; i++) {
			// 値を格納
			data[i] = per_data.act_data_normalized_array[late_commu_num_index]
					.getSpeed()[late_commu_data_num_index--];
//			debug_time_data[i] = per_data.act_data_normalized_array[late_commu_num_index]
//					.getTime()[late_commu_data_num_index];

			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				late_commu_num_index--;
				late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i;
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){
					double last_data = per_data.act_data_normalized_array[late_commu_num_index]
							.getAccy()[late_commu_data_num_index];
					i++;
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
//					debug_time_data[i] = debug_time_data[i-1] - 1;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
				}
			}
		}

//		OutPutText.output_txt("debug_time", debug_time_data, debug_txt_index, false);
//		OutPutText.output_txt("debug_data", data, debug_txt_index, false);
		return(data);
	}

	private double getStride(SmartPhonePersonalData per_data){

		return per_data.act_para.getAve_speed()/3.6*per_data.act_para.getCycle();
	}


	/**
	 * 平均値を取得
	 *
	 * @param data
	 * @return
	 */
	private double getAverage(double[] data) {
		double sum = 0;
		int index_num = data.length;
		for (int i = 0; i < index_num; i++) {
			sum+=data[i];
		}
		return sum / index_num;
	}

	/**
	 * 最大値を取得
	 *
	 * @param data
	 * @return
	 */
	private double getMax(double[] data) {
		double max = 0;
		int index = 0;
		for (int i = 0; i < data.length; i++) {
			if (max < data[i]) {
				max = data[i];
			}
		}
		return max;
	}

	/**
	 * データから周期を取得
	 *
	 * @param data
	 * @return
	 */
	private double getCycle(double[] data) {
		Debug.debug_print("CreateParameterAnalysis.getCycle(double[] data)",1);

		// 周波数分析を行い周波数特徴の絶対値の配列を取得
		data = FFT.fftAnalysis(data);
		Debug.debug_print("最大特徴=" + Calculate.getMaxIndex(data),2);
		// 最大特徴を返却

		double cycle = 1 / (Calculate.getMaxIndex(data) * 1000 / (SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM * SettingValues.ANALYSIS_SMART_PHONE_DATA_MUL_NUM));
		Debug.debug_print("ピッチ(歩/秒)=" + cycle,2);

		return cycle;
	}

}
