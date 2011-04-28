package de.axis_bank.datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import de.axis_bank.daos.DaoObject;

public class DB {

	public static DaoObject select(DaoObject daoObject) {
		return null;
	}

	public static void update(DaoObject daoObject) {

	}

	public static void insert(DaoObject daoObject) {

	}

	private static String createSelect(DaoObject daoObject) {

		String select = "";

		try {
			Class<?> c = Class.forName(daoObject.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return select;
	}
}

class DbMySQL {

	protected static Connection connection = null;
	private final static String URL = "jdbc:mysql:";
	private final static String SERVER_NAME = "//localhost";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String PORT = ":5554";
	private final static String DB_NAME = "/";
	private final static String USER_NAME = "";
	private final static String PASSWORD = "";
	private final static String Table_Prefix = "";

	public DbMySQL() {

	}

	private static void connecting() {

		if (connection == null) {
			try {
				Class.forName(DRIVER);
				Properties prop = new Properties();
				prop.put("user", USER_NAME);
				prop.put("password", PASSWORD);
				connection = DriverManager.getConnection(URL + SERVER_NAME
						+ PORT + DB_NAME, prop);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void closing() {
		if (connection != null) {

			try {
				connection.clearWarnings();
				connection.close();
				connection = null;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void reconnect() {
		closing();
		connecting();
	}

}
