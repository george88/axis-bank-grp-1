package de.axisbank.datenbank;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.impl.Log4JLogger;

import de.axisbank.daos.DaoObject;

public class DB {

	public static Object select(DaoObject daoObject) {
		return new DB().select(createSelect(daoObject, null), daoObject);
	}

	public static void update(DaoObject daoObject) {

	}

	public static void insert(DaoObject daoObject) {

	}

	private static String createSelect(DaoObject daoObject, String columns) {

		String select = "SELECT " + (columns != null ? columns : "*")
				+ " FROM " + daoObject.getTableName();
		String where = "";

		try {
			Class<?> c = Class.forName("de.axisbank.daos."
					+ daoObject.getTableName());
			Method[] ms = c.getDeclaredMethods();
			Object objId = c.getMethod("getId", new Class<?>[] {}).invoke(
					daoObject, new Object[] {});
			if (objId == null || (objId != null && ((Integer) objId) == 0)) {
				for (Method m : ms) {
					String mn = m.getName();
					if (mn.startsWith("get")) {
						Object obj = m.invoke(daoObject, new Object[] {});
						if (obj != null) {
							if (obj instanceof Integer) {
								if (((Integer) obj).intValue() == 0)
									continue;
								where += "`" + mn.substring(3).toLowerCase()
										+ "` = " + ((Integer) obj).intValue()
										+ " AND ";
							}
							if (obj instanceof Double) {
								if (((Double) obj).doubleValue() == 0.0D)
									continue;
								where += "`" + mn.substring(3).toLowerCase()
										+ "` = " + ((Double) obj).doubleValue()
										+ " AND ";
							} else if (obj instanceof String)
								where += "`" + mn.substring(3).toLowerCase()
										+ "` = '" + obj.toString() + "' AND ";
						}
					}
				}
			} else {
				where += "`id` = " + objId.toString();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (where.length() > 0) {
			where = " WHERE " + where.substring(0, where.length() - 4);
		}

		return select + where;
	}

	public DB() {
		connecting();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		closing();
	}

	protected static Connection connection = null;
	private final static String URL = "jdbc:mysql:";
	private final static String SERVER_NAME = "//localhost";
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String PORT = ":3306";
	private final static String DB_NAME = "/axisbank";// "/axis_bank";
	private final static String USER_NAME = "root";
	private final static String PASSWORD = "d3v3l0p3rs";
	private final static String Table_Prefix = "";

	public Object select(String select, DaoObject daoObj) {

		Vector<DaoObject> daoObjects = new Vector<DaoObject>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(select);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				DaoObject daoObject = daoObj.getClass().newInstance();
				for (Method m : daoObject.getClass().getDeclaredMethods()) {
					String mn = m.getName();
					if (mn.startsWith("set")) {
						Class<?>[] parameterTypes = m.getParameterTypes();
						if (parameterTypes[0] == int.class) {
							daoObject
									.getClass()
									.getMethod(mn, parameterTypes)
									.invoke(daoObject,
											new Object[] { rs.getInt(mn
													.substring(3)) });
						} else if (parameterTypes[0] == String.class) {
							daoObject
									.getClass()
									.getMethod(mn, parameterTypes)
									.invoke(daoObject,
											new Object[] { rs.getObject(mn
													.substring(3)) });
						} else if (parameterTypes[0] == double.class) {
							daoObject
									.getClass()
									.getMethod(mn, parameterTypes)
									.invoke(daoObject,
											new Object[] { rs.getDouble(mn
													.substring(3)) });
						} else if (parameterTypes[0] == DaoObject.class) {
							String subSelect = "SELECT * FROM "
									+ parameterTypes[0].getComponentType()
											.getSimpleName()
									+ " WHERE id"
									+ daoObject.getTableName()
									+ " = "
									+ rs.getInt("id" + daoObject.getTableName());

							Object d = select(subSelect,
									(DaoObject) parameterTypes[0].newInstance());
							if (Array.getLength(d) > 0)
								daoObject
										.getClass()
										.getMethod(mn, parameterTypes)
										.invoke(daoObject,
												new Object[] { Array.get(d, 0) });
						} else if (parameterTypes[0].isArray()) {
							String subSelect = "SELECT * FROM "
									+ parameterTypes[0].getComponentType()
											.getSimpleName()
									+ " WHERE id"
									+ daoObject.getTableName()
									+ " = "
									+ rs.getInt("id" + daoObject.getTableName());
							Object d = select(subSelect,
									(DaoObject) parameterTypes[0]
											.getComponentType().newInstance());
							if (Array.getLength(d) > 0) {
								m.invoke(daoObject, new Object[] { d });
							}
						}
					}
				}
				daoObjects.add(daoObject);
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
		return ds;
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
