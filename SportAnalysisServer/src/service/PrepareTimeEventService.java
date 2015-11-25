package service;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sub.Debug;
import timerdata.time.TimeManager;

import coach.CoachingManager;

import analysis.AnalysisManager;
import analysis.calc.Calculate;

import data.DataManager;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;
import graph.GraphManager;

public class PrepareTimeEventService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("PrepareTimeEventService.doGet",11);
		//イベント名を取得
		String name = request.getParameter("name");
		//機体の役割を取得(stat or goal)
		String type = request.getParameter("type");
		//キャリブレーション用時間を取得
		String time = request.getParameter("time");

		//ユーザ名および運動データが空でないかチェック
		if (name != null&&type != null&&time != null) {
			Debug.debug_print("イベント："+name+"\nキャリブレーションタイプ："+type+"\n時間："+time,11);
			TimeManager.time_event_map.get(name).prepareTimeEvent(type,Long.parseLong(time));
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_success_response(out);
		}
	}

	private void create_success_response(PrintWriter out) {
		out.println("11");
	}
	
	private void create_error_response(PrintWriter out) {
		out.println("0");
	}
	
}