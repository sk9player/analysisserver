package coach.sensor;

import coach.calc.CalcCompareScore;
import setting.SettingValues;
import sub.OutPutText;
import data.sensor.SensorPersonalData;


public class StateCoach implements SensorCoach{

	private static SensorCoach sensor_compare=null;
	
	public static SensorCoach getSensorCoach(){
		if(sensor_compare==null){
			sensor_compare=new StateCoach();
		}
		return sensor_compare;
	}
	
	@Override
	public int compare(SensorPersonalData data) {
		int calc_num = data.act_para.getState();
		int ret_num = CalcCompareScore.count_coatch_id(data,calc_num,SettingValues.STATE_FREQ);
		OutPutText.output_txt(data.name+"_paramater", data.act_para.toString()+calc_num+"\t"+ret_num+"\n", 0,true);
		return ret_num;
	}
	
}
