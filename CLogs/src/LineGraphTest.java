import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Map.Entry;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class LineGraphTest extends JoglTemplate_marcel {

	public static HashMap<String, Integer> actors = new HashMap<String, Integer>();
	public LineGraph graph;
	public LineGraph g1, g2;
	private GL gl;
	private float lastX = -1, lastY;
	
	public static void main(String[] args)
	{
		LineGraphTest template = new LineGraphTest();
		template.setVisible(true);
		actors.put("Cursia", 0);
		actors.put("Kangee", 0);
		actors.put("Illiash", 0);
		actors.put("Spatzenhirn", 0);
	}	

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
		return new LineGraph(map, 200, 4);
	}
	
	public void init(GLAutoDrawable drawable)
	{
		super.init(drawable);			
		
		g1 = calcGraph("FullCLogExample.txt");
		g2 = calcGraph("FullCLogExample.txt");		
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
		
		
		gl.glTranslatef(-LineGraph.WIDTH/2, -0.5f, 0);		
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
}
