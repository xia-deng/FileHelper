package filehelper.surface.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Element;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.CreateDirAndFile;
import filehelper.helper.ModelFileHelper;
import filehelper.helper.TxtHelper;
import filehelper.helper.WindowOpen;
import filehelper.helper.XMLHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;

public class CreateTable {

	private final String taskPath = ConfigHelper.rightTaskPath();
	private final String fileTypePath = ConfigHelper.fileTypePath();
	private int selectedIndex = 0;

	/**
	 * 获取目录表格的行数据
	 * 
	 * @param path
	 * @return 双字节数组
	 */
	public String[][] returnDirTableRowDatas(String path) {
		// ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir+"/"+ConfigVariable.XML_Dir
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

	/**
	 * 生成表格并添加事件
	 * 
	 * @param rowDatas
	 * @param columnDatas
	 * @return
	 */
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			final SysXMLHandler sysXml) {

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

		table.addMouseListener(this.getMouseInputListener(table, popupMenu));

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

	public void reloadTable(String path, JTable table) {
		table.removeAll();
		table.setModel(new DefaultTableModel(returnDirTableRowDatas(path),
				new String[] { "" }));
		table.clearSelection();
		table.repaint();

	}

	public String[][] returnTaskTableRows() {

		List<DirAndFilesIn> lists = TxtHelper.readFromTxt(taskPath);
		String[][] elementStrs = new String[lists.size()][1];

		for (int i = 0; i < lists.size(); i++) {
			elementStrs[i] = new String[] { ConfigVariable.Sys_Space
					+ lists.get(i).getDir() };
		}
		return elementStrs;
	}

	public JTable createTaskTable() {

		String[][] rowDatas = this.returnTaskTableRows();
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
				new String[] { "文件夹列表" }));

		final JPopupMenu popupMenu = new JPopupMenu(); // 弹出式菜单
		JMenuItem menuOpen = new JMenuItem("打开文件夹");
		menuOpen.setSize(50, 30);
		menuOpen.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuOpen);
		JMenuItem menuAdd = new JMenuItem("生成文件夹");
		menuAdd.setSize(50, 30);
		menuAdd.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuAdd);
		JMenuItem menuSelect = new JMenuItem("选择文件类型");
		menuSelect.setSize(50, 30);
		menuSelect.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuSelect);
		JMenuItem menuCreate = new JMenuItem("加入类型文件");
		menuSelect.setSize(50, 30);
		menuSelect.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuCreate);

		table.addMouseListener(this.getMouseInputListener(table, popupMenu));

		//给右键----打开文件夹
		menuOpen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int[] rows=table.getSelectedRows();
				DirAndFilesIn dir=TxtHelper.getRowByIndex(taskPath, rows[0]);
				WindowOpen.openInWin(new File(dir.getFilePath()));
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
		// 给右键---生成添加事件
		menuAdd.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = table.getSelectedRows(); // table
				// 默认情况容许多行选择
				DirAndFilesIn dirAndIn = TxtHelper.getRowByIndex(taskPath,
						selectedRows[0]);

				if (dirAndIn.getDir() != null) {
					CreateDirAndFile.createDir(dirAndIn,selectedRows[0]);
				}
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
		// 给右键----选择文件类型添加事件
		menuSelect.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				CreateDiaog createDiaog=new CreateDiaog();
				JDialog dialog=createDiaog.returnFileTypeAddDialog(selectedIndex);
				dialog.show();
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
		// 給右鍵---生成模板文件到文件夾
		menuCreate.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = table.getSelectedRows();
				DirAndFilesIn dirIn = (DirAndFilesIn) TxtHelper.getRowByIndex(
						taskPath, selectedRows[0]);
				// CreateDirAndFile.createModelFileList(dirIn.getFiles(),
				// dirIn.getDir());
				List<String> fileIns = dirIn.getFiles();
				if (fileIns != null && fileIns.size() > 0) {
					CreateDirAndFile.createModelFileList(fileIns,
							dirIn.getDir());
				} else {
					JOptionPane.showMessageDialog(null, dirIn.getDir()
							+ "未要生成指定文件类型，请选择！", "标题",
							JOptionPane.ERROR_MESSAGE);
					CreateDiaog createDiaog=new CreateDiaog();
					JDialog dialog=createDiaog.returnFileTypeAddDialog(selectedIndex);
					dialog.show();
				}
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

	private MouseInputListener getMouseInputListener(final JTable table,
			final JPopupMenu popupMenu) {
		return new MouseInputListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == MouseEvent.BUTTON3) {
					// popupMenu.show(table, e.getX(), e.getY());//右键菜单显示
					int[] selectedRows = table.getSelectedRows(); // table
					if (selectedRows.length > 1) {
						JOptionPane.showMessageDialog(table, "请只选择一行进行操作！",
								"Warning", JOptionPane.WARNING_MESSAGE);
						table.clearSelection();
					} else if (selectedRows.length == 1) {
						int mouseIn = table.rowAtPoint(e.getPoint());
						if (mouseIn == selectedRows[0]) {
							selectedIndex = mouseIn;
							popupMenu.show(table, e.getX(), e.getY());
						}
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				// processEvent(e);
			}

			/*
			 * private void processEvent(MouseEvent e) {
			 * 
			 * System.out.println("e.getModifiers()："+e.getModifiers()); if
			 * ((e.getModifiers() & MouseEvent.BUTTON3) != 0) {
			 * 
			 * int modifiers = e.getModifiers();
			 * 
			 * modifiers -= MouseEvent.BUTTON3;
			 * 
			 * modifiers |= MouseEvent.BUTTON3;
			 * 
			 * MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(),
			 * 
			 * e.getWhen(), modifiers, e.getX(), e.getY(), e
			 * 
			 * .getClickCount(), false);
			 * 
			 * table.dispatchEvent(ne);
			 * 
			 * } }
			 */
		};

	}

	public String[][] returnFileTypeTableRows() {

		List<String> lists = TxtHelper.readFromTxt(fileTypePath);
		String[][] elementStrs = new String[lists.size()][1];

		for (int i = 0; i < lists.size(); i++) {
			elementStrs[i] = new String[] { ConfigVariable.Sys_Space
					+ lists.get(i) };
			ModelFileHelper.createExitsModel(lists.get(i));
		}
		return elementStrs;
	}

	public JTable createFileTypeTable() {

		String[][] rowDatas = this.returnFileTypeTableRows();
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
		table.addMouseListener(this.getMouseInputListener(table, popupMenu));
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
				String name=TxtHelper.getRowByIndex(fileTypePath, rows[0]);
				File selectedFile=ModelFileHelper.returnSelectedFile(name);
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
}
