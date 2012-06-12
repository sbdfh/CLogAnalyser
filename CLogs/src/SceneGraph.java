

import java.util.Vector;

import javax.media.opengl.GL;
import javax.swing.Popup;



public class SceneGraph {
	
	Vector<Note> notes= new Vector<Note>();

	
	public void draw(GL gl,float[] mousePosition){
		for(int i=0; i<notes.size();i++){
			notes.get(i).draw(gl,mousePosition);
		}
	}
	
	
	public QuadNote addQuad(float x_1,float y_1,float x_2,float y_2,float x_3,float y_3,float x_4,float y_4, CLog cLog){
		QuadNote note= new QuadNote(x_1,y_1,x_2,y_2,x_3,y_3,x_4,y_4,cLog);
		notes.add(note);
		return note;
		
	}

}
