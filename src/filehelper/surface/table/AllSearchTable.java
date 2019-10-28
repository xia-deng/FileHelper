package filehelper.surface.table;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.CreateDirAndFile;
import filehelper.helper.DateUtil;
import filehelper.helper.FileUtility;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;

public class AllSearchTable implements TableInter{

	private String[] columns=new String[]{"文件夹名","最后修改时间","文件夹地址"};
	@Override
	public String[][] returnTableRowDatas(String path) {
		// TODO Auto-generated method stub
		String str =path;
		String[][] rowDatas=null;
		if (str != null && str.length() >= 1) {
			SysXMLHandler sysXml = new SysXMLHandler();
			List<String> listStrs = sysXml.getAllSysDir();
			List<File> listFiles = new ArrayList<File>();
			for (String tempStr : listStrs) {
				File file = new File(tempStr);
				if (file != null && file.exists() && file.isDirectory()) {
					listFiles = FileUtility.listDirs(file, listFiles,
							null);
				} else {
					continue;
				}
			}
			List<File> newFiles = new ArrayList<>();
			for (File file : listFiles) {
				String name=file.getName();
				if(name.contains(str)||name.toLowerCase().contains(str.toLowerCase())){
					newFiles.add(file);
				}else{
					continue;
				}
			}
			rowDatas=new String[newFiles.size()][3];
			for (int i = 0; i < newFiles.size(); i++) {
				File temp=newFiles.get(i);
				rowDatas[i]=new String[]{temp.getName(),
						DateUtil.longToDateString(temp.lastModified(), ConfigVariable.SYS_TIME),temp.getAbsolutePath()};
			}
		}else{
			rowDatas=new String[1][3];
			rowDatas[0]=new String[]{"","",""};
		}
		return rowDatas;
	}

	@Override
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			SysXMLHandler sysXml) {
		// TODO Auto-generated method stub
		int rows = rowDatas.length;
		final JTable table = new JTable(rows,columnDatas.length) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(1);
		table.setRowHeight(CommonHelper.returnHeight(0.05));
		table.setModel(new javax.swing.table.DefaultTableModel(rowDatas,
				columnDatas));

		final JPopupMenu popupMenu = new JPopupMenu(); // 弹出式菜单
		popupMenu.setSize(200, 100);
		JMenuItem menuOpen = new JMenuItem("打开文件夹");
		menuOpen.setSize(50, 30);
		menuOpen.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuOpen);
		CommonMouseInputListener commonMouseInputListener=new CommonMouseInputListener();
		table.addMouseListener(commonMouseInputListener.getMouseInputListener(table, popupMenu));
		
		//给右键----打开文件夹
				menuOpen.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						//int[] rows=table.getSelectedRows();
						int[] rows=table.getSelectedRows();
						int[] columns=table.getSelectedColumns();
						
						String value=table.getValueAt(rows[0], 2).toString().trim();
						
						WindowOpen.openInWin(new File(value));
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
		table.setModel(new DefaultTableModel(returnTableRowDatas(path),columns));
		table.clearSelection();
		table.repaint();
	}

}
