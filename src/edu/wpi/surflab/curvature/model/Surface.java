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
}
