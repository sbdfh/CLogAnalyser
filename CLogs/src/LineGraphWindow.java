import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import com.sun.opengl.util.FPSAnimator;


public class LineGraphWindow extends JoglTemplate_fabian{
		
	
	int width=1200;
	int height=200/2;
	private Popup popup;
    public boolean toolTip=false;
	float[] mousePosition={0.0f,0.0f};
	public JLabel toolTipLabel=new JLabel("Tip");
	public LineGraph graph;
	public LineGraph g1, g2;
	private GL gl;
	private float lastX = -1, lastY;
	private GLCanvas canvas;
	private FPSAnimator animator;
	public JLabel toolTipName=new JLabel("Tip");
	public JLabel toolTipNameLabel=new JLabel("Name: ");
	public JLabel toolTipAbsolute=new JLabel("Tip");
	public JLabel toolTipAbsoluteLabel=new JLabel("DPS:  ");
	public JLabel toolTipAdditional=new JLabel("Tip");
	public JLabel toolTipAdditionalLable=new JLabel("Time:  ");
	CLog clog;
	protected HashMap<String, LineGraph> encounters = new HashMap<String, LineGraph>();
	protected HashMap<String, LineGraph> detailGraphs = new HashMap<String, LineGraph>();
	
	
	public LineGraphWindow(CLog clog)
	{
		this.clog=clog;
		canvas = new GLCanvas();
		// the DisplayListener is responsible for showing us the graphics
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 60);
	    setSize(1200,200);
	    setResizable(false);
	    
	}
	
//	public static void main(String[] args)
//	{
//		LineGraphTest template = new LineGraphTest();
//		template.setVisible(true);
//		actors.put("Cursia", 0);
//		actors.put("Kangee", 0);
//		actors.put("Illiash", 0);
//		actors.put("Spatzenhirn", 0);
//	}	

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			graph.increaseStart();
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			graph.decreaseStart();
		if (e.getKeyCode() == KeyEvent.VK_UP)
			graph.increaseEnd();
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			graph.decreaseEnd();
		/*if (e.getKeyCode() == KeyEvent.VK_C){
			g1.setFocus("Cursia");
			g2.setFocus("Cursia");
		}
		if (e.getKeyCode() == KeyEvent.VK_V){		
			g1.setFocus("");
			g2.setFocus("");
		}
		if (e.getKeyCode() == KeyEvent.VK_B){
			if (graph == g1){
				graph = g2;
				g1.deDraw();
			}
			else{
				graph = g1;
				g2.deDraw();
			}
					
				
		}
		if (e.getKeyCode() == KeyEvent.VK_T){
			Random r = new Random();
			float x = r.nextFloat(), y = r.nextFloat();
			lastX = x; lastY = y;
			System.out.println("Queueing ("+x+","+y+")");
			TooltipInformation info = graph.getInfo(x, y);
			if (info == null)
				System.out.println("Miss");
			else
				System.out.println(info.name+" at "+info.additional+" done "+info.absolute);
		}*/
				
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
	public void activateAnimator(boolean b)
	{
		if (b)
			{
				animator.start();
				// request focus for event handling
				canvas.requestFocus();
			}
			else
				animator.stop();
	}
	
	public void setGraph(String path){
		graph = encounters.get(path);
		graph.notDraw = new LinkedList<String>();
		clog.windowButtom.piechart.setData(graph.getFullPieData());
		clog.windowButtom.piechart.setColor(graph.colors);
		clog.setCheckBoxes(graph.getFullPieData());
		graph.deDraw();
		clog.windowButtom.piechart.deDraw();
	}
	
	public void setDetailedGraph(String path, String actor){
		graph = detailGraphs.get(path+actor);
		graph.notDraw = new LinkedList<String>();
		clog.windowButtom.piechart.setData(graph.getFullPieData());
		clog.windowButtom.piechart.setColor(graph.colors);
		clog.setCheckBoxes(graph.getFullPieData());
		graph.deDraw();
		clog.windowButtom.piechart.deDraw();
	}
	
	protected LineGraph calcGraph(String path){
		HashMap<String, Color> actors = LineGraph.colors;
		HashMap<String, Integer> found = new HashMap<String, Integer>();
		CLogParser p = new CLogParser(path);
		p.parse();
		LinkedList<CLogEntry> log = p.getLog();
		HashMap<Integer, LineGraphData> map = new HashMap<Integer, LineGraphData>();
		ListIterator<CLogEntry> it = log.listIterator();
		int i = 0;
		while (it.hasNext()){
			CLogEntry current = it.next();
			if (current.suf != CLogEntry.Suffix.DAMAGE || !actors.containsKey(current.sourceName))
				continue;
			if (!found.containsKey(current.sourceName))
				found.put(current.sourceName, 0);
			LineGraphData entry = new LineGraphData();
			entry.hour = current.hour; entry.minute = current.minute; entry.second = current.second; entry.millis = current.millis;
			entry.amount = current.amount;
			entry.name = current.sourceName;
			map.put(i++, entry);
		}
		return new LineGraph(map, 200, found.size());
	}
	
	protected LineGraph calcDetailedGraph (String path, String actor){
		HashMap<String, Color> actors = LineGraph.colors;
		HashMap<String, Integer> found = new HashMap<String, Integer>();
		CLogParser p = new CLogParser(path);
		p.parse();
		LinkedList<CLogEntry> log = p.getLog();
		HashMap<Integer, LineGraphData> map = new HashMap<Integer, LineGraphData>();
		ListIterator<CLogEntry> it = log.listIterator();
		int i = 0;
		while (it.hasNext()){
			CLogEntry current = it.next();		
			if ((current.pre == CLogEntry.Prefix.SWING || current.pre == CLogEntry.Prefix.RANGE)&& current.suf == CLogEntry.Suffix.DAMAGE)
				current.spellName = "Attack";			
			if (current.sourceName == null)
				continue;
			if (current.suf != CLogEntry.Suffix.DAMAGE || !actors.containsKey(current.spellName) || !current.sourceName.equals(actor))
				continue;		
			if (!found.containsKey(current.spellName))
				found.put(current.spellName, 0);
			LineGraphData entry = new LineGraphData();
			entry.hour = current.hour; entry.minute = current.minute; entry.second = current.second; entry.millis = current.millis;
			entry.amount = current.amount;			
			entry.name = current.spellName;				
			map.put(i++, entry);
		}
		return new LineGraph(map, 200, found.size());
	}
	
	protected void calcDetails(String encounter){
		String[] actors = encounters.get(encounter).getActors();
		for (int i = 0; i < actors.length; ++i){
			detailGraphs.put(encounter+actors[i], calcDetailedGraph(encounter, actors[i]));
		}
	}
	
	public void init(GLAutoDrawable drawable)
	{
		super.init(drawable);			
		
		/*g1 = calcGraph("MadnessOfDeathwing.txt");
		g2 = calcGraph("WarlordZonozz.txt");		
		graph = g1;*/		
		encounters.put("MadnessOfDeathwing.txt", calcGraph("MadnessOfDeathwing.txt"));
		encounters.put("WarlordZonozz.txt", calcGraph("WarlordZonozz.txt"));
		calcDetails("MadnessOfDeathwing.txt");
		calcDetails("WarlordZonozz.txt");
		graph = encounters.get("WarlordZonozz.txt");
		graph.deDraw();		
		clog.setCheckBoxes(graph.getFullPieData());
	}
	
	public void display(GLAutoDrawable drawable)
	{
		// get the gl object
		gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(1f, 1f, 1f, 0f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);		
		gl.glPushMatrix();	
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-2.5, 2.5, -2, 2, -1, 1);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		
		gl.glTranslatef(-LineGraph.WIDTH/2, -1f, 0);	
		gl.glScalef(1.1f, 2.3f, 1);
		
		// Remove unchecked
		LinkedList<String> removed = new LinkedList<String>();
		for (int i = 0; i < graph.numActors; ++i){
			if (!clog.boxes[i].isSelected())
				removed.add(clog.boxes[i].getText());			
		}
		graph.notDraw = removed;
		if (clog.windowButtom.piechart != null){
			clog.windowButtom.piechart.setData(graph.getPieData());
			clog.windowButtom.piechart.setColor(graph.colors);			
		}
		
		graph.draw(gl);
		if(lastX != -1){
			gl.glColor3f(1, 0, 0);
			gl.glPointSize(8);
			gl.glBegin(GL.GL_POINTS);
			gl.glVertex2f(lastX*4, lastY);
			gl.glEnd();
		}
		
		gl.glPopMatrix();
	}
	
	public PopupFactory factory = PopupFactory.getSharedInstance();
	public Point positionOnScreen;
	
	
	public void mouseMoved(MouseEvent e)
	{
		Point p=e.getPoint();
		positionOnScreen=e.getLocationOnScreen();
		mousePosition= calcPosition(p);
		
		if(popup!=null){
			popup.hide();
		}
		
		TooltipInformation tooltip=graph.getInfo(mousePosition[0], mousePosition[1]);
		if (tooltip == null){
			graph.setFocus("");
			clog.windowButtom.piechart.setFocus("");
		}
		if(tooltip!=null){
				graph.setFocus(tooltip.name);
				clog.windowButtom.piechart.setFocus(tooltip.name);
				toolTipName.setText(tooltip.name);
				toolTipAbsolute.setText(String.valueOf(tooltip.absolute));
				toolTipAdditional.setText(String.valueOf(tooltip.additional/60000)+":"+(((tooltip.additional%60000)/1000)<10?"0":"")+String.valueOf((tooltip.additional%60000)/1000)+":"+String.valueOf(tooltip.additional%1000));
				JPanel toolTipPanel = new JPanel(new GridLayout(0,2));
				toolTipPanel.add(toolTipNameLabel);
				toolTipPanel.add(toolTipName);
				toolTipPanel.add(toolTipAbsoluteLabel);
				toolTipPanel.add(toolTipAbsolute);
				toolTipPanel.add(toolTipAdditionalLable);
				toolTipPanel.add(toolTipAdditional);
				popup = factory.getPopup(this,toolTipPanel, positionOnScreen.x, positionOnScreen.y-50);
			     popup.show();
		}
	
		
	}

	private float[] calcPosition(Point p) {
		int clogHeight=clog.getHeight()/2;
		int clogWidth=(clog.getWidth());
		p.x-=(clog.getWidth()/10);
		p.y-=(clog.getHeight()/19);
		float[] position=new float[2];
		position[0]=((float)p.x/((float)clogWidth))*1.14f;
		position[1]=1-(float)p.y/((float)clogHeight)*1.5f;
		if(position[0]>1){
			position[0]=-1.0f;
		}
		return position;
	}
	 
	String path = "WarlordZonozz.txt";
	
	 public void mouseClicked(MouseEvent e)
	{
    	if (e.getButton() == MouseEvent.BUTTON1){
    		Point p=e.getPoint();
    		positionOnScreen=e.getLocationOnScreen();
    		mousePosition= calcPosition(p);
    		TooltipInformation tooltip=graph.getInfo(mousePosition[0], mousePosition[1]);
    		if (tooltip != null&&!clog.detaildGraph){
    			clog.detaildGraph=true;
    			setDetailedGraph(path, tooltip.name);
    			graph.setFocus("");
    			clog.windowButtom.piechart.setFocus("");
    		}
    	}
    	if (e.getButton() == MouseEvent.BUTTON2){
    		if ("WarlordZonozz.txt".equals(path))
    			path = "MadnessOfDeathwing.txt";
    		else
    			path = "WarlordZonozz.txt";
    		clog.detaildGraph = false;
    		setGraph(path);
    		graph.setFocus("");
			clog.windowButtom.piechart.setFocus("");
    	}
    	if (e.getButton() == MouseEvent.BUTTON3){
    		if (!clog.detaildGraph)
    			return;
    		clog.detaildGraph=false;
    		setGraph(path);
    		graph.setFocus("");
			clog.windowButtom.piechart.setFocus("");
    	}
    		
		 
   
    
      
        
    }
}