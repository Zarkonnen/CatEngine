package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Pt;

public abstract class Hook {
	public static enum Type {
		HOVER,
		MOUSE_1_DOWN,
		MOUSE_2_DOWN,
		MOUSE_3_DOWN,
		MOUSE_1_CLICKED,
		MOUSE_2_CLICKED,
		MOUSE_3_CLICKED,
		TEST
	}

	public Hook(Type... type) {
		this.types = type;
	}
	
	public boolean ofType(Hook.Type type) {
		for (Type t : types) {
			if (type == t) { return true; }
		}
		return false;
	}
	
	public final Type[] types;
	public abstract void run(Input in, Pt p, Type type);
}
