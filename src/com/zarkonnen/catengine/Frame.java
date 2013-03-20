package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.ScreenMode;

public interface Frame {
	public ScreenMode mode();
	public int fps();
	
	public Object nativeRenderer();

	public void rect(Clr c, double x, double y, double width, double height, double angle);
	public void blit(Img img, Clr tint, double alpha, double x, double y, double width, double height, double angle);
}
