package com.operation.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.operation.vo.ExamInfoVO;
import com.springmvc.util.StringUtil;

@Repository("examInfoDao")
public class ExamInfoDao {
	
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
	 * selectExamInfoList
	 * 查询测试实例信息
	 */
	public List<Map<String, Object>> selectExamInfoList(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		String today=StringUtil.getCurrentDate("yyyyMMdd");
		sql.append("select * from exam_info where exam_info_status='01' and '"+today+"'<=end_time ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增测试实例
	 * insertItemBank
	 */
	public void insertExamInfo(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		ExamInfoVO examInfoVO=new ExamInfoVO();
		String tableColmuns=examInfoVO.getColmuns();
		
		String sql = " insert into exam_info ("+tableColmuns+") values("+values+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 关闭测试实例
	 * updateExamInfoStatus
	 */
	public void updateExamInfoStatus(HttpServletRequest request) {
		String exam_info_id = request.getParameter("exam_info_id");
		String today=StringUtil.getCurrentDate("yyyyMMdd");
		
		String sql = "update exam_info set exam_info_status='02',end_time='"+today+"' where exam_info_id = '"+exam_info_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * selectExamInfoMaxCode
	 * 查询测试实例编码最大值
	 */
	public List<Map<String, Object>> selectExamInfoMaxCode(HttpServletRequest request) {
		
		String today=StringUtil.getCurrentDate("yyyyMMdd");
		StringBuffer sql=new StringBuffer();
		sql.append("select max(exam_info_code) as exam_info_code from exam_info where exam_info_code like '"+today.substring(0, 4)+"%' ");
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectExamInfoForTree
	 * 查询测试实例，拼接成树
	 */
	public List<Map<String, Object>> selectExamInfoForTree(HttpServletRequest request) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select concat(substring(exam_info_code,1,4),'年') as father_name,substring(exam_info_code,1,4) as father_value,concat(exam_info_code,'期') as child_name,exam_info_id as child_id ");
		sql.append("from exam_info ORDER BY exam_info_code desc ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
}
