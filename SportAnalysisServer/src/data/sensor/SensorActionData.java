package data.sensor;

import java.io.IOException;

import sub.Base64;
import sub.Debug;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import data.ActionData;
import data.calc.CalcSensorData;
import data.parse.DataParse;

/**
 * 運動データのクラス 各種データとその格納機能を有する
 *
 * @author OZAKI
 *
 */
public class SensorActionData extends ActionData {

	/**
	 * 時間（ミリ秒）
	 */
	long[] time;

	/**
	 * x軸加速度 Accelerometer data in m/s^2　インデックス番号
	 */
	public static final int ACCX=0;
	/**
	 * y軸加速度 Accelerometer data in m/s^2　インデックス番号
	 */
	public static final int ACCY=1;
	/**
	 * z軸加速度 Accelerometer data in m/s^2　インデックス番号
	 */
	public static final int ACCZ=2;
	/**
	 * x軸四元数 Orientation quaternion　インデックス番号
	 */
	public static final int QUAX=3;
	/**
	 * y軸四元数 Orientation quaternion　インデックス番号
	 */
	public static final int QUAY=4;
	/**
	 * z軸四元数 Orientation quaternion　インデックス番号
	 */
	public static final int QUAZ=5;
	/**
	 * w軸四元数 Orientation quaternion　インデックス番号
	 */
	public static final int QUAW=6;
	/**
	 * x軸オイラー角 Euler angles in degrees　インデックス番号
	 */
	public static final int EULX=7;
	/**
	 * y軸オイラー角 Euler angles in degrees　インデックス番号
	 */
	public static final int EULY=8;
	/**
	 * z軸オイラー角 Euler angles in degrees　インデックス番号
	 */
	public static final int EULZ=9;
	/**
	 * x軸線形加速度 Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int LINX=10;
	/**
	 * y軸線形加速度 Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int LINY=11;
	/**
	 * z軸線形加速度 Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int LINZ=12;
	/**
	 * x軸絶対座標線形加速度 World Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int WLINX=13;
	/**
	 * y軸絶対座標線形加速度 World Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int WLINY=14;
	/**
	 * z軸絶対座標線形加速度 World Linear acceleration in m/s^2　インデックス番号
	 */
	public static final int WLINZ=15;
	/**
	 * 速度km/h　インデックス番号
	 */
	public static final int SPEED=16;
	/**
	 * 経度　longitude　インデックス番号
	 */
	public static final int LONGI=17;
	/**
	 * 緯度　latitude　インデックス番号
	 */
	public static final int LATI=18;



	/**
	 * 全ての軸のデータ
	 */
	private float[][] datas;

	public static final int DATA_KIND=19;

	/**
	 * ラジアンからディグリーに変換する定数
	 */
	private final float R2D = 57.2958f;

	/**
	 * データ数（各データの格納数）data_num　個のActionDataオブジェクトを生成
	 *
	 * @param data_num
	 */
	public SensorActionData(int data_num) {
		Debug.debug_print("ActionData(int data_num)", 1);
		max_index = data_num;
		time = new long[max_index];
		datas = new float[DATA_KIND][max_index];
	}

	/**
	 * 初期センサデータから初期キャリブレーションデータを設定
	 *
	 * @param act_data
	 */
	public void setFirstData(SensorActionData act_data) {
		Debug.debug_print("ActionData.setData(SensorActionData act_data)", 1);
		int data_num=act_data.getMax_index();
		for (int i = 0; i < data_num; i++) {
			time[0] += act_data.getTime()[i];
			for(int ikind = 0; ikind<DATA_KIND;ikind++){
				datas[ikind][0] += act_data.getDataUsingID(ikind)[i];
			}
		}
		time[0] /= data_num;
		for(int ikind = 0; ikind<DATA_KIND;ikind++){
			datas[ikind][0] /= data_num;
		}

		System.out.println("平均傾斜："+datas[EULX][0]);
		System.out.println("平均左右加速度："+datas[LINX][0]);
	}



	/**
	 * Base64のセンサデータをこのオブジェクトに挿入
	 *
	 * @param data
	 * @throws IOException
	 * @throws Base64DecodingException
	 */
	public void setData(String data) throws IOException {



		Debug.debug_print("ActionData.setData(String data)", 1);
		byte[] byte_data = Base64.decode(data, Base64.URL_SAFE);
		for (int o = 0; o < byte_data.length - 1;) {
			// ミリ秒単位にするため×１０００
			time[index] = (long) (CalcSensorData.convertRxbytesToFloat(o, byte_data) * 1000);
			o += 4;

			//加速度と四元数を設定
			for(int ikind=0;ikind<7;ikind++){
				datas[ikind][index]= CalcSensorData.convertRxbytesToFloat(o, byte_data);
				o += 4;
			}

			//４元角からヨーピッチローを取得して設定
			float[] q = new float[4];
			q[0]=datas[3][index];
			q[1]=datas[4][index];
			q[2]=datas[5][index];
			q[3]=datas[6][index];
			float[] axsis3 = new float[3];
			axsis3 = DataParse.quaternionToEuler(q);
			datas[7][index] = axsis3[0] * R2D;
			datas[8][index] = axsis3[1] * R2D;
			datas[9][index] = axsis3[2] * R2D;

			//４元角と加速度から線形加速度を取得して設定
			axsis3[0]=datas[0][index];
			axsis3[1]=datas[1][index];
			axsis3[2]=datas[2][index];
			axsis3=DataParse.accToLinacc(q, axsis3);
			datas[10][index] = axsis3[0];
			datas[11][index] = axsis3[1];
			datas[12][index] = axsis3[2];

			//４元角と加速度から絶対座標の線形加速度を取得して設定
			axsis3[0]=datas[0][index];
			axsis3[1]=datas[1][index];
			axsis3[2]=datas[2][index];
			axsis3=DataParse.accToWorldLinacc(q, axsis3);
			datas[13][index] = axsis3[0];
			datas[14][index] = axsis3[1];
			datas[15][index] = axsis3[2];


			datas[16][index] = byte_data[o++];
			byte b1[] = { byte_data[o++], byte_data[o++], byte_data[o++],
					byte_data[o++] };
			datas[17][index] = CalcSensorData.byte_to_float(b1);
			byte b2[] = { byte_data[o++], byte_data[o++], byte_data[o++],
					byte_data[o++] };
			datas[18][index] = CalcSensorData.byte_to_float(b2);

			index++;
		}
		if (byte_data[byte_data.length - 1] >= 0) {
			commu_index = byte_data[byte_data.length - 1];
		} else {
			commu_index = byte_data[byte_data.length - 1] + 256;
		}
		Debug.debug_print("通信ナンバー=" + commu_index, 99);

	}

	/**
	 * 他のアクションデータをこのオブジェクトに挿入
	 *
	 * @param act_data
	 */
	public void setData(SensorActionData act_data) {
		Debug.debug_print("ActionData.setData(SensorActionData act_data)", 1);
		for (int i = 0; i < act_data.getMax_index(); i++) {
			time[index] = act_data.getTime()[i];
			for(int ikind = 0; ikind<DATA_KIND;ikind++){
				datas[ikind][i] = act_data.getDataUsingID(ikind)[i];
			}

			index++;
		}
	}

	/**
	 * 他のアクションデータを時間で線形保管してこのオブジェクトに挿入
	 *
	 * @param act_data
	 */
	public void normalizeData(SensorActionData act_data) {
		Debug.debug_print(
				"ActionData.normalizeData(SensorActionData act_data)", 1);

		// ループ回数
		int orig_index = 0;

		// 時間が同時だった回数
		int same_time_count = 1;

		// 線形保管の為の保管時間のカウント
		int skip_count = 0;

		// 初回はそのまま値を挿入
		if (index == 0) {
			time[index] = act_data.getTime()[orig_index];

			for(int ikind = 0; ikind<DATA_KIND;ikind++){
				datas[ikind][index] = act_data.getDataUsingID(ikind)[orig_index];
			}

			index++;
			orig_index++;
		}

		for (; orig_index < act_data.getMax_index() && index < max_index;) {
			int pre_index = index - 1;

			// 今回の時間の値が前回の時間の値と同じだった場合、平均の値を設定
			// 元データのカウントをインクリメント
			if (time[pre_index] == act_data.getTime()[orig_index]) {
				same_time_count++;


				float pre_eurz=act_data.getEulz()[orig_index - 1];
				float now_eurz=act_data.getEulz()[orig_index];
				act_data.getEulz()[orig_index] = CalcSensorData.correct_eulz(pre_eurz,now_eurz);

				for(int ikind = 0; ikind<DATA_KIND;ikind++){
					datas[ikind][pre_index] = (act_data.getDataUsingID(ikind)[orig_index] + datas[ikind][pre_index] * (same_time_count - 1))/ same_time_count;
				}


				orig_index++;

				// 今回の時間の値が前回の時間+1だった場合、今回の運動データを設定
				// 元データと保管後データのカウントをインクリメント
			} else if (time[pre_index] + 1 == act_data.getTime()[orig_index]) {
				same_time_count = 1;
				time[index] = act_data.getTime()[orig_index];

				float pre_eurz=act_data.getEulz()[orig_index - 1];
				float now_eurz=act_data.getEulz()[orig_index];
				now_eurz=CalcSensorData.correct_eulz(pre_eurz,now_eurz);
				act_data.getEulz()[orig_index] = CalcSensorData.correct_eulz(pre_eurz,now_eurz);

				for(int ikind = 0; ikind<DATA_KIND;ikind++){
					datas[ikind][index] = act_data.getDataUsingID(ikind)[orig_index];
				}


				// スキップした値を補完
				for (int j = 0; j < skip_count; j++) {
					// 保管する時間の値を取得
					int comp_index = index - skip_count + j;
					float comp_val_coef = (float) (j + 1)
							/ (float) (skip_count + 1);
					// データを補完
					for(int ikind = 0; ikind<DATA_KIND;ikind++){
						datas[ikind][comp_index] = act_data.getDataUsingID(ikind)[orig_index-1]+
								((act_data.getDataUsingID(ikind)[orig_index] - act_data
								.getDataUsingID(ikind)[orig_index - 1]) * comp_val_coef);
					}
				}

				index++;
				orig_index++;
				// 補間を行ったのでスキップカウントをリセット
				skip_count = 0;

				// 今回の時間の値が前回の時間+1ではなかった場合、データ設定をスキップしてスキップ回数をカウント
				// 保管後データのカウントをインクリメント
			} else if ((time[pre_index] + 1) != act_data.getTime()[orig_index]) {
				same_time_count = 1;
				time[index] = time[pre_index] + 1;
				skip_count++;
				index++;
			}
		}
	}

	/**
	 * データを文字列にして返却（テキスト出力用）
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < index; i++) {
			sb.append(time[i]);
			sb.append(",");
			sb.append(datas[7][i]);
			sb.append(",");
			sb.append(datas[8][i]);
			sb.append(",");
			sb.append(datas[9][i]);
			sb.append(",");
			sb.append(datas[10][i]);
			sb.append(",");
			sb.append(datas[11][i]);
			sb.append(",");
			sb.append(datas[12][i]);
			sb.append(",");
			sb.append(datas[13][i]);
			sb.append(",");
			sb.append(datas[14][i]);
			sb.append(",");
			sb.append(datas[15][i]);
			sb.append(",");
			sb.append(datas[16][i]);
			sb.append(",");
			sb.append(datas[17][i]);
			sb.append(",");
			sb.append(datas[18][i]);
			sb.append("\n");

		}
		return sb.toString();
	}

	public String toString2() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < index; i+=10) {
			sb.append(time[i]);
			sb.append(",");
			sb.append(datas[7][i]);
			sb.append(",");
			sb.append(datas[8][i]);
			sb.append(",");
			sb.append(datas[9][i]);
			sb.append(",");
			sb.append(datas[10][i]);
			sb.append(",");
			sb.append(datas[11][i]);
			sb.append(",");
			sb.append(datas[12][i]);
			sb.append(",");
			sb.append(datas[13][i]);
			sb.append(",");
			sb.append(datas[14][i]);
			sb.append(",");
			sb.append(datas[15][i]);
			sb.append(",");
			sb.append(datas[16][i]);
			sb.append(",");
			sb.append(datas[17][i]);
			sb.append(",");
			sb.append(datas[18][i]);
			sb.append("\n");

		}
		return sb.toString();
	}


	/**
	 * 現在の経度を返却
	 *
	 * @return
	 */
	public float getNowLongitude() {
		return datas[17][index - 1];
	}


	/**
	 * 現在の緯度を返却
	 *
	 * @return
	 */
	public float getNowLatitude() {
		return datas[18][index - 1];
	}


	public long[] getTime() {
		return time;
	}

	public void setTime(long[] time) {
		this.time = time;
	}

	public float[] getAccx() {
		return datas[0];
	}

	public void setAccx(float[] accx) {
		datas[0] = accx;
	}

	public float[] getAccy() {
		return datas[1];
	}

	public void setAccy(float[] accy) {
		datas[0] = accy;
	}

	public float[] getAccz() {
		return datas[2];
	}

	public void setAccz(float[] accz) {
		datas[2] = accz;
	}

	public float[] getQuax() {
		return datas[3];
	}

	public void setQuax(float[] quax) {
		datas[3] = quax;
	}

	public float[] getQuay() {
		return datas[4];
	}

	public void setQuay(float[] quay) {
		datas[4] = quay;
	}

	public float[] getQuaz() {
		return datas[5];
	}

	public void setQuaz(float[] quaz) {
		datas[5] = quaz;
	}

	public float[] getQuaw() {
		return datas[6];
	}

	public void setQuaw(float[] quaw) {
		datas[6] = quaw;
	}

	public float[] getEulx() {
		return datas[7];
	}

	public void setEulx(float[] eulx) {
		datas[7] = eulx;
	}

	public float[] getEuly() {
		return datas[8];
	}

	public void setEuly(float[] euly) {
		datas[8] = euly;
	}

	public float[] getEulz() {
		return datas[9];
	}

	public void setEulz(float[] eulz) {
		datas[9] = eulz;
	}

	public float[] getLinx() {
		return datas[10];
	}

	public void setLinx(float[] linx) {
		datas[10] = linx;
	}

	public float[] getLiny() {
		return datas[11];
	}

	public void setLiny(float[] liny) {
		datas[11] = liny;
	}

	public float[] getLinz() {
		return datas[12];
	}

	public void setLinz(float[] linz) {
		datas[12] = linz;
	}

	public float[] getWlinx() {
		return datas[13];
	}

	public void setWlinx(float[] wlinx) {
		datas[13] = wlinx;
	}

	public float[] getWliny() {
		return datas[14];
	}

	public void setWliny(float[] wliny) {
		datas[14] = wliny;
	}

	public float[] getWlinz() {
		return datas[15];
	}

	public void setWlinz(float[] wlinz) {
		datas[15] = wlinz;
	}

	public float[] getSpeed() {
		return datas[16];
	}

	public void setSpeed(float[] speed) {
		this.datas[16] = speed;
	}

	public float[] getLongitude() {
		return datas[17];
	}

	public void setLongitude(float[] longitude) {
		this.datas[17] = longitude;
	}

	public float[] getLatitude() {
		return datas[18];
	}

	public void setLatitude(float[] latitude) {
		this.datas[18] = latitude;
	}


	public float[] getDataUsingID(int data_id){
		return datas[data_id];
	}



	public int setDatas(byte[] byte_data, int o,long[] sensorDiffTimes,Integer sensorId){

		// ミリ秒単位にするため×１０００

		time[index] = (long) (CalcSensorData.convertRxbytesToFloat(o, byte_data) * 1000)+sensorDiffTimes[sensorId];
		o += 4;

		//加速度と四元数を設定
		for(int ikind=0;ikind<7;ikind++){
			datas[ikind][index]= CalcSensorData.convertRxbytesToFloat(o, byte_data);
			o += 4;
		}

		//４元角からヨーピッチローを取得して設定
		float[] q = new float[4];
		q[0]=datas[3][index];
		q[1]=datas[4][index];
		q[2]=datas[5][index];
		q[3]=datas[6][index];
		float[] axsis3 = new float[3];
		axsis3 = DataParse.quaternionToEuler(q);
		datas[7][index] = axsis3[0] * R2D;
		datas[8][index] = axsis3[1] * R2D;
		datas[9][index] = axsis3[2] * R2D;

		//４元角と加速度から線形加速度を取得して設定
		axsis3[0]=datas[0][index];
		axsis3[1]=datas[1][index];
		axsis3[2]=datas[2][index];
		axsis3=DataParse.accToLinacc(q, axsis3);
		datas[10][index] = axsis3[0];
		datas[11][index] = axsis3[1];
		datas[12][index] = axsis3[2];

		//４元角と加速度から絶対座標の線形加速度を取得して設定
		axsis3[0]=datas[0][index];
		axsis3[1]=datas[1][index];
		axsis3[2]=datas[2][index];
		axsis3=DataParse.accToWorldLinacc(q, axsis3);
		datas[13][index] = axsis3[0];
		datas[14][index] = axsis3[1];
		datas[15][index] = axsis3[2];


		datas[16][index] = byte_data[o++];
		byte b1[] = { byte_data[o++], byte_data[o++], byte_data[o++],
				byte_data[o++] };
		datas[17][index] = CalcSensorData.byte_to_float(b1);
		byte b2[] = { byte_data[o++], byte_data[o++], byte_data[o++],
				byte_data[o++] };
		datas[18][index] = CalcSensorData.byte_to_float(b2);


		incrementIndex();

		return o;
	}



}