package de.axisbank.services.filiale;

import java.util.HashMap;
import java.util.Random;

public class SessionManagement {

	private static HashMap<Long, Session> sessions = new HashMap<Long, Session>();
	private static Random random = new Random();

	public static Long addSession(String username) {
		Long sessionID = random.nextLong();
		sessions.put(sessionID, new Session(username, sessionID));
		return sessionID;
	}

	public static void updateSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			sessions.get(sessionID).updateTimer();
		}
	}

	public static String checkSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			return sessions.get(sessionID).getUsername();
		}
		return null;
	}

	public static void deleteSession(Long sessionID) {
		if (sessions.get(sessionID) != null) {
			sessions.remove(sessionID);
		}
	}
}
