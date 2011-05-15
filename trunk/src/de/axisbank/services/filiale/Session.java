package de.axisbank.services.filiale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Session implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4034449263354130980L;
	private String username;
	private Long sessionID;
	private final int delay = 30 * 60 * 1000;
	private final Timer timer;

	public Session(String username, Long sessionID) {
		setUsername(username);
		setSessionID(sessionID);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void updateTimer() {
		timer.restart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SessionManagement.deleteSession(getSessionID());
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}

	public Long getSessionID() {
		return sessionID;
	}
}
