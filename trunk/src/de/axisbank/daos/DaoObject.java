package de.axisbank.daos;

import java.util.HashMap;

public class DaoObject {

	private int id = -1;

	private int[] referenzIds;

	private String IdName;

	private String[] referenzIdNames;

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

	public void setReferenzIds(int[] referenzIds) {
		this.referenzIds = referenzIds;
	}

	public int[] getReferenzIds() {
		return referenzIds;
	}

	public void setReferenzIdNames(String[] referenzIdName) {
		this.referenzIdNames = referenzIdName;
	}

	public String[] getReferenzIdNames() {
		return referenzIdNames;
	}
}
