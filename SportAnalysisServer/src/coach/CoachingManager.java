package coach;

import coach.multiSensor.MultiSensorCompareParameterCoach;
import coach.sensor.SensorCompareParameterCoach;
import coach.smartPhone.SmartPhoneCompareParameterCoach;

public class CoachingManager {

	/**
	 * 返却頻度
	 * 何回に1回返却するのか。（機能未実装）
	 */
	private final static int RETURN_FREQ = 1;

	/**
	 * 教師スマートフォンデータと比較
	 */
	public static SmartPhoneCompareParameterCoach Compare_sp = new SmartPhoneCompareParameterCoach(RETURN_FREQ);

	/**
	 * 教師センサデータと比較
	 */
	public static SensorCompareParameterCoach Compare_s = new SensorCompareParameterCoach(RETURN_FREQ);

	/**
	 * 教師複数センサデータと比較
	 */
	public static MultiSensorCompareParameterCoach Compare_m = new MultiSensorCompareParameterCoach(RETURN_FREQ);

	/**
	 * コーチングデータベースと比較
	 */
	public static DataBaseCoach Data_base = new DataBaseCoach(RETURN_FREQ);

	/**
	 * コーチングを外部入力
	 */
	public static InputCoach Input = new InputCoach(RETURN_FREQ);

}
