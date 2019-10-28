package filehelper.surface.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.DateUtil;
import filehelper.helper.FileUtility;
import filehelper.helper.TxtHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.surface.table.CreateTable;
import filehelper.surface.table.DirTaskTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysDataCorrect;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;
/**
 * 
 * 描述：RightPanel——文件夹管理
 * <br />@version:1.0.0
 * <br />@author 邓林峰
 * <br />@email： deng_xia@sina.cn
 * <br />@date： 2016年3月24日 下午8:09:40
 */
public class RightPanel implements PanelInter {

	private final String taskPath = ConfigHelper.rightTaskPath();

	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		final TableInter tableClass = new DirTaskTable();
		String[][] rowDatas = tableClass.returnTableRowDatas(taskPath);
		final JTable table = tableClass.createTable(rowDatas,
				new String[] { "文件夹列表","新建时间" }, null);
		final DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, CommonHelper.returnWidth(0.95),
				CommonHelper.returnHeight(0.7));
		// scrollPane.setBorder(new TitledBorder("右键文件名"));
		scrollPane.setViewportView(table);
		panel.add(scrollPane);
		final JPanel southPanel = new JPanel();
		southPanel.setBounds(10, CommonHelper.returnHeight(0.72),
				CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.2));
		panel.add(southPanel);
		JLabel labelA = new JLabel("名字: ");
		final JTextField textFieldA = new JTextField(15);

		JButton buttonAdd = new JButton("添加");
		JButton buttonDel = new JButton("删除");
		JButton buttonSearch = new JButton("搜索");
		JButton buttonReflush = new JButton("刷新/全部生成");
		//JButton buttonAddAll = new JButton("全部生成");
		southPanel.add(labelA);
		southPanel.add(textFieldA);
		southPanel.add(buttonAdd);
		southPanel.add(buttonDel);
		southPanel.add(buttonSearch);
		southPanel.add(buttonReflush);
		//southPanel.add(buttonAddAll);
		buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = table.getSelectedRows(); // table
																// 默认情况容许多行选择
				Vector<String> rowData = new Vector<String>();
				String temp = textFieldA.getText();
				if (temp.trim().length() < 1) {
					JOptionPane.showMessageDialog(panel, "新增名称不能为空！",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					rowData.add(ConfigVariable.Sys_Space + textFieldA.getText());
					String date=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
					rowData.add(date);
					//defaultTableModel.addRow(rowData);
					
					DirAndFilesIn dirAndIn = new DirAndFilesIn();
					Map<String, Object>map=dirAndIn.findDirByValue(textFieldA.getText().trim(), null);
					if(map.get("obj")!=null){
						JOptionPane.showMessageDialog(panel, "该文件夹已存在！",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}else{
						dirAndIn.setDir(textFieldA.getText().trim());
						dirAndIn.setDate(date);
						TxtHelper.writeIntoTxt(taskPath, dirAndIn);
						textFieldA.setText(null);
						tableClass.reloadTable(taskPath, table);
					}
					
				}
				
				/*
				 * if (selectedRows.length == 0) {
				 * defaultTableModel.addRow(rowData); textFieldA.setText(null);
				 * 
				 * } else if (selectedRows.length == 1) { //
				 * System.out.println(selectedRows[0]);
				 * defaultTableModel.insertRow(selectedRows[0] + 1, rowData);
				 * textFieldA.setText(null);
				 * 
				 * } else { JOptionPane.showMessageDialog(panel,
				 * "Your operation is forbidden", "Warning",
				 * JOptionPane.WARNING_MESSAGE); }
				 */
			}

		});
		buttonDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = table.getSelectedRows(); // table
																// 默认情况容许多行选择
				for (int i = 0; i < selectedRows.length; i++) {
					// System.out.println(selectedRows[i]);
					System.out.println("被删除的行号：" + (selectedRows[i] - i));
					/*DirAndFilesIn dirIn=TxtHelper.getRowByIndex(taskPath, selectedRows[i] - i);*/
					String value=table.getValueAt(selectedRows[i] - i, 0).toString().trim();
					String date=table.getValueAt(selectedRows[i] - i, 1).toString().trim();
					DirAndFilesIn dirIn=new DirAndFilesIn();
					Map<String, Object> map=dirIn.findDirByValue(value, date);
					dirIn=(DirAndFilesIn) map.get("obj");
					TxtHelper.deleteFromTxt(taskPath, Integer.parseInt(map.get("index").toString()));
					if(dirIn.getFilePath()!=null){
						File file=new File(dirIn.getFilePath());
						FileUtility.deleteDir(file);
						//file.delete();
					}
					
				}
				tableClass.reloadTable(taskPath, table);
			}

		});
		//搜索按鈕函數
		buttonSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CreateDiaog cd=new CreateDiaog();
				cd.findDirOptionPane();
			}

		});
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("here");
				if (e.getClickCount() == 1
						&& e.getButton() == MouseEvent.BUTTON1) {
					table.clearSelection();
				}
			}
		});
		buttonReflush.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SysDataCorrect.sysDataCorrectForDir(ConfigHelper.rightTaskPath(), new SysXMLHandler().getUsedSysDirName());
				
				tableClass.reloadTable(taskPath, table);
			}
		});

		// make the text field focused every time the window is activated

		// **************************************************************
		// This is a standard snippet to realize the desired column selection as
		// in
		// Excel
		// **************************************************************

		// row selection mode
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// column selection mode
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		// allow the column selection (the row selection is allowed by default)
		table.setColumnSelectionAllowed(true);

		return panel;
	}

}
