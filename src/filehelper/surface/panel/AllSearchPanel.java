package filehelper.surface.panel;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.DateUtil;
import filehelper.helper.FileUtility;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.surface.table.AllSearchTable;
import filehelper.surface.table.LawFilesTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;

public class AllSearchPanel implements PanelInter {

	private String[] columns=new String[]{"文件夹名","最后修改时间","文件夹地址"};
	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		final JPanel panel = new JPanel();
		final TableInter tableClass = new AllSearchTable();
		// String dirNow = ConfigHelper.lawFilePath();
		panel.setLayout(null);
		JButton openFile = new JButton("搜索文件夹");
		openFile.setBounds(new Rectangle(CommonHelper.returnWidth(0.6)
				+ CommonHelper.returnWidth(0.05), 30, CommonHelper
				.returnWidth(0.26), CommonHelper.returnHeight(0.08)));
		final JTextField txtFile = new JTextField(0);
		txtFile.setText("");
		txtFile.setBounds(10, 30, CommonHelper.returnWidth(0.6),
				CommonHelper.returnHeight(0.08));
		panel.add(txtFile);
		panel.add(openFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, CommonHelper.returnHeight(0.2),
				CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.6));
		// scrollPane.setBorder(new TitledBorder(""));
		final JTable table = tableClass.createTable(tableClass.returnTableRowDatas(""), columns, null);

		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str = txtFile.getText().trim();
				tableClass.reloadTable(str, table);
			}
		});

		scrollPane.setViewportView(table);
		panel.add(scrollPane);

		return panel;
	}

}
