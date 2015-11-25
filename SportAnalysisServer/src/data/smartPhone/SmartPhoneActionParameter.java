package data.smartPhone;

/**
 * 運動パラメータのクラス
 * 運動データから分析したパラメータを格納する機能を有する
 * @author OZAKI
 *
 */
public class SmartPhoneActionParameter {

	/**
	 * パラメータを取得した時間
	 */
	private long last_time;	
	/**
	 * 現在緯度
	 */
	private float now_latitude;
	/**
	 * 現在経度
	 */
	private float now_longitude;
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
	public SmartPhoneActionParameter(){		
	}
	
	/**
	 * コンストラクタ（値を設定）
	 * @param action_para
	 */
	public SmartPhoneActionParameter(SmartPhoneActionParameter action_para){
		last_time = action_para.getLast_time();
		now_latitude = action_para.getNow_latitude();
		now_longitude = action_para.getNow_longitude();
		cycle = action_para.getCycle();
		stride = action_para.getStride();
		ave_speed = action_para.getAve_speed();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getLast_time());
		sb.append("\t");
		sb.append(getNow_latitude());
		sb.append("\t");
		sb.append(getNow_longitude());
		sb.append("\t");
		sb.append(getCycle());
		sb.append("\t");
		sb.append(getAve_speed());
		sb.append("\t");
		sb.append(getStride());
		sb.append("\n");

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


	public long getLast_time() {
		return last_time;
	}


	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}
	
}
