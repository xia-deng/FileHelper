package filehelper.lizi;

import java.awt.BorderLayout; 
import java.awt.Container; 
import java.awt.Point; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.InputEvent; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.util.Arrays; 
import java.util.Vector; 
 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JScrollPane; 
import javax.swing.JTable; 
import javax.swing.JTextField; 
import javax.swing.ListSelectionModel; 
import javax.swing.event.TableModelEvent; 
import javax.swing.event.TableModelListener; 
import javax.swing.table.DefaultTableModel; 
import javax.swing.table.JTableHeader; 
import javax.swing.table.TableColumnModel; 
 
/**
 * 表格模型事件示例
 * <p>
 * <li>A component generally gains the focus when the user clicks it, 
 * or when the user tabs between components, or otherwise interacts 
 * with a component. A component can also be given the focus programmatically,
 * such as when its containing frame or dialog-box is made visible. 
 * The snippet of the codes below shows how to give a particular component  
 * the focus every time the window gains the focus.
 * 
 * @author HAN
 * 
 */ 
public class TableModelEvent_1 extends JFrame { 
 
    /**
     * 
     */ 
    private static final long serialVersionUID = -8581492063632813033L; 
 
    public TableModelEvent_1() { 
        // TODO Auto-generated constructor stub  
        final Container container = getContentPane(); 
        Vector<String> tableColumnNames = new Vector<String>(); 
        tableColumnNames.add("A"); 
        tableColumnNames.add("B"); 
        Vector<Vector<String>> tableValues = new Vector<Vector<String>>(); 
        for (int i = 1; i < 5; i++) { 
            Vector<String> vector = new Vector<String>(); 
            vector.add("A" + i); 
            vector.add("B" + i); 
            tableValues.add(vector); 
        } 
        final DefaultTableModel defaultTableModel = new DefaultTableModel( 
                tableValues, tableColumnNames); 
        final JTable table = new JTable(defaultTableModel); 
        JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(table); 
        container.add(scrollPane, BorderLayout.CENTER); 
        JPanel panel = new JPanel(); 
        container.add(panel, BorderLayout.SOUTH); 
        JLabel labelA = new JLabel("A: "); 
        final JTextField textFieldA = new JTextField(15); 
        JLabel labelB = new JLabel("B: "); 
        final JTextField textFieldB = new JTextField(15); 
        JButton buttonAdd = new JButton("添加"); 
        JButton buttonDel = new JButton("删除"); 
        JButton buttonDeselected = new JButton("取消选择"); 
        panel.add(labelA); 
        panel.add(textFieldA); 
        panel.add(labelB); 
        panel.add(textFieldB); 
        panel.add(buttonAdd); 
        panel.add(buttonDel); 
        panel.add(buttonDeselected); 
        buttonAdd.addActionListener(new ActionListener() { 
 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                // TODO Auto-generated method stub  
                int[] selectedRows = table.getSelectedRows(); // table  
                                                                // 默认情况容许多行选择  
                Vector<String> rowData = new Vector<String>(); 
                rowData.add(textFieldA.getText()); 
                rowData.add(textFieldB.getText()); 
                if (selectedRows.length == 0) { 
                    defaultTableModel.addRow(rowData); 
                    textFieldA.setText(null); 
                    textFieldB.setText(null); 
                } else if (selectedRows.length == 1) { 
//                  System.out.println(selectedRows[0]);  
                    defaultTableModel.insertRow(selectedRows[0] + 1, rowData); 
                    textFieldA.setText(null); 
                    textFieldB.setText(null); 
                } else { 
                    JOptionPane.showMessageDialog(container, 
                            "Your operation is forbidden", "Warning", 
                            JOptionPane.WARNING_MESSAGE); 
                } 
            } 
 
        }); 
        buttonDel.addActionListener(new ActionListener() { 
 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                // TODO Auto-generated method stub  
                int[] selectedRows = table.getSelectedRows(); // table  
                                                                // 默认情况容许多行选择  
                for (int i = 0; i < selectedRows.length; i++) { 
//                  System.out.println(selectedRows[i]);  
                    defaultTableModel.removeRow(selectedRows[i] - i); 
                } 
            } 
 
        }); 
        buttonDeselected.addActionListener(new ActionListener() { 
 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                // TODO Auto-generated method stub  
                table.clearSelection(); 
            } 
 
        }); 
        scrollPane.addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseClicked(MouseEvent e) { 
//               System.out.println("here");  
                if (e.getClickCount() == 1 
                        && e.getButton() == MouseEvent.BUTTON1) { 
                    table.clearSelection(); 
                } 
            } 
        }); 
         
        // make the text field focused every time the window is activated  
        addWindowFocusListener(new WindowAdapter() { 
 
            @Override 
            public void windowGainedFocus(WindowEvent e) { 
                // TODO Auto-generated method stub  
                textFieldA.requestFocus();  
            } 
             
        }); 
 
        // **************************************************************  
        // This is a standard snippet to realize the desired column selection as in  
        // Excel  
        // **************************************************************  
 
        // row selection mode  
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        // column selection mode  
        TableColumnModel tableColumnModel = table.getColumnModel(); 
        tableColumnModel.getSelectionModel().setSelectionMode( 
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        // allow the column selection (the row selection is allowed by default)  
        table.setColumnSelectionAllowed(true); 
        final JTableHeader tableHeader = table.getTableHeader(); 
        tableHeader.addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseClicked(MouseEvent e) { 
                // TODO Auto-generated method stub  
                // Extended modifiers represent the state of all modal keys,  
                // such as ALT, CTRL, META.  
                if (e.getClickCount() == 1 
                        && e.getButton() == MouseEvent.BUTTON1) { 
                    // Point point = new Point(e.getX(), e.getY());  
                    Point point = new Point(e.getPoint()); 
                    int columnNum = tableHeader.columnAtPoint(point); 
                    // System.out.println(columnNum);  
                    int[] selectedColumns = table.getSelectedColumns(); 
                    if (selectedColumns.length != 0) { 
                        // System.out.println("here1");  
                        // System.out.println(InputEvent.getModifiersExText(e.getModifiersEx()));  
                        if (e.getModifiersEx() == (InputEvent.CTRL_DOWN_MASK)) { 
//                          System.out.println("ctrl");  
                            if (Arrays.binarySearch(selectedColumns, columnNum) >= 0) { 
                                table.removeColumnSelectionInterval(columnNum, 
                                        columnNum); 
                            } else { 
                                table.addColumnSelectionInterval(columnNum, 
                                        columnNum); 
                            } 
                        } else if (e.getModifiersEx() == (InputEvent.SHIFT_DOWN_MASK)) { 
                            // System.out.println("shift");  
                            table.setColumnSelectionInterval( 
                                    selectedColumns[0], columnNum); 
                        } else { 
                            table.setColumnSelectionInterval(columnNum, 
                                    columnNum);     
                        } 
                    } else { 
                        // System.out.println("here2");  
                        table.setColumnSelectionInterval(columnNum, columnNum); 
                    } 
                    table.setRowSelectionInterval(0, table.getRowCount() - 1); 
                } 
            } 
        }); 
         
        defaultTableModel.addTableModelListener(new TableModelListener() { 
 
            @Override 
            public void tableChanged(TableModelEvent e) { 
                // TODO Auto-generated method stub  
                int type = e.getType(); 
                int firstRow = e.getFirstRow(); 
//              int lastRow = e.getLastRow(); // the last row seems to be always equal to the first row  
                int column = e.getColumn(); 
                switch (type) { 
                case TableModelEvent.DELETE: 
                    System.out.print("此次事件由 删除 行触发："); 
                    System.out.println("此次删除的是第 " + firstRow + " 行"); 
                    break; 
                case TableModelEvent.INSERT: 
                    System.out.print("此次事件由 插入 行触发："); 
                    System.out.println("此次插入的是第 " + firstRow + " 行"); 
                    break; 
                case TableModelEvent.UPDATE: 
                    System.out.print("此次事件由 更新 行触发："); 
                    System.out.println("此次更新的是第 " + firstRow + " 行第 " + column + " 列"); 
                    break; 
                default: 
                    System.out.println("此次事件由 其他原因 触发"); 
                } 
            } 
 
        }); 
    } 
 
    /**
     * @param args
     */ 
    public static void main(String[] args) { 
        // TODO Auto-generated method stub  
        TableModelEvent_1 frame = new TableModelEvent_1(); 
        frame.setTitle("表格模型事件示例"); 
        frame.pack(); //Realize the components.  
//      frame.setBounds(100, 100, 600, 300);  
//      textFieldA.requestFocus();  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setVisible(true); //Display the window.  
    } 
 
} 