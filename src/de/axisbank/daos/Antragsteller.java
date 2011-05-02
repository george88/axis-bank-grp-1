package de.axisbank.daos;

public class Antragsteller extends DaoObject {

	private String anrede;

	private String titel;

	private String vorname;

	private String nachname;

	private String gebName;

	private String gebOrt;

	private String str;

	private String hnr;

	private String plz;

	private String ort;

	private String wohnhaftSeit;

	private String telefon;

	private String email;

	private String beruf;

	private int anzKinder;

	private String familienstand;

	private Einnahmen einnahmen;

	private Versicherungen[] versicherungen;

	private Ausgaben[] ausgaben;

	private Arbeitgeber[] arbeitgeber;

	private Kreditverbindlichkeiten[] kreditverbindlichkeiten;

	private Kreditantrag_hat_Antragsteller[] kreditantrag_hat_antragsteller;

	public Antragsteller() {

	}

	public Antragsteller(String anrede, String titel, String vorname,
			String nachname, String gebName, String gebOrt, String str,
			String hnr, String plz, String ort, String wohnhaftSeit,
			String telefon, String email, String beruf, int anzKinder,
			String familienstand, Einnahmen einnahmen,
			Versicherungen[] versicherungen, Ausgaben[] ausgaben,
			Arbeitgeber[] arbeitgeber,
			Kreditverbindlichkeiten[] kreditverbindlichkeiten,
			Kreditantrag_hat_Antragsteller[] kreditantrag_hat_antragsteller) {
		super();
		this.anrede = anrede;
		this.titel = titel;
		this.vorname = vorname;
		this.nachname = nachname;
		this.gebName = gebName;
		this.gebOrt = gebOrt;
		this.str = str;
		this.hnr = hnr;
		this.plz = plz;
		this.ort = ort;
		this.wohnhaftSeit = wohnhaftSeit;
		this.telefon = telefon;
		this.email = email;
		this.beruf = beruf;
		this.anzKinder = anzKinder;
		this.familienstand = familienstand;
		this.einnahmen = einnahmen;
		this.versicherungen = versicherungen;
		this.ausgaben = ausgaben;
		this.arbeitgeber = arbeitgeber;
		this.kreditverbindlichkeiten = kreditverbindlichkeiten;
		this.kreditantrag_hat_antragsteller = kreditantrag_hat_antragsteller;

	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getGebName() {
		return gebName;
	}

	public void setGebName(String gebName) {
		this.gebName = gebName;
	}

	public String getGebOrt() {
		return gebOrt;
	}

	public void setGebOrt(String gebOrt) {
		this.gebOrt = gebOrt;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getHnr() {
		return hnr;
	}

	public void setHnr(String hnr) {
		this.hnr = hnr;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getWohnhaftSeit() {
		return wohnhaftSeit;
	}

	public void setWohnhaftSeit(String wohnhaftSeit) {
		this.wohnhaftSeit = wohnhaftSeit;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBeruf() {
		return beruf;
	}

	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}

	public int getAnzKinder() {
		return anzKinder;
	}

	public void setAnzKinder(int anzKinder) {
		this.anzKinder = anzKinder;
	}

	public String getFamilienstand() {
		return familienstand;
	}

	public void setFamilienstand(String familienstand) {
		this.familienstand = familienstand;
	}

	public Einnahmen getEinnahmen() {
		return einnahmen;
	}

	public void setEinnahmen(Einnahmen einnahmen) {
		this.einnahmen = einnahmen;
	}

	public Versicherungen[] getVersicherungen() {
		return versicherungen;
	}

	public void setVersicherungen(Versicherungen[] versicherungen) {
		this.versicherungen = versicherungen;
	}

	public Ausgaben[] getAusgaben() {
		return ausgaben;
	}

	public void setAusgaben(Ausgaben[] ausgaben) {
		this.ausgaben = ausgaben;
	}

	public Arbeitgeber[] getArbeitgeber() {
		return arbeitgeber;
	}

	public void setArbeitgeber(Arbeitgeber[] arbeitgeber) {
		this.arbeitgeber = arbeitgeber;
	}

	public Kreditverbindlichkeiten[] getKreditverbindlichkeiten() {
		return kreditverbindlichkeiten;
	}

	public void setKreditverbindlichkeiten(
			Kreditverbindlichkeiten[] verbindlichkeiten) {
		this.kreditverbindlichkeiten = verbindlichkeiten;
	}

	public Kreditantrag_hat_Antragsteller[] getKreditantraege() {
		return kreditantrag_hat_antragsteller;
	}

	public void setKreditantraege(
			Kreditantrag_hat_Antragsteller[] kreditantrag_hat_antragsteller) {
		this.kreditantrag_hat_antragsteller = kreditantrag_hat_antragsteller;
	}
}
