package com.zarkonnen.catengine.util;

public final class Pt {
	public final double x;
	public final double y;

	public Pt(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Pt)) { return false; }
		return this.x == ((Pt) o2).x && this.x == ((Pt) o2).y;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		return hash;
	}
}
