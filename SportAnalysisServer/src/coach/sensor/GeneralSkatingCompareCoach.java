package coach.sensor;

import setting.ModeValues;
import setting.SettingValues;
import sub.OutPutText;
import coach.calc.CalcCompareScore;
import data.sensor.SensorPersonalData;


public class GeneralSkatingCompareCoach implements SensorCoach{

	private static SensorCoach sensor_compare=null;

	public static SensorCoach getSensorCoach(){
		if(sensor_compare==null){
			sensor_compare=new GeneralSkatingCompareCoach();
		}
		return sensor_compare;
	}

	@Override
	public int compare(SensorPersonalData data) {

		//分析で求められた値
		int calc_num = 0;
		//返却する値（calc_numをそのまま返さない場合もある為）
		int ret_num = 0;

		int state =data.act_para.getState();

		switch (state) {
		case ModeValues.STRAIGHT_SKATING_STATE:
			set_best_parameter(data);
			calc_num = CalcCompareScore.calc_best_straight_scores(data);
			//n回に1回コーチングを行う
			ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.COACH_FREQ);
			if(ret_num!=0)
				ret_num = CalcCompareScore.count_same_coatch_id(data,ret_num,SettingValues.SAME_COATCHING_ID_COUNT);
			//コーナーのコーチングのみなので0
			ret_num=0;
			OutPutText.output_txt(data.name+"straight_paramater", data.act_para.toString()+state+"\t"+calc_num+"\t"+ret_num+"\t"+SettingValues.COACH_ONOFF+"\n", 0,true);
 			break;
		case ModeValues.STRAIGHT_DASH_STATE:
//			set_best_parameter(data);
//			calc_num = CalcCompareScore.calc_best_straight_scores(data);
//			//n回に1回コーチングを行う
//			ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.COACH_FREQ);
//			if(ret_num!=0)
//				ret_num = CalcCompareScore.count_same_coatch_id(data,ret_num,SettingValues.SAME_COATCHING_ID_COUNT);
 			break;
		case ModeValues.CORNER_SKATING_STATE:
			set_best_parameter(data);
			calc_num = CalcCompareScore.calc_best_corner_scores(data);
			//n回に1回コーチングを行う
			ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.COACH_FREQ);
			if(ret_num!=0)
				ret_num = CalcCompareScore.count_same_coatch_id(data,ret_num,SettingValues.SAME_COATCHING_ID_COUNT);
			OutPutText.output_txt(data.name+"corner_paramater", data.act_para.toString()+"\t"+calc_num+"\t"+ret_num+"\t"+SettingValues.COACH_ONOFF+"\n", 0,true);
 			break;
		case ModeValues.CORNER_DASH_STATE:
//			set_best_parameter(data);
//			calc_num = CalcCompareScore.calc_best_corner_scores(data);
//			//n回に1回コーチングを行う
//			ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.COACH_FREQ);
//			if(ret_num!=0)
//				ret_num = CalcCompareScore.count_same_coatch_id(data,ret_num,SettingValues.SAME_COATCHING_ID_COUNT);
 			break;
		default:
			ret_num=0;
			break;
		}


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
