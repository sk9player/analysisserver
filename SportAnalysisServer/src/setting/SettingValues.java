package setting;

/**
 * 各種設定値
 *
 * @author OZAKI
 *
 */
public class SettingValues {

	/* JavaFX GUI関連 */
	public static boolean USE_GUI = false;


	/* データ関連 */

	/**
	 * bluetoothの最大接続数
	 */
	public static final int BLUETOOTH_MAX = 7;

	/**
	 * サーバのメモリに保持する（分析の計算に用いる）データ数
	 */
	public static final int STORE_DATA_NUM = 5000;

	/**
	 * サーバのメモリに保持する（分析の計算に用いる）時間正規化データ数
	 */
	public static final int STORE_TIME_NORMALIZED_DATA_NUM = 100000;

	/**
	 * 1度に受信するデータの数(スマホ用)
	 */
	public static final int ACC_DATA_NUM = 100;
	/**
	 * 1度に受信するデータの数(センサ用)
	 */
	public static int ACC_DATA_NUM_S = 50;
	/**
	 * 1度に受信するデータの種類（Byte型の配列の数）
	 */
	public static final int ACC_DATA_KIND = 26;
	/**
	 * 保存する時間正規化運動データ配列の数
	 */
	public static final int STORE_NORMALIZED_DATA_NUM = 256;
	/**
	 * 分析に用いるデータの種別数
	 */
	public static final int ANALYSIS_DATA_AXIS_NUM = 3;

	/* グラフ関連 */

	/**
	 * グラフ表示するデータの種類
	 */
	public static final int GRAPH_DATA_KIND_NUM = 7;

	/* 分析関連 */

	/**
	 * 1度に分析を行うスマホ運動データの数
	 */
	public static final int ANALYSIS_SMART_PHONE_DATA_NUM = 2048;

	/**
	 * 周波数分析用にスマホ運動データをN倍に増やす係数
	 */
	public static final int ANALYSIS_SMART_PHONE_DATA_MUL_NUM = 128;

	/**
	 * 1度に分析を行うセンサ運動データの数
	 */
	// public static final int ANALYSIS_SENSOR_DATA_NUM = 8192;
	public static final int ANALYSIS_SENSOR_DATA_NUM = 4096;

	/**
	 * 周波数分析用にセンサ運動データをN倍に増やす係数
	 */
	// public static final int ANALYSIS_SENSOR_DATA_MUL_NUM = 8;
	public static final int ANALYSIS_SENSOR_DATA_MUL_NUM = 16;

	/**
	 * データの周波数
	 */
	public static final double SENSOR_DATA_FREQUENCY = 1000;

	/**
	 * データの遮断周波数
	 */
	public static final double SENSOR_DATA_CUT_FREQUENCY = 5;

	/* 状態推定関連 */
	/**
	 * 1度に状態推定を行うセンサ運動データの数
	 */
	public static final int CATEGORIZE_SENSOR_DATA_NUM = 2048;

	/**
	 * 状態推定を行うときの閾値補正
	 */
	// public static double THRESHOLD_VAL=0;
	public static double THRESHOLD_VAL = 0.3;

	/* コーチング関連 */

	/**
	 * 差分通知コーチングで同じコーチングを行わない回数
	 */
	// public static final int SAME_COATCHING_ID_COUNT = 3;
	public static final int SAME_COATCHING_ID_COUNT = 0;

	/**
	 * スコア係数を算出する為の最大ピッチ
	 */
	public static double MAX_PITCH = 1.85;
	/**
	 * スコア係数を算出する為の最大上体の振りの角度
	 */
	public static double MAX_DIFF_AVEZ = 35;
	/**
	 * スコア係数を算出する為の最大上体傾斜角度
	 */
	public static double MAX_AVEX = 90;
	/**
	 * スコア係数を算出する為の最小ピッチ
	 */
	public static double MIN_PITCH = 1.55;
	/**
	 * スコア係数を算出する為の最小上体の振りの角度
	 */
	public static double MIN_DIFF_AVEZ = 25;
	/**
	 * スコア係数を算出する為の最小上体傾斜角度
	 */
	public static double MIN_AVEX = 80;

	/**
	 * ピッチの差分からスコアを算出する係数
	 */
	public static int PITCH_SCORE_COEF = 666;
	/**
	 * 上体の振りの角度の差分からスコアを算出する係数
	 */
	public static int DIFF_AVEZ_SCORE_COEF = 20;

	/**
	 * 上体傾斜角度の差分からスコアを算出する係数
	 */
	public static int AVEX_SCORE_COEF = 20;
	/**
	 * 上体左右傾斜角度の差分からスコアを算出する係数
	 */
	public static int AVEY_SCORE_COEF = 20;

	/**
	 * z軸加速度絶対値の積分の差分からスコアを算出する係数
	 */
	public static int MOVEZ_SCORE_COEF = 2;

	/**
	 * xy軸加速度絶対値の積分の差分からスコアを算出する係数
	 */
	public static int MOVEXY_SCORE_COEF = 2;

	/**
	 * x軸加速度絶対値の積分の差分からスコアを算出する係数
	 */
	public static int MOVEX_SCORE_COEF = 2;

	/**
	 * コーチングスコア閾値 この値以下の前パラメータがこのスコア以下の場合コーチングを行わない
	 */
	public static double COACH_SCORE_THRESHOLD = 100;

	/**
	 * コーチング頻度 コーチモードでコーチングを行う頻度（何回に１回コーチングを行うか）
	 */
	public static int COACH_FREQ = 5;

	/**
	 * コーチング実施 コーチモードでコーチングを実施するかどうかのスイッチ （1:実施 0:実施しない）
	 */
	public static int COACH_ONOFF = 1;

	/**
	 * 状態推定頻度 状態推定モードでコーチングを行う頻度（何回に１回コーチングを行うか）
	 */
	public static int STATE_FREQ = 3;

	/**
	 * ベストなピッチ
	 */
	// public static double BEST_CYCLE = 1.7;ストレート
	// public static double BEST_CYCLE = 1.7;両足支持
	public static double BEST_CYCLE = 0.8;

	/**
	 * ベストな上体傾斜平均
	 */
	// public static double BEST_EURX_AVE = -80.0;ストレート
	public static double BEST_EURX_AVE = -70.0;

	/**
	 * ベストな上体向き遷移平均
	 */
	// public static double BEST_EURZ_DIFF_AVE = 20.0;ストレート
	public static double BEST_EURZ_DIFF_AVE = 15.0;

	public static String get_str_para() {
		StringBuilder sb = new StringBuilder();
		sb.append("1,");
		sb.append(System.currentTimeMillis());
		sb.append(",");
		sb.append(COACH_ONOFF);
		sb.append(",");
		sb.append(THRESHOLD_VAL);
		sb.append(",");
		sb.append(COACH_FREQ);
		sb.append(",");
		sb.append(COACH_SCORE_THRESHOLD);
		sb.append(",");
		sb.append(MAX_PITCH);
		sb.append(",");
		sb.append(MAX_DIFF_AVEZ);
		sb.append(",");
		sb.append(MAX_AVEX);
		sb.append(",");
		sb.append(MIN_PITCH);
		sb.append(",");
		sb.append(MIN_DIFF_AVEZ);
		sb.append(",");
		sb.append(MIN_AVEX);
		sb.append(",");
		sb.append(BEST_CYCLE);
		sb.append(",");
		sb.append(BEST_EURZ_DIFF_AVE);
		sb.append(",");
		sb.append(BEST_EURX_AVE);
		return sb.toString();

	}

	public static String get_para() {
		StringBuilder sb = new StringBuilder();
		sb.append("1,");
		sb.append(COACH_ONOFF);
		sb.append(",");
		sb.append(THRESHOLD_VAL);
		sb.append(",");
		sb.append(COACH_FREQ);
		sb.append(",");
		sb.append(COACH_SCORE_THRESHOLD);
		sb.append(",");
		sb.append(MAX_PITCH);
		sb.append(",");
		sb.append(MAX_DIFF_AVEZ);
		sb.append(",");
		sb.append(MAX_AVEX);
		sb.append(",");
		sb.append(MIN_PITCH);
		sb.append(",");
		sb.append(MIN_DIFF_AVEZ);
		sb.append(",");
		sb.append(MIN_AVEX);
		sb.append(",");
		sb.append(BEST_CYCLE);
		sb.append(",");
		sb.append(BEST_EURZ_DIFF_AVE);
		sb.append(",");
		sb.append(BEST_EURX_AVE);
		return sb.toString();

	}

	/**
	 * 設定名リスト
	 */
	public static String[] setting_names = { "COACH_ONOFF","THRESHOLD_VAL", "COACH_FREQ",
			"COACH_SCORE_THRESHOLD", "MAX_PITCH", "MIN_PITCH", "BEST_CYCLE",
			"MAX_DIFF_AVEZ", "MIN_DIFF_AVEZ", "BEST_EURZ_DIFF_AVE", "MAX_AVEX",
			"MIN_AVEX", "BEST_EURX_AVE" };
	/**
	 * 設定値リスト
	 * @return setting_values
	 */
	public static double[] getSettingValues() {
		double[] setting_values = { COACH_ONOFF,THRESHOLD_VAL,
				COACH_FREQ, COACH_SCORE_THRESHOLD,
				MAX_PITCH,MIN_PITCH,BEST_CYCLE,
				MAX_DIFF_AVEZ,MIN_DIFF_AVEZ,BEST_EURZ_DIFF_AVE,
				MAX_AVEX,	MIN_AVEX , BEST_EURX_AVE};
		return setting_values;
	}

	public static void setSettingValues(int index,double value) {
		switch(index){
		case 0:
			COACH_ONOFF=(int) value;
			break;
		case 1:
			THRESHOLD_VAL= value;
			break;
		case 2:
			COACH_FREQ=(int) value;
			break;
		case 3:
			COACH_SCORE_THRESHOLD= value;
			break;
		case 4:
			MAX_PITCH=value;
			break;
		case 5:
			MIN_PITCH= value;
			break;
		case 6:
			BEST_CYCLE= value;
			break;
		case 7:
			MAX_DIFF_AVEZ=value;
			break;
		case 8:
			MIN_DIFF_AVEZ= value;
			break;
		case 9:
			BEST_EURZ_DIFF_AVE= value;
			break;
		case 10:
			MAX_AVEX=value;
			break;
		case 11:
			MIN_AVEX= value;
			break;
		case 12:
			BEST_EURX_AVE= value;
			break;

		}

	}
}
