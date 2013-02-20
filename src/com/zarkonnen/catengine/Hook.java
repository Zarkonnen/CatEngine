package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Pt;

public abstract class Hook {
	public static enum Type {
		HOVER,
		MOUSE_1,
		MOUSE_2,
		MOUSE_3
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
