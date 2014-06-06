package edu.wpi.surflab.curvature.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jna.Native;

import edu.wpi.surflab.curvature.model.DataPoint3D;
import edu.wpi.surflab.curvature.model.Surface;
import edu.wpi.surflab.curvature.view.SurfaceOptionPanel;

public class SurfaceLoader implements Runnable {
	String filename;
	Surface surface;
	MainController mainController; 
	
	public SurfaceLoader(MainController mainController) {
		this.mainController = mainController;
		//C:\Users\Steven\Desktop\\test.txt
		//System.out.println(System.getProperty("user.dir"));
		// ISurfaceDataLoader surfaceDataLoader = (ISurfaceDataLoader) Native.loadLibrary(System.getProperty("user.dir") + System.getProperty("file.separator") + "SurfaceDataLoading.dll",ISurfaceDataLoader.class);
	}
	
	public void setFileToLoad(String filename) {
		this.filename = filename;
	}
	
	public void loadFile() {
		surface = new Surface();
		File file = new File(filename);
		try {
			SurfaceOptionPanel.getInstance().showProgressBar();
			SurfaceOptionPanel.getInstance().showStatusLabel("Preparing to Load Surface");
			SurfaceOptionPanel.getInstance().setProgress(0);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			int totalLines = 0;
			int currentLine = 0;
			while (reader.readLine() != null) totalLines++;
			reader.close();
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			String[] columnData;
			SurfaceOptionPanel.getInstance().showStatusLabel("Loading Surface");
			while ((line = reader.readLine()) != null) {
			      columnData = line.split("[\t, ]+");
			      if (columnData.length == 3) {
			    	  surface.add(new DataPoint3D(Double.valueOf(columnData[0]), Double.valueOf(columnData[1]),Double.valueOf(columnData[2])));
			      }
			      currentLine++;
			      SurfaceOptionPanel.getInstance().setProgress((int)((((float)currentLine)/totalLines)*100));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found","File Not Found",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException","IOException",JOptionPane.ERROR_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Input file must be x, y, and z coordinates deliminated by either tabs, spaces, or commas.","Incorrect File Format",JOptionPane.ERROR_MESSAGE);
		}
		SurfaceOptionPanel.getInstance().hideProgressBar();
		SurfaceOptionPanel.getInstance().hideStatusLabel();
		mainController.finishLoadingSurface();
	}

	public Surface getSurface() {
		return this.surface;
	}
	
	@Override
	public void run() {
		this.loadFile();
	}
	
}
