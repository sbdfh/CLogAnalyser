

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;



public class CLog extends JoglTemplate{

	public static void main(String[] args){ 
		CLog cLog = new CLog();
		cLog.setVisible(true);
		
	}
	int width=600;
	int height=400;
	float[] mousePosition={0.0f,0.0f};
	boolean red =true;
	SceneGraph scene;
	private Popup popup;
    public boolean toolTip=false;
	public JLabel toolTipLabel=new JLabel("Tip");
    
    
	// true if event listeners are initialized (added to GLAutoDrawable, see
	// public void init(GLAutoDrawable drawable))
	private boolean listenersInitialized = false;
	
	/*
	 * The init() method is called immediately after the OpenGL context is
	 * initialized for the first time. This method is where we do our basic
	 * environment set up.
	 * 
	 * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
	 */
	public void init(GLAutoDrawable drawable)
	{
		
		super.init(drawable);
		scene= new SceneGraph();
		scene.addQuad(0, 0, 0, 1, 1, 1, 1, 0,this);
		
	}
	
	public void display(GLAutoDrawable drawable){
		// get the gl object
				GL gl = drawable.getGL();
				// set the erasing color (black)
				gl.glClearColor(1f, 1f, 1f, 1f);
				// clear screen with the defined erasing color and depth buffer
				gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
				gl.glPushMatrix();
				applyMouseTranslation(gl);
				applyMouseRotation(gl);
				gl.glTranslatef(0.03f, -0.14f, 0);
				if(red){
				// set a default drawing color (red)
				gl.glColor3f(1f, 0f, 0f);
				}else{
					gl.glColor3f(0f, 0f, 1f);
				}
				this.toolTip=false;
				scene.draw(gl,mousePosition);
				

				
			
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
