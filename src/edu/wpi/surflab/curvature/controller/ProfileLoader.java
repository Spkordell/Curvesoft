/**
 * 
 */
package edu.wpi.surflab.curvature.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import edu.wpi.surflab.curvature.model.DataPoint2D;
import edu.wpi.surflab.curvature.model.Profile;

/**
 * @author Steven
 *
 */
public class ProfileLoader {

	public Profile loadFile(String filename) {
		Profile profile = new Profile();
		File file = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			String[] columnData;
			while ((line = reader.readLine()) != null) {
			      columnData = line.split("[\t, ]+");
			      if (columnData.length == 2) {
			    	  profile.add(new DataPoint2D(Double.valueOf(columnData[0]), Double.valueOf(columnData[1])));
			      }
			}
			reader.close();
		}  catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File Not Found","File Not Found",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException","IOException",JOptionPane.ERROR_MESSAGE);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Input file must be x and y coordinates deliminated by either tabs, spaces, or commas.","Incorrect File Format",JOptionPane.ERROR_MESSAGE);
		}
		return profile;
	}
	
}
