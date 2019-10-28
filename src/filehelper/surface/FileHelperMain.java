package filehelper.surface;

import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

import org.dom4j.Element;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.surface.panel.SysSetPanel;
import filehelper.sys.SysDataCorrect;
import filehelper.sys.SysXMLHandler;

public class FileHelperMain extends JFrame {

	// private CommonHelper comHelper=new CommonHelper();
	// private JPanel panel;
	public static final int DIR_ALL_KEY_MARK=6;
	public static final int DIR_CREATE_KEY_MARK=5;
	public static final int LAW_FIND_KEY_MARK=4;
	public static final int DIR_FIND_KEY_MARK=3;
	public static final int FILETYPE_KEY_MARK=2;
	public static final int DIR_KEY_MARK = 1;
	public static final int EXIT_KEY_MARK = 0;
	public static final int MAX_KEY_MARK=110;
	public static final int MIN_KEY_MARK=120;

	public FileHelperMain() {
		// TODO Auto-generated constructor stub
		super();
		SysDataCorrect.sysDataCorrectForDir(ConfigHelper.rightTaskPath(), new SysXMLHandler().getUsedSysDirName());
		//ConfigHelper.container = getContentPane(); 
		// 设置JFrame的属性
		this.setSize(ConfigHelper.mainWidth(), ConfigHelper.mainHeight());
		this.setAlwaysOnTop(false);
		this.setIconImage(CommonHelper.getIconImg().getImage());
		this.setTitle(ConfigHelper.mainName());
		this.setBackground(new java.awt.Color(153, 255, 102));
		// 设置窗口居中显示
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		// 加入TAB控件
		this.add(CommonHelper.createTabPane());
		//新增键盘监控事件
		/*KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager(); 
		manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {
			
			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				return true;
			}
		});*/
		System.out.println(ConfigHelper.lawFilePath());
		//第一步：注册热键，第一个参数表示该热键的标识，第二个参数表示组合键，如果没有则为0，第三个第三个参数为定义的主要热键
		//Alt:1;Ctrl:2;shift:3;win:8;
		JIntellitype.getInstance().registerHotKey(DIR_KEY_MARK, JIntellitype.MOD_CONTROL, 49); 
		JIntellitype.getInstance().registerHotKey(FILETYPE_KEY_MARK, JIntellitype.MOD_CONTROL, 50);
		JIntellitype.getInstance().registerHotKey(DIR_FIND_KEY_MARK, JIntellitype.MOD_CONTROL, 51);
		JIntellitype.getInstance().registerHotKey(LAW_FIND_KEY_MARK, JIntellitype.MOD_CONTROL, 52);
		JIntellitype.getInstance().registerHotKey(DIR_CREATE_KEY_MARK, JIntellitype.MOD_CONTROL, (int)'A');
		JIntellitype.getInstance().registerHotKey(DIR_ALL_KEY_MARK, JIntellitype.MOD_CONTROL, (int)'D');
	    JIntellitype.getInstance().registerHotKey(EXIT_KEY_MARK, JIntellitype.MOD_CONTROL, (int)'Q');
	    
	  //第二步：添加热键监听器
	     JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
	       
	       public void onHotKey(int markCode) {
	    	   CreateDiaog cd=new CreateDiaog();
	         switch (markCode) { 
	         case DIR_KEY_MARK: //打开新建文件夹热键：Alt+1
	           cd.returnCreateAddDiaog();
	           break; 
	         case FILETYPE_KEY_MARK://打开新建文件类型热键:Alt+3
	        	 cd.returnCreateFileType();
	        	 break;
	         case DIR_FIND_KEY_MARK://打开搜索文件夹热键:Alt+2
	        	 cd.findDirOptionPane();
	        	 break;
	         case LAW_FIND_KEY_MARK://打开文件夹生成快捷键:Alt+A
	        	 cd.findLawFileoptionPane(ConfigHelper.lawFilePath());//打开搜索文档热键:Alt+4
	        	 break;
	         case DIR_CREATE_KEY_MARK:
	        	 cd.returnCreateAllDir();
	        	 break;
	         case DIR_ALL_KEY_MARK:
	        	 cd.findAlloptionPane();
	        	 break;
	         case EXIT_KEY_MARK: //退出系统热键:Alt+Q
	           System.exit(0);
	           break;  
	         }         
	       }
	     }); 
		this.setVisible(true);
		//this.pack();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		FileHelperMain t = new FileHelperMain();
	}

	public void initTABPanel() {
		SysXMLHandler sysXML = new SysXMLHandler();
		if (sysXML.isFirstUse()) {
			sysXML.initSysFile();
		}
	}
	
	public Map<String, Integer> returnKeys(){
		SysSetPanel sysSet=new SysSetPanel();
		Map<String, Object> map=sysSet.initSysSet();
		Map<String,Integer> resultMap=new HashMap<String, Integer>();
		for (String key : map.keySet()) {
			resultMap.put(key, returnIntFromStringKey(map.get(key).toString()));
		}
		return resultMap;
	}
	
	public int returnIntFromStringKey(String key){
	
		int temp=0;
		switch (key) {
		case "Ctrl":
			temp=2;
			break;
		case "Shift":
			temp=3;
			break;
		case "Alt":
			temp=1;
			break;
		case "Win":
			temp=8;
		break;

		default:
			temp=Integer.parseInt(key);
			break;
		}
		return temp;
	}
	
}
