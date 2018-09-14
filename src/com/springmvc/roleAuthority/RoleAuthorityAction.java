package com.springmvc.roleAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.springmvc.util.DaoUtil;
import com.springmvc.util.StringUtil;

@Controller
public class RoleAuthorityAction {
	@Autowired
	@Qualifier("roleAuthorityDao")
	private RoleAuthorityDao roleAuthorityDao;
	private RoleAuthorityVO roleAuthorityVO=new RoleAuthorityVO();
	
	/** 
	 * txnra00001
	 * 角色列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00001.do")
	public ModelAndView txnra00001(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=roleAuthorityVO.getColmuns();
		
		List<Map<String, Object>> sqlList = roleAuthorityDao.selectRoleAuthority(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		
		model.put("dataList", dataStr);
		return new ModelAndView("app/userlist/queryRoleAuthority", model);
	}
	
	/**
	 * txnra00002
	 * 新增/修改 角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00002.do")
	public ModelAndView txnra00002(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		
		String tableColmuns=roleAuthorityVO.getColmuns();
		String role_authority_id = request.getParameter("role_authority_id");
		if(StringUtil.isEmpty(role_authority_id)){
			String values=DaoUtil.insertTableValues(tableColmuns,role_authority_id, request);
			
			request.setAttribute("values", values);
			roleAuthorityDao.insertRoleAuthority(request);
			model.put("statusCode", 200);
		}
		else{
			String tableUpdateColmuns=roleAuthorityVO.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			
			request.setAttribute("values", values);
			roleAuthorityDao.updateRoleAuthority(request);
			model.put("statusCode", 200);
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnra00003
	 * 查看角色信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00003.do")
	public ModelAndView txnra00003(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=roleAuthorityVO.getColmuns();
		
		List<Map<String, Object>> sqlList = roleAuthorityDao.selectRoleAuthority(request);
		model=DaoUtil.getDataMap(tableColmuns, sqlList);
		
		return new ModelAndView("app/userlist/editRoleAuthority", model);
	}
	
	/**
	 * txnra00004
	 * 修改角色信息状态为无效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00004.do")
	public ModelAndView txnra00004(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		roleAuthorityDao.updateRoleAuthorityStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/** 
	 * txnra00005
	 * 授权维护
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00005.do")
	public ModelAndView txnra00005(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns="user_roal_authority_id,role_authority_id,userid,usercode,username,office_one,grade";
		String role_authority_id = request.getParameter("role_authority_id");
		List<Map<String, Object>> sqlList = roleAuthorityDao.selectUserRoleAuthority(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		
		model.put("dataList", dataStr);
		model.put("role_authority_id", role_authority_id);
		return new ModelAndView("app/userlist/queryUserRole", model);
	}
	
	/**
	 * txnra00006
	 * 新增授权
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00006.do")
	public ModelAndView txnra00006(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String role_authority_id = request.getParameter("role_authority_id");
		String userid = request.getParameter("userid");
		if(!StringUtil.isEmpty(userid)){
			String useridA[]=userid.split(",");
			for(int i=0;i<useridA.length;i++){
				String id=DaoUtil.getUUID();
				String values="'"+id+"','"+useridA[i]+"','"+role_authority_id+"'";
				
				request.setAttribute("values", values);
				roleAuthorityDao.insertUserRoleAuthority(request);
			}
		}
		
		model.put("statusCode", 200);
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnra00007
	 * 删除用户权限
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnra00007.do")
	public ModelAndView txnra00007(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		roleAuthorityDao.deleteUserRoleAuthority(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
}
