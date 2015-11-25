package data.multiSensor;

import java.io.IOException;
import java.util.HashMap;

import sub.Base64;
import sub.Debug;
import sub.OutPutText;

import data.parse.DataParse;
import data.sensor.SensorActionData;

public class MultiSensorActionData {

	/**
	 * センサデータのハッシュマップ
	 */
	public HashMap<Integer,SensorActionData> sensorActionDatas = new HashMap<Integer,SensorActionData>();
	
	/**
	 * １つのセンサデータクラスの保持する最大データ数
	 */
	private int data_num = 0;

	/**
	 * センサの数
	 */
	private int sensor_num = 0;
	
	public MultiSensorActionData(int data_num){
		this.data_num =data_num;
	}

	public void setMultiData(String data, long[] sensorDiffTimes) {
		byte[] byte_data = null;
		try {
			byte_data = Base64.decode(data, Base64.URL_SAFE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SensorActionData sensorActionData = null;
		for (int o = 0; o < byte_data.length - 1;) {
			
			Integer sensorId = new Integer(byte_data[o]);
			
			if(!sensorActionDatas.containsKey(sensorId)){
				sensorActionDatas.put(sensorId, new SensorActionData(data_num));
			}
			
			sensorActionData = sensorActionDatas.get(sensorId);
			o+=1;
			
			int index= sensorActionData.getIndex();
			o=sensorActionData.setDatas(byte_data,o,sensorDiffTimes,sensorId);
			
			

		}
		sensor_num=sensorActionDatas.size();
		for (int i=0;i<sensor_num;i++){
			sensorActionData=sensorActionDatas.get(new Integer(i));
			if (byte_data[byte_data.length - 1] >= 0) {
				sensorActionData.setCommu_index(byte_data[byte_data.length - 1]);
			} else {
				sensorActionData.setCommu_index(byte_data[byte_data.length - 1] + 256);
			}
//			OutPutText.output_txt("sensorActionData"+i,sensorActionData.toString(), 0, false);
		}
		
		Debug.debug_print("通信ナンバー=" + sensorActionData.getCommu_index(), 99);
	
		
	}

	public int getData_num() {
		return data_num;
	}

	public void setData_num(int data_num) {
		this.data_num = data_num;
	}

	public int getSensor_num() {
		return sensor_num;
	}

	public void setSensor_num(int sensor_num) {
		this.sensor_num = sensor_num;
	}
	
}
