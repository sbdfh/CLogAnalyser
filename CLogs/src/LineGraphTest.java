import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class LineGraphTest extends JoglTemplate {

	public static HashMap<String, Integer> actors = new HashMap<String, Integer>();
	public LineGraph graph;
	
	public static void main(String[] args)
	{
		LineGraphTest template = new LineGraphTest();
		template.setVisible(true);
		actors.put("Cursia", 0);
		actors.put("Kangee", 0);
		actors.put("Illiash", 0);
		actors.put("Spatzenhirn", 0);
	}	

	public void init(GLAutoDrawable drawable)
	{
		super.init(drawable);
		
		CLogParser p = new CLogParser("test.txt");
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
		graph = new LineGraph(map, 200, 4);
	}
	
	public void display(GLAutoDrawable drawable)
	{
		// get the gl object
		GL gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(1f, 1f, 1f, 0f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
		applyMouseTranslation(gl);
		applyMouseRotation(gl);

		gl.glLineWidth(4);
		gl.glTranslatef(-0.5f, -0.5f, 0);
		graph.draw(gl);
		
		gl.glPopMatrix();
	}
}
