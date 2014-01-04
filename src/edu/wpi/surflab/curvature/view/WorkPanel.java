package edu.wpi.surflab.curvature.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import net.ericaro.surfaceplotter.JSurfacePanel;
import net.ericaro.surfaceplotter.Mapper;
import net.ericaro.surfaceplotter.ProgressiveSurfaceModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.wpi.surflab.curvature.controller.MainController;
import edu.wpi.surflab.curvature.model.DataPoint2D;

@SuppressWarnings("serial")
public class WorkPanel extends JPanel implements Runnable {

	static WorkPanel workPanel;
	private MainController mainController;
	public boolean isRunning;
	private Thread t;
	
	ProgressiveSurfaceModel model;
	private float chartRotation;
	private float chartElevation;
	
	private WorkPanel(MainController mainController) {
		setLayout(new BorderLayout());
		this.mainController = mainController;       
	}

	public static WorkPanel getInstance(MainController mainController) {
		if (workPanel == null) {
			workPanel = new WorkPanel(mainController);
		}
		return workPanel;
	}
	
	public static WorkPanel getInstance() {
		return workPanel;
	}
	
	public void run() {
		removeAll();
		if (mainController.getMode().equals("Profile")){
			graphProfileMode();
		} else if(mainController.getMode().equals("Surface")){
			graphSurfaceMode();
		}
		validate();
		isRunning = false;
		//OptionPanel.getInstance().enablePanel();
	}

	public void update() {
		if (!isRunning) {
			t = new Thread(this);
	    	t.start();
	    	isRunning = true;
	    	//OptionPanel.getInstance().disablePanel();
		}
	}
	
	private void graphSurfaceMode() {
		//System.out.println("Surface Mode");
		drawSurface();
	}
	
	private void drawSurface() {		
		JSurfacePanel surfacePanel = new JSurfacePanel();

        surfacePanel.setTitleText("");
        surfacePanel.setBackground(Color.white);
        surfacePanel.setTitleFont(surfacePanel.getTitleFont().deriveFont(surfacePanel.getTitleFont().getStyle() | Font.BOLD, surfacePanel.getTitleFont().getSize() + 6f));
        surfacePanel.setConfigurationVisible(false);
		surfacePanel.getSurface().setXLabel("X ("+mainController.getSurface().getUnits()+")");
		surfacePanel.getSurface().setYLabel("Y ("+mainController.getSurface().getUnits()+")");
		
		this.savePointOfView();
        model = new ProgressiveSurfaceModel() ;        
        surfacePanel.setModel(model);
           
        //TODO: Complete the below two lines
        //model.setYMin((float) mainController.getSurface().getMinPosition());
        //model.setYMax((float) mainController.getSurface().getMaxPosition());            
                    
        model.setDisplayXY(true);
        model.setDisplayZ(true);
 
        this.restorePointOfView();
        
        /*
        model.setMapper(new Mapper() {
                public  float f1( float a, float b) {
                	                    	

                	int x = (int) (map(a,model.getXMin(),model.getXMax(),0,mainController.getSurface().allCalculatedScales.size())*.999);
                	//int y = (int) (map(b,model.getYMin(),model.getYMax(),0,mainController.getProfile().allCalculatedPoints.get(x).size())*.999);            	
                	int y = (int) (map(b,model.getYMin(),model.getYMax(),0,mainController.getSurface().allCalculatedPoints.getFirst().size())*.999);
                	
                	
                	try {
                		float z = mainController.getSurface().allCalculatedPoints.get(x).get(y).getY().floatValue();
                		//System.out.println(y+"--"+mainController.getProfile().allCalculatedPoints.get(x).size()+"--"+model.getYMax());
                		return z;
                	}  catch (Exception  e) {
                		//e.printStackTrace();
                		//System.out.println("Exception");
                		return 0;
                	}
                	//System.out.println(x+"/"+mainController.getProfile().allCalculatedScales.size()+","+y+"/"+mainController.getProfile().allCalculatedPoints.get(0).size()+","+z);             
                	
                }

				@Override
				public float f2(float x, float y) {
					// TODO Auto-generated method stub
					return 0;
				}
        });
    */
        model.plot().execute();
        this.add(surfacePanel);
	}

	@SuppressWarnings("deprecation")
	private void graphProfileMode() {
		JFreeChart chart = null;
		
		if (mainController.getPlotType().equals("2DScatter")) {
		
			String xLabel = "Position ("+mainController.getProfile().getUnits()+")";
			
			if (mainController.getCalculationType().equals("Profile")) {
				chart = ChartFactory.createScatterPlot("Profile", xLabel, "Height ("+mainController.getProfile().getUnits()+")", this.makeProfileDataSet(), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Heron")) {
				chart = ChartFactory.createScatterPlot("Heron's Curvature", xLabel, "Curvature (1/"+mainController.getProfile().getUnits()+")", this.makeCurvatureDataSet("Heron"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Calculus")) {
				chart = ChartFactory.createScatterPlot("Calculus Curvature", xLabel, "Curvature (1/"+mainController.getProfile().getUnits()+")", this.makeCurvatureDataSet("Calculus"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Hybrid")) {
				chart = ChartFactory.createScatterPlot("Heron-Calculus Hybrid Curvature", xLabel, "Curvature (1/"+mainController.getProfile().getUnits()+")", this.makeCurvatureDataSet("Hybrid"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("DoubleDerivative")) {
				chart = ChartFactory.createScatterPlot("Double Differential Curvature Approximation",xLabel, "Curvature (1/"+mainController.getProfile().getUnits()+")", this.makeCurvatureDataSet("DoubleDerivative"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Slope")) {
				chart = ChartFactory.createScatterPlot("Slope", xLabel, "Slope ("+mainController.getProfile().getUnits()+")", this.makeCurvatureDataSet("Slope"), PlotOrientation.VERTICAL, true,false,false);
			}
		
			final XYPlot plot = (XYPlot) chart.getPlot();
			
			Shape dot = new Ellipse2D.Double(0,0,2,2);
			XYItemRenderer renderer = plot.getRenderer();
			renderer.setShape(dot);	
			plot.setNoDataMessage("No data available");
			
			final JPanel chartPanel = new ChartPanel(chart);
			this.add(chartPanel);	
		} else if (mainController.getPlotType().equals("Histogram")) {
			
			if (mainController.getCalculationType().equals("Profile")) {
				chart = ChartFactory.createScatterPlot("Profile", "Position ("+mainController.getProfile().getUnits()+")", "Height ("+mainController.getProfile().getUnits()+")", this.makeProfileDataSet(), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Heron")) {
				chart = ChartFactory.createBarChart( "Histogram", "Curvature (1/"+mainController.getProfile().getUnits()+")", "Frequency",  this.makeHistogramDataSet("Heron"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Calculus")) {
				chart = ChartFactory.createBarChart( "Histogram", "Curvature (1/"+mainController.getProfile().getUnits()+")", "Frequency",  this.makeHistogramDataSet("Calculus"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Hybrid")) {
				chart = ChartFactory.createBarChart( "Histogram", "Curvature (1/"+mainController.getProfile().getUnits()+")", "Frequency",  this.makeHistogramDataSet("Hybrid"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("DoubleDerivative")) {
				chart = ChartFactory.createBarChart( "Histogram", "Curvature (1/"+mainController.getProfile().getUnits()+")", "Frequency",  this.makeHistogramDataSet("DoubleDerivative"), PlotOrientation.VERTICAL, true,false,false);
			} else if (mainController.getCalculationType().equals("Slope")) {
				chart = ChartFactory.createBarChart( "Histogram", "Slope ("+mainController.getProfile().getUnits()+")", "Frequency",  this.makeHistogramDataSet("Slope"), PlotOrientation.VERTICAL, true,false,false);
			}
			
			if (!mainController.getCalculationType().equals("Profile")) {
				final CategoryPlot plot = (CategoryPlot) chart.getPlot();
				plot.setNoDataMessage("No data available");
				
			    //CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
				//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
				
			} else {
				final XYPlot plot = (XYPlot) chart.getPlot();			
				Shape dot = new Ellipse2D.Double(0,0,2,2);
				XYItemRenderer renderer = plot.getRenderer();
				renderer.setShape(dot);		
				plot.setNoDataMessage("No data available");				
			}
			
			final JPanel chartPanel = new ChartPanel(chart);
			this.add(chartPanel);		
		} else if (mainController.getPlotType().equals("3DScatter")) {	
			
			ProfileOptionPanel.getInstance().showProgressBar();
			ProfileOptionPanel.getInstance().setProgress(0);
			ProfileOptionPanel.getInstance().showStatusLabel("Calculating Values");
			int count = 0;
			mainController.getProfile().allCalculatedPoints = new LinkedList<LinkedList<DataPoint2D>>();
			mainController.getProfile().allCalculatedScales = new LinkedList<Double>();	
			if (!mainController.getCalculationType().equals("Profile")) {	
				for (Double scale: mainController.getScales()) {
					mainController.getProfile().allCalculatedPoints.add(mainController.getProfile().calculateCurvature(mainController.getCalculationType(), scale));
					mainController.getProfile().allCalculatedScales.add(scale);
					if (mainController.getProfile().allCalculatedPoints.getLast().isEmpty()){
						mainController.getProfile().allCalculatedPoints.removeLast();
						mainController.getProfile().allCalculatedScales.removeLast();
					}
					ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/mainController.getScales().size())*100));
					count++;
				}
			} else {
				mainController.getProfile().allCalculatedPoints.add(mainController.getProfile().getProfile());
				mainController.getProfile().allCalculatedScales.add(0.0);
			}
			
			
			JSurfacePanel surfacePanel = new JSurfacePanel();

            surfacePanel.setTitleText("");
            surfacePanel.setBackground(Color.white);
            surfacePanel.setTitleFont(surfacePanel.getTitleFont().deriveFont(surfacePanel.getTitleFont().getStyle() | Font.BOLD, surfacePanel.getTitleFont().getSize() + 6f));
            surfacePanel.setConfigurationVisible(false);
			surfacePanel.getSurface().setXLabel("Scale ("+mainController.getProfile().getUnits()+")");
			surfacePanel.getSurface().setYLabel("Position ("+mainController.getProfile().getUnits()+")");
			
			this.savePointOfView();
            model = new ProgressiveSurfaceModel() ;        
            surfacePanel.setModel(model);
               

	        if (mainController.getScales().size() > 0 || mainController.getCalculationType().equals("Profile")) {
		        model.setXMin(mainController.getProfile().allCalculatedScales.getFirst().floatValue());
		        if (mainController.getProfile().allCalculatedScales.size() != 1) { 
		        	model.setXMax(mainController.getProfile().allCalculatedScales.getLast().floatValue());
		        } else {
		        	if (mainController.getCalculationType().equals("Profile")){
		        		model.setXMin(1);
		        		model.setXMax(1.0001f);
		        	} else {
		        		model.setXMax(mainController.getProfile().allCalculatedScales.getFirst().floatValue() + 0.0001f);
		        	}
		        }
		        model.setYMin((float) mainController.getProfile().getMinPosition());
		        model.setYMax((float) mainController.getProfile().getMaxPosition());            
		                    
		        model.setDisplayXY(true);
		        model.setDisplayZ(true);
		 
		        this.restorePointOfView();
		        
		        model.setMapper(new Mapper() {
		                public  float f1( float a, float b) {
		                	                    	
		                	
		                	int x = (int) (map(a,model.getXMin(),model.getXMax(),0,mainController.getProfile().allCalculatedScales.size())*.999);
		                	//int y = (int) (map(b,model.getYMin(),model.getYMax(),0,mainController.getProfile().allCalculatedPoints.get(x).size())*.999);            	
		                	int y = (int) (map(b,model.getYMin(),model.getYMax(),0,mainController.getProfile().allCalculatedPoints.getFirst().size())*.999);
		                	
		                	
		                	try {
		                		float z = mainController.getProfile().allCalculatedPoints.get(x).get(y).getY().floatValue();
		                		//System.out.println(y+"--"+mainController.getProfile().allCalculatedPoints.get(x).size()+"--"+model.getYMax());
		                		return z;
		                	}  catch (Exception  e) {
		                		//e.printStackTrace();
		                		//System.out.println("Exception");
		                		return 0;
		                	}
		                	//System.out.println(x+"/"+mainController.getProfile().allCalculatedScales.size()+","+y+"/"+mainController.getProfile().allCalculatedPoints.get(0).size()+","+z);             
		                	
		                }
		
						@Override
						public float f2(float x, float y) {
							// TODO Auto-generated method stub
							return 0;
						}
		        });
	    
	        }
            model.plot().execute();

            this.add(surfacePanel);
		}
		ProfileOptionPanel.getInstance().hideProgressBar();
		ProfileOptionPanel.getInstance().hideStatusLabel();
	}
	
	public void savePointOfView() {
		if (model == null){
			this.chartRotation = 60;
			this.chartElevation = 20;
		} else {
			this.chartRotation = model.getProjector().getRotationAngle();
			this.chartElevation = model.getProjector().getElevationAngle();
		}
	}
	
	public void restorePointOfView() {
		model.getProjector().setRotationAngle(this.chartRotation);
		model.getProjector().setElevationAngle(this.chartElevation);
	}
	
	private float map(float x, float in_min, float in_max, float out_min, float out_max) {
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	private CategoryDataset makeHistogramDataSet(String calculationType) {
		int count;
		
		double binSize = mainController.getBinSize();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();       

		//Calculate the curvatures
		ProfileOptionPanel.getInstance().showStatusLabel("Calculating Values");
		ProfileOptionPanel.getInstance().showProgressBar();
		ProfileOptionPanel.getInstance().setProgress(0);
		mainController.getProfile().allCalculatedPoints = new LinkedList<LinkedList<DataPoint2D>>();
		mainController.getProfile().allCalculatedScales = new LinkedList<Double>();	
		count = 0;
		for (Double scale: mainController.getScales()) {
			mainController.getProfile().allCalculatedPoints.add(mainController.getProfile().calculateCurvature(calculationType, scale));
			mainController.getProfile().allCalculatedScales.add(scale);
			ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/mainController.getScales().size())*100));
			//System.out.println((int)((((float)count)/mainController.getScales().size())*100));
			count++;
		}
		
		//prepare bin statistics
		ProfileOptionPanel.getInstance().showStatusLabel("Preparing Bins");
		double maxBin = mainController.getProfile().getMaxCurvature();
		double minBin = mainController.getProfile().getMinCurvature();		
		maxBin = Math.ceil(maxBin/ binSize) * binSize;
		minBin = Math.floor(minBin/ binSize) * binSize;
		int binCount = 0;
		for (double bin = minBin; bin < maxBin; bin+=binSize) {
			binCount++;
		} 

		
		if (binCount > 0) {
		    //zero the bins
		    //LinkedList<DataPoint> distribution = new LinkedList<DataPoint>();
			DataPoint2D[] distribution = new DataPoint2D[binCount];
		    //distribution.add(new DataPoint(minBin,0.0));
			distribution[0] = new DataPoint2D(minBin,0.0);
			int i = 1;
		    /*while (distribution.size() < binCount) {
		      distribution.add(new DataPoint(distribution.getLast().getX()+binSize,0.0));
		    }*/
			count = 0;
		    while (i < binCount) {
		      distribution[i] = new DataPoint2D(distribution[i-1].getX()+binSize,0.0);
		      i++;
		      ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/binCount)*100));
		      count++;
		    }
		    		  
		    //Fill the bins
			ProfileOptionPanel.getInstance().showStatusLabel("Filling Bins");
		    count = 0;
		    int bin;	  
		    for (LinkedList<DataPoint2D> curvaturesAtScale: mainController.getProfile().allCalculatedPoints) {
			   for (DataPoint2D point: curvaturesAtScale) {		  
			 	  bin = (int)(((Math.floor((point.getY()) / binSize) * binSize)-minBin)/binSize);
			 	  //distribution.get(bin).incrementY();
			 	  distribution[bin].incrementY();
			   }
			   ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/mainController.getProfile().allCalculatedPoints.size())*100));
			   count++;
		    } 
			
		    mainController.getProfile().calculatedDistribution = distribution;
		    
			ProfileOptionPanel.getInstance().showStatusLabel("Preparing Plot");
		    count = 0;
			for (DataPoint2D point: distribution) {
				dataset.addValue(point.getY(), calculationType, point.getX());
				ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/distribution.length)*100));
				count++;
			}
		}
		ProfileOptionPanel.getInstance().hideStatusLabel();
		ProfileOptionPanel.getInstance().hideProgressBar();
		return dataset;
	}

	private XYDataset makeCurvatureDataSet(String calculationType) {
		XYSeriesCollection dataSet = new XYSeriesCollection();
		
		mainController.getProfile().allCalculatedPoints = new LinkedList<LinkedList<DataPoint2D>>();
		mainController.getProfile().allCalculatedScales = new LinkedList<Double>();
		
		ProfileOptionPanel.getInstance().showProgressBar();
		ProfileOptionPanel.getInstance().setProgress(0);
		ProfileOptionPanel.getInstance().showStatusLabel("Calculating Values");
		int count = 0;
		for (Double scale: mainController.getScales()) {
			XYSeries series = new XYSeries("Scale:"+scale);
		
			mainController.getProfile().allCalculatedPoints.add(mainController.getProfile().calculateCurvature(calculationType, scale));
			mainController.getProfile().allCalculatedScales.add(scale);
			
			for (DataPoint2D point: mainController.getProfile().allCalculatedPoints.getLast()) {
				Double x = point.getX();
				Double y = point.getY();
				series.add(x,y);
			}
			dataSet.addSeries(series);
			ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/mainController.getScales().size())*100));
			count++;
		}
		 
		ProfileOptionPanel.getInstance().hideProgressBar();
		ProfileOptionPanel.getInstance().hideStatusLabel();
		return dataSet;
	}

	public XYDataset makeProfileDataSet() {
		ProfileOptionPanel.getInstance().showProgressBar();
		ProfileOptionPanel.getInstance().setProgress(0);
		ProfileOptionPanel.getInstance().showStatusLabel("Plotting Profile");
		
	   XYSeriesCollection dataSet = new XYSeriesCollection();
	    XYSeries series = new XYSeries("Profile");
	    int count = 0;
	    for (DataPoint2D point: mainController.getProfile().getProfile()) {
	    	Double x = point.getX();
	    	Double y = point.getY();
	        series.add(x,y);
	        ProfileOptionPanel.getInstance().setProgress((int)((((float)count)/mainController.getProfile().getProfile().size())*100));
	        count++;
	    }
	    dataSet.addSeries(series);
	    ProfileOptionPanel.getInstance().hideProgressBar();
	    ProfileOptionPanel.getInstance().hideStatusLabel();
	   return dataSet;
	}
}
