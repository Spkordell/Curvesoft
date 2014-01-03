package edu.wpi.surflab.curvature.model;

public class DataPoint {
	Double x;
	Double y;
	public DataPoint(Double x,Double y) {
		this.x = x;
		this.y = y;
	}
	public Double getX() {
		return x;
	}
	public Double getY() {
		return this.y;
	}
	public void incrementX() {
		this.x++;
	}
	public void incrementY() {
		this.y++;
	}

}
