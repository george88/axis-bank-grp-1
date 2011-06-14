package de.axisbank.datenbank;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import de.axisbank.daos.DaoObject;
import de.axisbank.tools.DatumsKonvertierung;
import de.axisbank.tools.Logging;

/**
 * Diese Klasse dient zur Erstellung von Querys für die Datenbankabfrage.
 * Sie nutzt die Technik der Reflexion mit der, anhand der Klassennamen und der Namen der Get- und Set-Methoden, die Datenbankquerys erstellt werden. 
 * @author Georg Neufeld
 *
 */
class MySqlQueryFactory {

	/**
	 * Es wird ein Updatequery erstellt.
	 * Dabei werden alle Get-Methoden aus der DAO-Klasse abgefragt.
	 * Welche Get-Methoden dann im Falle einer String-Rückgabe keine null zurückgeben, werden im Query berücksichtigt.
	 * Im Falle einer Double- oder Integer-Rückgabe wird Rücksicht auf den Wert genommen, wenn diser nicht -1 bzw. -1.0 ist.s 
	 * Eine Bedingung für die Erstellung eines Update ist, dass die id des DAO-Objektes gesetzt ist um einen eindeutigen Eintrag zu erhalten.
	 * @param daoObject
	 * @return String[]
	 */
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
												set += "`" + mn.substring(3, mn.indexOf("_dt")) + "` = '" + DatumsKonvertierung.getStringToDbSttringDate(o.toString()) + "', ";

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

	/**
	 * Es wird bei der Erstellung des Insert genauso vorgegangen wie bei der Erstellung eines Updates mit Ausnahme der eindeutigen Id, die hierbei nicht benötigt wird. 
	 * @param daoObject
	 * @return String[]
	 */
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

	/**
	 * Analog zu der createUpdate-Methode.
	 * @param daoObject
	 * @param columns
	 * @return String
	 */
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

	/**
	 * Es wird ein einfacher Deletequery gestrickt, anhand der id im Objekt. 
	 * @param daoObject
	 * @return String[]
	 */
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