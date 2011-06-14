package de.axisbank.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Diese Klasse bietet eine schnelle Möglichkeit geordneter Logginginformationen in der Konsole darzustellen.
 * Sie prüft vor Ausgabe des Loggings, ob das Logging für diese Klasse aktiviert ist aktiviert ist.
 * @author Georg Neufeld
 *
 */
public class Logging {

	public static void log(Object obj) {
		if (KonfigFiles.getBoolean(KonfigFiles.Logging_Aktiv) && KonfigFiles.getLogging(Thread.currentThread().getStackTrace()[2].getClassName()))
			System.out.print(obj);
	}

	public static void logLine(Object obj) {
		if (KonfigFiles.getBoolean(KonfigFiles.Logging_Aktiv) && KonfigFiles.getLogging(Thread.currentThread().getStackTrace()[2].getClassName()))
			System.out.println("[ Class:" + Thread.currentThread().getStackTrace()[2].getFileName().substring(0, Thread.currentThread().getStackTrace()[2].getFileName().indexOf(".")) + " ][ Line:"
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + " ]\n\t" + obj);
	}

	public static void logObjectDetail(Object obj) {
		if (obj != null) {
			if (KonfigFiles.getBoolean(KonfigFiles.Logging_Aktiv) && KonfigFiles.getLogging(Thread.currentThread().getStackTrace()[2].getClassName())) {
				Method[] methods = obj.getClass().getMethods();
				System.out.println("[ Class:" + Thread.currentThread().getStackTrace()[2].getFileName().substring(0, Thread.currentThread().getStackTrace()[2].getFileName().indexOf("."))
						+ " ][ Line:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + " ]\t");
				System.out.println("\tObjectdetails von:" + obj.getClass().getName());
				for (Method m : methods) {
					if (m.getName().startsWith("get"))
						try {
							System.out.println("\t" + m.getName() + ":\t\t" + m.invoke(obj, new Object[] {}));
						} catch (IllegalArgumentException e) {
							//							e.printStackTrace();
						} catch (IllegalAccessException e) {
							//							e.printStackTrace();
						} catch (InvocationTargetException e) {
							//							e.printStackTrace();
						}
				}
				methods = obj.getClass().getDeclaredMethods();
				System.out.println();
				for (Method m : methods) {
					if (m.getName().startsWith("get"))
						try {
							System.out.println("\t" + m.getName() + ":\t\t" + m.invoke(obj, new Object[] {}));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
				}
				System.out.println("Fields:");
				for (Field f : obj.getClass().getDeclaredFields()) {
					try {
						f.setAccessible(true);
						System.out.println("\t" + f.getName() + " = " + f.get(obj));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("[ Class:" + Thread.currentThread().getStackTrace()[2].getFileName().substring(0, Thread.currentThread().getStackTrace()[2].getFileName().indexOf(".")) + " ][ Line:"
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + " ]\t");
			System.out.println("\tObjectdetails von:" + obj);
		}
	}
}
