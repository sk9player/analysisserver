package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import setting.SettingValues;
import sub.Debug;
import sub.OutPutText;
import analysis.categorize.CategorizeManager;

/**
 * Servlet implementation class SettingValuesService
 */
@WebServlet("/setting")
public class SettingValuesService extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingValuesService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug.debug_print("SettingService.doGet", 11);
		boolean setting_flg = false;
		String val = request.getParameter("coach_onoff");
		Enumeration<String> e = request.getParameterNames();
	while ( e.hasMoreElements() ) {
	String name = (String) e.nextElement();
	System.out.println( name + "=" + request.getParameter(name) + "<br>");
	}
		System.out.println("-2please"+request.getParameter("coach_onoff"));
		System.out.println("-2please"+val);
		if (val != null) {
			SettingValues.COACH_ONOFF = Integer.parseInt(val);
			Debug.debug_print("COACH_ONOFF:" + val, 99);
			setting_flg = true;
		}





		val = request.getParameter("threshold_val");
		if (val != null) {
			SettingValues.THRESHOLD_VAL = Double.parseDouble(val);
			Debug.debug_print("THRESHOLD_VAL:" + val, 99);
			setting_flg = true;
		}
		//状態識別器を構築
		CategorizeManager.state_categorize.init();

		val = request.getParameter("coach_freq");
		if (val != null) {
			SettingValues.COACH_FREQ = Integer.parseInt(val);
			Debug.debug_print("COACH_FREQ:" + val, 99);
			setting_flg = true;
		}
		val = request.getParameter("coach_score_threshold");
		if (val != null) {
			SettingValues.COACH_SCORE_THRESHOLD = Double.parseDouble(val);
			Debug.debug_print("COACH_SCORE_THRESHOLD:" + val, 99);
			setting_flg = true;
		}
		val = request.getParameter("max_pitch");
		if (val != null) {
			SettingValues.MAX_PITCH = Double.parseDouble(val);
			if (SettingValues.MAX_PITCH == 0.0) {
				SettingValues.PITCH_SCORE_COEF = 0;
				Debug.debug_print("PITCH_SCORE_COEF:"
						+ SettingValues.PITCH_SCORE_COEF, 99);
			} else {
				val = request.getParameter("min_pitch");
				if (val != null) {
					SettingValues.MIN_PITCH = Double.parseDouble(val);
					SettingValues.PITCH_SCORE_COEF = (int) (SettingValues.COACH_SCORE_THRESHOLD / ((SettingValues.MAX_PITCH - SettingValues.MIN_PITCH) / 2));
					Debug.debug_print("PITCH_SCORE_COEF:"
							+ SettingValues.PITCH_SCORE_COEF, 99);
					setting_flg = true;
				}
			}
		}
		val = request.getParameter("max_diff_avez");
		if (val != null) {
			SettingValues.MAX_DIFF_AVEZ = Double.parseDouble(val);
			if (SettingValues.MAX_DIFF_AVEZ == 0.0) {
				SettingValues.DIFF_AVEZ_SCORE_COEF = 0;
				Debug.debug_print("DIFF_AVEZ_SCORE_COEF:"
						+ SettingValues.DIFF_AVEZ_SCORE_COEF, 99);
			} else {
				val = request.getParameter("min_diff_avez");
				if (val != null) {
					SettingValues.MIN_DIFF_AVEZ = Double.parseDouble(val);
					SettingValues.DIFF_AVEZ_SCORE_COEF = (int) (SettingValues.COACH_SCORE_THRESHOLD / ((SettingValues.MAX_DIFF_AVEZ - SettingValues.MIN_DIFF_AVEZ) / 2));
					Debug.debug_print("DIFF_AVEZ_SCORE_COEF:"
							+ SettingValues.DIFF_AVEZ_SCORE_COEF, 99);
					setting_flg = true;
				}
			}
		}
		val = request.getParameter("max_avex");
		if (val != null) {
			SettingValues.MAX_AVEX = Double.parseDouble(val);
			if (SettingValues.MAX_AVEX == 0.0) {
				SettingValues.AVEX_SCORE_COEF = 0;
				Debug.debug_print("AVEX_SCORE_COEF:"
						+ SettingValues.AVEX_SCORE_COEF, 99);
			} else {
				val = request.getParameter("min_avex");
				if (val != null) {
					SettingValues.MIN_AVEX = Double.parseDouble(val);
					SettingValues.AVEX_SCORE_COEF = (int) (SettingValues.COACH_SCORE_THRESHOLD / ((SettingValues.MAX_AVEX - SettingValues.MIN_AVEX) / 2));
					Debug.debug_print("AVEX_SCORE_COEF:"
							+ SettingValues.AVEX_SCORE_COEF, 99);
					setting_flg = true;
				}
			}
		}
		// ベストパラメーターの設定
		val = request.getParameter("best_pitch");
		if (val != null) {
			SettingValues.BEST_CYCLE = Double.parseDouble(val);
			Debug.debug_print("BEST_CYCLE:" + val, 99);
			setting_flg = true;
		}
		val = request.getParameter("best_eurx_ave");
		if (val != null) {
			SettingValues.BEST_EURX_AVE = Double.parseDouble(val);
			Debug.debug_print("BEST_EURX_AVE:" + val, 99);
			setting_flg = true;
		}
		val = request.getParameter("best_eurz_diff_ave");
		if (val != null) {
			SettingValues.BEST_EURZ_DIFF_AVE = Double.parseDouble(val);
			Debug.debug_print("BEST_EURZ_DIFF_AVE:" + val, 99);
			setting_flg = true;
		}
		PrintWriter out = response.getWriter();
		if (setting_flg) {
			OutPutText.output_txt("setting", SettingValues.get_str_para() + "\n",
					0, true);
			create_response(0, out);
		} else
			data_response(out);
	}

	private void create_response(int ret_num, PrintWriter out) {
		Debug.debug_print("返却値" + ret_num, 11);
		out.print(ret_num);
	}

	private void create_error_response(PrintWriter out) {
		out.println("fault");
	}

	private void data_response(PrintWriter out) {
		out.println(SettingValues.get_para());
	}
}
