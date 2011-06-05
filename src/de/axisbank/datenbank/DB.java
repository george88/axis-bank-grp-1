package de.axisbank.datenbank;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

import de.axisbank.daos.DaoObject;
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
										Calendar c = Calendar.getInstance();
										c.setTime(o);
										String d = (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH)) + "."
												+ (c.get(Calendar.MONTH) < 10 ? "0" + c.get(Calendar.MONTH) : c.get(Calendar.MONTH)) + "." + c.get(Calendar.YEAR);

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
							// else if (!parameterTypes[0].isPrimitive()
							// && !parameterTypes[0].isArray()) {
							//
							// if
							// (lastAskedSubClasses.contains(parameterTypes[0]))
							// {
							// continue;
							// }
							// lastAskedSubClasses.add(parameterTypes[0]);
							//
							// DaoObject dObj = (DaoObject)
							// parameterTypes[0]
							// .newInstance();
							//
							// Logging.log("SubTable: "
							// + dObj.getTableName() + "\n");
							// dObj.setId(rs.getInt(dObj.getReferenzIdName()));
							// String subSelect =
							// MySqlQueryFactory.createSelect(
							// dObj, null);
							//
							// Object d = select(subSelect, dObj, false);
							// if (Array.getLength(d) > 0)
							// daoObject
							// .getClass()
							// .getMethod(mn, parameterTypes)
							// .invoke(daoObject,
							// new Object[] { Array.get(d, 0) });
							// } else if (parameterTypes[0].isArray()) {
							//
							// if
							// (lastAskedSubClasses.contains(parameterTypes[0]))
							// {
							// continue;
							// }
							// lastAskedSubClasses.add(parameterTypes[0]);
							//
							// DaoObject dObj = (DaoObject)
							// parameterTypes[0]
							// .getComponentType().newInstance();
							//
							// Logging.log("SubTable: "
							// + dObj.getTableName() + "\n");
							// dObj.setReferenzId(rs.getInt(daoObject.getIdName()));
							// String subSelect =
							// MySqlQueryFactory.createSelect(
							// dObj, null);
							// Object d = select(subSelect, dObj, false);
							// if (Array.getLength(d) > 0) {
							// m.invoke(daoObject, new Object[] { d });
							// }
							// }
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

class MySqlQueryFactory {
	protected static String[] createUpdate(DaoObject[] daoObject) {
		if (daoObject == null)
			return null;
		String[] updates = new String[daoObject.length];
		for (int i = 0; i < updates.length; i++) {
			if (daoObject[i] != null) {
				int id = daoObject[i].getId();
				if (id > 0) {
					String update = "UPDATE " + DB.Table_Prefix + daoObject[i].getTableName() + " SET ";
					try {
						String set = "";
						Class<?> c = Class.forName(daoObject[i].getClass().getPackage().getName() + "." + daoObject[i].getTableName());
						Method[] ms = c.getDeclaredMethods();
						for (Method m : ms) {
							String mn = m.getName();
							if (mn.startsWith("get")) {
								Object o = m.invoke(daoObject[i], new Object[] {});
								if (o != null) {
									if (o.getClass().equals(String.class)) {
										if (mn.substring(3).endsWith("_dt")) {
											try {
												String[] ds = o.toString().split("\\.");
												for (String s : ds)
													Logging.logLine(s);
												String d = ds[2] + "-" + ds[1] + "-" + ds[0];
												set += "`" + mn.substring(3, mn.indexOf("_dt")) + "` = '" + d + "', ";

											} catch (Exception e) {
												e.printStackTrace();
											}
										} else {
											set += "`" + mn.substring(3) + "` = '" + o.toString() + "', ";
										}

									} else if (o.getClass().equals(Integer.class) && ((Integer) o) != -1) {
										set += "`" + mn.substring(3) + "` = '" + o.toString() + "', ";
									} else if (o.getClass().equals(Long.class) && ((Long) o) != -1L) {
										set += "`" + mn.substring(3) + "` = '" + o.toString() + "', ";
									} else if (o.getClass().equals(Double.class) && ((Double) o) != -1.0D) {
										set += "`" + mn.substring(3) + "` = '" + o.toString() + "', ";
									}
								}
							}
						}
						if (set.length() > 0) {
							update += set.substring(0, set.length() - 2) + " WHERE " + daoObject[i].getIdName() + " = " + id;
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

	protected static String[] createInsert(DaoObject[] daoObject) {
		if (daoObject == null)
			return null;

		String[] inserts = new String[daoObject.length];
		for (int i = 0; i < inserts.length; i++) {
			if (daoObject[i].getId() > 0)
				continue;
			try {
				String insert = "INSERT INTO " + DB.Table_Prefix + daoObject[i].getTableName() + "(";
				Class<?> c = Class.forName(daoObject[i].getClass().getPackage().getName() + "." + daoObject[i].getTableName());

				Method[] ms = c.getDeclaredMethods();
				String values = "";
				String valueNames = "";
				if (daoObject[i].getReferenzIdNames() != null && daoObject[i].getReferenzIdNames().length > 0 && daoObject[i].getReferenzIds() != null && daoObject[i].getReferenzIds().length > 0) {
					for (int j = 0; j < daoObject[i].getReferenzIds().length; j++) {
						valueNames += "`" + daoObject[i].getReferenzIdNames()[j] + "`, ";
						values += "'" + daoObject[i].getReferenzIds()[j] + "', ";
					}
				}
				for (Method m : ms) {
					String mn = m.getName();
					if (mn.startsWith("get")) {
						Object obj = m.invoke(daoObject[i], new Object[] {});
						if (obj != null) {
							if (obj instanceof String) {
								if (mn.substring(3).endsWith("_dt")) {
									try {
										String[] ds = obj.toString().split("\\.");
										for (String s : ds)
											Logging.logLine(s);
										String d = ds[2] + "-" + ds[1] + "-" + ds[0];
										valueNames += "`" + mn.substring(3, mn.indexOf("_dt")) + "`, ";
										values += "'" + d + "', ";

									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									valueNames += "`" + mn.substring(3) + "`, ";
									values += "'" + obj.toString() + "', ";
								}
							} else if (obj instanceof Integer && ((Integer) obj) != -1) {
								valueNames += "`" + mn.substring(3) + "`, ";
								values += "'" + ((Integer) obj).intValue() + "', ";
							} else if (obj instanceof Double && ((Double) obj) != -1.0D) {
								valueNames += "`" + mn.substring(3) + "`, ";
								values += "'" + ((Double) obj).doubleValue() + "', ";
							} else if (obj instanceof Long && ((Long) obj) != -1L) {
								valueNames += "`" + mn.substring(3) + "`, ";
								values += "'" + ((Long) obj).longValue() + "', ";
							}
						}
					}
				}
				if (values.length() > 0 && valueNames.length() > 0) {
					values = values.substring(0, values.length() - 2);
					valueNames = valueNames.substring(0, valueNames.length() - 2);
					insert += valueNames + ") VALUES(" + values + ")";
					inserts[i] = insert;
				} else
					inserts[i] = null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				inserts[i] = null;

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				inserts[i] = null;

			} catch (IllegalAccessException e) {
				e.printStackTrace();
				inserts[i] = null;

			} catch (InvocationTargetException e) {
				e.printStackTrace();
				inserts[i] = null;

			}
		}

		return inserts;
	}

	protected static String createSelect(DaoObject daoObject, String columns) {
		String select = "SELECT " + (columns != null ? columns : "*") + " FROM " + DB.Table_Prefix + daoObject.getTableName();
		String where = "";

		try {
			Class<?> c = Class.forName(daoObject.getClass().getPackage().getName() + "." + daoObject.getTableName());
			Method[] ms = c.getDeclaredMethods();
			int id = daoObject.getId();
			if (id < 0) {
				for (Method m : ms) {
					String mn = m.getName();
					if (mn.startsWith("get")) {
						Object obj = m.invoke(daoObject, new Object[] {});
						if (obj != null) {
							if (obj instanceof Integer) {
								if (((Integer) obj).intValue() == -1)
									continue;
								where += "`" + mn.substring(3).toLowerCase() + "` = " + ((Integer) obj).intValue() + " AND ";
							} else if (obj instanceof Double) {
								if (((Double) obj).doubleValue() == -1.0D)
									continue;
								where += "`" + mn.substring(3).toLowerCase() + "` = " + ((Double) obj).doubleValue() + " AND ";
							} else if (obj instanceof String) {
								if (mn.substring(3).endsWith("_dt")) {
									where += "`" + mn.substring(3, mn.indexOf("_dt")).toLowerCase() + "` = '" + obj.toString() + "' AND ";
								} else
									where += "`" + mn.substring(3).toLowerCase() + "` like '" + obj.toString() + "' AND ";
							}
						}
					}
				}
				where += "`" + daoObject.getIdName() + "` > 0 AND ";
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
		if (daoObject.getReferenzIdNames() != null && daoObject.getReferenzIdNames().length > 0 && daoObject.getReferenzIds() != null && daoObject.getReferenzIds().length > 0) {
			for (int j = 0; j < daoObject.getReferenzIds().length; j++) {
				where += "`" + daoObject.getReferenzIdNames()[j] + "` = '" + daoObject.getReferenzIds()[j] + "' AND ";

			}
		}
		if (where.length() > 0) {
			where = " WHERE " + where.substring(0, where.length() - 4);
		}
		return select + where;
	}

	protected static String[] createDelete(DaoObject[] daoObject) {
		if (daoObject == null)
			return null;
		String[] deletes = new String[daoObject.length];
		for (int i = 0; i < deletes.length; i++) {
			if (daoObject[i] != null) {
				int id = daoObject[i].getId();
				if (id > 0) {
					deletes[i] = "DELETE  FROM " + DB.Table_Prefix + daoObject[i].getTableName() + " WHERE " + daoObject[i].getIdName() + " = " + id;
				} else
					deletes[i] = null;
			} else
				deletes[i] = null;
		}
		return deletes;
	}

}
