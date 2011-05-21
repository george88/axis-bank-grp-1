package de.axisbank.tools;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.text.DateFormatter;

public class DatumsKonvertierung {

	public static Date getDatumFromString(String datum) {
		String[] ds = datum.split("\\.");

		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(ds[2]), Integer.parseInt(ds[1]), Integer.parseInt(ds[0]));

		return c.getTime();
	}

	public static String getStringFromDatum(Date datum) {
		Calendar c = Calendar.getInstance();
		c.setTime(datum);

		return c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "" + c.get(Calendar.YEAR);
	}

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

	public static String getNaechstenMonatsAnfang(String datum) {
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
		if (monat > 0) {
			monat = monat < 12 ? ++monat : 1;
			return "01." + (monat < 10 ? "0" + monat : monat) + datum.substring(5);
		}
		return datum;
	}
}
