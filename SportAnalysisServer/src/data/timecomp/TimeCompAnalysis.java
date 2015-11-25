package data.timecomp;

import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;
import data.multiSensor.MultiSensorActionData;
import data.multiSensor.MultiSensorPersonalData;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;

/**
 * 時間補間
 *
 * @author OZAKI
 *
 */
public class TimeCompAnalysis {

	/**
	 * 時間補間したスマホ運動データを生成し個人データに格納する。
	 *
	 * @param per_data
	 *            個人データ
	 * @param act_data
	 *            スマホ運動データ
	 */
	public void time_comp(SmartPhonePersonalData per_data,
			SmartPhoneActionData act_data) {
		Debug.debug_print(
				"TimeNormalizedAnalysis.time_normalize(PersonalData per_data,ActionData act_data)",
				1);
		long[] time = act_data.getTime();
		// 運動データの最初の時間を取得
		long first_time = time[0];
		// 運動データの最後の時間を取得
		long last_time = time[SettingValues.ACC_DATA_NUM - 1];
		// 作成する時間補間運動データのデータ数を取得
		int data_num = (int) (last_time - first_time + 1);
		// 運動データの時間長の新しい運動データ（時間補間）オブジェクトを生成
		SmartPhoneActionData act_data_normalized = new SmartPhoneActionData(
				data_num);

		// 運動データの時間を補間して運動データ（時間補間）オブジェクトに設定
		act_data_normalized.normalizeData(act_data);

		// 通信ナンバーを取得
		int commu_num = act_data.getCommu_index();
		// 通信ナンバーの運動データ（時間補間）を運動データ（時間補間）リストに挿入
		per_data.act_data_normalized_array[commu_num] = act_data_normalized;

		OutPutText.output_txt(per_data.name + "normalized",
				act_data_normalized.toString(), commu_num, false);

		// 通信ナンバーが最新だった場合個人データの最新通信ナンバーを更新
		if (commu_num > per_data.normalized_data_commu_num || commu_num == 0)
			per_data.normalized_data_commu_num = commu_num;

	}

	/**
	 * 時間補間したセンサ運動データを生成し個人データに格納する。
	 *
	 * @param per_data
	 *            個人データ
	 * @param act_data
	 *            センサ運動データ
	 */
	public void time_comp(SensorPersonalData per_data,
			SensorActionData act_data) {
		Debug.debug_print(
				"TimeNormalizedAnalysis.time_normalize(PersonalData per_data,ActionData act_data)",
				1);
		// 運動データの時間情報を取得
		long[] time = act_data.getTime();
		// 運動データの最初の時間を取得
		long first_time = time[0];
		// 運動データの最後の時間を取得
		long last_time = time[SettingValues.ACC_DATA_NUM_S - 1];

		// 作成する時間補間運動データのデータ数を取得
		int data_num = (int) (last_time - first_time + 1);
		// 運動データの時間長の新しい運動データ（時間補間）オブジェクトを生成
		SensorActionData act_data_normalized = new SensorActionData(data_num);

		// 運動データの時間を補間して運動データ（時間補間）オブジェクトに設定
		act_data_normalized.normalizeData(act_data);

		// 通信ナンバーを取得
		int commu_num = act_data.getCommu_index();

		per_data.count_index++;
		if (per_data.count_index > 220 && commu_num < 20) {
			per_data.txt_index++;
			per_data.count_index = 0;
		}

		// 通信ナンバーの運動データ（時間補間）を運動データ（時間補間）リストに挿入
		per_data.act_data_normalized_array[commu_num] = act_data_normalized;

//		OutPutText.output_txt(per_data.name + "normalized_s",
//				act_data_normalized.toString2(), commu_num
//						+ (per_data.txt_index * 256), true);
		OutPutText.output_txt(per_data.name + "normalized_s",
				act_data_normalized.toString2(), 0, true);

		// 通信ナンバーが最新だった場合個人データの最新通信ナンバーを更新
		if (commu_num > per_data.normalized_data_commu_num || commu_num == 0) {
			per_data.normalized_data_commu_num = commu_num;
			if (per_data.normalized_data_commu_num == SettingValues.STORE_NORMALIZED_DATA_NUM - 5)
				per_data.normalized_data_num++;
		}

	}

	/**
	 * 時間補間した複数センサ運動データを生成し個人データに格納する。
	 *
	 * 開発中！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 *
	 * @param per_data
	 *            個人データ
	 * @param act_data
	 *            センサ運動データ
	 */
	public void time_comp(MultiSensorPersonalData multi_per_data,
			MultiSensorActionData multi_act_data) {
		Debug.debug_print(
				"TimeNormalizedAnalysis.time_normalize(MultiSensorPersonalData per_data,MultiSensorActionData act_data)",
				1);

		// センサの数を取得
		int sensor_num = multi_act_data.getSensor_num();

		for (int i = 0; i < sensor_num; i++) {
			//対象のセンサデータを取得
			SensorActionData act_data = multi_act_data.sensorActionDatas.get(i);
			// 運動データの時間情報を取得
			long[] time = act_data.getTime();
			// 運動データの最初の時間を取得
			long first_time = time[0];
			// 運動データの最後の時間を取得
			long last_time = time[act_data.getIndex() - 1];
			// 作成する時間補間運動データのデータ数を取得
			int data_num = (int) (last_time - first_time + 1);
			// 運動データの時間長の新しい運動データ（時間補間）オブジェクトを生成
			SensorActionData act_data_normalized = new SensorActionData(data_num);
			// 運動データの時間を補間して運動データ（時間補間）オブジェクトに設定
			act_data_normalized.normalizeData(act_data);

			// 通信ナンバーを取得
			int commu_num = act_data.getCommu_index();


			//テキスト出力用ナンバー更新
			multi_per_data.count_index[i]++;
			if (multi_per_data.count_index[i] > 220 && commu_num < 20) {
				multi_per_data.txt_index[i]++;
				multi_per_data.count_index[i] = 0;
			}

			// 通信ナンバーの運動データ（時間補間）を運動データ（時間補間）リストに挿入
			multi_per_data.multi_act_data_normalized_array[i][commu_num] = act_data_normalized;

			//テキスト出力用
			OutPutText.output_txt(multi_per_data.name + i + "normalized_s",
					act_data_normalized.toString(), commu_num
							+ (multi_per_data.txt_index[i] * 256), false);

			// 通信ナンバーが最新だった場合個人データの最新通信ナンバーを更新
			if (commu_num > multi_per_data.normalized_data_commu_num[i] || commu_num == 0) {
				multi_per_data.normalized_data_commu_num[i] = commu_num;
				if (multi_per_data.normalized_data_commu_num[i] == SettingValues.STORE_NORMALIZED_DATA_NUM - 5)
					multi_per_data.normalized_data_num[i]++;
			}

		}






	}

}
