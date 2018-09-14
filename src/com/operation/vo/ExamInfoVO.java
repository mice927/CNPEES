package com.operation.vo;

public class ExamInfoVO {
	//table:exam_info  测试实例
	private String exam_info_id;       //测试实例主键
	private String exam_info_code;     //测试实例编码
	private String start_time;     //起始时间
	private String end_time;     //结束时间
	private String exam_info_status;     //状态
	private String exam_info_note;     //备注
	
	private String colmuns="exam_info_id,exam_info_code,start_time,end_time,exam_info_status,exam_info_note";
	private String updateColmuns="start_time,end_time,exam_info_note";
	
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
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getExam_info_status() {
		return exam_info_status;
	}
	public void setExam_info_status(String exam_info_status) {
		this.exam_info_status = exam_info_status;
	}
	public String getExam_info_note() {
		return exam_info_note;
	}
	public void setExam_info_note(String exam_info_note) {
		this.exam_info_note = exam_info_note;
	}
	
	
}
