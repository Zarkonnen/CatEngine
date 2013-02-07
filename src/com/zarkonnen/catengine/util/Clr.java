package com.zarkonnen.catengine.util;

public final class Clr {
	public int r, g, b, a;

	public Clr(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Clr(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + this.r;
		hash = 89 * hash + this.g;
		hash = 89 * hash + this.b;
		hash = 89 * hash + this.a;
		return hash;
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof Clr)) { return false; }
		return
				((Clr) o2).r == r &&
				((Clr) o2).g == g &&
				((Clr) o2).b == b &&
				((Clr) o2).a == a;
	}
	
	@Override
	public String toString() {
		return "r=" + r + "g=" + g + "b=" + b + "a=" + a;
	}
	
	public static final Clr BLACK = new Clr(0, 0, 0);
	public static final Clr WHITE = new Clr(255, 255, 255);
	public static final Clr RED = new Clr(255, 0, 0);
}
