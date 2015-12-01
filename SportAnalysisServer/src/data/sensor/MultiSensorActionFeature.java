package data.sensor;

/**
 * 運動パラメータのクラス 運動データから分析したパラメータを格納する機能を有する
 *
 * @author OZAKI
 *
 */
public class MultiSensorActionFeature {


	/**
	 * 平均値
	 */
	private double average_feature;
	/**
	 * 最大値
	 */
	private double max_feature;
	/**
	 * 最小値
	 */
	private double min_feature;
	/**
	 * 値域（最大-最小）
	 */
	private double range_feature;
	/**
	 * 波長（1周期の時間）
	 */
	private double freq_feature;
	/**
	 * 位相
	 */
	private double phase_feature;


	/**
	 * コンストラクタ
	 */
	public MultiSensorActionFeature() {
	}

	/**
	 * コンストラクタ（値を設定）
	 *
	 * @param features
	 */
	public MultiSensorActionFeature(MultiSensorActionFeature features) {
		this.average_feature = features.getAverage_feature();
		this.max_feature = features.max_feature;
		this.min_feature = features.min_feature;
		this.range_feature = features.range_feature;
		this.freq_feature = features.freq_feature;
		this.phase_feature = features.phase_feature;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(average_feature);
		sb.append("\t");
		sb.append(max_feature);
		sb.append("\t");
		sb.append(min_feature);
		sb.append("\t");
		sb.append(range_feature);
		sb.append("\t");
		sb.append(freq_feature);
		sb.append("\t");
		sb.append(phase_feature);
		sb.append("\t");

		return sb.toString();
	}



	public double getAverage_feature() {
		return average_feature;
	}

	public void setAverage_feature(double average_feature) {
		this.average_feature = average_feature;
	}

	public double getMax_feature() {
		return max_feature;
	}

	public void setMax_feature(double max_feature) {
		this.max_feature = max_feature;
	}

	public double getMin_feature() {
		return min_feature;
	}

	public void setMin_feature(double min_feature) {
		this.min_feature = min_feature;
	}

	public double getRange_feature() {
		return range_feature;
	}

	public void setRange_feature(double range_feature) {
		this.range_feature = range_feature;
	}

	public double getFreq_feature() {
		return freq_feature;
	}

	public void setFreq_feature(double freq_feature) {
		this.freq_feature = freq_feature;
	}

	public double getPhase_feature() {
		return phase_feature;
	}

	public void setPhase_feature(double phase_feature) {
		this.phase_feature = phase_feature;
	}

}
