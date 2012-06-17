import java.util.*;
import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class PiechartTemplate {

	private HashMap<String,Integer> data = new HashMap<String,Integer> ();
	private Integer sum_damage = 0;
	public static float TESSELATION = 50;
	private HashMap<String,Color> color;
	private String focus = "";
	

	public PiechartTemplate (HashMap<String,Integer> datamap){
			setData(datamap);
	
	}	
	
	public void setFocus (String focus){
		this.focus = focus;
	}
	
	public void setData(HashMap<String,Integer> datamap){
		data = new HashMap<String, Integer>(datamap);
		sum_damage = 0;
		for (Map.Entry<String,Integer> entry : data.entrySet()) {
		    //String spellname = entry.getKey();
		    Integer damage  = entry.getValue();
		    sum_damage += damage;
		}
	}
	
	public void draw_piechart(GL gl, GLUT glut){
		
		for (Map.Entry<String,Integer> entry : data.entrySet()) {
			float prozent = (entry.getValue()*100.0f)/sum_damage;
			float winkel = prozent*360f/100f;
			pie(gl, glut, winkel, prozent, entry.getKey());
		}
		
	}
	
	public TooltipInformation getInfo (float x, float y){
		TooltipInformation ttinfo  = new TooltipInformation();
		float arc = 0;
		x = x*2f-1f;
		y = y*2f-1f;
		float laenge = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (x*x + y*y > 1){
			return null;
		} else {
			if (x > 0 && y > 0){
				arc = (float)(Math.acos(y/laenge)/Math.PI*90);	
			}
			if (x > 0 && y < 0){
				arc = (float)(Math.acos(x/laenge)/Math.PI*90) + 90f;
			}
			if (x < 0 && y < 0){
				arc = (float)(Math.acos(y/laenge)/Math.PI*90) + 180f;
			}
			if (x < 0 && y > 0){
				arc = (float)(Math.acos(x/laenge)/Math.PI*90) + 270f;
			}
			float winkel = 0;
			for (Map.Entry<String,Integer> entry : data.entrySet()) {
				float prozent = (entry.getValue()*100.0f)/sum_damage;
				winkel += prozent*360f/100f;
				if (winkel > arc) {
					ttinfo.absolute = entry.getValue();
					ttinfo.name = entry.getKey();
					ttinfo.additional = (int)prozent;
					return ttinfo;
				}
			}
			return null;
		}
	}
	
	public void setColor (HashMap<String,Color> colormap){
		color = new HashMap<String,Color> (colormap);
	}
	
	public void pie (GL gl, GLUT glut, float winkel, float prozent, String name){
		
		while (winkel>90){
			gl.glBegin(GL.GL_POLYGON);	
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(0, 1, 0);
			float x = 0;
			while (x <= TESSELATION){
				++x;
				float xP = x/TESSELATION;
				gl.glVertex3f(xP, (float)Math.sqrt(1-xP*xP), 0);
			}
			gl.glEnd();	
			gl.glRotatef(-90, 0, 0, 1);
			winkel -= 90;
		}
		
		gl.glBegin(GL.GL_POLYGON);
			Color farbe = color.get(name);
			if ("".equals(focus) || name.equals(focus)){
				gl.glColor3f(farbe.r, farbe.g, farbe.b);
			} else {
				gl.glColor3f(0.8f, 0.8f, 0.8f);
			}
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(0, 1, 0);
			float x= 0,  d=(float)Math.sin(winkel/180f*Math.PI);
			while (x < d*TESSELATION){
				++x;
				float xP = x/TESSELATION;
				gl.glVertex3f(xP, (float)Math.sqrt(1-xP*xP), 0);
			}
		gl.glEnd();
		
		gl.glRotatef(-winkel, 0, 0, 1);	
	}	
}