package de.axis_bank.daos;

public class Antragsteller {

	private int idAntragsteller;

	private String anrede;

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

	private Ausgaben ausgaben;

	private Arbeitgeber[] arbeitgeber;

	private Verbindlichkeiten[] verbindlichkeiten;

	private Kreditantrag[] kreditantraege;

	public Antragsteller() {

	}

	public Antragsteller(int idAntragsteller, String anrede, String vorname,
			String nachname, String gebName, String gebOrt, String str,
			String hnr, String plz, String ort, String wohnhaftSeit,
			String telefon, String email, String beruf, int anzKinder,
			String familienstand, Einnahmen einnahmen,
			Versicherungen[] versicherungen, Ausgaben ausgaben,
			Arbeitgeber[] arbeitgeber, Verbindlichkeiten[] verbindlichkeiten,
			Kreditantrag[] kreditantraege) {
		super();
		this.idAntragsteller = idAntragsteller;
		this.anrede = anrede;
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
		this.verbindlichkeiten = verbindlichkeiten;
		this.kreditantraege = kreditantraege;
	}

	public int getIdAntragsteller() {
		return idAntragsteller;
	}

	public void setIdAntragsteller(int idAntragsteller) {
		this.idAntragsteller = idAntragsteller;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
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

	public Ausgaben getAusgaben() {
		return ausgaben;
	}

	public void setAusgaben(Ausgaben ausgaben) {
		this.ausgaben = ausgaben;
	}

	public Arbeitgeber[] getArbeitgeber() {
		return arbeitgeber;
	}

	public void setArbeitgeber(Arbeitgeber[] arbeitgeber) {
		this.arbeitgeber = arbeitgeber;
	}

	public Verbindlichkeiten[] getVerbindlichkeiten() {
		return verbindlichkeiten;
	}

	public void setVerbindlichkeiten(Verbindlichkeiten[] verbindlichkeiten) {
		this.verbindlichkeiten = verbindlichkeiten;
	}

	public Kreditantrag[] getKreditantraege() {
		return kreditantraege;
	}

	public void setKreditantraege(Kreditantrag[] kreditantraege) {
		this.kreditantraege = kreditantraege;
	}
}
