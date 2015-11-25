package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

import data.parse.DataParse;

/**
 * スマホにストアするタイプのセンサデータを整形
 *
 * @author OZAKI スマホのセンサデータファイルをPCのセンサデータファイルの形式で再構築
 */
public class ParseSensorData {

	/**
	 * ラジアンからディグリーに変換
	 */
	private static final float R2D = 57.2958f;

	/**
	 * センサの数
	 */
	private static final int sensor_num = 2;


	/**
	 * 元データの出力元（true:スマホ false:PC）
	 */
	private static boolean mode=true;

	/**
	 * センサデータファイルのアドレス
	 */
//	private static String file_uri = "C:\\Users\\OZAKI\\Desktop\\GoogleDrive\\実験センサデータ\\コーナースケーティング\\20150302\\";
	private static String file_uri = "C:\\Users\\honlab\\Desktop\\20151022skating\\";




	private static String user_name="tanaka01";

	private static String[] in_files;
	private static String[] out_files;
	private static String sum_file;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// ファイルパスを作成
		create_sensor_file_path();

		if(mode){
			// それぞれのセンサについて角度等のデータを計算し追加
			out_put_sensor_files();
			create_sum_sensor_file();
		}else{
			out_put_PC_sensor_file();
		}


	}



	/**
	 * 入出力するセンサファイルのパスを生成
	 */
	private static void create_sensor_file_path() {

		in_files = new String[sensor_num];
		out_files = new String[sensor_num];
		for (int i = 0; i < sensor_num; i++) {
			in_files[i] = file_uri+user_name+
					+ i + ".txt";
			out_files[i] = file_uri+"編集後\\"+user_name+
					+ i + "out.txt";
		}
		sum_file = file_uri+"編集後\\"+user_name+"sum.txt";

	}

	/**
	 * パースしたセンサファイルを出力
	 */
	private static void out_put_sensor_files() {

		for (int i = 0; i < in_files.length; i++) {
			try {
				File input_file = new File(in_files[i]);
				File output_file = new File(out_files[i]);
				BufferedReader br = new BufferedReader(new FileReader(
						input_file));
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						output_file), 1024 * 1024);

				String str;
				int frame_num = 1;

				// ラベルを出力

				while ((str = br.readLine()) != null) {
					bw.write(purse_sensor_data(str, frame_num));
					bw.newLine();
					frame_num++;
				}

				br.close();
				bw.flush();
				bw.close();
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	private static void out_put_PC_sensor_file() {
		// TODO Auto-generated method stub
		for (int i = 0; i < in_files.length; i++) {
			try {
				File input_file = new File(in_files[i]);
				File output_file = new File(out_files[i]);
				BufferedReader br = new BufferedReader(new FileReader(
						input_file));
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						output_file), 1024 * 1024);

				String str;

				// ラベルを出力

				while ((str = br.readLine()) != null) {
					bw.write(purse_PC_sensor_data(str));
					bw.newLine();
				}

				br.close();
				bw.flush();
				bw.close();
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}

	private static String purse_PC_sensor_data(String data_line) {

		StringBuilder sb = new StringBuilder();
		String[] data = data_line.split(",");

		//ラベル行だったらそのまま出力
		if(data[0].equals("SensorId"))
			return data_line;

		/*
		 * SensorId, TimeStamp (s), FrameNumber, AccX (g), AccY (g), AccZ (g),
		 * GyroX (deg/s), GyroY (deg/s), GyroZ (deg/s), MagX (uT), MagY (uT),
		 * MagZ (uT), EulerX (deg), EulerY (deg), EulerZ (deg), QuatX, QuatY,
		 * QuatZ, QuatW, LinAccX (m/s^2), LinAccY (m/s^2), LinAccZ (m/s^2),
		 * Pressure (hPa), Altitude (m), Temperature (degC), HeaveMotion (m)
		 */
		// 1:SensorId
		sb.append(data[0]);
		sb.append(",");
		// 2:TimeStamp
		sb.append(data[1]);
		sb.append(",");
		// 3:FrameNumber
		sb.append(data[2]);
		sb.append(",");

		// 4:AccX
		sb.append(data[3]);
		sb.append(",");
		// 5:AccY
		sb.append(data[4]);
		sb.append(",");
		// 6:AccZ
		sb.append(data[5]);
		sb.append(",");

		// 7:GyroX
		sb.append(data[6]);
		sb.append(",");
		// 8:GyroY
		sb.append(data[7]);
		sb.append(",");
		// 9:GyroZ
		sb.append(data[8]);
		sb.append(",");

		// 10:MagX
		sb.append(data[9]);
		sb.append(",");
		// 11:MagY
		sb.append(data[10]);
		sb.append(",");
		// 12:MagZ
		sb.append(data[11]);
		sb.append(",");

		// ４元角からヨーピッチローを取得して設定
		float[] q = new float[4];
		q[0] = Float.parseFloat(data[15]);
		q[1] = Float.parseFloat(data[16]);
		q[2] = Float.parseFloat(data[17]);
		q[3] = Float.parseFloat(data[18]);
		float[] axsis3 = new float[3];
		axsis3 = DataParse.quaternionToEuler(q);
		// 13:EulerX
		sb.append(axsis3[0] * R2D);
		sb.append(",");
		// 14:EulerY
		sb.append(axsis3[1] * R2D);
		sb.append(",");
		// 15:EulerZ
		sb.append(axsis3[2] * R2D);
		sb.append(",");

		// 16:QuatX
		sb.append(data[15]);
		sb.append(",");
		// 17:QuatY
		sb.append(data[16]);
		sb.append(",");
		// 18:QuatZ
		sb.append(data[17]);
		sb.append(",");
		// 19:QuatW
		sb.append(data[18]);
		sb.append(",");

		// ４元角と加速度から線形加速度を取得して設定
		axsis3[0] = Float.parseFloat(data[3]);
		axsis3[1] = Float.parseFloat(data[4]);
		axsis3[2] = Float.parseFloat(data[5]);
		axsis3 = DataParse.accToLinacc(q, axsis3);
		// 20:LinX
		sb.append(axsis3[0]);
		sb.append(",");
		// 21:LinY
		sb.append(axsis3[1]);
		sb.append(",");
		// 22:LinZ
		sb.append(axsis3[2]);
		sb.append(",");

		// 23:Pressure (hPa)
		sb.append(axsis3[22]);
		sb.append(",");


		// 24:Altitude (m)
		sb.append(axsis3[23]);
		sb.append(",");


		// 25:Temperature (degC)
		sb.append(axsis3[24]);
		sb.append(",");


		// 26:HeaveMotion (m)
		sb.append(axsis3[25]);
		sb.append(",");

		// ４元角と加速度から絶対座標の線形加速度を取得して設定
		axsis3[0] = Float.parseFloat(data[3]);
		axsis3[1] = Float.parseFloat(data[4]);
		axsis3[2] = Float.parseFloat(data[5]);
		axsis3 = DataParse.accToWorldLinacc(q, axsis3);
		// 27:WLinX
		sb.append(axsis3[0]);
		sb.append(",");
		// 28:WLinY
		sb.append(axsis3[1]);
		sb.append(",");
		// 29:WLinZ
		sb.append(axsis3[2]);

		return sb.toString();
	}



	private static String purse_sensor_data(String data_line, int frame_num) {

		StringBuilder sb = new StringBuilder();
		String[] data = data_line.split("\t");

		// 1:SensorId
		sb.append(data[0]);
		sb.append(",");
		// 2:TimeStamp
		sb.append(data[1]);
		sb.append(",");
		// 3:FrameNumber
		sb.append(frame_num);
		sb.append(",");

		// 4:AccX
		sb.append(data[2]);
		sb.append(",");
		// 5:AccY
		sb.append(data[3]);
		sb.append(",");
		// 6:AccZ
		sb.append(data[4]);
		sb.append(",");

		// 7:GyroX
		sb.append("0");
		sb.append(",");
		// 8:GyroY
		sb.append("0");
		sb.append(",");
		// 9:GyroZ
		sb.append("0");
		sb.append(",");

		// 10:MagX
		sb.append("0");
		sb.append(",");
		// 11:MagY
		sb.append("0");
		sb.append(",");
		// 12:MagZ
		sb.append("0");
		sb.append(",");

		// ４元角からヨーピッチローを取得して設定
		float[] q = new float[4];
		q[0] = Float.parseFloat(data[5]);
		q[1] = Float.parseFloat(data[6]);
		q[2] = Float.parseFloat(data[7]);
		q[3] = Float.parseFloat(data[8]);
		float[] axsis3 = new float[3];
		axsis3 = DataParse.quaternionToEuler(q);
		// 13:EulerX
		sb.append(axsis3[0] * R2D);
		sb.append(",");
		// 14:EulerY
		sb.append(axsis3[1] * R2D);
		sb.append(",");
		// 15:EulerZ
		sb.append(axsis3[2] * R2D);
		sb.append(",");

		// 16:QuatX
		sb.append(data[5]);
		sb.append(",");
		// 17:QuatY
		sb.append(data[6]);
		sb.append(",");
		// 18:QuatZ
		sb.append(data[7]);
		sb.append(",");
		// 19:QuatW
		sb.append(data[8]);
		sb.append(",");

		// ４元角と加速度から線形加速度を取得して設定
		axsis3[0] = Float.parseFloat(data[2]);
		axsis3[1] = Float.parseFloat(data[3]);
		axsis3[2] = Float.parseFloat(data[4]);
		axsis3 = DataParse.accToLinacc(q, axsis3);
		// 20:LinX
		sb.append(axsis3[0]);
		sb.append(",");
		// 21:LinY
		sb.append(axsis3[1]);
		sb.append(",");
		// 22:LinZ
		sb.append(axsis3[2]);
		sb.append(",");

		// ４元角と加速度から絶対座標の線形加速度を取得して設定
		axsis3[0] = Float.parseFloat(data[2]);
		axsis3[1] = Float.parseFloat(data[3]);
		axsis3[2] = Float.parseFloat(data[4]);
		axsis3 = DataParse.accToWorldLinacc(q, axsis3);
		// 23:WLinX
		sb.append(axsis3[0]);
		sb.append(",");
		// 24:WLinY
		sb.append(axsis3[1]);
		sb.append(",");
		// 25:WLinZ
		sb.append(axsis3[2]);
		sb.append(",");
		// speed
		sb.append(data[10]);
		sb.append(",");
		// lati
		sb.append(data[11]);
		sb.append(",");
		// longi
		sb.append(data[12]);
		sb.append(",");
		// systime
		sb.append(data[9]);

		return sb.toString();
	}

	/**
	 * 結合したセンサファイルを出力
	 */
	private static void create_sum_sensor_file() {

		try {
			File output_file = new File(sum_file);
			File[] input_files = new File[out_files.length];
			BufferedReader[] brs = new BufferedReader[out_files.length];
			BufferedWriter bw = new BufferedWriter(new FileWriter(sum_file), 1024 * 1024);

			for (int i = 0; i < out_files.length; i++) {
				File input_file = new File(out_files[i]);
				input_files[i] = input_file;
				BufferedReader br = new BufferedReader(new FileReader(
						input_file));
				brs[i] = br;
			}

			String str;
			BitSet file_end = new BitSet(out_files.length);
			for (int i = 0; i < out_files.length; i++) {
				file_end.set(i, false);
			}

			bw.write("SensorId, TimeStamp (s), FrameNumber, AccX (g), AccY (g), AccZ (g), GyroX (deg/s), GyroY (deg/s), GyroZ (deg/s), MagX (uT), MagY (uT), MagZ (uT), EulerX (deg), EulerY (deg), EulerZ (deg), QuatX, QuatY, QuatZ, QuatW, LinAccX (m/s^2), LinAccY (m/s^2), LinAccZ (m/s^2), Pressure (hPa), Altitude (m), Temperature (degC), HeaveMotion (m)");
			bw.newLine();

			while (true) {
				for (int i = 0; i < out_files.length; i++) {
					if (!file_end.get(i) && (str = brs[i].readLine()) != null) {
						bw.write(str);
						bw.newLine();
					} else {
						file_end.set(i, true);
					}
				}

				if (file_end.cardinality() == out_files.length) {
					break;
				}
			}

			for (int i = 0; i < out_files.length; i++) {
				brs[i].close();
				bw.flush();
				bw.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
