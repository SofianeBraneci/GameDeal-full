package requests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;
import tools.User;

public class UserRegistrationSqlRequest {

	public int registerUser(User user) throws ClassNotFoundException {

		String INSERT_USERS_SQL = "INSERT INTO Users" + "(LastName,FirstName,Login,Password,Email) VALUES "
				+ "(?,?,?,?,?)"; // Requete SQL d'insertion

		int result = 0;

		try {

			Connection connection = Database.getMySQLConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getLastName());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getUserName());
			preparedStatement.setString(4, user.getPassWord());
			preparedStatement.setString(5, user.getEmail());

			result = preparedStatement.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Boolean checkUserName(String userName) throws ClassNotFoundException {
		String CHECK_USER_NAME_SQL = "SELECT * FROM Users WHERE Login = '" + userName + "'";

		ResultSet queryResult = null;

		try {
			Connection connection = Database.getMySQLConnection();

			Statement statement = connection.createStatement();

			queryResult = statement.executeQuery(CHECK_USER_NAME_SQL);

			if (queryResult.next())
				return true;
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean checkEmail(String email) throws ClassNotFoundException {
		String CHECK_USER_NAME_SQL = "SELECT * FROM Users WHERE Email = '" + email + "'";

		ResultSet queryResult = null;

		try {
			Connection connection = Database.getMySQLConnection();

			Statement statement = connection.createStatement();

			queryResult = statement.executeQuery(CHECK_USER_NAME_SQL);

			if (queryResult.next())
				return true;
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
