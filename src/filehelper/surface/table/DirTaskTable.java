package filehelper.surface.table;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import filehelper.helper.CommonHelper;
import filehelper.helper.ConfigHelper;
import filehelper.helper.CreateDirAndFile;
import filehelper.helper.TxtHelper;
import filehelper.helper.WindowOpen;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.dialog.CreateDiaog;
import filehelper.surface.table.inter.TableInter;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;
import filehelper.sys.model.FileTypeIn;

/**
 * 文件夹管理表格
 * @author Administrator
 *
 */
public class DirTaskTable implements TableInter{

	@Override
	public String[][] returnTableRowDatas(String path) {
		// TODO Auto-generated method stub

		List<DirAndFilesIn> lists = TxtHelper.readFromTxt(path);
		Collections.sort(lists);
		String[][] elementStrs = new String[lists.size()][1];

		for (int i = 0; i < lists.size(); i++) {
			elementStrs[i] = new String[] { ConfigVariable.Sys_Space
					+ lists.get(i).getDir(),lists.get(i).getDate() };
		}
		return elementStrs;
	}

	@Override
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			SysXMLHandler sysXml) {
		// TODO Auto-generated method stub
		final String taskPath=ConfigHelper.rightTaskPath();
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
		JMenuItem menuAddAll = new JMenuItem("加入全部类型");
		menuAddAll.setSize(50, 30);
		menuAddAll.setFont(new Font("黑体", 1, 15));
		popupMenu.add(menuAddAll);

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
				DirAndFilesIn dirIn=new DirAndFilesIn();
				Map<String,Object> map=getSelectedVlaueAndIndex(table);
				dirIn=(DirAndFilesIn) map.get("obj");
				//WindowOpen.openInWin(new File(dirIn.getFilePath()));
				if(dirIn.getFilePath()==null){
					CreateDirAndFile.createDir(dirIn,Integer.valueOf(map.get("index").toString()));
				}
				WindowOpen.openInWin(new File(dirIn.getFilePath()));
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
				
				DirAndFilesIn dirIn=new DirAndFilesIn();
				Map<String,Object> map=getSelectedVlaueAndIndex(table);
				// 默认情况容许多行选择
				DirAndFilesIn dirAndIn = TxtHelper.getRowByIndex(taskPath,
						Integer.valueOf(map.get("index").toString()));

				if (dirAndIn.getDir() != null) {
					CreateDirAndFile.createDir(dirAndIn,Integer.valueOf(map.get("index").toString()));
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
				int[] selectedRows = table.getSelectedRows(); // table
				CreateDiaog createDiaog=new CreateDiaog();
				JDialog dialog=createDiaog.returnFileTypeAddDialog(selectedRows[0]);
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
				DirAndFilesIn dirIn=new DirAndFilesIn();
				Map<String,Object> map=getSelectedVlaueAndIndex(table);
				int selectedRow=Integer.valueOf(map.get("index").toString());
				dirIn = (DirAndFilesIn) TxtHelper.getRowByIndex(
						taskPath, selectedRow);
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
					JDialog dialog=createDiaog.returnFileTypeAddDialog(selectedRow);
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
		
		// 給右鍵---生成全部模板文件到文件夾
		menuAddAll.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				DirAndFilesIn dirIn=new DirAndFilesIn();
				Map<String,Object> map=getSelectedVlaueAndIndex(table);
				int selectedRow=Integer.valueOf(map.get("index").toString());
				dirIn = (DirAndFilesIn) TxtHelper.getRowByIndex(
						taskPath, selectedRow);
				List<FileTypeIn> lists = TxtHelper.readFromTxt(ConfigHelper.fileTypePath());
				List<String> listFileNames=new ArrayList<String>();
				for (FileTypeIn fileTypeIn : lists) {
					listFileNames.add(fileTypeIn.getFilePath());
				}
				try {
					CreateDirAndFile.createModelFileList(listFileNames,
							dirIn.getDir());
					JOptionPane.showMessageDialog(null, dirIn.getDir()
							+ "生成所有类型文件成功！", "创建",
							JOptionPane.NO_OPTION);
				} catch (Exception e2) {
					// TODO: handle exception
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

	@Override
	public void reloadTable(String path, JTable table) {
		// TODO Auto-generated method stub
		table.removeAll();
		table.setModel(new DefaultTableModel(returnTableRowDatas(path),
				new String[] { "文件夹列表","新建时间" }));
		table.clearSelection();
		table.repaint();
	}
	
	private Map<String, Object> getSelectedVlaueAndIndex(JTable table){
		int[] selectedRows = table.getSelectedRows(); // table
		int[] selectedColumns=table.getSelectedColumns();
		String value=(String) table.getValueAt(selectedRows[0], selectedColumns[0]).toString().trim();
		String date=table.getValueAt(selectedRows[0], selectedColumns[0]+1).toString().trim();
		//DirAndFilesIn dir=TxtHelper.getRowByIndex(taskPath, selectedRows[0]);
		DirAndFilesIn dirIn=new DirAndFilesIn();
		Map<String, Object> map=dirIn.findDirByValue(value, date);
		return map;
	}

}
