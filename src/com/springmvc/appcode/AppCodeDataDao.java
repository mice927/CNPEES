package com.springmvc.appcode;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.util.StringUtil;

@Repository("appCodedataDao")
public class AppCodeDataDao {
	
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
	 * selectCodeValue
	 * 动态查询代码集
	 */
	public List<Map<String, Object>> selectCodeValueOnload(HttpServletRequest request) {
		
		String codetype = request.getParameter("codetype");
		String codevalue = request.getParameter("codevalue");
		StringBuffer sql=new StringBuffer();
		sql.append("select b.codevalue as value,b.codename as label from app_codedata b where b.codetype='"+codetype+"' and b.father_codevalue='"+codevalue+"' ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectCodeIndexOnload
	 * 查询需要初始化的静态代码集
	 */
	public List<Map<String, Object>> selectCodeIndexOnload(String onload) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from app_codeindex where status='01' and onload in ('"+onload+"') ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectCodeValueByS
	 * 获取代码集 名值
	 */
	public List<Map<String, Object>> selectCodeValueByS(String codetype) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select codevalue,codename,codetype,father_codevalue from app_codedata where codetype='"+codetype+"' ");
		
		return jdbcTemplate.queryForList(sql.toString() ); 
	}
	/**
	 * selectCodeData
	 * 查询代码集
	 */
	public List<Map<String, Object>> selectCodeData(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String codevalue = request.getParameter("codevalue");
		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from app_codedata where 1=1 ");
		
		if(!StringUtil.isEmpty(codetype)){
			sql.append(" and codetype = '"+codetype+"' ");
		}
		if(!StringUtil.isEmpty(codevalue)){
			sql.append(" and codevalue = '"+codevalue+"' ");
		}
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectCodeIndex
	 * 查询代码集
	 */
	public List<Map<String, Object>> selectCodeIndex(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String description = request.getParameter("description");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from app_codeindex where status='01' ");
		
		if(!StringUtil.isEmpty(codetype)){
			sql.append(" and codetype = '"+codetype+"' ");
		}
		if(!StringUtil.isEmpty(description)){
			sql.append(" and description like '%"+description+"%' ");
		}
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增代码信息
	 * insertCodeIndex
	 */
	public void insertCodeIndex(HttpServletRequest request) {
		AppCodeIndexVO appcodeIndexVO=new AppCodeIndexVO();
		String tableColmuns=appcodeIndexVO.getColmuns();
		String values=(String) request.getAttribute("values");
		
		String sql = " insert into app_codeindex ("+tableColmuns+") values("+values+")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新代码信息
	 * updateCodeIndex
	 */
	public void updateCodeIndex(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String values=(String) request.getAttribute("values");
		
		String sql = "update app_codeindex set "+values+" where codetype='"+codetype+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新代码信息状态
	 * updateUser
	 */
	public void updateCodeIndexStatus(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String status = request.getParameter("status");
		if(StringUtil.isEmpty(status)){
			status="02";
		}
		if(!StringUtil.isEmpty(codetype)){
			codetype=codetype.replaceAll(",", "','");
		}
		String sql = "update app_codeindex set status='"+status+"' where codetype in ('"+codetype+"')";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 新增代码集信息
	 * insertCodeIndex
	 */
	public void insertCodeData(HttpServletRequest request) {
		AppCodeDataVO appcodeDataVO=new AppCodeDataVO();
		String tableColmuns=appcodeDataVO.getColmuns();
		String values=(String) request.getAttribute("values");
		
		String sql = " insert into app_codedata ("+tableColmuns+") values("+values+")";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新代码集信息
	 * updateCodeIndex
	 */
	public void updateCodeData(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String codevalue = request.getParameter("codevalue");
		String values=(String) request.getAttribute("values");
		
		String sql = "update app_codedata set "+values+" where codetype='"+codetype+"' and codevalue='"+codevalue+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 删除代码集信息
	 * deleteUser
	 */
	public void deleteCodeData(HttpServletRequest request) {
		String codetype = request.getParameter("codetype");
		String codevalue = request.getParameter("codevalue");
		if(!StringUtil.isEmpty(codetype)){
			String codetypeA[]=codetype.split(",");
			String codevalueA[]=codevalue.split(",");
			for(int i=0;i<codetypeA.length;i++){
				String sql="delete from app_codedata where codetype='"+codetypeA[i]+"' and codevalue='"+codevalueA[i]+"'";
				jdbcTemplate.update(sql);
			}
		}
	}

}
