package de.axisbank.services.filiale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Tilgungsplan;
import de.axisbank.daos.User;
import de.axisbank.datenbank.DB;

public class FilialBankService {

	public final static int Liqui_1 = 1;
	public final static int Liqui_2 = 2;
	public final static int Liqui_3 = 3;
	public final static int Liqui_4 = 4;
	public final static int Liqui_5 = 5;
	private final int logoffTimeInSeconds = 720;
	private int currentTime;
	private boolean login;
	private Timer timer;

	public FilialBankService() {

	}

	public boolean login(String benutzername, String passwort) {

		User tmpUser = new User();
		tmpUser.setBenutzername(benutzername);
		tmpUser.setPasswort(passwort);
		if (DB.select(tmpUser) != null)
			if (((User[]) DB.select(tmpUser)).length > 0)
				if (((User[]) DB.select(tmpUser))[0] != null)
					setLogin(((User[]) DB.select(tmpUser))[0].getBenutzername()
							.equals(benutzername));
		return isLogin();
	}

	public void logoff() {
		login = false;
	}

	private void continueLogoffCounter() {
		// if (isLogin()) {
		// if (timer != null) {
		// currentTime = logoffTimeInSeconds;
		// timer = new Timer(1000, new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// currentTime--;
		// if (currentTime < 1) {
		// logoff();
		// }
		// }
		// });
		// }
		// currentTime = logoffTimeInSeconds;
		// }
	}

	public int getLiquiditaet() {
		if (!isLogin())
			return -1;
		continueLogoffCounter();

		return Liqui_1;
	}

	public Tilgungsplan getRueckzahlungsPlan() {
		if (!isLogin())
			return null;
		continueLogoffCounter();

		return new Tilgungsplan();
	}

	public Antragssteller[] getAntragsteller(String vorname, String nachname) {
		if (!isLogin())
			return null;
		Antragssteller as = new Antragssteller();
		as.setVorname(vorname);
		as.setNachname(nachname);
		return (Antragssteller[]) DB.select(as);
	}

	public int[] updateAntragsteller(DaoObject[] daoObject) {
		return DB.update(daoObject);
	}

	private void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isLogin() {
		return login;
	}

}
