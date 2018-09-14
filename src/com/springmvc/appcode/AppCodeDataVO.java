package com.springmvc.appcode;

public class AppCodeDataVO {
	//table:app_codedata
	private String codetype;       //codetypeÖ÷¼ü
	private String codevalue;       //value
	private String codename;       //name
	private String father_codevalue;       //father_codevalue
	
	private String colmuns="codetype,codevalue,codename,father_codevalue";
	private String updateColmuns="codename,father_codevalue";
	
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}
	public String getFather_codevalue() {
		return father_codevalue;
	}
	public void setFather_codevalue(String father_codevalue) {
		this.father_codevalue = father_codevalue;
	}
	public String getUpdateColmuns() {
		return updateColmuns;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
	}

}
