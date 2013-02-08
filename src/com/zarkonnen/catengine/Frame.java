package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.ScreenMode;

public interface Frame {
	public ScreenMode mode();

	public Frame rect(Clr c, double x, double y, double width, double height, double angle);
	public Frame blit(String img, Clr tint, double x, double y, double width, double height, double angle);
}
