/**
 * 
 */
package edu.wpi.surflab.curvature.controller;

import java.util.LinkedList;

import edu.wpi.surflab.curvature.model.DataPoint;
import edu.wpi.surflab.curvature.model.Profile;
import edu.wpi.surflab.curvature.view.OptionPanel;
import edu.wpi.surflab.curvature.view.WorkPanel;

/**
 * @author Steven
 *
 */
public class MainController {

	LinkedList<Profile> profiles;
	FileLoader fileLoader;
	FileSaver fileSaver;
	private String calculationType = "Profile";
	private String plotType = "2DScatter";
	
	private LinkedList<Double> scales;
	private double binSize = 0.001;
	private boolean precalculate;
	
	public MainController() {
		scales = new LinkedList<Double>(); 
		fileLoader = new FileLoader();
		fileSaver = new FileSaver();
		profiles = new LinkedList<Profile>();
	}

	public void loadFile(String filePath) {
		profiles.add(fileLoader.loadFile(filePath));
		WorkPanel.getInstance().update();
		OptionPanel.getInstance().enablePanel();
	}

	public void saveFile(String filePath) {
		fileSaver.saveFile(filePath,profiles.getLast().allCalculatedPoints,profiles.getLast().allCalculatedScales, profiles.getLast().calculatedDistribution,getProfile().getUnits());
	}
	
	public Profile getProfile() {
		return profiles.getLast();
	}

	public void setCalculationType(String actionCommand) {
		this.calculationType = actionCommand;
	}
	
	public void setPlotType(String actionCommand) {
		this.plotType = actionCommand;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public LinkedList<Double> getScales() {
		return this.scales;
	}
	
	public void setScales(LinkedList<Double> scales) {
		this.scales = scales;
	}

	public String getPlotType() {
		return plotType;
	}

	public void setBinSize(String binSize) {
		this.binSize = Double.parseDouble(binSize);
	}
	
	public double getBinSize() {
		return this.binSize;
	}

	public void setPrecalculate(boolean selected) {
		this.precalculate = selected;		
	}
	
	public boolean getPrecalculate(){
		return this.precalculate;
	}

	/*
	public void precalculate() {
		if (this.precalculate) {
			final MainController mc = this;
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
			
			
			
			OptionPanel.getInstance().showProgressBar();
			OptionPanel.getInstance().setProgress(0);
			OptionPanel.getInstance().showStatusLabel("Precalculating Values");
			mc.scales = new LinkedList<Double>(); 
			for (double i = mc.getProfile().getSmallestPossibleScale(); i <= mc.getProfile().getLargestPossibleScale(); i+=(2 * mc.getProfile().getSamplingInterval())) {
				mc.scales.add(i);											
			}
			int count = 0;
			mc.getProfile().allCalculatedPoints = new LinkedList<LinkedList<DataPoint>>();
			mc.getProfile().allCalculatedScales = new LinkedList<Double>();
			for (Double scale: mc.getScales()) {

				mc.getProfile().allCalculatedPoints.add(mc.getProfile().calculateCurvature(calculationType, scale));
				mc.getProfile().allCalculatedScales.add(scale);				
			
				OptionPanel.getInstance().setProgress((int)((((float)count)/mc.getScales().size())*100));
				count++;
			}
	        }
			});
		}
	}
*/
}
