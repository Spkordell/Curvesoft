package edu.wpi.surflab.curvature.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.drpj.util.DoubleJSlider;
import edu.wpi.surflab.curvature.controller.MainController;

@SuppressWarnings("serial")
public class SurfaceOptionPanel extends JPanel implements ActionListener, ChangeListener {

	private static SurfaceOptionPanel surfaceOptionPanel;
	private MainController mainController;
	
	private JRadioButton verticalRadio;
	private JRadioButton horizontalRadio;
	private JPanel profileExtractionPanel;
	private DoubleJSlider profileSelectionSlider;
	private JTextField profileSelectionTextField;
	private JButton extractProfileButton;
	final ButtonGroup profileSelecitonButtonGroup;
	
	private final JLabel statusLabel;
	private JProgressBar progressBar;

	public SurfaceOptionPanel(MainController mainController) {
		this.mainController = mainController;
		
		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		horizontalRadio = new JRadioButton("Horizontal");
		horizontalRadio.setSelected(true);
		horizontalRadio.setMnemonic(KeyEvent.VK_H);
		horizontalRadio.setActionCommand("Horizontal");
		horizontalRadio.addActionListener(this);
		
		verticalRadio = new JRadioButton("Vertical");
	    verticalRadio.setMnemonic(KeyEvent.VK_V);
	    verticalRadio.setActionCommand("Vertical");
	    verticalRadio.addActionListener(this);
	    
	    
		profileSelecitonButtonGroup = new ButtonGroup();
		profileSelecitonButtonGroup.add(verticalRadio);
		profileSelecitonButtonGroup.add(horizontalRadio);		
		
		//profileSelectionSlider = new DoubleJSlider(JSlider.HORIZONTAL,1,20,5);
		profileSelectionSlider = new DoubleJSlider(0, 0, 0, .01);
		//profileSelectionSlider.setMajorTickSpacing(5);
		//profileSelectionSlider.setMinorTickSpacing(1);
		//profileSelectionSlider.setPaintLabels(true);
		//profileSelectionSlider.setPaintLabels(true);
		//profileSelectionSlider.setPaintTrack(true);
		profileSelectionSlider.addChangeListener(this);
		profileSelectionTextField = new JTextField();
		extractProfileButton = new JButton("Extract Profile"); 
		extractProfileButton.setActionCommand("Extract Profile");
		extractProfileButton.addActionListener(this);
		
		profileExtractionPanel = new JPanel(new GridLayout(5, 1));
		profileExtractionPanel.add(horizontalRadio);
		profileExtractionPanel.add(verticalRadio);
		profileExtractionPanel.add(profileSelectionSlider);
		profileExtractionPanel.add(profileSelectionTextField);
		profileExtractionPanel.add(extractProfileButton);
		profileExtractionPanel.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createEtchedBorder(), "Profile Extraction"));

		verticalRadio.setEnabled(false);
		horizontalRadio.setEnabled(false);
		profileSelectionSlider.setEnabled(false);
		profileSelectionTextField.setEnabled(false);
		extractProfileButton.setEnabled(false);
		profileExtractionPanel.setEnabled(false);

		
		progressBar = new JProgressBar();
		progressBar.setValue(100);
		progressBar.setVisible(false);
		
		statusLabel = new JLabel();
		statusLabel.setVisible(false);
		
		this.add(profileExtractionPanel);
		this.add(statusLabel);
		this.add(progressBar);
		
		
		final int VERTICAL_PADDING = 5;
		final int HORIZONTAL_PADDING = 5;
		final int CLOSE = -5;
		
		layout.putConstraint(SpringLayout.NORTH, profileExtractionPanel, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,profileExtractionPanel,HORIZONTAL_PADDING,SpringLayout.WEST,this);		
		
		layout.putConstraint(SpringLayout.SOUTH,progressBar,VERTICAL_PADDING+CLOSE-4,SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST,progressBar,HORIZONTAL_PADDING+CLOSE+CLOSE,SpringLayout.EAST,this);
		
		layout.putConstraint(SpringLayout.SOUTH,statusLabel,VERTICAL_PADDING+CLOSE-2,SpringLayout.NORTH, progressBar);
		layout.putConstraint(SpringLayout.WEST,statusLabel,0,SpringLayout.WEST,progressBar);
	}

	public static SurfaceOptionPanel getInstance(MainController mainController) {
		if (surfaceOptionPanel == null) {
			surfaceOptionPanel = new SurfaceOptionPanel(mainController);
		}
		return surfaceOptionPanel;
	}
	
	public static SurfaceOptionPanel getInstance() {
		return surfaceOptionPanel;
	}

	public void enablePanel() {
		verticalRadio.setEnabled(true);
		horizontalRadio.setEnabled(true);
		profileSelectionSlider.setEnabled(true);
		profileSelectionTextField.setEnabled(true);
		extractProfileButton.setEnabled(true);
		profileExtractionPanel.setEnabled(true);
		mainController.setMode("Surface");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Extract Profile") {
			mainController.extractProfile();
		} else {
			if(e.getActionCommand() == "Horizontal") {
				profileSelectionSlider.setDoubleStep(mainController.getSurface().horizontalSamplingInterval());
				profileSelectionSlider.setDoubleMinimum(mainController.getSurface().getMinXPosition());
				profileSelectionSlider.setDoubleMaximum(mainController.getSurface().getMaxXPosition());
				//System.out.println(mainController.getSurface().horizontalSamplingInterval());
			} else if (e.getActionCommand() == "Vertical") {
				profileSelectionSlider.setDoubleStep(mainController.getSurface().verticalSamplingInterval());
				//System.out.println(mainController.getSurface().verticalSamplingInterval());
				profileSelectionSlider.setDoubleMinimum(mainController.getSurface().getMinYPosition());
				profileSelectionSlider.setDoubleMaximum(mainController.getSurface().getMaxYPosition());	
			}
			WorkPanel.getInstance().updateCrossSection();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {		
		DoubleJSlider source = (DoubleJSlider)e.getSource();
		if(!source.getValueIsAdjusting()) {
			profileSelectionTextField.setText(String.valueOf(profileSelectionSlider.getDoubleValue()));
			WorkPanel.getInstance().updateCrossSection();
		}
	}

	public void setDefaultSliderValues() {
		profileSelectionSlider.setDoubleStep(mainController.getSurface().horizontalSamplingInterval());
		profileSelectionSlider.setDoubleMinimum(mainController.getSurface().getMinXPosition());
		profileSelectionSlider.setDoubleMaximum(mainController.getSurface().getMaxXPosition());
		profileSelectionSlider.setDoubleValue(mainController.getSurface().getMinXPosition());
	}

	public Double getProfileSelection() {
		return profileSelectionSlider.getDoubleValue();
	}
	
	public boolean isHorizontalSelected() {
		return horizontalRadio.isSelected();
	}
	
	public void setProgress(int i) {
		progressBar.setValue(i);
	}
	
	public void showProgressBar() {
		progressBar.setVisible(true);
	}
	
	public void hideProgressBar() {
		progressBar.setVisible(false);
	}
	
	public void hideStatusLabel() {
		this.statusLabel.setVisible(false);
	}
	
	public void showStatusLabel(String text) {
		this.statusLabel.setText(text);
		this.statusLabel.setVisible(true);
	}
}
