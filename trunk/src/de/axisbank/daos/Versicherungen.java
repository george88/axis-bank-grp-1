package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verf�gung. Sie ist quasi Eintrag der Datenbanktabelle Versicherungen. 
 * @author Georg Neufeld
 *
 */
public class Versicherungen extends DaoObject {

	/************************** Variablen *************************************/
	private String versArt;

	private String versGesellschaft;

	private double versSumme = -1.0D;

	private double mtlBeitrag = -1.0D;

	/************************** Konstruktor *************************************/
	public Versicherungen() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Versicherungen(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAntragssteller });
	}

	public Versicherungen(String versArt, String versGesellschaft, double versSumme, double mtlBeitrag) {
		super();
		this.versArt = versArt;
		this.versGesellschaft = versGesellschaft;
		this.versSumme = versSumme;
		this.mtlBeitrag = mtlBeitrag;
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	/************************* Getter/Setter ************************************/
	public String getVersArt() {
		return versArt;
	}

	public void setVersArt(String versArt) {
		this.versArt = versArt;
	}

	public String getVersGesellschaft() {
		return versGesellschaft;
	}

	public void setVersGesellschaft(String versGesellschaft) {
		this.versGesellschaft = versGesellschaft;
	}

	public double getVersSumme() {
		return versSumme;
	}

	public void setVersSumme(double versSumme) {
		this.versSumme = versSumme;
	}

	public double getMtlBeitrag() {
		return mtlBeitrag;
	}

	public void setMtlBeitrag(double mtlBeitrag) {
		this.mtlBeitrag = mtlBeitrag;
	}

}
