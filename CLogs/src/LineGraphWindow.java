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
import javax.swing.Popup;
import javax.swing.PopupFactory;

import com.sun.opengl.util.FPSAnimator;


public class LineGraphWindow extends JoglTemplate_fabian{

	public static HashMap<String, Integer> actors = new HashMap<String, Integer>();
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
	
	public LineGraphWindow()
	{
		canvas = new GLCanvas();
		// the DisplayListener is responsible for showing us the graphics
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 60);
		actors.put("Cursia", 0);
		actors.put("Kangee", 0);
		actors.put("Illiash", 0);
		actors.put("Spatzenhirn", 0);
		actors.put("Troublemaker", 0);
		actors.put("Fellmuh", 0);
		actors.put("Krümml", 0);
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
		if (e.getKeyCode() == KeyEvent.VK_C){
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
		}
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
	
	
	public LineGraph calcGraph(String path){
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
			LineGraphData entry = new LineGraphData();
			entry.hour = current.hour; entry.minute = current.minute; entry.second = current.second; entry.millis = current.millis;
			entry.amount = current.amount;
			entry.name = current.sourceName;
			map.put(i++, entry);
		}
		return new LineGraph(map, 200, 5);
	}
	
	public void init(GLAutoDrawable drawable)
	{
		super.init(drawable);			
		
		g1 = calcGraph("MadnessOfDeathwing.txt");
		g2 = calcGraph("WarlordZonozz.txt");		
		graph = g1;
		
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
		gl.glScalef(1, 2, 1);
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
	private int toolPositionX=0;
	private int toolPositionY=0;

	public void mouseMoved(MouseEvent e)
	{
		Point p=e.getPoint();
		positionOnScreen=e.getLocationOnScreen();
		mousePosition= calcPosition(p);
		
		if(popup!=null){
			popup.hide();
		}
		if(toolTip&&Math.abs(positionOnScreen.x-toolPositionX)>2&&Math.abs(positionOnScreen.y-toolPositionY)>2){
			toolPositionX=positionOnScreen.x;
			toolPositionY=positionOnScreen.y;
			
		      popup = factory.getPopup(this, this.toolTipLabel, positionOnScreen.x, positionOnScreen.y-10);
		     
		      popup.show();
			}
		
	}

	private float[] calcPosition(Point p) {
		float xWidth=4.525f;
		float yWidth=2.77f;
//		if(height!=this.getHeight()||width!=this.getWidth()){
//			height=this.getHeight();
//			width=this.getWidth();
//			prepareFBO(gl);
//		}
		float[] position=new float[2];
		position[0]=(float)((float)p.x/(float)width)*2.0f*xWidth-xWidth;
		position[1]=(float)((float)p.y/(float)height)*2.0f*yWidth-yWidth;
		position[1]*=(-1);
		return position;
	}
	

	
	 
	 public void mouseClicked(MouseEvent e)
	{
    	
    
   
    
      
        
    }
}