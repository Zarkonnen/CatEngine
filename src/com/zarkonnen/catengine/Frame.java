package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Rect;
import com.zarkonnen.catengine.util.ScreenMode;

public interface Frame {
	public ScreenMode mode();
	public int fps();
	
	public Object nativeRenderer();

	public Rect rect(Clr c, double x, double y, double width, double height, double angle);
	public Rect blit(String img, Clr tint, double x, double y, double width, double height, double angle, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipped);
}
