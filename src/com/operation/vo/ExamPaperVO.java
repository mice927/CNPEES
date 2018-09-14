package com.operation.vo;

public class ExamPaperVO {
	//table:exam_paper  考题
	private String exam_paper_id;       //考题主键
	private String item_bank_id;       //题库主键
	private String exam_paper_code;     //考题编码
	private String exam_paper_name;     //题目
	private String exam_paper_solution;     //答案
	private String exam_paper_tab;      //选项
	private String exam_paper_type;      //考题类型
	private String exam_paper_status;     //考题状态
	private String exam_paper_score;     //考题分值
	private String exam_paper_wbs;     //WBS标识
	
	private String colmuns="exam_paper_id,item_bank_id,exam_paper_code,exam_paper_name,exam_paper_solution,exam_paper_tab,exam_paper_type,exam_paper_status,exam_paper_score,exam_paper_wbs";
	private String updateColmuns="exam_paper_name,exam_paper_solution,exam_paper_tab,exam_paper_score,exam_paper_wbs";
	private String queryColmuns="exam_paper_id,item_bank_id,exam_paper_code,exam_paper_name,exam_paper_type,exam_paper_score,exam_paper_wbs";
	
	public String getQueryColmuns() {
		return queryColmuns;
	}
	public void setQueryColmuns(String queryColmuns) {
		this.queryColmuns = queryColmuns;
	}
	public String getItem_bank_id() {
		return item_bank_id;
	}
	public void setItem_bank_id(String item_bank_id) {
		this.item_bank_id = item_bank_id;
	}
	public String getExam_paper_id() {
		return exam_paper_id;
	}
	public void setExam_paper_id(String exam_paper_id) {
		this.exam_paper_id = exam_paper_id;
	}
	public String getExam_paper_code() {
		return exam_paper_code;
	}
	public void setExam_paper_code(String exam_paper_code) {
		this.exam_paper_code = exam_paper_code;
	}
	public String getExam_paper_name() {
		return exam_paper_name;
	}
	public void setExam_paper_name(String exam_paper_name) {
		this.exam_paper_name = exam_paper_name;
	}
	public String getExam_paper_solution() {
		return exam_paper_solution;
	}
	public void setExam_paper_solution(String exam_paper_solution) {
		this.exam_paper_solution = exam_paper_solution;
	}
	public String getExam_paper_type() {
		return exam_paper_type;
	}
	public void setExam_paper_type(String exam_paper_type) {
		this.exam_paper_type = exam_paper_type;
	}
	public String getExam_paper_status() {
		return exam_paper_status;
	}
	public void setExam_paper_status(String exam_paper_status) {
		this.exam_paper_status = exam_paper_status;
	}
	public void setUpdateColmuns(String updateColmuns) {
		this.updateColmuns = updateColmuns;
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
	public String getExam_paper_tab() {
		return exam_paper_tab;
	}
	public void setExam_paper_tab(String exam_paper_tab) {
		this.exam_paper_tab = exam_paper_tab;
	}
	public String getExam_paper_score() {
		return exam_paper_score;
	}
	public void setExam_paper_score(String exam_paper_score) {
		this.exam_paper_score = exam_paper_score;
	}
	public String getExam_paper_wbs() {
		return exam_paper_wbs;
	}
	public void setExam_paper_wbs(String exam_paper_wbs) {
		this.exam_paper_wbs = exam_paper_wbs;
	}
	

}
