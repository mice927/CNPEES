package com.operation.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.operation.vo.ItemBankVO;
import com.springmvc.util.StringUtil;

@Repository("itemBankDao")
public class ItemBankDao {
	
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
	 * selectItemBankList
	 * 查询题库信息
	 */
	public List<Map<String, Object>> selectItemBankList(HttpServletRequest request) {
		
		String item_bank_code = request.getParameter("item_bank_code");
		String item_bank_codes = request.getParameter("item_bank_codes");
		String item_bank_name = request.getParameter("item_bank_name");
		String item_bank_description = request.getParameter("item_bank_description");
		String item_bank_id = request.getParameter("item_bank_id");
		StringBuffer sql=new StringBuffer();
		sql.append("select * from item_bank where item_bank_status='01' ");
		if(!StringUtil.isEmpty(item_bank_id)){
			sql.append(" and item_bank_id = '"+item_bank_id+"' ");
		}
		if(!StringUtil.isEmpty(item_bank_code)){
			sql.append(" and item_bank_code like '%"+item_bank_code+"%' ");
		}
		if(!StringUtil.isEmpty(item_bank_codes)){
			sql.append(" and item_bank_code = '"+item_bank_codes+"' ");
		}
		if(!StringUtil.isEmpty(item_bank_name)){
			sql.append(" and item_bank_name like '%"+item_bank_name+"%' ");
		}
		if(!StringUtil.isEmpty(item_bank_description)){
			sql.append(" and item_bank_description like '%"+item_bank_description+"%' ");
		}
		sql.append("order by item_bank_code ");
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * 新增题库信息
	 * insertItemBank
	 */
	public void insertItemBank(HttpServletRequest request) {
		String values=(String) request.getAttribute("values");
		ItemBankVO itemBankVO=new ItemBankVO();
		String tableColmuns=itemBankVO.getColmuns();
		
		String sql = " insert into item_bank ("+tableColmuns+") values("+values+")";
		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新题库信息
	 * updateItemBank
	 */
	public void updateItemBank(HttpServletRequest request) {
		String item_bank_id = request.getParameter("item_bank_id");
		String values=(String) request.getAttribute("values");
		
		String sql = "update item_bank set "+values+" where item_bank_id='"+item_bank_id+"'";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 更新题库状态
	 * updateItemBankStatus
	 */
	public void updateItemBankStatus(HttpServletRequest request) {
		String item_bank_id = request.getParameter("item_bank_id");
		String item_bank_status = request.getParameter("item_bank_status");
		if(StringUtil.isEmpty(item_bank_status)){
			item_bank_status="02";
		}
		if(!StringUtil.isEmpty(item_bank_id)){
			item_bank_id=item_bank_id.replaceAll(",", "','");
		}
		String sql = "update item_bank set item_bank_status='"+item_bank_status+"' where item_bank_id in ('"+item_bank_id+"')";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * selectItemBankForTree
	 * 查询考题，拼接成树
	 */
	public List<Map<String, Object>> selectItemBankForTree(HttpServletRequest request) {
		
		StringBuffer sql=new StringBuffer();
		sql.append("select a.codename as father_name,a.codevalue as father_value,concat(b.item_bank_code,' ',b.item_bank_name) as child_name,b.item_bank_id as child_id ");
		sql.append("from app_codedata a LEFT JOIN item_bank b on a.codevalue=b.item_bank_type and b.item_bank_status='01' where a.codetype='item_bank_type' ORDER BY a.codevalue,b.item_bank_code  ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}
	
	/**
	 * selectItemBankForUserlist
	 * 根据用户信息查询考题
	 */
	public List<Map<String, Object>> selectItemBankForUserlist(HttpServletRequest request) {
		String userid=request.getParameter("userid");
		StringBuffer sql=new StringBuffer();
		sql.append("select a.username,a.usercode,c.* from userlist a,exam_office b,item_bank c ");
		sql.append("where a.userid='"+userid+"' and (concat(a.office_one,'-',a.office_two) =b.office_id or concat(a.office_one,'-',a.office_two,'-',a.office_thr) =b.office_id) and b.item_bank_id=c.item_bank_id and c.item_bank_status='01'  ORDER BY item_bank_code ");
		
		return jdbcTemplate.queryForList(sql.toString() );
	}

}
