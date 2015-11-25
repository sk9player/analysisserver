package analysis.categorize;

import setting.ModeValues;
import sub.Debug;
import analysis.categorize.tree.SkatingStateTree;

public class StateCategorize {

	private SkatingStateTree sst;

	/**
	 * 状態識別器を構築
	 * @return
	 */
	public void init(){
		sst = new SkatingStateTree();
		sst.init();
	}


	/**
	 * 状態推定し、状態IDを返却
	 * 状態ID
	 * 0:判定なし
	 * 1:停止
	 *
	 * @param sensor_datas 0:speed 1:eurz 2:eurx 3:wlinz 4:wlinx 5:wliny 6:linx
	 * @return
	 */
	public int get_state(double[][] sensor_datas){

		if(sst==null){
			sst = new SkatingStateTree();
			sst.init();
		}
		String state=sst.calc(sensor_datas);
		Debug.debug_print("滑走状態名:"+state,99);
		return define_state(state);

	}

	private int define_state(String s){
		if(s.equals("stop_state"))
			return ModeValues.STOP_STATE;
		else if(s.equals("start_state"))
			return ModeValues.START_STATE;
		else if(s.equals("straight_rest_state"))
			return ModeValues.STRAIGHT_REST_STATE;
		else if(s.equals("corner_rest_state"))
			return ModeValues.CORNER_REST_STATE;
		else if(s.equals("straight_skating_state"))
			return ModeValues.STRAIGHT_SKATING_STATE;
		else if(s.equals("corner_skating_state"))
			return ModeValues.CORNER_SKATING_STATE;
		else if(s.equals("straight_dash_state"))
			return ModeValues.STRAIGHT_DASH_STATE;
		else if(s.equals("corner_dash_state"))
			return ModeValues.CORNER_DASH_STATE;
		else
			return ModeValues.FAIL_STATE;
	}


}
