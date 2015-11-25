/**
 * スマートフォンセンサデータを取得し分析するプログラム
 */

package service;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sub.Debug;

import coach.CoachingManager;

import analysis.AnalysisManager;
import analysis.calc.Calculate;

import data.DataManager;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;
import graph.GraphManager;

public class AnalysisService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		Calculate.set_window_func_sp();
		Debug.debug_print("AnalysisService.init 窓関数を設定",11);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("AnalysisService.doGet",11);
		//ユーザ名を取得
		String name = request.getParameter("name");
		//コーチモードを取得
		String mode = request.getParameter("mode");
		//運動データを取得
		String data = request.getParameter("data");
		
		//設定値を取得
		String val = request.getParameter("val");
		
		//ユーザ名および運動データが空でないかチェック
		if (name != null && data != null) {
			//ユーザの運動データを設定（ユーザデータがない場合は新規作成）
			SmartPhoneActionData act_data = DataManager.create_smart_phone_data(name,mode,data);
			//ユーザデータの取得
			SmartPhonePersonalData per_data = DataManager.personal_map.get(name);
			//時系列補間
			DataManager.time_comp.time_comp(per_data, act_data);
			//時間正規化した運動データを分析して運動パラメータに設定
			AnalysisManager.Create_para.create(per_data);
			//パラメータ分析結果からコーチングIDを取得(比較対象がある場合は比較対象も設定)
			int ret = 0;
			if(val != null)
				ret = CoachingManager.Compare_sp.compare_parameter(per_data.coach_mode, per_data,Double.parseDouble(val));
			else
				ret = CoachingManager.Compare_sp.compare_parameter(per_data.coach_mode, per_data);
			
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_response(ret,out);			
		}else{
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_error_response(out);			
		}
	}

	private void create_response(int ret_num,PrintWriter out) {
		Debug.debug_print("返却値"+ret_num,11);
		out.println(ret_num);
	}
	
	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}
	
}