package com.springmvc.util;

import java.util.List;

public class DictItemCache {
	
	private static DictItemCache instance= new DictItemCache(); 
	
	private DictItemCache() {
		
	}
	
	public static DictItemCache getInstance() {
		return instance;
	}
	
	public List<String[]> getDictItem(String code) {
		return DictItem.getDictItemList(code);
	}
	
	public String getDictItemJson(String code) {
		return DictItem.getDictItemJson(code);
	}
}
