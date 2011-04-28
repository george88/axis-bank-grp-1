package de.axis_bank.daos;

public class DaoObject {

	private int id;

	private String tableName;

	public DaoObject() {

	}

	public DaoObject(int id, String name) {
		super();
		this.id = id;
		this.tableName = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return tableName;
	}

	public void setTableName(String name) {
		this.tableName = name;
	}

}
