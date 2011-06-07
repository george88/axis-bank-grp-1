package de.axisbank.datenbank;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import de.axisbank.daos.DaoObject;
import de.axisbank.tools.DatumsKonvertierung;
import de.axisbank.tools.KonfigFiles;
import de.axisbank.tools.Logging;

public class DB {

	public static Object select(DaoObject daoObject) {
		return new DB().select(MySqlQueryFactory.createSelect(daoObject, null), daoObject, true);
	}

	public static boolean update(DaoObject[] daoObject) {
		return new DB().update(MySqlQueryFactory.createUpdate(daoObject), true);
	}

	public static int[] insert(DaoObject[] daoObject) {
		String[] inserts = MySqlQueryFactory.createInsert(daoObject);
		return new DB().insert(inserts, true);
	}

	public static boolean delete(DaoObject[] daoObject) {
		String[] deletes = MySqlQueryFactory.createDelete(daoObject);
		return new DB().delete(deletes, true);
	}

	public DB() {
		connecting();
	}

	private Connection connection = null;
	private final static String URL = "jdbc:mysql:";
	private final static String SERVER_NAME = "//" + KonfigFiles.getString(KonfigFiles.DB_HOST);
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String PORT = ":" + KonfigFiles.getString(KonfigFiles.DB_PORT);
	private final static String DB_NAME = "/" + KonfigFiles.getString(KonfigFiles.DB_NAME);
	private final static String USER_NAME = KonfigFiles.getString(KonfigFiles.DB_USER);
	private final static String PASSWORD = KonfigFiles.getString(KonfigFiles.DB_PASSWORD);
	protected final static String Table_Prefix = KonfigFiles.getString(KonfigFiles.DB_TABLEPREFIX);

	public Object select(String select, DaoObject daoObj, boolean hauptSelect) {
		if (connection == null)
			return null;
		Vector<Object> daoObjects = new Vector<Object>();
		try {
			Statement stmt = connection.createStatement();
			Logging.logLine("SELECT: " + select + "\n\n");
			if (stmt != null) {
				ResultSet rs = stmt.executeQuery(select);
				while (rs.next()) {
					DaoObject daoObject = daoObj.getClass().newInstance();
					Logging.logLine("\n\nMainTable: " + daoObject.getTableName());
					for (Method m : daoObject.getClass().getDeclaredMethods()) {
						String mn = m.getName();
						if (mn.startsWith("set")) {
							Class<?>[] parameterTypes = m.getParameterTypes();
							if (parameterTypes[0] == int.class) {
								Logging.log("Integer:");
								Object obj = rs.getInt(mn.substring(3));
								Logging.logLine(mn.substring(3) + " = " + obj);

								daoObject.getClass().getMethod(mn, parameterTypes).invoke(daoObject, new Object[] { obj });
							} else if (parameterTypes[0] == String.class) {
								Object obj = null;
								if (mn.substring(3).endsWith("_dt")) {
									Logging.log("String:");
									java.sql.Date o = rs.getDate(mn.substring(3, mn.indexOf("_dt")));
									if (o != null) {
										String d = DatumsKonvertierung.getStringFromDbDate(o);
										Logging.logLine(mn.substring(3) + " = " + d);
										obj = d;
									}

								} else {
									Logging.log("String:");
									Object o = rs.getObject(mn.substring(3));
									Logging.logLine(mn.substring(3) + " = " + o);
									obj = o;
								}
								daoObject.getClass().getMethod(mn, parameterTypes).invoke(daoObject, new Object[] { obj });
							} else if (parameterTypes[0] == double.class) {
								Logging.log("Double:");
								Object obj = rs.getDouble(mn.substring(3));
								Logging.logLine(mn.substring(3) + " = " + obj);

								daoObject.getClass().getMethod(mn, parameterTypes).invoke(daoObject, new Object[] { obj });
							} else if (parameterTypes[0] == long.class) {
								Logging.log("Long:");
								Object obj = rs.getLong(mn.substring(3));
								Logging.logLine(mn.substring(3) + " = " + obj + "\n");
								daoObject.getClass().getMethod(mn, parameterTypes).invoke(daoObject, new Object[] { rs.getLong(mn.substring(3)) });
							}
						}
					}
					Logging.log("Integer:");
					int id = rs.getInt(daoObject.getIdName());
					Logging.logLine(daoObject.getIdName() + " = " + id + "\n");
					daoObject.setId(id);
					daoObjects.add(daoObject);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Object ds = Array.newInstance(daoObj.getClass(), daoObjects.size());
		for (int i = 0; i < Array.getLength(ds); i++)
			Array.set(ds, i, daoObjects.get(i));

		if (hauptSelect)
			closing();
		return ds;
	}

	public boolean update(String[] updates, boolean hauptUpdate) {
		if (connection == null)
			return false;

		if (updates == null)
			return false;

		boolean erfolg = true;
		for (int i = 0; i < updates.length; i++) {
			if (updates[i] != null)
				try {
					Logging.logLine("UPDATE: " + updates[i] + "\n\n");
					Statement stmt = connection.createStatement();
					int j = stmt.executeUpdate(updates[i]);
					Logging.logLine(j + " Datensätze/Datensatz geändert.");
				} catch (SQLException e) {
					erfolg = false;
					e.printStackTrace();
				}
		}
		if (hauptUpdate)
			closing();
		return erfolg;
	}

	public boolean delete(String[] deletes, boolean hauptUpdate) {
		if (connection == null)
			return false;

		if (deletes == null)
			return false;

		boolean erfolg = true;
		for (int i = 0; i < deletes.length; i++) {
			if (deletes[i] != null)
				try {
					Logging.logLine("DELETE: " + deletes[i] + "\n");
					Statement stmt = connection.createStatement();
					int j = stmt.executeUpdate(deletes[i]);
					Logging.logLine(j + " Datensätze/Datensatz gelöscht.");
				} catch (SQLException e) {
					erfolg = false;
					e.printStackTrace();
				}
		}
		if (hauptUpdate)
			closing();
		return erfolg;
	}

	public int[] insert(String[] inserts, boolean hauptInsert) {
		if (connection == null)
			return null;

		if (inserts == null || (inserts != null && inserts.length < 1))
			return null;

		ResultSet rs = null;
		int[] ids = new int[inserts.length];
		for (int i = 0; i < inserts.length; i++) {
			try {
				if (inserts[i] == null)
					continue;
				Statement stmt = connection.createStatement();
				Logging.logLine("INSERT: " + inserts[i] + "\n\n");
				stmt.executeUpdate(inserts[i], Statement.RETURN_GENERATED_KEYS);
				ids[i] = -1;

				rs = stmt.getGeneratedKeys();

				if (rs.next()) {
					ids[i] = rs.getInt(1);
				}
				Logging.logLine("ReturnID ist: " + ids[i]);

			} catch (SQLException e) {
				e.printStackTrace();
				ids[i] = -1;
			}
		}
		if (hauptInsert)
			closing();
		return ids;
	}

	private void connecting() {

		if (connection == null) {
			try {
				Class.forName(DRIVER);
				Properties prop = new Properties();
				prop.put("user", USER_NAME);
				prop.put("password", PASSWORD);
				connection = DriverManager.getConnection(URL + SERVER_NAME + PORT + DB_NAME, prop);
				Logging.logLine("DB-Connection established");
			} catch (Exception e) {
				Logging.logLine("DB-Connection failed");
			}
		}
	}

	private void closing() {
		if (connection != null) {

			try {
				connection.clearWarnings();
				connection.close();
				connection = null;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			Logging.logLine("DB-Connection closed");
		}
	}
}