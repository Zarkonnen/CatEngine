package com.zarkonnen.catengine.util;

public final class ScreenMode {
	public final int width;
	public final int height;
	public final boolean fullscreen;

	public ScreenMode(int width, int height, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + this.width;
		hash = 13 * hash + this.height;
		hash = 13 * hash + (this.fullscreen ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof ScreenMode)) { return false; }
		return
				((ScreenMode) o2).width == width &&
				((ScreenMode) o2).height == height &&
				((ScreenMode) o2).fullscreen == fullscreen;
	}
	
	@Override
	public String toString() {
		return width + "x" + height + (fullscreen ? " Fullscreen" : " Windowed");
	}
}
