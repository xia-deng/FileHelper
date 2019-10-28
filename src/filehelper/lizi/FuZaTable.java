package filehelper.lizi;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

public class FuZaTable extends JFrame{

    public FuZaTable()
    {
        super();
        setTitle("MenuTest");
        setBounds(100,100,350,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final   JLabel   jLabel=new   JLabel("JPopupMenu",SwingConstants.CENTER); 
        final JPopupMenu popupMenu = new JPopupMenu();  //弹出式菜单
        JMenuItem menuItem = new JMenuItem("菜单项名称");
        popupMenu.add(menuItem);
        getContentPane().addMouseListener(new MouseAdapter(){   //鼠标事件
            /*public void mouseRelease(MouseEvent e){  //释放鼠标事件
                if(e.isPopupTrigger()){
                    //popupMenu.show(e.getComponent(),e.getX(),e.getY());
                    popupMenu.show(jLabel,e.getX(),e.getY());
                }
            }*/
            //public void mouseRelease(MouseEvent e){  //释放鼠标事件
            //if(e.isPopupTrigger()){
                //popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //popupMenu.show(jLabel,e.getX(),e.getY());
            //}
            //}
            @Override
			public   void   mousePressed(MouseEvent   e) 
            { 
                popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //popupMenu.show(jLabel,e.getX(),e.getY()); 
            }
        });
        jLabel.addMouseListener(new MouseAdapter(){   //鼠标事件
            
            @Override
			public   void   mousePressed(MouseEvent   e) 
            { 
                //if(e.getButton()==3)   //1左键,2中键，在这里可以设置键值，这里可设置的不正确，请核实下
                //{
                popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //}
                //popupMenu.show(jLabel,e.getX(),e.getY()); 
            }
        });
        this.getContentPane().add(jLabel); 
        //popupMenu.show(jLabel,e.getX(),e.getY());
        //getContentPane().add(popupMenu);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FuZaTable jPopupMenuTest= new FuZaTable();
        jPopupMenuTest.setVisible(true);
    }

}