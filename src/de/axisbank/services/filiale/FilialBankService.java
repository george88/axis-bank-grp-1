package de.axisbank.services.filiale;

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
import de.axisbank.tools.Logging;
import de.axisbank.tools.TilgungsPlanErsteller;

public class FilialBankService {

	public FilialBankService() {
	}

	public String getSessionInfos(String pw) {
		if (!pw.equals("Kennwort1!"))
			return "";

		String r = "";
		r += "Anzahl der aktiven Sessions: " + SessionManagement.getSessions().size() + "\nsessionID\t\t\tBenutzname\tRestzeit\n______________________________________________________________\n";
		for (Long sessionID : SessionManagement.getSessions().keySet()) {
			r += sessionID + "\t\t" + SessionManagement.getSessions().get(sessionID).getBenutzername() + "\t\t" + SessionManagement.getSessions().get(sessionID).getDelayTime() + "\n";
		}
		return r;
	}

	public void deleteAllSessions(String pw) {
		if (!pw.equals("Kennwort1!"))
			return;
		for (Long l : SessionManagement.getSessions().keySet())
			SessionManagement.deleteSession(l);
	}

	public Long login(String benutzername, String passwort) {
		User tmpUser = new User();
		tmpUser.setBenutzername(benutzername);
		tmpUser.setPasswort(passwort);
		User[] users = (User[]) DB.select(tmpUser);
		Long sessionID = -1L;
		if (users != null && users.length == 1 && (users)[0] != null) {
			sessionID = SessionManagement.checkSession(benutzername);
			if (sessionID != -1L) {
				Logging.logLine("Benutzer war bereits angemeldet, Session wird gel�scht und neue erstellt!");
				SessionManagement.deleteSession(sessionID);
			}
			User user = users[0];
			user.setStatus(1);
			user.setLetzterLogin(System.currentTimeMillis());
			DB.update(new DaoObject[] { user });
			sessionID = SessionManagement.addSession(benutzername);
			return sessionID;
		}
		return -1L;
	}

	public boolean logoff(Long sessionID) {
		User updateuser = new User();
		updateuser.setBenutzername(SessionManagement.checkSession(sessionID));
		updateuser.setStatus(1);
		SessionManagement.deleteSession(sessionID);
		Object userObjs = DB.select(updateuser);
		if (userObjs != null)
			if (((User[]) userObjs).length > 0) {
				User user = new User();
				user.setId(((User[]) userObjs)[0].getId());
				user.setStatus(0);
				return DB.update(new DaoObject[] { user });
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

		if ((einnahmen / 2.) > rateKredit && ueberschuss > 1)
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
		as.setVorname(vorname != null && !vorname.equals("") ? "%" + vorname + "%" : null);
		as.setNachname(nachname != null && !nachname.equals("") ? "%" + nachname + "%" : null);
		as.setGebDatum_dt(gebDatum);
		as.setHauptGirokonto(hauptGirokonto);
		Antragssteller[] asss = (Antragssteller[]) DB.select(as);
		if (asss != null)
			for (Antragssteller ass : asss) {
				ass.setAusgaben((Ausgaben[]) DB.select(new Ausgaben(ass.getId())));
				ass.setArbeitgeber((Arbeitgeber[]) DB.select(new Arbeitgeber(ass.getId())));
				ass.setEinnahmen((Einnahmen[]) DB.select(new Einnahmen(ass.getId())));
				ass.setVersicherungen((Versicherungen[]) DB.select(new Versicherungen(ass.getId())));
				ass.setKreditantraege((Kreditantrag[]) DB.select(new Kreditantrag(ass.getId())));
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
						Logging.logLine("userID: " + ka.getidUser());
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

	public boolean insertKreditantrag(User berater, Antragssteller antragsteller, Antragssteller antragsteller_2, String verhaeltnisZu_2, String filiale, String status, Tilgungsplan tilgungsPlan,
			Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return false;
		Kreditantrag kreditantrag = new Kreditantrag();
		kreditantrag.setDatum_dt(tilgungsPlan.getKreditBeginn());
		kreditantrag.setFiliale(filiale);
		kreditantrag.setVerhaeltnisZu_2(verhaeltnisZu_2);
		kreditantrag.setKreditWunsch(tilgungsPlan.getKreditHoehe());
		kreditantrag.setStatus(status);
		kreditantrag.setRatenHoehe(Math.round(tilgungsPlan.getRatenHoehe() * 100) / 100);
		kreditantrag.setRatenAnzahl(tilgungsPlan.getLaufzeitMonate());
		kreditantrag.setReferenzIds(new int[] { antragsteller.getId(), berater.getId() });
		kreditantrag.setReferenzIdNames(new String[] { "idAntragssteller", "idUser" });
		if (antragsteller_2 != null)
			kreditantrag.setIdAntragssteller_2(antragsteller_2.getId());
		Logging.logObjectDetail(kreditantrag);
		int[] erg = DB.insert(new DaoObject[] { kreditantrag });
		if (erg.length == 1)
			return true;
		else
			return false;
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
				ids = DB.insert(new DaoObject[] { ka.getBerater() });
			}
		ids = DB.insert(kreditantraege);

		SessionManagement.updateSession(sessionID);

		return true;
	}

	public boolean deleteAntragssteller(Antragssteller antragssteller, boolean nurEnthalteneReferenzen, Long sessionID) {
		if (SessionManagement.checkSession(sessionID) == null)
			return false;

		DB.delete(antragssteller.getArbeitgeber());
		DB.delete(antragssteller.getAusgaben());
		DB.delete(antragssteller.getEinnahmen());
		DB.delete(antragssteller.getKreditantraege());
		DB.delete(antragssteller.getKreditverbindlichkeiten());
		DB.delete(antragssteller.getVersicherungen());
		if (nurEnthalteneReferenzen) {
			return true;
		}
		return DB.delete(new DaoObject[] { antragssteller });
	}
}
