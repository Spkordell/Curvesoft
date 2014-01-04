package edu.wpi.surflab.curvature.model;

public class DataPoint3D {
	Double x;
	Double y;
	Double z;
	public DataPoint3D(Double x,Double y, Double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Double getX() {
		return this.x;
	}
	public Double getY() {
		return this.y;
	}
	public Double getZ() {
		return this.z;
	}
}
