package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Clr;
import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.ScreenMode;
import java.util.ArrayList;

public interface Frame {
	public boolean keyDown(String key);
	public Pt cursor();
	public Pt click();
	public int clickButton();
	
	public ScreenMode mode();
	public Frame setMode(ScreenMode mode);
	public ArrayList<ScreenMode> modes();
	
	public Frame rect(Clr c, double x, double y, double width, double height, double angle);
	public Frame blit(String img, Clr tint, double x, double y, double width, double height, double angle);
	
	public void quit();
}
