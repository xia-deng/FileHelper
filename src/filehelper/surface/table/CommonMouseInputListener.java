package filehelper.surface.table;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.MouseInputListener;

import filehelper.helper.CreateDirAndFile;
import filehelper.helper.WindowOpen;
import filehelper.sys.model.DirAndFilesIn;

public class CommonMouseInputListener {

	public MouseInputListener getMouseInputListener(final JTable table,
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
					int[] selectedColumns=table.getSelectedColumns();
					if (selectedRows.length > 1||selectedColumns.length>1) {
						JOptionPane.showMessageDialog(table, "请只选择一行一列进行操作！",
								"Warning", JOptionPane.WARNING_MESSAGE);
						table.clearSelection();
					} else if (selectedRows.length == 1&&selectedColumns.length==1) {
						int mouseIn = table.rowAtPoint(e.getPoint());
						if (mouseIn == selectedRows[0]&&selectedColumns[0]==0) {
							//selectedIndex = mouseIn;
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
				if(e.getClickCount()==2){
					try {
						DirAndFilesIn dirIn=new DirAndFilesIn();
						int[] selectedRows = table.getSelectedRows(); // table
						int[] selectedColumns=table.getSelectedColumns();
						String value=(String) table.getValueAt(selectedRows[0], selectedColumns[0]).toString().trim();
						Map<String, Object> map=dirIn.findDirByValue(value, null);
						dirIn=(DirAndFilesIn) map.get("obj");
						//WindowOpen.openInWin(new File(dirIn.getFilePath()));
						if(dirIn.getFilePath()==null){
							CreateDirAndFile.createDir(dirIn,Integer.valueOf(map.get("index").toString()));
						}
						WindowOpen.openInWin(new File(dirIn.getFilePath()));
					} catch (Exception e2) {
						// TODO: handle exception
						return ;
					}
					
				}
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
}
