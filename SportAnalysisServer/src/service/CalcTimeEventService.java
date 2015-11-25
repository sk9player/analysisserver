package service;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sub.Debug;
import timerdata.time.TimeEvent;
import timerdata.time.TimeManager;
import timerdata.time.TimeUser;

import coach.CoachingManager;

import analysis.AnalysisManager;
import analysis.calc.Calculate;

import data.DataManager;
import data.smartPhone.SmartPhoneActionData;
import data.smartPhone.SmartPhonePersonalData;
import graph.GraphManager;

public class CalcTimeEventService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("CalcTimeEventService.doGet",11);
		//�C�x���g�����擾
		String ename = request.getParameter("ename");
		//�C�x���g�����擾
		String name = request.getParameter("name");
		//�@�̖̂������擾(stat or goal)
		String type = request.getParameter("type");
		//�L�����u���[�V�����p���Ԃ��擾
		String time = request.getParameter("time");

		//���[�U������щ^���f�[�^����łȂ����`�F�b�N
		if (name != null&&type != null&&time != null) {
			Debug.debug_print("�C�x���g�F"+ename+"\n���[�U�F"+name+"\n�L�����u���[�V�����^�C�v�F"+type+"\n���ԁF"+time,11);
			TimeEvent event=TimeManager.time_event_map.get(ename);
			if(type.equals("start")){
				TimeUser user= event.getUser(name);
				user.set_start_time(Long.parseLong(time));
				event.current_user=user;
				response.setContentType("text/text");
				PrintWriter out = response.getWriter();
				start_success_response(out);
			}
			else if(type.equals("goal")){
				if(event.current_user!=null){
				long calc_time=event.current_user.get_time(Long.parseLong(time), event.diff_time);
				response.setContentType("text/text");
				PrintWriter out = response.getWriter();
				goal_success_response(out,event.current_user.user_name,calc_time);
				}else{
					response.setContentType("text/text");
					PrintWriter out = response.getWriter();
					create_error_response(out);

				}
				
			}else{
			
				response.setContentType("text/text");
				PrintWriter out = response.getWriter();
				create_error_response(out);
			
			}
				
			
		}
	}

	private void start_success_response(PrintWriter out) {
		out.println("11");
	}
	private void goal_success_response(PrintWriter out,String name,long time) {
		double timed= (double)time/1000;
		out.println(name+":"+timed);
	}
	
	private void create_error_response(PrintWriter out) {
		out.println("0");
	}
	
}