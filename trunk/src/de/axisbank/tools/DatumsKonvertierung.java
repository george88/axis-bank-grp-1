package de.axisbank.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Die DatumsKonvertierung dient als Hilfe zur Umwandlung und von Datumsformaten.
 * @author Georg Neufeld
 *
 */
public class DatumsKonvertierung {

	/**
	 * Kovertiert ein Datum als String aus diesem Fomat "tt.mm.yyyy" in ein <code>java.util.Date</code>
	 * @param datum
	 * @return Date
	 */
	public static Date getDatumFromString(String datum) {
		String[] ds = datum.split("\\.");

		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(ds[2]), Integer.parseInt(ds[1]), Integer.parseInt(ds[0]));

		return c.getTime();
	}

	/**
	 * Kovertiert ein <code>java.util.Date</code> - Datum in einen String mit diesem Fomat "tt.mm.yyyy" 
	 * @param datum
	 * @return String
	 */
	public static String getStringFromDatum(Date datum) {
		Calendar c = Calendar.getInstance();
		c.setTime(datum);

		return c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "" + c.get(Calendar.YEAR);
	}

	/**
	 * Sucht das letzte Datum des übergebenen Monats
	 * Berücksichtigt dabei Schaltjahre
	 * @param datum
	 * @return String
	 */
	public static String getMonatsEnde(String datum) {
		int monat = -1;
		try {
			monat = Integer.parseInt(datum.substring(3, 5));
		} catch (Exception e) {
			try {
				monat = Integer.parseInt(datum.substring(4, 5));
			} catch (Exception e2) {
				monat = -1;
			}
		}
		String tag = "30";
		switch (monat) {
		case 1:
			tag = "31";
			break;
		case 2:
			if (new GregorianCalendar().isLeapYear(Integer.parseInt(datum.substring(6))))
				tag = "29";
			else
				tag = "28";
			break;
		case 3:
			tag = "31";
			break;
		case 4:
			tag = "30";
			break;
		case 5:
			tag = "31";
			break;
		case 6:
			tag = "30";
			break;
		case 7:
			tag = "31";
			break;
		case 8:
			tag = "31";
			break;
		case 9:
			tag = "30";
			break;
		case 10:
			tag = "31";
			break;
		case 11:
			tag = "30";
			break;
		case 12:
			tag = "31";
			break;
		}
		return tag + datum.substring(2);
	}

	/**
	 * Findet das erste Datum des Folgemonats
	 * @param datum
	 * @return String
	 */
	public static String getNaechstenMonatsAnfang(String datum) {
		int monat = -1;
		int jahr = -1;
		try {
			monat = Integer.parseInt(datum.substring(3, 5));

		} catch (Exception e) {
			try {
				monat = Integer.parseInt(datum.substring(4, 5));
			} catch (Exception e2) {
				monat = -1;
			}
		}
		try {
			jahr = Integer.parseInt(datum.substring(6));
		} catch (Exception e) {
		}

		if (monat > 0 && jahr > 0) {
			if (monat < 12)
				monat++;
			else {
				monat = 1;
				jahr++;
			}
			return "01." + (monat < 10 ? "0" + monat : monat) + "." + jahr;
		}
		return datum;
	}

	/**
	 * Wandelt das datum aus dem Datenbankdatumsformat in folgendendes String-Format um "tt.mm.yyyy"
	 * @param dbDate
	 * @return String
	 */
	public static String getStringFromDbDate(java.sql.Date dbDate) {
		String d = dbDate.toString();
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dbDate);
			d = (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : c.get(Calendar.DAY_OF_MONTH)) + "."
					+ (c.get(Calendar.MONTH) < 10 ? "0" + c.get(Calendar.MONTH) : c.get(Calendar.MONTH)) + "." + c.get(Calendar.YEAR);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * Wandelt ein Datums-String des Formates "tt.mm.yyyy" in einen String, der für einen Datums-Datenbankeintrag konform ist, um ("tt-mm-yyyy")
	 * @param date
	 * @return String
	 */
	public static String getStringToDbStringDate(String date) {
		String d = date;
		try {
			String[] ds = date.split("\\.");
			d = ds[2] + "-" + ds[1] + "-" + ds[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
}
