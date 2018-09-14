package com.springmvc.roleAuthority;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.util.StringUtil;

@Repository("roleAuthorityDao")
public class RoleAuthorityDao {
	
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
	 * selectRoleAuthority
	 * 查询权限列表
	 */
	public List<Map<String,Object>> selectRoleAuthority(HttpServletRequest request) {
		
		String role_description = request.getParameter("role_description");
		String role_authority_id = request.getParameter("role_authority_id");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from role_authority where status='01' ");
		
		if(!StringUtil.isEmpty(role_authority_id)){
			sql.append(" and role_authority_id='"+role_authority_id+"' ");
		}
		if(!StringUtil.isEmpty(role_description)){
			sql.append(" and role_description like '%"+role_description+"%' ");
		}
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增角色信息
	 * insertRoleAuthority
	 */
	public void insertRoleAuthority(HttpServletRequest request) {
		RoleAuthorityVO roleAuthorityVO=new RoleAuthorityVO();
		String tableColmuns=roleAuthorityVO.getColmuns();
		String values=(String) request.getAttribute("values");
		
		String sql = " insert into role_authority ("+tableColmuns+") values("+values+")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新角色信息
	 * updateRoleAuthority
	 */
	public void updateRoleAuthority(HttpServletRequest request) {
		String role_authority_id = request.getParameter("role_authority_id");
		String values=(String) request.getAttribute("values");
		
		String sql = "update role_authority set "+values+" where role_authority_id='"+role_authority_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新角色信息状态
	 * updateUser
	 */
	public void updateRoleAuthorityStatus(HttpServletRequest request) {
		String role_authority_id = request.getParameter("role_authority_id");
		String status = request.getParameter("status");
		if(StringUtil.isEmpty(status)){
			status="02";
		}
		if(!StringUtil.isEmpty(role_authority_id)){
			role_authority_id=role_authority_id.replaceAll(",", "','");
		}
		String sql = "update role_authority set status='"+status+"' where role_authority_id in ('"+role_authority_id+"')";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * selectRoleAuthority
	 * 查询权限列表
	 */
	public List<Map<String,Object>> selectUserRoleAuthority(HttpServletRequest request) {
		
		String role_authority_id = request.getParameter("role_authority_id");
		if(StringUtil.isEmpty(role_authority_id)){
			role_authority_id=(String) request.getAttribute("role_authority_id");
		}
		String userid=(String) request.getAttribute("userid");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select b.*,a.user_roal_authority_id,a.role_authority_id from user_role_authority a,userlist b where a.role_authority_id='"+role_authority_id+"' and a.userid=b.userid and b.status='01' ");
		if(!StringUtil.isEmpty(userid)){
			sql.append(" and a.userid='"+userid+"' ");
		}
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增授权
	 * insertUserRoleAuthority
	 */
	public void insertUserRoleAuthority(HttpServletRequest request) {
		UserRoleAuthorityVO userRoleauthorityVO=new UserRoleAuthorityVO();
		String tableColmuns=userRoleauthorityVO.getColmuns();
		String values=(String) request.getAttribute("values");
		
		String sql = " insert into user_role_authority ("+tableColmuns+") values("+values+")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 取消授权
	 * deleteUser
	 */
	public void deleteUserRoleAuthority(HttpServletRequest request) {
		String user_roal_authority_id = request.getParameter("user_roal_authority_id");
		String role_authority_id = request.getParameter("role_authority_id");
		String userid = request.getParameter("userid");
		if(!StringUtil.isEmpty(user_roal_authority_id)){
			String user_roal_authority_idA[]=user_roal_authority_id.split(",");
			String role_authority_idA[]=role_authority_id.split(",");
			String useridA[]=userid.split(",");
			for(int i=0;i<user_roal_authority_idA.length;i++){
				String sql="delete from user_role_authority where role_authority_id='"+role_authority_idA[i]+"' and userid='"+useridA[i]+"'";
				jdbcTemplate.update(sql);
			}
		}
	}

}
