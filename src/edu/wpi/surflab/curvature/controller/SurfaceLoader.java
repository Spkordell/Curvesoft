package edu.wpi.surflab.curvature.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
			      columnData = line.split("[\t,]+");
			      if (columnData.length == 2) {
			    	  surface.add(new DataPoint3D(Double.valueOf(columnData[0]), Double.valueOf(columnData[1]),Double.valueOf(columnData[2])));
			      }
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return surface;
	}
	
}
