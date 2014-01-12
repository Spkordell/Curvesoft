package edu.wpi.surflab.curvature.model;

import java.util.LinkedList;

public class Surface {
	LinkedList<DataPoint3D> points = new LinkedList<DataPoint3D>();
	private String units;
	Double minXPosition = null;
	Double minYPosition = null;
	Double minZPosition = null;
	Double maxXPosition = null;
	Double maxYPosition = null;
	Double maxZPosition = null;
	Double horizontalSamplingInterval = null;
	Double verticalSamplingInterval = null;
	
	public Surface() {
		this.points = new LinkedList<DataPoint3D>();
	}
	
	public void add(DataPoint3D dataPoint) {
		points.add(dataPoint);
		resetMinMaxCachedValues();
	}
	
	public LinkedList<DataPoint3D> getSurface() {
		return points;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getUnits() {
		return this.units;
	}

	public double getMinXPosition() {
		if (minXPosition != null) {
			return minXPosition;
		}
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getX() < min) {
				min = p.getX();
			}
		}
		minXPosition = min;
		return min;
	}
	
	public double getMinYPosition() {
		if (minYPosition != null) {
			return minYPosition;
		}
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getY() < min) {
				min = p.getY();
			}
		}
		minYPosition = min;
		return min;
	}
	
	public double getMinZPosition() {
		if (minZPosition != null) {
			return minZPosition;
		}
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getZ() < min) {
				min = p.getZ();
			}
		}
		minZPosition = min;
		return min;
	}
	
	public double getMaxXPosition() {
		if (maxXPosition != null) {
			return maxXPosition;
		}
		double max = Double.NEGATIVE_INFINITY;		
		for (DataPoint3D p: points) {
			if (p.getX() > max) {
				max = p.getX();
			}
		}
		maxXPosition = max;
		return max;
	}
	
	public double getMaxYPosition() {
		if (maxYPosition != null) {
			return maxYPosition;
		}
		double max = Double.NEGATIVE_INFINITY;
		for (DataPoint3D p: points) {
			if (p.getY() > max) {
				max = p.getY();
			}
		}
		maxYPosition = max;
		return max;
	}
	
	public double getMaxZPosition() {
		if (maxYPosition != null) {
			return maxXPosition;
		}
		double max = Double.NEGATIVE_INFINITY;
		for (DataPoint3D p: points) {
			if (p.getZ() > max) {
				max = p.getZ();
			}
		}
		maxZPosition = max;
		return max;
	}
	
	public double getHeight(float x, float y) {
		for (DataPoint3D p: points) {
			if (p.getX() >= x && p.getY() >= y) {
				return p.getZ();
			}
		}
		return 0;
	}
	
	public void resetMinMaxCachedValues() {
		minXPosition = null;
		minYPosition = null;
		minZPosition = null;
		maxXPosition = null;
		maxYPosition = null;
		maxZPosition = null;
	}

	public double findClosestXProfile(Double profileSelection) {
		for (DataPoint3D p: points) {
			if (p.getX() >= profileSelection) {
				return p.getX();
			}
		}
		return 0;
		
		/*
		float tolerence = 0;
		while(true) {
			for (DataPoint3D p: points) {
				if(profileSelection <= p.getX()+tolerence && profileSelection >= p.getX()-tolerence){
					return p.getX();
				}
			}
			tolerence+=.0001;
		}
		*/
	}
	
	public double horizontalSamplingInterval() {
		if (horizontalSamplingInterval != null) {
			return horizontalSamplingInterval;
		}
		horizontalSamplingInterval = this.points.get(1).getX() - this.points.getFirst().getX();
		return horizontalSamplingInterval;
	}
	
	public double verticalSamplingInterval() {
		if (verticalSamplingInterval != null) {
			return verticalSamplingInterval;
		}
		for (DataPoint3D p: points) {	
			if (p.getX() != this.points.getFirst().getX() && p.getY() != 0) {
				System.out.println(p.getY()+","+this.points.getFirst().getY());
				verticalSamplingInterval = (p.getY() - this.points.getFirst().getY());
				return verticalSamplingInterval;
			}	
		}
		return 0;

		
		//return 0;
		//verticalSamplingInterval = this.points.get(((points.size()-1)/2)+3).getY() - this.points.getFirst().getY();
		//return verticalSamplingInterval;
	}	
}


