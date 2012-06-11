import javax.media.opengl.GL;


public class LineGraphLine {

	
	protected float[] data;
	protected float yMax;
	
	public LineGraphLine(float[] data, float yMax){
		this.data = data.clone();
		this.yMax = yMax;
				
	}
	
	public void draw(GL gl){
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int i = 0; i < data.length; ++i){
			gl.glVertex2f(i/(data.length-1.0f), data[i] / yMax);		
		}
		gl.glEnd();
	}
}
