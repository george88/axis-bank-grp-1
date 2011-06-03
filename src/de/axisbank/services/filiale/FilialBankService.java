package de.axisbank.services.filiale;

import java.util.Random;
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
import de.axisbank.tools.KonfigFiles;
import de.axisbank.tools.TilgungsPlanErsteller;

public class FilialBankService {

	public FilialBankService() {
	}

	public String getServerInfos(String pw, int infocode, String parameter) {
		if (!pw.equals("Kennwort1!"))
			return "";
		switch (infocode) {
		case 0:
			return SessionManagement.getSessions().size() + "";
		case 1:
			SessionManagement.deleteSession(Long.parseLong(parameter));
			break;
		case 2:

			break;
		case 3:

			break;

		}
		return "Nothing";
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
		return checkUser != null && checkUser.equals(benutzername) ? sessionID : -1;
	}

	public boolean logoff(Long sessionID) {
		User updateuser = new User();
		updateuser.setBenutzername(SessionManagement.checkSession(sessionID));
		// updateuser.setStatus(1);
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
				return DB.update(new DaoObject[] { updateuser });
			}
		return false;
	}

	public User getUser(String benutzername, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null) {
			return null;
		}
		User user = new User();
		user.setBenutzername(benutzername);
		User[] u = ((User[]) DB.select(user));
		if (u != null && u.length > 0)
			return u[0];
		return null;
	}

	public boolean getLiquiditaet(double einnahmen, double ueberschuss, double rateKredit, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return false;
		SessionManagement.updateSession(sessionID);

		if ((einnahmen / 2 > rateKredit) && ueberschuss > 1)
			return true;
		else
			return false;
	}

	public Tilgungsplan getTilgungsPlan(double kreditHoehe, String kreditBeginn, double zinsatzDifferenz, double ratenHoehe, int laufzeitMonate, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return null;
		if (zinsatzDifferenz > KonfigFiles.getDouble(KonfigFiles.Kalkulation_MAX_ZINSSATZDIF)) {
			zinsatzDifferenz = KonfigFiles.getDouble(KonfigFiles.Kalkulation_MAX_ZINSSATZDIF);
		} else if (zinsatzDifferenz < KonfigFiles.getDouble(KonfigFiles.Kalkulation_MIN_ZINSSATZDIF)) {
			zinsatzDifferenz = KonfigFiles.getDouble(KonfigFiles.Kalkulation_MIN_ZINSSATZDIF);
		}
		Tilgungsplan tp = new Tilgungsplan(kreditHoehe, kreditBeginn, KonfigFiles.getDouble(KonfigFiles.Kalkulation_ZINSSATZ) + zinsatzDifferenz, ratenHoehe, laufzeitMonate, null);
		tp.setTilgungen(TilgungsPlanErsteller.erstelleTilgungsPlan(tp));
		if (tp.getLaufzeitMonate() == -1)
			tp.setLaufzeitMonate(tp.getTilgungen() != null ? tp.getTilgungen().length : -1);
		else if (tp.getRatenHoehe() == -1)
			tp.setRatenHoehe(tp.getTilgungen() != null && tp.getTilgungen().length > 0 ? tp.getTilgungen()[0].getRate() : -1);

		SessionManagement.updateSession(sessionID);

		return tp;
	}

	public Antragssteller[] getAntragssteller(String vorname, String nachname, String gebDatum, int hauptGirokonto, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null) {
			return null;
		}

		Antragssteller as = new Antragssteller();
		as.setVorname(vorname != null && !vorname.equals("") ? vorname + "%" : null);
		as.setNachname(nachname != null && !nachname.equals("") ? nachname + "%" : null);
		as.setGebDatum_dt(gebDatum);
		as.setHauptGirokonto(hauptGirokonto);
		Antragssteller[] asss = (Antragssteller[]) DB.select(as);
		if (asss != null)
			for (Antragssteller ass : asss) {
				ass.setAusgaben((Ausgaben[]) DB.select(new Ausgaben(ass.getId())));
				ass.setArbeitgeber((Arbeitgeber[]) DB.select(new Arbeitgeber(ass.getId())));
				ass.setEinnahmen((Einnahmen[]) DB.select(new Einnahmen(ass.getId())));
				ass.setKreditantraege((Kreditantrag[]) DB.select(new Kreditantrag(ass.getId())));
				ass.setVersicherungen((Versicherungen[]) DB.select(new Versicherungen(ass.getId())));
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
				ass.setKreditverbindlichkeiten((Kreditverbindlichkeiten[]) DB.select(new Kreditverbindlichkeiten(ass.getId())));
			}
		SessionManagement.updateSession(sessionID);
		return asss;
	}

	public boolean updateAntragssteller(Antragssteller antragsssteller, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return false;

		boolean update = false;
		update = DB.update(new DaoObject[] { antragsssteller });

		update = DB.update(antragsssteller.getAusgaben());
		update = DB.update(antragsssteller.getArbeitgeber());
		update = DB.update(antragsssteller.getEinnahmen());
		update = DB.update(antragsssteller.getKreditantraege());
		update = DB.update(antragsssteller.getVersicherungen());
		update = DB.update(antragsssteller.getKreditverbindlichkeiten());
		for (Kreditantrag ka : antragsssteller.getKreditantraege()) {
			update = DB.update(new DaoObject[] { ka.getAntragssteller_2() });
			update = DB.update(new DaoObject[] { ka.getBerater() });
		}

		SessionManagement.updateSession(sessionID);

		return update;
	}

	public boolean insertAntragssteller(Antragssteller antragsssteller, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return false;

		int[] ids;
		ids = DB.insert(new DaoObject[] { antragsssteller });
		if (ids != null && ids.length > 0 && ids[0] > 0)
			antragsssteller.setId(ids[0]);

		Ausgaben[] ausgaben = antragsssteller.getAusgaben();
		if (ausgaben != null)
			for (Ausgaben a : ausgaben)
				a.setReferenzIds(new int[] { antragsssteller.getId() });
		ids = DB.insert(ausgaben);

		Arbeitgeber[] arbeitgeber = antragsssteller.getArbeitgeber();
		if (arbeitgeber != null)
			for (Arbeitgeber a : arbeitgeber)
				a.setReferenzIds(new int[] { antragsssteller.getId() });
		ids = DB.insert(arbeitgeber);

		Einnahmen[] einnahmen = antragsssteller.getEinnahmen();
		if (einnahmen != null)
			for (Einnahmen a : einnahmen) {
				a.setReferenzIds(new int[] { antragsssteller.getId() });
			}
		ids = DB.insert(einnahmen);

		Versicherungen[] versicherungen = antragsssteller.getVersicherungen();
		if (versicherungen != null)
			for (Versicherungen a : versicherungen)
				a.setReferenzIds(new int[] { antragsssteller.getId() });
		ids = DB.insert(versicherungen);

		Kreditverbindlichkeiten[] kreditverbindlichkeiten = antragsssteller.getKreditverbindlichkeiten();
		if (kreditverbindlichkeiten != null)
			for (Kreditverbindlichkeiten a : kreditverbindlichkeiten)
				a.setReferenzIds(new int[] { antragsssteller.getId() });
		ids = DB.insert(kreditverbindlichkeiten);

		Kreditantrag[] kreditantraege = antragsssteller.getKreditantraege();
		if (kreditantraege != null)
			for (Kreditantrag ka : kreditantraege) {
				ka.setReferenzIds(new int[] { antragsssteller.getId() });
				ids = DB.insert(new DaoObject[] { ka.getAntragssteller_2() });
				// insert = DB.insert(new DaoObject[] { ka.getBerater() });
			}
		ids = DB.insert(kreditantraege);

		SessionManagement.updateSession(sessionID);

		return true;
	}
}
