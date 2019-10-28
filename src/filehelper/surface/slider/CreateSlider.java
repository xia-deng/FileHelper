package filehelper.surface.slider;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * 创建标尺控件类
 * @author Administrator
 *
 */
public class CreateSlider {

	private JSlider slider=null;
	
	public CreateSlider() {
		// TODO Auto-generated constructor stub
		slider=new JSlider();
	}
	
	/**
	 * 创建是否停用的标尺
	 * @return
	 */
	public JSlider createUserOrNoSlider(){
		
		slider.setPaintTicks(true);// 显示标尺
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1); // 20一大格
        slider.setMinorTickSpacing(1);// 5一小格
        slider.setMaximum(1);
        slider.setPreferredSize(new Dimension(100, 50));
        Dictionary<Integer, Component> labelTables = new Hashtable<Integer, Component>();
        labelTables.put(0, new JLabel("停用"));
        labelTables.put(1, new JLabel("使用"));
        slider.setLabelTable(labelTables);
        
        return slider;
	}
}
