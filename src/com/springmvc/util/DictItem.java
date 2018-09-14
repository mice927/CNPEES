package com.springmvc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.springmvc.appcode.AppCodeDataDao;

public class DictItem {
	private static ConcurrentHashMap<String, List<String[]>> map = new ConcurrentHashMap<String, List<String[]>>();
	private static ConcurrentHashMap<String, String> mapJ = new ConcurrentHashMap<String, String>();

	@Autowired
	@Qualifier("appCodedataDao")
	private AppCodeDataDao appCodedataDao;

	@PostConstruct
	public void init() {
		System.out.println("初始化代码集....");

		getDataListToList();
		getDataListToJson();
	}
	
	public String getString() {
		return "111";
	}
	
	/**
	 * 加载初始化代码集
	 * @param tableTitle
	 * @param sqlList
	 * @return
	 */
	private void getDataListToList(){
		
		List<Map<String, Object>> indexList = appCodedataDao.selectCodeIndexOnload("01','02");

		if (indexList != null && indexList.size() > 0) {
			for (int i = 0; i < indexList.size(); i++) {
				String codetype = (String) indexList.get(i).get("codetype");

				List<Map<String, Object>> valueList = appCodedataDao.selectCodeValueByS(codetype);
				
				List<String[]> itemL =new ArrayList<String[]>();
				for(int j=0;j<valueList.size();j++){
					String[] itemA={(String)valueList.get(j).get("codevalue"),(String)valueList.get(j).get("codename")};
					itemL.add(itemA);
				}
				map.put(codetype, itemL);
			}
			
			List<Map<String, Object>> indexListA = appCodedataDao.selectCodeIndexOnload("01','02");
			List<String[]> itemL =new ArrayList<String[]>();
			for (int i = 0; i < indexListA.size(); i++) {
				//定义所有代码代码集
				String[] itemA={(String) indexListA.get(i).get("codetype"),(String) indexListA.get(i).get("description")};
				itemL.add(itemA);
			}
			map.put("codeindex", itemL);
		}
	}
	
	/**
	 * 加载初始化代码集  拼接成json字符串格式
	 * @param tableTitle
	 * @param sqlList
	 * @return
	 */
	private void getDataListToJson(){
		
		List<Map<String, Object>> indexList = appCodedataDao.selectCodeIndexOnload("01','02");

		if (indexList != null && indexList.size() > 0) {
			for (int i = 0; i < indexList.size(); i++) {
				String codetype = (String) indexList.get(i).get("codetype");

				List<Map<String, Object>> valueList = appCodedataDao.selectCodeValueByS(codetype);
				if (valueList != null && valueList.size() > 0) {
					String dataStr="[";
					for(int j=0;j<valueList.size();j++){
						dataStr=dataStr+"{'"+(String) valueList.get(j).get("codevalue")+"':'"+(String) valueList.get(j).get("codename")+"'},";
					}
					dataStr=dataStr.substring(0, dataStr.length()-1)+"]";
					
					mapJ.put(codetype, dataStr);
				}
			}
		}
	}

	public static List<String[]> getDictItemList(String key) {
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		return map.get(key);
	}
	
	public static String getDictItemJson(String key) {
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		return mapJ.get(key);
	}
	
	public static synchronized void reload() {
		System.out.println("更新代码集.....");
		//修改map
		
		
	}

	@PreDestroy
	public void dostory() {
		System.out.println("销毁代码集.....");
		map.clear();
	}
}
