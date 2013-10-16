package com.hyrt.foshanLaw.e;

public class EntInfo {
	private String id;
	private String email;
	private String ent_name;
	private String law_name;

	public EntInfo() {

	}

	public EntInfo(String id, String email, String ent_name, String law_name) {
		super();
		this.id = id;
		this.email = email;
		this.ent_name = ent_name;
		this.law_name = law_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnt_name() {
		return ent_name;
	}

	public void setEnt_name(String ent_name) {
		this.ent_name = ent_name;
	}

	public String getLaw_name() {
		return law_name;
	}

	public void setLaw_name(String law_name) {
		this.law_name = law_name;
	}

}
