package analysis.multiSensor;

import setting.SettingValues;
import sub.Debug;
import analysis.calc.Calculate;
import data.multiSensor.MultiSensorActionFeature;
import data.multiSensor.MultiSensorPersonalData;

public class DrySkatingAnalyzer implements MultiSensorDataAnalyzer{

	@Override
	public void analysis(MultiSensorPersonalData per_data,
			double[][][] sensor_datas) {
		if (per_data.count_normalized_data_num()) {
			dry_skating_state_analysis(per_data,sensor_datas);
			per_data.analysis_count++;
		}

	}


	/**
	 * ドライスケーティングの分析
	 * @param per_data
	 * @param sensor_datas
	 */
	private void dry_skating_state_analysis(MultiSensorPersonalData per_data,double[][][] datas) {

		int sensor_num=per_data.sensor_num;

		double[][][] sensor_datas=cutout_sensor_datas(datas);

		for (int isensor=0;isensor<sensor_num;isensor++){
			for (int iaxis=0;iaxis<SettingValues.ANALYSIS_DATA_AXIS_NUM;iaxis++){
				//動作特徴1:平均値を分析
				per_data.features.setFeature(Calculate.getAverage(sensor_datas[isensor][iaxis],SettingValues.ANALYSIS_SENSOR_DATA_NUM),isensor,MultiSensorActionFeature.AVERAGE_FEATURE_INDEX);
				Debug.debug_print("センサ："+isensor+"軸"+iaxis+"平均値:"+per_data.features.getFeature(isensor,MultiSensorActionFeature.AVERAGE_FEATURE_INDEX),99);

				//動作特徴2:値域を分析
				per_data.features.setFeature(Calculate.getRange(sensor_datas[isensor][iaxis],SettingValues.ANALYSIS_SENSOR_DATA_NUM),isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX);
				Debug.debug_print("センサ："+isensor+"軸"+iaxis+"値域:"+per_data.features.getFeature(isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX),99);

				//動作特徴3:周期を分析
				per_data.features.setFeature(Calculate.getCycle(sensor_datas[isensor][iaxis]),isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX);
				Debug.debug_print("センサ："+isensor+"軸"+iaxis+"周期:"+per_data.features.getFeature(isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX),99);

				//動作特徴4:位相を分析
				per_data.features.setFeature(Calculate.getAverage(sensor_datas[isensor][iaxis],SettingValues.ANALYSIS_SENSOR_DATA_NUM),isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX);
				Debug.debug_print("センサ："+isensor+"軸"+iaxis+"位相:"+per_data.features.getFeature(isensor,MultiSensorActionFeature.RANGE_FEATURE_INDEX),99);

			}

		}




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
