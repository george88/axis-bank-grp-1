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

	/************************** Variablen *************************************/

	private static final long serialVersionUID = -4034449263354130980L;

	/**
	 * Der Benutzername, der mit der Session angemeldet ist
	 */
	private String benutzername;

	/**
	 * die Sessionidentifikation
	 */
	private Long sessionID;

	/**
	 * Legt die Länge einer Session in Sekunden fest
	 */
	private final int INITIAL_DELAY = 30 * 60;

	/**
	 * Enthält den aktuellen Zeitstempel einer Session 
	 */
	private int delay;

	/**
	 * Dient zur Reduzierung der Sessionzeit
	 */
	private final Timer timer;

	/************************** Konstruktor *************************************/

	/**
	 * Konstruktor zu Erstellung einer Session
	 * @param benutzername
	 * @param sessionID
	 */
	public Session(String benutzername, Long sessionID) {
		setBenutzername(benutzername);
		setSessionID(sessionID);
		delay = INITIAL_DELAY;
		timer = new Timer(1000, this);
		timer.start();
	}

	/**
	 * setzt den Ablaufwert wieder zurück
	 */
	public void updateTimer() {
		Logging.logLine("Es wären noch " + delay + " Sekunden zum SessionDelete");
		delay = INITIAL_DELAY;
	}

	/**
	 * Gibt die aktuelle Erhaltszeit der Session zurück
	 * @return int
	 */
	public int getDelayTime() {
		return delay;
	}

	/**
	 * zählt den Wert des Sessionerhalt sekündlich herunter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		delay--;
		if (delay <= 0) {
			Logging.logLine("Die Session mit der ID " + getSessionID() + " wird gelöscht!");
			timer.stop();
			SessionManagement.deleteSession(getSessionID());
		}
	}

	/************************* Getter/Setter ************************************/

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
