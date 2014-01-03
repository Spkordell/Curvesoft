/**
 * 
 */
package edu.wpi.surflab.curvature.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.wpi.surflab.curvature.model.DataPoint;
import edu.wpi.surflab.curvature.model.Profile;

/**
 * @author Steven
 *
 */
public class FileLoader {

	public Profile loadFile(String filename) {
		Profile profile = new Profile();
		File file = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			String[] columnData;
			while ((line = reader.readLine()) != null) {
			      columnData = line.split("\t");
			      if (columnData.length == 2) {
			    	  profile.add(new DataPoint(Double.valueOf(columnData[0]), Double.valueOf(columnData[1])));
			      }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profile;
	}
	
}
