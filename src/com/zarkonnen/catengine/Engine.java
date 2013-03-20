package com.zarkonnen.catengine;

public interface Engine {
	public void setup(Game g);
	public void runUntil(Condition u);
	public void destroy();
	public void setExceptionHandler(ExceptionHandler h);
}
