package analysis.categorize;

public class CategorizeParameter {
	/**
	 * GPSの停止判定
	 */
	public static double th0 = -1;
//	public static double th0 = 5;

	/**
	 * 加速度のスタート判定40~60
	 */
	public static double th1 = 40;

	/**
	 * 加速度の休み判定
	 */
	public static double th2 = 50;

	/**
	 * 角度のコーナー判定
	 */
	public static double th3 = 50;

	/**
	 * 加速度のスケート判定
	 */
	public static double th4 = 80;

	/**
	 * 周波数のクロス判定
	 */
	public static double th5 = 2.5;

	/**
	 * 周波数のクロス判定
	 */
	public static double th6 = 2.5;
}
