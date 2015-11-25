package analysis.multiSensor;

import setting.ModeValues;
import sub.Debug;
import data.multiSensor.MultiSensorPersonalData;

public class CreateMultiSensorParameterAnalysis {

	MultiSensorDataAnalyzer analyzer;

	/**
	 * センサ運動データを分析して運動パラメータを返却
	 * @param per_data
	 * @return 分析回数を返却
	 */
	public int create(MultiSensorPersonalData per_data) {
		Debug.debug_print("CreateMultiSensorParameterAnalysis.create(PersonalData per_data)",1);
		//センサデータの配列を取得
		double[][][] sensor_datas = per_data.get_sensor_datas();

		if(per_data.coach_mode==ModeValues.MULTI_SENSOR_COACH_MODE){
			analyzer=new RunningAnalyzer();
		}
		//ここに新しいコーチングモードを追加



		analyzer.analysis(per_data, sensor_datas);

		return per_data.analysis_count;

	}


}
