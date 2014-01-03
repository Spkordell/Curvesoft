package edu.wpi.surflab.curvature.view.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.surflab.curvature.controller.MainController;
import edu.wpi.surflab.curvature.view.Main;
import edu.wpi.surflab.curvature.view.WorkPanel;

public class loadProfileAction implements ActionListener {

	FileDialog fd; 
	MainController mainController;
	Frame mainFrame;
	
	public loadProfileAction(Frame mainFrame, MainController mainController) {
		this.mainController = mainController;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		fd = new FileDialog(this.mainFrame, "Select a profile", FileDialog.LOAD);
	    fd.setVisible(true);
	    mainController.loadFile(fd.getDirectory() + System.getProperty("file.separator") + fd.getFile());
	    
	    //Prompt the user for the correct units
	    
	    Object[] possibilities = {"pm","nm","um","mm","cm","dm","m","hm","km","in","ft","mile"};
	    /*String s = (String)JOptionPane.showInputDialog(
	                        Main.getFrame(),
	                        "Select the file's units:\n",
	                        "Units",
	                        JOptionPane.QUESTION_MESSAGE,
	                        null, possibilities,
	                        "mm"); */

	    
	    
	    //////////////////////////////////
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
	        mainController.getProfile().setUnits(s);
	        WorkPanel.getInstance().update();
	        //return;
	    }

	    //mainController.precalculate();
	    // //If you're here, the return value was null/empty.
 
    
	}

}
