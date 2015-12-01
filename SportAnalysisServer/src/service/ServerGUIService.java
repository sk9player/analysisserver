package service;

import java.io.IOException;

import javaFXGUI.Main;
import javaFXGUI.SampleController;
import javafx.application.Application;
import javafx.application.Platform;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sub.Debug;

/**
 * Servlet implementation class SensorAnalysisService
 */
@WebServlet("/gui")
public class ServerGUIService extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerGUIService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Application.launch(Main.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug.debug_print("ServerGUIService.doGet", 99999999);
		Platform.runLater(new SampleController());
	}

}
