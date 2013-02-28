package com.zarkonnen.catengine.util;

public final class Rect implements Shp<Rect> {
	public double x, y, width, height;

	public Rect(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rect(Pt topLeft, Pt bottomRight) {
		this.x = topLeft.x;
		this.y = topLeft.y;
		this.width = bottomRight.x - topLeft.x;
		this.height = bottomRight.y - topLeft.y;
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + width + " " + height;
	}
	
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Rect)) { return false; }
		return
				x == ((Rect) o2).x &&
				y == ((Rect) o2).y &&
				width == ((Rect) o2).width &&
				height == ((Rect) o2).height;
	}

	@Override
	public int hashCode() {
		int hash = 19;
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.width) ^ (Double.doubleToLongBits(this.width) >>> 32));
		hash = 53 * hash + (int) (Double.doubleToLongBits(this.height) ^ (Double.doubleToLongBits(this.height) >>> 32));
		return hash;
	}

	public boolean contains(Pt p) {
		return
				p != null &&
				p.x >= x &&
				p.y >= y &&
				p.x <  x + width &&
				p.y <  y + height;
	}
	
	public Pt relative(Pt p) {
		return new Pt(p.x - x, p.y - y);
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
	
	public Rect grow(boolean fromCenter, double amt) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Rect intersection(Rect r2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Ln intersection(Ln l) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Rect add(Pt p) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Rect add(Ln l) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Rect add(Rect r2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Rect shifted(double dx, double dy) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Rect shifted(Delta d) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Rect scaled(boolean fromCenter, double scale) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Ln[] borders() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Rect quantized(double quantum) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
