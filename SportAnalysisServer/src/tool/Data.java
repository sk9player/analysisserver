package tool;

import java.util.ArrayList;

import setting.SettingValues;

public class Data {// Dataクラスにたくさんの配列が入ってる。データクラスはデータそのもの
	public ArrayList<Double> time_list = new ArrayList<Double>();
	public ArrayList<Double> x_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> y_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> z_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> x_acc_list = new ArrayList<Double>();
	public ArrayList<Double> y_acc_list = new ArrayList<Double>();
	public ArrayList<Double> z_acc_list = new ArrayList<Double>();
	public ArrayList<Double> x_magneticfield_list = new ArrayList<Double>();
	public ArrayList<Double> y_magneticfield_list = new ArrayList<Double>();
	public ArrayList<Double> z_magneticfield_list = new ArrayList<Double>();
	public ArrayList<Double> x_absolute_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> y_absolute_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> z_absolute_angularvelocity_list = new ArrayList<Double>();
	public ArrayList<Double> a_quaternion_angle_list = new ArrayList<Double>();
	public ArrayList<Double> b_quaternion_angle_list = new ArrayList<Double>();
	public ArrayList<Double> c_quaternion_angle_list = new ArrayList<Double>();
	public ArrayList<Double> d_quaternion_angle_list = new ArrayList<Double>();
	public ArrayList<Double> x_angle_list = new ArrayList<Double>();
	public ArrayList<Double> y_angle_list = new ArrayList<Double>();
	public ArrayList<Double> z_angle_list = new ArrayList<Double>();
	public ArrayList<Double> x_linear_acceleration_list = new ArrayList<Double>();
	public ArrayList<Double> y_linear_acceleration_list = new ArrayList<Double>();
	public ArrayList<Double> z_linear_acceleration_list = new ArrayList<Double>();
	public ArrayList<Double> atmospheric_pressure_list = new ArrayList<Double>();
	public ArrayList<Double> altitude_list = new ArrayList<Double>();
	public ArrayList<Double> system_time_list = new ArrayList<Double>();
	public ArrayList<Double> velocity_list = new ArrayList<Double>();
	public ArrayList<Double> latitude_list = new ArrayList<Double>();
	public ArrayList<Double> longitude_list = new ArrayList<Double>();

	public double[][] getDoubleMatrix() {
		double[][] data = new double[10][SettingValues.ANALYSIS_SENSOR_DATA_NUM];
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM; i++) {
			data[0][i] = velocity_list.get(i);
			data[1][i] = z_angle_list.get(i);
			data[2][i] = x_angle_list.get(i);
			data[3][i] = z_linear_acceleration_list.get(i);
			data[4][i] = x_linear_acceleration_list.get(i);
			data[5][i] = y_linear_acceleration_list.get(i);
			data[6][i] = x_linear_acceleration_list.get(i);
		}
		return data;
	}
}
