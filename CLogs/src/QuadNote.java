

import javax.media.opengl.GL;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;



public class QuadNote implements Note{

	float[] points;

	CLog cLog;
	
	QuadNote(float x_1,float y_1,float x_2,float y_2,float x_3,float y_3,float x_4,float y_4, CLog cLog){
		points=new float[8];
		points[0]=x_1;
		points[1] = y_1;
		points[2] = x_2;
		points[3] = y_2;
		points[4] = x_3;
		points[5] = y_3;
		points[6] = x_4;
		points[7] = y_4;
		
		
		this.cLog=cLog;
	}
	
	
	@Override
	public void draw(GL gl,float[] mousePosition) {

		if(checkPosition(mousePosition)){
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			cLog.toolTip=true;
			
			
		}else{
			gl.glColor3f(1.0f, 0.0f, 0.0f);
		
		}gl.glBegin(GL.GL_QUADS);
		 gl.glVertex2f(points[0],points[1]);
		 gl.glVertex2f(points[2],points[3]);
		 gl.glVertex2f(points[4],points[5]);
		 gl.glVertex2f(points[6],points[7]);
		gl.glEnd();
		 
	}


	private boolean checkPosition(float[] mousePosition) {
		if(mousePosition[0]>points[0]&&mousePosition[0]<points[4]&&mousePosition[1]>points[1]&&mousePosition[1]<points[5]){
			return true;
		}		
		return false;
	}
	
	

}
