/**
 * 
 */
package edu.wpi.surflab.curvature.controller;

import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.wpi.surflab.curvature.model.DataPoint2D;
import edu.wpi.surflab.curvature.model.DataPoint3D;
import edu.wpi.surflab.curvature.model.Profile;
import edu.wpi.surflab.curvature.model.Surface;
import edu.wpi.surflab.curvature.view.Main;
import edu.wpi.surflab.curvature.view.OptionPanel;
import edu.wpi.surflab.curvature.view.ProfileOptionPanel;
import edu.wpi.surflab.curvature.view.SurfaceOptionPanel;
import edu.wpi.surflab.curvature.view.WorkPanel;

/**
 * @author Steven
 *
 */
public class MainController {

	LinkedList<Profile> profiles;
	LinkedList<Surface> surfaces;
	ProfileLoader profileLoader;
	SurfaceLoader surfaceLoader;
	FileSaver fileSaver;
	private String calculationType = "Profile";
	private String plotType = "2DScatter";
	private String mode = "Profile";
	
	private LinkedList<Double> scales;
	private double binSize = 0.001;
	private boolean precalculate;

	
	public MainController() {
		scales = new LinkedList<Double>(); 
		profileLoader = new ProfileLoader();
		surfaceLoader = new SurfaceLoader(this);
		fileSaver = new FileSaver();
		profiles = new LinkedList<Profile>();
		surfaces = new LinkedList<Surface>();
	}

	/** Loads a 2D profile from the specified file path
	 * @param filePath The location of the profile on the file system
	*/ 
	public void loadProfile(String filePath) {
		profiles.add(profileLoader.loadFile(filePath));
		WorkPanel.getInstance().update();
		ProfileOptionPanel.getInstance().enablePanel();
		OptionPanel.getInstance().disableSurfaceTab();
		OptionPanel.getInstance().setProfileTabActive();
	}

	/** Loads a 3D surface from the specified file path
	 * @param filePath The location of the surface on the file system
	*/ 
	public void loadSurface(String filePath) {
		OptionPanel.getInstance().setSurfaceTabActive();
		surfaceLoader.setFileToLoad(filePath);
		Thread t = new Thread(surfaceLoader);
    	t.start();
	}
	
	/** Prompts the user for the surfaces units and saves the surface to the maincontroller 
	*/ 
	public void finishLoadingSurface() {
		surfaces.add(surfaceLoader.getSurface());
		WorkPanel.getInstance().update();
		SurfaceOptionPanel.getInstance().enablePanel();
		OptionPanel.getInstance().disableProfileTab();	
		SurfaceOptionPanel.getInstance().setDefaultSliderValues();
				
		//Prompt the user for the correct units	    
	    Object[] possibilities = {"pm","nm","um","mm","cm","dm","m","hm","km","in","ft","mile"};
	    JPanel message = new JPanel();
	    message.setLayout(new GridLayout(2, 1));
	    message.add(new JLabel("Select the file's units:\n"));
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox UnitsComboBox = new JComboBox(possibilities);
	    UnitsComboBox.setSelectedItem("mm");
        message.add(UnitsComboBox);
        // JCheckBox PreCalcCheck = new JCheckBox("Precalculate",true);
        //message.add(PreCalcCheck);              
        		JOptionPane.showOptionDialog(Main.getFrame(), message, "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                null, null, null);
	    ////////////////////////////// 
	    //If a string was returned, say so.
        String s = (String)UnitsComboBox.getSelectedItem();
        // mainController.setPrecalculate(PreCalcCheck.isSelected());
	    if ((s != null) && (s.length() > 0)) {
	        this.getSurface().setUnits(s);
	        WorkPanel.getInstance().update();
	    }
	}
	
	/** Extracts a profile from a surface
	*/ 
	public void extractProfile() {
		Profile extractedProfile = new Profile();
		 if (SurfaceOptionPanel.getInstance().isHorizontalSelected()) {
			   for (DataPoint3D p: this.getSurface().getSurface()) {
				   if (p.getX() < SurfaceOptionPanel.getInstance().getProfileSelection() + this.getSurface().horizontalSamplingInterval()  && p.getX() > SurfaceOptionPanel.getInstance().getProfileSelection() - this.getSurface().horizontalSamplingInterval()) {
					   extractedProfile.add(new DataPoint2D(p.getY(),p.getZ()));
				   }
			   }
		   } else {
			   for (DataPoint3D p: this.getSurface().getSurface()) {
				   if (p.getY() < SurfaceOptionPanel.getInstance().getProfileSelection() + this.getSurface().verticalSamplingInterval()  && p.getY() > SurfaceOptionPanel.getInstance().getProfileSelection() - this.getSurface().verticalSamplingInterval()) {
					   extractedProfile.add(new DataPoint2D(p.getX(),p.getZ()));
				   }
			   }		   	   
		   }
		 extractedProfile.setUnits(this.getSurface().getUnits());
		 profiles.add(extractedProfile);
		 OptionPanel.getInstance().enableProfileTab();
		 ProfileOptionPanel.getInstance().enablePanel();
		 OptionPanel.getInstance().setProfileTabActive();
	}
	
	public void saveFile(String filePath) {
		fileSaver.saveFile(filePath,profiles.getLast().allCalculatedPoints,profiles.getLast().allCalculatedScales, profiles.getLast().calculatedDistribution,getProfile().getUnits());
	}
	
	public Profile getProfile() {
		return profiles.getLast();
	}
	
	public Surface getSurface() {
		return surfaces.getLast();
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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
