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
	private final int INITIAL_DELAY = 30 * 60;
	private int delay;
	private final Timer timer;

	public Session(String username, Long sessionID) {
		setUsername(username);
		setSessionID(sessionID);
		delay = INITIAL_DELAY;
		timer = new Timer(1000, this);
		timer.start();
	}

	public void updateTimer() {
		System.out.println("Es wären noch " + delay
				+ " Sekunden zum SessionDelete");
		delay = INITIAL_DELAY;
	}

	public int getDelayTime() {
		return delay;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		delay--;
		if (delay <= 0) {
			System.out.println("Die Session mit der ID " + getSessionID()
					+ " wird gelöscht!");
			timer.stop();
			SessionManagement.deleteSession(getSessionID());
		}
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
