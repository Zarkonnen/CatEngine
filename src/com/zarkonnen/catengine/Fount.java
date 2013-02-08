package com.zarkonnen.catengine;

public final class Fount {
	public final String loadBase;
	public final int width;
	public final int displayWidth;
	public final int height;

	public Fount(String name, int width, int displayWidth, int height) {
		this.loadBase = name + "/";
		this.width = width;
		this.displayWidth = displayWidth;
		this.height = height;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + (this.loadBase != null ? this.loadBase.hashCode() : 0);
		hash = 79 * hash + this.width;
		hash = 79 * hash + this.displayWidth;
		hash = 79 * hash + this.height;
		return hash;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Fount)) { return false; }
		return
				((Fount) o2).displayWidth == displayWidth &&
				((Fount) o2).height       == height       &&
				((Fount) o2).width        == width        &&
				((Fount) o2).loadBase.equals(loadBase);
	}
}
