package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import requests.Authentification;
import requests.RequestHandler;
import tools.UserTools;

/**
 * Servlet implementation class AuthentificationServlet
 */
@WebServlet("/login")
public class AuthentificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthentificationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		String login = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			json = Authentification.seConnecter(login, password);
			if (json.has("OK")) {
				System.out.println("COOLL");
				Cookie userCookie = new Cookie("usercookie","loggedIn");
				userCookie.setMaxAge(7 * 24 * 60 * 60 );
				response.addCookie(userCookie);
				HttpSession session = request.getSession(true);
				session.setAttribute("userId", login);
				JSONObject responseJSON = new JSONObject();
				responseJSON.put("message", "user found");
				responseJSON.put("userId", UserTools.getUserId(login));
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().print(responseJSON);
			}else {
				RequestHandler.handleError(response, "unknown user", 200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			requests.RequestHandler.handleError(response, "An internal error occured", 500);

		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();

			try {
				JSONObject json = new JSONObject();
				
				json.put("message", "session was invalidated");
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().print(json);
			} catch (Exception e) {
				requests.RequestHandler.handleError(response, "An internal error occured", 500);
			}
		}
	}

}
