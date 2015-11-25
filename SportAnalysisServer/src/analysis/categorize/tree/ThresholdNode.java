package analysis.categorize.tree;

import sub.Debug;
import analysis.categorize.CtegorizeCalc;


public class ThresholdNode extends Node {
	String feature;
	double th;
	public ThresholdNode(String name,String feature,double th) {
		super(name);
		this.feature = feature;//とりあえずいれてみた2014/10/15
		this.th = th;
		// TODO Auto-generated constructor stub
	}
	public String fork(double[][] data){

		double x = 0.0;
		if (feature.equals("x1")) {
			x = CtegorizeCalc.getSpeed(data);
		}
		if (feature.equals("x2")) {
			x = CtegorizeCalc.getAcc(data);
		}
		if (feature.equals("x3")) {
			x = CtegorizeCalc.getTurn(data);
		}
		if (feature.equals("x4")) {
			x = CtegorizeCalc.getXaccave(data);
		}
		Debug.debug_print("ノード名"+name+":"+x, 4);
		if (x < th) {
			return childNodes[0].fork(data);
		} else {
			return childNodes[1].fork(data);
		}

	}

}
