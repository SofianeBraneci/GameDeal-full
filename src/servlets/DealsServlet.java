package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import requests.RequestHandler;

/**
 * Servlet implementation class DealsServlet
 */
@WebServlet("/deals")
public class DealsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DealsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doget");
		System.out.println(request.getCookies() != null);
		System.out.println(request.getQueryString());
		HttpSession userSession = request.getSession(false);
		if (userSession == null)
			RequestHandler.handleError(response, "unauthorized user", 401);
		if (userSession.getAttribute("userId") != null)
			
			RequestHandler.dealsRequestHandler(response, request.getParameterMap());

		else

			RequestHandler.handleError(response, "unauthorized user", 401);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("dopost");
		doGet(request, response);
	}

}
