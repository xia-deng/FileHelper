package filehelper.surface.table;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.ModelFileHelper;
import filehelper.helper.TxtHelper;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;

public class LawFilesTable implements TableInter{

	/**
	 * 返回选定目录下的文件列表，以作为表格数据显示
	 */
	@Override
	public String[][] returnTableRowDatas(String path) {
		// TODO Auto-generated method stub
		String[][] filesData=null;
		if (path != null && path.length() >= 1) {
			List<File> listsTemp=new ArrayList<File>();
			File file = new File(path);
			File[] filesIn = null;

			if (file.exists() && file.isDirectory()) {
				filesIn = file.listFiles();
				for (int i = 0; i < filesIn.length; i++) {
					if(filesIn[i].isFile()){
						listsTemp.add(filesIn[i]);
					}
				}
			}
			filesData = new String[listsTemp.size()][1];
			for (int i = 0; i < listsTemp.size(); i++) {
				filesData[i] = new String[] { listsTemp.get(i).getName() };
			}

			return filesData;
		}else{
			filesData=new String[1][1];
			filesData[0]=new String[]{""};
			return filesData;
		}
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
				new String[] { "文档列表" }));
		final JPopupMenu popupMenu = new JPopupMenu(); // 弹出式菜单
		JMenuItem menuEdit = new JMenuItem("打开法律文档");
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
				int [] columns=table.getSelectedColumns();
				String value=table.getValueAt(rows[0], columns[0]).toString();
				File selectedFile=new File(new SysXMLHandler().getElement(ConfigVariable.XML_ROOT+"/"+ConfigVariable.XML_LAW_DIR).getText()+File.separator+value);
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
		table.setModel(new DefaultTableModel(returnTableRowDatas(path), new String[] { "文档列表" }));
		table.clearSelection();
		table.repaint();
	}

}
