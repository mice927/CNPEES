package com.operation.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.operation.vo.ExamInfoUserListVO;
import com.springmvc.util.StringUtil;

@Repository("examInfoUserlistDao")
public class ExamInfoUserlistDao {
	
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
	public List<Map<String, Object>> selectExamInfoUserlist(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		String exam_info_id=(String) request.getAttribute("exam_info_id");
		String userid=(String) request.getAttribute("userid");
		String exam_info_userlist_id=request.getParameter("exam_info_userlist_id");
		sql.append("select a.*,b.username,b.usercode from exam_info_userlist a,userlist b where a.userid=b.userid ");
		if(!StringUtil.isEmpty(exam_info_userlist_id)){
			sql.append("and a.exam_info_userlist_id='"+exam_info_userlist_id+"' ");
		}
		if(!StringUtil.isEmpty(exam_info_id)){
			sql.append("and a.exam_info_id='"+exam_info_id+"' ");
		}
		if(!StringUtil.isEmpty(userid)){
			sql.append("and a.userid='"+userid+"' ");
		}
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增测试实例
	 * insertItemBank
	 */
	public void insertExamInfoUserlist(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		ExamInfoUserListVO examInfoUserListVO=new ExamInfoUserListVO();
		String tableColmuns=examInfoUserListVO.getColmuns();
		
		String sql = " insert into exam_info_userlist ("+tableColmuns+") values("+values+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新测试实例状态
	 * updateExamPaperSolution
	 */
	public void updateExamInfoStatus(HttpServletRequest request) {
		String exam_info_userlist_id=(String) request.getAttribute("exam_info_userlist_id");
		String paper_status=(String) request.getAttribute("paper_status");
		String sql = "update exam_info_userlist set paper_status='"+paper_status+"' where exam_info_userlist_id = '"+exam_info_userlist_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 批量增加用户考题实例
	 * insertItemBank
	 */
	public void insertExamPaperUserlist(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		
		String exam_paper_id=(String) request.getAttribute("exam_paper_id");
		String exam_info_userlist_id=(String) request.getAttribute("exam_info_userlist_id");
		
		sql.append("INSERT into exam_paper_userlist(exam_paper_userlist_id,exam_paper_id,exam_paper_code,exam_info_userlist_id,exam_paper_solution,score,right_wrong) ");
		sql.append("select UUID_SHORT(),exam_paper_id,exam_paper_code,'"+exam_info_userlist_id+"','','','' ");
		sql.append("from exam_paper where exam_paper_id in ('"+exam_paper_id+"') ORDER BY exam_paper_code ");
		jdbcTemplate.update(sql.toString());
	}
	
	/**
	 * selectExamPaperUserlist
	 * 查询测试实例信息
	 */
	public List<Map<String, Object>> selectExamPaperUserlist(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		String exam_info_userlist_id=(String) request.getAttribute("exam_info_userlist_id");
		
		sql.append("select b.exam_paper_userlist_id,d.username,d.usercode,a.score,a.paper_status,c.exam_paper_code,c.exam_paper_name,c.exam_paper_tab,c.exam_paper_type,b.exam_paper_solution,b.right_wrong,b.score as test_score,concat(e1.codename,e2.codename,if(ISNULL(e3.codename),'',e3.codename)) as office ");
		sql.append("from exam_info_userlist a,exam_paper_userlist b,exam_paper c,userlist d LEFT JOIN app_codedata e1 on e1.codetype='office_one' and d.office_one=e1.codevalue ");
		sql.append("LEFT JOIN app_codedata e2 on e2.codetype='office_two' and d.office_two=e2.codevalue LEFT JOIN app_codedata e3 on e3.codetype='office_thr' and d.office_thr=e3.codevalue ");
		sql.append("where a.exam_info_userlist_id='"+exam_info_userlist_id+"' and a.exam_info_userlist_id=b.exam_info_userlist_id and b.exam_paper_id=c.exam_paper_id and a.userid=d.userid ");
		sql.append("ORDER BY c.exam_paper_type,c.exam_paper_code ");
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 更新用户做题试题答案
	 * updateExamPaperSolution
	 */
	public void updateExamPaperSolution(String exam_paper_solution,String exam_paper_userlist_id) {
		String sql = "update exam_paper_userlist set exam_paper_solution='"+exam_paper_solution+"' where exam_paper_userlist_id = '"+exam_paper_userlist_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 提交后系统自动打分
	 * updateExamPaperSolution
	 */
	public void updateExamPaperScore(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		String exam_info_userlist_id=(String) request.getAttribute("exam_info_userlist_id");
		
		sql.append("update exam_paper_userlist INNER JOIN( ");
		sql.append("select a.exam_paper_userlist_id,case when a.exam_paper_solution=b.exam_paper_solution then 'R' else 'W' end right_wrong, ");
		sql.append("case when a.exam_paper_solution=b.exam_paper_solution and (b.exam_paper_score='' or b.exam_paper_score is null) then '1' when a.exam_paper_solution=b.exam_paper_solution and (b.exam_paper_score!='' and b.exam_paper_score is not null) then b.exam_paper_score else '0' end score ");
		sql.append("from exam_paper_userlist a,exam_paper b where a.exam_info_userlist_id='"+exam_info_userlist_id+"' and a.exam_paper_id=b.exam_paper_id and b.exam_paper_type!='05' ");
		sql.append(") a on exam_paper_userlist.exam_paper_userlist_id=a.exam_paper_userlist_id ");
		sql.append("set exam_paper_userlist.right_wrong=a.right_wrong,exam_paper_userlist.score=a.score ");
		
		jdbcTemplate.update(sql.toString());
	}
	
	/**
	 * 提交后系统自动打分--总分
	 * updateExamPaperTotalScore
	 */
	public void updateExamPaperTotalScore(HttpServletRequest request) {
		StringBuffer sql=new StringBuffer();
		String exam_info_userlist_id=(String) request.getAttribute("exam_info_userlist_id");
		
		sql.append("update exam_info_userlist INNER JOIN( ");
		sql.append("select sum(score) as score,exam_info_userlist_id from exam_paper_userlist where exam_info_userlist_id='"+exam_info_userlist_id+"' )a on exam_info_userlist.exam_info_userlist_id=a.exam_info_userlist_id ");
		sql.append("set exam_info_userlist.score=a.score ");
		
		jdbcTemplate.update(sql.toString());
	}
}
