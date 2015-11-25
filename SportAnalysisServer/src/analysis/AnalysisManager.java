package analysis;

import analysis.multiSensor.CreateMultiSensorParameterAnalysis;
import analysis.sensor.CreateSensorParameterAnalysis;
import analysis.smartPhone.CreateSmartPhoneParameterAnalysis;

/**
 * 運動データ分析処理管理クラス
 * @author OZAKI
 *
 */
public class AnalysisManager {

	/**
	 * スマホ運動データを分析してパラメータを生成するクラス
	 */
	public static CreateSmartPhoneParameterAnalysis Create_para = new CreateSmartPhoneParameterAnalysis();

	/**
	 * センサ運動データを分析してパラメータを生成するクラス
	 */
	public static CreateSensorParameterAnalysis Create_sensor_para = new CreateSensorParameterAnalysis();

	/**
	 * 複数センサ運動データを分析してパラメータを生成するクラス
	 */
	public static CreateMultiSensorParameterAnalysis Create_multi_sensor_para = new CreateMultiSensorParameterAnalysis();


}
