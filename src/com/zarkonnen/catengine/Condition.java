package com.zarkonnen.catengine;

public interface Condition {
	public boolean satisfied();
	
	public static final Condition ALWAYS = new Condition() {
		@Override
		public boolean satisfied() {
			return false;
		}
	};
}
