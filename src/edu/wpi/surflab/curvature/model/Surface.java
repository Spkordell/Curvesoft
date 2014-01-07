package edu.wpi.surflab.curvature.model;

import java.util.LinkedList;

public class Surface {
	LinkedList<DataPoint3D> points = new LinkedList<DataPoint3D>();
	private String units;
	
	public Surface() {
		this.points = new LinkedList<DataPoint3D>();
	}
	
	public void add(DataPoint3D dataPoint) {
		points.add(dataPoint);
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
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getX() < min) {
				min = p.getX();
			}
		}
		return min;
	}
	
	public double getMinYPosition() {
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getY() < min) {
				min = p.getY();
			}
		}
		return min;
	}
	
	public double getMinZPosition() {
		double min = Double.POSITIVE_INFINITY;
		for (DataPoint3D p: this.points) {
			if (p.getZ() < min) {
				min = p.getZ();
			}
		}
		return min;
	}
	
	public double getMaxXPosition() {
		double max = Double.NEGATIVE_INFINITY;
		for (DataPoint3D p: points) {
			if (p.getX() > max) {
				max = p.getX();
			}
		}
		return max;
	}
	
	public double getMaxYPosition() {
		double max = Double.NEGATIVE_INFINITY;
		for (DataPoint3D p: points) {
			if (p.getY() > max) {
				max = p.getY();
			}
		}
		return max;
	}
	
	public double getMaxZPosition() {
		double max = Double.NEGATIVE_INFINITY;
		for (DataPoint3D p: points) {
			if (p.getZ() > max) {
				max = p.getZ();
			}
		}
		return max;
	}

	public double getHeight(float x, float y) {
		//return x*y;
		float tolerence = 0;
		while(true) {
			for (DataPoint3D p: points) {
				if(x <= p.getX()+tolerence && x >= p.getX()-tolerence && y <= p.getY()+tolerence && y >= p.getY()-tolerence){
					return p.getZ();
				}
			}
			tolerence+=.01;
		}
	}
}
