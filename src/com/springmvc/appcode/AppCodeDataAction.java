package com.springmvc.appcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.springmvc.util.DaoUtil;
import com.springmvc.util.DictItem;
import com.springmvc.util.DictItemCache;
import com.springmvc.util.StringUtil;

@Controller
public class AppCodeDataAction extends MultiActionController {
	
	public static Log log = LogFactory.getLog(AppCodeDataAction.class);
	
	@Autowired
	@Qualifier("appCodedataDao")
	private AppCodeDataDao appCodedataDao;
	private AppCodeIndexVO appcodeIndexVO=new AppCodeIndexVO();
	private AppCodeDataVO appcodeDataVO=new AppCodeDataVO();
	
	/**
	 * 获取动态代码集
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCodeValue.do")
	@ResponseBody
	public List<Map<String, Object>> getCodeValue(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Map<String, Object>> sqlList = appCodedataDao.selectCodeValueOnload(request);
		return sqlList;
	}
	
	/**
	 * 查询代码集
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txncd00001.do")
	public ModelAndView txncd00001(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=appcodeIndexVO.getColmuns();
		
		List<Map<String, Object>> sqlList = appCodedataDao.selectCodeIndex(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		model.put("dataList", dataStr); 
		return new ModelAndView("app/codedata/queryCodeIndex", model);
	}
	
	/**
	 * 新增、修改 代码集
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txncd00002.do")
	public ModelAndView txncd00002(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String tableColmuns=appcodeIndexVO.getColmuns();
		String check = request.getParameter("check");
		if(StringUtil.isEmpty(check)){
			String codetype = request.getParameter("codetype");
			String values=DaoUtil.insertTableValues(tableColmuns,codetype, request);
			
			request.setAttribute("values", values);
			appCodedataDao.insertCodeIndex(request);
			model.put("statusCode", 200);
		}
		else{
			String tableUpdateColmuns=appcodeIndexVO.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			
			request.setAttribute("values", values);
			appCodedataDao.updateCodeIndex(request);
			model.put("statusCode", 200);
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txncd00003
	 * 查看代码信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txncd00003.do")
	public ModelAndView txncd00003(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=appcodeIndexVO.getColmuns();
		
		List<Map<String, Object>> sqlList = appCodedataDao.selectCodeIndex(request);
		model=DaoUtil.getDataMap(tableColmuns, sqlList);
		model.put("check", "update");
		return new ModelAndView("app/codedata/editCodeIndex", model);
	}
	
	/**
	 * txncd00004
	 * 修改代码信息状态为无效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txncd00004.do")
	public ModelAndView txncd00004(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		appCodedataDao.updateCodeIndexStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * 维护代码集值信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txncd00005.do")
	public ModelAndView txncd00005(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=appcodeDataVO.getColmuns();
		String codetype = request.getParameter("codetype");
		
		List<Map<String, Object>> sqlList = appCodedataDao.selectCodeValueByS(codetype);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		
		model.put("dataList", dataStr); 
		model.put("codetype", codetype); 
		
		//获取父代码集信息
		List<Map<String, Object>> sqlListF = appCodedataDao.selectCodeIndex(request);
		if(sqlListF!=null&&sqlListF.size()>0){
			String father_code=(String) sqlListF.get(0).get("father_code");
			model.put("father_code", father_code); 
		}
		return new ModelAndView("app/codedata/queryCodeData", model);
	}
	
	/**
	 * 新增、修改 代码集  值
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txncd00006.do")
	public ModelAndView txncd00006(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String tableColmuns=appcodeDataVO.getColmuns();
		String check = request.getParameter("check");
		if(StringUtil.isEmpty(check)){
			String codetype = request.getParameter("codetype");
			String values=DaoUtil.insertTableValues(tableColmuns,codetype, request);
			
			request.setAttribute("values", values);
			appCodedataDao.insertCodeData(request);
			model.put("statusCode", 200);
		}
		else{
			String tableUpdateColmuns=appcodeDataVO.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			
			request.setAttribute("values", values);
			appCodedataDao.updateCodeData(request);
			model.put("statusCode", 200);
		}
		
		//代码集增加修改时需要调整
		DictItem.reload();
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txncd00007
	 * 查看代码值信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txncd00007.do")
	public ModelAndView txncd00007(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableColmuns=appcodeDataVO.getColmuns();
		
		List<Map<String, Object>> sqlList = appCodedataDao.selectCodeData(request);
		model=DaoUtil.getDataMap(tableColmuns, sqlList);
		model.put("check", "update");
		return new ModelAndView("app/codedata/editCodeData", model);
	}
	
	/**
	 * txncd00008
	 * 查看代码值信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txncd00008.do")
	public ModelAndView txncd00008(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		appCodedataDao.deleteCodeData(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
}
