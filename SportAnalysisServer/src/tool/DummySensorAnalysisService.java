package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;

import analysis.AnalysisManager;
import analysis.calc.Calculate;
import coach.CoachingManager;

import data.DataManager;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;


/**
 * テキストファイルを読み込み、ダミーデータとして自動コーチングのリザルトを出力
 * @author OZAKI
 *
 */
public class DummySensorAnalysisService {

		
	private static String USER_NAME="test";
	private static int MODE=ModeValues.GENERAL_COACH_MODE;
	private static int DATA_NUM=150;
	
	
	/**
	 * 初期設定
	 */
	private static void init(){
		Debug.debug_level=999;
		SettingValues.COACH_FREQ=1;
		//SettingValues.STATE_FREQ=1;
		SettingValues.THRESHOLD_VAL=0.3;
		SettingValues.ACC_DATA_NUM_S=99;
		Calculate.set_window_func_s();
	}
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		init();
		SensorActionData[] dummy_data = create_dummy_data(USER_NAME);
		
		for(int idata=0;idata<dummy_data.length;idata++){
			SensorActionData act_data = DataManager.create_sensor_data(
					USER_NAME, String.valueOf(MODE), dummy_data[idata]);
			// ユーザデータの取得
			SensorPersonalData per_data = DataManager.sensor_personal_map.get(USER_NAME);

			// 時系列補間してユーザデータに設定
			DataManager.time_comp.time_comp(per_data, act_data);

			// 時系列補間した運動データを分析して運動パラメータに設定(初回は-1(開始ID)を返して終了、5回目までは0(指示なし)を返却)
			int analysis_count = AnalysisManager.Create_sensor_para
					.createFeature(per_data);
			if (analysis_count < 5) {
			}else{
			System.out.println(CoachingManager.Compare_s.compare_parameter(
						per_data.coach_mode, per_data));
			}
		}
	}

	/**
	 * ダミーのセンサデータ群の生成
	 * @return
	 */
	private static SensorActionData[] create_dummy_data(String name) {
		String[] files=createInputFileNames(name,DATA_NUM);
		SensorActionData[] dummy_data =new SensorActionData[files.length];
		
		for (int i = 0; i < files.length; i++) {
			try {
				File input_file = new File(files[i]);
				BufferedReader br = new BufferedReader(new FileReader(
						input_file));

				String str;
				ArrayList<String> data_array = new ArrayList<String>();

				while ((str = br.readLine()) != null) {
					data_array.add(str);
				}
				
				SensorActionData act_data=createSensorData(data_array,i);
				dummy_data[i]=act_data;
				br.close();
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
		
		
		return dummy_data;
	}

	private static SensorActionData createSensorData(ArrayList<String> data_array,int ifile) {
		int nsample = data_array.size();
		SensorActionData act_data=new SensorActionData(nsample);
		long[] time = new long[nsample];
		float[][] sensor_data=new float[12][nsample];
		
		
		
		for(int isample=0;isample<nsample;isample++){
			String line=data_array.get(isample);
			String[] datas=line.split("\t");
			time[isample]=Long.parseLong(datas[0]);
			
			for(int idata=1;idata<datas.length;idata++){
				sensor_data[idata-1][isample]=Float.parseFloat(datas[idata]);
			}
		}
		act_data.setTime(time);
		act_data.setEulx(sensor_data[0]);
		act_data.setEuly(sensor_data[1]);
		act_data.setEulz(sensor_data[2]);
		act_data.setLinx(sensor_data[3]);
		act_data.setLiny(sensor_data[4]);
		act_data.setLinz(sensor_data[5]);
		act_data.setWlinx(sensor_data[6]);
		act_data.setWliny(sensor_data[7]);
		act_data.setWlinz(sensor_data[8]);
		act_data.setSpeed(sensor_data[9]);
		act_data.setLongitude(sensor_data[10]);
		act_data.setLatitude(sensor_data[11]);
		act_data.incrementIndex(nsample);
		act_data.setCommu_index(ifile);
		
		// TODO Auto-generated method stub
		return act_data;
	}

	/**
	 * ファイル名のリストを生成
	 * @param string
	 * @return
	 */
	private static String[] createInputFileNames(String name,int nfile) {
		ArrayList<String> file_name_array = new ArrayList<String>();
		DecimalFormat dformat = new DecimalFormat("0000");
		
		for(int ifile=0;ifile<nfile;ifile++){
			file_name_array.add("C:\\Users\\OZAKI\\Desktop\\GoogleDrive\\server_data\\dummy\\"+name+"normalized_s"+dformat.format(ifile)+".log");
		}
		
		return file_name_array.toArray(new String[0]);
	}
	
	
	

}
