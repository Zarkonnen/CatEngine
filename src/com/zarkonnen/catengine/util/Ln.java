package com.zarkonnen.catengine.util;

public final class Ln implements Shp<Ln> {
	public final Pt start;
	public final Pt end;

	public Ln(Pt start, Pt end) {
		this.start = start;
		this.end = end;
	}
	
	public Ln(double startX, double startY, double endX, double endY) {
		this(new Pt(startX, startY), new Pt(endX, endY));
	}
	
	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Ln)) { return false; }
		return ((Ln) o2).start.equals(start) && ((Ln) o2).end.equals(end);
	}
	
	@Override
	public int hashCode() {
		return start.hashCode() + 53 * end.hashCode();
	}
	
	public Ln reversed() {
		throw new UnsupportedOperationException("Not supported yet.");
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
	
	public Angle angle() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Angle angleVs(Ln l2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public boolean parallel(Ln l2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public boolean perpendicular(Ln l2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Pt intersection(Ln l2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Pt[] intersections(Rect r2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Ln shifted(double dx, double dy) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Ln shifted(Delta d) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Ln scaled(boolean fromCenter, double scale) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
