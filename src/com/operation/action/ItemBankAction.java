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

import com.operation.dao.ItemBankDao;
import com.operation.vo.ItemBankVO;
import com.springmvc.util.DaoUtil;
import com.springmvc.util.StringUtil;

@Controller
public class ItemBankAction extends MultiActionController {
	
	public static Log log = LogFactory.getLog(ItemBankAction.class);
	
	@Autowired
	@Qualifier("itemBankDao")
	private ItemBankDao itemBankDao;
	private ItemBankVO itemBankVO= new ItemBankVO();
	
	/**
	 * 查询题库列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("txnib00001.do")
	public ModelAndView txnib00001(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String office_id = request.getParameter("office_id");
		String check = request.getParameter("check");
		
		String tableColmuns=itemBankVO.getColmuns();
		
		List<Map<String, Object>> sqlList = itemBankDao.selectItemBankList(request);
		String dataStr=DaoUtil.getDataList(tableColmuns, sqlList);
		model.put("dataList", dataStr); 
		model.put("total", sqlList.size());
		model.put("office_id", office_id);
		model.put("check", check);
		return new ModelAndView("app/itembank/queryItemBank", model);
	}
	
	/**
	 * txnib00002
	 * 校验该编码是否已经存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnib00002.do")
	public ModelAndView txnib00002(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		//查询编码信息  判断该编码是否存在
		List<Map<String,Object>> itemBankVOL = itemBankDao.selectItemBankList(request);
		if (itemBankVOL != null&&itemBankVOL.size()>0) {   //当存在编码信息时
			model.put("statusCode", "200");
			model.put("message", "该编码已存在");
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
	 * txnib00003
	 * 增加/修改 题库信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnib00003.do")
	public ModelAndView txnib00003(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		String item_bank_id = request.getParameter("item_bank_id");
		
		if(StringUtil.isEmpty(item_bank_id)){
			String tableColmuns=itemBankVO.getColmuns();
			item_bank_id=DaoUtil.getUUID();
			String values=DaoUtil.insertTableValues(tableColmuns,item_bank_id, request);
			request.setAttribute("values", values);
			
			itemBankDao.insertItemBank(request);
			model.put("statusCode", 200);
			model.put("item_bank_id", item_bank_id);
		}
		else{
			String tableUpdateColmuns=itemBankVO.getUpdateColmuns();
			String values=DaoUtil.updateTableSet(tableUpdateColmuns, request);
			request.setAttribute("values", values);
			
			itemBankDao.updateItemBank(request);
			model.put("statusCode", 200);
			model.put("item_bank_id", item_bank_id);
		}
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}
	
	/**
	 * txnib00004
	 * 查看题库信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnib00004.do")
	public ModelAndView txnib00004(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String tableTitle=itemBankVO.getColmuns();
		
		List<Map<String, Object>> sqlList = itemBankDao.selectItemBankList(request);
		model=DaoUtil.getDataMap(tableTitle, sqlList);
		
		return new ModelAndView("app/itembank/editItemBank", model);
	}
	
	/**
	 * txnib00005
	 * 修改题库状态为无效
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/txnib00005.do")
	public ModelAndView txnib00005(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		itemBankDao.updateItemBankStatus(request);
		
		ModelAndView mav = new ModelAndView();
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		model.put("statusCode", "200");
		model.put("message", "已删除!");
		
		view.setAttributesMap(model);
		mav.setView(view);
		return mav;
	}

}
