package filehelper.surface.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import filehelper.helper.FreeMarkHelper;
import filehelper.helper.ModelFileHelper;
import filehelper.helper.TxtHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.surface.table.CreateTable;
import filehelper.surface.table.FileTypeTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.model.DirAndFilesIn;
import filehelper.sys.model.FileTypeIn;

public class FilePanel implements PanelInter{

	private final String  fileTypePath=ConfigHelper.fileTypePath();
	@Override
	public JPanel createPanel() {
		
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		final TableInter tableClass = new FileTypeTable();
		String[][] rowDatas=tableClass.returnTableRowDatas(fileTypePath);
		final JTable table = tableClass.createTable(rowDatas, new String[]{"文件类型"}, null);
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
		JButton buttonReflush = new JButton("刷新");
		southPanel.add(labelA);
		southPanel.add(textFieldA);
		southPanel.add(buttonAdd);
		southPanel.add(buttonDel);
		southPanel.add(buttonReflush);
		buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = table.getSelectedRows(); // table
																// 默认情况容许多行选择
				Vector<String> rowData = new Vector<String>();
				String temp = textFieldA.getText();
				if (temp.trim().length() < 1) {
					JOptionPane.showMessageDialog(panel,
							  "新增名称不能为空！", "Warning",
							  JOptionPane.WARNING_MESSAGE); 
				} else {
					rowData.add(ConfigVariable.Sys_Space + textFieldA.getText());
					//defaultTableModel.addRow(rowData);
					//String dirAndIn=textFieldA.getText().trim();
					FileTypeIn fileIn=new FileTypeIn();
					Map<String, Object>map=fileIn.findDirByValue(textFieldA.getText().trim(), null);
					if(map.get("obj")!=null){
						JOptionPane.showMessageDialog(panel,
								  "该文件类型已存在！", "Warning",
								  JOptionPane.WARNING_MESSAGE);
					}else{
						fileIn.setFilePath(textFieldA.getText().trim());
						fileIn.setDate(DateUtil.dateToString(new Date(), ConfigVariable.SYS_TIME));
						TxtHelper.writeIntoTxt(fileTypePath,fileIn);
						ModelFileHelper.createModel(fileIn.getFilePath());
						textFieldA.setText(null);
						tableClass.reloadTable(fileTypePath, table);
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
				//int[] selectedColumns = table.getSelectedColumns(); 
				System.out.println(selectedRows);
				//System.out.println(selectedColumns);
				// 默认情况容许多行选择
				for (int i = 0; i < selectedRows.length; i++) {
					// System.out.println(selectedRows[i]);
					int row=selectedRows[i] ;
					//int column=selectedColumns[i];
					
					
					FileTypeIn fileIn=new FileTypeIn();
					System.out.println("被删除的行号：" + (selectedRows[i] - i));
					String value=table.getValueAt(row, 0).toString().trim();
					Map<String, Object> map=fileIn.findDirByValue(value, null);
					TxtHelper.deleteFromTxt(fileTypePath,Integer.parseInt(map.get("index").toString()));
					fileIn=(FileTypeIn) map.get("obj");
					List<DirAndFilesIn> dirIns=TxtHelper.readFromTxt(ConfigHelper.rightTaskPath());
					for (int j = 0; j < dirIns.size(); j++) {
						DirAndFilesIn dirIn=dirIns.get(i);
						dirIn.deleteFileInDir(fileIn.getFilePath(), dirIn);
					}
					TxtHelper.editAllTxt(ConfigHelper.rightTaskPath(), dirIns);
				}
				tableClass.reloadTable(fileTypePath, table);
			}

		});
		buttonReflush.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tableClass.reloadTable(fileTypePath, table);
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
