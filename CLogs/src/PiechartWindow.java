import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;


public class PiechartWindow extends JoglTemplate_fabian{
	int width=1200;
	int height=200/2;
	private Popup popup;
	float[] mousePosition={0.0f,0.0f};
	public JLabel toolTipName=new JLabel("Tip");
	public JLabel toolTipNameLabel=new JLabel("Name: ");
	public JLabel toolTipAbsolute=new JLabel("Tip");
	public JLabel toolTipAbsoluteLabel=new JLabel("Absolute:  ");
	public JLabel toolTipAdditional=new JLabel("Tip");
	public JLabel toolTipAdditionalLable=new JLabel("Percentage:  ");
	
    public boolean toolTip=false;
	private GLCanvas canvas;
	private FPSAnimator animator;
	PiechartTemplate piechart;
	private GLUT glut = new GLUT();
	CLog clog;
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
		// get a "gl" object. this object provides you with all OpenGL methods
		GL gl = drawable.getGL();
		// who is the vendor of the graphics card?
		System.out.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
		// which OpenGL renderer is used?
		System.out.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
		// what kind of OpenGL version is currently supported?
		System.out.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

		
		
		HashMap<String,Integer> data = new HashMap<String,Integer> ();		
		data.put("Moonfire", 	12365);
		data.put("Mangle", 		67111);
		data.put("Shred", 		95751);
		data.put("Rip", 		67236);
		data.put("Attack", 		45734);
		data.put("Rake", 		64644);
		data.put("Swipe", 		97283);
		piechart =  new PiechartTemplate(data);
		
		HashMap<String,Color> color = new HashMap<String,Color> ();
		color.put("Moonfire", 	new Color(0.1f,0.1f,0));
		color.put("Mangle", 	new Color(0.2f,0.2f,0));
		color.put("Shred", 		new Color(0.3f,0.3f,0));
		color.put("Rip", 		new Color(0.4f,0.4f,0));
		color.put("Attack", 	new Color(0.5f,0.5f,0));
		color.put("Rake", 		new Color(0.6f,0.6f,0));
		color.put("Swipe", 		new Color(0.7f,0.7f,0));
		piechart.setColor(color);
		
 
		
	}
	
	public PiechartWindow(CLog clog)
	{
		this.clog=clog;
		canvas = new GLCanvas();
		// the DisplayListener is responsible for showing us the graphics
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 60);
	
	    setSize(1200,200);
	    setResizable(false);
	    
	}
	
	public void display(GLAutoDrawable drawable)
	{
		// get the gl object
		GL gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(1f, 1f, 1f, 1f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
		applyMouseTranslation(gl);
		applyMouseRotation(gl);

		HashMap<String,Integer> data = new HashMap<String,Integer> ();		
		data.put("Moonfire", 	12365);
		data.put("Mangle", 		67111);
		data.put("Shred", 		95751);
		data.put("Rip", 		67236);
		data.put("Attack", 		45734);
		data.put("Rake", 		64644);
		data.put("Swipe", 		97283);
		LinkedList<String> l = clog.getNotSelected();
		for (String k: l){
			if (data.containsKey(k)){
				data.remove(k);
			}
		}
		piechart.setData(data);
		
		piechart.draw_piechart(gl, glut);
		
		
		gl.glPopMatrix();
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
	public void activateAnimator(boolean b)
	{
		if (b)
			{
				animator.start();
				// request focus for event handling
				canvas.requestFocus();
			}
			else
				animator.stop();
	}
	
	
	public PopupFactory factory = PopupFactory.getSharedInstance();
	public Point positionOnScreen;

	public void mouseMoved(MouseEvent e)
	{
		Point p=e.getPoint();
		positionOnScreen=e.getLocationOnScreen();
		mousePosition= calcPosition(p);
		
		if(popup!=null){
			popup.hide();
		}
		
		TooltipInformation tooltip=piechart.getInfo(mousePosition[0], mousePosition[1]);
		if(tooltip!=null){
				toolTipName.setText(tooltip.name);
				toolTipAbsolute.setText(String.valueOf(tooltip.absolute));
				toolTipAdditional.setText(String.valueOf(tooltip.additional));
				JPanel toolTipPanel = new JPanel(new GridLayout(0,2));
				toolTipPanel.add(toolTipNameLabel);
				toolTipPanel.add(toolTipName);
				toolTipPanel.add(toolTipAbsoluteLabel);
				toolTipPanel.add(toolTipAbsolute);
				toolTipPanel.add(toolTipAdditionalLable);
				toolTipPanel.add(toolTipAdditional);
				popup = factory.getPopup(this,toolTipPanel, positionOnScreen.x, positionOnScreen.y-50);
			     popup.show();
		}
	
		
	}

	private float[] calcPosition(Point p) {
		int clogHeight=clog.getHeight()/2;
		int clogWidth=(clog.getWidth()/2);
		p.x-=(clog.getWidth()/7.5);
		p.y-=(clog.getHeight()/19);
		float[] position=new float[2];
		position[0]=((float)p.x/((float)clogWidth))*2;
		position[1]=1-(float)p.y/((float)clogHeight)*1.5f;
		
		return position;
	}
	
}
