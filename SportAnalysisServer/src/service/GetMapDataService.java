package service;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import sub.Debug;
import sub.OutPutText;

import data.DataManager;
import data.smartPhone.SmartPhoneActionData;
import graph.GraphManager;

/**
 * マップ情報を返すクラス
 * 
 * @author OZAKI
 * 
 */
public class GetMapDataService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private float testf = 0.000001f;
	
	private int txt_index = 0;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Debug.debug_print("GetMapDataService.doGet",11);
		// ユーザ名を取得
		String name = request.getParameter("name");
		if (name != null) {

			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_response(out, name);
		} else {
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_error_response(out);
		}

	}

	private void create_response(PrintWriter out, String name) {
		System.out.println("called");
		String s = DataManager.personal_map.get(name).act_para.getCycle()
				+ ","
				+ DataManager.personal_map.get(name).act_para.getStride()
				+ ","
				+ DataManager.personal_map.get(name).act_para.getNow_latitude()
				+ ","
				+ DataManager.personal_map.get(name).act_para
						.getNow_longitude();
		out.println(s);
		txt_index++;

	}

	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}

}