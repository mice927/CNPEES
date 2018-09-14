package com.springmvc.appcode;

public class AppCodeIndexVO {
	//table:app_codeindex
	private String codetype;       //codetype主键
	private String description;       //描述
	private String status;       //状态
	private String onload;       //初始化代码集
	private String father_code;       //父代码集
	
	private String colmuns="codetype,description,status,onload,father_code";
	private String updateColmuns="description,onload,father_code";
	
	public String getCodetype() {
		return codetype;
	}
	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOnload() {
		return onload;
	}
	public void setOnload(String onload) {
		this.onload = onload;
	}
	public String getFather_code() {
		return father_code;
	}
	public void setFather_code(String father_code) {
		this.father_code = father_code;
	}
	public String getUpdateColmuns() {
		return updateColmuns;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
	}
	
	

}
