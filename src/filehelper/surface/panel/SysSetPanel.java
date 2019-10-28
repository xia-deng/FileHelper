package filehelper.surface.panel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.dom4j.Element;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.XMLHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.sys.SysXMLHandler;

public class SysSetPanel implements PanelInter {

	private final Map<String, Integer> mapKeys=new HashMap<String, Integer>(){
		{
			put("Ctrl", 2);
			put("Shift", 3);
			put("Alt", 1);
			put("Win", 8);
		}
	};
	private Map<String, Object> tempMap=new HashMap<String, Object>();
	
	private List<String> secondKeys=Arrays.asList("0","1","2","3","4","5","6","7","8","9","A","B","D","E","F","G","H","I","K","L","M","N","O","P","Q","R","S","T","U","V","W","Y");
	private List<String> tempList=new ArrayList<String>();
	
	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		final SysXMLHandler sysXml = new SysXMLHandler();
		JPanel panel = new JPanel();
		panel.setLayout(null);
		final Map<String, Object> elementValues=initSysSet();
		tempMap=elementValues;
		final JPanel panelKey=new JPanel();
		panelKey.setLayout(null);
		panelKey.setBorder(new TitledBorder("快捷键设置"));
		panelKey.setBounds(10, 10, CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.55));
		//新增文件夹按键设置
		JLabel lableDirAdd=new JLabel("新增文件夹:");
		lableDirAdd.setBounds(20,20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldDirAdd=elementValues.get(ConfigVariable.XML_DIRADD_MARK_2).toString();
		final JComboBox txtDirAdd=returnSecondCombox(tempList,oldDirAdd);
		//txtDirAdd.addFocusListener(returnFocusListener(txtDirAdd,ConfigVariable.XML_DIRADD_MARK_2));
		txtDirAdd.addActionListener(returnComboxChanged(txtDirAdd,ConfigVariable.XML_DIRADD_MARK_2,oldDirAdd));
		txtDirAdd.setBounds(CommonHelper.returnWidth(0.6), 20, CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableDirAdd);
		panelKey.add(txtDirAdd);
		
		//新增文件类型按键设置
		JLabel lableFileTypeAdd=new JLabel("新增文件类型:");
		lableFileTypeAdd.setBounds(20,CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldFileType=elementValues.get(ConfigVariable.XML_FILETYPEADD_MARK_2).toString();
		final JComboBox txtFileTypeAdd=returnSecondCombox(tempList,oldFileType);
		//txtFileTypeAdd.addFocusListener(returnFocusListener(txtFileTypeAdd,ConfigVariable.XML_FILETYPEADD_MARK_2));
		txtFileTypeAdd.addActionListener(returnComboxChanged(txtFileTypeAdd,ConfigVariable.XML_FILETYPEADD_MARK_2,oldFileType));
		txtFileTypeAdd.setBounds(CommonHelper.returnWidth(0.6), CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableFileTypeAdd);
		panelKey.add(txtFileTypeAdd);
		
		//文件夹搜索按键设置
		JLabel lableDirSearch=new JLabel("文件夹搜索:");
		lableDirSearch.setBounds(20,2*CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldDirSearch=elementValues.get(ConfigVariable.XML_DIRSEARCH_MARK_2).toString();
		final JComboBox txtDirSearch=returnSecondCombox(tempList, oldDirSearch);
		//txtDirSearch.addFocusListener(returnFocusListener(txtDirSearch,ConfigVariable.XML_DIRSEARCH_MARK_2));
		txtDirSearch.addActionListener(returnComboxChanged(txtDirSearch,ConfigVariable.XML_DIRSEARCH_MARK_2,oldDirSearch));
		txtDirSearch.setBounds(CommonHelper.returnWidth(0.6), 2*CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableDirSearch);
		panelKey.add(txtDirSearch);
		
		//法律文档搜索按键设置
		JLabel lableLawSearch=new JLabel("法律文档搜索:");
		lableLawSearch.setBounds(20,3*CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldLawSearch=elementValues.get(ConfigVariable.XML_LAWSEARCH_MARK_2).toString();
		final JComboBox txtLawSearch=returnSecondCombox(tempList,oldLawSearch);
		//txtLawSearch.addFocusListener(returnFocusListener(txtLawSearch,ConfigVariable.XML_LAWSEARCH_MARK_2));
		txtLawSearch.addActionListener(returnComboxChanged(txtLawSearch,ConfigVariable.XML_LAWSEARCH_MARK_2,oldLawSearch));
		txtLawSearch.setBounds(CommonHelper.returnWidth(0.6), 3*CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableLawSearch);
		panelKey.add(txtLawSearch);
		
		//文件夹创建按键设置
		JLabel lableDirCreate=new JLabel("文件夹创建:");
		lableDirCreate.setBounds(20,4*CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldDirCreate=elementValues.get(ConfigVariable.XML_DIRCREATE_MARK_2).toString();
		final JComboBox txtDirCreate=returnSecondCombox(tempList,oldDirCreate);
		//txtDirCreate.addFocusListener(returnFocusListener(txtDirCreate,ConfigVariable.XML_DIRCREATE_MARK_2));
		txtDirCreate.addActionListener(returnComboxChanged(txtDirCreate,ConfigVariable.XML_DIRCREATE_MARK_2,oldDirCreate));
		txtDirCreate.setBounds(CommonHelper.returnWidth(0.6), 4*CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableDirCreate);
		panelKey.add(txtDirCreate);
		
		//全目录搜索按键设置
		JLabel lableAllSearch=new JLabel("全目录搜索:");
		lableAllSearch.setBounds(20,5*CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldAllSearch=elementValues.get(ConfigVariable.XML_ALLSEARCH_MARK_2).toString();
		final JComboBox txtAllSearch=returnSecondCombox(tempList,oldAllSearch);
		//txtAllSearch.addFocusListener(returnFocusListener(txtAllSearch,ConfigVariable.XML_ALLSEARCH_MARK_2));
		txtAllSearch.addActionListener(returnComboxChanged(txtAllSearch,ConfigVariable.XML_ALLSEARCH_MARK_2,oldAllSearch));
		txtAllSearch.setBounds(CommonHelper.returnWidth(0.6), 5*CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableAllSearch);
		panelKey.add(txtAllSearch);
		
		//退出按键设置
		JLabel lableQuiet=new JLabel("退出系统:");
		lableQuiet.setBounds(20,6*CommonHelper.returnHeight(0.05)+20,CommonHelper.returnWidth(0.22),CommonHelper.returnHeight(0.05));
		String oldQuiet=elementValues.get(ConfigVariable.XML_QUIET_MARK_2).toString();
		final JComboBox txtQuiet=returnSecondCombox(tempList,oldQuiet);
		//txtQuiet.addFocusListener(returnFocusListener(txtQuiet,ConfigVariable.XML_QUIET_MARK_2));
		txtQuiet.addActionListener(returnComboxChanged(txtQuiet,ConfigVariable.XML_QUIET_MARK_2,oldQuiet));
		txtQuiet.setBounds(CommonHelper.returnWidth(0.6), 6*CommonHelper.returnHeight(0.05)+20, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(lableQuiet);
		panelKey.add(txtQuiet);
		
		//保存设置按钮
		JButton saveBtn=new JButton("保存");
		saveBtn.setBounds(CommonHelper.returnWidth(0.6), 7*CommonHelper.returnHeight(0.05)+50, 
				CommonHelper.returnWidth(0.22), CommonHelper.returnHeight(0.05));
		panelKey.add(saveBtn);
		
		//第一热键设置
		final JComboBox comBox=returnComboBox(elementValues.get(ConfigVariable.XML_MARK_1).toString());
		comBox.setBounds(CommonHelper.returnWidth(0.33), 4*CommonHelper.returnHeight(0.05), 
				CommonHelper.returnWidth(0.2), CommonHelper.returnHeight(0.05));
		panelKey.add(comBox);
		
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					sysXml.updateNodes(tempMap);
					JOptionPane.showMessageDialog(null, "修改成功！");
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "修改失败！");
				}
				
				
				
				// TODO Auto-generated method stub
				/*String mark1=(comBox.getSelectedObjects()[0].toString());
				sysXml.updateNode(mark1, sysXml.getElement(
						ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_MARK_1));*/
				/*String dirAdd=txtDirAdd.getText();
				if(dirAdd!=null&&dirAdd.trim().length()>0){
					sysXml.updateNode(dirAdd, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_DIRADD_MARK_2));
				}
				String fileTypeAdd=txtFileTypeAdd.getText();
				if(fileTypeAdd!=null&&fileTypeAdd.trim().length()>0){
					sysXml.updateNode(fileTypeAdd, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_FILETYPEADD_MARK_2));
				}
				String dirSearch=txtDirSearch.getText();
				if(dirSearch!=null&&dirSearch.trim().length()>0){
					sysXml.updateNode(dirSearch, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_DIRSEARCH_MARK_2));
				}
				String lawSearch=txtLawSearch.getText();
				if(lawSearch!=null&&lawSearch.trim().length()>0){
					sysXml.updateNode(lawSearch, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_LAWSEARCH_MARK_2));
				}
				String dirCreate=txtDirCreate.getText();
				if(dirCreate!=null&&dirCreate.trim().length()>0){
					sysXml.updateNode(dirCreate, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_DIRCREATE_MARK_2));
				}
				String allSearch=txtAllSearch.getText();
				if(allSearch!=null&&allSearch.trim().length()>0){
					sysXml.updateNode(allSearch, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_ALLSEARCH_MARK_2));
				}
				String quiet=txtQuiet.getText();
				if(quiet!=null&&quiet.trim().length()>0){
					sysXml.updateNode(quiet, sysXml.getElement(
							ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_MARK+"/"+ConfigVariable.XML_QUIET_MARK_2));
				}*/
			}
		});
		
		panel.add(panelKey);
		return panel;
	}
	
	private JComboBox returnComboBox(String index){
		String[][] strs=new String[][]{{"Ctrl","2"},{"Shift","3"},{"Alt","1"},{"Win","8"}};
		String[] names=new String[]{"Ctrl","Shift","Alt","Win"};
		//List<String> lists=Arrays.asList(names);
		String selected=null;
		for (int i = 0; i < strs.length; i++) {
			String[] temps=strs[i];
			if(temps[1].equals(index)||temps[1]==index||
					temps[0].toLowerCase()==index.toLowerCase()||temps[0].toLowerCase().equals(index.toLowerCase())){
				selected=names[i];
				break;
			}
		}
		JComboBox combo=new JComboBox(names);
		combo.setSelectedItem(selected);
		return combo;
	}
	
	private JComboBox returnSecondCombox(List<String> tempList,String index){
		secondKeys.removeAll(tempList);
		JComboBox combo=new JComboBox(secondKeys.toArray());
		//tempList.add(index);
		combo.setSelectedItem(index);
		return combo;
	}
	
	private JTextField returnTxtFiled(String txt){
		JTextField txtFiled=new JTextField(txt);
		return txtFiled;
	}

	public Map<String, Object> initSysSet() {
		Map<String, Object> map=new HashMap<String, Object>();
		SysXMLHandler sysXml = new SysXMLHandler();
		String eleStr = ConfigVariable.XML_ROOT + "/" + ConfigVariable.XML_MARK;
		Element newEle = sysXml.getElement(eleStr);
		if (!XMLHelper.nodeExit(eleStr)) {
			Element root = XMLHelper.getXmlRoot();
			Element marks = root.addElement(ConfigVariable.XML_MARK);
			marks.addElement(ConfigVariable.XML_MARK_1).setText("Ctrl");
			map.put(ConfigVariable.XML_MARK_1, "Ctrl");
			marks.addElement(ConfigVariable.XML_DIRADD_MARK_2).setText("1");
			map.put(ConfigVariable.XML_DIRADD_MARK_2, "1");
			marks.addElement(ConfigVariable.XML_FILETYPEADD_MARK_2)
					.setText("2");
			map.put(ConfigVariable.XML_FILETYPEADD_MARK_2, "2");
			marks.addElement(ConfigVariable.XML_DIRSEARCH_MARK_2).setText("3");
			map.put(ConfigVariable.XML_DIRSEARCH_MARK_2, "3");
			marks.addElement(ConfigVariable.XML_LAWSEARCH_MARK_2).setText("4");
			map.put(ConfigVariable.XML_LAWSEARCH_MARK_2, "4");
			marks.addElement(ConfigVariable.XML_DIRCREATE_MARK_2).setText("A");
			map.put(ConfigVariable.XML_DIRCREATE_MARK_2, "A");
			marks.addElement(ConfigVariable.XML_ALLSEARCH_MARK_2).setText("D");
			map.put(ConfigVariable.XML_ALLSEARCH_MARK_2, "D");
			marks.addElement(ConfigVariable.XML_QUIET_MARK_2).setText("Q");
			map.put(ConfigVariable.XML_QUIET_MARK_2, "Q");
			XMLHelper.writeToXML(ConfigHelper.sysInfoLocation());
			newEle = sysXml.getElement(eleStr);
		}else{
			map.put(ConfigVariable.XML_MARK_1, newEle.element(ConfigVariable.XML_MARK_1).getText());
			map.put(ConfigVariable.XML_DIRADD_MARK_2, newEle.element(ConfigVariable.XML_DIRADD_MARK_2).getText());
			map.put(ConfigVariable.XML_FILETYPEADD_MARK_2, newEle.element(ConfigVariable.XML_FILETYPEADD_MARK_2).getText());
			map.put(ConfigVariable.XML_DIRSEARCH_MARK_2, newEle.element(ConfigVariable.XML_DIRSEARCH_MARK_2).getText());
			map.put(ConfigVariable.XML_LAWSEARCH_MARK_2, newEle.element(ConfigVariable.XML_LAWSEARCH_MARK_2).getText());
			map.put(ConfigVariable.XML_DIRCREATE_MARK_2, newEle.element(ConfigVariable.XML_DIRCREATE_MARK_2).getText());
			map.put(ConfigVariable.XML_ALLSEARCH_MARK_2, newEle.element(ConfigVariable.XML_ALLSEARCH_MARK_2).getText());
			map.put(ConfigVariable.XML_QUIET_MARK_2, newEle.element(ConfigVariable.XML_QUIET_MARK_2).getText());
		}
		return map;
	}
	
	private FocusListener returnFocusListener(final JTextField txtFiled,final String key){
		return new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				String value=txtFiled.getText();
				if(value.length()==1&&value!=""&&!value.equals("")&&tempMap.containsValue(value)==false){
					tempMap.put(key, value);
				}else{
					//txtFiled.requestFocus();
					txtFiled.setText("快捷键不合法！");
					//txtFiled.setFont(new f);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txtFiled.setText("");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener returnComboxChanged(final JComboBox combox,final String key,final String oldValue){
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String value=combox.getSelectedObjects()[0].toString();
				if(tempMap.containsValue(value)){
					JOptionPane.showMessageDialog(null, "快捷键设置重复"); 
					//tempMap.put(key, oldValue);
				}else{
					tempMap.put(key, value);
				}
				System.out.println(tempMap);
			}
		};
	}
	
	private String returnPath(Map<String, String> map,String value){
		String str=null;
		for (String key : map.keySet()) {
			String mapValue=map.get(key);
			if(value.equals(mapValue)||value==mapValue){
				str= key;
				break;
			}
		}
		return str;
	}
}
