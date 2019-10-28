package filehelper.lizi;

import javax.swing.*;

import java.awt.Rectangle;
import java.awt.event.*;

public class NewWin extends JFrame implements ActionListener {
    JButton jb = new JButton();

    public NewWin() {
        this.setTitle("Java——");
        jb.setText("确定");
        jb.setMnemonic('a');
        this.add(jb);
        this.setBounds(200, 300, 250, 300);
        this.setVisible(true);
        jb.addActionListener(this); //由于Swing7实现了ActionListener接口，所以给jb添加的ActionListener就是Swing7实例。
    }

    @Override
	public void actionPerformed(ActionEvent e) {// 实现ActionListener接口的actionPerformed接口。
        JFrame frame = new JFrame("新窗口");//构造一个新的JFrame，作为新窗口。
        frame.setBounds(// 让新窗口与Swing7窗口示例错开50像素。
                new Rectangle(
                        (int) this.getBounds().getX() + 50,
                        (int) this.getBounds().getY() + 50, 
                        (int) this.getBounds().getWidth(), 
                        (int) this.getBounds().getHeight()
                )
            );
        JLabel jl = new JLabel();// 注意类名别写错了。
        frame.getContentPane().add(jl);
        jl.setText("这是新窗口");
        jl.setVerticalAlignment(SwingConstants.CENTER);
        jl.setHorizontalAlignment(SwingConstants.CENTER);// 注意方法名别写错了。
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        NewWin s = new NewWin();
    }
}