package setting;

/**
 * 各モードを決める定数
 * @author OZAKI
 *
 */
public class ModeValues {

	/**************************************分析ID（状態による分析種別値）***********************************************/
	/**
	 * 判定不能状態
	 */
	public static final int FAIL_STATE = 0;
	/**
	 * 静止状態
	 */
	public static final int STOP_STATE = 1;
	/**
	 * スタート状態
	 */
	public static final int START_STATE = 2;
	/**
	 * ストレート休み状態
	 */
	public static final int STRAIGHT_REST_STATE = 3;
	/**
	 * コーナー休み状態
	 */
	public static final int CORNER_REST_STATE = 4;
	/**
	 * ストレート滑走状態
	 */
	public static final int STRAIGHT_SKATING_STATE = 5;
	/**
	 * コーナー滑走状態
	 */
	public static final int CORNER_SKATING_STATE = 6;
	/**
	 * ストレートダッシュ状態
	 */
	public static final int STRAIGHT_DASH_STATE = 7;
	/**
	 * コーナーダッシュ滑走
	 */
	public static final int CORNER_DASH_STATE = 8;
	/**
	 * ドライスケーティング
	 */
	public static final int DRY_SKATING_STATE = 9;




	/**************************************考察ID（コーチングモード値）***********************************************/
	/**
	 * コーチングなし
	 */
	public static final int NO_COACH_MODE = 0;
	/**
	 * 前回のサイクル（ピッチ）と比較する方法
	 */
	public static final int LAST_CYCLE_COMPARE_MODE = 1;
	/**
	 * 設定したサイクル（ピッチ）と比較する方法
	 */
	public static final int SET_CYCLE_COMPARE_MODE = 2;
	/**
	 * 算出したサイクル（ピッチ）をそのまま返却する方法
	 */
	public static final int CYCLE_RETURN_MODE = 3;
	/**
	 * 上体の振りの角度返却
	 */
	public static final int FURI_RETURN_MODE = 4;

	/**
	 * 各パラメータを規定値と比較する総合的なコーチングモード
	 */
	public static final int GENERAL_COACH_MODE = 5;

	/**
	 * ストレート滑走限定の規定値と比較する総合的なコーチングモード
	 */
	public static final int STRAIGHT_COACH_MODE = 6;

	/**
	 * 各パラメータを最高安定時の値と比較する総合的なコーチングモード
	 */
	public static final int BEST_MOVE_COACH_MODE = 7;

	/**
	 * 滑走状態を返却するモード
	 */
	public static final int STATE_MODE = 8;

	/**
	 * 複数センサの値からコーチングするモード
	 */
	public static final int MULTI_SENSOR_COACH_MODE = 9;

}
