import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.media.opengl.GL;


public class LineGraph {

	protected static class Color{
		float r, g, b;
		
		public Color(float r, float g, float b){
			this.r = r;
			this.g = g;
			this.b = b;
		}
	}
	
	protected static final HashMap<String, Color> players;
	
	static{
		players = new HashMap<String,Color>();
		players.put("Cursia", new Color(0.41f,0.80f,0.94f));
		players.put("Kangee", new Color(0.77f, 0.12f, 0.23f));
		players.put("Illiash", new Color(0.78f, 0.61f, 0.43f));
		players.put("Spatzenhirn", new Color(0.67f, 0.83f, 0.45f));
	}
	
	protected HashMap<Integer, LineGraphData> data;
	protected int steps, numActors, start, end;
	protected LineGraphLine[] lines;
	protected float[][] parsedData;
	protected float yMax = 0;
	protected String[] assocByInt;
	
	public LineGraph(HashMap<Integer, LineGraphData> data, int steps, int numActors){
		this.data = data;
		this.steps = steps;
		this.numActors = numActors;
		assocByInt = new String[numActors];
		calcDuration();
		invokeDataCalc();
	}
	
	public void setSteps(int steps){
		this.steps = steps;
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
		for (int i = 0; i < numActors; ++i){
			Color c = players.get(assocByInt[i]);
			gl.glColor3f(c.r, c.g, c.b);
			lines[i].draw(gl);
		}
		gl.glColor3f(0,0,0);
		drawGrid(gl);
	}
	
	public void drawGrid(GL gl){
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(0,0);
		gl.glVertex2f(1,0);
		gl.glEnd();
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex2f(0,0);
		gl.glVertex2f(0,1);
		gl.glEnd();
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
		for (i = 0; i < numActors; ++i)
			lines[i] = new LineGraphLine(parsedData[i], yMax);
	}
	
	protected void calcDPS(LinkedList<LineGraphData> sortedData, int actor){
		int currentAmount = 0, j = 0;
		float lastInterval = start, stepSize = (end-start)/(float)steps;
		for (int i = 0; i < sortedData.size(); ++i){
			LineGraphData now = sortedData.get(i);
			currentAmount += now.amount;
			int timestamp = now.millis + now.second*1000 + now.minute*60*1000 + now.hour*60*60*1000;	
			while (timestamp - (start+stepSize*j) > stepSize && j < steps)
				++j;
			while (timestamp - lastInterval > 1000 && j < steps){				
				parsedData[actor][j] = currentAmount*1000.0f/(timestamp - start);						
				//currentAmount = 0;				
				if (parsedData[actor][j] > yMax)
					yMax = parsedData[actor][j];
				lastInterval = lastInterval + 1000;
			}			
		}		
		for (int i = 1; i < steps-1; ++i){
			if (parsedData[actor][i] != 0)
				continue;
			float prevValue, nextValue;
			int prevStep, nextStep;
			j = i;
			while (--j > 0 && parsedData[actor][j] == 0);				
			prevValue = parsedData[actor][j];
			prevStep = j;
			j = i;
			while (++j < steps-1 && parsedData[actor][j] == 0);			
			nextValue = parsedData[actor][j];
			nextStep = j;
			if (nextValue != 0 && prevValue != 0) // Linear Interpolation
				parsedData[actor][i] = ((float)i-prevStep)/(nextStep-prevStep)*prevValue + (nextStep-(float)i)/(nextStep-prevStep)*nextValue;						
		}
		int i = 0;
		j = 0;
		while(parsedData[actor][j] == 0)
			++j;
		while (parsedData[actor][i] == 0){
			parsedData[actor][i] = parsedData[actor][j];	
			++i;
		}
		i = steps-1;
		j = steps-1;
		while(parsedData[actor][j] == 0)
			--j;
		while (parsedData[actor][i] == 0){
			parsedData[actor][i] = parsedData[actor][j];			
			--i;
		}
	}
	
}
