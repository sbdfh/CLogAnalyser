
import java.awt.FlowLayout;

import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;


public class SpellSelector extends JFrame {

	JCheckBox[] boxes;
	
	public SpellSelector (HashMap<String,Integer> datamap) {
		super();
		boxes = new JCheckBox[datamap.size()];
		this.setLayout(new FlowLayout());
		int i = 0;
		for(String s:datamap.keySet()){
			JCheckBox box = new JCheckBox(s);
			boxes[i++] = box;
			box.setSelected(true);
			this.add(box);
		}
	}
	
	public LinkedList<String> getNotSelected(){
		LinkedList<String> ret = new LinkedList<String>(); 
		for (JCheckBox box : boxes){	
			if (!box.isSelected()){
				ret.add(box.getText());
			}
		}
		return ret;
	}
	
}
