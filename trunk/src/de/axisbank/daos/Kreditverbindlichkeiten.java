package de.axisbank.daos;

/**
 * Diese Klasse stellt das gleichnamige Datenbankobjekt zur Verf�gung. Sie ist quasi Eintrag der Datenbanktabelle Kreditverbindlichkeiten. 
 * @author Georg Neufeld
 *
 */
public class Kreditverbindlichkeiten extends DaoObject {

	/************************** Variablen *************************************/
	private String kreditinstitut;

	private double gesamtschuld = -1.0D;

	private double restSchuld = -1.0D;

	private double mtlRate = -1.0D;

	/************************** Konstruktor *************************************/
	public Kreditverbindlichkeiten(String kreditinstitut, double gesamtschuld, double restSchuld, double mtlRate) {
		super();
		this.kreditinstitut = kreditinstitut;
		this.gesamtschuld = gesamtschuld;
		this.restSchuld = restSchuld;
		this.mtlRate = mtlRate;
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Kreditverbindlichkeiten() {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
	}

	public Kreditverbindlichkeiten(int idAntragssteller) {
		super();
		setReferenzIdNames(new String[] { "idAntragssteller" });
		setReferenzIds(new int[] { idAntragssteller });
	}

	/************************* Getter/Setter ************************************/
	public String getKreditinstitut() {
		return kreditinstitut;
	}

	public void setKreditinstitut(String kreditinstitut) {
		this.kreditinstitut = kreditinstitut;
	}

	public double getGesamtschuld() {
		return gesamtschuld;
	}

	public void setGesamtschuld(double gesamtschuld) {
		this.gesamtschuld = gesamtschuld;
	}

	public double getRestSchuld() {
		return restSchuld;
	}

	public void setRestSchuld(double restSchuld) {
		this.restSchuld = restSchuld;
	}

	public double getMtlRate() {
		return mtlRate;
	}

	public void setMtlRate(double mtlRate) {
		this.mtlRate = mtlRate;
	}
}
