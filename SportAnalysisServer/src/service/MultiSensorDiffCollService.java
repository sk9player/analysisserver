package service;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import sub.Debug;
import sub.OutPutText;

import coach.CoachingManager;

import analysis.AnalysisManager;
import analysis.calc.Calculate;

import data.DataManager;
import data.multiSensor.MultiSensorPersonalData;
import data.sensor.SensorActionData;
import data.sensor.SensorPersonalData;
import graph.GraphManager;


/**
 * 各センサの時間差を取得し、キャリブレーション値を設定するプログラム
 * @author OZAKI
 *
 */
public class MultiSensorDiffCollService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("MultiSensorDiffCollService.doGet",11);
		//ユーザ名を取得
		String name = request.getParameter("name");
		//時間差データを取得
		String data = request.getParameter("data");
		//ユーザ名および運動データが空でないかチェック
		if (name != null && data != null) {
			MultiSensorPersonalData per_data;
			//ユーザデータの取得
			DataManager.set_multi_sensor_time_diff(name, data);
			per_data = DataManager.multi_sensor_personal_map.get(name);
				

			PrintWriter out = response.getWriter();
			create_response(0,out,per_data);			
		}else{
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_error_response(out);			
		}
	}

	private void create_response(int ret_num,PrintWriter out,MultiSensorPersonalData per_data) {
		Debug.debug_print("返却値"+ret_num,11);
		out.print(ret_num);
	}
	
	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}
	
}