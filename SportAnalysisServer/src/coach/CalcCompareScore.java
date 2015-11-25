package coach;

import setting.SettingValues;
import sub.Debug;
import data.sensor.SensorActionParameter;
import data.sensor.SensorPersonalData;
import data.smartPhone.SmartPhoneActionParameter;
import data.smartPhone.SmartPhonePersonalData;

/**
 * パラメータを比較しスコアを算出するクラス
 * @author OZAKI
 *
 */
public class CalcCompareScore {

	/**
	 * 各パラメータのスコアを算出し、もっともスコアについて返却
	 * 0:差分なし
	 * 1:ピッチ増加
	 * 2:ピッチ減少
	 * @param data
	 * @return
	 */
	public static int calc_best_scores(SmartPhonePersonalData data){


		return 0;
	}

	/**
	 * 各パラメータのスコアを算出し、もっとも差分スコアが高かった差分のIDを返却
	 * 0:差分なし
	 * 1:ピッチ増加
	 * 2:ピッチ減少
	 * @param data
	 * @return
	 */
	public static int calc_best_straight_scores(SensorPersonalData data){
		double scores[]=new double[5];
		scores[0] = compare_aveX_score(data.act_para.getEurx_ave(),data.compare_act_para.getEurx_ave());
		Debug.debug_print("平均上体傾斜スコア："+scores[0], 4);
		scores[1] = compare_pitch_score(data.act_para.getCycle(),data.compare_act_para.getCycle());
		Debug.debug_print("平均ピッチスコア："+scores[1], 4);
		scores[2] = compare_diff_aveZ_score(data.act_para.getEurz_diff_ave(),data.compare_act_para.getEurz_diff_ave());
		Debug.debug_print("平均上体向き遷移スコア："+scores[2], 4);
		double temp=compare_move_z_score(data.act_para.getMove_z(),data.compare_act_para.getMove_z());
		Debug.debug_print("上下動スコア："+temp, 4);
		temp=compare_move_xy_score(data.act_para.getMove_xy(),data.compare_act_para.getMove_xy());
		Debug.debug_print("水平移動スコア："+temp, 4);
		temp=compare_move_x_score(data.act_para.getMove_x(),data.compare_act_para.getMove_x());
		Debug.debug_print("左右移動スコア："+temp, 4);

		Debug.debug_print("分析結果ID："+get_score_index(scores), 99);
		return get_score_index(scores);
	}

	/**
	 * 各パラメータのスコアを算出し、もっとも差分スコアが高かった差分のIDを返却
	 * 0:差分なし
	 * 1:ピッチ増加
	 * 2:ピッチ減少
	 * @param data
	 * @return
	 */
	public static int calc_best_corner_scores(SensorPersonalData data){
		double scores[]=new double[3];
		scores[0] = compare_aveX_score(data.act_para.getEurx_ave(),data.compare_act_para.getEurx_ave());
		Debug.debug_print("平均上体傾斜スコア："+scores[0], 4);
		scores[1] = compare_pitch_score(data.act_para.getCycle(),data.compare_act_para.getCycle());
		Debug.debug_print("平均ピッチスコア："+scores[1], 4);
		scores[2] = compare_diff_aveZ_score(data.act_para.getEurz_diff_ave(),data.compare_act_para.getEurz_diff_ave());
		Debug.debug_print("平均上体向き遷移スコア："+scores[2], 4);
		double temp=compare_move_z_score(data.act_para.getMove_z(),data.compare_act_para.getMove_z());
		Debug.debug_print("上下動スコア："+temp, 4);
		temp=compare_move_xy_score(data.act_para.getMove_xy(),data.compare_act_para.getMove_xy());
		Debug.debug_print("水平移動スコア："+temp, 4);
		temp=compare_move_x_score(data.act_para.getMove_x(),data.compare_act_para.getMove_x());
		Debug.debug_print("左右移動スコア："+temp, 4);

		Debug.debug_print("分析結果ID："+get_score_index(scores), 99);
		return get_score_index(scores);
	}


	/**
	 * ピッチを比較し、差分IDを返却
	 * @param now_data
	 * @param pre_data
	 * @param limit
	 * @return
	 */
	public static int compare_pitch(SensorActionParameter act_para,SensorActionParameter comp_para,double limit){

		Debug.debug_print("現在のピッチ："+act_para.getCycle(), 2);
		Debug.debug_print("前回のピッチ："+comp_para.getCycle(), 2);
		int ret_num = 0;
		double score = compare_pitch_score(act_para.getCycle(),comp_para.getCycle());

		if(score<-limit*SettingValues.PITCH_SCORE_COEF){
			ret_num = 1;
		}else if(score>limit*SettingValues.PITCH_SCORE_COEF){
			ret_num = 2;
		}
		Debug.debug_print("比較結果："+ret_num, 99);

		return ret_num;
	}
	/**
	 * ピッチを比較し、差分IDを返却
	 * @param now_data
	 * @param pre_data
	 * @param limit
	 * @return
	 */
	public static int compare_pitch(SmartPhoneActionParameter act_para,SmartPhoneActionParameter comp_para,double limit){

		Debug.debug_print("現在のピッチ："+act_para.getCycle(), 2);
		Debug.debug_print("前回のピッチ："+comp_para.getCycle(), 2);
		int ret_num = 0;
		double score = compare_pitch_score(act_para.getCycle(),comp_para.getCycle());

		if(score<-limit*SettingValues.PITCH_SCORE_COEF){
			ret_num = 1;
		}else if(score>limit*SettingValues.PITCH_SCORE_COEF){
			ret_num = 2;
		}
		Debug.debug_print("比較結果："+ret_num, 99);

		return ret_num;
	}

	/**
	 * ピッチを比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_pitch_score(double now_data,double comp_data){
		return (now_data - comp_data)*SettingValues.PITCH_SCORE_COEF;
	}

	/**
	 * 上体平均振り角度を比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_diff_aveZ_score(double now_data,double comp_data){
		return (now_data-comp_data)*SettingValues.DIFF_AVEZ_SCORE_COEF;
	}

	/**
	 * 上体傾斜角度を比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_aveX_score(double now_data,double comp_data){
		return (comp_data - now_data)*SettingValues.AVEX_SCORE_COEF;
	}

	/**
	 * z軸加速度を比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_move_z_score(double now_data,double comp_data){
		return (now_data - comp_data)*SettingValues.MOVEZ_SCORE_COEF;
	}

	/**
	 * xy軸加速度を比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_move_xy_score(double now_data,double comp_data){
		return (now_data - comp_data)*SettingValues.MOVEXY_SCORE_COEF;
	}

	/**
	 * x軸加速度を比較し、差分スコアを返却
	 * @param now_data
	 * @param comp_data
	 * @return
	 */
	private static double compare_move_x_score(double now_data,double comp_data){
		return (now_data - comp_data)*SettingValues.MOVEX_SCORE_COEF;
	}

	/**
	 * 各スコアを格納した配列からIDを返却
	 * @param scores
	 * @return
	 */
	private static int get_score_index(double[] scores){
		double max=0;
		int max_index = 0;
		for(int i =0;i<scores.length;i++){
			if(Math.abs(scores[i])>max){
				max=Math.abs(scores[i]);
				max_index=i;
			}
		}
		Debug.debug_print("最大スコア："+scores[max_index], 4);
		//スコアが閾値以下だったらコーチングしない
		if(Math.abs(scores[max_index])<SettingValues.COACH_SCORE_THRESHOLD)
			return 0;

		if(scores[max_index]>0)
			return max_index*2+1;
		else
			return max_index*2+2;
	}

}
