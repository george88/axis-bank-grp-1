package de.axisbank.daos;

import java.util.HashMap;

public class DaoObject {

	private int id;

	private int referenzId;

	private String IdName;

	private String referenzIdName;

	private String tableName;

	public DaoObject() {
		setTableName(getClass().getSimpleName());
		setIdName("id" + getTableName());
	}

	public DaoObject(int id, String tableName) {
		super();
		this.id = id;
		this.tableName = tableName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public HashMap<Class<DaoObject>, String> getForeignKeyDefinition() {
		return null;
	}

	public void setIdName(String idName) {
		IdName = idName;
	}

	public String getIdName() {
		return IdName;
	}

	public void setReferenzId(int referenzId) {
		this.referenzId = referenzId;
	}

	public int getReferenzId() {
		return referenzId;
	}

	public void setReferenzIdName(String referenzIdName) {
		this.referenzIdName = referenzIdName;
	}

	public String getReferenzIdName() {
		return referenzIdName;
	}
}
