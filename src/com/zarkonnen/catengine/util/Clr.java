package com.zarkonnen.catengine.util;

import java.io.Serializable;
import java.util.HashMap;

public final class Clr implements Serializable {
	public int r, g, b, a;
	public transient Object machineColorCache;

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
	
	public static Clr fromHex(String hex) {
		if (hex.length() < 6) {
			return null;
		}
		if (!hex.matches("[0-9a-fA-F]{6,8}")) { return null; }
		return new Clr(
				Integer.parseInt(hex.substring(0, 2), 16),
				Integer.parseInt(hex.substring(2, 4), 16),
				Integer.parseInt(hex.substring(4, 6), 16),
				hex.length() >= 8 ? Integer.parseInt(hex.substring(6, 8), 16) : 255);
	}
	
	public static int clamp(int amt) {
		return amt > 255 ? 255 : amt < 0 ? 0 : amt;
	}
	
	public Clr mix(double proportion, Clr c2) {
		return new Clr(
				clamp((int) (r * (1 - proportion) + c2.r * proportion)),
				clamp((int) (g * (1 - proportion) + c2.g * proportion)),
				clamp((int) (b * (1 - proportion) + c2.b * proportion)),
				clamp((int) (a * (1 - proportion) + c2.a * proportion))
		);
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
		return hex(r) + hex(g) + hex(b) + (a == 255 ? "" : hex(a));
	}
	
	String hex(int i) {
		String h = Integer.toHexString(i);
		return h.length() == 1 ? "0" + h : h;
	}
	
	public static final Clr BLACK = new Clr(0, 0, 0);
	public static final Clr WHITE = new Clr(255, 255, 255);
	public static final Clr GREY = new Clr(127, 127, 127);
	public static final Clr LIGHT_GREY = new Clr(191, 191, 191);
	public static final Clr DARK_GREY = new Clr(31, 31, 31);
	public static final Clr RED = new Clr(255, 0, 0);
	public static final Clr GREEN = new Clr(0, 255, 0);
	public static final Clr BLUE = new Clr(0, 0, 255);
	public static final Clr YELLOW = new Clr(255, 255, 0);
	public static final Clr ORANGE = new Clr(255, 127, 0);
	public static final Clr MAGENTA = new Clr(255, 0, 255);
	public static final Clr CYAN = new Clr(0, 255, 255);
	
	public static final HashMap<String, Clr> COLORS = new HashMap<String, Clr>();
	static {
		COLORS.put("BLACK", BLACK);
		COLORS.put("WHITE", WHITE);
		COLORS.put("GREY", GREY);
		COLORS.put("LIGHT_GREY", LIGHT_GREY);
		COLORS.put("DARK_GREY", DARK_GREY);
		COLORS.put("RED", RED);
		COLORS.put("GREEN", GREEN);
		COLORS.put("BLUE", BLUE);
		COLORS.put("YELLOW", YELLOW);
		COLORS.put("MAGENTA", MAGENTA);
		COLORS.put("CYAN", CYAN);
	}
	
	public static Clr getNamedColor(String name) {
		return COLORS.get(name);
	}
}
