package edu.wpi.surflab.curvature.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import edu.wpi.surflab.curvature.model.DataPoint3D;
import edu.wpi.surflab.curvature.model.Surface;

public class SurfaceLoader {
	public Surface loadFile(String filename) {
		Surface surface = new Surface();
		File file = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			String[] columnData;
			while ((line = reader.readLine()) != null) {
			      columnData = line.split("[\t, ]+");
			      if (columnData.length == 3) {
			    	  surface.add(new DataPoint3D(Double.valueOf(columnData[0]), Double.valueOf(columnData[1]),Double.valueOf(columnData[2])));
			      }
			}
			reader.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found","File Not Found",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException","IOException",JOptionPane.ERROR_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Input file must be x, y, and z coordinates deliminated by either tabs, spaces, or commas.","Incorrect File Format",JOptionPane.ERROR_MESSAGE);
		}
		return surface;
	}
	
}
