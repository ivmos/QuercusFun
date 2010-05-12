package net.ktulur;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.JApplet;

/**
 * @author Ivan Mosquera Paulo
 * 
 */
public class AppletView extends JApplet {
	
	public void init() {
		
		this.setSize(400, 400);
		AccessController.doPrivileged(new PrivilegedAction() {
	        public Object run() {

		BasicView v = new BasicView();
		getContentPane().add(v);
		return null;
		
	        }
		});
	    
	}
}
