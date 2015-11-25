package data.sensor;

import setting.SettingValues;
import sub.Debug;
import data.PersonalData;

public class SensorPersonalData extends PersonalData{


	/**
	 * センサ運動データ（時間正規化後）の配列
	 */
	public SensorActionData[] act_data_normalized_array = new SensorActionData[SettingValues.STORE_NORMALIZED_DATA_NUM];

	/**
	 * センサ運動データ（時間正規化後）の配列毎の状態
	 */
	public int[] state_array = new int[SettingValues.STORE_NORMALIZED_DATA_NUM];

	/**
	 * 運動データ（時間正規化後）ナンバー
	 */
	public int normalized_data_num = 0;

	/**
	 * 運動データ（時間正規化後）の最新通信ナンバー
	 */
	public int normalized_data_commu_num = 0;

	/**
	 * センサ運動パラメータ
	 */
	public SensorActionParameter act_para = new SensorActionParameter();

	/**
	 * 比較対象のセンサ運動パラメータ
	 */
	public SensorActionParameter compare_act_para = new SensorActionParameter();


	/**
	 * 運動データ（時間正規化後）生成回数
	 */
	public int count_index = 0;

	/**
	 * 運動データ（時間正規化後）出力テキストインデックス
	 */
	public int txt_index = 0;

	/**
	 * 分析回数
	 */
	public int analysis_count = 0;

	/**
	 * キャリブレーション用初期センサデータ（スタート地点で直立制止して計測）
	 */
	public SensorActionData first_act_data = new SensorActionData(1);

	/**
	 * 最後に行ったアドバイスのID
	 */
	public int last_coatching_id = 0;

	/**
	 * アドバイスを行わなかった数
	 */
	public int count_coatch = 0;

	/**
	 * 連続して同じコーチングidが取得された回数
	 */
	public int same_coatching_id_count=0;

	/**
	 * 個人データオブジェクトコンストラクタ
	 * @param user_name ユーザ名
	 * @param act_data 運動データ
	 */
	public SensorPersonalData(String name,String coach_mode){
		super(name,coach_mode);
	}


	/**
	 * 最新の通信ナンバーの時間正規化運動データから前に連続した状態推定可能データ数をカウント
	 *
	 * @param analysis_num
	 * @return
	 */
	public boolean count_normalized_data_num(int analysis_num) {
		Debug.debug_print("CreateSensorParameterAnalysis.count_normalized_data_num(PersonalData per_data)",1);
		int data_num = 0;

		//2周目（通信回数256回）以降はチェックしない
		if(normalized_data_num!=0)
			return true;

		Debug.debug_print("最新通信ナンバー"+normalized_data_commu_num,2);

		for (int i = normalized_data_commu_num; i >= 0; i--) {

			// 連続した分析可能なデータがない場合、分析を行わずに終了
			if (act_data_normalized_array[i] == null)
				return false;

			// 連続した分析可能なデータ数をカウント
			data_num += act_data_normalized_array[i].getMax_index();
			if(data_num > analysis_num){
				Debug.debug_print("分析可能データ数"+data_num,2);
				return true;
			}
		}
		Debug.debug_print("分析可能データ数"+data_num,2);
		return false;
	}


	/**
	 * 最新の通信ナンバーの時間補間運動データから前に連続した同じ状態の分析可能データ数をカウント
	 *
	 * @param per_data
	 * @return
	 */
	public boolean count_normalized_same_state_data_num(int analysis_num) {
		Debug.debug_print("CreateSensorParameterAnalysis.count_normalized_data_num(PersonalData per_data)",1);
		int data_num = 0;

		//2周目（通信回数256回）以降はチェックしない
		if(normalized_data_num!=0)
			return true;

		Debug.debug_print("最新通信ナンバー"+normalized_data_commu_num,999999);

		for (int i = normalized_data_commu_num; i >= 2; i--) {

			// 連続した分析可能なデータがない場合、分析を行わずに終了
			if (act_data_normalized_array[i] == null){
				Debug.debug_print("分析可能データ数"+data_num,2);
				return false;
			}

			// 2つ連続して現在と違う状態データが混ざったらデータ数不足と判定
			if(state_array[i]!=state_array[i-1]){
				if(state_array[i]!=state_array[i-2]){
					Debug.debug_print("分析可能データ数"+data_num,2);
					return false;
				}
				state_array[i-1]=state_array[i];
			}

			// 連続した分析可能なデータ数をカウント
			data_num += act_data_normalized_array[i].getMax_index();
			if(data_num > analysis_num){
				Debug.debug_print("分析可能データ数"+data_num,2);
				return true;
			}
		}
		Debug.debug_print("分析可能データ数"+data_num,2);
		return false;
	}



	/**
	 * センサデータの配列を返却
	 *
	 * @param per_data
	 * @return
	 */
	public double[][] get_sensor_datas(SensorPersonalData per_data,int data_num,int data_mul_num) {
		double[][] sensor_datas= new double[SensorActionData.DATA_KIND][data_num];
		sensor_datas[SensorActionData.SPEED] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.SPEED);

		//実験用疑似速度設定
		//for(int i =0; i<sensor_datas[SensorActionData.SPEED].length; i++){
		//	sensor_datas[SensorActionData.SPEED][i]+=20.0;
		//}

		sensor_datas[SensorActionData.EULZ] = get_eurz_array(per_data,data_num,data_mul_num);
		sensor_datas[SensorActionData.EULX] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.EULX);
		sensor_datas[SensorActionData.WLINZ] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.WLINZ);
		sensor_datas[SensorActionData.WLINX] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.WLINX);
		sensor_datas[SensorActionData.WLINY] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.WLINY);
		sensor_datas[SensorActionData.LINX] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.LINX);
		sensor_datas[SensorActionData.EULY] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.EULY);
		sensor_datas[SensorActionData.LINY] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.LINY);
		sensor_datas[SensorActionData.LINZ] = get_data_array(per_data,data_num,data_mul_num,SensorActionData.LINZ);

		//加速度補正
		int num=sensor_datas[SensorActionData.LINX].length;
		for(int isample=0;isample<num;isample++){
			sensor_datas[SensorActionData.LINY][isample]-=(double)(per_data.first_act_data.getLinx()[0]);
		}

		return sensor_datas;
	}


	/**
	 * スピードのデータ配列を取得
	 * @param per_data
	 * @return
	 */
	private double[] get_data_array(SensorPersonalData per_data,int data_num,int data_mul_num,int data_kind){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);

		// データ配列を生成
		double[] data = new double[data_num * data_mul_num];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;

		// 最新通信ナンバーを取得
		int late_commu_num_index = per_data.normalized_data_commu_num;
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < data_num;) {
			// 値を格納
			data[i] = per_data.act_data_normalized_array[late_commu_num_index]
					.getDataUsingID(data_kind)[late_commu_data_num_index--];
			//値を格納したのでインクリメント
			i++;

			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i-1;
				double last_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getDataUsingID(data_kind)[late_commu_data_num_index];
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){

					if(i < data_num)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//値を格納したのでインクリメント
					i++;
				}
			}
		}

		return(data);
	}


	private int eulz_correct_num = 0;

	/**
	 * z軸オイラー角のデータ配列を取得(データの補正がある為他のデータとメソッドを分けている)
	 * @param per_data
	 * @return
	 */
	private double[] get_eurz_array(SensorPersonalData per_data,int data_num,int data_mul_num){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);
		// 周波数分析を行うy軸加速度データ配列

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[data_num * data_mul_num];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;

		// 最新通信ナンバーを取得
		int late_commu_num_index = per_data.normalized_data_commu_num;
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < data_num;) {
			// 値を格納
			data[i] = per_data.act_data_normalized_array[late_commu_num_index]
					.getEulz()[late_commu_data_num_index--]+360*eulz_correct_num;
			//値を格納したのでインクリメント
			i++;

			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = per_data.act_data_normalized_array[late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-per_data.act_data_normalized_array[late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i-1;
				double last_data = per_data.act_data_normalized_array[late_commu_num_index]
						.getEulz()[late_commu_data_num_index]+360*eulz_correct_num;
				last_data=correct_eulz(data[last_index],last_data);

				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){

					if(i < data_num)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//値を格納したのでインクリメント
					i++;
				}
			}
		}
		return(data);
	}

	/**
	 * オイラー角の180~-180の跳びを修正
	 * @param pre_eurz
	 * @param now_eurz
	 * @return
	 */
	private double correct_eulz(double pre_eurz,double now_eurz){
		if (pre_eurz - now_eurz > 60 && pre_eurz-360*eulz_correct_num > 0) {
			eulz_correct_num++;
			Debug.debug_print("correct",1);
		} else if (pre_eurz - now_eurz < -60
				&& pre_eurz-360*eulz_correct_num < 0) {
			eulz_correct_num--;
			Debug.debug_print("correct",1);
		}
		return now_eurz;
	}








}
