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

	@Override
	public Pt center() {
		return center(start.x, start.y, end.x, end.y);
	}
	
	public static Pt center(double startX, double startY, double endX, double endY) {
		return new Pt(startX / 2 + endX / 2, startY / 2 + endY / 2);
	}

	@Override
	public Rect bounds() {
		return bounds(start.x, start.y, end.x, end.y);
	}
	
	public static Rect bounds(double startX, double startY, double endX, double endY) {
		return new Rect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY));
	}
	
	@Override
	public Ln[] borders() {
		return new Ln[] {
			this
		};
	}
	
	public static Ln[] borders(double startX, double startY, double endX, double endY) {
		return new Ln[] {
			new Ln(startX, startY, endX, endY)
		};
	}

	@Override
	public boolean intersects(Shp s) {
		if (s instanceof Pt) {
			return contains(s);
		}
		if (s instanceof Ln) {
			return intersection((Ln) s) != null;
		}
		if (s instanceof Rect) {
			return intersections((Rect) s).length != 0;
		}
		return false;
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

	@Override
	public Ln quantized(double quantum) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public Ln reversed() {
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
		return intersection(start.x, start.y, end.x, end.y, l2.start.x, l2.start.y, l2.end.x, l2.end.y);
	}
	
	public Pt intersection(double xa1, double ya1, double xa2, double ya2, double xb1, double yb1, double xb2, double yb2) {
		double div = (ya2 - ya1) * (xa2 - xa1) - (xb2 - xb1) * (yb2 - yb1);
		if (div == 0) { return null; }
		double tb = ((yb2 - ya1) * (xa2 - xa1) + (xb1 - xa1) * (yb2 - yb1)) / div;
		if (tb < 0 || tb > 1) { return null; }
		return new Pt(xb1 + tb * (xb2 - xb1), yb1 + tb * (yb2 - yb1));
	}
	
	public Pt[] intersections(Rect r2) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
