package edu.wpi.surflab.curvature.model;

import java.util.ArrayList;
import java.util.List;

public class Surface {
	List<DataPoint3D> points = new ArrayList<DataPoint3D>();
	private String units;
	Double minXPosition = null;
	Double minYPosition = null;
	Double minZPosition = null;
	Double maxXPosition = null;
	Double maxYPosition = null;
	Double maxZPosition = null;
	Double verticalSamplingInterval = null;
	Double horizontalSamplingInterval = null;
	
	public Surface() {
		this.points = new ArrayList<DataPoint3D>();
	}
	
	public void add(DataPoint3D dataPoint) {
		points.add(dataPoint);
		resetMinMaxCachedValues();
	}
	
	public List<DataPoint3D> getSurface() {
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
			return maxZPosition;
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
	
	public double verticalSamplingInterval() {
		if (verticalSamplingInterval != null) {
			return verticalSamplingInterval;
		}
		verticalSamplingInterval = this.points.get(1).getY() - this.points.get(0).getY();
		return verticalSamplingInterval;
	}
	
	public double horizontalSamplingInterval() {
		if (horizontalSamplingInterval != null) {
			return horizontalSamplingInterval;
		}
		for (DataPoint3D p: points) {	
			if (!p.getX().equals(this.points.get(0).getX())) {				
				horizontalSamplingInterval = (p.getX() - this.points.get(0).getX());
				return horizontalSamplingInterval;
			}	
		}
		return 0;
	}	
	
	/* THE CODE WAS AS BELOW, BUT WAS CHANGED (REPLACED BY ABOVE) WHEN A POSSIBLE BUG WAS FOUND. IT MIGHT STILL BE RIGHT. IF MORE BUGS SHOW UP, CHECK HERE FIRST
	public double horizontalSamplingInterval() {
		if (horizontalSamplingInterval != null) {
			return horizontalSamplingInterval;
		}
		horizontalSamplingInterval = this.points.get(1).getX() - this.points.get(0).getX();
		System.out.println(this.points.get(1).getX() + "::"+this.points.get(0).getX());
		System.out.println("hSamp:" + horizontalSamplingInterval);
		return horizontalSamplingInterval;
	}
	
	public double verticalSamplingInterval() {
		if (verticalSamplingInterval != null) {
			return verticalSamplingInterval;
		}
		for (DataPoint3D p: points) {	
			if (p.getX() != this.points.get(0).getX() && p.getY() != 0) {
				System.out.println(p.getY()+","+this.points.get(0).getY());
				verticalSamplingInterval = (p.getY() - this.points.get(0).getY());
				System.out.println("vSamp:" + verticalSamplingInterval);
				return verticalSamplingInterval;
			}	
		}
		System.out.println("vSamp:" + 0);
		return 0;

		
		//return 0;
		//verticalSamplingInterval = this.points.get(((points.size()-1)/2)+3).getY() - this.points.getFirst().getY();
		//return verticalSamplingInterval;
	}	
	*/
}


