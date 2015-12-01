package coach.sensor;

import javaFXGUI.SampleController;

import javafx.application.Platform;
import setting.SettingValues;
import sub.OutPutText;
import coach.calc.CalcCompareScore;
import data.sensor.SensorPersonalData;


public class StraightSkatingCompareCoach implements SensorCoach{

	private static SensorCoach sensor_compare=null;

	public static SensorCoach getSensorCoach(){
		if(sensor_compare==null){
			sensor_compare=new StraightSkatingCompareCoach();
		}
		return sensor_compare;
	}

	@Override
	public int compare(SensorPersonalData data) {
		set_best_parameter(data);
		int calc_num = CalcCompareScore.calc_best_straight_scores(data);
		//n回に1回コーチングを行う
		int ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.COACH_FREQ);
		if(ret_num!=0)
			ret_num = CalcCompareScore.count_same_coatch_id(data,ret_num,SettingValues.SAME_COATCHING_ID_COUNT);

		if(SettingValues.USE_GUI)
			Platform.runLater(new SampleController());

		OutPutText.output_txt(data.name+"_paramater", data.act_para.toString()+calc_num+"\t"+ret_num+"\n", 0,true);
		return ret_num;
	}


	/**
	 * 固定のベストパラメータを設定
	 */
	private void set_best_parameter(SensorPersonalData data){
		data.compare_act_para.setCycle(SettingValues.BEST_CYCLE);
		data.compare_act_para.setEurx_ave(SettingValues.BEST_EURX_AVE);
		data.compare_act_para.setEurz_diff_ave(SettingValues.BEST_EURZ_DIFF_AVE);
	}

}
