import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CLog extends JFrame {

	LineGraphWindow windowUp = new LineGraphWindow(this);
	PiechartWindow windowButtom = new PiechartWindow(this);
	JPanel spellPanel;
	boolean detaildGraph=false;

	public static void main(String[] args) {
		CLog log2 = new CLog();
		log2.windowUp.activateAnimator(true);
		log2.windowButtom.activateAnimator(true);
		log2.setVisible(true);

	}

	CLog() {

		setTitle("CLog");
		// set the Window size
		setSize(1200, 800);
		setLocationRelativeTo(null);

		// setResizable(false);
		jbInit();
		// WindowListener that stops rendering and shuts down the application if
		// the window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});

	}

	JCheckBox[] boxes;

	private void jbInit() {

		JPanel mainPanel = new JPanel(new GridLayout(0, 1));
		JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

		JPanel piechartPanel = new JPanel(new BorderLayout());
		spellPanel = new JPanel(new FlowLayout());
		spellPanel.setBackground(Color.WHITE);
		
		piechartPanel.add(spellPanel, BorderLayout.CENTER);
		bottomPanel.add(piechartPanel);
		bottomPanel.add(windowButtom.getCanvas());
		mainPanel.add(bottomPanel);

		mainPanel.add(windowUp.getCanvas());

		add(mainPanel);

	}
	
	public void setCheckBoxes(HashMap<String, Integer> data){
		spellPanel.removeAll();
		boxes = new JCheckBox[data.size()];
	
		int i = 0;
		for (String s : data.keySet()) {			
			JCheckBox box = new JCheckBox(s);
			boxes[i++] = box;
			box.setBackground(Color.WHITE);
			box.setSelected(true);
			spellPanel.add(box);
		}
		spellPanel.revalidate();
		spellPanel.repaint();
	}

	public LinkedList<String> getNotSelected() {
		LinkedList<String> ret = new LinkedList<String>();
		for (JCheckBox box : boxes) {
			if (!box.isSelected()) {
				ret.add(box.getText());
			}
		}
		return ret;
	}

}
