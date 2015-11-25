package analysis.multiSensor;

import sub.Debug;
import analysis.calc.Calculate;
import data.multiSensor.MultiSensorActionFeature;
import data.multiSensor.MultiSensorPersonalData;
import data.sensor.SensorActionData;

public class RunningAnalyzer  implements MultiSensorDataAnalyzer{

	@Override
	public void analysis(MultiSensorPersonalData per_data,
			double[][][] sensor_datas) {
		if (per_data.count_normalized_data_num()) {
			running_analysis(per_data,sensor_datas);
			per_data.analysis_count++;
		}

	}

	/**
	 * ランニングの特徴抽出
	 * @param per_data
	 * @param sensor_datas
	 */
	private void running_analysis(MultiSensorPersonalData per_data,double[][][] datas) {

		int sensor_num=per_data.sensor_num;

		double[][][] sensor_datas=cutout_sensor_datas(datas);

		//動作特徴:ピッチを分析
		per_data.features.setFeature(Calculate.getCycle(sensor_datas[0][SensorActionData.WLINZ]),0,MultiSensorActionFeature.FREQ_FEATURE_INDEX);
		Debug.debug_print("ピッチ："+per_data.features.getFeature(0, MultiSensorActionFeature.FREQ_FEATURE_INDEX), 99999999);

	}

	/**
	 * 分析データの切り出し
	 * @param datas
	 * @return
	 */
	private double[][][] cutout_sensor_datas(double[][][] datas){
		//Calculate.digital_low_pass_filter();
		//Calculate.subAverage();

		//ゼロクロス


		return datas;
	}


}
