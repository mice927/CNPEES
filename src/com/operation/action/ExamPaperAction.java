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

import com.operation.dao.ExamPaperDao;
import com.operation.dao.ItemBankDao;
import com.operation.vo.ExamPaperVO;
import com.springmvc.userlist.UserListDao;
import com.springmvc.util.DaoUtil;
import com.springmvc.util.StringUtil;

@Controller
public class ExamPaperAction extends MultiActionController {
	
	public static Log log = LogFactory.getLog(ItemBankAction.class);
	
	@Autowired 
	@Qualifier("examPaperDao")
	private ExamPaperDao examPaperDao;
	@Autowired
	@Qualifier("itemBankDao")
	private ItemBankDao itemBankDao;
	@Autowired
	@Qualifier("userlistDao")
	private UserListDao userlistDao;
	private ExamPaperVO examPaperVO= new ExamPaperVO();
	
	/**
	 * 查询考题列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnep00001.do")
	public ModelAndView txnep00001(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=examPaperVO.getQueryColmuns();
		String item_bank_id = request.getParameter("item_bank_id");
		String item_bank_name = request.getParameter("item_bank_name");
		List<Map<String, Object>> sqlList = examPaperDao.selectExamPaperList(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		model.put("dataList", dataStr); 
		model.put("total", sqlList.size()); 
		model.put("item_bank_id", item_bank_id);
		model.put("item_bank_name", item_bank_name);
		
		return new ModelAndView("app/itembank/selectExamPaper", model);
	}
	
	/**
	 * txnep00002
	 * 查看题库信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnep00002.do")
	public ModelAndView txnep00002(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableTitle=examPaperVO.getColmuns();
		
		String exam_paper_id = request.getParameter("exam_paper_id");
		String item_bank_id = request.getParameter("item_bank_id");
		if(!StringUtil.isEmpty(exam_paper_id)){
			List<Map<String, Object>> sqlList = examPaperDao.selectExamPaperList(request);
			model=DaoUtil.getDataMap(tableTitle, sqlList);
		}
		else{
			model.put("item_bank_id", item_bank_id);
			List<Map<String, Object>> sqlList = examPaperDao.selectExamPaperMaxCode(request);
			if(sqlList!=null&&sqlList.size()>0){
				String item_bank_code=(String) sqlList.get(0).get("item_bank_code");
				String exam_paper_code=(String) sqlList.get(0).get("exam_paper_code");
				if(StringUtil.isEmpty(exam_paper_code)){
					model.put("exam_paper_code", item_bank_code+"00001");
				}
				else{
					String maxCode=(Integer.parseInt(exam_paper_code.substring(item_bank_code.length(), exam_paper_code.length()))+1)+"";
					for(int i=5;maxCode.length()<i;){
						maxCode="0"+maxCode;
					}
					model.put("exam_paper_code", item_bank_code+maxCode);
				}
			}
		}
		
		return new ModelAndView("app/itembank/editExamPaper", model);
	}
	
	/**
	 * txnep00003
	 * 增加/修改 题库信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnep00003.do")
	public ModelAndView txnep00003(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String exam_paper_id = request.getParameter("exam_paper_id");
		
		if(StringUtil.isEmpty(exam_paper_id)){
			String tableColmuns=examPaperVO.getColmuns();
			exam_paper_id=DaoUtil.getUUID();
			String values=DaoUtil.insertTableValues(tableColmuns,exam_paper_id, request);
			request.setAttribute("values", values);
			examPaperDao.insertExamPaper(request);
			model.put("statusCode", 200);
			model.put("exam_paper_id", exam_paper_id);
		}
		else{
			String tableUpdateColmuns=examPaperVO.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			request.setAttribute("values", values);
			
			examPaperDao.updateExamPaper(request);
			model.put("statusCode", 200);
			model.put("exam_paper_id", exam_paper_id);
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnep00004
	 * 获取题库列表为树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnep00004.do")
	public ModelAndView txnep00004(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		List<Map<String, Object>> sqlList = itemBankDao.selectItemBankForTree(request);
		String treeStr=DaoUtil.getDataListForTree(sqlList,"txnep00001.do","layout-item");
		model.put("treeStr", treeStr);
		return new ModelAndView("app/itembank/queryExamPaper", model);
	}
	
	/**
	 * txnep00005
	 * 获取部门列表为树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnep00005.do")
	public ModelAndView txnep00005(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		List<Map<String, Object>> sqlList = userlistDao.selectOfficeForTree(request);
		String treeStr=DaoUtil.getDataListForTree(sqlList,"txnep00006.do","layout-office");
		model.put("treeStr", treeStr);
		return new ModelAndView("app/itembank/queryExamOffice", model);
	}
	
	/**
	 * 查询部门下的题库列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnep00006.do")
	public ModelAndView txnep00006(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HashMap<String, Object> model = new HashMap<String, Object>();
		String office_id = request.getParameter("office_id");
		String office_name = request.getParameter("office_name");
		
		List<Map<String, Object>> sqlList = userlistDao.selectExamOffice(request);
		String dataStr=DaoUtil.getDataList("exam_office_id,item_bank_code,item_bank_name,item_bank_type,percentage", sqlList);
		model.put("dataList", dataStr); 
		model.put("total", sqlList.size()); 
		model.put("office_id", office_id);
		model.put("office_name", office_name);
		return new ModelAndView("app/itembank/selectItemBank", model);
	}
	
	/**
	 * 添加部门题库关联
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("txnep00007.do")
	public ModelAndView txnep00007(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String office_id = request.getParameter("office_id");
		String item_bank_id = request.getParameter("item_bank_id");
		if(!StringUtil.isEmpty(item_bank_id)){
			String item_bank_idA[]=item_bank_id.split(",");
			for(int i=0;i<item_bank_idA.length;i++){
				String id=DaoUtil.getUUID();
				String percentage="";     //分配规则暂定
				String values="'"+id+"','"+item_bank_idA[i]+"','"+office_id+"','"+percentage+"'";
				
				request.setAttribute("values", values);
				userlistDao.insertExamOffice(request);
			}
		}
		
		model.put("statusCode", 200);
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * 删除部门题库关联
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("txnep00008.do")
	public ModelAndView txnep00008(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		userlistDao.deleteExamOffice(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnep00009
	 * 修改考题状态为无效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnep00009.do")
	public ModelAndView txnep00009(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		examPaperDao.updateExamPaperStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	

}
