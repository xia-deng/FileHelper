package filehelper.surface.table;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Element;

import filehelper.helper.CommonHelper;
import filehelper.helper.XMLHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;

/**
 * 系统文件夹选择显示的Table
 * @author Administrator
 *
 */
public class SysDirTable implements TableInter{

	@Override
	public String[][] returnTableRowDatas(String path) {
		// TODO Auto-generated method stub
		List<Element> lists = XMLHelper.nodeList(path);
		String[][] elementStrs = new String[lists.size()][1];
		/*
		 * for (Element element : lists) { elementStrs[] (new String[]{
		 * ConfigVariable.Sys_Space+element.getText()}); }
		 */
		for (int i = 0; i < lists.size(); i++) {
			elementStrs[i] = new String[] { ConfigVariable.Sys_Space
					+ lists.get(i).getText() };
		}
		return elementStrs;
	}

	@Override
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			final SysXMLHandler sysXml) {
		// TODO Auto-generated method stub
		int rows = rowDatas.length;
		// 覆写isCellEditable方法，使Table的行不能编辑
		final JTable table = new JTable(rows, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setRowHeight(CommonHelper.returnHeight(0.05));

		table.setModel(new javax.swing.table.DefaultTableModel(rowDatas,
				columnDatas));

		// 给表格新增右键
		final JPopupMenu popupMenu = new JPopupMenu(); // 弹出式菜单
		JMenuItem menuItem = new JMenuItem("删除");
		menuItem.setSize(50, 30);
		menuItem.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuItem);

		final DefaultTableModel tableModel = (DefaultTableModel) table
				.getModel();

		CommonMouseInputListener commonMouseInputListener=new CommonMouseInputListener();
		table.addMouseListener(commonMouseInputListener.getMouseInputListener(table, popupMenu));

		//给右键----删除操作
		menuItem.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("生成删除操作");
				int[] selectedRows = table.getSelectedRows(); // table
				// 默认情况容许多行选择
				for (int i = 0; i < selectedRows.length; i++) {
					// System.out.println(selectedRows[i]);
					sysXml.removeNode(i);
					tableModel.removeRow(selectedRows[i] - i);

				}
				table.clearSelection();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				table.clearSelection();
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
				new String[] { "" }));
		table.clearSelection();
		table.repaint();
	}

}
