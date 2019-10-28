package filehelper.surface.panel.other;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Element;

import filehelper.helper.XMLHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.surface.panel.inter.PanelInter;

/**
 * V1.0
 * 已使用过的文件目录展示，不提供其他服务
 * @author Administrator
 *
 */
public class DirsHandlerPanel implements PanelInter{

	@Override
	public JPanel createPanel() {
		// TODO Auto-generated method stub
		JPanel panel=new JPanel();
		panel.setBorder(new TitledBorder("使用过的目录"));
		List<Element> lists=XMLHelper.nodeList(ConfigVariable.XML_Dir_Collectoin+"/"+ConfigVariable.XML_Sys_Dir);
		int rows=lists.size();
		JTable table=new JTable(rows, 1);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		for (Element element : lists) {
			    tableModel.addRow(new String[]{element.element(ConfigVariable.XML_Dir).getText()});
		}
		panel.add(table,BorderLayout.CENTER);
		return panel;
	}

}
