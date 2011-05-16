package de.axisbank.services.filiale;

import java.util.HashMap;
import java.util.Random;

public class SessionManagement {

	private static HashMap<Long, Session> sessions = new HashMap<Long, Session>();
	private static Random random = new Random();

	public static Long addSession(String username) {
		Long sessionID = -1L;
		sessionID = random.nextLong();
		while (checkSession(sessionID) != null) {
			sessionID = random.nextLong();
		}

		sessions.put(sessionID, new Session(username, sessionID));
		System.out.println("Session " + sessionID + " vom Benutzer " + username
				+ " wurde erstellt");
		return sessionID;
	}

	public static void updateSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			System.out.println("Session " + sessionID + " wird verlängert");
			sessions.get(sessionID).updateTimer();
		}
	}

	public static String checkSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			System.out.println("Noch " + sessions.get(sessionID).getDelayTime()
					+ " Sekunden zum SessionDelete");
			return sessions.get(sessionID).getUsername();
		}
		System.out.println("Session " + sessionID + " ungültig");

		return null;
	}

	public static void deleteSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			sessions.remove(sessionID);
			System.out.print("Jetzt sollte sie geloescht sein:");
			System.out.println(sessions.get(sessionID) == null);
		}
	}
}
