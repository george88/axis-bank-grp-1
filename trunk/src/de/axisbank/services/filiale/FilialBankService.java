package de.axisbank.services.filiale;

import de.axisbank.daos.Antragssteller;
import de.axisbank.daos.DaoObject;
import de.axisbank.daos.Tilgungsplan;
import de.axisbank.daos.User;
import de.axisbank.datenbank.DB;

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

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getBenutzername() {
		return benutzername;
	}

}
