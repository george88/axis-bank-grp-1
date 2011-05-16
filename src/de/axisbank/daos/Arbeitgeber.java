package de.axisbank.daos;

public class Arbeitgeber extends DaoObject {

	private String nameArbeitgeber;

	private String beschSeit;

	private String strArbeitgeber;

	private String hnrArbeitgeber;

	private String plzArbeitgeber;

	private String ortArbeitgeber;

	public Arbeitgeber() {
		super();
		setReferenzIdName("idAntragssteller");
	}

	public Arbeitgeber(int idArbeitgeber) {
		super();
		setReferenzIdName("idAntragssteller");
		setReferenzId(idArbeitgeber);
	}

	public Arbeitgeber(String nameArbeitgeber, String beschSeit,
			String strArbeitgeber, String hnrArbeitgeber,
			String plzArbeitgeber, String ortArbeitgeber) {
		super();
		this.nameArbeitgeber = nameArbeitgeber;
		this.beschSeit = beschSeit;
		this.strArbeitgeber = strArbeitgeber;
		this.hnrArbeitgeber = hnrArbeitgeber;
		this.plzArbeitgeber = plzArbeitgeber;
		this.ortArbeitgeber = ortArbeitgeber;
	}

	public String getNameArbeitgeber() {
		return nameArbeitgeber;
	}

	public void setNameArbeitgeber(String nameArbeitgeber) {
		this.nameArbeitgeber = nameArbeitgeber;
	}

	public String getBeschSeit_dt() {
		return beschSeit;
	}

	public void setBeschSeit_dt(String beschSeit) {
		this.beschSeit = beschSeit;
	}

	public String getStrArbeitgeber() {
		return strArbeitgeber;
	}

	public void setStrArbeitgeber(String strArbeitgeber) {
		this.strArbeitgeber = strArbeitgeber;
	}

	public String getHnrArbeitgeber() {
		return hnrArbeitgeber;
	}

	public void setHnrArbeitgeber(String hnrArbeitgeber) {
		this.hnrArbeitgeber = hnrArbeitgeber;
	}

	public String getPlzArbeitgeber() {
		return plzArbeitgeber;
	}

	public void setPlzArbeitgeber(String plzArbeitgeber) {
		this.plzArbeitgeber = plzArbeitgeber;
	}

	public String getOrtArbeitgeber() {
		return ortArbeitgeber;
	}

	public void setOrtArbeitgeber(String ortArbeitgeber) {
		this.ortArbeitgeber = ortArbeitgeber;
	}
}
