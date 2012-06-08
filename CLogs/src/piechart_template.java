import java.util.*;
import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class piechart_template {

	private HashMap<String,Integer> data = new HashMap<String,Integer> ();
	private Integer sum_damage = 0;
	private float color = 1f;
	

	public piechart_template (HashMap<String,Integer> datamap){
		data = new HashMap<String, Integer>(datamap);
		
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
			pie(gl, glut, winkel, prozent, entry.getValue(), entry.getKey());
		}
		
	}
	
	//Buggy, sobald Winkel > 180. Wird aber innerhalb des Datensatzes nicht vorkommen (Arcane Mage?). :trollface:
	public void pie (GL gl, GLUT glut, float winkel, float prozent, Integer absolut, String key){
		color -= 0.1f;
		gl.glColor3f(0, color, 0);
		
		gl.glBegin(GL.GL_TRIANGLES);	
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(0, 1, 0);	
			gl.glVertex3f((float)Math.sin(winkel/180f*Math.PI), (float)Math.cos(winkel/180f*Math.PI), 0);
		gl.glEnd();
		
		gl.glColor3f(1, 0, 0);
		
		gl.glRasterPos3f(winkel/100f, 1, 0);
		glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_10, key+" "+absolut+" ("+Math.round(prozent)+"%)");
		
		gl.glRotatef(-winkel, 0, 0, 1);
	
		
	}
	
	
}