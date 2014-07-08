package edu.wpi.surflab.curvature.view.actions;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.wpi.surflab.curvature.controller.MainController;

public class SaveResultAction implements ActionListener {

	//FileDialog fd;
	JFileChooser chooser;
	MainController mainController;
	Frame mainFrame;
	
	public SaveResultAction(Frame mainFrame, MainController mainController) {
		this.mainController = mainController;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();		
		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Comma Seperated Values", "csv");
		FileNameExtensionFilter surfFilter = new FileNameExtensionFilter("SURF Format","sur","pro");
		//chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(csvFilter);
		chooser.addChoosableFileFilter(surfFilter);
		int returnVal = chooser.showSaveDialog(mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String ext = "";
			if (chooser.getFileFilter() == csvFilter) {
				ext = ".csv";
			} else if (chooser.getFileFilter() == surfFilter){
				ext = ".sur";
			}
			try {
				System.out.println(chooser.getSelectedFile().getCanonicalPath()+ext);
				mainController.saveFile(chooser.getSelectedFile().getCanonicalPath()+ext);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		/*
		fd = new FileDialog(this.mainFrame, "Select a Save Location", FileDialog.SAVE);
		fd.setFile("*.csv");
	    fd.setVisible(true);
	    mainController.saveFile(fd.getDirectory() + System.getProperty("file.separator") + fd.getFile());
	    */
	}

}
