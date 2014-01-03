/**
 * 
 */
package edu.wpi.surflab.curvature.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import edu.wpi.surflab.curvature.controller.MainController;
import edu.wpi.surflab.curvature.view.actions.ExitApplicationAction;
import edu.wpi.surflab.curvature.view.actions.SaveResultAction;
import edu.wpi.surflab.curvature.view.actions.loadProfileAction;

/**
 * @author Steven
 *
 */
@SuppressWarnings("serial")
public class MainMenu extends JPanel{

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	
	public MainMenu(Frame parent, MainController mainController) {
		//Create the menu bar.
		menuBar = new JMenuBar();
		this.setLayout(new BorderLayout());
		
		//Build the File menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("File Menu");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Load Profile",KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Load's a profile");
		menuItem.addActionListener(new loadProfileAction(parent, mainController));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Export Result",KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves the current calculations to a file");
		menuItem.addActionListener(new SaveResultAction(parent, mainController));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Exit",KeyEvent.VK_E);
		menuItem.getAccessibleContext().setAccessibleDescription("Exit the program");
		menuItem.addActionListener(new ExitApplicationAction());
		menu.add(menuItem);	
		
		//Build the Analyze menu
/*		menu = new JMenu("Analyze");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("Perform Analysis");
		menuBar.add(menu); */
	
		this.add(menuBar, BorderLayout.NORTH);
	}

}
