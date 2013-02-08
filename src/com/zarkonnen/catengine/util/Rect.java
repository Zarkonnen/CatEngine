package com.zarkonnen.catengine.util;

public final class Rect {
	public double x, y, width, height;

	public Rect(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
}
