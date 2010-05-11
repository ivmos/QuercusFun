package net.ktulur;

import javax.swing.JApplet;

/**
 * @author Ivan Mosquera Paulo
 * 
 */
public class AppletView extends JApplet {
	public void init() {
		BasicView v = new BasicView();
		getContentPane().add(v);
		this.setSize(400, 400);
	}
}
