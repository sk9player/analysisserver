package service;

import graph.GraphManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sub.Debug;
import analysis.AnalysisManager;
import coach.CoachingManager;
import data.DataManager;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;

public class ShowGraphService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("ShowGraphService.doGet",11);
		//ユーザ名を取得
		String name = request.getParameter("name");
		//コーチモードを取得
		String mode = request.getParameter("mode");
		//運動データを取得
		String data = request.getParameter("data");
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
			//パラメータ分析結果からコーチングIDを取得
			int ret = CoachingManager.Compare_sp.compare_parameter(per_data.coach_mode, per_data);

			//グラフを生成
			GraphManager.createGraph(act_data);
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