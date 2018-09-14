package com.springmvc.userlist;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.util.StringUtil;

@Repository("userlistDao")
public class UserListDao {
	
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
	 * 根据用户代码查询用户密码，判断登陆信息
	 * @return
	 */
	public List<Map<String,Object>> selectUserListByCode(HttpServletRequest request){
		String usercode = request.getParameter("usercode");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select password,username,userid from userlist where usercode='"+usercode+"' and status='01' ");

		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 根据用户代码查询用户角色权限
	 * @return
	 */
	public List<Map<String,Object>> selectUserRoleByCode(HttpServletRequest request){
		String usercode = request.getParameter("usercode");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select b.authority_list from userlist a,role_authority b,user_role_authority c where a.usercode='"+usercode+"' and a.userid=c.userid and c.role_authority_id=b.role_authority_id ");

		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectList
	 * 查询用户信息
	 */
	public List<Map<String,Object>> selectUserList(HttpServletRequest request) {
		
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		String userid = request.getParameter("userid");
		String office_one = request.getParameter("office_one");
		String office_two = request.getParameter("office_two");
		String office_thr = request.getParameter("office_thr");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from userlist where status='01' ");
		
		if(!StringUtil.isEmpty(userid)){
			sql.append(" and userid='"+userid+"' ");
		}
		if(!StringUtil.isEmpty(usercode)){
			sql.append(" and usercode='"+usercode+"' ");
		}
		if(!StringUtil.isEmpty(username)){
			sql.append(" and username='"+username+"' ");
		}
		if(!StringUtil.isEmpty(office_one)){
			sql.append(" and office_one='"+office_one+"' ");
		}
		if(!StringUtil.isEmpty(office_two)){
			sql.append(" and office_two='"+office_two+"' ");
		}
		if(!StringUtil.isEmpty(office_thr)){
			sql.append(" and office_thr='"+office_thr+"' ");
		}
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增用户信息
	 * insertUser
	 */
	public void insertUserList(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		UserListVO userlistVo=new UserListVO();
		String tableColmuns=userlistVo.getColmuns();
		
		String sql = " insert into userlist ("+tableColmuns+") values("+values+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新用户信息
	 * updateUser
	 */
	public void updateUserList(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String values=(String) request.getAttribute("values");
		
		String sql = "update userlist set "+values+" where userid='"+userid+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新用户信息状态
	 * updateUser
	 */
	public void updateUserListStatus(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		String status = request.getParameter("status");
		if(StringUtil.isEmpty(status)){
			status="02";
		}
		if(!StringUtil.isEmpty(userid)){
			userid=userid.replaceAll(",", "','");
		}
		String sql = "update userlist set status='"+status+"' where userid in ('"+userid+"')";
		jdbcTemplate.update(sql);
	}

	/**
	 * 删除用户信息
	 * deleteUser
	 */
	public void deleteUserList(int userid) {
		String sql = "delete from userlist where userid=?";
		jdbcTemplate.update(sql, userid);
	}
	
	/**
	 * selectOfficeForTree
	 * 查询部门，拼接成树
	 */
	public List<Map<String, Object>> selectOfficeForTree(HttpServletRequest request) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select a.codename as father_name,a.codevalue as father_value,b.codename as child_name,concat(a.codevalue,'-',b.codevalue) as child_id,c.codename as grandchild_name,concat(a.codevalue,'-',b.codevalue,'-',c.codevalue) as grandchild_id ");
		sql.append("from app_codedata a LEFT JOIN app_codedata b on b.codetype='office_two' and b.father_codevalue=a.codevalue LEFT JOIN app_codedata c on c.codetype='office_thr' and c.father_codevalue=b.codevalue where a.codetype='office_one' ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectExamOffice
	 * 查询部门题库关联
	 */
	public List<Map<String, Object>> selectExamOffice(HttpServletRequest request) {
		String office_id = request.getParameter("office_id");
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.exam_office_id,b.percentage from item_bank a,exam_office b where a.item_bank_id=b.item_bank_id and a.item_bank_status='01' and b.office_id='"+office_id+"' ORDER BY a.item_bank_code ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增部门题库关联
	 * insertUserRoleAuthority
	 */
	public void insertExamOffice(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		
		String sql = " insert into exam_office (exam_office_id,item_bank_id,office_id,percentage) values("+values+")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 删除用户信息
	 * deleteUser
	 */
	public void deleteExamOffice(HttpServletRequest request) {
		String exam_office_id = request.getParameter("exam_office_id");
		exam_office_id=exam_office_id.replace(",", "','");
		String sql = "delete from exam_office where exam_office_id in ('"+exam_office_id+"')";
		jdbcTemplate.update(sql);
	}

}
