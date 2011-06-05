package de.axisbank.tools;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

public class KonfigFiles {

	private static final String fileType = "_Konfiguration.properties";
	private static final String konfigPath = "/de/axisbank/konf/";

	public static final String DB_USER = "DB_USER";
	public static final String DB_PASSWORD = "DB_PASSWORD";
	public static final String DB_HOST = "DB_HOST";
	public static final String DB_NAME = "DB_NAME";
	public static final String DB_PORT = "DB_PORT";
	public static final String DB_TABLEPREFIX = "DB_TABLEPREFIX";

	public static final String Kalkulation_MIN_LAUFZEIT = "Kalkulation_MIN_LAUFZEIT";
	public static final String Kalkulation_MAX_LAUFZEIT = "Kalkulation_MAX_LAUFZEIT";
	public static final String Kalkulation_MIN_KREDIT = "Kalkulation_MIN_KREDIT";
	public static final String Kalkulation_MAX_KREDIT = "Kalkulation_MAX_KREDIT";
	public static final String Kalkulation_ZINSSATZ = "Kalkulation_ZINSSATZ";
	public static final String Kalkulation_MAX_ZINSSATZDIF = "Kalkulation_MAX_ZINSSATZDIF";
	public static final String Kalkulation_MIN_ZINSSATZDIF = "Kalkulation_MIN_ZINSSATZDIF";

	public static final String Logging_Aktiv = "Logging_Aktiv";
	public static final String Logging_Classes = "Logging_Classes";

	public static HashMap<String, Object> props = new HashMap<String, Object>();

	public static String getString(String key) {
		String file = key.substring(0, key.indexOf("_"));
		key = key.substring(key.indexOf("_") + 1);
		if (props.containsKey(key))
			return props.get(key).toString();
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(file));
			String prop = p.getProperty(key);
			if (prop != null)
				props.put(key, prop);
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getInt(String key) {
		String file = key.substring(0, key.indexOf("_"));
		key = key.substring(key.indexOf("_") + 1);
		if (props.containsKey(key))
			return Integer.parseInt(props.get(key).toString());
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(file));
			String prop = p.getProperty(key);
			if (prop != null)
				props.put(key, prop);
			return Integer.parseInt(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static double getDouble(String key) {
		String file = key.substring(0, key.indexOf("_"));
		key = key.substring(key.indexOf("_") + 1);
		if (props.containsKey(key))
			return Double.parseDouble(props.get(key).toString());
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(file));
			String prop = p.getProperty(key);
			if (prop != null)
				props.put(key, prop);
			return Double.parseDouble(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean getBoolean(String key) {
		String file = key.substring(0, key.indexOf("_"));
		key = key.substring(key.indexOf("_") + 1);
		if (props.containsKey(key))
			return Boolean.parseBoolean(props.get(key).toString());
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(file));
			String prop = p.getProperty(key);
			if (prop != null)
				props.put(key, prop);
			return Boolean.parseBoolean(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean getLogging(String className) {
		String file = "Logging";
		if (props.containsKey(className))
			return Boolean.parseBoolean(props.get(className).toString());
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(file));
			String prop = p.getProperty(className, "true");
			if (prop != null)
				props.put(className, prop);
			return Boolean.parseBoolean(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public InputStreamReader readFile(String konfigFile) {
		try {
			return new InputStreamReader(this.getClass().getResourceAsStream(konfigPath + konfigFile + fileType));
		} catch (Exception e) {
			return null;
		}
	}
}
