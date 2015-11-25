package data.sensor;

import data.ActionData;
import data.PersonalData;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;

/**
 * 複数センサ分析モード時のユーザデータ
 * 
 * いずれ、SensorPersonalDataもセンサ１つのMultiSensorPersonalDataとして機能統合する予定（MultiSensorActionDataについても同様）
 * 
 * @author OZAKI
 *
 */
public class MultiSensorPersonalData extends PersonalData{

	/**
	 * センサの数
	 */
	public int sensor_num=0;
	
	/**
	 * センサ毎のセンサ運動データ（時間正規化後）の配列
	 */
	public SensorActionData[][] multi_act_data_normalized_array;
	
	/**
	 * センサ毎のセンサ時間の差（スマートフォンの時間との差で算出）
	 */
	public long[] sensorDiffTimes;
	
	/**
	 * 運動データ（時間正規化後）ナンバー
	 */
	public int[] normalized_data_num;
	
	/**
	 * 運動データ（時間正規化後）の最新通信ナンバー
	 */
	public int[] normalized_data_commu_num;
	
	
	/**
	 * センサ運動パラメータ
	 */
	public MultiSensorActionFeature features = new MultiSensorActionFeature();
	
	
	/**
	 * センサ毎の運動データ（時間正規化後）生成回数
	 */
	public int[] count_index;
	
	/**
	 * 運動データ（時間正規化後）出力テキストインデックス
	 */
	public int[] txt_index;
	
	/**
	 * 分析回数
	 */
	public int analysis_count = 0;
	
	/**
	 * キャリブレーション用初期センサデータ（スタート地点で直立制止して計測）
	 */
	public SensorActionData first_act_data = new SensorActionData(1);

	
	/**
	 * 個人データオブジェクトコンストラクタ
	 * @param user_name ユーザ名
	 * @param act_data 運動データ
	 */
	public MultiSensorPersonalData(String name,String coach_mode,int sensor_num){
		super(name,coach_mode);
		init(sensor_num);
	}
	
	/**
	 * 個人データオブジェクトコンストラクタ
	 * @param user_name ユーザ名
	 * @param act_data 運動データ
	 */
	public MultiSensorPersonalData(String name,int coach_mode,int sensor_num){
		super(name,Integer.toString(coach_mode));
		init(sensor_num);
	}

	private void init(int sensor_num){
		this.sensor_num=sensor_num;
		multi_act_data_normalized_array = new SensorActionData[sensor_num][SettingValues.STORE_NORMALIZED_DATA_NUM];
		count_index = new int[sensor_num];
		sensorDiffTimes = new long[sensor_num];
		normalized_data_num = new int[sensor_num];
		normalized_data_commu_num = new int[sensor_num];
		txt_index = new int[sensor_num];
	}
	
	/**
	 * 複数センサの時間差情報を算出して設定
	 * @param byte_data
	 */
	public void set_sensor_diff_times(byte[] byte_data) {


		final int LONG_NUM=8;

		for(int i=0;i<byte_data.length/LONG_NUM/2;i++){
			byte[] sensor_b= new byte[LONG_NUM];
			byte[] android_b= new byte[LONG_NUM];

			System.arraycopy(byte_data, i*LONG_NUM*2, sensor_b, 0, LONG_NUM);
			System.arraycopy(byte_data, i*LONG_NUM*2+LONG_NUM, android_b, 0, LONG_NUM);
			
			long android_time = CalcSensorData.byte_to_long(android_b);
			Debug.debug_print("android_time:"+android_time,99);
			long sensor_time = CalcSensorData.byte_to_long(sensor_b);
			Debug.debug_print("sensor_time:"+sensor_time,99);
			
			sensorDiffTimes[i] = android_time-sensor_time;
		}
	}

	
	/**
	 * センサデータの配列を返却
	 *  センサデータ仕様
	 *  0:速度
	 *  1:z軸オイラー角（上半身の向き）
	 *  2:x軸オイラー角（上半身の傾斜）
	 *  3:z軸(上下方向)加速度
	 * 
	 * 
	 * 
	 * @return
	 */
	public double[][][] get_sensor_datas() {
		double[][][] sensor_datas= new double[sensor_num][SettingValues.ANALYSIS_DATA_AXIS_NUM][SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		
		for(int i = 0;i<sensor_num;i++){
			sensor_datas[i][0] = get_eurx_array(i);
			sensor_datas[i][1] = get_eury_array(i);
			sensor_datas[i][2] = get_eurz_array(i);
			
		}
		
		//基準となるセンサ番号と部位による分析データの切り出し
		
		
		return sensor_datas;
	}
	
	/**
	 * x軸オイラー角のデータ配列を取得
	 * @param per_data
	 * @return
	 */
	private double[] get_eurx_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;

		// 最新通信ナンバーを取得
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// 値を格納
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEulx()[late_commu_data_num_index--];
			//値を格納したのでインクリメント
			i++;
			
			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				
				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEulx()[late_commu_data_num_index];
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//値を格納したのでインクリメント
					i++;
				}
			}
		}
	
		return(data);
	}

	
	/**
	 * y軸オイラー角のデータ配列を取得
	 * @param per_data
	 * @return
	 */
	private double[] get_eury_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;

		// 最新通信ナンバーを取得
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// 値を格納
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEuly()[late_commu_data_num_index--];
			//値を格納したのでインクリメント
			i++;
			
			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				
				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEuly()[late_commu_data_num_index];
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
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
	 * z軸オイラー角のデータ配列を取得
	 * @param per_data
	 * @return
	 */
	private double[] get_eurz_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);
		// 周波数分析を行うy軸加速度データ配列

		// 周波数分析を行うy軸加速度データ配列
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// 時系列波形の結合時にデータ補完する為に保持する、
		double pre_time_data = 0.0;

		// 最新通信ナンバーを取得
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// 最新通信ナンバーの運動データの数を取得
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// 周波数分析を行う運動データ（y軸加速度）配列に値を格納
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// 値を格納
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEulz()[late_commu_data_num_index--]+360*eulz_correct_num;
			//値を格納したのでインクリメント
			i++;

			// 通信ナンバーの運動データをすべて格納したら、１つ前の通信ナンバーの運動データを取得
			if (late_commu_data_num_index == 0) {
				//前回の時間データを取得
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// 時系列波形の間の時間差を取得
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				
				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEulz()[late_commu_data_num_index]+360*eulz_correct_num;
				last_data=correct_eulz(data[last_index],last_data);
				
				// 時系列波形の間を補間
				for(int j=0;j<diff_time;j++){

					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//値を格納したのでインクリメント
					i++;
				}
			}
		}//debug
//		OutPutText.output_txt(per_data.name+"_correcteulz", data, txt_index,false);
//		eulz_correct_num=0;
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
			System.out.println("correct");
		} else if (pre_eurz - now_eurz < -60
				&& pre_eurz-360*eulz_correct_num < 0) {
			eulz_correct_num--;
			System.out.println("correct");
		}
		return now_eurz;
	}
	
	/**
	 * 最新の通信ナンバーの時間正規化運動データから前に連続した分析可能データ数をカウントし、分析可能かどうかを返却
	 * 
	 * @param per_data
	 * @return
	 */
	public boolean count_normalized_data_num() {
		Debug.debug_print("CreateSensorParameterAnalysis.count_normalized_data_num(PersonalData per_data)",1);
		int data_num = 0;
		
		
		Debug.debug_print("最新通信ナンバー"+normalized_data_commu_num,2);
		boolean[] enough_data_flgs=new  boolean[sensor_num];
		
		for(int isensor = 0;isensor<sensor_num;isensor++){
			int data_commu_num=normalized_data_commu_num[isensor];
			for (int i = data_commu_num; i >= 0; i--) {
				// 連続した分析可能なデータがない場合、分析を行わずに終了
				if (multi_act_data_normalized_array[isensor][i] == null)
					return false;
				
				data_num += multi_act_data_normalized_array[isensor][i].getMax_index();
				if(data_num > SettingValues.ANALYSIS_SENSOR_DATA_NUM){
					enough_data_flgs[isensor]=true;
					break;
				}
			}
		}
		
		for (int isensor = 0;isensor<sensor_num;isensor++){
			if(!enough_data_flgs[isensor])
				return false;
		}
		return true;
	}
	
	
}
