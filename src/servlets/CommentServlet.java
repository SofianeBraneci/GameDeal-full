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
 * Servlet implementation class CommentServlet
 */
@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession();
		if (userSession.getAttribute("userId") != null)
			RequestHandler.getComments(response, request.getParameter("gameId"));
		else
			RequestHandler.handleError(response, "unauthorized user", 401);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = request.getSession();
		if (userSession.getAttribute("userId") != null)
			RequestHandler.addComment(response, request.getParameter("userId"), request.getParameter("gameId"),
					request.getParameter("comment"));
		else
			RequestHandler.handleError(response, "unauthorized user", 401);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = req.getSession();
		if (userSession.getAttribute("userId") != null)
			RequestHandler.updateComment(resp, req.getParameter("commentId"), req.getParameter("comment"),
					req.getParameter("gameId"));
		else
			RequestHandler.handleError(resp, "unauthorized user", 401);

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession userSession = req.getSession();

		if (userSession.getAttribute("userId") != null)

			RequestHandler.deleteComment(resp, req.getParameter("gameId"), req.getParameter("commentId"));
		else
			RequestHandler.handleError(resp, "unauthorized user", 401);

	}
}
