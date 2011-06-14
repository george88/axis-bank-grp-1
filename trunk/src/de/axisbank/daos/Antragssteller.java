package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verf�gung. Sie ist quasi Eintrag der Datenbanktabelle Antragssteler 
 * @author Georg Neufeld
 *
 */
public class Antragssteller extends DaoObject {

	/************************** Variablen *************************************/
	private String anrede;

	private String titel;

	private String vorname;

	private String nachname;

	private String gebName;

	private String gebOrt;

	private String gebDatum;

	private String str;

	private String hnr;

	private String plz;

	private String ort;

	private String wohnhaftSeit;

	private String telefon;

	private String email;

	private String beruf;

	private int anzKinder = -1;

	private String familienstand;

	private int hauptGirokonto = -1;

	private Einnahmen[] einnahmen;

	private Versicherungen[] versicherungen;

	private Ausgaben[] ausgaben;

	private Arbeitgeber[] arbeitgeber;

	private Kreditverbindlichkeiten[] kreditverbindlichkeiten;

	private Kreditantrag[] kreditantraege;

	/************************** Konstruktor *************************************/
	public Antragssteller() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller_2" });
	}

	public Antragssteller(int idAntragssteller_2) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller_2" });
		setReferenzIds(new int[] { idAntragssteller_2 });
	}

	public Antragssteller(String anrede, String titel, String vorname, String nachname, String gebName, String gebOrt, String gebDatum, String str, String hnr, String plz, String ort,
			String wohnhaftSeit, String telefon, String email, String beruf, int anzKinder, String familienstand, int hauptGirokonto, Einnahmen[] einnahmen, Versicherungen[] versicherungen,
			Ausgaben[] ausgaben, Arbeitgeber[] arbeitgeber, Kreditverbindlichkeiten[] kreditverbindlichkeiten, Kreditantrag[] kreditantraege) {
		super();
		this.anrede = anrede;
		this.titel = titel;
		this.vorname = vorname;
		this.nachname = nachname;
		this.gebName = gebName;
		this.gebOrt = gebOrt;
		this.gebDatum = gebDatum;
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
		this.hauptGirokonto = hauptGirokonto;
		this.einnahmen = einnahmen;
		this.versicherungen = versicherungen;
		this.ausgaben = ausgaben;
		this.arbeitgeber = arbeitgeber;
		this.kreditverbindlichkeiten = kreditverbindlichkeiten;
		this.setKreditantraege(kreditantraege);
		setReferenzIdNames(new String[] { "idAntragssteller_2" });
	}

	/************************* Getter/Setter ************************************/
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

	public void setGebDatum_dt(String gebDatum) {
		this.gebDatum = gebDatum;
	}

	public String getGebDatum_dt() {
		return gebDatum;
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

	public String getWohnhaftSeit_dt() {
		return wohnhaftSeit;
	}

	public void setWohnhaftSeit_dt(String wohnhaftSeit) {
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

	public Einnahmen[] getEinnahmen() {
		return einnahmen;
	}

	public void setEinnahmen(Einnahmen[] einnahmen) {
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

	public void setKreditverbindlichkeiten(Kreditverbindlichkeiten[] verbindlichkeiten) {
		this.kreditverbindlichkeiten = verbindlichkeiten;
	}

	public void setKreditantraege(Kreditantrag[] kreditantraege) {
		this.kreditantraege = kreditantraege;
	}

	public Kreditantrag[] getKreditantraege() {
		return kreditantraege;
	}

	public void setHauptGirokonto(int hauptGirokonto) {
		this.hauptGirokonto = hauptGirokonto;
	}

	public int getHauptGirokonto() {
		return hauptGirokonto;
	}
}
