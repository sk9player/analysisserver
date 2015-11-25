package data.multiSensor;

/**
 * 運動パラメータのクラス 運動データから分析したパラメータを格納する機能を有する
 * 
 * @author OZAKI
 * 
 */
public class MultiSensorActionFeature {

	/**
	 * 特徴の数
	 */
	public static final int FEATURE_NUM=6;
	/**
	 * 平均値のインデックス
	 */
	public static final int AVERAGE_FEATURE_INDEX=0;
	/**
	 * 最大値のインデックス
	 */
	public static final int MAX_FEATURE_INDEX=1;
	/**
	 * 最小値のインデックス
	 */
	public static final int MIN_FEATURE_INDEX=2;
	/**
	 * 値域（最大-最小）のインデックス
	 */
	public static final int RANGE_FEATURE_INDEX=3;
	/**
	 * 波長（1周期の時間）のインデックス
	 */
	public static final int FREQ_FEATURE_INDEX=4;
	/**
	 * 位相のインデックス
	 */
	public static final int PHASE_FEATURE_INDEX=5;
	
	/**
	 * センサの数
	 */
	private int sensor_num=0;
	
	/**
	 * 特徴値
	 */
	private double[][] feature_data;
	

	/**
	 * コンストラクタ
	 */
	public MultiSensorActionFeature(int sensor_num) {
		this.sensor_num=sensor_num;
		feature_data=new double[sensor_num][FEATURE_NUM];
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int isensor=0;isensor<sensor_num;isensor++){
			for(int ifeature=0;ifeature<FEATURE_NUM;ifeature++){
				sb.append(feature_data[isensor][ifeature]);
				sb.append("\t");
			}
		}

		return sb.toString();
	}


	public double[][] getAllFeature_data() {
		return feature_data;
	}


	public void setAllFeature_data(double[][] feature_data) {
		this.feature_data = feature_data;
	}

	/**
	 * 
	 * @param sensor
	 * @param feature_index
	 * @return
	 */
	public double getFeature(int sensor,int feature_index) {
		return feature_data[sensor][feature_index];
	}


	/**
	 * 
	 * @param feature_data
	 * @param sensor
	 * @param feature_index
	 */
	public void setFeature(double feature,int sensor,int feature_index) {
		this.feature_data[sensor][feature_index] = feature;
	}


	
}
