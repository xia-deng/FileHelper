package filehelper.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import filehelper.helper.ConfigHelper;
import filehelper.helper.XMLHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.sys.model.SysFloder;

/**
 * 系统使用信息存放操作类
 * @author Administrator
 *
 */
public class SysXMLHandler {
	
	public SysXMLHandler() {
		// TODO Auto-generated constructor stub
		XMLHelper.loadXMLFile(ConfigHelper.sysInfoLocation());
	}

	/**
	 * 
	 * 判断系统是不是第一次启动
	 * @return
	 */
	public boolean isFirstUse(){
		boolean flag=XMLHelper.nodeExit(ConfigVariable.XML_Dir_Collectoin);
		return (!flag);
	}
	
	/**
	 * 如果是第一次启动(设定目录文件夹后)，就要向系统使用文件中写入信息
	 * @return
	 */
	public boolean initSysFile(){
		
		//XMLHelper.nodeAdd(XMLHelper.getXmlRoot(), ConfigVariable.XML_Dir_Name, null);
		try {
			Element temp=XMLHelper.nodeAdd(XMLHelper.getXmlRoot(), ConfigVariable.XML_Dir_Collectoin, null);
			XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	public String getUsedSysDirName(){
		return getUsedSysDir().element(ConfigVariable.XML_Dir).getText();
	}
	
	/**
	 * 获取正在使用的系统指定目录Sys_Dir
	 * @return 对应的XML节点
	 */
	public  Element getUsedSysDir(){
		
		Element dir=null;
		try {
			List<Element> list=XMLHelper.getXmlRoot().element(ConfigVariable.XML_Dir_Collectoin).elements(ConfigVariable.XML_Sys_Dir);
			for (Element element : list) {
				String use= element.element(ConfigVariable.XML_IS_Used).getText();
				if(use==ConfigVariable.DIR_USED_TRUE||use.equals(ConfigVariable.DIR_USED_TRUE)){
					dir=element;
					break;
				}
			}
			return dir;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			return dir;
		}
		
		
	}
	
	/**
	 * 系统开始使用新的指定目录
	 * @param dir
	 * @return
	 */
	public boolean addNewDir(String dir,SysFloder floder){
		try {
			//修改正在使用目录为停止状态
			if(this.getUsedSysDir()!=null){
				getUsedSysDir().element(ConfigVariable.XML_IS_Used).setText(ConfigVariable.DIR_USED_FALSE);
			}
			Element dirs=XMLHelper.nodeList(ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin).get(0);
			Element temp= XMLHelper.nodeAdd(dirs, ConfigVariable.XML_Sys_Dir, null);
			XMLHelper.nodeAdd(temp, ConfigVariable.XML_IS_Used, floder.getIsUsed());
			XMLHelper.nodeAdd(temp, ConfigVariable.XML_Time, floder.getTime());
			XMLHelper.nodeAdd(temp, ConfigVariable.XML_Dir, floder.getDir());
			XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 修改节点的值
	 * @param newStr
	 * @param element
	 */
	public void updateNode(String newStr,Element element){
		XMLHelper.nodeEdit(element, newStr);
		XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
	}
	
	/**
	 * 修改多个节点的值
	 * 描述：
	 * @param map 
	 * @return：void
	 * @author 吴宝龙
	 * @date： 2016年1月22日 下午5:06:31
	 */
	public void updateNodes(Map<String, Object> map){
		for (String key : map.keySet()) {
			Element element=getElement(ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+key);
			element.setText(map.get(key).toString());
		}
		XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
	}
	
	/**
	 * 删除XML文件中的目录
	 * @param dirName
	 * @return 
	 */
	public boolean removeNode(String dirName){
		String nodeName=ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir;
		boolean flag= XMLHelper.removeNode(nodeName, dirName);
		XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
		return flag;
	}
	
	/**
	 * 删除XML文件中的目录
	 * @param index 目录的排行
	 */
	public boolean removeNode(int index){
		boolean flag=false;
		String nodeName=ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir;
		List<Element> lists=XMLHelper.nodeList(nodeName);
		for (int i = 0; i < lists.size(); i++) {
			if(i==index){
				flag=  removeNode(lists.get(i).element(ConfigVariable.XML_Dir).getText());
				break;
			}else{
				continue;
			}
		}
		return flag;
	}
	
	/**
	 * 根据节点路径获取该节点
	 * @param eleString
	 * @return
	 */
	public Element getElement(String eleString){
		List<Element> lists=XMLHelper.nodeList(eleString);
		if(lists!=null&&lists.size()>0){
			return lists.get(0);
		}else{
			Element catalogElement=XMLHelper.getXmlRoot();
			Element temp= catalogElement.addElement(ConfigVariable.XML_LAW_DIR);
			XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
			return temp;
		}
	}
	
	
	/**
	 * 获取所有的系统目录
	 * @return
	 */
	public List<String> getAllSysDir(){
		String nodeName=ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir;
		List<Element> lists=XMLHelper.nodeList(nodeName);
		List<String> listStrs=new ArrayList<String>();
		for (Element element : lists) {
			String temp=element.element(ConfigVariable.XML_Dir).getText();
			listStrs.add(temp);
		}
		return listStrs;
	}
}
