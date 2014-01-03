package edu.wpi.surflab.curvature.view.actions;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.surflab.curvature.controller.MainController;

public class SaveResultAction implements ActionListener {

	FileDialog fd; 
	MainController mainController;
	Frame mainFrame;
	
	public SaveResultAction(Frame mainFrame, MainController mainController) {
		this.mainController = mainController;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		fd = new FileDialog(this.mainFrame, "Select a Save Location", FileDialog.SAVE);
		fd.setFile("*.csv");
	    fd.setVisible(true);
	    mainController.saveFile(fd.getDirectory() + System.getProperty("file.separator") + fd.getFile());
	}

}
