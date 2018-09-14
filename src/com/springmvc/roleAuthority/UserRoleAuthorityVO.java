package com.springmvc.roleAuthority;

public class UserRoleAuthorityVO {
	//table:user_role_authority
	private String user_roal_authority_id;       //user_roal_authority_id主键
	private String userid;       //用户ID
	private String role_authority_id;       //角色ID
	
	private String colmuns="user_roal_authority_id,userid,role_authority_id";
	
	
	public String getUser_roal_authority_id() {
		return user_roal_authority_id;
	}
	public void setUser_roal_authority_id(String user_roal_authority_id) {
		this.user_roal_authority_id = user_roal_authority_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRole_authority_id() {
		return role_authority_id;
	}
	public void setRole_authority_id(String role_authority_id) {
		this.role_authority_id = role_authority_id;
	}
	
	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}

}
