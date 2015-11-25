package graph;

import java.util.HashMap;

import data.sensor.SensorActionData;
import data.smartPhone.SmartPhoneActionData;
import setting.SettingValues;

/**
 * グラフ管理クラス
 * @author OZAKI
 *
 */
public class GraphManager {
	
	public static final int GraphWidth = 600;
	public static final int GraphHeight = 500;
	
	static boolean fFlg=true;
	public static SwingGraph[] graph = new SwingGraph[SettingValues.GRAPH_DATA_KIND_NUM];
	
  
	/**
	 * スマホ用グラフ作成（兼データ追加）
	 * @param act_data
	 */
    public static void createGraph(SmartPhoneActionData act_data){
    	if(fFlg){
    		graph[0]=new SwingGraph("x軸加速度",GraphWidth,GraphHeight,4);
    		graph[1]=new SwingGraph("y軸加速度",GraphWidth,GraphHeight,4);
    		graph[2]=new SwingGraph("z軸加速度",GraphWidth,GraphHeight,4);
    		graph[3]=new SwingGraph("方位",GraphWidth,GraphHeight,1);
    		graph[4]=new SwingGraph("傾斜",GraphWidth,GraphHeight,1);
    		graph[5]=new SwingGraph("左右傾き",GraphWidth,GraphHeight,1);
    		fFlg=false;
    	}
    	graph[0].addData(act_data.getAccx());
    	graph[1].addData(act_data.getAccy());
    	graph[2].addData(act_data.getAccz());
    	graph[3].addData(act_data.getArgmuki());
    	graph[4].addData(act_data.getArgtate());
    	graph[5].addData(act_data.getArgyoko());
    }

	/**
	 * センサ用グラフ作成（兼データ追加）
	 * @param act_data
	 */
    public static void createGraph(SensorActionData act_data){
    	if(fFlg){
    		fFlg=false;
    		graph[0]=new SwingGraph("x軸線形加速度",GraphWidth,GraphHeight,1);
    		graph[1]=new SwingGraph("y軸線形加速度",GraphWidth,GraphHeight,1);
    		graph[2]=new SwingGraph("z軸線形加速度",GraphWidth,GraphHeight,1);
    		graph[3]=new SwingGraph("x軸角度",GraphWidth,GraphHeight,1);
    		graph[4]=new SwingGraph("y軸角度",GraphWidth,GraphHeight,1);
    		graph[5]=new SwingGraph("z軸角度",GraphWidth,GraphHeight,1);
    	}
    	graph[0].addData(act_data.getLinx(),300);
    	graph[1].addData(act_data.getLiny(),300);
    	graph[2].addData(act_data.getLinz(),300);
    	graph[3].addData(act_data.getEulx(),1);
    	graph[4].addData(act_data.getEuly(),1);
    	graph[5].addData(act_data.getEulz(),1);
    }
    
}
