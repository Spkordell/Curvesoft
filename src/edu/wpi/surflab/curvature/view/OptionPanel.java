package edu.wpi.surflab.curvature.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.surflab.curvature.controller.MainController;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel implements ActionListener {

	private static OptionPanel optionPanel;
	
	private JRadioButton profileRadio;
	private JRadioButton heronRadio;
	private JRadioButton calculusRadio;
	private JRadioButton hybridRadio;
	private JRadioButton doubleDerivativeRadio;
	private JRadioButton slopeRadio;
	
	private JTextField scale;
	private final JPanel calculationTypeRadioPanel;

	private final JLabel scaleLabel;
	private final JLabel scaleInstructionLabel;

	private JRadioButton scatter2dRadio;
	private JRadioButton scatter3dRadio;
	private JRadioButton histogramRadio;
	private final JPanel plotTypeRadioPanel;

	private JTextField binSize;
	private final JLabel binSizeLabel;
	
	private JCheckBox autoUpdateCheckBox;
	
	private final JLabel statusLabel;
	private JProgressBar progressBar;
	
	private MainController mainController;

	public OptionPanel(final MainController mainController) {	
		this.mainController = mainController;
		
		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		profileRadio = new JRadioButton("Profile");
		profileRadio.setMnemonic(KeyEvent.VK_P);
		profileRadio.setActionCommand("Profile");
		profileRadio.addActionListener(this);

		heronRadio = new JRadioButton("Heron's Curvature");
		heronRadio.setMnemonic(KeyEvent.VK_H);
		heronRadio.setActionCommand("Heron");
		heronRadio.addActionListener(this);
		
		calculusRadio = new JRadioButton("Calculus Curvature");
		calculusRadio.setMnemonic(KeyEvent.VK_C);
		calculusRadio.setActionCommand("Calculus");
		calculusRadio.addActionListener(this);
		
		hybridRadio = new JRadioButton("Calculus-Heron Hybrid Curvature");
		hybridRadio.setMnemonic(KeyEvent.VK_Y);
		hybridRadio.setActionCommand("Hybrid");
		hybridRadio.addActionListener(this);

	    doubleDerivativeRadio = new JRadioButton("Double Derivative Curvature Approximation");
	    doubleDerivativeRadio.setMnemonic(KeyEvent.VK_D);
	    doubleDerivativeRadio.setActionCommand("DoubleDerivative");
	    doubleDerivativeRadio.addActionListener(this);
		
	    slopeRadio = new JRadioButton("Slope");
	    slopeRadio.setMnemonic(KeyEvent.VK_S);
	    slopeRadio.setActionCommand("Slope");
	    slopeRadio.addActionListener(this);
	    
		final ButtonGroup group = new ButtonGroup();
		group.add(profileRadio);
		group.add(heronRadio);
		group.add(calculusRadio);
		group.add(hybridRadio);
		group.add(doubleDerivativeRadio);
		group.add(slopeRadio);
		profileRadio.setSelected(true);
		profileRadio.setEnabled(false);
		heronRadio.setEnabled(false);
		calculusRadio.setEnabled(false);
		hybridRadio.setEnabled(false);
		doubleDerivativeRadio.setEnabled(false);
		slopeRadio.setEnabled(false);
		
		calculationTypeRadioPanel = new JPanel(new GridLayout(6, 1));
		calculationTypeRadioPanel.add(profileRadio);
		calculationTypeRadioPanel.add(heronRadio);
		calculationTypeRadioPanel.add(calculusRadio);
		calculationTypeRadioPanel.add(hybridRadio);
		calculationTypeRadioPanel.add(doubleDerivativeRadio);
		calculationTypeRadioPanel.add(slopeRadio);
		calculationTypeRadioPanel.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createEtchedBorder(), "Calculation Type"));
		calculationTypeRadioPanel.setEnabled(false);

		scaleLabel = new JLabel("Scales:");
		scale = new JTextField(20);

		scaleInstructionLabel = new JLabel("<HTML><SMALL><I>e.g. \"30, 50, 80-90, 90-120;10\" or \"all\"</I></SMALL></HTML>");
		scaleInstructionLabel.setEnabled(false);
		
		scaleLabel.setEnabled(false);
		scale.setEnabled(false);
		
	    scatter2dRadio = new JRadioButton("2D Scatter");
	    scatter2dRadio.setMnemonic(KeyEvent.VK_2);
	    scatter2dRadio.setActionCommand("2DScatter");
	    scatter2dRadio.addActionListener(this);	   
	    
	    scatter3dRadio = new JRadioButton("3D Scatter");
	    scatter3dRadio.setMnemonic(KeyEvent.VK_3);
	    scatter3dRadio.setActionCommand("3DScatter");
	    scatter3dRadio.addActionListener(this);	   
		
	    histogramRadio = new JRadioButton("Histogram");
	    histogramRadio.setMnemonic(KeyEvent.VK_I);
	    histogramRadio.setActionCommand("Histogram");
	    histogramRadio.addActionListener(this);		   
	    
	    final ButtonGroup plotTypeGroup = new ButtonGroup();
	    plotTypeGroup.add(scatter2dRadio);
	    plotTypeGroup.add(scatter3dRadio);
	    plotTypeGroup.add(histogramRadio);
		scatter2dRadio.setSelected(true);
		scatter2dRadio.setEnabled(false);
		scatter3dRadio.setEnabled(false);
		histogramRadio.setEnabled(false);
	    
		plotTypeRadioPanel = new JPanel(new GridLayout(3, 1));
		plotTypeRadioPanel.add(scatter2dRadio);
		plotTypeRadioPanel.add(scatter3dRadio);
		plotTypeRadioPanel.add(histogramRadio);
		plotTypeRadioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Plot Type"));
		plotTypeRadioPanel.setEnabled(false);
	    
		
		binSizeLabel = new JLabel("Bin Size:");
		binSize = new JTextField(20);
		binSize.setText("0.001");
		binSizeLabel.setEnabled(false);
		binSize.setEnabled(false);
		
		autoUpdateCheckBox = new JCheckBox("Enable Auto Update",true);
		autoUpdateCheckBox.setEnabled(false);
		autoUpdateCheckBox.setActionCommand("AutoUpdateToggled");
		autoUpdateCheckBox.addActionListener(this);
		
		progressBar = new JProgressBar();
		progressBar.setValue(100);
		progressBar.setVisible(false);
		
		statusLabel = new JLabel();
		statusLabel.setVisible(false);
		
		scale.getDocument().addDocumentListener(new DocumentListener() {
			  public void removeUpdate(DocumentEvent e) {
				  if (scale.getText().equals("")) {
					  heronRadio.setEnabled(false);
					  calculusRadio.setEnabled(false);
					  hybridRadio.setEnabled(false);
					  doubleDerivativeRadio.setEnabled(false);
					  slopeRadio.setEnabled(false);
				  } else {
					  try {
						  if (autoUpdateCheckBox.isSelected()) {
							mainController.setScales(parseScale(scale.getText()));
						  	WorkPanel.getInstance().update();
						  }
					  } catch (NumberFormatException E) {
					  }
				  }
			  }
			  public void insertUpdate(DocumentEvent e) {
				  heronRadio.setEnabled(true);
				  calculusRadio.setEnabled(true);
			      hybridRadio.setEnabled(true);
			      doubleDerivativeRadio.setEnabled(true);
				  slopeRadio.setEnabled(true);
				  try {
					  if (autoUpdateCheckBox.isSelected()) {
						  mainController.setScales(parseScale(scale.getText()));
						  WorkPanel.getInstance().update();
					  }
				  } catch (NumberFormatException E) {
				  }
			  }
			  public void changedUpdate(DocumentEvent e) {	
			  }
			});
		
		
		binSize.getDocument().addDocumentListener(new DocumentListener() {
			  public void removeUpdate(DocumentEvent e) {
				  if (scale.getText().equals("")) {
					  scatter2dRadio.setSelected(true);
					  histogramRadio.setEnabled(false);
				  } else {
					  try {
						  if (autoUpdateCheckBox.isSelected() && histogramRadio.isSelected()) {
							mainController.setScales(parseScale(scale.getText()));
							mainController.setBinSize(binSize.getText());
						  	WorkPanel.getInstance().update();
						  }
					  } catch (NumberFormatException E) {
					  }
				  }
			  }
			  public void insertUpdate(DocumentEvent e) {
				  histogramRadio.setEnabled(true);
				  try {
					  if (autoUpdateCheckBox.isSelected() && profileRadio.isSelected() == false) {
						  histogramRadio.setSelected(true);
						  mainController.setScales(parseScale(scale.getText()));
						  mainController.setBinSize(binSize.getText());
						  WorkPanel.getInstance().update();
					  }
				  } catch (NumberFormatException E) {
				  }
			  }
			  public void changedUpdate(DocumentEvent e) {	
			  }
			});
		
		this.add(calculationTypeRadioPanel);
		this.add(scaleLabel);
		this.add(scale);
		this.add(scaleInstructionLabel);
		this.add(plotTypeRadioPanel);
		this.add(binSizeLabel);
		this.add(binSize);
		this.add(autoUpdateCheckBox);
		this.add(statusLabel);
		this.add(progressBar);
		
		final int VERTICAL_PADDING = 5;
		final int HORIZONTAL_PADDING = 5;
		final int CLOSE = -5;
		
		layout.putConstraint(SpringLayout.NORTH, calculationTypeRadioPanel, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST,calculationTypeRadioPanel,HORIZONTAL_PADDING,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.NORTH,scaleLabel,VERTICAL_PADDING,SpringLayout.SOUTH, calculationTypeRadioPanel);
		layout.putConstraint(SpringLayout.WEST, scaleLabel,HORIZONTAL_PADDING,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.NORTH,scale,VERTICAL_PADDING,SpringLayout.SOUTH, calculationTypeRadioPanel);
		layout.putConstraint(SpringLayout.WEST,scale,HORIZONTAL_PADDING,SpringLayout.EAST,scaleLabel);
		
		layout.putConstraint(SpringLayout.NORTH,scaleInstructionLabel,VERTICAL_PADDING+CLOSE,SpringLayout.SOUTH, scale);
		layout.putConstraint(SpringLayout.WEST,scaleInstructionLabel,HORIZONTAL_PADDING,SpringLayout.WEST,scale);

		layout.putConstraint(SpringLayout.NORTH,plotTypeRadioPanel,VERTICAL_PADDING+CLOSE,SpringLayout.SOUTH, scaleInstructionLabel);
		layout.putConstraint(SpringLayout.WEST,plotTypeRadioPanel,HORIZONTAL_PADDING,SpringLayout.WEST,this);
		layout.putConstraint(SpringLayout.EAST,plotTypeRadioPanel,HORIZONTAL_PADDING,SpringLayout.EAST,calculationTypeRadioPanel);
		
		layout.putConstraint(SpringLayout.NORTH,binSizeLabel,VERTICAL_PADDING,SpringLayout.SOUTH, plotTypeRadioPanel);
		layout.putConstraint(SpringLayout.WEST, binSizeLabel,HORIZONTAL_PADDING,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.NORTH,binSize,VERTICAL_PADDING,SpringLayout.SOUTH, plotTypeRadioPanel);
		layout.putConstraint(SpringLayout.EAST,binSize,HORIZONTAL_PADDING,SpringLayout.EAST,scale);
		
		layout.putConstraint(SpringLayout.SOUTH,autoUpdateCheckBox,VERTICAL_PADDING+CLOSE,SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST,autoUpdateCheckBox,HORIZONTAL_PADDING,SpringLayout.WEST,this);
		
		layout.putConstraint(SpringLayout.SOUTH,progressBar,VERTICAL_PADDING+CLOSE-4,SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST,progressBar,HORIZONTAL_PADDING,SpringLayout.EAST,autoUpdateCheckBox);
		layout.putConstraint(SpringLayout.EAST,progressBar,HORIZONTAL_PADDING+CLOSE+CLOSE,SpringLayout.EAST,this);
		
		layout.putConstraint(SpringLayout.SOUTH,statusLabel,VERTICAL_PADDING+CLOSE-2,SpringLayout.NORTH, progressBar);
		layout.putConstraint(SpringLayout.WEST,statusLabel,0,SpringLayout.WEST,progressBar);
	}

	private LinkedList<Double> parseScale(String s) {
		String[] scales;
		LinkedList<Double> result = new LinkedList<Double>();
		scales = s.split(",");
		hideStatusLabel();
		//if (!WorkPanel.getInstance().isRunning) {
			for (String scale : scales) {
				try {
					//check for dashes to parse scale ranges
					String[] scaleRange = scale.split("-|;");
					if (scaleRange.length != 0) {
						if (scaleRange.length == 1) {
							if (!result.contains(Double.parseDouble(scaleRange[0]))) { //Ensure we don't add the scale twice
								if (Double.parseDouble(scaleRange[0]) != 0) {
									if (isScaleValid(Double.parseDouble(scaleRange[0]))) {
										result.add(Double.parseDouble(scaleRange[0]));
									} else {
										showStatusLabel("A scale is too large"); //Todo, change to a warnign label somewhere else
									}
								}
							}
						} else {
							Double increment;
							if (Double.parseDouble(scaleRange[0]) > Double.parseDouble(scaleRange[1])) {
								//Swap the large and small number
								String temp = scaleRange[0];
								scaleRange[0] = scaleRange[1];
								scaleRange[1] = temp;
							}
							if (scaleRange.length == 3) { //We've included an interval
								increment = Double.parseDouble(scaleRange[2]);
							} else {
								increment = 2 * mainController.getProfile().getSamplingInterval();
							}
							for (double i = Double.parseDouble(scaleRange[0]); i <= Double.parseDouble(scaleRange[1]); i+=increment) {
								if (!result.contains(i)) { //Ensure we don't add the scale twice
									if (i != 0) {
										result.add(i);
									}
								}					
							}
						}
					}
				} catch (NumberFormatException E) {
					if (s.toLowerCase().equals("all")) {
						for (double i = mainController.getProfile().getSmallestPossibleScale(); i <= mainController.getProfile().getLargestPossibleScale(); i+=(2 * mainController.getProfile().getSamplingInterval())) {
							if (!result.contains(i)) { //Ensure we don't add the scale twice
								if (i != 0) {
									result.add(i);
								}
							}										
						}
					}
				}
			}
			return result;
		/*} else {
			return mainController.getScales();
		}*/
	}
	
	private boolean isScaleValid(double scale) {
		return mainController.getProfile().isScaleValid(scale);
	}

	public void hideStatusLabel() {
		this.statusLabel.setVisible(false);
	}
	
	public void showStatusLabel(String text) {
		this.statusLabel.setText(text);
		this.statusLabel.setVisible(true);
	}

	public static OptionPanel getInstance(MainController mainController) {
		if (optionPanel == null) {
			optionPanel = new OptionPanel(mainController);
		}
		return optionPanel;
	}
	
	public static OptionPanel getInstance() {
		return optionPanel;
	}
	
	public void enablePanel() {
		this.calculationTypeRadioPanel.setEnabled(true);
		this.scaleLabel.setEnabled(true);
		this.scale.setEnabled(true);
		this.profileRadio.setEnabled(true);
		scaleInstructionLabel.setEnabled(true);
		autoUpdateCheckBox.setEnabled(true);
		this.plotTypeRadioPanel.setEnabled(true);
		scatter2dRadio.setEnabled(true);
		scatter3dRadio.setEnabled(true);
		binSizeLabel.setEnabled(true);
		binSize.setEnabled(true);
	}
	
	public void disablePanel() {
		this.calculationTypeRadioPanel.setEnabled(false);
		this.scaleLabel.setEnabled(false);
		this.scale.setEnabled(false);
		this.profileRadio.setEnabled(false);
		scaleInstructionLabel.setEnabled(false);
		autoUpdateCheckBox.setEnabled(false);
		this.plotTypeRadioPanel.setEnabled(false);
		scatter2dRadio.setEnabled(false);
		scatter3dRadio.setEnabled(false);
		binSizeLabel.setEnabled(false);
		binSize.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("AutoUpdateToggled")) {
			if (autoUpdateCheckBox.isSelected()) {
				mainController.setScales(parseScale(scale.getText()));
				WorkPanel.getInstance().update();
			}
		} else if (e.getActionCommand().equals("Histogram") || e.getActionCommand().equals("2DScatter") || e.getActionCommand().equals("3DScatter")) {
			mainController.setPlotType(e.getActionCommand());
			WorkPanel.getInstance().update();
		} else {
			mainController.setCalculationType(e.getActionCommand());
			WorkPanel.getInstance().update();
			if (e.getActionCommand().equals("Profile")) {
				histogramRadio.setEnabled(false);
				if (!scatter3dRadio.isSelected()) {
					scatter2dRadio.setSelected(true);
				}
			} else {
				histogramRadio.setEnabled(true);	
			}
		}
	}
	
	
	public JRadioButton getScatter2dRadio() {
		return this.scatter2dRadio;
	}

	public void setProgress(int i) {
		progressBar.setValue(i);
	}
	
	public void showProgressBar() {
		autoUpdateCheckBox.setSelected(false);
		progressBar.setVisible(true);
	}
	
	public void hideProgressBar() {
		progressBar.setVisible(false);
		autoUpdateCheckBox.setSelected(true);
	}
}
