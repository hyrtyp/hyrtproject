package com.hyrt.cei.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FormFile {
	
	private String formname;
	private String filename;
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}

	public byte[] getData() throws IOException{
		InputStream is = new FileInputStream(filename);
		byte[] b = new byte[Integer.parseInt(new File(filename).length()+"")];
		is.read(b);
		return b;
	}
	public FormFile(String formname, String filename) {
		super();
		this.formname = formname;
		this.filename = filename;
	}
}
