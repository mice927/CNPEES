package com.operation.vo;

public class ExamInfoUserListVO {
	//table:exam_info_userlist  �������û�������
	private String exam_info_userlist_id;       //����
	private String exam_info_id;       //����ʵ������
	private String exam_info_code;     //����ʵ������
	private String userid;         //�û���Ϣ
	private String score;     //����
	private String paper_status;     //״̬
	
	private String colmuns="exam_info_userlist_id,exam_info_id,exam_info_code,userid,score,paper_status";
	
	public String getColmuns() {
		return colmuns;
	}
	public void setColmuns(String colmuns) {
		this.colmuns = colmuns;
	}
	public String getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(String exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getExam_info_code() {
		return exam_info_code;
	}
	public void setExam_info_code(String exam_info_code) {
		this.exam_info_code = exam_info_code;
	}
	public String getPaper_status() {
		return paper_status;
	}
	public void setPaper_status(String paper_status) {
		this.paper_status = paper_status;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getExam_info_userlist_id() {
		return exam_info_userlist_id;
	}
	public void setExam_info_userlist_id(String exam_info_userlist_id) {
		this.exam_info_userlist_id = exam_info_userlist_id;
	}
	
}
