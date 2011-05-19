package de.axisbank.tools;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

public class KonfigFiles {

	public static final String DB_Konfiguration = "DB_Konfiguration";
	private static final String fileType = ".properties";
	private static final String konfigPath = "/konf/";
	public static final String DBUSER = "DBUSER";
	public static final String DBPASSWORD = "DBPASSWORD";
	public static HashMap<String, String> props = new HashMap<String, String>();

	public static String get(String key, String konfigFile) {
		if (props.containsKey(key))
			return props.get(key);
		try {
			Properties p = new Properties();
			p.load(new KonfigFiles().readFile(konfigFile));
			String prop = p.getProperty(key);
			if (prop != null)
				props.put(key, prop);
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public InputStreamReader readFile(String konfigFile) {
		return new InputStreamReader(this.getClass().getResourceAsStream(
				konfigPath + konfigFile + fileType));
	}
}
