package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import requests.RequestHandler;
import requests.UserRegistrationSqlRequest;
import tools.User;
import tools.UserTools;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/signup")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserRegistrationSqlRequest userRegistration = new UserRegistrationSqlRequest();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("alll worinking");
		RequestDispatcher dispatcher = request.getRequestDispatcher("inscription.html");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String email = request.getParameter("email");

		User user = new User(firstName, lastName, userName, passWord, email);

		if (passWord.equals(confirmPassword)) {
			try {
				if (userRegistration.checkUserName(userName)) {
					RequestHandler.handleError(response, "Username already exists", 200);

				} else {
					if (userRegistration.checkEmail(email)) {
						RequestHandler.handleError(response, "Email already exists", 200);

					} else {
						Cookie userCookie = new Cookie("usercookie","loggedIn");
						userCookie.setMaxAge(7 * 24 * 60 * 60 );
						response.addCookie(userCookie);
						userRegistration.registerUser(user);
						HttpSession session = request.getSession(true);
						session.setAttribute("userId", userName);
						JSONObject json = new JSONObject();
						json.put("message", "user signed in");
						json.put("userId", UserTools.getUserId(userName));		
						response.setStatus(200);
						response.setContentType("application/json");
						response.getWriter().print(json);
						// this should work!! and commit
						response.getWriter().flush();
						

					}
				}
			} catch (ClassNotFoundException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			RequestHandler.handleError(response, "Passwords are not corresponding, please try again", 200);
		}

	}
}
