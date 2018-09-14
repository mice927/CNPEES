package com.operation.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.operation.vo.ExamPaperVO;
import com.springmvc.util.StringUtil;

@Repository("examPaperDao")
public class ExamPaperDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * selectExamPaperList
	 * 查询考题信息
	 */
	public List<Map<String, Object>> selectExamPaperList(HttpServletRequest request) {
		
		String exam_paper_code = request.getParameter("exam_paper_code");
		String exam_paper_name = request.getParameter("exam_paper_name");
		String exam_paper_type = request.getParameter("exam_paper_type");
		String item_bank_id = request.getParameter("item_bank_id");
		String exam_paper_id = request.getParameter("exam_paper_id");
		if(StringUtil.isEmpty(item_bank_id)){
			item_bank_id=(String) request.getAttribute("item_bank_id");
		}
		StringBuffer sql=new StringBuffer();
		sql.append("select * from exam_paper where exam_paper_status='01' ");
		if(!StringUtil.isEmpty(item_bank_id)){
			sql.append(" and item_bank_id = '"+item_bank_id+"' ");
		}
		if(!StringUtil.isEmpty(exam_paper_id)){
			sql.append(" and exam_paper_id = '"+exam_paper_id+"' ");
		}
		if(!StringUtil.isEmpty(exam_paper_code)){
			sql.append(" and exam_paper_code = '"+exam_paper_code+"' ");
		}
		if(!StringUtil.isEmpty(exam_paper_name)){
			sql.append(" and exam_paper_name like '%"+exam_paper_name+"%' ");
		}
		if(!StringUtil.isEmpty(exam_paper_type)){
			sql.append(" and exam_paper_type = '"+exam_paper_type+"' ");
		}
		sql.append("order by exam_paper_code ");
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增考题信息
	 * insertExamPaper
	 */
	public void insertExamPaper(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		ExamPaperVO examPaperVO=new ExamPaperVO();
		String tableColmuns=examPaperVO.getColmuns();
		
		String sql = " insert into exam_paper ("+tableColmuns+") values("+values+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新考题信息
	 * updateExamPaper
	 */
	public void updateExamPaper(HttpServletRequest request) {
		String exam_paper_id = request.getParameter("exam_paper_id");
		String values=(String) request.getAttribute("values");
		
		String sql = "update exam_paper set "+values+" where exam_paper_id='"+exam_paper_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新试题状态
	 * updateExamPaperStatus
	 */
	public void updateExamPaperStatus(HttpServletRequest request) {
		String exam_paper_id = request.getParameter("exam_paper_id");
		String exam_paper_status = request.getParameter("exam_paper_status");
		if(StringUtil.isEmpty(exam_paper_status)){
			exam_paper_status="02";
		}
		if(!StringUtil.isEmpty(exam_paper_id)){
			exam_paper_id=exam_paper_id.replaceAll(",", "','");
		}
		String sql = "update exam_paper set exam_paper_status='"+exam_paper_status+"' where exam_paper_id in ('"+exam_paper_id+"')";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * selectExamPaperMaxCode
	 * 查询考题编码最大值
	 */
	public List<Map<String, Object>> selectExamPaperMaxCode(HttpServletRequest request) {
		
		String item_bank_id = request.getParameter("item_bank_id");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.item_bank_code,max(b.exam_paper_code) as exam_paper_code from item_bank a left join exam_paper b on a.item_bank_id=b.item_bank_id where a.item_bank_id='"+item_bank_id+"' ");
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
}
