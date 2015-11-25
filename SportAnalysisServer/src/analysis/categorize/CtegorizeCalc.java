package analysis.categorize;

import setting.SettingValues;
import analysis.calc.Calculate;
import analysis.calc.spectrum.FFT.FFT;
import data.sensor.SensorActionData;

public class CtegorizeCalc {

	static int debugmode = 10;
	static final int fs=1000;

	/**
	 * Data型のdataを引数 accを計算して返却
	 *
	 * @param data
	 * @return　acc
	 */
	public static double getAcc(double[][] data) {
		// ・加速度（ABS）を求めるプログラム
		double[] xacc = data[SensorActionData.LINX];
		double[] yacc = data[SensorActionData.LINY];
		double[] zacc = data[SensorActionData.LINZ];
		double accall[] = new double[SettingValues.CATEGORIZE_SENSOR_DATA_NUM]; // fs0という配列を宣言,fs0に加速度を入れる
		double sum0 = 0.0;
		for (int i = 0; i < SettingValues.CATEGORIZE_SENSOR_DATA_NUM; i++) {
			accall[i] = Math.abs(xacc[i]) + Math.abs(yacc[i])
					+ Math.abs(zacc[i]);// 加速度を計算してfs0に入れる
			sum0 = sum0 + accall[i];
		}


		double acc = sum0 / SettingValues.CATEGORIZE_SENSOR_DATA_NUM;
		return acc;
	}

	public static double getTurn(double[][] data) {

		double[] zangle = data[SensorActionData.EULZ];
		double anglemax = zangle[1];
		double anglemin = zangle[1];
		for (int i = 1; i < SettingValues.CATEGORIZE_SENSOR_DATA_NUM; i++) {
			if (anglemax < zangle[i])
				anglemax = zangle[i];
			if (anglemin > zangle[i])
				anglemin = zangle[i];
		}
		return anglemax - anglemin;
	}

	/**
	 * １周期の時間を計算
	 * 現在未使用
	 * @param data
	 * @return
	 */
	static double getT_pitch(double[][] data) {
		double[] zangle = data[SensorActionData.EULZ];
		double sum1 = 0.0;
		for (int i = 0; i < SettingValues.CATEGORIZE_SENSOR_DATA_NUM; i++) {
			sum1 = sum1 + zangle[i];
		}
		double average = sum1 / SettingValues.CATEGORIZE_SENSOR_DATA_NUM;
		for (int i = 0; i < SettingValues.CATEGORIZE_SENSOR_DATA_NUM; i++) {
			zangle[i] -= average;
		}
		Calculate.set_window_func_s();
		zangle = Calculate.mul_window_func_s(zangle);
		zangle = FFT.fftAnalysis(zangle);
		int index = getmaxidx(zangle);
		double f_pitch = (double)fs / 2.0 * (double) index / (8192.0 / 2.0);
		double t_pitch = 1 / f_pitch;
		return t_pitch;
	}

	public static double getXaccave(double[][] data) {
		double[] xacc = data[SensorActionData.LINX];
		double xaccsum = 0.0;
		for (int i = 0; i < xacc.length; i++) {
			xaccsum = xaccsum + xacc[i];
		}
		double xaccave = xaccsum / xacc.length *9.8;
		return xaccave;
	}

	public static double getSpeed(double[][] data) {
		double[] speed = data[SensorActionData.SPEED];
		double sum1 = 0.0;
		for (int i = 0; i < speed.length; i++) {
			sum1 = sum1 + speed[i]/3.6;
		}
		double speedave = sum1 / speed.length;
		return speedave;
	}

	private static int getmaxidx(double[] d) {
		double dmax = d[4];
		int idx = 4;
		for (int i = 2; i < d.length / 2; i++) {
			if (d[i] > dmax) {
				dmax = d[i];
				idx = i;
			}
		}
		return idx;
	}

}
