package analysis.categorize.tree;

import setting.SettingValues;
import sub.Debug;

public class SkatingStateTree {
	Node root;
	boolean init_flg=false;

	public String print(){
		return"";
	}
	public String print(String name){
		return"";
	}
	public void normarize(){
	}
	public void normarize(String name){
	}
	public String calc(double[][] data){
		return root.fork(data);
	}
	public String calc(double[][] data,String name){
		return root.fork(data,name);
	}



	public void init(){
		Debug.debug_print("SkatingStateTree.init",11);
		if(init_flg)
			return;



		//---------ツリープログラム書き込みここから--------------

		Node node1= new ThresholdNode("node1","x4",-0.00100008+SettingValues.THRESHOLD_VAL);
		Node node2= new StateNode("straight_skating_state");
		Node node3= new StateNode("corner_skating_state");
		root = node1;
		node1.addchildNodes(node2,node3);

/*		Node node1= new ThresholdNode("node1","x4",-0.00430282);
		Node node2= new StateNode("stop_state");
		Node node3= new ThresholdNode("node3","x3",65.7318);
		Node node4= new StateNode("stop_state");
		Node node5= new StateNode("corner_skating_state");
		root = node1;
		node1.addchildNodes(node2,node3);
		node3.addchildNodes(node4,node5);
*/

		//---------ツリープログラム書き込みここまで--------------




		init_flg=true;
	}
}
