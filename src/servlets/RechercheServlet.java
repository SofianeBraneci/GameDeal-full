package servlets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import requests.RequestHandler;

/**
 * Servlet implementation class RechercheServlet
 */
@WebServlet("/search")
public class RechercheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RechercheServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("userId");
		if (login == null) {
			RequestHandler.handleError(response, "unauthorized user", 401);
		}

		String title = request.getParameter("Title");
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest requestToApi = HttpRequest.newBuilder()
				.uri(URI.create("https://www.cheapshark.com/api/1.0/games?title=" + title)).build();
		try {
			HttpResponse<String> responseFromApi = client.send(requestToApi, HttpResponse.BodyHandlers.ofString());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("results", new JSONArray(response.getWriter().append(responseFromApi.body())));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			RequestHandler.handleError(response, "An error occured", 500);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			RequestHandler.handleError(response, "An error occured", 500);
		}
	}
}
