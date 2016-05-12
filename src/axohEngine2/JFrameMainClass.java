/**
 * Main class that holds the JFrame and the configurations to decide which graphics code to
 * use (either Windows or Mac OS X).
 * @author Team A2 (Melkis Espinal, Ally Colisto, Argyrios Doumas, and Alex Baez) 
 */
package axohEngine2;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.apple.eawt.Application;
import com.apple.eawt.FullScreenUtilities;

public class JFrameMainClass extends JFrame{
	//instance variables
	private static String osVersion;
	//Toolkit gets the width and height of your current screen
	private static Toolkit toolkit =  Toolkit.getDefaultToolkit ();
	private static Dimension currentDimensions = toolkit.getScreenSize();
	private static int SCREENHEIGHT = currentDimensions.height;
	private static int SCREENWIDTH = currentDimensions.width;
	private static final long serialVersionUID = 1L;
	
	//constructor
	public JFrameMainClass(String frameName){
		super(frameName);
		osVersion = System.getProperty("os.name");//get OS Name
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//code for Mac OS X 
		if(osVersion.equals("Mac OS X")){
			Dimension size = new Dimension(SCREENWIDTH, SCREENHEIGHT); //screen size
			setPreferredSize(size);
			setSize(size);
			setVisible(true);
			pack();
			setResizable(false);
		}
	}
	//main
	public static void main(String[] args) {
		Judgement judgment = new Judgement(); //jpanel
		JFrameMainClass frame = new JFrameMainClass("Judgment");//create Frame object
		frame.add(judgment);//add panel to frame
		judgment.setFocusable(true);//set focus
		if(osVersion.equals("Mac OS X")){//if Mac OS X
			FullScreenUtilities.setWindowCanFullScreen(frame,true);
			Application.getApplication().requestToggleFullScreen(frame);
			judgment.setFrame(frame);
		}
		else{//if anything else (windows mostly)
				GraphicsDevice gd = 
						GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				
				if (gd.isFullScreenSupported()) {
			       	frame.setUndecorated(true);
			        gd.setFullScreenWindow(frame);//
			    }else {
			        System.err.println("Full screen not supported");
			        frame.setSize(100, 100); //just something to let you see the window
			        frame.setVisible(true);
			    }
				judgment.setFrame(frame);
		}
	}
}