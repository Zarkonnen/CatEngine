package com.zarkonnen.catengine;

import com.zarkonnen.catengine.util.Pt;
import com.zarkonnen.catengine.util.ScreenMode;
import java.util.ArrayList;

public interface Input {
	public boolean keyDown(String key);
	public Pt cursor();
	public Pt click();
	public int clickButton();
	
	public ScreenMode mode();
	public Input setMode(ScreenMode mode);
	public ArrayList<ScreenMode> modes();
	
	public void quit();
}
