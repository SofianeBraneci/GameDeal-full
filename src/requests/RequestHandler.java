package requests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.Database;

public class RequestHandler {

	private final static String DEALS_BASE_URL = "https://www.cheapshark.com/api/1.0/deals?";

	private final static String GAMES_BASE_URL = "https://www.cheapshark.com/api/1.0/games?";

	private static String constructURL(Map<String, String[]> requestParams, String baseUrl) {
		StringBuilder requestURLBuilder = new StringBuilder(baseUrl);
		for (Entry<String, String[]> entry : requestParams.entrySet()) {
			System.out.println(entry.getKey());
			requestURLBuilder.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");

		}
		requestURLBuilder.setLength(requestURLBuilder.length() - 1);
		return requestURLBuilder.toString();
	}

	public static void dealsRequestHandler(HttpServletResponse servletResponse, Map<String, String[]> requestParams) {

		String requestURL = constructURL(requestParams, DEALS_BASE_URL);

		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(30))
				.build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURL)).GET().build();
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			System.out.println(requestParams.containsKey("id"));
			JSONObject json = new JSONObject();
			if (requestParams.containsKey("id")) {
				System.out.println(response.body());
				json.put("game", new JSONObject(response.body()));
			} else {
				JSONArray array = new JSONArray(response.body());
				json.put("games", array);
			}
			servletResponse.setContentType("application/json");
			servletResponse.setStatus(200);
			servletResponse.getWriter().print(json);
		} catch (IOException | InterruptedException | JSONException e) {
			handleError(servletResponse, "an error occured when trying to contact the API", 500);
			e.printStackTrace();

		}
	}

	/*
	 * Handles game lookup by id or can search for games by title too
	 * 
	 */
	public static void gamesRequestHandler(HttpServletResponse servletResponse, Map<String, String[]> requestParams) {

		String requestURL = constructURL(requestParams, GAMES_BASE_URL);

		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(30))
				.build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestURL)).GET().build();

		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			JSONObject json = new JSONObject();
			if (requestParams.containsKey("id")) {
				json.put("game", new JSONObject(response.body()));
			} else {
				JSONArray array = new JSONArray(response.body());
				json.put("games", array);
			}
			servletResponse.setContentType("application/json");
			servletResponse.setStatus(200);
			servletResponse.getWriter().print(json);
		} catch (IOException | InterruptedException | JSONException e) {
			handleError(servletResponse, "an error occured when trying to contact the API", 500);
		}
	}

	// handling comments add remove update delete, and retrieve all comments of a
	// particular game

	public static void getComments(HttpServletResponse response, String gameId) {
		System.out.println("id = "+ gameId);
		Connection conn = null;

		try {
			conn = Database.getMySQLConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM Comments WHERE gameId = ?");
			PreparedStatement findUsername = conn.prepareStatement("SELECT login FROM Users WHERE id = ?");
			statement.setString(1, gameId);
			ResultSet comments = statement.executeQuery();
			JSONArray array = new JSONArray();

			while (comments.next()) {
				JSONObject comment = new JSONObject();
				String userId = comments.getString("userId");
				findUsername.setString(1, userId);
				// querying the database for the user name
				ResultSet userlogin = findUsername.executeQuery();
				userlogin.next();
				String login = userlogin.getString("login");
				// constructing the json object
				comment.put("id", comments.getString("commentID"));
				comment.put("user", login);
				comment.put("comment", comments.getString("comment"));
				comment.put("date", comments.getString("date"));
				array.put(comment);
			}

			JSONObject json = new JSONObject();
			json.put("comments", array);

			response.setContentType("application/json");
			response.setStatus(200);
			response.getWriter().print(json);

		} catch (Exception e) {
			e.printStackTrace();
			handleError(response, "an error occured when trying to contact the API", 500);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void updateComment(HttpServletResponse response, String commentId, String comment, String gameId) {

		Connection connection = null;
		System.out.println(commentId + " " + comment);
		try {

			connection = Database.getMySQLConnection();
			PreparedStatement updateStatement = connection
					.prepareStatement("UPDATE Comments SET comment = ? WHERE commentId = ?");
			updateStatement.setString(1, comment);
			updateStatement.setString(2, commentId);
			updateStatement.executeUpdate();
			getComments(response, gameId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void addComment(HttpServletResponse response, String userId, String gameId, String comment) {
		Connection connection = null;

		try {
			Date date = Calendar.getInstance().getTime();  
            DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:s");  
            String strDate = dateFormat.format(date);
            
			connection = Database.getMySQLConnection();
			PreparedStatement insertStatement = connection
					.prepareStatement("INSERT INTO Comments(userId, gameId, comment, date) VALUES(?,?,?,?)");
			insertStatement.setString(1, userId);
			insertStatement.setString(2, gameId);
			insertStatement.setString(3, comment);
			insertStatement.setString(4, strDate);

			insertStatement.executeUpdate();

			connection.close();
			getComments(response, gameId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteComment(HttpServletResponse response, String gameId, String commentId) {
		Connection connection = null;

		try {
			connection = Database.getMySQLConnection();
			PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Comments WHERE commentId = ?");
			deleteStatement.setString(1, commentId);
			deleteStatement.executeUpdate();
			connection.close();
			getComments(response, gameId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void handleError(HttpServletResponse servletResponse, String errorMessage, int httpStatue) {

		try {
			JSONObject json = new JSONObject();
			json.put("message", errorMessage);
			servletResponse.setStatus(httpStatue);
			servletResponse.setContentType("application/json");
			servletResponse.getWriter().print(json);
		} catch (Exception e) {
			;
		}
	}

	public static void handleSuccess(HttpServletResponse servletResponse, String message, int httpCode) {

		try {
			JSONObject json = new JSONObject();
			json.put("message", message);
			servletResponse.setStatus(httpCode);
			servletResponse.setContentType("application/json");
			servletResponse.getWriter().print(json);
		} catch (Exception e) {
			;
		}
	}

	public static boolean searchForCookie(Cookie[] cookies, String cookieName) {

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName))
				return true;
		}
		return false;
	}
}
