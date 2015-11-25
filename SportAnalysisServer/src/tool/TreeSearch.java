package tool;

import java.io.BufferedReader;
import java.io.File;//javaがつくったやつを持ってくる
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import analysis.categorize.tree.SkatingStateTree;



public class TreeSearch {

	static int debugmode = 10;
	
	public static double th0 = 5;// GPSの停止判定
	public static double th1 = 40;// 加速度のスタート判定40~60
	public static double th2 = 50;// 加速度の休み判定
	public static double th3 = 50;// 角度のコーナー判定
	public static double th4 = 80;// 加速度のスケート判定
	public static double th5 = 2.5;// 周波数のクロス判定
	public static double th6 = 2.5;// 周波数のクロス判定

	public static void main(String[] args) {// TODO Auto-generated method
											// stub概念はクラス、物はオブジェクト

		Data data = getData();// staticだからAaa.getData()を省略してる
		
		//Dataをdoubleの配列に変更
		double[][] data_matrix = data.getDoubleMatrix();
		SkatingStateTree sst = new SkatingStateTree();
		sst.init();
		System.out.println(sst.calc(data_matrix));
	}

	private static Data getData() {
		Data data = new Data();// リストの場所を確保

		File in = new File("E:\\java練習\\test\\尾崎テスト01_2.txt");// inというファイルについてのオブジェクトを作ります,\tは特殊な意味なので\\tにする、newは実際に作ってる
		FileReader filereader;// 読み込みますクラスを確保、読み込む能力をもつfilereaderというクラスを作る為の場所を用意しました
		try {
			filereader = new FileReader(in);// inのファイルを読み込み機能を作った
			BufferedReader br = new BufferedReader(filereader);
			// filereaderを拡張して使う。buffredはテキストを01（バイナリ）で（まとめて）読める、ｂｒという土地があってinのファイルを読み込む機能を持っているfilereaderを持っているbuffreｄreaderをつくった
			String s;// 前もって宣言ｓの場所を確保
			while ((s = br.readLine()) != null) {// brは読み込む機能。readLineは１行を読む、現在何行読んだか分かる、配列で１個目に次の場所が書いてる、nullはその次の場所が書いてない
				// System.out.println(s);
				String[] ss = s.split("\t");// ｓｓという場所にｓのをタブで区切ったものがssに入る、区切りはstringじゃないとだめ
				// System.out.println(ss[0]);//javaの配列は０からss[0]にはタイムリストが入る
				double[] fs = new double[ss.length];// 配列をss.length個の場所を宣言
				for (int i = 0; i < ss.length; i++) {
					fs[i] = Double.parseDouble(ss[i]);// 文字列を数字に変換してfsに入れる、ss[]に入ってるものをfs[](配列)に入れてる
				}

				data.time_list.add(fs[0]);// time_list配列？
				data.x_angularvelocity_list.add(fs[1]);
				data.y_angularvelocity_list.add(fs[2]);
				data.z_angularvelocity_list.add(fs[3]);
				data.x_acc_list.add(fs[4]);
				data.y_acc_list.add(fs[5]);
				data.z_acc_list.add(fs[6]);
				data.x_magneticfield_list.add(fs[7]);
				data.y_magneticfield_list.add(fs[8]);
				data.z_magneticfield_list.add(fs[9]);
				data.x_absolute_angularvelocity_list.add(fs[10]);
				data.y_absolute_angularvelocity_list.add(fs[11]);
				data.z_absolute_angularvelocity_list.add(fs[12]);
				data.a_quaternion_angle_list.add(fs[13]);
				data.b_quaternion_angle_list.add(fs[14]);
				data.c_quaternion_angle_list.add(fs[15]);
				data.d_quaternion_angle_list.add(fs[16]);
				data.x_angle_list.add(fs[17]);
				data.y_angle_list.add(fs[18]);
				data.z_angle_list.add(fs[19]);
				data.x_linear_acceleration_list.add(fs[20]);
				data.y_linear_acceleration_list.add(fs[21]);
				data.z_linear_acceleration_list.add(fs[22]);
				data.atmospheric_pressure_list.add(fs[23]);
				data.altitude_list.add(fs[24]);
				data.system_time_list.add(fs[25]);
				data.velocity_list.add(fs[26]);
				data.latitude_list.add(fs[27]);
				data.longitude_list.add(fs[28]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// ファイルにアクセスできないエラーを出力してくれる
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// ファイルの入出力のエラーを出力してくれる
		}
		return data;// dataクラスを返す。返せるのは一つだが、クラスの中にいっぱい入れとけばいい
	}

}
