package edu.wpi.surflab.curvature.view;

import javax.swing.JPanel;

import edu.wpi.surflab.curvature.controller.MainController;

@SuppressWarnings("serial")
public class SurfaceOptionPanel extends JPanel {

	private static SurfaceOptionPanel surfaceOptionPanel;
	private MainController mainController;

	public SurfaceOptionPanel(MainController mainController) {
		this.mainController = mainController;
	}

	public static SurfaceOptionPanel getInstance(MainController mainController) {
		if (surfaceOptionPanel == null) {
			surfaceOptionPanel = new SurfaceOptionPanel(mainController);
		}
		return surfaceOptionPanel;
	}
	
	public static SurfaceOptionPanel getInstance() {
		return surfaceOptionPanel;
	}

	public void enablePanel() {
		mainController.setMode("Surface");
	}

}
