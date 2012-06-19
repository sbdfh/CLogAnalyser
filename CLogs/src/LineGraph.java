import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class LineGraph {

	public static final int WIDTH = 4;
	
	protected static class Color{
		float r, g, b;
		
		public Color(float r, float g, float b){
			this.r = r;
			this.g = g;
			this.b = b;
		}
	}
	
	protected static GLUT glut = new GLUT();
	
	protected static final HashMap<String, Color> players;
	
	static{
		players = new HashMap<String,Color>();
		players.put("Cursia", new Color(0.41f,0.80f,0.94f));
		players.put("Kangee", new Color(0.77f, 0.12f, 0.23f));
		players.put("Illiash", new Color(0.78f, 0.61f, 0.43f));
		players.put("Spatzenhirn", new Color(0.67f, 0.83f, 0.45f));		
	}
	
	protected String focus = "";
	protected Texture dps;
	protected HashMap<Integer, LineGraphData> data;
	protected int steps, numActors, start, end, drawStatus = 0, maxOffset;
	protected LineGraphLine[] lines;
	protected float[][] parsedData;
	protected float yMax = 0, offsetValue;
	protected String[] assocByInt;
	
	public LineGraph(HashMap<Integer, LineGraphData> data, int steps, int numActors){
		this.data = data;
		this.steps = steps;
		this.numActors = numActors;
		assocByInt = new String[numActors];
		calcDuration();
		invokeDataCalc();		
		try {			
			dps = TextureIO.newTexture(new File("src/textures/dps.png"), true);
		} catch (Exception e){
			System.err.println("Error loading Textures");
		}
	
	}
	
	public void setSteps(int steps){
		this.steps = steps;
	}
	
	public void setFocus(String name){
		focus = name;
	}
	
	public void deDraw(){
		drawStatus = 0;
	}
	
	
	public TooltipInformation getInfo(float x, float y){
		TooltipInformation ret = new TooltipInformation();	
		if (Math.ceil(x*steps) > 199)
			x -= 1;
		ret.additional = (int)(x*(end-start));
		float[] values = new float[numActors];
		for (int i = 0; i < numActors; ++i)			
			values[i] = (float)(x*steps - Math.floor(x*steps))*parsedData[i][(int)Math.floor(x*steps)]+(float)(Math.ceil(x*steps)-x*steps)*parsedData[i][(int)Math.ceil(x*steps)];
		float target = yMax*y, dist = yMax*0.25f+1;
		for (int i = 0; i < numActors; ++i)
			if (Math.abs(values[i]-target) < dist){
				dist = Math.abs(values[i]-target);
				ret.absolute = (int)values[i];
				ret.name = assocByInt[i];
			}
		if (dist > yMax*0.25f)
			return null;
		return ret;
	}
	
	protected void calcDuration(){
		int num = data.keySet().size();
		LineGraphComp c = new LineGraphComp();
		LineGraphData max = new LineGraphData(), min = new LineGraphData();
		max.hour = -1;
		min.hour = 25;
		for (int i = 0; i < num; ++i){
			LineGraphData current = data.get(i);
			if (c.compare(current, min) < 0)							
				min = current;							
			if (c.compare(max, current) < 0)
				max = current;
		}		
		end = max.millis + max.second * 1000 + max.minute*60*1000 + max.hour*60*60*1000;
		start = min.millis + min.second * 1000 + min.minute*60*1000 + min.hour*60*60*1000;		
	}
	
	public void draw(GL gl){
		if (drawStatus == 0){
			drawStatus = 1;
			maxOffset = 25;
			offsetValue = yMax*5;
		}
		if (drawStatus == 1 && maxOffset > 0)
			--maxOffset;	
		if (drawStatus == 1 && maxOffset == 0)
			drawStatus = 2;
		gl.glColor3f(0,0,0);
		gl.glLineWidth(2);
		drawGrid(gl);
		drawKey(gl);
		gl.glLineWidth(4);				
		for (int i = 0; i < numActors; ++i){					
			if ("".equals(focus) || assocByInt[i].equals(focus) || drawStatus != 2){
				Color c = players.get(assocByInt[i]);
				gl.glColor3f(c.r, c.g, c.b);
			}
			else
				gl.glColor3f(0.85f, 0.85f, 0.85f);
			lines[i].setMax(yMax+(float)Math.pow(maxOffset/25.0, 4)*offsetValue);			
			lines[i].draw(gl);
		}
	}
	
	protected void drawGrid(GL gl){
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(0,0);
		gl.glVertex2f(WIDTH,0);
		gl.glVertex2f(WIDTH,1);
		gl.glVertex2f(0,1);
		gl.glVertex2f(0,0);
		gl.glEnd();
		int i = 10000;
		gl.glLineWidth(1);
		// Horizontal DPS Lines
		while (i < yMax){
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f(0,i/yMax);
			gl.glVertex2f(WIDTH, i/yMax);
			gl.glEnd();
			gl.glRasterPos2f(-0.2f, i/yMax-0.03f);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, Integer.toString(i/1000)+".000");			
			i += 10000;
		}
		// Vertical Time Lines
		i = 30*1000;
		while (i < end-start){
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glVertex2f((float)i/(end-start)*WIDTH, 0);
			gl.glVertex2f((float)i/(end-start)*WIDTH, 1);
			gl.glEnd();
			gl.glRasterPos2f((float)i/(end-start)*WIDTH-0.075f, -0.1f);			
			if (i/60000 < 10 && (i/1000)%60 < 10)
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "0"+i/60000+":0"+(i/1000)%60);
			else if (i/60000 < 10)
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "0"+i/60000+":"+(i/1000)%60);
			else
				glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, i/60000+":"+(i/1000)%60);			
			i+=30*1000;			
		}		
		//Time Label
		gl.glRasterPos2f(WIDTH/2-0.075f, -0.25f);
		glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Time");
		//DPS Label
		gl.glColor3f(1,1,1);
		gl.glEnable(GL.GL_TEXTURE_2D);
		dps.bind();	
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(0, 0);
		gl.glVertex2f(-0.35f, 0.35f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex2f(-0.25f, 0.35f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex2f(-0.25f, 0.7f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex2f(-0.35f, 0.7f);
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
	
	protected void drawKey(GL gl){
		gl.glColor3f(0,0,0);		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(0, 1.1f, -0.01f);
		gl.glVertex3f(numActors/10.0f*WIDTH, 1.1f, -0.01f);
		gl.glVertex3f(numActors/10.0f*WIDTH, 1.3f, -0.01f);
		gl.glVertex3f(0, 1.3f, -0.01f);
		gl.glVertex3f(0, 1.1f, -0.01f);
		gl.glEnd();
		for (int i = 0; i < numActors; ++i){
			gl.glColor3f(0,0,0);	
			gl.glRasterPos2f(i/10.0f*WIDTH+0.05f, 1.175f);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, assocByInt[i]);
			Color c = players.get(assocByInt[i]);
			gl.glColor3f(c.r, c.g, c.b);
			gl.glBegin(GL.GL_QUADS);
			gl.glVertex2f(i/10.0f*WIDTH, 1.15f);
			gl.glVertex2f(i/10.0f*WIDTH+0.04f, 1.15f);
			gl.glVertex2f(i/10.0f*WIDTH+0.04f, 1.25f);
			gl.glVertex2f(i/10.0f*WIDTH, 1.25f);
			gl.glEnd();
		}
	}
	
	public void invokeDataCalc(){
		HashMap<String, Integer> assoc = new HashMap<String, Integer>();	
		int actorsFound = 0, i = 0;	
		while(actorsFound < numActors){
			LineGraphData current = data.get(new Integer(i++));
			if (!assoc.containsKey(current.name)){
				assocByInt[actorsFound] = current.name;
				assoc.put(current.name, actorsFound++);	
			}
		}
		parsedData = new float[numActors][steps];	
		for (i = 0; i < numActors; ++i){
			LinkedList<LineGraphData> sortedData = new LinkedList<LineGraphData>();
			int num = data.keySet().size();
			for (int j = 0; j < num; ++j){
				LineGraphData current = data.get(j);
				if (!assoc.containsKey(current.name)){
					System.out.println("WARNING: Unknown Actor: "+current.name);
					continue;
				}
				if (assoc.get(current.name) == i)
					sortedData.add(current);
			}
			Collections.sort(sortedData, new LineGraphComp());
			calcDPS(sortedData, i);
		}		
		lines = new LineGraphLine[numActors];
		while ((int)yMax % 10000 != 0)
			++yMax;
		for (i = 0; i < numActors; ++i)
			lines[i] = new LineGraphLine(parsedData[i], yMax);
	}
	
	protected void calcDPS(LinkedList<LineGraphData> sortedData, int actor){
		int j = 0;
		TimeBuffer buf = new TimeBuffer();
		float stepSize = (end-start)/(float)steps;
		for (int i = 0; i < steps; ++i){
			while (sortedData.size() > j){
				LineGraphData now = sortedData.get(j);
				int timestamp = now.millis + now.second*1000 + now.minute*60*1000 + now.hour*60*60*1000;
				if (timestamp - start <= i*stepSize){
					buf.put(timestamp-start, now.amount);
					++j;
				} else
					break;
			}
			parsedData[actor][i] = buf.giveDPS(i*(int)stepSize);
			if (parsedData[actor][i] > yMax)
				yMax = parsedData[actor][i];
		}
	}
	
}
