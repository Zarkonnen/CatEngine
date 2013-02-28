package com.zarkonnen.catengine.util;

public interface Rotatable<T extends Rotatable> extends Shp {
	public T rotated(boolean aroundCenter, double radians);
	public T rotated(boolean aroundCenter, Angle angle);
}
