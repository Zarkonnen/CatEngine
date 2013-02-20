package com.zarkonnen.catengine.util;

public interface Shp<T extends Shp> {
	public Pt center();
	public Rect bounds();
	public boolean intersects(Shp s);
	public boolean contains(Shp s);
	public double centerDist(Shp s);
	public double centerDistSq(Shp s);
	public Delta centerDelta(Shp s);
	public T shifted(double dx, double dy);
	public T shifted(Delta d);
	public T scaled(boolean fromCenter, double scale);
}
