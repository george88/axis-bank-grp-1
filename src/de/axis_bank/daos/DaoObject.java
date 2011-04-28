package de.axis_bank.daos;

public class DaoObject {

	private int id;

	private String name;

	public DaoObject() {

	}

	public DaoObject(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
