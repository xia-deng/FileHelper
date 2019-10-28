package filehelper.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 配置文件实例化
 * @author Administrator
 *
 */
public class ConfigHelper {
	
	//public static Container container = null; 

	//静态实例化
	private static Properties prop;
	static {
		System.out.println("开始实例化配置文件.....");
		prop = new Properties();
		ClassLoader loader=ConfigHelper.class.getClassLoader();
		try {
			InputStream in =new BufferedInputStream(new FileInputStream(new File("resource/config/config.properties")));
			System.out.println("开始实例化完成....."+in);
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置文件中得系统图标
	 * @return
	 */
	public static String mainIcon() {
		try {
			return prop.getProperty("mainIcon");
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		 
	}
	
	/**
	 * 获取配置文件中文件类型的图标
	 * @return
	 */
	public static String fileTypeIcon() {
		try {
			return prop.getProperty("fileType");
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		 
	}
	
	/**
	 * 获取配置文件中的系统名
	 * @return
	 */
	public static String mainName() {
		try {
			return new String(prop.getProperty("mainName").getBytes("iso-8859-1")) ;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		 
	}
	
	/**
	 * 获取配置文件中系统的宽度
	 * @return
	 */
	public static int mainWidth(){
		try {
			return Integer.parseInt( prop.getProperty("width"));
		} catch (Exception e) {
			// TODO: handle exception
			return 400;
		} 
	}
	
	/**
	 * 获取配置文件中系统的高度
	 * @return
	 */
	public static int mainHeight(){
		try {
			return Integer.parseInt( prop.getProperty("height"));
		} catch (Exception e) {
			// TODO: handle exception
			return 400;
		} 
	}
	
	/**
	 * 获取配置文件中的TAB名称和对应的Panel
	 * @return TAB名称和Panel的对应MAP
	 */
	public static Map<String, Object> tabNames() {
		
		String[] tabs=null;
		String[] panels=null;
		Map<String, Object> map=new LinkedHashMap<String, Object>();
		try {
			//return new String(prop.getProperty("tabNames").getBytes("iso-8859-1")) ;
			String tempTabs=new String(prop.getProperty("tabNames").getBytes("iso-8859-1"));
			String tempPanels=prop.getProperty("tabPanels");
			//System.out.println(tempTabs);
			tabs=tempTabs.split(",");
			panels=tempPanels.split(",");
			
		} catch (Exception e) {
			// TODO: handle exception
			tabs=new String[]{"目录定位","文件管理","人员管理","右键管理"};
			panels=new String[tabs.length];
		}
		for (int i = 0; i < tabs.length; i++) {
			map.put(tabs[i], panels[i]);
		}
		return map;
	}
	
	//sysInfoLocation
	/**
	 * 获取配置文件中的存放使用信息的位置字段
	 * @return
	 */
	public static String sysInfoLocation() {
		try {
			return new String(prop.getProperty("sysInfoLocation").getBytes("iso-8859-1")) ;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/msg/system.xml";
		}
		 
	}
	
	/**
	 * 获取配置文件中的存放使用信息的位置字段
	 * @return
	 */
	public static String dirLabel() {
		try {
			String str= new String(prop.getProperty("dirLabel").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "功能说明：无内容";
		}
		 
	}
	
	/**
	 * 获取配置文件中的存放使用信息的位置字段
	 * @return
	 */
	public static String rightTaskPath() {
		try {
			String str= new String(prop.getProperty("rightTaskPath").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/msg/rightTask.txt";
		}
		 
	}
	
	/**
	 * 获取配置文件中的存放文件类型的位置字段
	 * @return
	 */
	public static String fileTypePath() {
		try {
			String str= new String(prop.getProperty("fileTypePath").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/msg/fileType.txt";
		}
		 
	}
	
	/**
	 * 获取配置文件中的存放文件类型的位置字段
	 * @return
	 */
	public static String tempModelPath() {
		try {
			String str= new String(prop.getProperty("tempModelPath").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/TempModel";
		}
		 
	}
	//searchIcon
	/**
	 * 获取配置文件中得搜索图标
	 * @return
	 */
	public static String searchIcon() {
		try {
			String str= new String(prop.getProperty("searchIcon").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/imgs/search.png";
		}
		 
	}
	
	
	
	/**
	 * 获取法律文档存放的位置
	 * @return
	 */
	public static String lawFilePath() {
		try {
			String str= new String(prop.getProperty("lawFilePath").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
		 
	}
	
	/**
	 * 获取法律文档存放的位置
	 * @return
	 */
	public static String readme() {
		try {
			String str= new String(prop.getProperty("readme").getBytes("iso-8859-1")) ;
			
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return "resource/imgs/readme.png";
		}
		 
	}
}
