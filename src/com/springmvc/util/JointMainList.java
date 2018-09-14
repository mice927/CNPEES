package com.springmvc.util;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class JointMainList {
	
	private static String TYPE_SLIDEBAR="slidebar";
	private static String TYPE_ZTREE="ztree";
	private static String TYPE_FOLDER="folder";
	private static String TYPE_TABS="tabs";
	
	/**
	 * getMainList
	 * 获取菜单列表
	 * @return
	 * @throws DocumentException
	 */
	public static String getMainList(String authority) throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read("http://localhost:8080/CNPEES/menu.xml");
		
		Element root = doc.getRootElement();    //根目录menus
		
		List<Element> menuList = root.elements("menu");    //第一层
		
		StringBuffer menuStr=new StringBuffer();    //最后合成的目录结构
		for(int i=0;i<menuList.size();i++){
			Element eSlidebar = (Element)menuList.get(i);
			String nameSlidebar=eSlidebar.attributeValue("name");
			String typeSlidebar=eSlidebar.attributeValue("type");
			String faiconSlidebar=eSlidebar.attributeValue("faicon");
			
			if(typeSlidebar.equals(TYPE_SLIDEBAR)){  //顶层菜单
				StringBuffer ztreeStr=new StringBuffer();   //单个导航结构
				for(Iterator iterZ = eSlidebar.elementIterator(); iterZ.hasNext();){  
					Element eZTree = (Element)iterZ.next();
					String idZTree=eZTree.attributeValue("id");
					String nameZTree=eZTree.attributeValue("name");
					String typeZTree=eZTree.attributeValue("type");
					String faiconZTree=eZTree.attributeValue("faicon");
					
					if(typeZTree.equals(TYPE_ZTREE)){  //导航首菜单
						StringBuffer tabsStr=new StringBuffer(); //单个树状结构
						for(Iterator iterT = eZTree.elementIterator(); iterT.hasNext();){
							Element eTabs = (Element)iterT.next();
							String idTabs=eTabs.attributeValue("id");
							String nameTabs=eTabs.attributeValue("name");
							String typeTabs=eTabs.attributeValue("type");
							String pidTabs=eTabs.attributeValue("pid");
							String urlTabs=eTabs.attributeValue("url");
							String faiconTabs=eTabs.attributeValue("faicon");
							
							if(authority.contains(idTabs)){//如果权限通过才可显示该列表信息
								if(typeTabs.equals(TYPE_FOLDER)){  //树菜单
									tabsStr.append("<li data-id=\""+idTabs+"\" data-pid=\""+pidTabs+"\" data-faicon=\"folder-open-o\" data-faicon-close=\"folder-o\">"+nameTabs+"</li>");
								}
								else if(typeTabs.equals(TYPE_TABS)){
									if(StringUtil.isEmpty(faiconTabs)){
										faiconTabs="caret-right";
									}
									tabsStr.append("<li data-id=\""+idTabs+"\" data-pid=\""+pidTabs+"\" data-url=\""+urlTabs+"\" data-tabid=\"t"+idTabs+"\" data-faicon=\""+faiconTabs+"\">"+nameTabs+"</li>");
								}
							}
						}
						
						if(tabsStr.length()>0){
							ztreeStr.append("<ul id=\""+idZTree+"\" class=\"ztree ztree_main\" data-toggle=\"ztree\" data-on-click=\"MainMenuClick\" data-expand-all=\"true\" data-faicon=\""+faiconZTree+"\" data-tit=\""+nameZTree+"\">");
							ztreeStr.append(tabsStr+"</ul>");
						}
					}
				}
				
				if(ztreeStr.length()>0){
					if(menuStr.length()==0){
						menuStr.append("<li class=\"active\">");
					}
					else{
						menuStr.append("<li>");
					}
					menuStr.append("<a href=\"javascript:;\" data-toggle=\"slidebar\"><i class=\"fa fa-"+faiconSlidebar+"\"></i> "+nameSlidebar+"</a><div class=\"items hide\" data-noinit=\"true\">");
					menuStr.append(ztreeStr+"</div></li>");
				}
			}
		}
		
		return menuStr.toString();
	}
	
	/**
	 * getAllMainListInRole
	 * 获取全部菜单列表用于增加权限
	 * @return
	 * @throws DocumentException
	 */
	public static String getAllMainListInRole() throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read("http://localhost:8080/CNPEES/menu.xml");
		
		Element root = doc.getRootElement();    //根目录menus
		
		List<Element> menuList = root.elements("menu");    //第一层
		
		StringBuffer menuStr=new StringBuffer();    //最后合成的目录结构
		for(int i=0;i<menuList.size();i++){
			Element eSlidebar = (Element)menuList.get(i);
			String typeSlidebar=eSlidebar.attributeValue("type");
			
			if(typeSlidebar.equals(TYPE_SLIDEBAR)){  //顶层菜单
				for(Iterator iterZ = eSlidebar.elementIterator(); iterZ.hasNext();){  
					Element eZTree = (Element)iterZ.next();
					String idZTree=eZTree.attributeValue("id");
					String nameZTree=eZTree.attributeValue("name");
					String typeZTree=eZTree.attributeValue("type");
					
					if(typeZTree.equals(TYPE_ZTREE)){  //导航首菜单
						StringBuffer tabsStr=new StringBuffer(); //单个树状结构
						for(Iterator iterT = eZTree.elementIterator(); iterT.hasNext();){
							Element eTabs = (Element)iterT.next();
							String idTabs=eTabs.attributeValue("id");
							String nameTabs=eTabs.attributeValue("name");
							String typeTabs=eTabs.attributeValue("type");
							String pidTabs=eTabs.attributeValue("pid");
							String urlTabs=eTabs.attributeValue("url");
							String faiconTabs=eTabs.attributeValue("faicon");
							
							if(typeTabs.equals(TYPE_FOLDER)){  //树菜单
								tabsStr.append("<li data-id=\""+idTabs+"\" data-pid=\""+pidTabs+"\">"+nameTabs+"</li>");
							}
							else if(typeTabs.equals(TYPE_TABS)){
								tabsStr.append("<li data-id=\""+idTabs+"\" data-pid=\""+pidTabs+"\" data-url=\""+urlTabs+"\" data-tabid=\"t"+idTabs+"\">"+nameTabs+"</li>");
							}
						}
						
						if(tabsStr.length()>0){
							menuStr.append("<li data-id=\""+idZTree+"\" data-pid=\"0\">"+nameZTree+"</li>");
							menuStr.append(tabsStr);
						}
					}
				}
			}
		}
		return menuStr.toString();
	}
	
//	/**
//	 * 
//	 * authority  用户权限
//	 * @return  根据权限产生的列表
//	 * @throws DocumentException 
//	 */
//	public static String JointUserList(String authority) throws DocumentException{
//		String userMainList="";
//		
//		ArrayList<String[]> showL=getAllMainList();
//		
//		for(int i=0;i<showL.size();i++){
//			String showA[]=showL.get(i);
//			
//			//判断该项是否有父项
//			if(StringUtil.isEmpty(showA[3])&&"true".equals(showA[2])){   //如果父项ID为空  并且子节点不为空，说明该项为最外层项   
//				userMainList=userMainList+"<div class=\"accordionHeader\"><h2><span>Folder</span>"+showA[1]+"</h2></div><div class=\"accordionContent\"><ul class=\"tree treeFolder\">";
//			}
//			else if(!StringUtil.isEmpty(showA[3])&&"false".equals(showA[2])){ //如果父项ID不为空  不含有子节点
//				userMainList=userMainList+"<li><a href=\""+showA[4]+"\" target=\"navTab\" rel=\""+showA[0]+"\">"+showA[1]+"</a></li>";
//			}
//			else if(!StringUtil.isEmpty(showA[3])&&"true".equals(showA[2])){//如果父项ID不为空 并且子节点也不为空，该项为第三层开始，暂不支持
//				
//			}
//			//判断是否为最后一项
//			if("last".equals(showA[5])){
//				userMainList=userMainList+"</ul></div>";
//			}
//		}
//		return userMainList;
//	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println(getMainList(""));
	}

}
