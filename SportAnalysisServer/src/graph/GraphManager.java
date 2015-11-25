package graph;

import java.util.HashMap;

import data.sensor.SensorActionData;
import data.smartPhone.SmartPhoneActionData;
import setting.SettingValues;

/**
 * �O���t�Ǘ��N���X
 * @author OZAKI
 *
 */
public class GraphManager {
	
	public static final int GraphWidth = 600;
	public static final int GraphHeight = 500;
	
	static boolean fFlg=true;
	public static SwingGraph[] graph = new SwingGraph[SettingValues.GRAPH_DATA_KIND_NUM];
	
  
	/**
	 * �X�}�z�p�O���t�쐬�i���f�[�^�ǉ��j
	 * @param act_data
	 */
    public static void createGraph(SmartPhoneActionData act_data){
    	if(fFlg){
    		graph[0]=new SwingGraph("x�������x",GraphWidth,GraphHeight,4);
    		graph[1]=new SwingGraph("y�������x",GraphWidth,GraphHeight,4);
    		graph[2]=new SwingGraph("z�������x",GraphWidth,GraphHeight,4);
    		graph[3]=new SwingGraph("����",GraphWidth,GraphHeight,1);
    		graph[4]=new SwingGraph("�X��",GraphWidth,GraphHeight,1);
    		graph[5]=new SwingGraph("���E�X��",GraphWidth,GraphHeight,1);
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
	 * �Z���T�p�O���t�쐬�i���f�[�^�ǉ��j
	 * @param act_data
	 */
    public static void createGraph(SensorActionData act_data){
    	if(fFlg){
    		fFlg=false;
    		graph[0]=new SwingGraph("x�����`�����x",GraphWidth,GraphHeight,1);
    		graph[1]=new SwingGraph("y�����`�����x",GraphWidth,GraphHeight,1);
    		graph[2]=new SwingGraph("z�����`�����x",GraphWidth,GraphHeight,1);
    		graph[3]=new SwingGraph("x���p�x",GraphWidth,GraphHeight,1);
    		graph[4]=new SwingGraph("y���p�x",GraphWidth,GraphHeight,1);
    		graph[5]=new SwingGraph("z���p�x",GraphWidth,GraphHeight,1);
    	}
    	graph[0].addData(act_data.getLinx(),300);
    	graph[1].addData(act_data.getLiny(),300);
    	graph[2].addData(act_data.getLinz(),300);
    	graph[3].addData(act_data.getEulx(),1);
    	graph[4].addData(act_data.getEuly(),1);
    	graph[5].addData(act_data.getEulz(),1);
    }
    
}
