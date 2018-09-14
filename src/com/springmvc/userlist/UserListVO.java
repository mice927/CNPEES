package com.springmvc.userlist;

public class UserListVO {
	
	//table:userlist
	private String userid;       //userid主键
	private String usercode;  //用户代码   登陆码
	private String username;  //用户姓名
	private String password;  //密码
	private String office_one;//部门
	private String office_two;//处级
	private String office_thr;//科室
	private String office_phone;  //办公电话
	private String phone;       //移动电话
	private String email;     //邮箱
	private String grade;     //资格等级
	private String status;    //在职状态
	private String colmuns="userid,usercode,username,password,office_one,office_two,office_thr,office_phone,phone,email,status,grade";
	private String updateColmuns="username,office_one,office_two,office_thr,office_phone,phone,email,grade";
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOffice_one() {
		return office_one;
	}
	public void setOffice_one(String office_one) {
		this.office_one = office_one;
	}
	public String getOffice_two() {
		return office_two;
	}
	public void setOffice_two(String office_two) {
		this.office_two = office_two;
	}
	public String getOffice_thr() {
		return office_thr;
	}
	public void setOffice_thr(String office_thr) {
		this.office_thr = office_thr;
	}
	public String getOffice_phone() {
		return office_phone;
	}
	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getuserid() {
		return userid;
	}
	public void setuserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getUpdateColmuns() {
		return updateColmuns;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
	}

}
