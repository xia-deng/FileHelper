package filehelper.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import filehelper.staticvariable.ConfigVariable;

/**
 * XML文件操作类:使用DOM4j
 * 
 * @author Administrator
 *
 */
public class XMLHelper {

	private static Document document = null;
	private static SAXReader saxReader=null;
	/**
	 * 静态化方法：加载XML文件
	 */
	static {
		saxReader = new SAXReader();
	}
	
	public static void loadXMLFile(String file){
		try {
			File files=new File(file);
			document = saxReader.read(files);
		} catch (Exception e) {//无论报什么错都执行下面这条,新建新的使用信息文件
			// TODO Auto-generated catch block
			e.printStackTrace();
			 document = DocumentHelper.createDocument(); 
			//使用 addElement() 方法创建根元素 <catalog> 。 addElement() 用于向 XML 文档中增加元素。
			 initXmlFile();
			 XMLHelper.writeToXML(file);
		}
	}
	
	public static void initXmlFile(){
		Element catalogElement = document.addElement(ConfigVariable.XML_ROOT); 
		catalogElement.addElement(ConfigVariable.XML_Dir_Collectoin);
		catalogElement.addElement(ConfigVariable.XML_LAW_DIR);
	}

	/**
	 * 判断某个节点是否存在
	 * 
	 * @param nodeName
	 * @return true:存在；false:不存在
	 */
	public static boolean nodeExit(String nodeName) {
		List list = document.selectNodes(nodeName);
		if (list!=null&&list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取指定节点名称的集合
	 * @param nodeName 节点名称
	 * @return 集合
	 */
	public static List<Element> nodeList(String nodeName) {
		
		try {
			List<Element> list= document.selectNodes(nodeName);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	
	/**
	 * 删除节点----nodeName like ROOT/DIRCOLLECTION/SYSDIR
	 * @param nodeName like ROOT/DIRCOLLECTION/SYSDIR
	 * @param nodeText dirName
	 */
	public static boolean removeNode(String nodeName,String nodeText){
		
		boolean flag=false;
		
		List<Element> lists=nodeList(nodeName);
		for (Element element : lists) {
			Element dirName=element.element(ConfigVariable.XML_Dir);
			Element isUse=element.element(ConfigVariable.XML_IS_Used);
			if((nodeText.equals(dirName.getText())&&isUse.getText().equals(ConfigVariable.DIR_USED_FALSE))
					||(nodeText==dirName.getText()&&isUse.getText()==ConfigVariable.DIR_USED_FALSE)){
				Element dirs= element.getParent();
				dirs.remove(element);
				flag=true;
				break;
			}else{
				continue;
			}
		}
		return flag;
	}
	
	/**
	 * 获取XML文件的根节点
	 * @return
	 */
	public static Element getXmlRoot(){
		
		Element root=document.getRootElement();
		return root;
	}
	
	/**
	 * 新增节点
	 * @param parentElement 父节点
	 * @param nodeName 节点名
	 * @param nodeContext 节点文本内容
	 * @return
	 */
	public static Element nodeAdd(Element parentElement, String nodeName,String nodeContext){
		
		Element newElement= parentElement.addElement(nodeName);
		if(nodeContext!=null){
			newElement.setText(nodeContext);
		}
		return newElement;
	}
	
	/**
	 * 更新节点的值
	 * @param element
	 * @param newStr
	 */
	public static void nodeEdit(Element element,String newStr){
		element.setText(newStr);
	}

	
	/**
	 * 将对XML文件的更新写入到XML中
	 */
	public static void writeToXML(String file) {
		OutputFormat opf = new OutputFormat(" ", true, "UTF-8");
		opf.setTrimText(true);
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file), opf);
			writer.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
