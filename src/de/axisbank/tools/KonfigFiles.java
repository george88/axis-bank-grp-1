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

	public static HashMap<String, String> props = new HashMap<String, String>();

	public static String getString(String key) {
		String file = key.substring(0, key.indexOf("_"));
		key = key.substring(key.indexOf("_") + 1);
		if (props.containsKey(key))
			return props.get(key);
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
			return Integer.parseInt(props.get(key));
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
			return Double.parseDouble(props.get(key));
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

	public InputStreamReader readFile(String konfigFile) {
		return new InputStreamReader(this.getClass().getResourceAsStream(konfigPath + konfigFile + fileType));
	}
}
