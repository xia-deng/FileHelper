package filehelper.surface.table;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.CreateDirAndFile;
import filehelper.helper.ModelFileHelper;
import filehelper.helper.TxtHelper;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.FileTypeIn;

/**
 * 文件类型显示表格
 * @author Administrator
 *
 */
public class FileTypeTable implements TableInter{

	@Override
	public String[][] returnTableRowDatas(String path) {
		// TODO Auto-generated method stub
		List<FileTypeIn> lists = TxtHelper.readFromTxt(path);
		Collections.sort(lists);
		String[][] elementStrs = new String[lists.size()][1];

		for (int i = 0; i < lists.size(); i++) {
			elementStrs[i] = new String[] { ConfigVariable.Sys_Space
					+ lists.get(i).getFilePath() };
			ModelFileHelper.createExitsModel(lists.get(i).getFilePath());
		}
		return elementStrs;
	}

	@Override
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			SysXMLHandler sysXml) {
		// TODO Auto-generated method stub
		final String fileTypePath=ConfigHelper.fileTypePath();
		int rows = rowDatas.length;
		final JTable table = new JTable(rows, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(1);
		table.setRowHeight(CommonHelper.returnHeight(0.05));
		table.setModel(new javax.swing.table.DefaultTableModel(rowDatas,
				new String[] { "文件类型" }));
		final JPopupMenu popupMenu = new JPopupMenu(); // 弹出式菜单
		JMenuItem menuEdit = new JMenuItem("编辑模板文档");
		menuEdit.setSize(50, 30);
		menuEdit.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuEdit);
		CommonMouseInputListener commonMouseInputListener=new CommonMouseInputListener();
		table.addMouseListener(commonMouseInputListener.getMouseInputListener(table, popupMenu));
		//右键添加----打开windows编辑器
		menuEdit.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int[] rows=table.getSelectedRows();
				int[] columns=table.getSelectedColumns();
				String value=table.getValueAt(rows[0], columns[0]).toString().trim();
				File selectedFile=ModelFileHelper.returnSelectedFile(value);
				WindowOpen.openInWin(selectedFile);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		return table;
	}

	@Override
	public void reloadTable(String path, JTable table) {
		// TODO Auto-generated method stub
		table.removeAll();
		table.setModel(new DefaultTableModel(returnTableRowDatas(path),
				new String[] { "文件类型" }));
		table.clearSelection();
		table.repaint();
	}

}
