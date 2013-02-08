package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Pt;

public abstract class Hook {
	public static enum Type {
		HOVER,
		MOUSE_1,
		MOUSE_2,
		MOUSE_3
	}

	public Hook(Type type) {
		this.type = type;
	}
	
	public final Type type;
	public abstract void run(Input in, Pt p);
}
