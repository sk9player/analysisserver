package analysis.sensor;

import setting.ModeValues;
import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;
import analysis.categorize.CategorizeManager;
import analysis.categorize.CtegorizeCalc;
import data.sensor.SensorPersonalData;

public class SkatingCategorizeAnalyzer implements SensorDataAnalyzer{

	@Override
	public void analysis(SensorPersonalData per_data,int late_commu_num){
		if (per_data.count_normalized_data_num(SettingValues.CATEGORIZE_SENSOR_DATA_NUM)) {
			analysisState(per_data,late_commu_num);
			//分析回数をカウント
			per_data.analysis_count++;
		}else{
			per_data.act_para.setState(ModeValues.FAIL_STATE);
		}
	}


	/**
	 * 状態推定を実行し、結果をper_dataに保存
	 * @param per_data
	 * @param late_commu_num
	 */
	private void analysisState(SensorPersonalData per_data,int late_commu_num){
		Debug.debug_print("状態推定開始",2);

		//センサデータの配列を取得
		double[][] sensor_datas = per_data.get_sensor_datas(per_data,SettingValues.CATEGORIZE_SENSOR_DATA_NUM,1);

		//状態推定
		int state = CategorizeManager.state_categorize.get_state(sensor_datas);
		Debug.debug_print("滑走状態:"+state,4);

		//滑走状態推定パメータ
		double speed=CtegorizeCalc.getSpeed(sensor_datas);
		double acc=CtegorizeCalc.getAcc(sensor_datas);
		double turn= CtegorizeCalc.getTurn(sensor_datas);
		double xacc=CtegorizeCalc.getXaccave(sensor_datas);

		//滑走状態をテキストに出力
		OutPutText.output_txt(per_data.name+"_state",per_data.act_para.getLast_time()+"\t"+state+"\t"+speed+"\t"+acc+"\t"+turn+"\t"+xacc+"\n",0,true);

		per_data.act_para.setState(state);


		//滑走状態をパラメータに設定
		per_data.state_array[late_commu_num]=state;

		int i=0;
		while(per_data.state_array[late_commu_num-i]==0){
			per_data.state_array[late_commu_num-i]=state;
			if(late_commu_num-i==0){
				i=-255;
			}


		}
	}




}
