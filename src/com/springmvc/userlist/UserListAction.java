package com.springmvc.userlist;

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
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.springmvc.util.DaoUtil;
import com.springmvc.util.JointMainList;
import com.springmvc.util.StringUtil;

@Controller
public class UserListAction extends MultiActionController {
	
	@Autowired
	@Qualifier("userlistDao")
	private UserListDao userlistDao;

	/**
	 * txn99999
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99999.do")
	public ModelAndView txn99999(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String password = request.getParameter("password");
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		//��ѯ�û���Ϣ  �жϸ��û��Ƿ����
		List<Map<String,Object>> userVOL = userlistDao.selectUserListByCode(request);
		if (userVOL != null&&userVOL.size()>0) {
			//��ѯ�û���Ϣ  �жϸ��û��Ƿ�����ӽ�ɫ
			String authority_list="";
			List<Map<String,Object>> userRoleL = userlistDao.selectUserRoleByCode(request);
			for(int i=0;i<userRoleL.size();i++){
				authority_list=authority_list+","+(String)userRoleL.get(i).get("authority_list");
			}
			
			String passwordS=(String) userVOL.get(0).get("password");
			String usernameS=(String) userVOL.get(0).get("username");
			String userid=(String) userVOL.get(0).get("userid");
			String usercode=(String) userVOL.get(0).get("usercode");
			if(passwordS.equals(password)){
				String showL=JointMainList.getMainList(authority_list);
				model.put("username", usernameS);
				model.put("showL", showL);
				request.getSession().setAttribute("userid", userid);
				request.getSession().setAttribute("usercode", usercode);
				return new ModelAndView("main", model);
			}
			else{
				model.put("error", "���벻��ȷ");
				return new ModelAndView("index", model);
			}
		} else {
			model.put("error", "�û���������");
			return new ModelAndView("index", model);
		}
	}
	
	/**
	 * txn99990
	 * �û��б��ѯ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99990.do")
	public ModelAndView txn99990(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		UserListVO userlistVo=new UserListVO();
		String tableColmuns=userlistVo.getColmuns();
		
		List<Map<String, Object>> sqlList = userlistDao.selectUserList(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		
		model.put("dataList", dataStr);
		
		String check= request.getParameter("check");
		String role_authority_id= request.getParameter("role_authority_id");
		model.put("check", check);
		model.put("role_authority_id", role_authority_id);
		return new ModelAndView("app/userlist/queryUserlist", model);
	}
	
	/**
	 * txn99991
	 * ����/�޸� �û���Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99991.do")
	public ModelAndView txn99991(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String userid = request.getParameter("userid");
		UserListVO userlistVo=new UserListVO();
		
		if(StringUtil.isEmpty(userid)){
			String tableColmuns=userlistVo.getColmuns();
			userid=DaoUtil.getUUID();
			String values=DaoUtil.insertTableValues(tableColmuns,userid, request);
			request.setAttribute("values", values);
			
			userlistDao.insertUserList(request);
			model.put("statusCode", 200);
			model.put("userid", userid);
		}
		else{
			String tableUpdateColmuns=userlistVo.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			request.setAttribute("values", values);
			
			userlistDao.updateUserList(request);
			model.put("statusCode", 200);
			model.put("userid", userid);
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txn99992
	 * У����û��Ƿ��Ѿ�����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99992.do")
	public ModelAndView txn99992(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		//��ѯ�û���Ϣ  �жϸ��û��Ƿ����
		List<Map<String,Object>> userVOL = userlistDao.selectUserListByCode(request);
		if (userVOL != null&&userVOL.size()>0) {   //�������û���Ϣʱ
			model.put("statusCode", "200");
			model.put("message", "���û�����");
		}
		else{
			model.put("statusCode", "200");
			model.put("message", "");
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txn99993
	 * �鿴�û���Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99993.do")
	public ModelAndView txn99993(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		UserListVO userListVO=new UserListVO();
		String tableTitle=userListVO.getColmuns();
		
		List<Map<String, Object>> sqlList = userlistDao.selectUserList(request);
		model=DaoUtil.getDataMap(tableTitle, sqlList);
		
		return new ModelAndView("app/userlist/editUserList", model);
	}
	
	/**
	 * txn99994
	 * �޸��û���Ϣ״̬Ϊ��Ч
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txn99994.do")
	public ModelAndView txn99994(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		userlistDao.updateUserListStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "��ɾ��!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	
	
	public UserListDao getUserlistDao() {
		return userlistDao;
	}

	public void setUserlistDao(UserListDao userlistDao) {
		this.userlistDao = userlistDao;
	}

}
