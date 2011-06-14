package de.axisbank.services.filiale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import de.axisbank.tools.Logging;

/**
 * Diese Klasse stellt eine Session das. Das heißt, dass eine Instanz dieser Klasse ein gültige Session dargestellt.
 * Diese Instanz befindet sich dann im Handling des Sessionmanagement. 
 * @author Georg Neufeld
 *
 */
public class Session implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4034449263354130980L;
	private String benutzername;
	private Long sessionID;
	private final int INITIAL_DELAY = 30 * 60;
	private int delay;
	private final Timer timer;

	public Session(String benutzername, Long sessionID) {
		setBenutzername(benutzername);
		setSessionID(sessionID);
		delay = INITIAL_DELAY;
		timer = new Timer(1000, this);
		timer.start();
	}

	public void updateTimer() {
		Logging.logLine("Es wären noch " + delay + " Sekunden zum SessionDelete");
		delay = INITIAL_DELAY;
	}

	public int getDelayTime() {
		return delay;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		delay--;
		if (delay <= 0) {
			Logging.logLine("Die Session mit der ID " + getSessionID() + " wird gelöscht!");
			timer.stop();
			SessionManagement.deleteSession(getSessionID());
		}
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}

	public Long getSessionID() {
		return sessionID;
	}
}
