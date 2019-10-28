package filehelper.surface.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.IconView;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.DateUtil;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.panel.inter.PanelInter;
import filehelper.surface.table.CreateTable;
import filehelper.surface.table.SysDirTable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.SysFloder;

/**
 * 文件选择Panel
 * 
 * @author Administrator
 *
 */
public class DirPanel implements PanelInter {
	
	private final String dirPath=ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir+"/"+ConfigVariable.XML_Dir;

	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub

		/**
		 * 设定文件选择工具
		 */
		final SysXMLHandler sysXMl = new SysXMLHandler();
		final TableInter tableClass=new SysDirTable();
		//JTable table=tableClass.
		String dirNow = sysXMl.getUsedSysDir() == null ? "" : sysXMl
				.getUsedSysDir().element(ConfigVariable.XML_Dir).getText();

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder("目录定位"));
		JButton openFile = new JButton("打开文件夹");

		// openFile.setPreferredSize(new
		// Dimension(CommonHelper.returnWidth(0.26) ,
		// CommonHelper.returnHeight(0.1)));
		openFile.setBounds(new Rectangle(CommonHelper.returnWidth(0.6)
				+ CommonHelper.returnWidth(0.05), 30, CommonHelper
				.returnWidth(0.26), CommonHelper.returnHeight(0.1)));
		final JTextField txtFile = new JTextField(0);
		txtFile.setText(dirNow);
		// txtFile.setPreferredSize(new Dimension(CommonHelper.returnWidth(0.6),
		// CommonHelper.returnHeight(0.1)));
		txtFile.setBounds(10, 30, CommonHelper.returnWidth(0.6),
				CommonHelper.returnHeight(0.1));
		panel.add(txtFile);
		panel.add(openFile);

		/**
		 * 设定帮助说明
		 */
		JTextArea label = new JTextArea();
		label.setBounds(10, CommonHelper.returnHeight(0.1) + 50,
				CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.2));
		label.setEditable(false);
		label.setLineWrap(true);
		label.setWrapStyleWord(true);
		label.setText(CommonHelper.utf8Str(ConfigHelper.dirLabel()));
		label.setBackground(Color.getColor("#EEEEEE"));
		label.setFont(new Font("微软雅黑", 0, CommonHelper.returnHeight(0.025)));
		JButton readMe=new JButton();
		readMe.setIcon(new ImageIcon(ConfigHelper.readme()));
		readMe.setBounds(10, CommonHelper.returnHeight(0.1) + 80, 150, 30);
		panel.add(readMe);
		panel.add(label);

		/**
		 * 设定使用过的目录
		 */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, CommonHelper.returnHeight(0.1) + 150,
				CommonHelper.returnWidth(0.95), CommonHelper.returnHeight(0.4));
		scrollPane.setBorder(new TitledBorder("使用过的目录"));
		
		String[][] rowDatas=tableClass.returnTableRowDatas(dirPath);
		final JTable table=tableClass.createTable(rowDatas, new String[]{""},sysXMl);
		
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
					SysFloder floder = new SysFloder(
							ConfigVariable.DIR_USED_TRUE, DateUtil
									.dateToString(new Date(),
											"yyyy-MM-dd hh:mm:ss"), file
									.getAbsolutePath());
					txtFile.setText(file.getAbsolutePath());
					sysXMl.addNewDir(file.getAbsolutePath(), floder);
					//System.out.println("表格总共的行数00001--------------》："+table.getRowCount());
					tableClass.reloadTable(dirPath, table);
					//System.out.println("表格总共的行数00002--------------》："+table.getRowCount());
				}
			}
		});
		readMe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WindowOpen.openInWin(new File("resource/readme.txt"));
			}
		});

		scrollPane.setViewportView(table);
		panel.add(scrollPane);
		
		return panel;
	}

}
