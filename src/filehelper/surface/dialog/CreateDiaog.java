package filehelper.surface.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.CreateDirAndFile;
import filehelper.helper.DateUtil;
import filehelper.helper.ModelFileHelper;
import filehelper.helper.TxtHelper;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.table.AllSearchTable;
import filehelper.surface.table.DirTaskTable;
import filehelper.surface.table.LawFilesTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysDataCorrect;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;
import filehelper.sys.model.FileTypeIn;

public class CreateDiaog {

	private final String taskPath = ConfigHelper.rightTaskPath();
	private final String fileTypePath=ConfigHelper.fileTypePath();

	/**
	 * 快捷键生成文件夹
	 */
	public void returnCreateAddDiaog() {
		String newDir = (String) JOptionPane.showInputDialog(null, "请输入要新建的文件夹名称：\n","录入新文件夹",JOptionPane.PLAIN_MESSAGE,null,null,
				"");
		System.out.println(newDir);
		if (newDir != null && newDir.length() >=1) {
			DirAndFilesIn dirIn = new DirAndFilesIn();
			dirIn.setDir(newDir);
			dirIn.setDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			TxtHelper.writeIntoTxt(taskPath, dirIn);
			List<DirAndFilesIn> dirIns = TxtHelper.readFromTxt(taskPath);
			DirTaskTable table=new DirTaskTable();
			///table.reloadTable(taskPath, table);
			JDialog dialog = returnFileTypeAddDialog(dirIns.size()-1);
			dialog.show();
		}
	}
	
	/**
	 * 快捷键生成文件类型
	 */
	public void returnCreateFileType() {
		String newDir = (String) JOptionPane.showInputDialog(null, "请输入要新建的文件类型名称：\n","录入新类型",JOptionPane.PLAIN_MESSAGE,null,null,
				"");
		System.out.println(newDir);
		if (newDir != null && newDir.length() > 0) {
			
			TxtHelper.writeIntoTxt(fileTypePath, new FileTypeIn(fileTypePath, DateUtil.dateToString(new Date(), ConfigVariable.SYS_TIME)));
			File selectedFile=ModelFileHelper.createModel(newDir);
			int temp=JOptionPane.showConfirmDialog(null, "是否开始编辑?", "提示",JOptionPane.YES_NO_OPTION);
			System.out.println(temp);
			if(temp==0){//点击了是选项---第一个选项
				System.out.println(selectedFile.getAbsolutePath());
				WindowOpen.openInWin(selectedFile);
			}
		}
	}
	
	/**
	 * 快捷键生成所有未生成到目录的文件夹
	 */
	public void returnCreateAllDir() {
		int temp= JOptionPane.showConfirmDialog(null, "确认要生成所有未生成的文件夹吗?", "提示",JOptionPane.YES_NO_OPTION);
		if(temp==0){
			SysDataCorrect.sysDataCorrectForDir(ConfigHelper.rightTaskPath(), new SysXMLHandler().getUsedSysDirName());
			JOptionPane.showMessageDialog(null,"生成所有未生成的文件夹————完成！", "生成成功",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 文件夹查询弹出框
	 */
	public void findDirOptionPane() {
		
		String newDir = (String) JOptionPane.showInputDialog(null,
				"搜索（文件夹）：\n", "文件夹搜索", JOptionPane.PLAIN_MESSAGE, null,
				null, "");
		List<DirAndFilesIn> strIn=null;
		if (newDir != null && newDir.trim().length() >= 1) {
			List<DirAndFilesIn> dirIns = TxtHelper
					.readFromTxt(taskPath);
			strIn = new ArrayList<>();
			for (DirAndFilesIn dirAndFilesIn : dirIns) {
				String dir = dirAndFilesIn.getDir();
				String path = dirAndFilesIn.getFilePath();
				String date=dirAndFilesIn.getDate();
				if (dir.contains(newDir)||dir.toLowerCase().contains(newDir.toLowerCase())||
						date.contains(newDir)) {
					strIn.add(dirAndFilesIn);
				}
			}
			String[][] rowDatas=new String[strIn.size()][1];
			for (int i = 0; i <strIn.size(); i++) {
				rowDatas[i]=new String[]{ConfigVariable.Sys_Space+ strIn.get(i).getDir(),strIn.get(i).getDate()};
			}
			
			TableInter tableClass=new DirTaskTable();
			JTable table= tableClass.createTable(rowDatas, new String[]{"文件夹搜索结果","新建时间"}, null);
			returnDirFindDialog(table);
		}
	}
	
	
	
	/**
	 * 文件夹查询列表返回弹出框
	 * @return
	 */
	public JDialog returnDirFindDialog(JTable table){
		JDialog dialog=new JDialog();
		dialog.setTitle("搜索结果");
		dialog.setIconImage(new ImageIcon(ConfigHelper.searchIcon())
		.getImage());
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setSize(450, CommonHelper.returnHeight(0.8));
		JScrollPane scrollPane = new JScrollPane(table);
		dialog.add(scrollPane);
		dialog.show();
		return dialog;
	}
	
	
	/**
	 * 法律文档查询弹出框
	 */
	public void findLawFileoptionPane(String path) {
		
		String newDir = (String) JOptionPane.showInputDialog(null,
				"搜索（法律文档）：\n", "法律文档搜索", JOptionPane.PLAIN_MESSAGE, null,
				null, "");
		if (newDir != null && newDir.trim().length() >= 1) {
			List<String> strIn=new ArrayList<String>();
			TableInter tableClass=new LawFilesTable();
			String[][] rowDatas=tableClass.returnTableRowDatas(path);
			for (int i = 0; i < rowDatas.length; i++) {
				if( rowDatas[i][0].contains(newDir)||rowDatas[i][0].toLowerCase().contains(newDir.toLowerCase())){
					strIn.add(rowDatas[i][0]);
					continue;
				}
			}
			String[][] result=new String[strIn.size()][1];
			for (int i = 0; i < strIn.size(); i++) {
				result[i]=new String[]{ strIn.get(i)};
			}
			JTable table= tableClass.createTable(result, new String[]{"文档搜索列表"}, null);
			returnDirFindDialog(table);
		}
	}
	
	/**
	 * 全局搜索弹出框
	 */
	public void findAlloptionPane() {
		
		String newDir = (String) JOptionPane.showInputDialog(null,
				"搜索（全局文件夹）：\n", "全局文件夹搜索", JOptionPane.PLAIN_MESSAGE, null,
				null, "");
		if (newDir != null && newDir.trim().length() >= 1) {
			TableInter tableClass=new AllSearchTable();
			JTable table= tableClass.createTable(tableClass.returnTableRowDatas(newDir), 
					new String[]{"文件夹名","最后修改时间","文件夹地址"}, null);
			returnDirFindDialog(table);
		}
	}

	/**
	 * 生成指定文件夹的类型文件选择Diaog
	 * 
	 * @param selectedIndex
	 * @return
	 */
	public JDialog returnFileTypeAddDialog(final int selectedIndex) {
		final JDialog dialog = new JDialog();
		final JPanel panelSmall = new JPanel();
		panelSmall.setBorder(new TitledBorder("文件类型列表"));
		// JScrollPane scrollPane = new JScrollPane(panelSmall);
		// scrollPane.setBorder(new TitledBorder("文件类型列表"));
		dialog.setTitle("文件类型选择");
		dialog.setIconImage(new ImageIcon(ConfigHelper.fileTypeIcon())
				.getImage());
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		// dialog.setBounds(CommonHelper.returnWidth(0.4),
		// CommonHelper.returnHeight(0.4),
		// CommonHelper.returnWidth(0.1),
		// CommonHelper.returnHeight(0.1));

		// scrollPane.add(panelSmall);
		List<FileTypeIn> lists = TxtHelper.readFromTxt(ConfigHelper.fileTypePath());
		final DirAndFilesIn tempIn = TxtHelper.getRowByIndex(taskPath,
				selectedIndex);
		List<String> tempList = tempIn.getFiles();
		dialog.setSize(CommonHelper.returnWidth(0.9),CommonHelper.returnHeight(0.9));
		for (FileTypeIn fileIn : lists) {
			try {
				JCheckBox check = new JCheckBox(fileIn.getFilePath());
				if (tempList != null && tempList.contains(fileIn.getFilePath())) {
					check.setSelected(true);
				}
				check.setFont(new Font("微软雅黑", 0, 13));
				panelSmall.add(check);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		/**
		 * 加入全选/全不选的check
		 */
		final JCheckBox check = new JCheckBox("全选/全不选");
		check.setFont(new Font("微软雅黑", 1, 16));
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Component[] comps = panelSmall.getComponents();
				if(check.isSelected()){
					if (comps.length > 0) {
						for (int i = 0; i < comps.length; i++) {
							Component comp = comps[i];
							if (comp instanceof JCheckBox) {
								JCheckBox box = (JCheckBox) comp;
								box.setSelected(true);
							}
						}
					}
				}else{
					if (comps.length > 0) {
						for (int i = 0; i < comps.length; i++) {
							Component comp = comps[i];
							if (comp instanceof JCheckBox) {
								JCheckBox box = (JCheckBox) comp;
								box.setSelected(false);
							}
						}
					}
				}
			}
		});
		panelSmall.add(check);
		
		dialog.add(panelSmall, BorderLayout.CENTER);
		
		/**
		 * 添加 -新增类型- 按钮及事件
		 */
		JButton addFile = new JButton("新增类型");
		addFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<String> list = new ArrayList<String>();
				Component[] comps = panelSmall.getComponents();
				if (comps.length > 0) {
					for (int i = 0; i < comps.length; i++) {
						Component comp = comps[i];
						if (comp instanceof JCheckBox) {
							JCheckBox box = (JCheckBox) comp;
							if (box.isSelected()) {
								list.add(box.getText());
							}
						}
					}
				} /*
				 * else { JOptionPane.showMessageDialog(null, "未选择文档类型"); }
				 */
				if (list != null && list.size() > 0) {
					try {
						DirAndFilesIn editIn = TxtHelper.getRowByIndex(
								taskPath, selectedIndex);
						editIn.setFiles(list);
						TxtHelper.editFromTxt(taskPath, selectedIndex, editIn);
						int tempInt = JOptionPane.showConfirmDialog(null,
								"新增完成，是否退出?", "标题", JOptionPane.YES_NO_OPTION);
						if (tempInt == 0) {
							dialog.dispose();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "出錯了！.",
								e2.getMessage(), JOptionPane.ERROR_MESSAGE);
						dialog.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "未选择文档类型");
				}

			}

		});
		
		/**
		 * 添加 -取消- 按钮及事件
		 */
		JButton cancle = new JButton("取消");
		cancle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dialog.dispose();
			}
		});
		
		/**
		 * 添加 -加入类型文件- 按钮及事件
		 */
		JButton createFileByModel = new JButton("加入类型文件");
		createFileByModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<String> list = new ArrayList<String>();
				Component[] comps = panelSmall.getComponents();
				if (comps.length > 0) {
					for (int i = 0; i < comps.length; i++) {
						Component comp = comps[i];
						if (comp instanceof JCheckBox) {
							JCheckBox box = (JCheckBox) comp;
							if (box.isSelected()) {
								list.add(box.getText());
							}
						}
					}
				} /*
				 * else { JOptionPane.showMessageDialog(null, "未选择文档类型"); }
				 */
				if (list != null && list.size() > 0) {
					try {
						DirAndFilesIn editIn = TxtHelper.getRowByIndex(
								taskPath, selectedIndex);

						editIn.setFiles(list);
						String path=CreateDirAndFile.createModelFileList(list,
								editIn.getDir());// 加入文件类型到文件夹
						editIn.setFilePath(path);
						TxtHelper.editFromTxt(taskPath, selectedIndex, editIn);// 加入文件类型时同时加入类型到对象
						int tempInt = JOptionPane.showConfirmDialog(null,
								"模板文档已生成，是否退出?", "标题",
								JOptionPane.YES_NO_OPTION);
						if (tempInt == 0) {
							dialog.dispose();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "出錯了！.",
								e2.getMessage(), JOptionPane.ERROR_MESSAGE);
						dialog.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "未选择文档类型");
				}

			}

		});
		JPanel panelSouth = new JPanel();
		panelSouth.add(addFile);
		panelSouth.add(cancle);
		panelSouth.add(createFileByModel);
		dialog.add(panelSouth, BorderLayout.SOUTH);

		return dialog;
	}

	private int returnHight(int allSize) {
		int height = 150;
		int defalut = 6;
		double result = allSize / defalut;
		result = result <= 1 ? 1 : result;
		int returnRes = (int) (height * result);
		System.out.println(result + "  is:" + result * 0.8 * height);
		return returnRes;
	}
}
