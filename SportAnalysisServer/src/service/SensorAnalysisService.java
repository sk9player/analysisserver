package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import setting.SettingValues;
import sub.Debug;
import analysis.AnalysisManager;
import analysis.calc.Calculate;
import analysis.categorize.CategorizeManager;
import coach.CoachingManager;
import data.DataManager;
import data.multiSensor.MultiSensorActionData;
import data.multiSensor.MultiSensorPersonalData;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;

/**
 * Servlet implementation class SensorAnalysisService
 */
@WebServlet("/sana")
public class SensorAnalysisService extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SensorAnalysisService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Calculate.set_window_func_s();
		Debug.debug_print("SensorAnalysisService.init 窓関数を設定", 11);
		CategorizeManager.state_categorize.init();
		Debug.debug_print("SensorAnalysisService.init 状態識別器を構築", 11);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug.debug_print("SensorAnalysisService.doGet", 99999999);

		// ユーザ名を取得
		String name = request.getParameter("name");
		// コーチモードを取得
		String mode = request.getParameter("mode");
		// 運動データを取得
		String data = request.getParameter("data");
		// 設定値を取得
		String val = request.getParameter("val");
		// ユーザ名および運動データが空でないかチェック
		if (name != null && data != null && mode != null) {

			if (mode.equals("9")) {
				// ユーザデータの取得
				MultiSensorActionData multi_act_data = DataManager.create_multi_sensor_data(name, mode, data);
				MultiSensorPersonalData per_data = DataManager.multi_sensor_personal_map.get(name);

				// 時系列補間
				DataManager.time_comp.time_comp(per_data, multi_act_data);

				// 時系列補間した運動データを分析して運動パラメータに設定(初回は-1を返して終了)
				int analysis_count = AnalysisManager.Create_multi_sensor_para.create(per_data);
				if (analysis_count == 1) {
					PrintWriter out = response.getWriter();
					create_response(-1, out);
					return;
				} else if (analysis_count < 5) {
					PrintWriter out = response.getWriter();
					create_response(0, out);
					return;
				}
				// グラフを生成（デバッグ）
				// GraphManager.createGraph(act_data);

				// 指示内容を取得してクライアントに返却
				int ret = 0;

				if (val != null||!val.equals("0"))
					ret = CoachingManager.Compare_m.compare_parameter(
							per_data.coach_mode, per_data, Double.parseDouble(val));
				else
					ret = CoachingManager.Compare_m.compare_parameter(
							per_data.coach_mode, per_data);
				PrintWriter out = response.getWriter();
				create_response(ret, out);
			} else {
				// ユーザの運動データを設定（ユーザデータがない場合は新規作成）
				SensorActionData act_data = DataManager.create_sensor_data(
						name, mode, data);
				// ユーザデータの取得
				SensorPersonalData per_data = DataManager.sensor_personal_map.get(name);

				// 時系列補間してユーザデータに設定
				DataManager.time_comp.time_comp(per_data, act_data);

				// 時系列補間した運動データを分析して運動パラメータに設定(初回は-1(開始ID)を返して終了、5回目までは0(指示なし)を返却)
				int analysis_count = AnalysisManager.Create_sensor_para
						.createFeature(per_data);
				if (analysis_count == 1) {
					PrintWriter out = response.getWriter();
					create_response(-1, out);
					return;
				} else if (analysis_count < 5) {
					PrintWriter out = response.getWriter();
					create_response(0, out);
					return;
				}
				// グラフを生成（デバッグ）
				// GraphManager.createGraph(act_data);

				// 指示内容を取得してクライアントに返却
				int ret = 0;

				if (val != null||!val.equals("0"))
					ret = CoachingManager.Compare_s.compare_parameter(
							per_data.coach_mode, per_data, Double.parseDouble(val));
				else
					ret = CoachingManager.Compare_s.compare_parameter(
							per_data.coach_mode, per_data);
				PrintWriter out = response.getWriter();
				create_response(ret, out);
				}

		} else {
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_error_response(out);
		}
	}


	private void create_response(int ret_num, PrintWriter out) {
		if(SettingValues.COACH_ONOFF==1){
			Debug.debug_print("返却値" + ret_num+" ---------------------------------------------------------------------------", 11);
			out.print(ret_num);
		}else{
			Debug.debug_print("非返却：返却値" + ret_num+" ---------------------------------------------------------------------------", 11);
			out.print(0);
		}
	}

	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}

}
