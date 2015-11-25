package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sub.Debug;
import data.DataManager;
import data.sensor.SensorPersonalData;

/**
 * Servlet implementation class ShowParamaterService
 */
@WebServlet("/shp")
public class ShowParamaterService extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowParamaterService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug.debug_print("ShowSensorParamaterService.doGet",11);
		//ユーザ名を取得
		String name = request.getParameter("name");
		//ユーザ名が空でないかチェック
		if (name != null) {
			//ユーザデータの取得
			SensorPersonalData per_data = DataManager.sensor_personal_map.get(name);


			PrintWriter out = response.getWriter();
			create_response(per_data.act_para.toComString(),out,per_data);
		}else{
			response.setContentType("text/text");
			PrintWriter out = response.getWriter();
			create_error_response(out);
		}
	}

	private void create_response(String data,PrintWriter out,SensorPersonalData per_data) {
		Debug.debug_print("返却値"+data,11);
		out.print(data);
	}

	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}

}