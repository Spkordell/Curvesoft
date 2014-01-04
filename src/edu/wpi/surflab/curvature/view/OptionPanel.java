package edu.wpi.surflab.curvature.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.surflab.curvature.controller.MainController;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel {
	private static OptionPanel optionPanel;
	private JTabbedPane tabbedPane;
	
	public OptionPanel(final MainController mainController) { 
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Profile", ProfileOptionPanel.getInstance(mainController));
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab("Surface",SurfaceOptionPanel.getInstance(mainController));
		tabbedPane.setMnemonicAt(1,KeyEvent.VK_0);
		
		setLayout(new BorderLayout());
		this.add(tabbedPane,BorderLayout.CENTER);
	}
	
	public void disableSurfaceTab() {
		tabbedPane.setEnabledAt(1, false);
	}
	
	public void disableProfileTab() {
		tabbedPane.setEnabledAt(2, false);
	}
	
	public static OptionPanel getInstance(MainController mainController) {
		if (optionPanel == null) {
			optionPanel = new OptionPanel(mainController);
		}
		return optionPanel;
	}
	
	public static OptionPanel getInstance() {
		return optionPanel;
	}
}
