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

public class CreateTimeEventService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("CreateTimeEventService.doGet",11);
		//�C�x���g�����擾
		String name = request.getParameter("name");

		PrintWriter out = response.getWriter();		
		
		//���[�U������щ^���f�[�^����łȂ����`�F�b�N
		if (name != null) {
			response.setContentType("text/text");
			
			if(TimeManager.createTimeEvent(name))
				create_success_response(name,out);	
			else
				create_error_response(out);
		}else
			create_error_response(out);
	}

	private void create_success_response(String name,PrintWriter out) {
		Debug.debug_print(name+"�C�x���g��������",11);
		out.println("11");
	}
	
	private void create_error_response(PrintWriter out) {
		Debug.debug_print("�C�x���g�������s",11);
		out.println("0");
	}
	
}