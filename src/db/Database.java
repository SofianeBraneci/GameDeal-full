package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;


import javax.naming.InitialContext;
import javax.naming.NamingException;
// class en charge de la connexion aux base de données
public class Database {
	
	private DataSource dataSource;
	public Database(String ressource_name) throws SQLException{
		try {
			// construire l'objet dataSource à partir du fichier de context
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/"+ressource_name);
		}catch (NamingException e) {
			throw new SQLException(ressource_name+" unreachable :"+e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// on créer une nouvelle connexion
		return DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"+DBStatic.mysql_db+
				"?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris",
				DBStatic.mysql_user, DBStatic.mysql_password);
	}

}