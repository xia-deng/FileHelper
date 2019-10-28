package filehelper.surface.table.inter;

import javax.swing.JTable;

import filehelper.sys.SysXMLHandler;

public interface TableInter {

	/**
	 * 得到表格的行数据
	 * @param path
	 * @return
	 */
	public String[][] returnTableRowDatas(String path);
	/**
	 * 生成表格
	 * @param rowDatas 表格行数据
	 * @param columnDatas 表格列数据
	 * @param sysXml 表格操作类
	 * @return
	 */
	public JTable createTable(String[][] rowDatas, String[] columnDatas,
			final SysXMLHandler sysXml) ;
	
	/**
	 * 刷新表格
	 * @param path 表格数据源地址
	 * @param table 表格
	 */
	public void reloadTable(String path, JTable table);
}
