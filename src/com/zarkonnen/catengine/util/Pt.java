package com.zarkonnen.catengine.util;

public final class Pt implements Shp<Pt> {
	public final double x;
	public final double y;

	public Pt(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Pt)) { return false; }
		return
				x == ((Pt) o2).x && 
				y == ((Pt) o2).y;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		return hash;
	}

	@Override
	public Pt center() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Rect bounds() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean intersects(Shp s) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean contains(Shp s) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double centerDist(Shp s) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public double centerDistSq(Shp s) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Delta centerDelta(Shp s) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Angle angle(Pt p2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Pt between(double fraction, Pt p2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Pt towards(double dist, Pt goal) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Pt shifted(double dx, double dy) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Pt shifted(Delta d) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Pt scaled(boolean fromCenter, double scale) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Ln[] borders() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Pt quantized(double quantum) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
