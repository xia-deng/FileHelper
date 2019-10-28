package filehelper.lizi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class FileChooser extends JPanel implements ActionListener{  
    private  JButton open=null;  
    private  JTextField textFile=null;
    public static void main(String[] args) {  
        new FileChooser();  
    }  
    public FileChooser(){  
        //open=new JButton("open");
        textFile=new JTextField(0);
        textFile.setBorder(new EtchedBorder());
        this.setSize(60, 20);
        this.add(textFile);
        //this.add(open);  
        this.setSize(300, 300);
        this.setVisible(true);  
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        open.addActionListener(this);  
    }  
    @Override  
    public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
        jfc.showDialog(new JLabel(), "选择");  
        File file=jfc.getSelectedFile();  
        if(file.isDirectory()){  
            System.out.println("文件夹:"+file.getAbsolutePath());  
        }else if(file.isFile()){  
            System.out.println("文件:"+file.getAbsolutePath());  
        }  
        System.out.println(jfc.getSelectedFile().getName());  
          
    }  
  
} 
