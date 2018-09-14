package com.springmvc.roleAuthority;

public class RoleAuthorityVO {
	//table:role_authority
	private String role_authority_id;       //role_authority_id主键
	private String role_description;       //角色描述
	private String authority_list;       //权限列表
	private String role_type;       //角色类型
	private String status;       //角色状态
	
	private String colmuns="role_authority_id,role_description,authority_list,role_type,status";
	private String updateColmuns="role_description,authority_list,role_type";
	
	public String getRole_authority_id() {
		return role_authority_id;
	}
	public void setRole_authority_id(String role_authority_id) {
		this.role_authority_id = role_authority_id;
	}
	public String getRole_description() {
		return role_description;
	}
	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}
	public String getAuthority_list() {
		return authority_list;
	}
	public void setAuthority_list(String authority_list) {
		this.authority_list = authority_list;
	}
	public String getRole_type() {
		return role_type;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}
	public String getUpdateColmuns() {
		return updateColmuns;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
	}

}
