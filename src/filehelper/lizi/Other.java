package filehelper.lizi;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class Other {
	private JProgressBar jpb = null;
	private JFrame jframe = null;

	public Other() {
		if (jframe == null) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			jframe = new Window();
		}
		jframe.setLayout(null);
		jframe.getContentPane().add(getJProgressBar());
	}

	private JProgressBar getJProgressBar() {
		if (jpb == null) {
			jpb = new JProgressBar();
			jpb.setBounds(0, 0, 580, 30);
			// 0水平 1垂直
			// jpb.setOrientation(0);
			// 是否绘制外边框
			jpb.setBorderPainted(false);
			// 是否在进度条上显示字符串
			jpb.setStringPainted(true);
			// 显示的字符串
			// jpb.setString("Kisra");
			jpb.setMaximum(255);
			jpb.setMinimum(0);
			// 开始线程填充JProgressBar
			new Thread(new changeJProgressBar()).start();
		}
		return jpb;
	}

	public static void main(String[] args) {
		new Other();
	}

	private class Window extends JFrame {
		private static final long serialVersionUID = 1L;

		public Window() {
			this.setBounds(300, 200, 600, 400);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
		}
	}

	private class changeJProgressBar implements Runnable {
		int min = jpb.getMinimum();
		int max = jpb.getMaximum();
		int now = 0;
		String showStr = null;

		@Override
		public void run() {
			while (now < max && now >= min) {
				// 计算并显示完成了多少
				jpb.setValue(++now);
				jpb.setString(((float) now / max * 100) + "%");
				// 修改进度条颜色
				if ((float) now / max > 0.25 && (float) now / max < 0.5) {
					jpb.setBackground(new Color(155, 2, 2));// UIManager设置了当前系统LookAndFeel（XP）以后没有效果
					jpb.setForeground(new Color(2, 155, 155));
				} else if ((float) now / max > 0.5 && (float) now / max < 0.75) {
					jpb.setBackground(new Color(2, 155, 2));
					jpb.setForeground(new Color(155, 2, 155));
				} else if ((float) now / max > 0.75 && (float) now / max < 1) {
					jpb.setBackground(new Color(2, 2, 155));
					jpb.setForeground(new Color(155, 155, 2));
				} else {
					jpb.setForeground(new Color(100, 100, 100));
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(showStr);
					e.printStackTrace();
				}
			}
			// 完成以后的显示
			jpb.setString("下载完成");
		}
	}
}
