package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;

public class UserTools {
	
	public static boolean checkPassword(String login, String password, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT login, password FROM Users WHERE login ='"+login+"' AND password = '"+password+"'");
		if(!result.next()) {
			result.close();
			statement.close();
			return false;
		}
		result.close();
		statement.close();
		return true;
	}
	
	public static int getIdU(String login, Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT id FROM Users WHERE login = '"+login+"'");
		if(!result.next()) {
			result.close(); statement.close();
			return 0;
		}
		int id = result.getInt("id");
		result.close(); statement.close();
		return id;
	}
	
	public static int getUserId(String login) {
		
		Connection connection = null;
		try {
			connection =  Database.getMySQLConnection();
			PreparedStatement statement = 	connection.prepareStatement("SELECT id FROM Users WHERE login = ?");
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt("id");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;		
		}		
		finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		 
	}
}
