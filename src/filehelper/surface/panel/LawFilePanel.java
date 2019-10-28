package filehelper.surface.panel;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Element;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.DateUtil;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.surface.table.FileTypeTable;
import filehelper.surface.table.LawFilesTable;
import filehelper.surface.table.SysDirTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.SysFloder;

public class LawFilePanel implements PanelInter {

	private final String lawDir=ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_LAW_DIR;
	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		final JPanel panel = new JPanel();
		final SysXMLHandler sysXML=new SysXMLHandler();
		final Element element=sysXML.getElement(lawDir);
		final TableInter tableClass = new LawFilesTable();
		String dirNow = element.getText();
		panel.setLayout(null);
		JButton openFile = new JButton("打开文件夹");
		openFile.setBounds(new Rectangle(CommonHelper.returnWidth(0.6)
				+ CommonHelper.returnWidth(0.05), 30, CommonHelper
				.returnWidth(0.26), CommonHelper.returnHeight(0.08)));
		final JTextField txtFile = new JTextField(0);
		txtFile.setText(dirNow);
		txtFile.setBounds(10, 30, CommonHelper.returnWidth(0.6),
				CommonHelper.returnHeight(0.08));
		panel.add(txtFile);
		panel.add(openFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, CommonHelper.returnHeight(0.2),
				CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.4));
		// scrollPane.setBorder(new TitledBorder(""));
		final JTable table = tableClass.createTable(tableClass.returnTableRowDatas(dirNow),
				new String[] { "法律文档列表" }, null);

		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser(txtFile.getText());
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int state = jfc.showOpenDialog(panel);
				/**
				 * 如果打开文件选择面板后，没有触发确定选择，而是触发了取消和关闭按钮，则state=1，直接跳出
				 */
				if (state == 1) {
					return;
				}
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println("文件夹:" + file.getAbsolutePath());
					txtFile.setText(file.getAbsolutePath());

					
					if(element!=null){
						sysXML.updateNode(file.getAbsolutePath(), element);
						tableClass.reloadTable(file.getAbsolutePath(), table);
					}
				}
			}
		});
		
		scrollPane.setViewportView(table);
		panel.add(scrollPane);
		JButton search = new JButton("搜索文档");
		search.setFont(new Font("微软雅黑", 1, 16));
		search.setBounds(new Rectangle(10, CommonHelper.returnHeight(0.65), CommonHelper
				.returnWidth(0.95), CommonHelper.returnHeight(0.08)));
		panel.add(search);
		JButton fresh = new JButton("刷新文档");
		fresh.setFont(new Font("微软雅黑", 1, 16));
		fresh.setBounds(new Rectangle(10, CommonHelper.returnHeight(0.76), CommonHelper
				.returnWidth(0.95), CommonHelper.returnHeight(0.08)));
		panel.add(fresh);
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CreateDiaog cd=new CreateDiaog();
				cd.findLawFileoptionPane(txtFile.getText().trim());
			}
		});
		fresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//sysXML.updateNode(file.getAbsolutePath(), element);
				tableClass.reloadTable(txtFile.getText(), table);
			}
		});
		return panel;
	}

	
}
