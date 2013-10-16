package com.hyrt.mwpm.vo;

/**
 * MwpmBussPatrolTable entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MwpmBussPatrolTable implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String dbtablename;
	private String tablename;

	// Constructors

	/** default constructor */
	public MwpmBussPatrolTable() {
	}

	/** minimal constructor */
	public MwpmBussPatrolTable(String id) {
		this.id = id;
	}

	/** full constructor */
	public MwpmBussPatrolTable(String id, String name, String dbtablename, String tablename) {
		this.id = id;
		this.name = name;
		this.dbtablename = dbtablename;
		this.tablename = tablename;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbtablename() {
		return this.dbtablename;
	}

	public void setDbtablename(String dbtablename) {
		this.dbtablename = dbtablename;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}