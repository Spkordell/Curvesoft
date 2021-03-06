/**
 * 
 */
package edu.wpi.surflab.curvature.model;

import java.util.LinkedList;

/**
 * @author Steve Kordell
 *
 */
public class Profile {

	LinkedList<DataPoint2D> points = new LinkedList<DataPoint2D>();
	private LinkedList<DataPoint2D> calculatedPoints;
	public LinkedList<LinkedList<DataPoint2D>> allCalculatedPoints;
	public LinkedList<Double> allCalculatedScales;
	public DataPoint2D[] calculatedDistribution;
	private String units;
	
	public void add(DataPoint2D dataPoint) {
		points.add(dataPoint);
	}

	public LinkedList<DataPoint2D> getProfile() {
		return points;
	}

	public LinkedList<DataPoint2D> calculateCurvature(String calculationType, Double scale) {
		int shift = 1;
		
		DataPoint2D one;
		DataPoint2D two;
		DataPoint2D three;
		
		calculatedPoints = new LinkedList<DataPoint2D>();
		
		/*
		for (DataPoint2D p: this.points) {
			System.out.println("("+p.getX()+", "+p.getY()+")");
		}
		*/
		
		if ((int)(scale/getSamplingInterval()) < points.size()) {
			one = points.get(0);
			two = points.get((int)(scale/getSamplingInterval())/2);
			three = points.get((int)(scale/getSamplingInterval()));
			  
			while (three.getX() < (points.getLast().getX())) {
				if (calculationType.equals("Heron")) {
					calculatedPoints.add(heronCurvature(one,two,three));
				} else if (calculationType.equals("Calculus")) {
					calculatedPoints.add(calculusCurvature(one,two,three));		
				} else if (calculationType.equals("Hybrid")) {
					calculatedPoints.add(hybridCurvature(one,two,three, scale));		
				} else if (calculationType.equals("DoubleDerivative")) {
					calculatedPoints.add(doubleDerivativeCurvature(one,two,three));		
				} else if (calculationType.equals("Slope")) {
					calculatedPoints.add(slope(one,three));		
				} 
				one = points.get(shift);
				two = points.get((int)(scale/getSamplingInterval())/2 + shift);
				three = points.get((int)(scale/getSamplingInterval()) + shift);	
				shift++;
			}
		}
		return calculatedPoints;
	}
		
	/** Calculates the curvature using herons method given three points
	 * @param one The first data point
	 * @param two The middle data point
	 * @param three The last data point
	 * @returns The curvature of the three points as determined using heron's method
	*/ 
	private DataPoint2D heronCurvature(DataPoint2D one, DataPoint2D two, DataPoint2D three) {
		  if (((one.getY()+three.getY())/2) == two.getY()) {
		    return new DataPoint2D(two.getX(),(double)0); //If the three points lie on the same line, the curvature is 0;
		  }

		  double a = Math.sqrt(square(two.getX() - one.getX()) + square(two.getY() - one.getY()));
		  double b = Math.sqrt(square(three.getX() - two.getX()) + square(three.getY() - two.getY()));
		  double c = Math.sqrt(square(three.getX() - one.getX()) + square(three.getY() - one.getY()));

		  double s = .5 * (a + b + c);
		  double k = Math.sqrt(s*poz(s-a)*poz(s-b)*poz(s-c));
		  double r = (a*b*c)/(4*k);
		  
		  if (((one.getY()+three.getY())/2) < two.getY()) {
		    r*=-1;
		  }

		  return new DataPoint2D(two.getX(),1/r);
	}
	
	
	/** Calculates the curvature using herons method and the calculus method, then determines which is better, given three points
	 * @param one The first data point
	 * @param two The middle data point
	 * @param three The last data point
	 * @returns The curvature of the three points as determined using either heron's or the calculus method, whichever is better
	*/ 
	private DataPoint2D hybridCurvature(DataPoint2D one, DataPoint2D two, DataPoint2D three, double scale) {
	  double a = Math.sqrt(square(two.getX() - one.getX()) + square(two.getY() - one.getY()));
	  double b = Math.sqrt(square(three.getX() - two.getX()) + square(three.getY() - two.getY()));

	  double comp = (a*Math.sqrt(2) + b*Math.sqrt(2))/2;

	  if (comp > three.getX() - one.getX()) {
	    return calculusCurvature(one,two,three);
	  } else {
	    return heronCurvature(one,two,three);
	  }
	}

	/** Calculates the curvature using the double derivative approximation method given three points
	 * @param one The first data point
	 * @param two The middle data point
	 * @param three The last data point
	 * @returns The curvature of the three points as determined using the double derivative approximation method
	*/ 
	private DataPoint2D doubleDerivativeCurvature(DataPoint2D one, DataPoint2D two, DataPoint2D three){
	  return new DataPoint2D(two.getX(),(three.getY()-2*two.getY()+one.getY())/square(two.getX()-one.getX()));
	}
	
	/** Squares a number
	 * @param b The number to square (multiply by itself)
	 * @return The result of the squaring operation
	 */
	private double square(double b) {
	    return  b*b;
	}
	
	/** Cubes a number
	 * @param b The number to cube
	 * @return The result of the cubing operation
	 */
	private double cube(double b) {
	    return  b*b*b;
	}
	
	/** PositiveOrZero - If the input is negative, makes it zero, otherwise returns the input
	 * @param b The incoming value to poz
	 * @return The input value or, if a value is less than 0, returns 0.
	*/
	private double poz(double b) {
	  if (b < 0) {
	    return 0;
	  } else {
	    return b;
	  }
	}	
	
	/** Calculates the curvature using calculus  method given three points
	 * @param one The first data point
	 * @param two The middle data point
	 * @param three The last data point
	 * @returns The curvature of the three points as determined using the calculus method
	*/ 
	private DataPoint2D calculusCurvature(DataPoint2D one, DataPoint2D two, DataPoint2D three) {
	  double yprime = (three.getY() - one.getY())/(2* (two.getX()-one.getX())); 
	  double ydoubleprime = (three.getY()-2*two.getY()+one.getY())/square(two.getX()-one.getX());
	  return new DataPoint2D(two.getX(),ydoubleprime/cube(Math.sqrt(1+square(yprime))));
	}

	
	/** Calculates the slope of two points
	* @param one The first data point
	* @param two The second data point
	* @returns The slope of line between two points
	*/
	private DataPoint2D slope(DataPoint2D one, DataPoint2D two){
	  return new DataPoint2D(two.getX(),(two.getY() - one.getY())/(two.getX() - one.getX()));
	}

	public LinkedList<DataPoint2D> getCalculatedData() {
		return this.calculatedPoints;
	}

	/** Determines the sampling interval of the profile
	 * @param profile A point array representing the profile (must contain at least two points)
	 * @return The sampling interval of the profile
	 */
	public double getSamplingInterval() {
		if (this.points.get(0).getX().equals(this.points.get(1).getX())) {
			this.points.remove(0);
		}
		//System.out.println("SI:"+points.get(1).getX()+"-"+points.get(0).getX());
		return points.get(1).getX() - points.get(0).getX();
	}

	public double getSmallestPossibleScale() {
		return 2*getSamplingInterval();
	}

	public double getLargestPossibleScale() {
		return points.getLast().getX();
	}

	public double getMinCurvature() {
		double min = Double.POSITIVE_INFINITY;
		for (LinkedList<DataPoint2D> a: this.allCalculatedPoints) {
			for (DataPoint2D b: a) {
				if (b.getY() < min) {
					min = b.getY();
				}
			}
		}
		return min;
	}
	
	public double getMaxCurvature() {
		double max = Double.NEGATIVE_INFINITY;
		for (LinkedList<DataPoint2D> a: this.allCalculatedPoints) {
			for (DataPoint2D b: a) {
				if (b.getY() > max) {
					max = b.getY();
				}
			}
		}
		return max;
	}
	
	public double getMinPosition() {
		double min = Double.POSITIVE_INFINITY;
		for (LinkedList<DataPoint2D> a: this.allCalculatedPoints) {
			if (a.getFirst().getX() < min) {
				min = a.getFirst().getX();
			}
		}
		return min;
	}
	
	
	public double getMaxPosition() {
		double max = Double.NEGATIVE_INFINITY;
		for (LinkedList<DataPoint2D> a: this.allCalculatedPoints) {
			if (a.getLast().getX() > max) {
				max = a.getLast().getX();
			}
		}
		return max;
	}

	public boolean isScaleValid(double scale) {
		if (/*(scale % 2*getSamplingInterval()) < 0.0001 && */scale <= getMaxScale()/* && scale != 0*/) {
			return true;
		} else {
			return false;
		}
	}

	private double getMaxScale() {
		return this.points.getLast().getX();
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	public String getUnits() {
		return this.units;
	}
	
	/*
	public double[][] getProfileAsArray() {
		double[][] profile = new double[points.size()][points.size()];
		for (int i = 0; i < points.size(); i++) {
			profile[i][0] = this.points.get(i).getX();
			profile[0][i] = this.points.get(i).getY();
		}
		
		return profile;
	}
*/
}
