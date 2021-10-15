package requests;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import db.Database;
import tools.ErrorTools;
import tools.UserTools;

public class Authentification {

	public static JSONObject seConnecter(String login, String password) throws JSONException {
		JSONObject json;
		try {
			Connection connection = Database.getMySQLConnection();
			if(login == null || password == null) {
				json = ErrorTools.serviceRefused("wrong argument");
			}
			else if(!UserTools.checkPassword(login,password,connection))
				json = ErrorTools.serviceRefused("wrong login or password");
			else {
				json = (new JSONObject()).put("OK", login);
			}
			connection.close();
			return json;
		} catch (SQLException e) {	return ErrorTools.serviceRefused(e.getMessage());
		}
	}
}