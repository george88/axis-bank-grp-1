package de.axis_bank.daos;

public class Arbeitgeber extends DaoObject {

	private String nameArbeitgeber;

	private String beschSeit;

	private String strArbeitgeber;

	private String hnrArbeitgeber;

	private String plzArbeitgeber;

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

	public Arbeitgeber() {
		setName(getClass().getName());

	}

	public String getNameArbeitgeber() {
		return nameArbeitgeber;
	}

	public void setNameArbeitgeber(String nameArbeitgeber) {
		this.nameArbeitgeber = nameArbeitgeber;
	}

	public String getBeschSeit() {
		return beschSeit;
	}

	public void setBeschSeit(String beschSeit) {
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

	private String ortArbeitgeber;

}
