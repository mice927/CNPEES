package com.springmvc.userlist;

public class UserListVO {
	
	//table:userlist
	private String userid;       //userid����
	private String usercode;  //�û�����   ��½��
	private String username;  //�û�����
	private String password;  //����
	private String office_one;//����
	private String office_two;//����
	private String office_thr;//����
	private String office_phone;  //�칫�绰
	private String phone;       //�ƶ��绰
	private String email;     //����
	private String grade;     //�ʸ�ȼ�
	private String status;    //��ְ״̬
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
