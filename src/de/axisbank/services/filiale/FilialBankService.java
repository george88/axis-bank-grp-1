package de.axisbank.services.filiale;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.Arbeitgeber;
import de.axisbank.daos.Ausgaben;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Einnahmen;
import de.axisbank.daos.Kreditantrag;
import de.axisbank.daos.Kreditverbindlichkeiten;
import de.axisbank.daos.User;
import de.axisbank.datenbank.DB;
import de.axisbank.services.Tilgungsplan;

public class FilialBankService {

	private boolean login;
	private String benutzername;

	public FilialBankService() {

	}

	public boolean login(String benutzername, String passwort) {

		User tmpUser = new User();
		tmpUser.setBenutzername(benutzername);
		tmpUser.setPasswort(passwort);
		User[] users = (User[]) DB.select(tmpUser);
		if (users != null)
			if (users.length == 1)
				if (((User[]) DB.select(tmpUser))[0] != null) {
					User user = users[0];
					setLogin(user.getBenutzername().equals(benutzername));
					setBenutzername(benutzername);
					User updateuser = new User();
					updateuser.setId(user.getId());
					updateuser.setStatus(1);
					updateuser.setLetzterLogin(System.currentTimeMillis());
					DB.update(new DaoObject[] { updateuser });
				}
		return isLogin();
	}

	public boolean logoff() {
		setLogin(false);
		User updateuser = new User();
		updateuser.setBenutzername(getBenutzername());
		updateuser.setStatus(1);
		if (((User[]) DB.select(updateuser)).length > 0) {
			updateuser = ((User[]) DB.select(updateuser))[0];
			updateuser.setId(updateuser.getId());
			updateuser.setBenutzername(null);
			updateuser.setStatus(0);
			updateuser.setPasswort(null);
			updateuser.setLetzterLogin(-1L);
			int[] e = DB.update(new DaoObject[] { updateuser });
			return e[0] == 1;
		}
		return false;

	}

	public int getLiquiditaet(Antragssteller antragsteller) {
		if (!isLogin())
			return -1;

		return 0;
	}

	public Tilgungsplan getTilgungsPlan(double kreditHoehe, double ratenHoehe) {
		if (!isLogin())
			return null;

		return new Tilgungsplan();
	}

	public Tilgungsplan getTilgungsPlan(double kreditHoehe, int laufzeitMonate) {
		if (!isLogin())
			return null;

		return new Tilgungsplan();
	}

	public Antragssteller[] getAntragssteller(String vorname, String nachname,
			String gebDatum) {
		if (!isLogin())
			return null;
		Antragssteller as = new Antragssteller();
		as.setVorname(vorname);
		as.setNachname(nachname);
		as.setGebDatum(gebDatum);
		Antragssteller[] asss = (Antragssteller[]) DB.select(as);
		for (Antragssteller ass : asss) {
			ass.setAusgaben((Ausgaben[]) DB.select(new Ausgaben(ass.getId())));
			ass.setArbeitgeber((Arbeitgeber[]) DB.select(new Arbeitgeber(ass
					.getId())));
			ass.setEinnahmen((Einnahmen[]) DB.select(new Einnahmen(ass.getId())));
			ass.setKreditantraege((Kreditantrag[]) DB.select(new Kreditantrag(
					ass.getId())));
			for (Kreditantrag ka : ass.getKreditantraege()) {
				try {
					Antragssteller a = new Antragssteller();
					a.setId(ka.getIdAntragssteller_2());
					ka.setAntragssteller_2(((Antragssteller[]) DB.select(a))[0]);
				} catch (Exception e) {
					ka.setAntragssteller_2(null);
				}
				try {
					User u = new User();
					System.out.println("userID" + ka.getidUser());
					u.setId(ka.getidUser());
					ka.setBerater(((User[]) DB.select(u))[0]);
				} catch (Exception e) {
					ka.setBerater(null);
				}
			}
			ass.setKreditverbindlichkeiten((Kreditverbindlichkeiten[]) DB
					.select(new Kreditverbindlichkeiten(ass.getId())));
		}

		return asss;
	}

	public int[] updateAntragssteller(Antragssteller[] antragsssteller) {
		return DB.update(antragsssteller);
	}

	private void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isLogin() {
		return login;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getBenutzername() {
		return benutzername;
	}

}
