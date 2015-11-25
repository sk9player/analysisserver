package coach.multiSensor;

import setting.ModeValues;
import sub.Debug;
import coach.sensor.SensorCoach;
import data.multiSensor.MultiSensorActionFeature;
import data.multiSensor.MultiSensorPersonalData;

public class MultiSensorCompareParameterCoach {

	/**
	 * 最後に行ったコーチングid
	 */
	private int last_coatching_id = 0;

	/**
	 * 連続して同じコーチングidが取得された回数
	 */
	private int same_coatching_id_count=0;

	SensorCoach sensor_compare;


	/**
	 * コンストラクタ
	 * @param returnFreq
	 */
	public MultiSensorCompareParameterCoach(int returnFreq) {
	}

	/**
	 * 固定のベストパラメータを設定
	 */
	public void set_best_parameter(MultiSensorPersonalData data){
	}


	/**
	 * 教師データと比較し、結果のIDを返却
	 *
	 * 比較対象
	 * １：自分の前回のピッチ
	 * ２：設定したピッチ
	 * ３：自分の平均パラメータ
	 * ４：教師パラメータ
	 *
	 * @param compare_mode　比較対象のID
	 * @param data
	 * @return
	 */
	public int compare_parameter(int compare_mode,MultiSensorPersonalData data,double compare_val){
		Debug.debug_print("SensorCompareParameterCoach.compare_parameter(int compare_mode,SensorPersonalData data)", 2);

		//分析で求められた値
		int calc_num = 0;
		//返却する値（calc_numをそのまま返さない場合もある為）
		int ret_num = 0;

		Debug.debug_print("コーチングモード："+compare_mode,99);
		switch (compare_mode) {
		case ModeValues.MULTI_SENSOR_COACH_MODE:
			calc_num = (int)(data.features.getFeature(0, MultiSensorActionFeature.FREQ_FEATURE_INDEX)*100);
			//3回に1回コーチングを行う
			ret_num = count_coatch_id(calc_num,5);
			break;
		default:
			break;
		}

		return ret_num;
	}

	/**
	 * 教師データと比較し、結果のIDを返却
	 *
	 * 比較対象
	 * １：自分の前回のパラメータ
	 * ２：自分の平均パラメータ
	 * ３：教師パラメータ
	 *
	 * @param compare_mode　比較対象のID
	 * @param data
	 * @return
	 */
	public int compare_parameter(int compare_mode,MultiSensorPersonalData data){
		return compare_parameter(compare_mode,data,0);
	}


	/**
	 * 前回と同じコーチングだった場合コーチングを行うかどうか設定
	 * @param coatch_id コーチングid
	 * @return
	 */
	private int count_same_coatch_id(int coatch_id,int num){
		Debug.debug_print("count_same_coatch_id("+coatch_id+")", 1);

		//前回のコーチングidと同じだった場合カウンタをインクリメント
		if(coatch_id==last_coatching_id){
			same_coatching_id_count++;
		}else{
			same_coatching_id_count=0;
		}
		last_coatching_id=coatch_id;

		//前回のコーチングidと同じで、カウンタが一定数を満たしていない場合、コーチングを行わないように設定。それ以外の場合はカウンタをリセット
		if(same_coatching_id_count!=0&&same_coatching_id_count<num){
			coatch_id = 0;
		}else{
			same_coatching_id_count=0;
		}

		Debug.debug_print("coatch_id;"+coatch_id, 2);

		return coatch_id;
	}


	private int count_coatch = 0;

	/**
	 * 設定回数毎にコーチングを行う。
	 * 前回のコーチングから設定回数以下の呼び出しだった場合コーチングなしのid(0)を返却
	 * @param coatch_id コーチングid
	 * @param num 設定回数
	 * @return
	 */
	private int count_coatch_id(int coatch_id,int num){
		if(count_coatch>=num-1){
			count_coatch=0;
			return coatch_id;
		}else{
			count_coatch++;
			return 0;
		}
	}
}
