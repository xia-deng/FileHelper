package filehelper.lizi;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedPane extends JFrame {
	private Container container = null;

	private JTabbedPane tabbedPane = null;

	public TabbedPane() {

		// 设置窗体基本属性
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setTitle("JTabbedPane练习");
		container = getContentPane();

		// 创建默认在顶部的JTabbedPane
		tabbedPane = new JTabbedPane();

		// 创建在左边的JTabbedPane,可以在上下左右四个方向显示JTabbedPane
		tabbedPane = new JTabbedPane(SwingConstants.LEFT);

		// 按照代码顺序为卡片窗格添加卡片，每个卡片窗格中放置有不同的组件，并且addTab方法也有多种重载方式
		tabbedPane.addTab("A", new JButton("btn-A"));
		tabbedPane.addTab("B", new JLabel("lbl-B"));
		tabbedPane.addTab(
				"C",
				null, new JSplitPane(),
				"这是提示信息，哈哈");
		tabbedPane.addTab("D", new JPasswordField());
		tabbedPane.addTab("E", new JTextArea());

		// 注册卡片窗格选择改变事件
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// 禁用选择的卡片窗格
				tabbedPane.setEnabledAt(tabbedPane.getSelectedIndex(), false);
			}
		});

		// 添加到内容窗格
		container.add(tabbedPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new TabbedPane().setVisible(true);
	}
}
