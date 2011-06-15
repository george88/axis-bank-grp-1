package de.axisbank.services.filiale;

import java.util.HashMap;
import java.util.Random;

import de.axisbank.tools.Logging;

/**
 * Diese Klasse h�ndelt die Sessions und bietet Methoden an, die f�r ein geregeltes Sessionmanagement von N�ten sind. 
 * @author Georg Neufeld
 *
 */
public class SessionManagement {

	/************************** Variablen *************************************/

	/**
	 * enth�lt alle Sessions
	 */
	private static HashMap<Long, Session> sessions = new HashMap<Long, Session>();

	/**
	 * Dient der Erstellung einer Sessionidentifikation
	 */
	private static Random random = new Random();

	/**
	 * Dient zur Synchronisierung s�mtlicher Methoden im Sessionmanagement 
	 */
	private static Object syncObj = new Object();

	/**
	 * Gibt alle Sessions zur�ck
	 * @return HashMap<Long, Session>
	 */
	public static HashMap<Long, Session> getSessions() {
		synchronized (syncObj) {
			return sessions;
		}
	}

	/**
	 * Erstellt eine Session und gibt die Identifikation dieser zur�ck
	 * @param benutzername
	 * @return Long
	 */
	public static Long addSession(String benutzername) {
		synchronized (syncObj) {
			Long sessionID = -1L;
			sessionID = random.nextLong();
			while (checkSession(sessionID) != null) {
				sessionID = random.nextLong();
			}
			sessions.put(sessionID, new Session(benutzername, sessionID));
			Logging.logLine("Session " + sessionID + " vom Benutzer " + benutzername + " wurde erstellt");
			return sessionID;
		}
	}

	/**
	 * Setzt den Erhaltswert der Session zur�ck
	 * @param sessionID
	 */
	public static void updateSession(Long sessionID) {
		synchronized (syncObj) {

			if (sessions.get(sessionID) != null) {
				Logging.logLine("Session " + sessionID + " wird verl�ngert");
				sessions.get(sessionID).updateTimer();
			}
		}
	}

	/**
	 * Pr�ft auf Vorhandensein einer Session anhand der Identifikation, bei Erfolg R�ckgabe des Benutzernames
	 * @param sessionID
	 * @return String
	 */
	public static String checkSession(Long sessionID) {
		synchronized (syncObj) {
			if (sessions.get(sessionID) != null) {
				Logging.logLine("Noch " + sessions.get(sessionID).getDelayTime() + " Sekunden zum SessionDelete");
				return sessions.get(sessionID).getBenutzername();
			}
		}
		Logging.logLine("Session " + sessionID + " ung�ltig");

		return null;
	}

	/**
	 * Pr�ft auf Vorhandensein einer Session anhand des Benutzernames, bei Erfolg R�ckgabe der Sessionidentifikation
	 * @param benutzername
	 * @return
	 */
	public static Long checkSession(String benutzername) {
		synchronized (syncObj) {
			for (Long s : sessions.keySet())
				if (sessions.get(s).getBenutzername().equals(benutzername))
					return s;
		}

		return -1L;
	}

	/**
	 * L�scht die Session
	 * @param sessionID
	 */
	public static void deleteSession(Long sessionID) {
		synchronized (syncObj) {
			if (sessions.get(sessionID) != null) {
				sessions.remove(sessionID);
				Logging.log("Jetzt sollte sie geloescht sein:");
				Logging.logLine(sessions.get(sessionID) == null);
			}
		}
	}
}
