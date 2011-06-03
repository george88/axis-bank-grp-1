package de.axisbank.tools;

public class Logging {

	public static void log(Object obj) {
		if (KonfigFiles.getBoolean(KonfigFiles.Logging_Aktiv) && KonfigFiles.getLogging(Thread.currentThread().getStackTrace()[2].getClassName()))
			System.out.print(obj);
	}

	public static void logLine(Object obj) {
		if (KonfigFiles.getBoolean(KonfigFiles.Logging_Aktiv) && KonfigFiles.getLogging(Thread.currentThread().getStackTrace()[2].getClassName()))
			System.out.println("[ Class:" + Thread.currentThread().getStackTrace()[2].getFileName().substring(0, Thread.currentThread().getStackTrace()[2].getFileName().indexOf(".")) + " ][ Line:"
					+ Thread.currentThread().getStackTrace()[2].getLineNumber() + " ]\t" + obj);
	}
}
