package com.operation.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.operation.dao.ExamInfoDao;
import com.operation.dao.ExamInfoUserlistDao;
import com.operation.dao.ExamPaperDao;
import com.operation.dao.ItemBankDao;
import com.operation.vo.ExamInfoUserListVO;
import com.operation.vo.ExamInfoVO;
import com.springmvc.roleAuthority.RoleAuthorityDao;
import com.springmvc.util.Constants;
import com.springmvc.util.DaoUtil;
import com.springmvc.util.StringUtil;

@Controller
public class ExamInfoAction extends MultiActionController {
	
	public static Log log = LogFactory.getLog(ItemBankAction.class);
	
	@Autowired
	@Qualifier("examInfoDao")
	private ExamInfoDao examInfoDao;
	@Autowired
	@Qualifier("examInfoUserlistDao")
	private ExamInfoUserlistDao examInfoUserlistDao;
	@Autowired
	@Qualifier("itemBankDao")
	private ItemBankDao itemBankDao;
	@Autowired
	@Qualifier("examPaperDao")
	private ExamPaperDao examPaperDao;
	@Autowired
	@Qualifier("roleAuthorityDao")
	private RoleAuthorityDao roleAuthorityDao;
	private ExamInfoVO examInfoVO= new ExamInfoVO();
	private ExamInfoUserListVO examInfoUserListVO= new ExamInfoUserListVO();
	
	/**
	 * 查询测试实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnea00001.do")
	public ModelAndView txnea00001(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String userid=(String) request.getSession().getAttribute("userid");
		request.setAttribute("userid", userid);
		
		String tableColmuns=examInfoVO.getColmuns();
		List<Map<String, Object>> sqlList = examInfoDao.selectExamInfoList(request);
		if(sqlList!=null&&sqlList.size()>0){   //如果当前为测试期间，则获取测试信息，否则提示不存在测试
			model=DaoUtil.getDataMap(tableColmuns, sqlList);
			String exam_info_id=(String) sqlList.get(0).get("exam_info_id");
			request.setAttribute("exam_info_id", exam_info_id);
			
			List<Map<String, Object>> sqlListU = examInfoUserlistDao.selectExamInfoUserlist(request);
			if(sqlListU!=null&&sqlListU.size()>0){   //如果当前用户已存在测试题，则显示测试题
				String exam_info_userlist_id=(String) sqlListU.get(0).get("exam_info_userlist_id");
				model.put("exam_info_userlist_id", exam_info_userlist_id); 
				model.put("userid", userid); 
				
				request.setAttribute("exam_info_userlist_id", exam_info_userlist_id);
				List<Map<String, Object>> sqlListPU = examInfoUserlistDao.selectExamPaperUserlist(request);
				if(sqlListPU!=null&&sqlListPU.size()>0){
					model.put("paper_str", DaoUtil.jointPaper(sqlListPU)); 
				}
			}
		}
		else{
			
			List<Map<String, Object>> sqlListM = examInfoDao.selectExamInfoMaxCode(request);
			String maxExam_info_code=(String) sqlListM.get(0).get("exam_info_code");
			
			if(!StringUtil.isEmpty(maxExam_info_code)){
				String num=(Integer.parseInt(maxExam_info_code.substring(4, 7))+1)+"";
				for(;num.length()<3;){
					num="0"+num;
				}
				maxExam_info_code=StringUtil.getCurrentDate("yyyyMMdd").substring(0, 4)+num;
			}
			else{
				maxExam_info_code=StringUtil.getCurrentDate("yyyyMMdd").substring(0, 4)+"001";
			}
			model.put("exam_info_code", maxExam_info_code); 
		}
		
		//判断按钮权限
		request.setAttribute("role_authority_id", Constants.ROLE_AUTHORITY_ZKG);//角色为主考官
		List<Map<String, Object>> sqlListR = roleAuthorityDao.selectUserRoleAuthority(request);
		if(sqlListR!=null&&sqlListR.size()>0){
			model.put("button_role", "true"); 
		}
		
		return new ModelAndView("app/examinfo/editexaminfo", model);
	}
	
	/**
	 * txnea00002
	 * 增加/修改 题库信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnea00002.do")
	public ModelAndView txnea00002(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String exam_info_id = DaoUtil.getUUID();
		
		String tableColmuns=examInfoVO.getColmuns();
		String values=DaoUtil.insertTableValues(tableColmuns,exam_info_id, request);
		request.setAttribute("values", values);
		
		examInfoDao.insertExamInfo(request);
		model.put("statusCode", 200);
		model.put("exam_info_id", exam_info_id);
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnea00003
	 * 修改题库状态为无效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnea00003.do")
	public ModelAndView txnea00003(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		examInfoDao.updateExamInfoStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已结束!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * 查看历史测试实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnea00004.do")
	public ModelAndView txnea00004(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		List<Map<String, Object>> sqlList = examInfoDao.selectExamInfoForTree(request);
		String treeStr=DaoUtil.getDataListForTree(sqlList,"txnea00007.do","layout-examinfo");
		model.put("treeStr", treeStr);
		
		return new ModelAndView("app/examinfo/queryexaminfo", model);
	}
	
	/**
	 * txnea00005
	 * 产生用户试题
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnea00005.do")
	public ModelAndView txnea00005(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		List<Map<String, Object>> userItemBankList = itemBankDao.selectItemBankForUserlist(request);
		if(userItemBankList!=null&&userItemBankList.size()>0){
			String randomDataList="";
			//获取试题
			for(int i=0;i<userItemBankList.size();i++){
				String item_bank_id=(String) userItemBankList.get(i).get("item_bank_id");
				String item_bank_percent=(String) userItemBankList.get(i).get("item_bank_percent");
				request.setAttribute("item_bank_id", item_bank_id);
				List<Map<String, Object>> paperList = examPaperDao.selectExamPaperList(request);
				if(paperList!=null&&paperList.size()>0){
					randomDataList=randomDataList+DaoUtil.getRandomDataList(paperList, "exam_paper_id", paperList.size(), Integer.parseInt(item_bank_percent))+",";
				}
			}
			
			randomDataList=randomDataList.substring(0, randomDataList.length()-1);
			
			if(randomDataList.split(",").length!=102){
				model.put("message", "该用户所在部门配置题库数量有误，请联系管理员调整！");
			}
			else{
				//添加新数据到测试实例中
				String exam_info_userlist_id = DaoUtil.getUUID();
				String tableColmuns=examInfoUserListVO.getColmuns();
				String values=DaoUtil.insertTableValues(tableColmuns,exam_info_userlist_id, request);
				request.setAttribute("values", values);
				examInfoUserlistDao.insertExamInfoUserlist(request);
				
				request.setAttribute("exam_info_userlist_id", exam_info_userlist_id);
				request.setAttribute("exam_paper_id", randomDataList.replace(",", "','"));
				examInfoUserlistDao.insertExamPaperUserlist(request);
				
				model.put("exam_info_userlist_id", exam_info_userlist_id);
				model.put("message", "添加成功！");
				
				List<Map<String, Object>> sqlListPU = examInfoUserlistDao.selectExamPaperUserlist(request);
				if(sqlListPU!=null&&sqlListPU.size()>0){
					model.put("paper_str", DaoUtil.jointPaper(sqlListPU)); 
				}
			}
		}
		else{
			model.put("message", "该用户所在部门未配置题库，请联系管理员调整！");
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnea00006
	 * 保存/提交考题
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnea00006.do")
	public ModelAndView txnea00006(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String paperValue=request.getParameter("paperValue"); 
		String type=request.getParameter("type"); 
		String exam_info_userlist_id=request.getParameter("exam_info_userlist_id"); 
		request.setAttribute("exam_info_userlist_id", exam_info_userlist_id);
		
		if(!StringUtil.isEmpty(paperValue)){
			String paperValueA[]=paperValue.split(";");
			for(int i=0;i<paperValueA.length;i++){
				if(!StringUtil.isEmpty(paperValueA[i])){
					examInfoUserlistDao.updateExamPaperSolution(paperValueA[i].split(":")[1], paperValueA[i].split(":")[0]);
				}
			}
		}
		if(type.equals("1")){   //提交，需要对试题打分
			request.setAttribute("paper_status", Constants.PAPERINFO_STATUS_SUBMIT);
			examInfoUserlistDao.updateExamInfoStatus(request);   //改变试卷状态
			examInfoUserlistDao.updateExamPaperScore(request);    //系统自动打分
			examInfoUserlistDao.updateExamPaperTotalScore(request);
			model.put("message", "提交成功！");
		}
		else{
			model.put("message", "保存成功！");
		}
		
		//刷新考试页面
		List<Map<String, Object>> sqlListPU = examInfoUserlistDao.selectExamPaperUserlist(request);
		if(sqlListPU!=null&&sqlListPU.size()>0){
			model.put("paper_str", DaoUtil.jointPaper(sqlListPU)); 
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * 查询用户考试列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnea00007.do")
	public ModelAndView txnea00007(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns="exam_info_userlist_id,exam_info_id,score,paper_status,username,usercode";
		String exam_info_id = request.getParameter("exam_info_id");
		String exam_info_code = request.getParameter("exam_info_code");
		request.setAttribute("exam_info_id", exam_info_id);
		List<Map<String, Object>> sqlList = examInfoUserlistDao.selectExamInfoUserlist(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		model.put("dataList", dataStr); 
		model.put("total", sqlList.size()); 
		model.put("exam_info_id", exam_info_id);
		model.put("exam_info_code", exam_info_code);
		
		return new ModelAndView("app/examinfo/selectexaminfo", model);
	}
	
	/**
	 * 查询用户考试列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnea00008.do")
	public ModelAndView txnea00008(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		//刷新考试页面
		String exam_info_userlist_id=request.getParameter("exam_info_userlist_id"); 
		request.setAttribute("exam_info_userlist_id", exam_info_userlist_id);
		List<Map<String, Object>> sqlListPU = examInfoUserlistDao.selectExamPaperUserlist(request);
		if(sqlListPU!=null&&sqlListPU.size()>0){
			model.put("paper_str", DaoUtil.jointPaper(sqlListPU)); 
		}
				
		return new ModelAndView("app/examinfo/viewexaminfo", model);
	}

}
