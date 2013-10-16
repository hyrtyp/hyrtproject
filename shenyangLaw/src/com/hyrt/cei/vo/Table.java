package com.hyrt.cei.vo;

public class Table{
	
	private String pkField;
	private String tableName;
	private String tableCode;
	private Column[] cols;
	
	public String getPkField() {
		return pkField;
	}
	public void setPkField(String pkField) {
		this.pkField = pkField;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public Column[] getCols() {
		return cols;
	}
	public void setCols(Column[] cols) {
		this.cols = cols;
	}
	
}