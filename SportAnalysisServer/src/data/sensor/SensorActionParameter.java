package data.sensor;

import setting.ModeValues;

/**
 * 運動パラメータのクラス 運動データから分析したパラメータを格納する機能を有する
 *
 * @author OZAKI
 *
 */
public class SensorActionParameter {


	/**
	 * 滑走状態(初期は分析失敗状態)
	 */
	private int state=ModeValues.FAIL_STATE;

	/**
	 * パラメータを取得した時間
	 */
	private long last_time;

	/**
	 * z軸オイラー角（上半身の向き）の最大値から最小値の差分の平均
	 */
	private double eurz_diff_ave;
	/**
	 * x軸オイラー角（上半身の傾斜）の平均
	 */
	private double eurx_ave;
	/**
	 * y軸オイラー角（上半身の左右傾斜）の平均
	 */
	private double eury_ave;

	/**
	 * 現在緯度
	 */
	private float now_latitude;
	/**
	 * 現在経度
	 */
	private float now_longitude;

	/**
	 * z軸方向運動量（から質量を除いた値） 比較用に用いるので質量は無視する
	 */
	private double move_z;
	/**
	 * 水平方向運動量（から質量を除いた値） 比較用に用いるので質量は無視する
	 */
	private double move_xy;
	/**
	 * 左右方向運動量（から質量を除いた値） 比較用に用いるので質量は無視する センサ座標系加速度から算出
	 */
	private double move_x;
	/**
	 * 行動の１周期の長さ
	 */
	private double cycle;
	/**
	 * 平均速度(m/s)/行動の１周期の長さ=ストライド？
	 */
	private double stride;

	/**
	 * 平均速度(km/h)
	 */
	private double ave_speed;

	/**
	 * コンストラクタ
	 */
	public SensorActionParameter() {
	}

	/**
	 * コンストラクタ（値を設定）
	 *
	 * @param action_para
	 */
	public SensorActionParameter(SensorActionParameter action_para) {
		last_time = action_para.getLast_time();
		cycle = action_para.getCycle();
		eurz_diff_ave = action_para.getEurz_diff_ave();
		eurx_ave = action_para.getEurx_ave();
		eury_ave = action_para.getEury_ave();
		ave_speed = action_para.getAve_speed();
		move_z = action_para.getMove_z();
		move_xy = action_para.getMove_xy();
		now_latitude = action_para.getNow_latitude();
		now_longitude = action_para.getNow_longitude();
	}

	/**
	 * 値を0にリセット
	 */
	public void resetParamater(){
		last_time = 0;
		cycle = 0;
		eurz_diff_ave = 0;
		eurx_ave = 0;
		eury_ave = 0;
		ave_speed = 0;
		move_z = 0;
		move_xy = 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(last_time);
		sb.append("\t");
		sb.append(cycle);
		sb.append("\t");
		sb.append(eurz_diff_ave);
		sb.append("\t");
		sb.append(eurx_ave);
		sb.append("\t");
		sb.append(eury_ave);
		sb.append("\t");
		sb.append(ave_speed);
		sb.append("\t");
		sb.append(now_latitude);
		sb.append("\t");
		sb.append(now_longitude);
		sb.append("\t");

		return sb.toString();
	}

	/**
	 *
	 * @return
	 */
	public String toComString() {
		StringBuilder sb = new StringBuilder();
		sb.append(last_time);
		sb.append(",");
		sb.append(String.format("%.2f", cycle));
		sb.append(",");
		sb.append(String.format("%.2f",eurz_diff_ave));
		sb.append(",");
		sb.append(String.format("%.2f",eurx_ave));
		sb.append(",");
		sb.append(String.format("%.2f",eury_ave));
		sb.append(",");
		sb.append(ave_speed);
		sb.append(",");
		sb.append(String.format("%.2f",move_z));
		sb.append(",");
		sb.append(String.format("%.2f",move_xy));
		sb.append(",");
		sb.append(String.format("%.2f",move_x));
		sb.append(",");
		sb.append(now_latitude);
		sb.append(",");
		sb.append(now_longitude);

		return sb.toString();
	}

	public float getNow_latitude() {
		return now_latitude;
	}

	public void setNow_latitude(float now_latitude) {
		this.now_latitude = now_latitude;
	}

	public float getNow_longitude() {
		return now_longitude;
	}

	public void setNow_longitude(float now_longitude) {
		this.now_longitude = now_longitude;
	}

	public double getCycle() {
		return cycle;
	}

	public void setCycle(double cycle) {
		this.cycle = cycle;
	}

	public double getAve_speed() {
		return ave_speed;
	}

	public void setAve_speed(double ave_speed) {
		this.ave_speed = ave_speed;
	}

	public double getStride() {
		return stride;
	}

	public void setStride(double stride) {
		this.stride = stride;
	}

	public double getEurz_diff_ave() {
		return eurz_diff_ave;
	}

	public void setEurz_diff_ave(double eurz_diff_ave) {
		this.eurz_diff_ave = eurz_diff_ave;
	}

	public double getEurx_ave() {
		return eurx_ave;
	}

	public void setEurx_ave(double eurx_ave) {
		this.eurx_ave = eurx_ave;
	}

	public long getLast_time() {
		return last_time;
	}

	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}

	public double getMove_z() {
		return move_z;
	}

	public void setMove_z(double move_z) {
		this.move_z = move_z;
	}

	public double getMove_xy() {
		return move_xy;
	}

	public void setMove_xy(double move_xy) {
		this.move_xy = move_xy;
	}

	public double getMove_x() {
		return move_x;
	}

	public void setMove_x(double move_x) {
		this.move_x = move_x;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getEury_ave() {
		return eury_ave;
	}

	public void setEury_ave(double eury_ave) {
		this.eury_ave = eury_ave;
	}

}
