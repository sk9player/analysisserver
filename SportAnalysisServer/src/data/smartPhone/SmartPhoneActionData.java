package data.smartPhone;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import data.ActionData;

import setting.SettingValues;
import sub.Base64;
import sub.Debug;

/**
 * 運動データのクラス
 * 各種データとその格納機能を有する
 * @author OZAKI
 *
 */
public class SmartPhoneActionData extends ActionData{
	
	/**
	 * 時間
	 */
	private long[] time;
	/**
	 * x軸加速度
	 */
	private int[] accx;
	/**
	 * y軸加速度
	 */
	private int[] accy;
	/**
	 * z軸加速度
	 */
	private int[] accz;
	/**
	 * 向き
	 */
	private int[] argmuki;
	/**
	 * 縦方向の角度
	 */
	private int[] argtate;
	/**
	 * 横方向の角度
	 */
	private int[] argyoko;
	/**
	 * 速度
	 */
	private int[] speed;
	/**
	 * 経度
	 */
	private float[] longitude;
	/**
	 * 緯度
	 */
	private float[] latitude;
	
	/**
	 * データ数（各データの格納数）data_num　個のActionDataオブジェクトを生成
	 * 
	 * @param data_num
	 */
	public SmartPhoneActionData(int data_num) {
		Debug.debug_print("ActionData(int data_num)",1);
		max_index = data_num;
		time = new long[max_index];
		speed = new int[max_index];
		latitude = new float[max_index];
		longitude = new float[max_index];
		accx = new int[max_index];
		accy = new int[max_index];
		accz = new int[max_index];
		argmuki = new int[max_index];
		argtate = new int[max_index];
		argyoko = new int[max_index];
	}

	/**
	 * Base64のデータをこのオブジェクトに挿入
	 * 
	 * @param data
	 * @throws IOException
	 * @throws Base64DecodingException
	 */
	public void setData(String data) throws IOException {
		Debug.debug_print("ActionData.setData(String data)",1);
		byte[] byte_data = Base64.decode(data, Base64.URL_SAFE);
		for (int i = 0; i < byte_data.length-1;) {
			
			
			byte[] time_b = new byte[8];
			time_b[0]=0;
			time_b[1]=0;
			for(int j=2;j<8;j++){
				time_b[j] = byte_data[i++];
			}
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(time_b));
	        try {
	        	time[index]=in.readLong();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			speed[index] = (int) byte_data[i++];
			byte b1[] = {byte_data[i++],byte_data[i++],byte_data[i++],byte_data[i++]};
			latitude[index] = byte_to_float(b1);
			byte b2[] = {byte_data[i++],byte_data[i++],byte_data[i++],byte_data[i++]};
			longitude[index] = byte_to_float(b2);	
			accx[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			accy[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			accz[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			argmuki[index] = (int) byte_data[i++] * 128
					+ (int) byte_data[i++];
			argtate[index] = (int) byte_data[i++];
			argyoko[index] = (int) byte_data[i++] * 128
					+ (int) byte_data[i++];
			index++;

		}
		if(byte_data[byte_data.length-1]>=0){
			commu_index = byte_data[byte_data.length-1];
		}else{
			commu_index = byte_data[byte_data.length-1]+256;
		}
		Debug.debug_print("通信ナンバー="+commu_index,99);

	}

	private float byte_to_float(byte[] b){
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(b);
		buffer.flip();
        return Float.intBitsToFloat(buffer.getInt());
	}
	
	
	/**
	 * 他のアクションデータをこのオブジェクトに挿入
	 * 
	 * @param act_data
	 */
	public void setData(SmartPhoneActionData act_data) {
		Debug.debug_print("ActionData.setData(ActionData act_data)",1);
		for (int i = 0; i < act_data.getMax_index(); i ++) {
			time[index] = act_data.getTime()[i];
			speed[index] = act_data.getSpeed()[i];
			latitude[index] = act_data.getLatitude()[i];
			longitude[index] = act_data.getLongitude()[i];			
			accx[index] = act_data.getAccx()[i];
			accy[index] = act_data.getAccy()[i];
			accz[index] = act_data.getAccz()[i];
			argmuki[index] = act_data.getArgmuki()[i];
			argtate[index] = act_data.getArgtate()[i];
			argyoko[index] = act_data.getArgyoko()[i];
			index++;
		}
	}
	
	/**
	 * 他のアクションデータを時系列補間してこのオブジェクトに挿入
	 * 
	 * @param act_data
	 */
	public void normalizeData(SmartPhoneActionData act_data) {
		Debug.debug_print("normalizeData(SmartPhoneActionData act_data)",1);
		//ループ回数
		int orig_index = 0;
		
		//時間が同時だった回数
		int same_time_count = 1;

		//線形保管の為の保管時間のカウント
		int skip_count = 0;
		
		//初回はそのまま値を挿入
		if(index==0){
			time[index] = act_data.getTime()[orig_index];
			speed[index] = act_data.getSpeed()[orig_index];
			latitude[index] = act_data.getLatitude()[orig_index];
			longitude[index] = act_data.getLongitude()[orig_index];			
			accx[index] = act_data.getAccx()[orig_index];
			accy[index] = act_data.getAccy()[orig_index];
			accz[index] = act_data.getAccz()[orig_index];
			argmuki[index] = act_data.getArgmuki()[orig_index];
			argtate[index] = act_data.getArgtate()[orig_index];
			argyoko[index] = act_data.getArgyoko()[orig_index];
			index++;
			orig_index++;
		}
		
			
		for (; orig_index < act_data.getMax_index()&&index < max_index;) {
			int pre_index = index-1;
			
			//今回の時間の値が前回の時間の値と同じだった場合、平均の値を設定
			//元データのカウントをインクリメント
			if(time[pre_index]==act_data.getTime()[orig_index]){
				same_time_count++;
				speed[pre_index] = (speed[pre_index]*(same_time_count-1) + act_data.getSpeed()[orig_index])/same_time_count;
				latitude[pre_index] = (latitude[pre_index]*(same_time_count-1) + act_data.getLatitude()[orig_index])/same_time_count;
				longitude[pre_index] = (longitude[pre_index]*(same_time_count-1) + act_data.getLongitude()[orig_index])/same_time_count;			
				accx[pre_index] = (accx[pre_index]*(same_time_count-1) + act_data.getAccx()[orig_index])/same_time_count;
				accy[pre_index] = (accy[pre_index]*(same_time_count-1) + act_data.getAccy()[orig_index])/same_time_count;
				accz[pre_index] = (accz[pre_index]*(same_time_count-1) + act_data.getAccz()[orig_index])/same_time_count;
				argmuki[pre_index] = (argmuki[pre_index]*(same_time_count-1) + act_data.getArgmuki()[orig_index])/same_time_count;
				argtate[pre_index] = (argtate[pre_index]*(same_time_count-1) + act_data.getArgtate()[orig_index])/same_time_count;
				argyoko[pre_index] = (argyoko[pre_index]*(same_time_count-1) + act_data.getArgyoko()[orig_index])/same_time_count;
				orig_index++;
				
			//今回の時間の値が前回の時間+1だった場合、今回の運動データを設定
			//元データと保管後データのカウントをインクリメント
			}else if(time[pre_index]+1==act_data.getTime()[orig_index]){
				same_time_count=1;
				time[index] = act_data.getTime()[orig_index];
				speed[index] = act_data.getSpeed()[orig_index];
				latitude[index] = act_data.getLatitude()[orig_index];
				longitude[index] = act_data.getLongitude()[orig_index];			
				accx[index] = act_data.getAccx()[orig_index];
				accy[index] = act_data.getAccy()[orig_index];
				accz[index] = act_data.getAccz()[orig_index];
				argmuki[index] = act_data.getArgmuki()[orig_index];
				argtate[index] = act_data.getArgtate()[orig_index];
				argyoko[index] = act_data.getArgyoko()[orig_index];
				
				//スキップした値を補完
				for (int j= 0;j < skip_count;j++){
					//保管する時間の値を取得
					int comp_index = index-skip_count+j;
					float comp_val_coef = (float)(j + 1) / (float)(skip_count + 1);
					//データを補完
					speed[comp_index] = (int) (act_data.getSpeed()[orig_index-1] + ((act_data.getSpeed()[orig_index] - act_data.getSpeed()[orig_index-1]) * comp_val_coef));
					latitude[comp_index] = act_data.getLatitude()[orig_index-1] + ((act_data.getLatitude()[orig_index] - act_data.getLatitude()[orig_index-1]) * comp_val_coef);
					longitude[comp_index] = act_data.getLongitude()[orig_index-1] + ((act_data.getLongitude()[orig_index] - act_data.getLongitude()[orig_index-1]) * comp_val_coef);
					accx[comp_index] = (int) (act_data.getAccx()[orig_index-1] + ((act_data.getAccx()[orig_index] - act_data.getAccx()[orig_index-1]) * comp_val_coef));
					accy[comp_index] = (int) (act_data.getAccy()[orig_index-1] + ((act_data.getAccy()[orig_index] - act_data.getAccy()[orig_index-1]) * comp_val_coef));
					accz[comp_index] = (int) (act_data.getAccz()[orig_index-1] + ((act_data.getAccz()[orig_index] - act_data.getAccz()[orig_index-1]) * comp_val_coef));
					argmuki[comp_index] = (int) (act_data.getArgmuki()[orig_index-1] + ((act_data.getArgmuki()[orig_index] - act_data.getArgmuki()[orig_index-1]) * comp_val_coef));
					argtate[comp_index] = (int) (act_data.getArgtate()[orig_index-1] + ((act_data.getArgtate()[orig_index] - act_data.getArgtate()[orig_index-1]) * comp_val_coef));
					argyoko[comp_index] = (int) (act_data.getArgyoko()[orig_index-1] + ((act_data.getArgyoko()[orig_index] - act_data.getArgyoko()[orig_index-1]) * comp_val_coef));
				}
				
				index++;
				orig_index++;
				// 補間を行ったのでスキップカウントをリセット
				skip_count=0;
	
			//今回の時間の値が前回の時間+1ではなかった場合、	
			}else if((time[pre_index]+1)!=act_data.getTime()[orig_index]){
				same_time_count=1;
				time[index] = time[pre_index]+1;
				skip_count++;
				index++;
			}
		}
	}	
	
	/**
	 * データを文字列にして返却（テキスト出力用）
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<index;i++){
			sb.append(time[i]);
			sb.append("\t");
			sb.append(latitude[i]);
			sb.append("\t");
			sb.append(longitude[i]);
			sb.append("\t");
			sb.append(speed[i]);
			sb.append("\t");
			sb.append(accx[i]);
			sb.append("\t");
			sb.append(accy[i]);
			sb.append("\t");
			sb.append(accz[i]);
			sb.append("\t");
			sb.append(argmuki[i]);
			sb.append("\t");
			sb.append(argtate[i]);
			sb.append("\t");
			sb.append(argyoko[i]);
			sb.append("\n");			
		}
		return sb.toString();
	}

	/**
	 * 現在の緯度を返却
	 * @return
	 */
	public float getNowLatitude() {
		return latitude[index-1];
	}	
	/**
	 * 現在の経度を返却
	 * @return
	 */
	public float getNowLongitude() {
		return longitude[index-1];
	}	

	public long[] getTime() {
		return time;
	}

	public void setTime(long[] time) {
		this.time = time;
	}

	public int[] getAccx() {
		return accx;
	}

	public void setAccx(int[] accx) {
		this.accx = accx;
	}

	public int[] getAccy() {
		return accy;
	}

	public void setAccy(int[] accy) {
		this.accy = accy;
	}

	public int[] getAccz() {
		return accz;
	}

	public void setAccz(int[] accz) {
		this.accz = accz;
	}

	public int[] getArgmuki() {
		return argmuki;
	}

	public void setArgmuki(int[] argmuki) {
		this.argmuki = argmuki;
	}

	public int[] getArgtate() {
		return argtate;
	}

	public void setArgtate(int[] argtate) {
		this.argtate = argtate;
	}

	public int[] getArgyoko() {
		return argyoko;
	}

	public void setArgyoko(int[] argyoko) {
		this.argyoko = argyoko;
	}

	public int[] getSpeed() {
		return speed;
	}

	public void setSpeed(int[] speed) {
		this.speed = speed;
	}

	public float[] getLongitude() {
		return longitude;
	}

	public void setLongitude(float[] longitude) {
		this.longitude = longitude;
	}

	public float[] getLatitude() {
		return latitude;
	}

	public void setLatitude(float[] latitude) {
		this.latitude = latitude;
	}

}