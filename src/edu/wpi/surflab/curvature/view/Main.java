package edu.wpi.surflab.curvature.view;
 
import java.awt.*;

import javax.swing.*;

import edu.wpi.surflab.curvature.controller.MainController;

public class Main {
	private static JFrame frame;
	private static MainMenu mainMenu;
	private static JSplitPane splitPane;
	private static MainController mainController;
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("WPI Curvature Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mainController = new MainController();        
        mainMenu = new MainMenu(frame, mainController);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,OptionPanel.getInstance(mainController), WorkPanel.getInstance(mainController));
        
        frame.add(mainMenu,BorderLayout.NORTH);
        frame.add(splitPane,BorderLayout.CENTER);

        //Display the window.
        frame.setPreferredSize(new Dimension(1000,600));
        frame.pack();
        frame.setVisible(true);
        splitPane.setDividerLocation(.3);
    }
    
    public static JFrame getFrame() {
    	return frame;
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}