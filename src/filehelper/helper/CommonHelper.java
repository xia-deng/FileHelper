package filehelper.helper;

import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import filehelper.surface.panel.inter.PanelInter;

public class CommonHelper {

	public static ImageIcon getIconImg() {

		ImageIcon imageIcon;
		try {
			imageIcon = new ImageIcon(ConfigHelper.mainIcon());
		} catch (Exception e) {
			// TODO: handle exception
			imageIcon=null;
		}

		return imageIcon;
	}
	
	public static String encodeStr(String str){
		System.out.println(str);
		try {
			return new String(str.getBytes("gbk"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	
	
	public static String utf8Str(String str){
		try {
			return new String(str.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	
	/**
	 * 获取基于系统宽度乘以系数的控件宽度
	 * @param num 系数
	 * @return
	 */
	public static int returnWidth(Double num){
		return (int)(ConfigHelper.mainWidth()*num);
	}
	
	/**
	 * 获取基于系统高度乘以系数的控件高度
	 * @param num 系数
	 * @return
	 */
	public static int returnHeight(Double num){
		return (int)(ConfigHelper.mainHeight()*num);
	}
	
	/**
	 * 返回控件绘制的起点位置
	 * @param width
	 * @param height
	 * @return
	 */
	public static int[] returnCenterLocation(int width,int height){
		int[] ints=new int[2];
		
		return ints;
	}
	
	/**
	 * 生成TAB标签页
	 * @return
	 */
	public static JTabbedPane createTabPane(){
		
		final JTabbedPane tab=new JTabbedPane();
		/*String[] tabs=ConfigHelper.tabNames();
		for (String string : tabs) {
			tab.addTab(string, new Panel());
		}*/
		Map<String, Object> map=ConfigHelper.tabNames();
		System.out.println(map);
		for (String key : map.keySet()) {
		try {
				PanelInter panel= (PanelInter) Class.forName(map.get(key).toString()).newInstance();
				tab.addTab(new String(key.getBytes(),"utf-8"), panel.createPanel());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		tab.setSize(CommonHelper.returnWidth(1.0), CommonHelper.returnHeight(0.2));
		tab.setFont(new Font("黑体", Font.BOLD, CommonHelper.returnHeight(0.035)));
		return tab;
	}
}
