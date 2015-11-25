/**
 * �X�}�[�g�t�H���Z���T�f�[�^���擾�����͂���v���O����
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
		Debug.debug_print("AnalysisService.init ���֐���ݒ�",11);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("AnalysisService.doGet",11);
		//���[�U�����擾
		String name = request.getParameter("name");
		//�R�[�`���[�h���擾
		String mode = request.getParameter("mode");
		//�^���f�[�^���擾
		String data = request.getParameter("data");
		
		//�ݒ�l���擾
		String val = request.getParameter("val");
		
		//���[�U������щ^���f�[�^����łȂ����`�F�b�N
		if (name != null && data != null) {
			//���[�U�̉^���f�[�^��ݒ�i���[�U�f�[�^���Ȃ��ꍇ�͐V�K�쐬�j
			SmartPhoneActionData act_data = DataManager.create_smart_phone_data(name,mode,data);
			//���[�U�f�[�^�̎擾
			SmartPhonePersonalData per_data = DataManager.personal_map.get(name);
			//���n����
			DataManager.time_comp.time_comp(per_data, act_data);
			//���Ԑ��K�������^���f�[�^�𕪐͂��ĉ^���p�����[�^�ɐݒ�
			AnalysisManager.Create_para.create(per_data);
			//�p�����[�^���͌��ʂ���R�[�`���OID���擾(��r�Ώۂ�����ꍇ�͔�r�Ώۂ��ݒ�)
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
		Debug.debug_print("�ԋp�l"+ret_num,11);
		out.println(ret_num);
	}
	
	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}
	
}