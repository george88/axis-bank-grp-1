package de.axisbank.services.filiale;

import java.util.HashMap;
import java.util.Random;

import de.axisbank.tools.Logging;

public class SessionManagement {

	private static HashMap<Long, Session> sessions = new HashMap<Long, Session>();
	private static Random random = new Random();
	private static Object syncObj = new Object();

	public static HashMap<Long, Session> getSessions() {
		synchronized (syncObj) {
			return sessions;
		}
	}

	public static Long addSession(String benutzername) {
		//		synchronized (syncObj) {
		Long sessionID = -1L;
		sessionID = random.nextLong();
		while (checkSession(sessionID) != null) {
			sessionID = random.nextLong();
		}
		sessions.put(sessionID, new Session(benutzername, sessionID));
		Logging.logLine("Session " + sessionID + " vom Benutzer " + benutzername + " wurde erstellt");
		return sessionID;
		//		}
	}

	public static void updateSession(Long sessionID) {
		synchronized (syncObj) {

			if (sessions.get(sessionID) != null) {
				Logging.logLine("Session " + sessionID + " wird verlängert");
				sessions.get(sessionID).updateTimer();
			}
		}
	}

	public static String checkSession(Long sessionID) {
		synchronized (syncObj) {
			if (sessions.get(sessionID) != null) {
				Logging.logLine("Noch " + sessions.get(sessionID).getDelayTime() + " Sekunden zum SessionDelete");
				return sessions.get(sessionID).getBenutzername();
			}
		}
		Logging.logLine("Session " + sessionID + " ungültig");

		return null;
	}

	public static Long checkSession(String benutzername) {
		synchronized (syncObj) {
			for (Long s : sessions.keySet())
				if (sessions.get(s).getBenutzername().equals(benutzername))
					return s;
		}

		return -1L;
	}

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
