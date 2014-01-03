/**
 * 
 */
package edu.wpi.surflab.curvature.view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Steven Kordell
 *
 */
public class ExitApplicationAction implements ActionListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
