package de.axisbank.services.filiale;

import org.apache.log4j.Logger;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.Arbeitgeber;
import de.axisbank.daos.Ausgaben;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Einnahmen;
import de.axisbank.daos.Kreditantrag;
import de.axisbank.daos.Kreditverbindlichkeiten;
import de.axisbank.daos.User;
import de.axisbank.daos.Versicherungen;
import de.axisbank.datenbank.DB;
import de.axisbank.services.Tilgungsplan;

public class FilialBankService {

	public FilialBankService() {
	}

	public Long login(String benutzername, String passwort) {
		User tmpUser = new User();
		tmpUser.setBenutzername(benutzername);
		tmpUser.setPasswort(passwort);
		User[] users = (User[]) DB.select(tmpUser);
		Long sessionID = -1L;
		if (users != null)
			if (users.length == 1)
				if ((users)[0] != null) {
					User user = users[0];
					User updateuser = new User();
					updateuser.setId(user.getId());
					updateuser.setStatus(1);
					updateuser.setLetzterLogin(System.currentTimeMillis());
					DB.update(new DaoObject[] { updateuser });
					if (benutzername.equals(user.getBenutzername()))
						sessionID = SessionManagement.addSession(benutzername);
				}
		String checkUser = SessionManagement.checkSession(sessionID);
		return checkUser != null && checkUser.equals(benutzername) ? sessionID
				: -1;
	}

	public boolean logoff(Long sessionID) {
		User updateuser = new User();
		updateuser.setBenutzername(SessionManagement.checkSession(sessionID));
		updateuser.setStatus(1);
		SessionManagement.deleteSession(sessionID);
		Object userObjs = DB.select(updateuser);
		if (userObjs != null)
			if (((User[]) userObjs).length > 0) {
				updateuser = ((User[]) userObjs)[0];
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

	public int getLiquiditaet(Antragssteller antragsteller, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return -1;

		SessionManagement.updateSession(sessionID);

		return 0;
	}

	public Tilgungsplan getTilgungsPlan(double kreditHoehe, double ratenHoehe,
			Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return null;

		SessionManagement.updateSession(sessionID);

		return new Tilgungsplan();
	}

	public Tilgungsplan getTilgungsPlan(double kreditHoehe, int laufzeitMonate,
			Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return null;

		SessionManagement.updateSession(sessionID);

		return new Tilgungsplan();
	}

	public Antragssteller[] getAntragssteller(String vorname, String nachname,
			String gebDatum, int hauptGirokonto, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null) {
			return null;
		}

		Antragssteller as = new Antragssteller();
		as.setVorname(vorname);
		as.setNachname(nachname);
		as.setGebDatum_dt(gebDatum);
		as.setHauptGirokonto(hauptGirokonto);
		Antragssteller[] asss = (Antragssteller[]) DB.select(as);
		if (asss != null)
			for (Antragssteller ass : asss) {
				ass.setAusgaben((Ausgaben[]) DB.select(new Ausgaben(ass.getId())));
				ass.setArbeitgeber((Arbeitgeber[]) DB.select(new Arbeitgeber(
						ass.getId())));
				ass.setEinnahmen((Einnahmen[]) DB.select(new Einnahmen(ass
						.getId())));
				ass.setKreditantraege((Kreditantrag[]) DB
						.select(new Kreditantrag(ass.getId())));
				ass.setVersicherungen((Versicherungen[]) DB
						.select(new Versicherungen(ass.getId())));
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
		SessionManagement.updateSession(sessionID);
		return asss;
	}

	public int[] updateAntragssteller(Antragssteller[] antragsssteller,
			Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return null;

		int[] updates = DB.update(antragsssteller);
		for (Antragssteller ass : antragsssteller) {
			DB.update(ass.getAusgaben());
			DB.update(ass.getArbeitgeber());
			DB.update(ass.getEinnahmen());
			DB.update(ass.getKreditantraege());
			DB.update(ass.getVersicherungen());
			DB.update(ass.getKreditverbindlichkeiten());
			for (Kreditantrag ka : ass.getKreditantraege()) {
				DB.update(new DaoObject[] { ka.getAntragssteller_2() });
				DB.update(new DaoObject[] { ka.getBerater() });
			}
		}

		SessionManagement.updateSession(sessionID);

		return updates;
	}
}
