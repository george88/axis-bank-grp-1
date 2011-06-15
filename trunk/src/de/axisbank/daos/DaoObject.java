package de.axisbank.daos;

/**
 * Diese Klasse stellt die Superklasse füpr alle anderen Klassen in diesem package dar. Sie stellt die grundlegenden Eigenschaften aller anderen Data-Access-Object-Klassen zur Verfügung. 
 * @author Georg Neufeld
 *
 */
public class DaoObject {

	/************************** Variablen *************************************/

	/**
	 * Die Identifikation in der Datenbank eines jeden Eintrages
	 */
	private int id = -1;

	/**
	 * Die zu diesem eintrag zugehörigen Identifikationsnummer aus den Referenztabellen in Kombination mit den Namen Attribute aus den Referenztabellen nutzbar
	 */
	private int[] referenzIds;

	/**
	 * Der Name des Identifikationsattributs in der Datenbank
	 */
	private String IdName;

	/**
	 * Die Namen der Attributsnamen Identifikationen aus den Referenztabellen, sollten in der gleichen Reihenfolge wie die zugehörigen Identifikationsnummer sein
	 */
	private String[] referenzIdNames;

	/**
	 * Der Tabellenname, typischerweise der gleiche des einfachen Klassennamens
	 */
	private String tableName;

	/************************** Konstruktor *************************************/
	/**
	 * Standardkonstruktor
	 */
	public DaoObject() {
		//Für alle DAO-Objekte gilt voreingestellt der Tabellenname gleich des Klassennamen und der Name des Identifikationsattributs gleich der Klassenname/Tabellenname mit der Abkürzung "id" davor
		setTableName(getClass().getSimpleName());
		setIdName("id" + getTableName());
	}

	/**
	 * Konstruktor um ein vollständiges DAO-Objekt anzulegen
	 * @param id
	 * @param tableName
	 */
	public DaoObject(int id, String tableName) {
		super();
		this.id = id;
		this.tableName = tableName;
	}

	/************************* Getter/Setter ************************************/

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
