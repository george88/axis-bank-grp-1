package de.axisbank.datenbank;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;
import de.axisbank.daos.DaoObject;

public class DB {

	public static Object select(DaoObject daoObject) {
		return new DB().select(createSelect(daoObject, null), daoObject);
	}

	public static int[] update(DaoObject[] daoObject) {
		return new DB().update(createUpdate(daoObject));
	}

	public static boolean[] insert(DaoObject[] daoObject) {
		return new DB().insert(createInsert(daoObject), daoObject);
	}

	private static String[] createUpdate(DaoObject[] daoObject) {
		if (daoObject == null)
			return null;
		String[] updates = new String[daoObject.length];
		for (int i = 0; i < updates.length; i++) {
			if (daoObject[i] != null) {
				int id = daoObject[i].getId();
				if (id > 0) {
					String update = "UPDATE " + Table_Prefix
							+ daoObject[i].getTableName() + " SET ";
					try {
						String set = "";
						Class<?> c = Class.forName(daoObject[i].getClass()
								.getPackage().getName()
								+ "." + daoObject[i].getTableName());
						Method[] ms = c.getDeclaredMethods();
						for (Method m : ms) {
							String mn = m.getName();
							if (mn.startsWith("get")) {
								Object o = m.invoke(daoObject[i],
										new Object[] {});
								if (o != null) {
									if (o.getClass().equals(String.class)) {
										set += "`" + mn.substring(3) + "` = '"
												+ o.toString() + "', ";
									} else if (o.getClass().equals(
											Integer.class)
											&& ((Integer) o) != -1) {
										set += "`" + mn.substring(3) + "` = '"
												+ o.toString() + "', ";
									} else if (o.getClass().equals(Long.class)
											&& ((Long) o) != -1L) {
										set += "`" + mn.substring(3) + "` = '"
												+ o.toString() + "', ";
									} else if (o.getClass()
											.equals(Double.class)
											&& ((Double) o) != -1.0D) {
										set += "`" + mn.substring(3) + "` = '"
												+ o.toString() + "', ";
									}
								}
							}
						}
						if (set.length() > 0) {
							update += set.substring(0, set.length() - 2)
									+ " WHERE " + daoObject[i].getIdName()
									+ " = " + id;
							updates[i] = update;
						} else
							updates[i] = null;

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else
					updates[i] = null;
			} else
				updates[i] = null;
		}
		return updates;
	}

	private static String[] createInsert(DaoObject[] daoObject) {
		String[] inserts = new String[daoObject.length];
		for (int i = 0; i < inserts.length; i++) {
			try {
				String insert = "INSERT INTO " + Table_Prefix
						+ daoObject[i].getTableName() + "(";
				Class<?> c = Class.forName(daoObject.getClass().getPackage()
						.getName()
						+ "." + daoObject[i].getTableName());
				Method[] ms = c.getDeclaredMethods();
				String values = "";
				String valueNames = "";
				for (Method m : ms) {
					String mn = m.getName();
					if (mn.startsWith("get")) {
						if (m.getReturnType().isPrimitive()) {
							valueNames += "`" + mn.substring(3) + "`, ";
							values += "'"
									+ m.invoke(daoObject[i], new Object[] {})
											.toString() + "', ";
						}
					}
				}
				if (values.length() > 0 && valueNames.length() > 0) {
					values = values.substring(0, values.length() - 2);
					valueNames = valueNames.substring(0,
							valueNames.length() - 2);
					insert += valueNames + ") VALUES(" + values + ")";
					inserts[i] = insert;
				} else
					inserts[i] = null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}

		return inserts;
	}

	private static String createSelect(DaoObject daoObject, String columns) {
		String select = "SELECT " + (columns != null ? columns : "*")
				+ " FROM " + Table_Prefix + daoObject.getTableName();
		String where = "";

		try {
			Class<?> c = Class.forName(daoObject.getClass().getPackage()
					.getName()
					+ "." + daoObject.getTableName());
			Method[] ms = c.getDeclaredMethods();
			int id = daoObject.getId();
			if (id == 0) {
				for (Method m : ms) {
					String mn = m.getName();
					if (mn.startsWith("get")) {
						Object obj = m.invoke(daoObject, new Object[] {});
						if (obj != null) {
							if (obj instanceof Integer) {
								if (((Integer) obj).intValue() == -1)
									continue;
								where += "`" + mn.substring(3).toLowerCase()
										+ "` = " + ((Integer) obj).intValue()
										+ " AND ";
							} else if (obj instanceof Double) {
								if (((Double) obj).doubleValue() == -1.0D)
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
				where += "`" + daoObject.getIdName() + "` = " + id + "  AND ";
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
		}

		if (daoObject.getReferenzId() != 0
				&& daoObject.getReferenzIdName() != null)
			where += "`" + daoObject.getReferenzIdName() + "` = '"
					+ daoObject.getReferenzId() + "' AND ";

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

		Vector<Object> daoObjects = new Vector<Object>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(select);
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
							System.out.println("String:");
							Object obj = rs.getObject(mn.substring(3));
							System.out.println(mn.substring(3) + " = " + obj);
							daoObject.getClass().getMethod(mn, parameterTypes)
									.invoke(daoObject, new Object[] { obj });
						} else if (parameterTypes[0] == double.class) {
							daoObject
									.getClass()
									.getMethod(mn, parameterTypes)
									.invoke(daoObject,
											new Object[] { rs.getDouble(mn
													.substring(3)) });
						} else if (parameterTypes[0] == long.class) {
							System.out.println("Long:");
							Object obj = rs.getLong(mn.substring(3));
							System.out.println(mn.substring(3) + " = " + obj
									+ "\n");
							daoObject
									.getClass()
									.getMethod(mn, parameterTypes)
									.invoke(daoObject,
											new Object[] { rs.getLong(mn
													.substring(3)) });
						} else if (!parameterTypes[0].isPrimitive()
								&& !parameterTypes[0].isArray()) {
							DaoObject dObj = (DaoObject) parameterTypes[0]
									.newInstance();
							System.out.println("fromTable: "
									+ daoObject.getTableName());
							System.out.println("toAsk: " + dObj.getTableName()
									+ "\n\n");
							dObj.setId(rs.getInt(dObj.getReferenzIdName()));
							String subSelect = createSelect(dObj, null);
							System.out
									.println("subselect: " + subSelect + "\n");
							Object d = select(subSelect, dObj);
							if (Array.getLength(d) > 0)
								daoObject
										.getClass()
										.getMethod(mn, parameterTypes)
										.invoke(daoObject,
												new Object[] { Array.get(d, 0) });
						} else if (parameterTypes[0].isArray()) {
							DaoObject dObj = (DaoObject) parameterTypes[0]
									.getComponentType().newInstance();
							System.out.println("...\nfromTable: "
									+ daoObject.getTableName());
							System.out.println("toAsk: " + dObj.getTableName()
									+ "\n");
							dObj.setReferenzId(rs.getInt(daoObject.getIdName()));
							String subSelect = createSelect(dObj, null);
							System.out.println("subselect: " + subSelect
									+ "\nMethod:" + m.getName() + "...\n\n");
							Object d = select(subSelect, dObj);
							if (Array.getLength(d) > 0) {
								m.invoke(daoObject, new Object[] { d });
							}
						}
					}
				}
				daoObject.setId(rs.getInt(daoObject.getIdName()));
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

	public int[] update(String[] updates) {
		if (updates == null)
			return null;
		int[] counts = new int[updates.length];
		for (int i = 0; i < updates.length; i++) {
			if (updates[i] != null)
				try {
					Statement stmt = connection.createStatement();
					System.out.println("update: " + updates[i] + "\n\n");
					counts[i] = stmt.executeUpdate(updates[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return counts;
	}

	public boolean[] insert(String[] update, DaoObject daoObj[]) {

		if (update == null
				|| daoObj == null
				|| ((daoObj != null && update != null) && update.length != daoObj.length))
			return null;

		boolean[] success = new boolean[daoObj.length];
		try {
			for (int i = 0; i < update.length; i++) {
				Statement stmt = connection.createStatement();
				success[i] = stmt.execute(update[i]);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
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
