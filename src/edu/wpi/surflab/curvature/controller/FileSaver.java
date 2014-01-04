/**
 * 
 */
package edu.wpi.surflab.curvature.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import edu.wpi.surflab.curvature.model.DataPoint2D;
import edu.wpi.surflab.curvature.view.ProfileOptionPanel;

/**
 * @author Steven
 *
 */
public class FileSaver {

	public void saveFile(String filename, LinkedList<LinkedList<DataPoint2D>> allCalculatedPoints, LinkedList<Double> allCalculatedScales, DataPoint2D[] calculatedDistribution, String units) {
		try { 
			File file = new File(filename);
 
			// if file doesn't exist, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			if (ProfileOptionPanel.getInstance().getScatter2dRadio().isSelected()) {
				bw.write("scale ("+units+"),postion ("+units+"),result (1/"+units+")\n");
				for (int i = 0; i < allCalculatedPoints.size(); i++) {
					for (DataPoint2D p : allCalculatedPoints.get(i)) {
						bw.write(allCalculatedScales.get(i)+","+p.getX()+","+p.getY()+"\n");
					}
				}
			} else {
				/*for (int i = 0; i < calculatedDistribution.size(); i++) {
					bw.write(calculatedDistribution.get(i).getX()+","+calculatedDistribution.get(i).getY()+"\n");
				}*/
				bw.write("scale ("+units+"),frequency\n");
				for (int i = 0; i < calculatedDistribution.length; i++) {
					bw.write(calculatedDistribution[i].getX()+","+calculatedDistribution[i].getY()+"\n");		
				}
			}
			bw.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}